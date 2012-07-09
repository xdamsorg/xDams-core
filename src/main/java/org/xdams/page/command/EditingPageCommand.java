package org.xdams.page.command;

import it.highwaytech.db.QueryResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.ui.ModelMap;
import org.xdams.conf.master.ConfBean;
import org.xdams.manager.conf.MultiEditingManager;
import org.xdams.page.view.bean.EditingBean;
import org.xdams.user.bean.UserBean;
import org.xdams.utility.request.MyRequest;
import org.xdams.workflow.bean.WorkFlowBean;
import org.xdams.xml.builder.XMLBuilder;
import org.xdams.xmlengine.connection.manager.ConnectionManager;
import org.xdams.xw.XWConnection;


public class EditingPageCommand {

	// private HttpServletRequest aReq = null;
	//
	// private ServletContext servletContext = null;
	//
	// public EditingPageCommand(HttpServletRequest req, ServletContext servletContext) throws Exception {
	// aReq = req;
	// this.servletContext = servletContext;
	// }

	private Map<String, String[]> parameterMap = null;

	private ModelMap modelMap = null;

	public EditingPageCommand(Map<String, String[]> parameterMap, ModelMap modelMap) throws Exception {
		this.parameterMap = parameterMap;
		this.modelMap = modelMap;
	}

	public void execute() throws Exception {
		XWConnection xwconn = null;
		ConnectionManager connectionManager = new ConnectionManager();
		ConfBean confBean = null;
		EditingBean editingBean = null;
		String physDoc = MyRequest.getParameter("physDoc", "", parameterMap);
		//vuol dire che vengo dal preinsert
		if(modelMap.get("physDoc")!=null){
			physDoc = (String) modelMap.get("physDoc");
		}
		
		List<String> confControl = new ArrayList<String>();
		confControl.add("docEdit");
		confControl.add("valoriControllati");
		try {
			UserBean userBean = (UserBean) modelMap.get("userBean");
			confBean = (ConfBean) modelMap.get("confBean");
			WorkFlowBean workFlowBean = (WorkFlowBean) modelMap.get("workFlowBean");
			if (physDoc.equals("")) {
				physDoc = (String) workFlowBean.getRequest().getAttribute("physDoc");
			}
			editingBean = new EditingBean();
			editingBean.setPhysDoc(Integer.parseInt(physDoc));
			xwconn = connectionManager.getConnection(workFlowBean.getArchive());
			editingBean.setDocXml(xwconn.getSingleXMLFromNumDoc(editingBean.getPhysDoc()));
			XMLBuilder theXMLDoc = new XMLBuilder(editingBean.getDocXml(), "ISO-8859-1");
			editingBean.setXmlBuilder(theXMLDoc);

			MultiEditingManager editingManager = new MultiEditingManager(parameterMap, confBean, userBean, workFlowBean);
			editingManager.setTheXML(editingBean.getXmlBuilder());
			confBean = editingManager.rewriteMultipleConf(confControl);

			int docFather = xwconn.docRelNavigate(xwconn.connection, workFlowBean.getAlias(), it.highwaytech.broker.ServerCommand.navigarel_FIGLIOPADRE, editingBean.getPhysDoc());
			editingBean.setDocFather(docFather);
			int docSon = xwconn.docRelNavigate(xwconn.connection, workFlowBean.getAlias(), it.highwaytech.broker.ServerCommand.navigarel_PADREFIGLIO, editingBean.getPhysDoc());
			editingBean.setDocSon(docSon);
			int docUpperBrother = xwconn.docRelNavigate(xwconn.connection, workFlowBean.getAlias(), it.highwaytech.broker.ServerCommand.navigarel_MAGGIOREMINORE, editingBean.getPhysDoc());
			editingBean.setDocUpperBrother(docUpperBrother);
			int docLowerBrother = xwconn.docRelNavigate(xwconn.connection, workFlowBean.getAlias(), it.highwaytech.broker.ServerCommand.navigarel_MINOREMAGGIORE, editingBean.getPhysDoc());
			editingBean.setDocLowerBrother(docLowerBrother);

			editingBean.setPos(MyRequest.getParameter("pos", parameterMap));
			editingBean.setSelid(MyRequest.getParameter("selid", parameterMap));

			if (!editingBean.getSelid().equals("") && !editingBean.getPos().equals("")) {
				QueryResult queryResult = xwconn.getQRFromSelId(editingBean.getSelid());

				try {
					editingBean.setPhysDocNext(xwconn.getNumDocFromQRElement(queryResult, Integer.parseInt(editingBean.getPos()) + 1));
				} catch (Exception e) {
					editingBean.setPhysDocNext(-1);
				}
				try {
					editingBean.setPhysDocPrev(xwconn.getNumDocFromQRElement(queryResult, Integer.parseInt(editingBean.getPos()) - 1));
				} catch (Exception e) {
					editingBean.setPhysDocPrev(-1);
				}
				if (Integer.parseInt(editingBean.getPos()) < queryResult.elements - 1) {
					editingBean.setPosNext(Integer.parseInt(editingBean.getPos()) + 1);
				}
				if (queryResult.elements > 0) {
					editingBean.setPosPrev(Integer.parseInt(editingBean.getPos()) - 1);
				}
			}
			if (!MyRequest.getParameter("thePne", parameterMap).equals("")) {
				editingBean.setThePne(MyRequest.getParameter("thePne", parameterMap));
			} else {
				editingBean.setThePne(workFlowBean.getArchive().getPne());
			}

			modelMap.put("confBean", confBean);
			modelMap.put("editingBean", editingBean);
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("confBean", null);
			modelMap.put("editingBean", null);
			throw new Exception(e.toString());
		} finally {
			connectionManager.closeConnection(xwconn);
		}
	}
}
