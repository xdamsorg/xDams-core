package org.xdams.ajax.command;

import it.highwaytech.db.QueryResult;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
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

import com.fasterxml.jackson.databind.util.JSONPObject;

public class AjaxCommandVerifyUserName {

	private HttpServletRequest req = null;

	private HttpServletResponse res = null;

	private ModelMap modelMap = null;

	public AjaxCommandVerifyUserName(HttpServletRequest req, HttpServletResponse res, ModelMap modelMap) throws FileNotFoundException, IOException {
		this.req = req;
		this.res = res;
		this.modelMap = modelMap;
	}

	public AjaxBean execute() throws Exception {

		AjaxBean ajaxBean = new AjaxBean();
		String username = MyRequest.getParameter("username", req.getParameterMap());
		String account = MyRequest.getParameter("accountVal", req.getParameterMap());
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
			if (account.equals("") || username.equals("")) {
				ajaxBean.setStrXmlOutput("{\"result\":\"exist\"}");
			}else{
				String queryUser = "([XML,/user/@id]=\"" + username + "\") AND ([XML,/user/@account]=\"" + account + "\")";
//				System.out.println("AjaxCommandVerifyUserName.execute(): " + queryUser);
				QueryResult queryResult = xwconn.getQRfromPhrase(queryUser);
				if (queryResult.elements != 0) {
					ajaxBean.setStrXmlOutput("{\"result\":\"exist\"}");
				} else {
					ajaxBean.setStrXmlOutput("{\"result\":\"notExist\"}");
				}
			}
	

		} catch (Exception e) {
			e.printStackTrace();
			ajaxBean.setStrXmlOutput("{\"result\":\"error\"}");
		} finally {
			connectionManager.closeConnection(xwconn);
		}
		return ajaxBean;
	}
}
