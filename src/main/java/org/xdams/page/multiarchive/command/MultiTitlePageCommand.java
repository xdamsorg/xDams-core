package org.xdams.page.multiarchive.command;

import it.highwaytech.db.QueryResult;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.xdams.conf.master.ConfBean;
import org.xdams.manager.conf.MultiEditingManager;
import org.xdams.page.multiarchive.bean.MultiArchiveBean;
import org.xdams.page.query.bean.QueryBean;
import org.xdams.page.query.command.FindDocumentCommand;
import org.xdams.user.access.ServiceUser;
import org.xdams.user.bean.Archive;
import org.xdams.user.bean.UserBean;
import org.xdams.utility.request.MyRequest;
import org.xdams.workflow.bean.WorkFlowBean;
import org.xdams.xml.builder.XMLBuilder;
import org.xdams.xmlengine.connection.manager.ConnectionManager;
import org.xdams.xw.XWConnection;
import org.xdams.xw.paging.PagingTool;

public class MultiTitlePageCommand {

	private Map<String, String[]> parameterMap = null;

	private ModelMap modelMap = null;

	public MultiTitlePageCommand(Map<String, String[]> parameterMap, ModelMap modelMap) throws Exception {
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
		confControl.add("query");
		String[] multiArchivedbNames = parameterMap.get("multiArchivedbName");
		try {
			UserBean userBean = (UserBean) modelMap.get("userBean");
			confBean = (ConfBean) modelMap.get("confBean");
			WorkFlowBean workFlowBean = (WorkFlowBean) modelMap.get("workFlowBean");
			httpSession = workFlowBean.getRequest().getSession(false);
			MultiEditingManager editingManager = new MultiEditingManager(parameterMap, confBean, userBean, workFlowBean);
			editingManager.setTheXML(new XMLBuilder("root"));
			confBean = editingManager.rewriteMultipleConf(confControl);
			// mettere queryBean
//			System.out.println("QueryParserCommand.execute() workFlowBean.getAlias() " + workFlowBean.getAlias());
//			System.out.println("QueryParserCommand.execute() aReq.getParameter(\"fromId\") " + workFlowBean.getRequest().getParameter("fromId") + "aaaaaaa");
			List<MultiArchiveBean> multiArchiveBeans = new ArrayList<MultiArchiveBean>();
			if (multiArchivedbNames != null) {
				for (int i = 0; i < multiArchivedbNames.length; i++) {
					try {
						MultiArchiveBean multiArchiveBean = new MultiArchiveBean();
						xwconn = connectionManager.getConnection(ServiceUser.getArchive(userBean, multiArchivedbNames[i]));
						FindDocumentCommand findDocumentCommand = null;
						findDocumentCommand = new FindDocumentCommand(parameterMap, modelMap);
						queryResult = findDocumentCommand.execute(workFlowBean, xwconn);
						QueryBean queryBean = new QueryBean();
						queryBean.setDb(workFlowBean.getAlias());
						System.out.println("findDocumentCommand.getLaFrase() " + findDocumentCommand.getLaFrase());
						System.out.println("findDocumentCommand.getLaFrase() " + findDocumentCommand.getLaFrase());
						System.out.println("findDocumentCommand.getLaFrase() " + findDocumentCommand.getLaFrase());
						System.out.println("findDocumentCommand.getLaFrase() " + findDocumentCommand.getLaFrase());
						System.out.println("findDocumentCommand.getLaFrase() " + findDocumentCommand.getLaFrase());
						queryBean.setQuery(findDocumentCommand.getLaFrase());
						queryBean.setTot(String.valueOf(queryResult.elements));
						multiArchiveBean.setArchive(ServiceUser.getArchive(userBean, multiArchivedbNames[i]));
						multiArchiveBean.setRecordFound((queryResult.elements));
						multiArchiveBeans.add(multiArchiveBean);
						if (httpSession.getAttribute("arrQueryBean" + multiArchivedbNames[i]) == null) {
							ArrayList arrQueryBean = new ArrayList();
							arrQueryBean.add(queryBean);
							httpSession.setAttribute("arrQueryBean" + multiArchivedbNames[i], arrQueryBean);
						} else {
							ArrayList arrQueryBean = (ArrayList) httpSession.getAttribute("arrQueryBean" + multiArchivedbNames[i]);
							boolean insert = true;
							int indexQr = 0;
							for (int z = 0; z < arrQueryBean.size(); z++) {
								QueryBean ilBean = (QueryBean) arrQueryBean.get(z);
								System.out.println("ilBean " + ilBean);
								System.out.println("queryBean " + queryBean);
								if (ilBean.getQuery().equals(queryBean.getQuery())) {
									insert = false;
									arrQueryBean.remove(z);
									arrQueryBean.add(queryBean);
									break;
								}
							}
							if (insert) {
								arrQueryBean.add(queryBean);
								httpSession.setAttribute("arrQueryBean" + multiArchivedbNames[i], arrQueryBean);
							}
							System.out.println("arrQueryBean " + arrQueryBean);
						}
						System.out.println("queryResult.elements: " + queryResult.elements + " " + xwconn.getTheDb());
					} catch (Exception e) {
						// TODO: handle exception
					} finally {
						connectionManager.closeConnection(xwconn);
					}

				}
				modelMap.put("multiArchiveBeans", multiArchiveBeans);
			}
			// System.out.println("QueryParserCommand.execute() " + findDocumentCommand.getLaFrase());
			// System.out.println("QueryParserCommand.execute() " + findDocumentCommand.getLaFrase());
			// System.out.println("QueryParserCommand.execute() " + findDocumentCommand.getLaFrase());
			// System.out.println("QueryParserCommand.execute() " + findDocumentCommand.getLaFrase());
			// System.out.println("QueryParserCommand.execute() " + findDocumentCommand.getLaFrase());
			// System.out.println("QueryParserCommand.execute() " + findDocumentCommand.getLaFrase());
			// System.out.println("QueryParserCommand.execute() " + findDocumentCommand.getLaFrase());
			// queryResult = xwconn.getQRfromPhrase("[XML,/c/did/container/@type]=Volume");
			// PagingTool pagingTool = new PagingTool(parameterMap, modelMap);
			// pagingTool.pagingTitleBean(queryResult, xwconn);
		} catch (Exception e) {
			e.printStackTrace();
			// aReq.setAttribute("confBean", null);
			// aReq.setAttribute("userBean", null);
			throw new Exception(e.toString());
		}

	}
}
