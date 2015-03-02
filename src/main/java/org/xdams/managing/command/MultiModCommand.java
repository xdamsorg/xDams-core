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
import org.xdams.utility.ExtractDocument;
import org.xdams.utility.SharpIncrementTool;
import org.xdams.utility.request.MyRequest;
import org.xdams.workflow.bean.WorkFlowBean;
import org.xdams.xml.builder.XMLBuilder;
import org.xdams.xmlengine.connection.manager.ConnectionManager;
import org.xdams.xw.XWConnection;

public class MultiModCommand {

	private Map<String, String[]> parameterMap = null;

	private ModelMap modelMap = null;

	public MultiModCommand(Map<String, String[]> parameterMap, ModelMap modelMap) throws Exception {
		this.parameterMap = parameterMap;
		this.modelMap = modelMap;

	}

	public ManagingBean execute() throws Exception {
		XWConnection xwconn = null;
		ConnectionManager connectionManager = new ConnectionManager();
		ConfBean confBean = null;
		ManagingBean managingBean = null;
		String physDoc = MyRequest.getParameter("physDoc", parameterMap); // myRequest.getParameter("physDoc");
		String makeAction = MyRequest.getParameter("makeAction", parameterMap); // myRequest.getParameter("makeAction");
		String theValue = MyRequest.getParameter("theValue", parameterMap); // myRequest.getParameter("theValue");
		String theXpath = MyRequest.getParameter("theXpath", parameterMap); // myRequest.getParameter("theXpath");
		String applyTo = MyRequest.getParameter("applyTo", parameterMap); // myRequest.getParameter("applyTo");
		String selid = MyRequest.getParameter("selid", parameterMap); // myRequest.getParameter("selid");
		HttpSession httpSession = null;
		List<String> confControl = new ArrayList<String>();
		String titleRole = "";
		confControl.add("titleManager");
		confControl.add("valoriControllati");
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
			
			managingBean.setSelid(selid);
			if (!physDoc.equals("") && makeAction.equals("")) {
				managingBean.setPhysDoc(Integer.parseInt(physDoc));
				managingBean.setDocLowerBrother(xwconn.getNumDocNextBrother(managingBean.getPhysDoc()));
				managingBean.setDocUpperBrother(xwconn.getNumDocPreviousBrother(managingBean.getPhysDoc()));
				it.highwaytech.db.QueryResult qrSons = xwconn.getSonsFromNumDoc(managingBean.getPhysDoc());
				managingBean.setNumElementiSons(qrSons.elements);
				it.highwaytech.db.QueryResult qrHier = xwconn.getQRFromHier(managingBean.getPhysDoc(), false);
				managingBean.setNumElementiHier(qrHier.elements);
				if (!selid.equals("")) {
					it.highwaytech.db.QueryResult qr = xwconn.getQRFromSelId(selid);
					managingBean.setNumElementi(qr.elements);
				}
				managingBean.setTitle((xwconn.getTitle(xwconn.connection, xwconn.getTheDb(), managingBean.getPhysDoc())).getTitle());
				managingBean.setDocXML(xwconn.getSingleXMLFromNumDoc(managingBean.getPhysDoc()));
				managingBean.setDispatchView("multiModMenu");
				if (httpSession.getAttribute(workFlowBean.getManagingBeanName()) != null) {
					ArrayList listDocs = ((ManagingBean) httpSession.getAttribute(workFlowBean.getManagingBeanName())).getListPhysDoc();
					if (listDocs != null && listDocs.size() > 0) {
						managingBean.setListPhysDoc(listDocs);
					}
				}

			} else if (!physDoc.equals("") && makeAction.equals("true")) { // DO
				managingBean.setPhysDoc(Integer.parseInt(physDoc));
				ArrayList elementiNum = ExtractDocument.extractDocument(httpSession, xwconn, managingBean, workFlowBean, applyTo, selid);
				managingBean.setNumElementi(elementiNum.size());
				managingBean.setTitle((xwconn.getTitle(xwconn.connection, xwconn.getTheDb(), Integer.parseInt(physDoc))).getTitle());
				managingBean.setDispatchView("multiModResult");
				int totElementi = elementiNum.size();
				int processati = 0;
				int errori = 0;
				SharpIncrementTool sharpIncrementTool = new SharpIncrementTool();
				for (int i = 0; i < totElementi; i++) {
					int docCorrente = 0;
					try {
						docCorrente = ((Integer) elementiNum.get(i)).intValue();
					} catch (Exception e) {
						docCorrente = (Integer.parseInt((String) elementiNum.get(i)));
					}
					try {
						// gestire con framework
						String docXML = xwconn.getSingleXMLFromNumDoc(docCorrente);
						XMLBuilder xmlBuilder = new XMLBuilder(docXML, "ISO-8859-1");
						String ilValore = theValue;
						String ilNome = theXpath;
						// if (ilValore.indexOf("[#") > 0 && ilValore.indexOf("#]") > 0 && totElementi > 1) {
						if ((ilValore.indexOf("[#") != -1) && (ilValore.indexOf("#]") > 0) && (totElementi > 1)) {
							ilValore = sharpIncrementTool.incrementValue(ilNome, ilValore);
						}
						xmlBuilder.insertValueAt(ilNome, ilValore);
						xwconn.executeUpdateByDocNumber(xmlBuilder.getXML("ISO-8859-1", false), docCorrente);
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
			xwconn.restoreTitleRole();
			connectionManager.closeConnection(xwconn);
		}

		return managingBean;
	}
}
