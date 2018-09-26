package org.xdams.managing.command;

import it.highwaytech.db.QueryResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.xdams.conf.master.ConfBean;
import org.xdams.manager.conf.MultiEditingManager;
import org.xdams.page.view.bean.ManagingBean;
import org.xdams.user.bean.UserBean;
import org.xdams.utility.request.MyRequest;
import org.xdams.workflow.bean.WorkFlowBean;
import org.xdams.xml.builder.XMLBuilder;
import org.xdams.xmlengine.connection.manager.ConnectionManager;
import org.xdams.xw.XWConnection;



public class EraseCommand {

	// private HttpServletRequest aReq = null;
	//
	// private ServletContext servletContext = null;
	//
	// public EraseCommand(HttpServletRequest req, ServletContext servletContext) throws FileNotFoundException, IOException {
	// this.aReq = req;
	// this.servletContext = servletContext;
	// }

	private Map<String, String[]> parameterMap = null;

	private ModelMap modelMap = null;

	public EraseCommand(Map<String, String[]> parameterMap, ModelMap modelMap) throws Exception {
		this.parameterMap = parameterMap;
		this.modelMap = modelMap;
	}

	public ManagingBean execute() throws Exception {
		XWConnection xwconn = null;
		ConnectionManager connectionManager = new ConnectionManager();
		ConfBean confBean = null;
		ManagingBean managingBean = null;
		String physDoc = MyRequest.getParameter("physDoc", parameterMap);
		String selid = MyRequest.getParameter("selid", parameterMap);
		String makeAction = MyRequest.getParameter("makeAction", parameterMap);
		String applyTo = MyRequest.getParameter("applyTo", parameterMap);
		String pos = MyRequest.getParameter("pos", parameterMap);
	
		HttpSession httpSession = null; 
		List<String> confControl = new ArrayList<String>();
		String titleRole = "";
		confControl.add("titleManager");
		confControl.add("valoriControllati");
		confControl.add("managing");
		try {
			// ManagingBean esiste in sessione ma io uso uno nuovo per le funzioni di gestione
			managingBean = new ManagingBean();
			UserBean userBean = (UserBean) modelMap.get("userBean");
			confBean = (ConfBean) modelMap.get("confBean");
			WorkFlowBean workFlowBean = (WorkFlowBean) modelMap.get("workFlowBean");
			httpSession = workFlowBean.getRequest().getSession(false);
			managingBean.setSelid(selid); 
			MultiEditingManager editingManager = new MultiEditingManager(parameterMap, confBean, userBean, workFlowBean);
			// prendo i riferimenti al record
			if (!physDoc.equals("") && makeAction.equals("")) {
				xwconn = connectionManager.getConnection(workFlowBean.getArchive());
				managingBean.setPhysDoc(Integer.parseInt(physDoc));
				editingManager.setTheXML(new XMLBuilder(xwconn.getSingleXMLFromNumDoc(managingBean.getPhysDoc()), "ISO-8859-1"));
				confBean = editingManager.rewriteMultipleConf(confControl);
				
				
				XMLBuilder builder = confBean.getTheXMLConfTitle();
				titleRole = builder.valoreNodo("/root/titleManager/sezione[@name='defaultTitle']/titleRole/text()", false);
	 			try {
					if (!titleRole.trim().equals("")) {
						xwconn.setTitleRole(titleRole);
					}
				} catch (Exception e) {
					System.out.println(" ---- ERROR ---- QueryParserCommand (xwconn.setTitleRole(titleRole)), title to parse: " + titleRole);
					xwconn.restoreTitleRole();
				}				
				
				managingBean.setDocLowerBrother(xwconn.getNumDocNextBrother(managingBean.getPhysDoc()));
				managingBean.setDocUpperBrother(xwconn.getNumDocPreviousBrother(managingBean.getPhysDoc()));

				it.highwaytech.db.QueryResult qrHier = xwconn.getQRFromHier(managingBean.getPhysDoc(), true);
				managingBean.setNumElementiHier(qrHier.elements);

				if (!selid.equals("")) {
					it.highwaytech.db.QueryResult qr = xwconn.getQRFromSelId(selid);
					managingBean.setNumElementi(qr.elements);
				}

				managingBean.setTitle((xwconn.getTitle(xwconn.connection, xwconn.getTheDb(), managingBean.getPhysDoc())).getTitle());
				managingBean.setDispatchView("eraseMenu");
				if (httpSession.getAttribute(workFlowBean.getManagingBeanName()) != null) {
					ArrayList listDocs = ((ManagingBean) httpSession.getAttribute(workFlowBean.getManagingBeanName())).getListPhysDoc();
					if (listDocs != null && listDocs.size() > 0) {
						managingBean.setListPhysDoc(listDocs);
					}
				}

			} else if (!physDoc.equals("") && makeAction.equals("true")) { // DO
				xwconn = connectionManager.getConnection(workFlowBean.getArchive());
				managingBean.setPhysDoc(Integer.parseInt(physDoc));
				editingManager.setTheXML(new XMLBuilder(xwconn.getSingleXMLFromNumDoc(managingBean.getPhysDoc()), "ISO-8859-1"));
				confBean = editingManager.rewriteMultipleConf(confControl);
				ArrayList elementiNum = new ArrayList();
				if (applyTo.equals("selected") || applyTo.equals("prevSibling") || applyTo.equals("nextSibling")) {
					if (applyTo.equals("nextSibling")) {
						int theBrother = managingBean.getPhysDoc();
						while (theBrother > 0) {
							theBrother = xwconn.getNumDocNextBrother(theBrother);
							if (theBrother > 0) {
								elementiNum.add(new Integer(theBrother));
							}
						}
					} else if (applyTo.equals("prevSibling")) {
						int theBrother = managingBean.getPhysDoc();
						while (theBrother > 0) {
							theBrother = xwconn.getNumDocPreviousBrother(theBrother);
							if (theBrother > 0) {
								elementiNum.add(new Integer(theBrother));
							}
						}
					} else {// DA SELEZIONE MULTIPLA
						elementiNum = ((ManagingBean) httpSession.getAttribute(workFlowBean.getManagingBeanName())).getListPhysDoc();
					}

				} else {
					it.highwaytech.db.QueryResult qr = null;
					if (applyTo.equals("selid")) {
						qr = xwconn.getQRFromSelId(selid);
						for (int z = 0; z < qr.elements; z++) {
							int theNumber = xwconn.getNumDocFromQRElement(qr, z);
							elementiNum.add(new Integer(theNumber));
						}
					} else if (applyTo.equals("hier") || applyTo.equals("hierfalse")) {
						boolean includeFather = true;
						if (applyTo.equals("hierfalse")) {
							includeFather = false;
						}
						qr = xwconn.getQRFromHier(managingBean.getPhysDoc(), includeFather);
						managingBean.setNumElementi(qr.elements);
						xwconn.deleteHier(managingBean.getPhysDoc());
						managingBean.setDocSuccessi(-100);
						managingBean.setDocErrori(-100);
					}

				}
				if (!applyTo.equals("hier") && !applyTo.equals("hierfalse")) {
					for (int i = 0; i < elementiNum.size(); i++) {
						QueryResult qrTemp = null;
						try {
							qrTemp = xwconn.getQRFromHier(((Integer) elementiNum.get(i)).intValue(), false);
						} catch (Exception e) {
							qrTemp = xwconn.getQRFromHier(Integer.parseInt((String) elementiNum.get(i)), false);
						}
						if (qrTemp != null && qrTemp.elements > 0) {
							for (int z = 0; z < qrTemp.elements; z++) {
								int theNumber = xwconn.getNumDocFromQRElement(qrTemp, z);
								elementiNum.add(new Integer(theNumber));
							}
						}
					}
					managingBean.setNumElementi(elementiNum.size());
					int totElementi = elementiNum.size();
					int processati = 0;
					int errori = 0;

					for (int i = 0; i < totElementi; i++) {
						int docCorrente = 0;
						try {
							docCorrente = ((Integer) elementiNum.get(i)).intValue();
						} catch (Exception e) {
							docCorrente = (Integer.parseInt((String) elementiNum.get(i)));
						}
						try {
							xwconn.delete(docCorrente);
							processati++;
						} catch (Exception e) {
							managingBean.addErrorMsg(e.getMessage());
							errori++;
						}
					}
					managingBean.setDocSuccessi(processati);
					managingBean.setDocErrori(errori);
				}
				managingBean.setTitle("");
				managingBean.setDispatchView("eraseResult");
				if (httpSession.getAttribute(workFlowBean.getManagingBeanName()) != null) {
					ManagingBean sessionMenaging = (ManagingBean) httpSession.getAttribute(workFlowBean.getManagingBeanName());
					sessionMenaging.setListPhysDoc(new ArrayList()); // SVUOTO GLI ELEMENTI SELEZIONATI
					httpSession.setAttribute(workFlowBean.getManagingBeanName(), sessionMenaging);
				}
			}
//			aReq.setAttribute("confBean", confBean);
//			aReq.setAttribute("userBean", userBean);
//			aReq.setAttribute("managingBean", managingBean);
			modelMap.put("confBean", confBean);
			modelMap.put("managingBean", managingBean);			
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("confBean", null);
			modelMap.put("managingBean", null);			
			throw new Exception(e.toString());
		} finally {
			xwconn.restoreTitleRole();
			connectionManager.closeConnection(xwconn);
		}

		return managingBean;
	}
}
