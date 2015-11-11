package org.xdams.managing.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.xdams.conf.master.ConfBean;
import org.xdams.manager.conf.MultiEditingManager;
import org.xdams.page.view.bean.ManagingBean;
import org.xdams.user.bean.UserBean;
import org.xdams.utility.XMLCopy;
import org.xdams.utility.request.MyRequest;
import org.xdams.workflow.bean.WorkFlowBean;
import org.xdams.xml.builder.XMLBuilder;
import org.xdams.xmlengine.connection.manager.ConnectionManager;
import org.xdams.xw.XWConnection;

public class MultiCopy {
	private Map<String, String[]> parameterMap = null;

	private ModelMap modelMap = null;

	public MultiCopy(Map<String, String[]> parameterMap, ModelMap modelMap) throws Exception {
		this.parameterMap = parameterMap;
		this.modelMap = modelMap;
	}

	public ManagingBean execute() throws Exception {
		XWConnection xwconn = null;
		ConnectionManager connectionManager = new ConnectionManager();
		ConfBean confBean = null;
		ManagingBean managingBean = null;
		String physDoc = MyRequest.getParameter("physDoc", parameterMap);
		String makeAction = MyRequest.getParameter("makeAction", parameterMap);
		String applyTo = MyRequest.getParameter("applyTo", parameterMap);
		String selid = MyRequest.getParameter("selid", parameterMap);
		String whichTo = MyRequest.getParameter("whichTo", parameterMap);
		boolean menuFlag = true;
		HttpSession httpSession = null;
		List<String> confControl = new ArrayList<String>();
		confControl.add("titleManager");
		confControl.add("valoriControllati");
		confControl.add("docEdit");
		try {
			UserBean userBean = (UserBean) modelMap.get("userBean");
			confBean = (ConfBean) modelMap.get("confBean");
			WorkFlowBean workFlowBean = (WorkFlowBean) modelMap.get("workFlowBean");
			httpSession = workFlowBean.getRequest().getSession(false);
			xwconn = connectionManager.getConnection(workFlowBean.getArchive());
			MultiEditingManager editingManager = new MultiEditingManager(parameterMap, confBean, userBean, workFlowBean);
			editingManager.setTheXML(new XMLBuilder(xwconn.getSingleXMLFromNumDoc(Integer.parseInt(physDoc)), "ISO-8859-1"));
			confBean = editingManager.rewriteMultipleConf(confControl);

			if (httpSession.getAttribute(workFlowBean.getManagingBeanName()) != null) {
				managingBean = ((ManagingBean) httpSession.getAttribute(workFlowBean.getManagingBeanName()));
			} else {
				managingBean = new ManagingBean();
			}
			// mi serve per contare i successi e no
			managingBean.setDocSuccessi(0);
			managingBean.setDocErrori(0);

			managingBean.setPhysDoc(Integer.parseInt(physDoc));
			managingBean.setTitle((xwconn.getTitle(xwconn.connection, xwconn.getTheDb(), managingBean.getPhysDoc())).getTitle());
			it.highwaytech.db.QueryResult qrHier = xwconn.getQRFromHier(managingBean.getPhysDoc(), true);
			managingBean.setNumElementiHier(qrHier.elements);
			it.highwaytech.db.QueryResult qr = null;
			if (!selid.equals("")) {
				qr = xwconn.getQRFromSelId(selid);
				managingBean.setNumElementi(qr.elements);
			}

			if (!whichTo.equals("") && whichTo.equals("selected")) {
				// eseguo il taglia multiplo come figlio
				if (managingBean != null && managingBean.getListPhysDoc().size() > 0 && makeAction != null && makeAction.equals("true") && applyTo != null && applyTo.equals("cut_as_son")) {
					menuFlag = false;
					ArrayList arrayList = managingBean.getListPhysDoc();
					for (int i = 0; i < arrayList.size(); i++) {
						try {
							int physDocToCut = duplica(xwconn, confBean, userBean, (String) arrayList.get(i));
							xwconn.docRelInsert(physDocToCut, Integer.parseInt(physDoc), it.highwaytech.broker.ServerCommand.navigarel_FIGLIOPADRE);
							managingBean.setDocSuccessi(managingBean.getDocSuccessi() + 1);
						} catch (Exception e) {
							managingBean.addErrorMsg("Errore in duplicazione per il documento  " + arrayList.get(i) + " " + e.getMessage());
							managingBean.setDocErrori(managingBean.getDocErrori() + 1);
						}
					}
					managingBean.setDispatchView("multiCopyResult");
				}
				// eseguo il taglia multiplo come fratello precedente
				if (managingBean != null && managingBean.getListPhysDoc().size() > 0 && makeAction != null && makeAction.equals("true") && applyTo != null && applyTo.equals("cut_as_before")) {
					menuFlag = false;
					ArrayList arrayList = managingBean.getListPhysDoc();
					for (int i = 0; i < arrayList.size(); i++) {
						try {
							int physDocToCut = duplica(xwconn, confBean, userBean, (String) arrayList.get(i));
							xwconn.docRelInsert(physDocToCut, Integer.parseInt(physDoc), it.highwaytech.broker.ServerCommand.navigarel_MINOREMAGGIORE);
							managingBean.setDocSuccessi(managingBean.getDocSuccessi() + 1);
						} catch (Exception e) {
							managingBean.addErrorMsg("Errore in duplicazione per il documento  " + arrayList.get(i) + " " + e.getMessage());
							managingBean.setDocErrori(managingBean.getDocErrori() + 1);
						}
					}
					managingBean.setDispatchView("multiCopyResult");
				}
				// eseguo il taglia multiplo come fratello successivo
				if (managingBean != null && managingBean.getListPhysDoc().size() > 0 && makeAction != null && makeAction.equals("true") && applyTo != null && applyTo.equals("cut_as_after")) {
					menuFlag = false;
					ArrayList arrayList = managingBean.getListPhysDoc();
					for (int i = arrayList.size() - 1; i >= 0; i--) {
						// for (int i = 0; i < arrayList.size(); i++) {
						try {
							int physDocToCut = duplica(xwconn, confBean, userBean, (String) arrayList.get(i));
							xwconn.docRelInsert(physDocToCut, Integer.parseInt(physDoc), it.highwaytech.broker.ServerCommand.navigarel_MAGGIOREMINORE);
							managingBean.setDocSuccessi(managingBean.getDocSuccessi() + 1);
						} catch (Exception e) {
							managingBean.addErrorMsg("Errore in duplicazione per il documento  " + arrayList.get(i) + " " + e.getMessage());
							managingBean.setDocErrori(managingBean.getDocErrori() + 1);
						}
					}
					managingBean.setDispatchView("multiCopyResult");
				}
			} else if (!whichTo.equals("") && whichTo.equals("selid")) {

				if (managingBean != null && managingBean.getNumElementi() > 0 && makeAction != null && makeAction.equals("true") && applyTo != null && applyTo.equals("cut_as_son") && qr != null) {
					menuFlag = false;
					// ArrayList arrayList = managingBean.getListPhysDoc();
					for (int i = 0; i < qr.elements; i++) {
						int physDocToCut = -1;
						try {
							physDocToCut = duplica(xwconn, confBean, userBean, String.valueOf(xwconn.getNumDocFromQRElement(qr, i)));
							;// Integer.parseInt((String) arrayList.get(i));
							if (physDocToCut != Integer.parseInt(physDoc)) {
								xwconn.docRelInsert(physDocToCut, Integer.parseInt(physDoc), it.highwaytech.broker.ServerCommand.navigarel_FIGLIOPADRE);
								managingBean.setDocSuccessi(managingBean.getDocSuccessi() + 1);
							}

						} catch (Exception e) {
							managingBean.addErrorMsg("Errore in duplicazione per il documento  " + physDocToCut + " " + e.getMessage());
							managingBean.setDocErrori(managingBean.getDocErrori() + 1);
						}
					}
					managingBean.setDispatchView("multiCopyResult");
				}
				// eseguo il taglia multiplo come fratello precedente
				if (managingBean != null && managingBean.getNumElementi() > 0 && makeAction != null && makeAction.equals("true") && applyTo != null && applyTo.equals("cut_as_before") && qr != null) {
					menuFlag = false;
					// ArrayList arrayList = managingBean.getListPhysDoc();

					managingBean.setDispatchView("multiCopyResult");
				}
				// eseguo il taglia multiplo come fratello successivo
				if (managingBean != null && managingBean.getNumElementi() > 0 && makeAction != null && makeAction.equals("true") && applyTo != null && applyTo.equals("cut_as_after") && qr != null) {
					menuFlag = false;
					// ArrayList arrayList = managingBean.getListPhysDoc();

					managingBean.setDispatchView("multiCopyResult");
				}

			}

			// se è false allora il jspDispatch è già stato settato per la risposta
			if (menuFlag) {
				managingBean.setDispatchView("multiCopyMenu");
			}

			managingBean.setNumElementi(managingBean.getDocErrori() + managingBean.getDocSuccessi());
			modelMap.put("confBean", confBean);
			modelMap.put("managingBean", managingBean);
		} catch (Exception e) {
			modelMap.put("confBean", confBean);
			modelMap.put("managingBean", managingBean);
			throw new Exception(e.toString());
		} finally {
			connectionManager.closeConnection(xwconn);
		}
		return managingBean;
	}

	private int duplica(XWConnection xwconn, ConfBean confBean, UserBean userBean, String physDoc) throws Exception {
		XMLCopy xmlCopy = new XMLCopy();
		// elemento da tagliare physDoc
		String xmlDoc = xwconn.getSingleXMLFromNumDoc(Integer.parseInt(physDoc));
		String newXML = xmlCopy.xmlCopy(confBean.getTheXMLConfEditing(), new XMLBuilder(xmlDoc, "ISO-8859-1"), userBean);
		int recordNum = xwconn.insert(newXML);
		int toPaste = -1;
//		System.out.println("MultiCopy.execute().duplica " + newXML);
		return recordNum;

	}

}
