package org.xdams.ajax.command;

import it.highwaytech.db.QueryResult;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.ModelMap;
import org.xdams.ajax.bean.AjaxBean;
import org.xdams.conf.master.ConfBean;
import org.xdams.manager.conf.MultiEditingManager;
import org.xdams.user.bean.UserBean;
import org.xdams.utility.request.MyRequest;
import org.xdams.workflow.bean.WorkFlowBean;
import org.xdams.xml.builder.XMLBuilder;
import org.xdams.xmlengine.connection.manager.ConnectionManager;
import org.xdams.xw.XWConnection;


public class AjaxCommandNotInHier {

	private HttpServletRequest req = null;

	private HttpServletResponse res = null;

	private ModelMap modelMap = null;

	public AjaxCommandNotInHier(HttpServletRequest req, HttpServletResponse res, ModelMap modelMap) throws FileNotFoundException, IOException {
		this.req = req;
		this.res = res;
		this.modelMap = modelMap;
	}

	public AjaxBean execute() throws Exception {

		AjaxBean ajaxBean = new AjaxBean();
		String nameFileXml = MyRequest.getParameter("nameFileXml", req.getParameterMap());
		// aReq.getParameter("nameFileXml");
		String xpath = MyRequest.getParameter("xpath", req.getParameterMap());// aReq.getParameter("xpath");
		// System.err.println("aaaaaaaaaaaaaaaaaaaaaaa xpath " + xpath);
		// System.err.println("aaaaaaaaaaaaaaaaaaaaaaa nameFileXml " + nameFileXml);
		ConfBean confBean = null;
		WorkFlowBean workFlowBean = null;
		ConnectionManager connectionManager = new ConnectionManager();
		XWConnection xwconn = null;
		List<String> confControl = new ArrayList<String>();
		confControl.add("query");
		try {
			UserBean userBean = (UserBean) modelMap.get("userBean");
			confBean = (ConfBean) modelMap.get("confBean");
			workFlowBean = (WorkFlowBean) modelMap.get("workFlowBean");

			xwconn = connectionManager.getConnection(workFlowBean.getArchive());
			MultiEditingManager editingManager = new MultiEditingManager(req.getParameterMap(), confBean, userBean, workFlowBean);
			editingManager.setTheXML(new XMLBuilder("root"));
			confBean = editingManager.rewriteMultipleConf(confControl);
			XMLBuilder xmlBuilder = confBean.getTheXMLConfQuery();
			String qlphrase = xmlBuilder.valoreNodo("/root/access_method/query[@ajaxCheck='notinhier']/text()");
			System.out.println("AjaxCommandNotInHier.execute() qlphrase " + qlphrase);
			if (!qlphrase.trim().equals("")) {
				int intDoc = Integer.parseInt(StringUtils.substringAfter(qlphrase.toLowerCase(), "notinhier:"));
				QueryResult queryResult = xwconn.getQRFromHier(intDoc, true);
				String query = " NOT ([?SEL]=" + queryResult.id + ") AND ([UD,/xw/@UdType/]=\"" + workFlowBean.getArchive().getPne() + "\")";
				queryResult = xwconn.getQRfromPhrase(query);
				if (queryResult.elements != 0) {
					ajaxBean.setStrXmlOutput("" + queryResult.elements);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			ajaxBean.setStrXmlOutput("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<optionList><option><text>Errore Caricamento</text><value></value></option>\n</optionList>");
		} finally {
			connectionManager.closeConnection(xwconn);
		}
		return ajaxBean;
	}

}
