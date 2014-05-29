package org.xdams.managing.command;

import it.highwaytech.db.QueryResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.ModelMap;
import org.xdams.adv.configuration.Element;
import org.xdams.adv.utility.MappingAdv;
import org.xdams.conf.master.ConfBean;
import org.xdams.manager.conf.MultiEditingManager;
import org.xdams.managing.bean.BuildElementToFindBean;
import org.xdams.managing.bean.ElementToFindBean;
import org.xdams.managing.bean.ModifyAutherBean;
import org.xdams.page.view.bean.ManagingBean;
import org.xdams.user.bean.Archive;
import org.xdams.user.bean.UserBean;
import org.xdams.utility.XMLCleaner;
import org.xdams.utility.request.MyRequest;
import org.xdams.workflow.bean.WorkFlowBean;
import org.xdams.xml.builder.XMLBuilder;
import org.xdams.xmlengine.connection.manager.ConnectionManager;
import org.xdams.xw.XWConnection;
import org.xdams.adv.utility.GenericInterface;

public class ModifyAuther {

	private Map<String, String[]> parameterMap = null;

	private ModelMap modelMap = null;

	public ModifyAuther(Map<String, String[]> parameterMap, ModelMap modelMap) throws Exception {
		this.parameterMap = parameterMap;
		this.modelMap = modelMap;
	}

	public ManagingBean execute() throws Exception {

		String physDoc = MyRequest.getParameter("physDoc", parameterMap);
		String codeToFind = MyRequest.getParameter("codeToFind", parameterMap);
		String nameToFind = MyRequest.getParameter("nameToFind", parameterMap);
		String prefix = MyRequest.getParameter("prefix", parameterMap);
		String makeAction = MyRequest.getParameter("makeAction", parameterMap);
		String jspDispatchMenu = MyRequest.getParameter("jspDispatchMenu", "updateAutherMenu", parameterMap);
		String jspDispatchResult = MyRequest.getParameter("jspDispatchResult", "updateAutherResult", parameterMap);
		final String nameToChange = MyRequest.getParameter("nameToChange", parameterMap);

		ConnectionManager connectionManager = new ConnectionManager();
		BuildElementToFindBean buildElementToFindBean = new BuildElementToFindBean();
		ConfBean confBean = null;
		ModifyAutherBean modifyAutherBean = new ModifyAutherBean();
		WorkFlowBean workFlowBean = null;
		// questa lista può essere presa da un file di configurazione
		List<String> confControl = new ArrayList<String>();
		confControl.add("titleManager");
		confControl.add("docEdit");
		try {
			UserBean userBean = (UserBean) modelMap.get("userBean");
			confBean = (ConfBean) modelMap.get("confBean");
			workFlowBean = (WorkFlowBean) modelMap.get("workFlowBean");
			XWConnection xwconnone = connectionManager.getConnection(workFlowBean.getArchive());
			MultiEditingManager editingManager = new MultiEditingManager(parameterMap, confBean, userBean, workFlowBean);
			editingManager.setTheXML(new XMLBuilder(xwconnone.getSingleXMLFromNumDoc(Integer.parseInt(physDoc)), "ISO-8859-1"));
			confBean = editingManager.rewriteMultipleConf(confControl);
			connectionManager.closeConnection(xwconnone);
			XMLBuilder theXMLconf = confBean.getTheXMLConfEditing();
			if (!codeToFind.equals("") && makeAction.equals("")) {
				
				Map<String, List<ElementToFindBean>> hashDbToFind = buildElementToFindBean.buildElementToFindBean(theXMLconf, workFlowBean.getAlias());
				System.out.println("ModifyAuther.execute() (hashDbToFind) " + (hashDbToFind));
				int contaArchProc = 0;
				String archivioCorrente = "";
				QueryResult qr = null;
				for (Entry<String, List<ElementToFindBean>> entrySet : hashDbToFind.entrySet()) {
					String dbToFind = entrySet.getKey();
					List<ElementToFindBean> arrElmToFind = hashDbToFind.get(dbToFind);
					archivioCorrente = dbToFind;
					if(userBean.getArchivesMap().get(dbToFind)==null){
						continue;
					}
					Archive archiveBean = userBean.getArchivesMap().get(dbToFind);
					String descrArchive = archiveBean.getArchiveDescr();
					List<String> listCtrlModAuther = new ArrayList<String>();
					if (!archiveBean.getAlias().equals("")) {
						for (int a = 0; a < arrElmToFind.size(); a++) {
							ModifyAutherBean autherBean = new ModifyAutherBean();
							ElementToFindBean elementToFindBean = (ElementToFindBean) arrElmToFind.get(a);
							System.out.println("ModifyAuther.execute() elementToFindBean.getStrQuery()" + elementToFindBean.getStrQuery() + "AAAA");
							if (elementToFindBean.getStrQuery() != null && !elementToFindBean.getStrQuery().equals("null")) {
								String query = "(" + elementToFindBean.getStrQuery() + "=" + codeToFind + ")";
								System.out.println("query " + query + "<br>");
								System.out.println("archivioCorrente " + archivioCorrente + "<br>");
								XWConnection xwconn = null;
								try {
									xwconn = connectionManager.getConnection(archiveBean);
									qr = xwconn.getQRfromPhrase(query);
									System.out.println("ModifyAuther.execute() qr " + qr.elements);
								} catch (Exception e) {
									// throw new Exception("ERRORE NELLA CONNESSIONE CON IL DATABASE " + archiviBean.getTheArch());
								} finally {
									connectionManager.closeConnection(xwconn);
								}
								// System.out.println("<span style=\"background:#fff;\"><b>" + archivioCorrente + "</b>" + query + " occorrenze=" + qr.elements + "</span><br>");
								autherBean.setArchivioAlias(archivioCorrente);
								autherBean.setArchivioDescr(descrArchive);
								autherBean.setElementToFindBean(elementToFindBean);
								autherBean.setQuery(query);
								autherBean.setNumElementi(qr.elements);
								// escludere per ArchivioAlias e query
								if (!listCtrlModAuther.contains(archivioCorrente + query)) {
									modifyAutherBean.addArrModifyAutherBean(autherBean);
									listCtrlModAuther.add(archivioCorrente + query);
								}
							}
						}
					}
				}
				modifyAutherBean.setDispatchView(jspDispatchMenu);
			} else if (!codeToFind.equals("") && makeAction.equals("true")) {
				System.out.println("ModifyAuther.execute() - SONO IN MODIFICA AUTH 1");
				Map<String, List<ElementToFindBean>> hashDbToFind = buildElementToFindBean.buildElementToFindBean(theXMLconf, workFlowBean.getAlias());
				int modificatiTotali = 0;
				int erroriTotali = 0;
				int modificatiXPathToChange = 0;
				for (Entry<String, List<ElementToFindBean>> entrySet : hashDbToFind.entrySet()) {
					System.out.println("ModifyAuther.execute() - SONO IN MODIFICA AUTH 2");
					try {
						String dbToFind = entrySet.getKey();
						List<ElementToFindBean> arrElmToFind = hashDbToFind.get(dbToFind);
						// Enumeration elementi = myRequest.getHttpServletRequest().getParameterNames();
						boolean processArch = false;
						System.out.println("ModifyAuther.execute() - SONO IN MODIFICA AUTH 3");
						// while (elementi.hasMoreElements()) {
						for (Entry<String, String[]> entry : parameterMap.entrySet()) {
							String nome = entry.getKey();
							if (nome.indexOf("theArchiveToProcess_") != -1) {
								String tmpArch = MyRequest.getParameter(nome, parameterMap);
								if (tmpArch.indexOf(dbToFind) != -1) {
									// out.println(theArch+"vado a processare i record <br>");
									processArch = true;
									break;
								}
							}
						}

						System.out.println("ModifyAuther.execute() - SONO IN MODIFICA AUTH 3 processArch " + processArch);
						if (processArch) {
							if(userBean.getArchivesMap().get(dbToFind)==null){
								continue;
							}
							Archive archiveBean = userBean.getArchivesMap().get(dbToFind);
							System.out.println("ModifyAuther.execute() - SONO IN MODIFICA AUTH 4 archiviBean " + archiveBean);
							String descrArchive = archiveBean.getArchiveDescr();
							System.out.println("ModifyAuther.execute() - SONO IN MODIFICA AUTH 5 descrArchive " + descrArchive);

							for (int z = 0; z < arrElmToFind.size(); z++) {
								ElementToFindBean elementToFindBean = (ElementToFindBean) arrElmToFind.get(z);
								String strQuery = elementToFindBean.getStrQuery();
								String strPrefix = elementToFindBean.getStrPrefix();
								String strCode = elementToFindBean.getStrCode();
								String xPathToChange = elementToFindBean.getXPathToChange();
								String isTextNode = elementToFindBean.getIsTextNode();

								String query = "(" + strQuery + "=" + codeToFind + ")";
								System.out.println("ModifyAuther.execute() - SONO IN MODIFICA AUTH 6 strQuery " + strQuery);
								System.out.println("ModifyAuther.execute() - SONO IN MODIFICA AUTH su Archivio  " + archiveBean.getAlias());
								QueryResult qr = null;
								XWConnection xwconn = null;
								try {
									xwconn = connectionManager.getConnection(archiveBean);
									qr = xwconn.getQRfromPhrase(query);
									if (qr.elements > 0) {
										ModifyAutherBean autherBean = new ModifyAutherBean();
										for (int a = 0; a < qr.elements; a++) {
											boolean modificato = false;
											int numDoc = xwconn.getNumDocFromQRElement(qr, a);
											XMLBuilder builder = new XMLBuilder(xwconn.getSingleXMLFromNumDoc(numDoc), "ISO-8859-1");
											// out.println("strPrefix "+strPrefix+"<br>");
											if (elementToFindBean.isAdvEditing()) {
												MappingAdv mappingAdv = new MappingAdv();
												List<Element> list = mappingAdv.extractMapping(elementToFindBean.getConfigurationXMLReader().getObjects(), null, new HashMap<String, String>());
												ArrayList<Element> arrayList = new ArrayList<Element>(list);
												for (Element element : arrayList) {
													List<Element> arrayListA = new ArrayList<Element>();
													arrayListA.add(element);
													mappingAdv.buildXML(arrayListA, builder, null, null, new GenericInterface<XMLBuilder>() {
														public void invoke(XMLBuilder builder, Element element) {
															try {
																if (!nameToChange.equals(element.getFieldValue())) {
																	System.out.println(element.getFieldValue());
																}
															} catch (Exception e) {
																e.printStackTrace();
															}
														}
													});
												}
												System.out.println("###############################");
												System.out.println("###############################");
												System.out.println("###############(elementToFindBean.isAdvEditing()################");
												System.out.println("###############################");
												System.out.println("###############################");
												System.out.println("###############################");

											} else {
												int countPrefix = builder.contaNodi(strPrefix);
												for (int x = 0; x < countPrefix; x++) {
													String newXPathToChange = strPrefix + "[" + (x + 1) + "]" + xPathToChange.substring(xPathToChange.indexOf(strPrefix) + strPrefix.length());
													String newXpathCode = "";
													if (isTextNode.equals("true")) {
														newXpathCode = newXPathToChange.substring(0, newXPathToChange.lastIndexOf("/") + 1) + strCode;
													} else {
//														System.out.println("ModifyAuther.execute() CI SONO UN PREFIX strCode.indexOf(strPrefix)!=-1 " + strPrefix.contains(strCode));
//														System.out.println("ModifyAuther.execute() CI SONO UN PREFIX strPrefix " + strPrefix);
//														System.out.println("ModifyAuther.execute() CI SONO UN PREFIX strCode " + strCode);
														if (strCode.indexOf(strPrefix) != -1) {
//															System.out.println("ModifyAuther.execute() CI SONO UN PREFIX DIDIDIDIDI");
															newXpathCode = strPrefix + "[" + (x + 1) + "]" + StringUtils.difference(strPrefix, strCode);
//															System.out.println("ModifyAuther.execute() CI SONO UN PREFIX DIDIDIDIDI" + newXpathCode);
															// newXpathCode = newXPathToChange.substring(0, newXPathToChange.lastIndexOf("/") + 1) + "@" + strCode;
														} else {
															if (strCode.indexOf("@") != -1) {
																newXpathCode = newXPathToChange.substring(0, newXPathToChange.lastIndexOf("/") + 1) + strCode;
															} else {
																newXpathCode = newXPathToChange.substring(0, newXPathToChange.lastIndexOf("/") + 1) + "@" + strCode;
															}

														}

													}
													String ilValoreCode = builder.valoreNodo(newXpathCode);
													String ilValoreText = builder.valoreNodo(newXPathToChange);
													if (ilValoreCode.indexOf(codeToFind) != -1 && !ilValoreText.equals(nameToChange)) {
														modificato = true;
														builder.insertValueAt(newXPathToChange, nameToChange);
														modificatiXPathToChange++;
													}
												}
												if (modificato) {
													// String rootElement = (builder.getDomDocument().getDocumentElement()).getNodeName();
													// // out.println("rootElement "+rootElement+"<br>");
													// if (rootElement.equals("c")) {
													// int numProcessInfo = builder.contaNodi("/" + rootElement + "/processinfo/list/item");
													// numProcessInfo++;
													// builder.insertValueAt("/" + rootElement + "/processinfo/list/item[" + numProcessInfo + "]/persname/text()", myRequest.getParameter(".persona"));
													// builder.insertValueAt("/" + rootElement + "/processinfo/list/item[" + numProcessInfo + "]/text()", myRequest.getParameter(".action"));
													// builder.insertValueAt("/" + rootElement + "/processinfo/list/item[" + numProcessInfo + "]/date/text()", myRequest.getParameter(".data"));
													// } else {
													// int numProcessInfo = builder.contaNodi("/" + rootElement + "/eacheader/mainhistory/mainevent");
													// numProcessInfo++;
													// builder.insertValueAt("/" + rootElement + "/eacheader/mainhistory/mainevent[" + numProcessInfo + "]/name/text()", myRequest.getParameter(".persona"));
													// builder.insertValueAt("/" + rootElement + "/eacheader/mainhistory/mainevent[" + numProcessInfo + "]/@maintype", myRequest.getParameter(".action"));
													// builder.insertValueAt("/" + rootElement + "/eacheader/mainhistory/mainevent[" + numProcessInfo + "]/maindate/text()", myRequest.getParameter(".data"));
													// }

													modificatiTotali++;
													String docXML = builder.getXML("ISO-8859-1");
													docXML = XMLCleaner.clearXwXML(docXML, true);
													try {
														xwconn.executeUpdateByDocNumber(docXML, numDoc);
													} catch (Exception e) {
														erroriTotali++;
														modifyAutherBean.addErrorMsg("Errore in proietta authority per il documento numero " + numDoc + " in  " + descrArchive + " " + e.getMessage());
														e.printStackTrace();
													}
												}
											}

										}
										autherBean.setArchivioAlias(archiveBean.getAlias());
										autherBean.setArchivioDescr(descrArchive);
										elementToFindBean.setNumTotXPathToChange(modificatiXPathToChange);
										autherBean.setElementToFindBean(elementToFindBean);
										autherBean.setQuery(query);
										autherBean.setNumElementi(qr.elements);
										modifyAutherBean.addArrModifyAutherBean(autherBean);
										modificatiXPathToChange = 0;
									}
								} catch (Exception e) {
									modifyAutherBean.addErrorMsg("Errore in proietta authority " + e.getMessage());
								} finally {
									connectionManager.closeConnection(xwconn);
								}
							}
						}

					} catch (Exception e) {
						throw new Exception(e.toString());
					}
				}
				modifyAutherBean.setDocSuccessi(modificatiTotali);
				modifyAutherBean.setDocErrori(erroriTotali);

				modifyAutherBean.setDispatchView(jspDispatchResult);

			}
			modelMap.put("confBean", confBean);
			modelMap.put("modifyAutherBean", modifyAutherBean);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.toString());
		}

		return modifyAutherBean;
	}
}
