package org.xdams.page.command;

import it.highwaytech.db.HierPath;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.ui.ModelMap;
import org.xdams.conf.master.ConfBean;
import org.xdams.manager.conf.MultiEditingManager;
import org.xdams.page.view.bean.PreInsertBean;
import org.xdams.user.bean.UserBean;
import org.xdams.utility.request.MyRequest;
import org.xdams.workflow.bean.WorkFlowBean;
import org.xdams.xml.builder.XMLBuilder;
import org.xdams.xmlengine.connection.manager.ConnectionManager;
import org.xdams.xw.XWConnection;


public class PreInsertPageCommand {

	private Map<String, String[]> parameterMap = null;

	private ModelMap modelMap = null;

	public PreInsertPageCommand(Map<String, String[]> parameterMap, ModelMap modelMap) throws Exception {
		this.parameterMap = parameterMap;
		this.modelMap = modelMap;
	}

	public void execute() throws Exception {
		XWConnection xwconn = null;
		ConnectionManager connectionManager = new ConnectionManager();
		ConfBean confBean = null;
		PreInsertBean preInsertBean = null;
		List<String> confControl = new ArrayList<String>();
		confControl.add("docEdit");
		confControl.add("valoriControllati");
		confControl.add("titleManager");
		String physDoc = MyRequest.getParameter("physDoc", parameterMap);
		try {
			UserBean userBean = (UserBean) modelMap.get("userBean");
			confBean = (ConfBean) modelMap.get("confBean");
			WorkFlowBean workFlowBean = (WorkFlowBean) modelMap.get("workFlowBean");
			preInsertBean = new PreInsertBean();
			// serve quando sono in preinsert dalla lookup
			if(modelMap.containsAttribute("srcArchive")){
				preInsertBean.setSrcArchive((String)modelMap.get("srcArchive"));
			}
			xwconn = connectionManager.getConnection(workFlowBean.getArchive());
			// Mi carico il file selezionato per l'inserimento se il physDoc e diverso da null
			if (!physDoc.equals("")) {
				// System.out.println("PreInsertPageCommand.execute() INIZIO CARICAMENTO DOCUMENTO SELEZIONATO");
				preInsertBean.setPhysDocSelected(Integer.parseInt(physDoc));
				String docXML = xwconn.getSingleXMLFromNumDoc(preInsertBean.getPhysDocSelected());
				preInsertBean.setDocXmlSelected(docXML);
				HierPath hierPath = xwconn.getHierPath(Integer.parseInt(physDoc));
				XMLBuilder theXMLDoc = new XMLBuilder(preInsertBean.getDocXmlSelected(), "ISO-8859-1");
				preInsertBean.setXmlBuilderSelected(theXMLDoc);
				preInsertBean.setDepthSelected(hierPath.depth());
				preInsertBean.setTitleSelected((xwconn.getTitle(xwconn.connection, xwconn.getTheDb(), Integer.parseInt(physDoc))).getTitle());
				MultiEditingManager editingManager = new MultiEditingManager(parameterMap, confBean, userBean, workFlowBean);
				editingManager.setTheXML(theXMLDoc);
				confBean = editingManager.rewriteMultipleConf(confControl);
				// System.out.println("PreInsertPageCommand.execute() FINE CARICAMENTO DOCUMENTO SELEZIONATO");
				// prendo il documento padre ma controllo che non sono il padre stesso...
				String ilPath = "/hierValues";
				preInsertBean.setXPathHierValues(ilPath);
				// System.out.println("aaaaaaaaaa " + "/" + ilPath + "/macroarea");
				int numeroMacroarea = (confBean.getTheXMLConfEditing()).contaNodi("/" + ilPath + "/macroarea");
				// System.out.println("numeroMacroarea " + numeroMacroarea);
				if (numeroMacroarea > 0) {
					int iLivelli = Integer.parseInt((confBean.getTheXMLConfEditing()).valoreNodo("/" + ilPath + "/@level"));
					int docFather = Integer.parseInt(physDoc);
					if (iLivelli > 0) {
						for (int i = 0; i < iLivelli; i++) {
							if (xwconn.getNumDocFather(docFather) > 0) {
								docFather = xwconn.getNumDocFather(docFather);
							}
						}
					}
					// System.out.println("PreInsertPageCommand.execute() INIZIO CARICAMENTO DOCUMENTO PADRE");
					preInsertBean.setPhysDocFather(docFather);
					docXML = xwconn.getSingleXMLFromNumDoc(preInsertBean.getPhysDocFather());
					preInsertBean.setDocXmlFather(docXML);
					theXMLDoc = new XMLBuilder(preInsertBean.getDocXmlFather(), "ISO-8859-1");
					preInsertBean.setXmlBuilderFather(theXMLDoc);
					preInsertBean.setDepthFather(hierPath.depth() - 1);
					preInsertBean.setTitleFather((xwconn.getTitle(xwconn.connection, xwconn.getTheDb(), preInsertBean.getPhysDocFather())).getTitle());
					// System.out.println("PreInsertPageCommand.execute() FINE CARICAMENTO DOCUMENTO PADRE");

				}

				// prendo il documento root dell'archivio quello con detph==1
				if ((hierPath.depth()) - (hierPath.depth() - 1) == 1) {
					// System.out.println("PreInsertPageCommand.execute() INIZIO CARICAMENTO DOCUMENTO ROOT");
					preInsertBean.setPhysDocRoot(hierPath.docNumber(1));
					docXML = xwconn.getSingleXMLFromNumDoc(preInsertBean.getPhysDocRoot());
					preInsertBean.setDocXmlRoot(docXML);
					theXMLDoc = new XMLBuilder(preInsertBean.getDocXmlRoot(), "ISO-8859-1");
					preInsertBean.setXmlBuilderRoot(theXMLDoc);
					preInsertBean.setDepthRoot(1);
					preInsertBean.setTitleRoot((xwconn.getTitle(xwconn.connection, xwconn.getTheDb(), preInsertBean.getPhysDocRoot())).getTitle());
					// System.out.println("PreInsertPageCommand.execute() FINE CARICAMENTO DOCUMENTO ROOT");
				}

			} else {
				// in caso di nuovo documento senza il phisDoc, come ad esempio un inserimento da lookup
				MultiEditingManager editingManager = new MultiEditingManager(parameterMap, confBean, userBean, workFlowBean);
				XMLBuilder builder = new XMLBuilder("root");
				editingManager.setTheXML(builder);
				confBean = editingManager.rewriteMultipleConf(confControl);
				preInsertBean.setXmlBuilderSelected(builder);
				preInsertBean.setXmlBuilderFather(builder);
			}
			// TODO DA VALUTARE introdotto per adv-editing
			preInsertBean.setXmlBuilderEmpty(new XMLBuilder(workFlowBean.getAlias()));

			modelMap.put("confBean", confBean);
			modelMap.put("preInsertBean", preInsertBean);

		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("confBean", confBean);
			modelMap.put("preInsertBean", preInsertBean);
			throw new Exception(e.toString());
		} finally {
			connectionManager.closeConnection(xwconn);
		}
	}
}
