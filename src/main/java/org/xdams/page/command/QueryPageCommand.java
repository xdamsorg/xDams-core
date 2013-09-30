package org.xdams.page.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.ui.ModelMap;
import org.xdams.conf.master.ConfBean;
import org.xdams.manager.conf.MultiEditingManager;
import org.xdams.page.query.bean.QueryBean;
import org.xdams.user.bean.UserBean;
import org.xdams.workflow.bean.WorkFlowBean;
import org.xdams.xml.builder.XMLBuilder;
import org.xdams.xmlengine.connection.manager.ConnectionManager;
import org.xdams.xw.XWConnection;

public class QueryPageCommand {

	private Map<String, String[]> parameterMap = null;

	private ModelMap modelMap = null;

	public QueryPageCommand(Map<String, String[]> parameterMap, ModelMap modelMap) throws Exception {
		this.parameterMap = parameterMap;
		this.modelMap = modelMap;
	}

	public void execute() throws Exception {
		XWConnection xwconn = null;
		ConfBean confBean = null;
		ConnectionManager connectionManager = new ConnectionManager();
		// questa lista pu essere presa da un file di configurazione
		List<String> confControl = new ArrayList<String>();
		confControl.add("query");
		confControl.add("valoriControllati");
		try {
			UserBean userBean = (UserBean) modelMap.get("userBean");
			confBean = (ConfBean) modelMap.get("confBean");
			WorkFlowBean workFlowBean = (WorkFlowBean) modelMap.get("workFlowBean");
			MultiEditingManager editingManager = new MultiEditingManager(parameterMap, confBean, userBean, workFlowBean);
			editingManager.setTheXML(new XMLBuilder("root"));
			confBean = editingManager.rewriteMultipleConf(confControl);
			QueryBean queryBean = new QueryBean();
			try {
				xwconn = connectionManager.getConnection(workFlowBean.getArchive());
				XMLBuilder theXMLconf = confBean.getTheXMLConfQuery();
				// System.out.println("theXMLconftheXMLconftheXMLconftheXMLconf " + theXMLconf);
				it.highwaytech.db.QueryResult qr = null;
				if (theXMLconf.valoreNodo("/root/query/@numDocInfoQuery").equals("")) {
					qr = xwconn.selectQR("([UD,/xw/@UdType/]=\"" + workFlowBean.getArchive().getPne() + "\")");
				} else {
					qr = xwconn.selectQR(theXMLconf.valoreNodo("/root/query/@numDocInfoQuery", false));
				}
				queryBean.setTotNumDoc(qr.elements);
				queryBean.setLastUpdate(xwconn.getLastUpdate());
			} catch (Exception e) {
				// TODO: handle exception
			}

			// System.out.println("QueryPageCommand.execute() " + confBean.getTheXMLConfQuery().getXML("ISO-8859-1"));
			// System.out.println("QueryPageCommand.execute() " + confBean.getTheXMLValControllati().getXML("ISO-8859-1"));
			modelMap.put("confBean", confBean);
			modelMap.put("queryBean", queryBean);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.toString());
		} finally {
			connectionManager.closeConnection(xwconn);
		}
	}
}
