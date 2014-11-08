package org.xdams.ajax.command;

import it.highwaytech.db.HierPath;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.ui.ModelMap;
import org.xdams.ajax.bean.AjaxBean;
import org.xdams.conf.master.ConfBean;
import org.xdams.manager.conf.MultiEditingManager;
import org.xdams.page.view.bean.ManagingBean;
import org.xdams.user.bean.UserBean;
import org.xdams.utility.TitleManager;
import org.xdams.utility.XMLCopy;
import org.xdams.utility.request.MyRequest;
import org.xdams.workflow.bean.WorkFlowBean;
import org.xdams.xml.builder.XMLBuilder;
import org.xdams.xmlengine.connection.manager.ConnectionManager;
import org.xdams.xw.XWConnection;


public class AjaxCutPasteCopyCommand {

	private HttpServletRequest req = null;

	private HttpServletResponse res = null;

	private ModelMap modelMap = null;

	public AjaxCutPasteCopyCommand(HttpServletRequest req, HttpServletResponse res, ModelMap modelMap) throws FileNotFoundException, IOException {
		this.req = req;
		this.res = res;
		this.modelMap = modelMap;
	}

	// @SuppressWarnings("unchecked")
	public AjaxBean execute() throws Exception {
		AjaxBean ajaxBean = new AjaxBean();
		HttpSession httpSession = null;
		String actionType = MyRequest.getParameter("action", req.getParameterMap());
		String physDoc = MyRequest.getParameter("physDoc", req.getParameterMap());
		String physDocToPaste = MyRequest.getParameter("physDocToPaste", req.getParameterMap());
		ManagingBean managingBean = null;
		String valori = "";
		ConnectionManager connectionManager = new ConnectionManager();
		XWConnection xwconn = null;
		ConfBean confBean = null;
		WorkFlowBean workFlowBean = null;
		List<String> confControl = new ArrayList<String>();
		confControl.add("titleManager");
		confControl.add("docEdit");

		try {
			UserBean userBean = (UserBean) modelMap.get("userBean");
			confBean = (ConfBean) modelMap.get("confBean");
			workFlowBean = (WorkFlowBean) modelMap.get("workFlowBean");
			httpSession = workFlowBean.getRequest().getSession(false);
			if (httpSession.getAttribute(workFlowBean.getManagingBeanName()) == null) {
				managingBean = new ManagingBean();
				managingBean.setMultipleChoise("none");
			} else {
				managingBean = (ManagingBean) httpSession.getAttribute(workFlowBean.getManagingBeanName());
			}

			xwconn = connectionManager.getConnection(workFlowBean.getArchive());
			MultiEditingManager editingManager = new MultiEditingManager(req.getParameterMap(), confBean, userBean, workFlowBean);
			editingManager.setTheXML(new XMLBuilder(xwconn.getSingleXMLFromNumDoc(Integer.parseInt(physDoc)), "ISO-8859-1"));
			confBean = editingManager.rewriteMultipleConf(confControl);

			if (actionType.equals("cut") || actionType.equals("paste") || actionType.equals("no_rel") || actionType.equals("only_hier")) {
				TitleManager titleManager = new TitleManager(confBean.getTheXMLConfTitle());
			//	System.out.println("AjaxCutPasteCopyCommand.execute() getTheXMLConfTitle " + confBean.getTheXMLConfTitle().getXML("ISO-8859-1"));
				XMLBuilder builder = confBean.getTheXMLConfTitle();
				String titleRole = builder.valoreNodo("/root/titleManager/sezione[@name='title']/titleRole/text()", false);
				System.out.println("QueryParserCommand.execute()" + titleRole);
				try {
					if (!titleRole.trim().equals("")) {
						xwconn.setTitleRole(titleRole);
					}
				} catch (Exception e) {
					System.out.println(" ---- ERROR ---- QueryParserCommand (xwconn.setTitleRole(titleRole)), title to parse: " + titleRole);
					xwconn.restoreTitleRole();
				}				
				try {
					managingBean.setCutPhysDoc(Integer.parseInt(physDoc));
					String titoloDoc = xwconn.getTitleFromNumDoc(Integer.parseInt(physDoc));
					System.out.println("AjaxCutPasteCopyCommand.execute() titoloDoc " + titoloDoc);
					managingBean.setCutTitle(titoloDoc);
				} catch (Exception e) {
					e.printStackTrace();
				}
				String azioneDescr = "";
				if (actionType.equals("cut")) {
					azioneDescr = "tagliato";
				} else if (actionType.equals("paste")) {
					azioneDescr = "copiato";
				} else if (actionType.equals("no_rel")) {
					azioneDescr = "copiato";
				} else if (actionType.equals("only_hier")) {
					azioneDescr = "copiato";
				}

				String contenitoreIni = "\n<div class=\"cont_ul2\"><ul class=\"bottoniMenu\">";
				String string0 = "";
				String string1 = "";
				String string2 = "";
				if (!actionType.equals("no_rel")) {
					string0 = "<li><a href=\"#\" target=\"\" onclick=\"ajaxSetSessionDocsToCut('" + managingBean.getCutPhysDoc() + "','@@physDocToPaste@@','" + actionType + "_as_son','" + workFlowBean.getAlias() + "');\" >INC.FIGLIO</a></li>";
					string1 = "<li><a href=\"#\" target=\"\" onclick=\"ajaxSetSessionDocsToCut('" + managingBean.getCutPhysDoc() + "','@@physDocToPaste@@','" + actionType + "_as_before','" + workFlowBean.getAlias() + "');\" >INC.PRIMA</a></li>";
					string2 = "<li><a href=\"#\" target=\"\" onclick=\"ajaxSetSessionDocsToCut('" + managingBean.getCutPhysDoc() + "','@@physDocToPaste@@','" + actionType + "_as_after','" + workFlowBean.getAlias() + "');\" >INC.DOPO</a></li>";
				}

				String string3 = "";
				// if(actionType.equals("paste") || actionType.equals("no_rel")){
				if (actionType.equals("no_rel")) {
					actionType = "paste";
					string3 = "<li><a href=\"#\" target=\"\" onclick=\"ajaxSetSessionDocsToCut('" + managingBean.getCutPhysDoc() + "','@@physDocToPaste@@','" + actionType + "_as_norel','" + workFlowBean.getAlias() + "');\" >NO REL</a></li>";
				}

				String string4 = "<li><a href=\"#\" target=\"\" onclick=\"ajaxSetSessionDocsToCut('" + managingBean.getCutPhysDoc() + "','@@physDocToPaste@@','" + actionType + "_delete','" + workFlowBean.getAlias() + "');\" >ANNULLA</a></li>";
				String contenitoreEnd = "</ul></div><div class=\"cutPasteClazz\">elemento " + azioneDescr + " " + (titleManager.defaultParsedTitle(managingBean.getCutTitle(), "hierTitle")) + "</div>";
				String string = contenitoreIni + string0 + string1 + string2 + string3 + string4 + contenitoreEnd;
				ajaxBean.setStrXmlOutput(string);
				managingBean.setCutHtmlOutput(string);
				// managingBean.setCutHtmlOutput(managingBean.getCutHtmlOutput(String.valueOf(managingBean.getCutPhysDoc())));
			}

			boolean cutTest = true;
			boolean pasteTest = true;
			try {
				if (actionType.startsWith("cut_as")) {
					HierPath hierPath = xwconn.getHierPath(Integer.parseInt(physDocToPaste));
					int numeroLivelli = hierPath.depth();
					for (int i = 1; i < numeroLivelli; i++) {
						int theFatherTemp = hierPath.docNumber(i);
						if (theFatherTemp == Integer.parseInt(physDoc) || Integer.parseInt(physDoc) == Integer.parseInt(physDocToPaste)) {
							cutTest = false;
							break;
						}
					}
				}
			} catch (Exception e) {
				cutTest = false;
			}

			if (!cutTest) {
				ajaxBean.setStrXmlOutput("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<error>" + StringEscapeUtils.escapeXml("Attenzione:\nNon e possibile effettuare l'operazione richiesta") + "</error>");
			}

			if (actionType.equals("cut_as_son")) {
				// elemento da tagliare physDoc
				if (cutTest) {
					xwconn.cut_paste(Integer.parseInt(physDoc), Integer.parseInt(physDocToPaste), it.highwaytech.broker.ServerCommand.navigarel_FIGLIOPADRE);
					ajaxBean.setStrXmlOutput("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<succes>reloadLocation()</succes>");
				}
				// elemento in cui spostare il documento tagliato physDocToPaste
			} else if (actionType.equals("cut_as_before")) {
				// elemento da tagliare physDoc
				if (cutTest) {
					xwconn.cut_paste(Integer.parseInt(physDoc), Integer.parseInt(physDocToPaste), it.highwaytech.broker.ServerCommand.navigarel_MINOREMAGGIORE);
					ajaxBean.setStrXmlOutput("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<succes>reloadLocation()</succes>");
				}
				// elemento in cui spostare il documento tagliato physDocToPaste
			} else if (actionType.equals("cut_as_after")) {
				// elemento da tagliare physDoc
				if (cutTest) {
					xwconn.cut_paste(Integer.parseInt(physDoc), Integer.parseInt(physDocToPaste), it.highwaytech.broker.ServerCommand.navigarel_MAGGIOREMINORE);
					ajaxBean.setStrXmlOutput("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<succes>reloadLocation()</succes>");
				}
				// elemento in cui spostare il documento tagliato physDocToPaste
			} else if (actionType.equals("cut_delete")) {
				managingBean.setCutTitle("");
				managingBean.setCutHtmlOutput("<div id=\"scriviQui\"></div>");
				ajaxBean.setStrXmlOutput("<div id=\"scriviQui\"></div>");
				managingBean.setCutPhysDoc(-1);
			} else if (actionType.equals("paste_as_son")) {
				// elemento da tagliare physDoc
				String xmlDoc = xwconn.getSingleXMLFromNumDoc(Integer.parseInt(physDoc));
				String newXML = XMLCopy.xmlCopy(confBean.getTheXMLConfEditing(), new XMLBuilder(xmlDoc, "ISO-8859-1"), userBean);
				int recordNum = xwconn.insert(newXML);
				int toPaste = -1;
				try {
					toPaste = Integer.parseInt(physDocToPaste);
				} catch (Exception e) {
					toPaste = Integer.parseInt(physDoc);
				}
				System.out.println("AjaxCutPasteCopyCommand.execute() " + newXML);
				int esitoAzione = xwconn.docRelInsert(xwconn.connection, xwconn.getTheDb(), it.highwaytech.broker.ServerCommand.navigarel_FIGLIOPADRE, recordNum, toPaste);
				ajaxBean.setStrXmlOutput("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<succes>reloadLocation()</succes>");
				// elemento in cui spostare il documento tagliato physDocToPaste
			} else if (actionType.equals("paste_as_before")) {
				// elemento da tagliare physDoc
				String xmlDoc = xwconn.getSingleXMLFromNumDoc(Integer.parseInt(physDoc));
				String newXML = XMLCopy.xmlCopy(confBean.getTheXMLConfEditing(), new XMLBuilder(xmlDoc, "ISO-8859-1"), userBean);
				int recordNum = xwconn.insert(newXML);
				int toPaste = -1;
				try {
					toPaste = Integer.parseInt(physDocToPaste);
				} catch (Exception e) {
					toPaste = Integer.parseInt(physDoc);
				}
				int esitoAzione = xwconn.docRelInsert(xwconn.connection, xwconn.getTheDb(), it.highwaytech.broker.ServerCommand.navigarel_MINOREMAGGIORE, recordNum, toPaste);
				ajaxBean.setStrXmlOutput("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<succes>reloadLocation()</succes>");
				// elemento in cui spostare il documento tagliato physDocToPaste
			} else if (actionType.equals("paste_as_after")) {
				// elemento da tagliare physDoc
				String xmlDoc = xwconn.getSingleXMLFromNumDoc(Integer.parseInt(physDoc));
				String newXML = XMLCopy.xmlCopy(confBean.getTheXMLConfEditing(), new XMLBuilder(xmlDoc, "ISO-8859-1"), userBean);
				int recordNum = xwconn.insert(newXML);
				int toPaste = -1;
				try {
					toPaste = Integer.parseInt(physDocToPaste);
				} catch (Exception e) {
					toPaste = Integer.parseInt(physDoc);
				}
				System.out.println("AjaxCutPasteCopyCommand.execute() " + newXML);
				int esitoAzione = xwconn.docRelInsert(xwconn.connection, xwconn.getTheDb(), it.highwaytech.broker.ServerCommand.navigarel_MAGGIOREMINORE, recordNum, toPaste);
				ajaxBean.setStrXmlOutput("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<succes>reloadLocation()</succes>");
				// elemento in cui spostare il documento tagliato physDocToPaste
			} else if (actionType.equals("paste_as_norel")) {
				// elemento da tagliare physDoc
				String xmlDoc = xwconn.getSingleXMLFromNumDoc(Integer.parseInt(physDoc));
				String newXML = XMLCopy.xmlCopy(confBean.getTheXMLConfEditing(), new XMLBuilder(xmlDoc, "ISO-8859-1"), userBean);
				int recordNum = xwconn.insert(newXML);
				/*
				 * int toPaste = -1; try { toPaste = Integer.parseInt(physDocToPaste); } catch (Exception e) { toPaste = Integer.parseInt(physDoc); } System.out.println("AjaxCutPasteCopyCommand.execute() " + newXML); int esitoAzione = xwconn.docRelInsert(xwconn.connection, xwconn.getTheDb(),
				 * it.highwaytech.broker.ServerCommand.navigarel_MAGGIOREMINORE, recordNum, toPaste);
				 */
				ajaxBean.setStrXmlOutput("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<succes>reloadLocation()</succes>");
				// elemento in cui spostare il documento tagliato physDocToPaste
			} else if (actionType.equals("paste_delete")) {
				managingBean.setCutTitle("");
				managingBean.setCutHtmlOutput("<div id=\"scriviQui\"></div>");
				ajaxBean.setStrXmlOutput("<div id=\"scriviQui\"></div>");
				managingBean.setCutPhysDoc(-1);
			}

		} catch (Exception e) {
			e.printStackTrace();
			ajaxBean.setStrXmlOutput("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<error>" + StringEscapeUtils.escapeXml("Attenzione:\nimpossibile effettuare l'operazione, il documento selezionato potrebbe non essere più in gerarchia") + "\n" + e.getMessage() + "</error>");
		} finally {
			httpSession.setAttribute(workFlowBean.getManagingBeanName(), managingBean);
			xwconn.restoreTitleRole();
			connectionManager.closeConnection(xwconn);
		}

		// ajaxBean.setStrXmlOutput("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<?xml-stylesheet type=\"text/xsl\" href=\"http://localhost:8080//xdams-front/REGEXE/xslt/prova.xslt\"?>\n" + "<sessionList>\n" + valori + "</sessionList>");
		return ajaxBean;
	}
}
