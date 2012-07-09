package org.xdams.page.command;

import it.highwaytech.db.QueryResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.ui.ModelMap;
import org.xdams.conf.master.ConfBean;
import org.xdams.manager.conf.MultiEditingManager;
import org.xdams.page.view.bean.ViewBean;
import org.xdams.user.bean.UserBean;
import org.xdams.utility.XMLCleaner;
import org.xdams.utility.request.MyRequest;
import org.xdams.workflow.bean.WorkFlowBean;
import org.xdams.xml.builder.XMLBuilder;
import org.xdams.xmlengine.connection.manager.ConnectionManager;
import org.xdams.xw.XWConnection;


public class InfoTabCommand {

	private Map<String, String[]> parameterMap = null;

	private ModelMap modelMap = null;

	public InfoTabCommand(Map<String, String[]> parameterMap, ModelMap modelMap) throws Exception {
		this.parameterMap = parameterMap;
		this.modelMap = modelMap;
	}

	public void execute() throws Exception {
		XWConnection xwconn = null;
		ConnectionManager connectionManager = new ConnectionManager();
		ConfBean confBean = null;
		ViewBean viewBean = null;
		// String physDoc = MyRequest.getParameter("physDoc", parameterMap);
		// String selid = MyRequest.getParameter("selid", parameterMap);
		// String totSelid = MyRequest.getParameter("totSelid", parameterMap);
		// String pos = MyRequest.getParameter("pos", parameterMap);
		// HttpSession httpSession = aReq.getSession(false);
		// questa lista può essere presa da un file di configurazione
		List<String> confControl = new ArrayList<String>();
		confControl.add("presentation");
		confControl.add("titleManager");
		confControl.add("valoriControllati");
		confControl.add("media");
		try {
			UserBean userBean = (UserBean) modelMap.get("userBean");
			confBean = (ConfBean) modelMap.get("confBean");
			WorkFlowBean workFlowBean = (WorkFlowBean) modelMap.get("workFlowBean");
			viewBean = new ViewBean();
			xwconn = connectionManager.getConnection(workFlowBean.getArchive());
			XMLBuilder theXMLDoc = null;
			String idCode = MyRequest.getParameter("idCode", parameterMap);
			String queryFix = MyRequest.getParameter("queryFix", parameterMap);
			queryFix = "" + queryFix + "=\"" + idCode + "\"";
			int docNumber = xwconn.getNumDocFromQRElement(xwconn.getQRfromPhrase(queryFix), 0);
			viewBean.setPhysDoc(docNumber);
			viewBean.setDocXml(XMLCleaner.clearXwXML(xwconn.getSingleXMLFromNumDoc(viewBean.getPhysDoc()), true));
			theXMLDoc = new XMLBuilder(viewBean.getDocXml(), "ISO-8859-1");
			viewBean.setXmlBuilder(theXMLDoc);
			viewBean.setHierPath(xwconn.getHierPath(viewBean.getPhysDoc()));
			MultiEditingManager editingManager = new MultiEditingManager(parameterMap, confBean, userBean, workFlowBean);
			editingManager.setTheXML(viewBean.getXmlBuilder());
			confBean = editingManager.rewriteMultipleConf(confControl);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.toString());
		} finally {
			modelMap.put("viewBean", viewBean);
			connectionManager.closeConnection(xwconn);
		}
	}

}
