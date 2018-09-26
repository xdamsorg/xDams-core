package org.xdams.managing.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.ui.ModelMap;
import org.xdams.conf.master.ConfBean;
import org.xdams.manager.conf.MultiEditingManager;
import org.xdams.page.view.bean.ManagingBean;
import org.xdams.user.bean.UserBean;
import org.xdams.utility.request.MyRequest;
import org.xdams.workflow.bean.WorkFlowBean;
import org.xdams.xml.builder.XMLBuilder;
import org.xdams.xmlengine.connection.manager.ConnectionManager;
import org.xdams.xw.XWConnection;


public class SortingCommand {

	private Map<String, String[]> parameterMap = null;

	private ModelMap modelMap = null;

	public SortingCommand(Map<String, String[]> parameterMap, ModelMap modelMap) throws Exception {
		this.parameterMap = parameterMap;
		this.modelMap = modelMap;
	}

	public ManagingBean execute() throws Exception {
		XWConnection xwconn = null;
		ConnectionManager connectionManager = new ConnectionManager();
		ConfBean confBean = null;
		ManagingBean managingBean = null;
		
		String physDoc = MyRequest.getParameter("physDoc", parameterMap);
 		String makeAction = MyRequest.getParameter("makeAction", parameterMap);
		String sortingCriteria = MyRequest.getParameter("sortingCriteria", parameterMap);
 		
		List<String> confControl = new ArrayList<String>();
		String titleRole = "";
		confControl.add("titleManager");
		confControl.add("docEdit");
		try {
			System.out.println("SortingCommand.execute() "+sortingCriteria);
			UserBean userBean = (UserBean) modelMap.get("userBean");
			confBean = (ConfBean) modelMap.get("confBean");
			WorkFlowBean workFlowBean = (WorkFlowBean) modelMap.get("workFlowBean");
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
			
			
			managingBean = new ManagingBean();
			if (!physDoc.equals("") && makeAction.equals("")) {
				it.highwaytech.db.QueryResult qr = xwconn.getSonsFromNumDoc(Integer.parseInt(physDoc));
				managingBean.setTitle((xwconn.getTitle(xwconn.connection, xwconn.getTheDb(), Integer.parseInt(physDoc))).getTitle());
				managingBean.setNumElementi(qr.elements);
				managingBean.setDispatchView("sortingMenu");
				managingBean.setPhysDoc(Integer.parseInt(physDoc));
			} else if (!physDoc.equals("") && makeAction.equals("true")) {
				it.highwaytech.db.QueryResult qr = xwconn.getSonsFromNumDoc(Integer.parseInt(physDoc));
				qr = xwconn.selectQR("[?SEL]=\"" + qr.id + "\"", sortingCriteria, it.highwaytech.broker.ServerCommand.find_SORT, 0);
				managingBean.setTitle((xwconn.getTitle(xwconn.connection, xwconn.getTheDb(), Integer.parseInt(physDoc))).getTitle());
				managingBean.setNumElementi(qr.elements);
				managingBean.setDispatchView("sortingResult");
				managingBean.setPhysDoc(Integer.parseInt(physDoc));
				int totElementi = qr.elements;
				int processati = 0;
				int errori = 0;
				for (int i = 0; i < totElementi; i++) {
					int docCorrente = xwconn.getNumDocFromQRElement(qr, i);
					try {
						// gestire con framework
						xwconn.docRelDelete(xwconn.connection, workFlowBean.getAlias(), it.highwaytech.broker.ServerCommand.navigarel_FIGLIOPADRE, docCorrente, managingBean.getPhysDoc());
						xwconn.docRelInsert(xwconn.connection, workFlowBean.getAlias(), it.highwaytech.broker.ServerCommand.navigarel_FIGLIOPADRE, docCorrente, managingBean.getPhysDoc());
						processati++;
					} catch (Exception e) {
						xwconn.docRelInsert(xwconn.connection, workFlowBean.getAlias(), it.highwaytech.broker.ServerCommand.navigarel_FIGLIOPADRE, docCorrente, 1);
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
