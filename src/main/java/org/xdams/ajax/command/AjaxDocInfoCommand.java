package org.xdams.ajax.command;

import it.highwaytech.db.QueryResult;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.xdams.ajax.bean.AjaxBean;
import org.xdams.conf.master.ConfBean;
import org.xdams.manager.conf.MultiEditingManager;
import org.xdams.user.bean.UserBean;
import org.xdams.utility.TitleManager;
import org.xdams.utility.request.MyRequest;
import org.xdams.workflow.bean.WorkFlowBean;
import org.xdams.xml.builder.XMLBuilder;
import org.xdams.xmlengine.connection.manager.ConnectionManager;
import org.xdams.xw.XWConnection;
import org.xdams.xw.exception.XWException;
import org.xdams.xw.utility.Key;

public class AjaxDocInfoCommand {
	private HttpServletRequest req = null;

	private HttpServletResponse res = null;

	private ModelMap modelMap = null;

	public AjaxDocInfoCommand(HttpServletRequest req, HttpServletResponse res, ModelMap modelMap) throws FileNotFoundException, IOException {
		this.req = req;
		this.res = res;
		this.modelMap = modelMap;
	}

	public AjaxBean execute() throws Exception {
		XWConnection xwconn = null;
		ConnectionManager connectionManager = new ConnectionManager();
		ConfBean confBean = null;
		WorkFlowBean workFlowBean = null;
		AjaxBean ajaxBean = new AjaxBean();
		String valori = "";
		String titleRole = "";
		List<String> confControl = new ArrayList<String>();
		confControl.add("titleManager");
		confControl.add("query");
		try {
			String physDoc = MyRequest.getParameter("numDoc", req.getParameterMap());
			UserBean userBean = (UserBean) modelMap.get("userBean");
			confBean = (ConfBean) modelMap.get("confBean");
			workFlowBean = (WorkFlowBean) modelMap.get("workFlowBean");

			xwconn = connectionManager.getConnection(workFlowBean.getArchive());
			MultiEditingManager editingManager = new MultiEditingManager(req.getParameterMap(), confBean, userBean, workFlowBean);
			editingManager.setTheXML(new XMLBuilder(xwconn.getSingleXMLFromNumDoc(Integer.parseInt(physDoc)), "ISO-8859-1"));
			confBean = editingManager.rewriteMultipleConf(confControl);
			
			XMLBuilder builder = confBean.getTheXMLConfTitle();
			titleRole = builder.valoreNodo("/root/titleManager/sezione[@name='title']/titleRole/text()", false);
 			try {
				if (!titleRole.trim().equals("")) {
					xwconn.setTitleRole(titleRole);
				}
			} catch (Exception e) {
				System.out.println(" ---- ERROR ---- QueryParserCommand (xwconn.setTitleRole(titleRole)), title to parse: " + titleRole);
				xwconn.restoreTitleRole();
			}
			
			
			if (MyRequest.getParameter("calcButtons", req.getParameterMap()).equals("true")) {
				calcolaBottoni(xwconn, ajaxBean, physDoc);
				return ajaxBean;
			}
			QueryResult qr = xwconn.getQRFromHier(Integer.parseInt(physDoc), false);
			String extraQuery = confBean.getTheXMLConfQuery().valoreNodoNoEscape("/root/infoDoc/element[@type='extraQuery']/text()");
			// System.out.println("AjaxDocInfoCommand.execute() extraQuery " + extraQuery);
			if (!extraQuery.equals("")) {
				try {
					// System.out.println("AjaxDocInfoCommand.execute() extraQuery " + "([?SEL]=\"" + qr.id + "\") " + extraQuery);
					qr = xwconn.getQRfromPhrase("([?SEL]=\"" + qr.id + "\") " + extraQuery);
				} catch (Exception e) {
					System.out.println("ERRORE IN AjaxDocInfoCommand extraQuery " + "([?SEL]=\"" + qr.id + "\") " + extraQuery);
					System.out.println("ERRORE IN AjaxDocInfoCommand error " + e.getMessage());
				}

			}

			// ELEMENTI INFERIORI COLLEGATI
			if (qr.elements > 0) { /* se ha dei figli */
				valori += "<li>Elementi inferiori collegati: <strong>" + qr.elements + "</strong></li>";
				// QueryResult queryResult = xwconn.getQRFromHier(Integer.parseInt(physDoc),false);
				// // ELEMENTI INFERIORI COLLEGATI
				// if (queryResult.elements > 0) { /* se ha dei figli */
				// valori += "<li>Elementi figli di primo livello: <strong>" + queryResult.elements + "</strong></li>";
				// }
			}

			int numDocFather = xwconn.getNumDocFather(Integer.parseInt(physDoc));

			if (numDocFather > 0) {
				// POSIZIONE NEL RAMO
				int numDocSon = xwconn.getNumDocFirstSon(numDocFather);
				int count = 0;
				while (numDocSon != Integer.parseInt(physDoc)) {
					++count;
					numDocSon = xwconn.getNumDocNextBrother(numDocSon);
				}
				valori += "<li>Posizione all'interno del ramo: <strong>" + (count + 1) + "</strong></li>";

				// LIVELLO DI PROFONDITA
				int depthLevel = 1;

				while ((numDocFather = xwconn.getNumDocFather(numDocFather)) > 0) {
					++depthLevel;
				}

				valori += "<li>Livello di profondit&agrave;: <strong>" + depthLevel + "</strong></li>";
			}

			if (qr.elements > 0) { /*
									 * se ha dei figli CALCOLO ESTREMI CRONOLOGICI
									 */
				try {
					TitleManager titleManager = new TitleManager(confBean.getTheXMLConfTitle());
					String normalDatePath = confBean.getTheXMLConfQuery().valoreNodoNoEscape("/root/infoDoc/element[@type='normalDatePath']/text()");
					if (normalDatePath.equals("")) {
						normalDatePath = "/c/did/unittitle/unitdate";
					}
					String laSel = qr.id;
					String ilMaggiore = "";
					String ilMinore = "";
					String laFrase = "([?SEL]=\"" + laSel + "\") and not ([XML," + normalDatePath + "]=\"s.d.\")";
					String ordinamento = "";
					qr = xwconn.selectQR(laFrase);
					laSel = qr.id;
					java.util.Vector chiaviFrom = xwconn.selectFilteredKey(qr, "XML," + normalDatePath + "/#from", qr.elements, "down", "0");
					java.util.Vector chiaviTo = xwconn.selectFilteredKey(qr, "XML," + normalDatePath + "/#to", qr.elements, "up", "0");
					// System.out.println("chiaviFromchiaviFromchiaviFromchiaviFrom
					// "+chiaviFrom);
					// System.out.println("chiaviTochiaviTochiaviTochiaviTochiaviTo
					// "+chiaviTo);
					for (int i = 0; i < chiaviFrom.size(); i++) {
						Key key = (Key) chiaviFrom.elementAt(i);
						ilMinore = key.key.toString();
						if (!ilMinore.equals("00000000")) {
							break;
						}
					}

					for (int i = 0; i < chiaviTo.size(); i++) {
						Key key = (Key) chiaviTo.elementAt(i);
						ilMaggiore = key.key.toString();
					}

					laFrase = "([?SEL]=\"" + laSel + "\") and ([XML," + normalDatePath + "/#from]=\"" + ilMinore + "\")";
					ordinamento = "XML(xpart:" + normalDatePath + "/@normal)";
					qr = xwconn.selectQR(laFrase, ordinamento, it.highwaytech.broker.ServerCommand.find_SORT, -1);

					for (int i = 0; i < qr.elements; i++) {
						it.highwaytech.db.Title ilTitolo = xwconn.getTitle(xwconn.connection, xwconn.getTheDb(), qr, i);
						String ilTitoloString = ilTitolo.getTitle();
						String laData = titleManager.defaultParsedTitle(ilTitoloString, "dataTitle");
						if (!(laData.trim()).equals("")) {
							ilMinore = titleManager.defaultParsedTitle(ilTitoloString, "defaultTitle");
							break;
						}
					}

					laFrase = "([?SEL]=\"" + laSel + "\") and ([XML," + normalDatePath + "/#to]=\"" + ilMaggiore + "\")";
					ordinamento = "xml(xpart:" + normalDatePath + "/@normal)";
					qr = xwconn.selectQR(laFrase, ordinamento, it.highwaytech.broker.ServerCommand.find_SORT, -1);

					it.highwaytech.db.Title ilTitolo = xwconn.getTitle(xwconn.connection, xwconn.getTheDb(), qr, qr.elements - 1);
					String ilTitoloString = ilTitolo.getTitle();

					String laData = titleManager.defaultParsedTitle(ilTitoloString, "dataTitle");
					if (!(laData.trim()).equals("")) {
						ilMaggiore = titleManager.defaultParsedTitle(ilTitoloString, "defaultTitle");
					}

					if (!ilMinore.trim().equals("")) {
						valori += "<li>Elemento collegato con data minore: <strong>" + ilMinore + "</strong></li>";
					}
					if (!ilMaggiore.trim().equals("")) {
						valori += "<li>Elemento collegato con data maggiore: <strong>" + ilMaggiore + "</strong></li>";
					}

				} catch (Exception e) {
					// e.printStackTrace();
				}
			}
			ajaxBean.setStrXmlOutput("<ul>" + valori + "</ul>");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ECCOLO");
			ajaxBean.setStrXmlOutput("<ul><li>attenzione e avvenuto un errore (" + e.getMessage() + ")</li></ul>");
		} finally {
			xwconn.restoreTitleRole();
			connectionManager.closeConnection(xwconn);
		}

		return ajaxBean;
	}

	private void calcolaBottoni(XWConnection xwconn, AjaxBean ajaxBean, String physDoc) throws NumberFormatException, SQLException, XWException {
		String returnValue = "";
		String father = "";
		String firstSon = "";
		String lastSon = "";
		WorkFlowBean workFlowBean = (WorkFlowBean) modelMap.get("workFlowBean");
		String contextPath = (String) req.getContextPath();

		int numDocFather = xwconn.getNumDocFather(Integer.parseInt(physDoc));
		father = String.valueOf(numDocFather);
		if (numDocFather > 0) {
			int numDocSon = Integer.parseInt(physDoc);
			int ls = numDocSon;
			while ((numDocSon = xwconn.getNumDocNextBrother(numDocSon)) > 0) {
				ls = numDocSon;
			}

			firstSon = String.valueOf(xwconn.getNumDocFirstSon(numDocFather));
			if (firstSon.equals(physDoc))
				firstSon = "";

			lastSon = String.valueOf(ls);
			if (lastSon.equals(physDoc)) {
				lastSon = "";
			}



			String href = contextPath + "/hier/" + workFlowBean.getAlias() + "/hierBrowser.html?docToggle=" + father + "&docStart=" + father;
			returnValue += "<li>";
			returnValue += "<a href=\"" + href + "\" target=\"_top\">Padre</a>";
			returnValue += "</li>";

			if (firstSon.length() > 0) {
				href =  contextPath + "/hier/" + workFlowBean.getAlias() + "/hierBrowser.html?docToggle=" + firstSon + "&docStart=" + firstSon + "&go=hierBrowser.jsp";
				returnValue += "<li>";
				returnValue += "<a href=\"" + href + "\" target=\"_top\">Primo fratello</a>";
				returnValue += "</li>";
			}

			if (lastSon.length() > 0) {
				href = contextPath + "/hier/" + workFlowBean.getAlias() + "/hierBrowser.html?docToggle=" + lastSon + "&docStart=" + lastSon + "&go=hierBrowser.jsp";
				returnValue += "<li>";
				returnValue += "<a href=\"" + href + "\" target=\"_top\">Ultimo fratello</a>";
				returnValue += "</li>";
			}
		}

		QueryResult qr = xwconn.getQRFromHier(Integer.parseInt(physDoc), false);

		if (qr.elements > 1) {
			int ultimoFiglio = xwconn.getNumDocFromQRElement(qr, qr.elements - 1);
			if (xwconn.getNumDocFather(ultimoFiglio) != Integer.parseInt(physDoc)) {
				qr = xwconn.getSonsFromNumDoc(Integer.parseInt(physDoc));
				ultimoFiglio = xwconn.getNumDocFromQRElement(qr, qr.elements - 1);
			}

			String href = contextPath + "/hier/" + workFlowBean.getAlias() + "/hierBrowser.html?docToggle=" + ultimoFiglio + "&docStart=" + ultimoFiglio + "&go=hierBrowser.jsp";
			returnValue += "<li>";
			returnValue += "<a href=\"" + href + "\" target=\"_top\">Ultimo figlio</a>";
			returnValue += "</li>";

		}

		ajaxBean.setStrXmlOutput("<ul>" + returnValue + "</ul>");
	}
}
