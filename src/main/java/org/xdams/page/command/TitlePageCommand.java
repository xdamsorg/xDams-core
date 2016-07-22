package org.xdams.page.command;

import it.highwaytech.db.QueryResult;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.xdams.conf.master.ConfBean;
import org.xdams.manager.conf.MultiEditingManager;
import org.xdams.page.query.bean.QueryBean;
import org.xdams.page.query.command.FindDocumentCommand;
import org.xdams.user.bean.UserBean;
import org.xdams.utility.request.MyRequest;
import org.xdams.workflow.bean.WorkFlowBean;
import org.xdams.xml.builder.XMLBuilder;
import org.xdams.xmlengine.connection.manager.ConnectionManager;
import org.xdams.xw.XWConnection;
import org.xdams.xw.paging.PagingTool;

public class TitlePageCommand {

	private Map<String, String[]> parameterMap = null;

	private ModelMap modelMap = null;

	public TitlePageCommand(Map<String, String[]> parameterMap, ModelMap modelMap) throws Exception {
		this.parameterMap = parameterMap;
		this.modelMap = modelMap;
	}

	public void execute() throws Exception {
		XWConnection xwconn = null;
		ConnectionManager connectionManager = new ConnectionManager();
		ConfBean confBean = null;
		QueryResult queryResult = null;
		HttpSession httpSession = null;
		String titleRole = "";
		List<String> confControl = new ArrayList<String>();
		confControl.add("titleManager");
		confControl.add("valoriControllati");
		confControl.add("media");
		confControl.add("query");
		try {
			UserBean userBean = (UserBean) modelMap.get("userBean");
			confBean = (ConfBean) modelMap.get("confBean");
			WorkFlowBean workFlowBean = (WorkFlowBean) modelMap.get("workFlowBean");
			httpSession = workFlowBean.getRequest().getSession(false);

			MultiEditingManager editingManager = new MultiEditingManager(parameterMap, confBean, userBean, workFlowBean);
			editingManager.setTheXML(new XMLBuilder("root"));
			confBean = editingManager.rewriteMultipleConf(confControl);

			// mettere queryBean
			// System.out.println("QueryParserCommand.execute() workFlowBean.getAlias() " + workFlowBean.getAlias());
			// System.out.println("QueryParserCommand.execute() aReq.getParameter(\"fromId\") " + workFlowBean.getRequest().getParameter("fromId") + "aaaaaaa");

			xwconn = connectionManager.getConnection(workFlowBean.getArchive());

			XMLBuilder builder = confBean.getTheXMLConfTitle();
			titleRole = builder.valoreNodo("/root/titleManager/sezione[@name='title']/titleRole/text()", false);
			// System.out.println("QueryParserCommand.execute()" + titleRole);
			try {
				if (!titleRole.trim().equals("")) {
					xwconn.setTitleRole(titleRole);
				}
			} catch (Exception e) {
				System.out.println(" ---- ERROR ---- QueryParserCommand (xwconn.setTitleRole(titleRole)), title to parse: " + titleRole);
				xwconn.restoreTitleRole();
			}

			if (MyRequest.getParameter("fromId", parameterMap).equals("")) {
				FindDocumentCommand findDocumentCommand = null;
				try {
					findDocumentCommand = new FindDocumentCommand(parameterMap, modelMap);
					queryResult = findDocumentCommand.execute(workFlowBean, xwconn);
					
					// mi setto il selid per le operazioni di raffinamento
					workFlowBean.getRequest().setAttribute("qrId", queryResult.id);
					QueryBean queryBean = new QueryBean();
					queryBean.setDb(workFlowBean.getAlias());
					System.out.println("findDocumentCommand.getLaFrase() " + findDocumentCommand.getLaFrase());
					queryBean.setQuery(findDocumentCommand.getLaFrase());
					queryBean.setTot(String.valueOf(queryResult.elements));

					if (httpSession.getAttribute(workFlowBean.getQueryBeanName()) == null) {
						ArrayList arrQueryBean = new ArrayList();
						arrQueryBean.add(queryBean);
						httpSession.setAttribute(workFlowBean.getQueryBeanName(), arrQueryBean);
					} else {
						ArrayList arrQueryBean = (ArrayList) httpSession.getAttribute(workFlowBean.getQueryBeanName());
						boolean insert = true;
						int indexQr = 0;
						for (int i = 0; i < arrQueryBean.size(); i++) {
							QueryBean ilBean = (QueryBean) arrQueryBean.get(i);
							if (ilBean.getQuery().equals(queryBean.getQuery())) {
								insert = false;
								arrQueryBean.remove(i);
								arrQueryBean.add(queryBean);
								break;
							}
						}
						if (insert) {
							arrQueryBean.add(queryBean);
							httpSession.setAttribute(workFlowBean.getQueryBeanName(), arrQueryBean);
						}
					}
				} catch (Exception e) {
					queryResult = new QueryResult();
					workFlowBean.getRequest().setAttribute("qrId", queryResult.id);
				}
				// System.out.println("QueryParserCommand.execute() " + findDocumentCommand.getLaFrase());
			} else {
				queryResult = xwconn.getQRFromSelId(MyRequest.getParameter("qrId", parameterMap));
				workFlowBean.getRequest().setAttribute("qrId", queryResult.id);
			}

			PagingTool pagingTool = new PagingTool(parameterMap, modelMap);
			pagingTool.pagingTitleBean(queryResult, xwconn);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.toString());
		} finally {
			if (!titleRole.trim().equals("")) {
				try {
					xwconn.restoreTitleRole();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			modelMap.put("confBean", confBean);
			connectionManager.closeConnection(xwconn);
		}
	}
}
