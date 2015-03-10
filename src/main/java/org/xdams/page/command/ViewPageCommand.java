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

public class ViewPageCommand {
	private Map<String, String[]> parameterMap = null;

	private ModelMap modelMap = null;

	public ViewPageCommand(Map<String, String[]> parameterMap, ModelMap modelMap) throws Exception {
		this.parameterMap = parameterMap;
		this.modelMap = modelMap;
	}

	public void execute() throws Exception {
		XWConnection xwconn = null;
		ConnectionManager connectionManager = new ConnectionManager();
		ConfBean confBean = null;
		ViewBean viewBean = null;
		String physDoc = MyRequest.getParameter("physDoc", parameterMap);
		String selid = MyRequest.getParameter("selid", parameterMap);
		String totSelid = MyRequest.getParameter("totSelid", parameterMap);
		String pos = MyRequest.getParameter("pos", parameterMap);
		String titleRole = "";
		// HttpSession httpSession = aReq.getSession(false);
		// questa lista può essere presa da un file di configurazione
		List<String> confControl = new ArrayList<String>();
		confControl.add("presentation");
		confControl.add("titleManager");
		confControl.add("valoriControllati");
		confControl.add("docEdit");
		confControl.add("media");
		confControl.add("managing");
		confControl.add("bar-vis");
		confControl.add("bar-managing");
		confControl.add("bar-nav");
		confControl.add("bar-edt");
		confControl.add("bar-docedit");
		try {
			UserBean userBean = (UserBean) modelMap.get("userBean");
			confBean = (ConfBean) modelMap.get("confBean");
			WorkFlowBean workFlowBean = (WorkFlowBean) modelMap.get("workFlowBean");
			viewBean = new ViewBean();
			viewBean.setPhysDoc(Integer.parseInt(physDoc));
			xwconn = connectionManager.getConnection(workFlowBean.getArchive());
			viewBean.setPageName((String) modelMap.get("pageName"));

		
			
			XMLBuilder theXMLDoc = null;
			if (!selid.equals("") && !pos.equals("")) {
				// httpSession.setAttribute("posInQr", new Integer(pos));
				QueryResult queryResult = xwconn.getQRFromSelId(selid);
				String docXML = xwconn.getSingleXMLFromQr(queryResult, Integer.parseInt(pos), true);
				try {
					viewBean.setPhysDocNext(xwconn.getNumDocFromQRElement(queryResult, Integer.parseInt(pos) + 1));
				} catch (Exception e) {
					viewBean.setPhysDocNext(-1);
				}
				try {
					viewBean.setPhysDocPrev(xwconn.getNumDocFromQRElement(queryResult, Integer.parseInt(pos) - 1));
				} catch (Exception e) {
					viewBean.setPhysDocPrev(-1);
				}
				if (Integer.parseInt(pos) < queryResult.elements - 1) {
					viewBean.setPosNext(Integer.parseInt(pos) + 1);
				}
				if (queryResult.elements > 0) {
					viewBean.setPosPrev(Integer.parseInt(pos) - 1);
				}
				// System.out.println("1111111111111111111111111111111111111111");
				// System.out.println("ViewPageCommand.execute() PRIMA" + docXML);
				viewBean.setDocXml(XMLCleaner.clearXwXML(docXML, false));
				// System.out.println("2222222222222222222222222222222");
				theXMLDoc = new XMLBuilder(viewBean.getDocXml(), "ISO-8859-1", "evid");
				// System.out.println("333333333333333333333333333333333333333333");
				// theXMLDoc = new XMLBuilder(viewBean.getDocXml(), "ISO-8859-1");
				theXMLDoc.setTheTerm("");
			} else {
				// System.out.println("4444444444444444444444444444444444444444444");
				viewBean.setDocXml(XMLCleaner.clearXwXML(xwconn.getSingleXMLFromNumDoc(viewBean.getPhysDoc()), true));
//				System.out.println("ViewPageCommand.execute() viewBean.getDocXml() BBB" + viewBean.getDocXml());
				theXMLDoc = new XMLBuilder(viewBean.getDocXml(), "ISO-8859-1");
				// System.out.println("555555555555555555555555555555555555555555");
			}
			  
			viewBean.setXmlBuilder(theXMLDoc);
			MultiEditingManager editingManager = new MultiEditingManager(parameterMap, confBean, userBean, workFlowBean);
			// System.out.println("66666666666666666666666666666666666666666");
			editingManager.setTheXML(viewBean.getXmlBuilder());
			// System.out.println("777777777777777777777777777777777777777777");
			confBean = editingManager.rewriteMultipleConf(confControl);
			XMLBuilder builder = confBean.getTheXMLConfTitle();
			titleRole = builder.valoreNodo("/root/titleManager/sezione[@name='title']/titleRole/text()", false);
//			System.out.println("QueryParserCommand.execute()" + titleRole);
			try {
				if (!titleRole.trim().equals("")) {
					xwconn.setTitleRole(titleRole);
				}
			} catch (Exception e) {
				System.out.println(" ---- ERROR ---- QueryParserCommand (xwconn.setTitleRole(titleRole)), title to parse: " + titleRole);
				xwconn.restoreTitleRole();
			}
			viewBean.setTitle((xwconn.getTitle(xwconn.connection, xwconn.getTheDb(), viewBean.getPhysDoc())).getTitle());
			int docFather = xwconn.docRelNavigate(xwconn.connection, workFlowBean.getArchive().getAlias(), it.highwaytech.broker.ServerCommand.navigarel_FIGLIOPADRE, viewBean.getPhysDoc());
			// se e 0 vuol dire che non e padre di nessuno e quindi e scollegato
			viewBean.setDocFather(docFather);
			// if (docFather == 0) {
			// viewBean.setDocFather(viewBean.getPhysDoc());
			// }

			int docSon = xwconn.docRelNavigate(xwconn.connection, workFlowBean.getArchive().getAlias(), it.highwaytech.broker.ServerCommand.navigarel_PADREFIGLIO, viewBean.getPhysDoc());
			viewBean.setDocSon(docSon);
			int docUpperBrother = xwconn.docRelNavigate(xwconn.connection, workFlowBean.getArchive().getAlias(), it.highwaytech.broker.ServerCommand.navigarel_MAGGIOREMINORE, viewBean.getPhysDoc());
			viewBean.setDocUpperBrother(docUpperBrother);
			int docLowerBrother = xwconn.docRelNavigate(xwconn.connection, workFlowBean.getArchive().getAlias(), it.highwaytech.broker.ServerCommand.navigarel_MINOREMAGGIORE, viewBean.getPhysDoc());
			viewBean.setDocLowerBrother(docLowerBrother);
			viewBean.setPos(MyRequest.getParameter("pos", parameterMap));

			int contatore = 0;
			int testNum = 0;
			// cerco il documento "buono" per il posizionamento sullalbero
			int treePos = viewBean.getPhysDoc();
			while (treePos > 0 && contatore < 5) {
				contatore++;
				testNum = xwconn.docRelNavigate(xwconn.connection, workFlowBean.getArchive().getAlias(), it.highwaytech.broker.ServerCommand.navigarel_MAGGIOREMINORE, treePos);
				if (testNum > 0) {
					treePos = testNum;
				} else {
					testNum = xwconn.docRelNavigate(xwconn.connection, workFlowBean.getArchive().getAlias(), it.highwaytech.broker.ServerCommand.navigarel_FIGLIOPADRE, treePos);
					if (testNum > 0) {
						treePos = testNum;
					} else {
						treePos = viewBean.getPhysDoc();
					}
					break;
				}
			}
			viewBean.setTreePos(treePos);
			viewBean.setHierPath(xwconn.getHierPath(viewBean.getPhysDoc()));
			viewBean.setSelid(selid);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.toString());
		} finally {
			modelMap.put("viewBean", viewBean);
			xwconn.restoreTitleRole();
			connectionManager.closeConnection(xwconn);
		}
	}
}
