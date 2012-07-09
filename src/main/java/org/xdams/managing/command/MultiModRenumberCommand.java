package org.xdams.managing.command;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.xdams.conf.master.ConfBean;
import org.xdams.manager.conf.MultiEditingManager;
import org.xdams.page.view.bean.ManagingBean;
import org.xdams.user.bean.UserBean;
import org.xdams.utility.RenumberUtils;
import org.xdams.utility.request.MyRequest;
import org.xdams.workflow.bean.WorkFlowBean;
import org.xdams.xml.builder.XMLBuilder;
import org.xdams.xmlengine.connection.manager.ConnectionManager;
import org.xdams.xw.XWConnection;


public class MultiModRenumberCommand {

	private Map<String, String[]> parameterMap = null;

	private ModelMap modelMap = null;

	public MultiModRenumberCommand(Map<String, String[]> parameterMap, ModelMap modelMap) throws Exception {
		this.parameterMap = parameterMap;
		this.modelMap = modelMap;
	}

	public ManagingBean execute() throws Exception {
		XWConnection xwconn = null;
		ConnectionManager connectionManager = new ConnectionManager();
		ConfBean confBean = null;
		ManagingBean managingBean = null;
		String physDoc =  MyRequest.getParameter("physDoc", parameterMap);//myRequest.getParameter("physDoc");
		String makeAction = MyRequest.getParameter("makeAction", parameterMap);//myRequest.getParameter("makeAction");
		String codiceId = MyRequest.getParameter("codiceId", parameterMap);//myRequest.getParameter("codiceId");
		String numZeri = MyRequest.getParameter("numZeri", parameterMap);//myRequest.getParameter("numZeri");
		String theValue = MyRequest.getParameter("theValue", parameterMap);//myRequest.getParameter("theValue");
		String applyTo = MyRequest.getParameter("applyTo", parameterMap);//myRequest.getParameter("applyTo");
		String selid = MyRequest.getParameter("selid", parameterMap);//myRequest.getParameter("selid");
		boolean countBlock = false;
		try {
			if (MyRequest.getParameter("countBlock", parameterMap).equals("on")) {
				countBlock = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		HttpSession httpSession = null; //aReq.getSession(false);
		List<String> confControl = new ArrayList<String>();
		confControl.add("titleManager");
		confControl.add("docEdit");
		try {

			managingBean = new ManagingBean();
			UserBean userBean = (UserBean) modelMap.get("userBean");
			confBean = (ConfBean) modelMap.get("confBean");
			WorkFlowBean workFlowBean = (WorkFlowBean) modelMap.get("workFlowBean");
			httpSession = workFlowBean.getRequest().getSession(false);
			xwconn = connectionManager.getConnection(workFlowBean.getArchive());

			MultiEditingManager editingManager = new MultiEditingManager(parameterMap, confBean, userBean, workFlowBean);
			editingManager.setTheXML(new XMLBuilder(xwconn.getSingleXMLFromNumDoc(Integer.parseInt(physDoc)), "ISO-8859-1"));
			confBean = editingManager.rewriteMultipleConf(confControl);
			managingBean.setSelid(selid);
			if (!physDoc.equals("") && makeAction.equals("true")) { // DO
				managingBean.setPhysDoc(Integer.parseInt(physDoc));
				ArrayList elementiNum = new ArrayList();
				if (applyTo.equals("selected") || applyTo.equals("prevSibling") || applyTo.equals("nextSibling")) {
					if (applyTo.equals("nextSibling")) {
						int theBrother = managingBean.getPhysDoc();
						RenumberUtils.Renumber rn = RenumberUtils.getSiblingRenumber(theValue, 1);
						while (theBrother > 0) {
							theBrother = xwconn.getNumDocNextBrother(theBrother);
							if (theBrother > 0) {
								Object[] value = new Object[2];
								value[0] = new Integer(theBrother);
								value[1] = rn.renumber();
								elementiNum.add(value);
							}
						}
					} else if (applyTo.equals("prevSibling")) {
						int theBrother = managingBean.getPhysDoc();
						RenumberUtils.Renumber rn = RenumberUtils.getSiblingRenumber(theValue, -1, Integer.parseInt(numZeri));
						while (theBrother > 0) {
							theBrother = xwconn.getNumDocPreviousBrother(theBrother);
							if (theBrother > 0) {
								Object[] value = new Object[2];
								value[0] = new Integer(theBrother);
								value[1] = rn.renumber();
								// elementiNum.add(new Integer(theBrother));
								elementiNum.add(value);
							}
						}
					} else {// DA SELEZIONE MULTIPLA
						RenumberUtils.Renumber rn = RenumberUtils.getSiblingRenumber(theValue, 1, Integer.parseInt(numZeri));
						ArrayList elementiNumTemp = ((ManagingBean) httpSession.getAttribute(workFlowBean.getManagingBeanName())).getListPhysDoc();
						for (int i = 0; i < elementiNumTemp.size(); i++) {
							Object[] value = new Object[2];
							value[0] = (Integer) (elementiNumTemp.get(i));
							value[1] = rn.renumber();
							elementiNum.add(value);
						}
					}
				} else {
					it.highwaytech.db.QueryResult qr = null;
					if (applyTo.equals("selid")) {
						qr = xwconn.getQRFromSelId(selid);
						RenumberUtils.Renumber rn = RenumberUtils.getSiblingRenumber(theValue, 1, Integer.parseInt(numZeri));

						for (int z = 0; z < qr.elements; z++) {
							int theNumber = xwconn.getNumDocFromQRElement(qr, z);
							Object[] value = new Object[2];
							value[0] = new Integer(theNumber);
							value[1] = rn.renumber();

							elementiNum.add(value);
						}
					} else if (applyTo.equals("sons")) {
						qr = xwconn.getSonsFromNumDoc(managingBean.getPhysDoc());
						RenumberUtils.Renumber rn = RenumberUtils.getSonsRenumber(theValue, Integer.parseInt(numZeri));

						for (int z = 0; z < qr.elements; z++) {
							int theNumber = xwconn.getNumDocFromQRElement(qr, z);
							Object[] value = new Object[2];
							value[0] = new Integer(theNumber);
							value[1] = rn.renumber();

							elementiNum.add(value);
						}
					} else if (applyTo.equals("hier")) {
						RenumberUtils.Renumber rn = RenumberUtils.getHierRenumber(theValue, Integer.parseInt(numZeri));

						qr = xwconn.getQRFromHier(managingBean.getPhysDoc(), true);
						for (int i = 0; i < qr.elements; i++) {
							int theNumber = xwconn.getNumDocFromQRElement(qr, i);
							int tmpLevel = (xwconn.getHierPath(theNumber)).depth();
							System.out.println("MultiModRenumberCommand.execute() " + tmpLevel);
							Object[] value = new Object[2];
							value[0] = new Integer(theNumber);
							value[1] = rn.renumber(tmpLevel, countBlock);
							elementiNum.add(value);
						}
					}
				}
				managingBean.setNumElementi(elementiNum.size());
				managingBean.setTitle((xwconn.getTitle(xwconn.connection, xwconn.getTheDb(), Integer.parseInt(physDoc))).getTitle());
				managingBean.setDispatchView("multiModResult");
				int totElementi = elementiNum.size();
				int processati = 0;
				int errori = 0;
				// HashMap laMappa = new HashMap();
				for (int i = 0; i < totElementi; i++) {
					int docCorrente = 0;
					try {
						docCorrente = ((Integer) (((Object[]) elementiNum.get(i))[0])).intValue();
					} catch (Exception e) {
						docCorrente = (Integer.parseInt((String) elementiNum.get(i)));
					}
					try {
 						String docXML = xwconn.getSingleXMLFromNumDoc(docCorrente);
						XMLBuilder xmlBuilder = new XMLBuilder(docXML, "ISO-8859-1");
						String codiceGenerato = ((String) (((Object[]) elementiNum.get(i))[1]));
						xmlBuilder.insertValueAt(codiceId, codiceGenerato);
						xwconn.executeUpdateByDocNumber(xmlBuilder.getXML("ISO-8859-1"), docCorrente);
						processati++;
					} catch (Exception e) {
						managingBean.addErrorMsg(e.getMessage());
						errori++;
					}
				}
				managingBean.setDocSuccessi(processati);
				managingBean.setDocErrori(errori);
			}
			modelMap.put("confBean", confBean);
			modelMap.put("managingBean", managingBean);
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("confBean", confBean);
			modelMap.put("managingBean", managingBean);
			throw new Exception(e.toString());
		} finally {
			connectionManager.closeConnection(xwconn);
		}
		return managingBean;
	}
}
