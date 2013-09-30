package org.xdams.page.command;

import it.highwaytech.db.Title;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.ui.ModelMap;
import org.xdams.conf.master.ConfBean;
import org.xdams.manager.conf.MultiEditingManager;
import org.xdams.page.view.bean.HierBrowserBean;
import org.xdams.page.view.bean.TreeBean;
import org.xdams.user.bean.UserBean;
import org.xdams.utility.request.MyRequest;
import org.xdams.workflow.bean.WorkFlowBean;
import org.xdams.xml.builder.XMLBuilder;
import org.xdams.xmlengine.connection.manager.ConnectionManager;
import org.xdams.xw.XWConnection;


public class HierBrowserPageCommand {

	private Map<String, String[]> parameterMap = null;

	private ModelMap modelMap = null;

	public HierBrowserPageCommand(Map<String, String[]> parameterMap, ModelMap modelMap) throws Exception {
		this.parameterMap = parameterMap;
		this.modelMap = modelMap;
	}

	public void execute() throws Exception {
		XWConnection xwconn = null;
		ConnectionManager connectionManager = new ConnectionManager();
		ConfBean confBean = null;
		TreeBean treeBean = null;
		String titleRole = "";
		List<String> confControl = new ArrayList<String>();
		confControl.add("titleManager");
		confControl.add("query");
		try {
			UserBean userBean = (UserBean) modelMap.get("userBean");
			confBean = (ConfBean) modelMap.get("confBean");
			WorkFlowBean workFlowBean = (WorkFlowBean) modelMap.get("workFlowBean");
			treeBean = new TreeBean();
			MultiEditingManager editingManager = new MultiEditingManager(parameterMap, confBean, userBean, workFlowBean);
			editingManager.setTheXML(new XMLBuilder("root"));
			confBean = editingManager.rewriteMultipleConf(confControl);
			xwconn = connectionManager.getConnection(workFlowBean.getArchive());
			if (MyRequest.getParameter("docStart", parameterMap) != null && !MyRequest.getParameter("docStart", parameterMap).equals("")) {
				treeBean.setDocStart(Integer.parseInt(MyRequest.getParameter("docStart", parameterMap)));
				String docXML = xwconn.getSingleXMLFromNumDoc(treeBean.getDocStart());
				editingManager.setTheXML(new XMLBuilder(docXML, "ISO-8859-1"));
				confBean = editingManager.rewriteMultipleConf(confControl);
			} else {
				confBean = editingManager.rewriteMultipleConf(confControl);
			}
			if (modelMap.get("pageName") != null && modelMap.get("pageName").equals("tree")) {
				System.out.println("HierBrowserPageCommand.execute()");
				XMLBuilder builder = confBean.getTheXMLConfTitle();
				titleRole = builder.valoreNodo("/root/titleManager/sezione[@name='hierBrowser']/titleRole/text()", false);
				System.out.println("HierBrowserPageCommand.execute()" + titleRole);
				try {
					if (!titleRole.trim().equals("")) {
						xwconn.setTitleRole(titleRole);
					}
				} catch (Exception e) {
					System.out.println(" ----- ERROR ---- HierBrowserPageCommand (xwconn.setTitleRole(titleRole)), title to parse: " + titleRole);
					xwconn.restoreTitleRole();
				}
				if (MyRequest.getParameter("docToggle", parameterMap) != null && !MyRequest.getParameter("docToggle", parameterMap).equals("")) {
					treeBean.setDocToggle(Integer.parseInt(MyRequest.getParameter("docToggle", parameterMap)));
				}
				if (!MyRequest.getParameter("docCount", parameterMap).equals("")) {
					treeBean.setDocCount(Integer.parseInt(MyRequest.getParameter("docCount", parameterMap)));
				}
					//else if(WebUtils.getCookie(workFlowBean.getRequest(), workFlowBean.getAlias()+userBean.getId())!=null){
//					treeBean.setDocCount(Integer.parseInt(WebUtils.getCookie(workFlowBean.getRequest(), workFlowBean.getAlias()+userBean.getId()).getValue()));
//				}
//				
//				System.out.println("HierBrowserPageCommand.execute() "+treeBean.getDocCount());
//				System.out.println("HierBrowserPageCommand.execute() WebUtils "+WebUtils.getCookie(workFlowBean.getRequest(), workFlowBean.getAlias()+userBean.getId()).getValue());
//				System.out.println("HierBrowserPageCommand.execute() MyRequest "+MyRequest.getParameter("docCount", parameterMap).equals(""));
//				System.out.println("HierBrowserPageCommand.execute() "+treeBean.getDocCount());
//				System.out.println("HierBrowserPageCommand.execute() "+treeBean.getDocCount());
//				System.out.println("HierBrowserPageCommand.execute() "+treeBean.getDocCount());
//				
 				if (MyRequest.getParameter("hierStatus", parameterMap) != null && !MyRequest.getParameter("hierStatus", parameterMap).equals("")) {
					treeBean.setHierStatus(MyRequest.getParameter("hierStatus", parameterMap));
				}

				if (MyRequest.getParameter("backward", parameterMap) != null && !MyRequest.getParameter("backward", parameterMap).equals("") && MyRequest.getParameter("backward", parameterMap).equals("1")) {
					treeBean.setBackward(true);
				}

				it.highwaytech.broker.BrowseExtras bExtras = new it.highwaytech.broker.BrowseExtras(treeBean.getHierStatus(), treeBean.getDocToggle());
				java.util.Vector vectHierTitle = xwconn.hierList(xwconn.connection, xwconn.getTheDb(), treeBean.getDocStart(), treeBean.isBackward() ? it.highwaytech.broker.ServerCommand.browse_MOVEUP : it.highwaytech.broker.ServerCommand.browse_MOVEDOWN, treeBean.getDocCount(), bExtras);
				treeBean.setHierStatus(bExtras.readStatus());
				treeBean.setVectHierTitle(vectHierTitle);
				if (vectHierTitle != null) {
					treeBean.setUpEnabled(!(((it.highwaytech.db.HierTitle) (vectHierTitle.firstElement())).isFirst()));
					treeBean.setDownEnabled(!(((it.highwaytech.db.HierTitle) (vectHierTitle.lastElement())).isLast()));
				}
				treeBean.setFirstDocNumber(((it.highwaytech.db.HierTitle) (vectHierTitle.firstElement())).docNumber());
				treeBean.setLastDocNumber(((it.highwaytech.db.HierTitle) vectHierTitle.lastElement()).docNumber());

				for (int index = 0; index < vectHierTitle.size(); index++) {
					HierBrowserBean hierBrowserBean = new HierBrowserBean();
					it.highwaytech.db.HierTitle theTitle = ((it.highwaytech.db.HierTitle) (vectHierTitle.get(index)));
					String hier = theTitle.getHier(xwconn.getTheDb(), xwconn.getServerConnection(xwconn.connection), "&#10;");
					hierBrowserBean.setHier(hier);
					int docNumber = theTitle.docNumber();
					hierBrowserBean.setDocNumber(docNumber);
					try {
						it.highwaytech.db.HierPath thePath = xwconn.getHierPath(hierBrowserBean.getDocNumber());
						hierBrowserBean.setHierPath(thePath);
					} catch (Exception e) {
					}
					int depth = theTitle.depth();
					hierBrowserBean.setDepth(depth);
					boolean hasSons = theTitle.hasSons();
					hierBrowserBean.setHasSons(hasSons);
					int firstDocNumber = ((it.highwaytech.db.HierTitle) (vectHierTitle.firstElement())).docNumber();
					hierBrowserBean.setFirstDocNumber(firstDocNumber);
					int lastDocNumber = ((it.highwaytech.db.HierTitle) vectHierTitle.lastElement()).docNumber();
					hierBrowserBean.setLastDocNumber(lastDocNumber);
					boolean opened = theTitle.isOpened();
					hierBrowserBean.setOpened(opened);
					Title titArch = xwconn.getTitle(xwconn.connection, xwconn.getTheDb(), theTitle.docNumber());
					// System.out.println("HierBrowserPageCommand.execute() title docNumber aa "+theTitle.docNumber(xwconn.getTheDb(),
					// xwconn.getServerConnection(xwconn.connection),
					// theTitle.depth()));
					// System.out.println("HierBrowserPageCommand.execute() title docNumber bb "+theTitle.docNumber());
					// System.out.println("HierBrowserPageCommand.execute() title "+title);
					// System.out.println("HierBrowserPageCommand.execute() title "+titArch.getTitle());
					String title = titArch.getTitle();
					hierBrowserBean.setTitle(title);
					boolean lastChild = theTitle.hasBrothers(theTitle.depth());
					hierBrowserBean.setLastChild(!lastChild);
					if (theTitle.depth() > 1) {
						boolean fatherLastChild = theTitle.hasBrothers(theTitle.depth() - 1);
						hierBrowserBean.setFatherLastChild(!fatherLastChild);
					}
					if (theTitle.depth() > 1) {
						boolean[] fathers = new boolean[theTitle.depth() - 1];
						for (int i = theTitle.depth() - 2; i > 0; i--) {
							fathers[(theTitle.depth() - 2) - i] = !theTitle.hasBrothers(theTitle.depth() - i);
						}
						hierBrowserBean.setFathersLastChild(fathers);
					}
					treeBean.getVectHierBrowserBean().addElement(hierBrowserBean);

				}
			}

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
			modelMap.put("treeBean", treeBean);
			modelMap.put("confBean", confBean);
			connectionManager.closeConnection(xwconn);
		}
	}
}
