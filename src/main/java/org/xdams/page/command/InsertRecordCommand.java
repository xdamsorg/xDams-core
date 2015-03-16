package org.xdams.page.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.ui.ModelMap;
import org.xdams.conf.master.ConfBean;
import org.xdams.manager.conf.MultiEditingManager;
import org.xdams.user.access.ServiceUser;
import org.xdams.user.bean.Archive;
import org.xdams.user.bean.UserBean;
import org.xdams.utility.DateUtil;
import org.xdams.utility.SharpIncrementTool;
import org.xdams.utility.request.MyRequest;
import org.xdams.workflow.bean.WorkFlowBean;
import org.xdams.xml.builder.XMLBuilder;
import org.xdams.xmlengine.connection.manager.ConnectionManager;
import org.xdams.xw.XWConnection;

public class InsertRecordCommand {

	private Map<String, String[]> parameterMap = null;

	private ModelMap modelMap = null;

	public InsertRecordCommand(Map<String, String[]> parameterMap, ModelMap modelMap) throws Exception {
		this.parameterMap = parameterMap;
		this.modelMap = modelMap;
	}

	public void execute() throws Exception {
		XWConnection xwconn = null;
		ConnectionManager connectionManager = new ConnectionManager();
		ConfBean confBean = null;
		String physDoc = MyRequest.getParameter("physDoc", parameterMap);
		String titleRule = "";
		List<String> confControl = new ArrayList<String>();
		confControl.add("docEdit");
		confControl.add("valoriControllati");
		try {
			UserBean userBean = (UserBean) modelMap.get("userBean");
			confBean = (ConfBean) modelMap.get("confBean");
			WorkFlowBean workFlowBean = (WorkFlowBean) modelMap.get("workFlowBean");
			xwconn = connectionManager.getConnection(workFlowBean.getArchive());
			XMLBuilder builderDoc = null;
			if (physDoc == null || physDoc.equals("") || physDoc.equals("-1")) {
				builderDoc = new XMLBuilder("root");
			} else {
				builderDoc = new XMLBuilder(xwconn.getSingleXMLFromNumDoc(Integer.parseInt(physDoc)), "ISO-8859-1");
			}

			MultiEditingManager editingManager = new MultiEditingManager(parameterMap, confBean, userBean, workFlowBean);
			editingManager.setTheXML(builderDoc);
			confBean = editingManager.rewriteMultipleConf(confControl);

			int newNrecord = 0;
			int relation = 0;
			int firstRecord = -1;
			if (!(MyRequest.getParameter("relation", parameterMap)).equals("")) {
				relation = Integer.parseInt(MyRequest.getParameter("relation", parameterMap));
			}

			// String theArch = myRequest.getParameter("db");
			String pne = workFlowBean.getArchive().getPne();

			if (!(MyRequest.getParameter("thePne", parameterMap)).equals("")) {
				pne = MyRequest.getParameter("thePne", parameterMap);
			}
//			System.out.println("InsertRecordCommand.execute() pne: " + pne);
			String dataCompilatoreCMPN = DateUtil.getDataSystem("dd/MM/yyyy");
			String nomeCompilatoreCMPN = userBean.getName() + " " + userBean.getLastName();

			int relatedPhysDoc = 0;
			int elementiMultipli = 0;

			if (MyRequest.getParameter("elementiMultipli", parameterMap).equals("")) {
				elementiMultipli = 1;
			} else {
				elementiMultipli = Integer.parseInt(MyRequest.getParameter("elementiMultipli", parameterMap));
			}
			if (!MyRequest.getParameter("relatedPhysDoc", parameterMap).equals("")) {
				relatedPhysDoc = Integer.parseInt(MyRequest.getParameter("relatedPhysDoc", parameterMap));
			}
			String[] nomiRequest = MyRequest.ordinaRequest(workFlowBean.getRequest(), "." + pne + ".");
			// HashMap laMappa = new HashMap();
			SharpIncrementTool sharpIncrementTool = new SharpIncrementTool();
			for (int cicli = 0; cicli < elementiMultipli; cicli++) {
				/* ordino la request e creo l'XML */
				XMLBuilder builder = new XMLBuilder(pne);
				for (int i = 0; i < nomiRequest.length; i++) {
					String ilNome = nomiRequest[i].replace('.', '/');
					String ilValore = (workFlowBean.getRequest().getParameter(nomiRequest[i])).trim();
					// System.out.println("ilNome=" + ilNome + " -- ilValore=" + ilValore + " ---");
					// System.out.println("elementiMultipli=" + elementiMultipli + " ---");
					// System.out.println("ilValore.indexOf(\"[#\") > 0 " + (ilValore.indexOf("[#") > 0));
					// System.out.println("ilValore.indexOf(\"#]\") > 0 " + (ilValore.indexOf("#]") > 0));
					// System.out.println("elementiMultipli > 1 " + (elementiMultipli > 1));
					if (!ilValore.equals("")) {
						try {
							if ((ilValore.indexOf("[#") != -1) && (ilValore.indexOf("#]") > 0) && (elementiMultipli > 0)) {
								ilValore = sharpIncrementTool.incrementValue(ilNome, ilValore);// ilValoreIni + contatoreMultiplo + ilValoreFin;
							}
						} catch (Exception eee) {

						} finally {
							if (ilNome.endsWith("/@cdata")) {
								ilNome = StringUtils.chomp(ilNome, "/@cdata");
								builder.insertNode(ilNome, ilValore, true);
							} else {
								if (ilNome.endsWith("/@crypted")) {
									ilNome = StringUtils.chomp(ilNome, "/@crypted");
									if (!ilValore.equals("")) {
										try {
											ilValore = new Md5PasswordEncoder().encodePassword(ilValore, null);
										} catch (Exception e) {

										}
									}
								}
								builder.insertNode(ilNome, ilValore); // QUI INSERISCO IL VERO VALORE
							}

						}
					}
				}

				/* fine ordino la request e creo l'XML */
				XMLBuilder theXMLconf = confBean.getTheXMLConfEditing();

				for (int a = 0; a < theXMLconf.contaNodi("/root/fixedValues/cdata/elemento"); a++) {
					String ilNodoCorrente = theXMLconf.valoreNodo("/root/fixedValues/cdata/elemento[" + (a + 1) + "]/@path");
					String ilValoreCorrente = theXMLconf.valoreNodo("/root/fixedValues/cdata/elemento[" + (a + 1) + "]/text()");
					ilValoreCorrente = ilValoreCorrente.replaceAll(" ", "_");
					ilValoreCorrente = ilValoreCorrente.replaceAll("<", "-");
					ilValoreCorrente = ilValoreCorrente.replaceAll(">", "-");
					if (ilNodoCorrente.endsWith("/@cdata")) {
						ilNodoCorrente = StringUtils.chomp(ilNodoCorrente, "/@cdata");
						builder.insertNode(ilNodoCorrente, ilValoreCorrente, true);
					} else {
						builder.insertNode(ilNodoCorrente, ilValoreCorrente); // QUI INSERISCO IL VERO VALORE
					}
					// builder.insertNode(ilNodoCorrente, ilValoreCorrente);
				}
				if (!(theXMLconf.valoreNodo("/root/fixedValues/text()")).equals("")) {
					builder.insertNode("/" + xwconn.getPne() + "/text()", theXMLconf.valoreNodo("/root/fixedValues/text()"));
				}

				titleRule = theXMLconf.valoreNodo("/root/param/elemento[@id='codice_identificativo' and @auto_increment='true']/@titleRule");
				// gestione generazione unitid
				if (!titleRule.equals("")) {
					String unitid = "";
					String newUnitid = "";
					String fatherUnitid = "";
					try {

						it.highwaytech.db.QueryResult qr = null;
						int docFather = 0;
						if ((MyRequest.getParameter("relation", parameterMap)).equals("1")) {
							docFather = Integer.parseInt(MyRequest.getParameter("relatedPhysDoc", parameterMap));
						}
						if ((MyRequest.getParameter("relation", parameterMap)).equals("2") || (MyRequest.getParameter("relation", parameterMap)).equals("4")) {
							docFather = Integer.parseInt(MyRequest.getParameter("relatedPhysDoc", parameterMap));
							docFather = xwconn.getNumDocFather(docFather);
						}
						qr = xwconn.findSons(xwconn.connection, xwconn.getTheDb(), docFather);
						xwconn.setTitleRule(xwconn.connection, xwconn.getTheDb(), titleRule);
						fatherUnitid = xwconn.getTitle(xwconn.connection, xwconn.getTheDb(), docFather).getTitle();

						if (qr.elements > 0) {
							int prossimoNumero = 0;
							int precedenteNumero = 0;

							if (true || MyRequest.getParameter("generaUnitidMode", parameterMap).equals("1")) {
								it.highwaytech.db.Title ilTitoloElemento = xwconn.getTitle(xwconn.connection, xwconn.getTheDb(), xwconn.getNumDocFromQRElement(qr, qr.elements - 1));
								unitid = ilTitoloElemento.getTitle();
								newUnitid = unitid.substring(unitid.lastIndexOf(".") + 1);
								prossimoNumero = qr.elements + 1;
							}
							/*
							 * if (myRequest.getParameter("generaUnitidMode").equals("2")) { for (int z = 0; z < qr.elements; z++) { it.highwaytech.db.Title ilTitoloElemento = xwconn.getTitle(xwconn.connection, xwconn.getTheDb(), xwconn.getNumDocFromQRElement(qr, z)); unitid =
							 * ilTitoloElemento.getTitle(); newUnitid = unitid.substring(unitid.lastIndexOf(".") + 1); prossimoNumero = Integer.parseInt(newUnitid); prossimoNumero++; if (prossimoNumero == (precedenteNumero + 1)) { precedenteNumero = prossimoNumero; if (z == qr.elements - 1) {
							 * prossimoNumero = qr.elements + 1; break; } else { prossimoNumero = qr.elements + 1; break; } } else { if (prossimoNumero > precedenteNumero) { prossimoNumero++; break; } else { prossimoNumero = qr.elements + 1; break; } } } }
							 */

							newUnitid = String.valueOf(prossimoNumero);
							for (int x = (String.valueOf(prossimoNumero)).length(); x < 5; x++) {
								newUnitid = "0" + newUnitid;
							}
							unitid = fatherUnitid + "." + newUnitid;
						} else {
							if (!fatherUnitid.equals("")) {
								unitid = fatherUnitid + ".00001";
							} else {
								unitid = "00001";
							}

						}

					} catch (Exception e) {
						xwconn.restoreTitleRole();
						e.printStackTrace();
					}

					try {
						if (!(theXMLconf.valoreNodo("/root/param/elemento[@id='codice_identificativo' and @auto_increment='true']/text()")).equals(""))
							builder.insertNode(theXMLconf.valoreNodo("/root/param/elemento[@id='codice_identificativo' and @auto_increment='true']/text()"), unitid);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				/*
				 * for (int a = 0; a < theXMLconf.contaNodi("//fixedValues/elemento"); a++) { String ilNodoCorrente = theXMLconf.valoreNodo("//fixedValues/elemento[" + (a + 1) + "]/text()"); String ilValoreCorrente = theXMLconf.valoreNodo("//fixedValues/elemento[" + (a + 1) + "]/@value");
				 * builder.insertNode(ilNodoCorrente, ilValoreCorrente); }
				 */

				String theXML = builder.getXML("ISO-8859-1");

				for (int a = 0; a < theXMLconf.contaNodi("/root/fixedValues/cdata/elemento"); a++) {
					String ilValoreCorrente = theXMLconf.valoreNodo("/root/fixedValues/cdata/elemento[" + (a + 1) + "]/text()");
					ilValoreCorrente = ilValoreCorrente.replaceAll(" ", "_");
					ilValoreCorrente = ilValoreCorrente.replaceAll("<", "-");
					ilValoreCorrente = ilValoreCorrente.replaceAll(">", "-");
					theXML = theXML.replaceAll(ilValoreCorrente, theXMLconf.valoreNodo("//fixedValues/cdata/elemento[" + (a + 1) + "]/text()"));
				}
				// gestione del nome del fondo
				theXML = theXML.replaceAll("@nomeFondo@", workFlowBean.getArchive().getArchiveDescr());
				// ArchiviBean archiviBean = userBean.getArchiviBeanFromAliasDb(MyRequest.getParameter("db_name_orig", parameterMap));
				// System.out.println("INSERT RECORD myRequest.getParameter(\"db_name_orig\") " + myRequest.getParameter("db_name_orig"));
				// System.out.println("INSERT RECORD archiviBean " + archiviBean);
				// System.out.println("INSERT RECORD archiviBean.getAliasDb() " + archiviBean.getAliasDb());
				// System.out.println("INSERT RECORD archiviBean.getDescrArchivio() " + archiviBean.getDescrArchivio());
				String archivioDaCuiInserisce = "";
				String archivioAbbreviatoDaCuiInserisce = "";

				if (!MyRequest.getParameter("srcArchive", parameterMap).equals("")) {
					Archive srcArchive = ServiceUser.getArchive(userBean, MyRequest.getParameter("srcArchive", parameterMap));
					archivioAbbreviatoDaCuiInserisce = srcArchive.getArchiveDescr();
					archivioDaCuiInserisce = srcArchive.getAlias();
				}

				// gestione di processinfo
				theXML = theXML.replaceAll("@coluiCheInserisce@", nomeCompilatoreCMPN);
				theXML = theXML.replaceAll("@quandoColuiInserisce@", dataCompilatoreCMPN);
				theXML = theXML.replaceAll("@archivioAbbreviatoDaCuiInserisce@", archivioAbbreviatoDaCuiInserisce);
				theXML = theXML.replaceAll("@archivioDaCuiInserisce@", archivioDaCuiInserisce);
				// inizio inserimento del record
				try {
//					System.out.println("insert " + theXML);
					newNrecord = xwconn.insert(theXML);
					if (firstRecord == -1) {
						firstRecord = newNrecord;
					}
					if (relatedPhysDoc > 0 && relation > 0) {

						if ((MyRequest.getParameter("relation", parameterMap)).equals("4")) {
							xwconn.docRelInsert(xwconn.connection, xwconn.getTheDb(), relation, relatedPhysDoc, newNrecord);
							relatedPhysDoc = newNrecord;
						} else {
							xwconn.docRelInsert(xwconn.connection, xwconn.getTheDb(), relation, relatedPhysDoc, newNrecord);
						}
					}

					/**/

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			modelMap.put("confBean", confBean);
			modelMap.put("physDoc", String.valueOf(firstRecord));
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.toString());
		} finally {
			if (!titleRule.equals("")) {
				try {
					xwconn.restoreTitleRule(xwconn.connection, xwconn.getTheDb());
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			connectionManager.closeConnection(xwconn);
		}
	}
}
