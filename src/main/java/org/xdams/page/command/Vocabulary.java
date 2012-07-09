package org.xdams.page.command;

import java.util.Map;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.xdams.user.access.ServiceUser;
import org.xdams.workflow.bean.WorkFlowBean;
import org.xdams.xmlengine.connection.manager.ConnectionManager;
import org.xdams.xw.XWConnection;
import org.xdams.xw.utility.Key;


public class Vocabulary {
	@Autowired
	ServiceUser serviceUser;

 	private ModelMap modelMap = null;

	private Map<String, String[]> parameterMap = null;

	public Vocabulary(Map<String, String[]> parameterMap, ModelMap modelMap) throws Exception {
		this.parameterMap = parameterMap;
		this.modelMap = modelMap;
	}

	@SuppressWarnings("unchecked")
	public void execute() throws Exception {
		XWConnection xwconn = null;
		ConnectionManager connectionManager = new ConnectionManager();
		Vector<Key> result = new Vector<Key>();
		System.out.println("Vocabulary.execute() parameterMap\n " + parameterMap);
		System.out.println("Vocabulary.execute() searchAlias\n " + parameterMap.get("searchAlias")[0]);
		String searchAlias = parameterMap.get("searchAlias")[0];
		System.out.println("searchAlias: " + searchAlias);
		WorkFlowBean workFlowBean = (WorkFlowBean) modelMap.get("workFlowBean");
		System.out.println(workFlowBean);
		String name = "";
		try {
			if (parameterMap.get("name") != null) {
				name = parameterMap.get("name")[0];
				System.out.println("name VALUE IS: " + name);
			}
		} catch (Exception e) {
		}

		String startParam = "";
		try {
			if (parameterMap.get("startParam") != null) {
				startParam = parameterMap.get("startParam")[0];
				System.out.println("startParam: " + startParam);
			}
		} catch (Exception e) {
		}

		String orientation = "up";
		try {
			if (parameterMap.get("orientation") != null) {
				orientation = parameterMap.get("orientation")[0];
				System.out.println("orientation: " + orientation);
			}
		} catch (Exception e) {
		}
		int totResult = 3;
		try {
			if (parameterMap.get("totResult") != null) {
				totResult = Integer.parseInt(parameterMap.get("totResult")[0]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("numero risultati per pagina: " + totResult);

		try {
			xwconn = connectionManager.getConnection(workFlowBean.getArchive());
			result = xwconn.getSingleKeys(searchAlias, totResult, orientation, startParam);
		} catch (Exception e) {
			throw e;
		} finally {
			modelMap.put("resultVoc", result);
			// model.put("numeropaginacorrente", numeropagina);
			modelMap.put("orientation", orientation);
			modelMap.put("startParam", startParam);
			modelMap.put("totResult", totResult);
			modelMap.put("searchAlias", searchAlias);
			modelMap.put("name", name);
			connectionManager.closeConnection(xwconn);
		}
	}
}