package org.xdams.managing.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;

import static org.xdams.utility.text.findreplace.Options.CASE_INSENSITIVE;
import static org.xdams.utility.text.findreplace.Options.WHOLE_WORD;

import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.ModelMap;
import org.xdams.adv.configuration.ConfigurationXMLReader;
import org.xdams.adv.configuration.Element;
import org.xdams.adv.utility.GenericInterface;
import org.xdams.adv.utility.MappingAdv;
import org.xdams.conf.master.ConfBean;
import org.xdams.manager.conf.MultiEditingManager;
import org.xdams.page.view.bean.ManagingBean;
import org.xdams.user.bean.UserBean;
import org.xdams.utility.ExtractDocument;
import org.xdams.utility.XMLCopy;
import org.xdams.utility.request.MyRequest;
import org.xdams.utility.text.findreplace.Replacer;
import org.xdams.workflow.bean.WorkFlowBean;
import org.xdams.xml.builder.XMLBuilder;
import org.xdams.xmlengine.connection.manager.ConnectionManager;
import org.xdams.xw.XWConnection;

public class AdvFindReplace {
	private Map<String, String[]> parameterMap = null;

	private ModelMap modelMap = null;

	public AdvFindReplace(Map<String, String[]> parameterMap, ModelMap modelMap) throws Exception {
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
		String action = MyRequest.getParameter("action", parameterMap);
		String applyTo = MyRequest.getParameter("applyTo", parameterMap);
		String selid = MyRequest.getParameter("selid", parameterMap);

		HttpSession httpSession = null;
		List<String> confControl = new ArrayList<String>();
		confControl.add("titleManager");
		confControl.add("docEdit");
		confControl.add("valoriControllati");
		confControl.add("managing");

		try {
			UserBean userBean = (UserBean) modelMap.get("userBean");
			confBean = (ConfBean) modelMap.get("confBean");
			WorkFlowBean workFlowBean = (WorkFlowBean) modelMap.get("workFlowBean");
			httpSession = workFlowBean.getRequest().getSession(false);
			xwconn = connectionManager.getConnection(workFlowBean.getArchive());
			MultiEditingManager editingManager = new MultiEditingManager(parameterMap, confBean, userBean, workFlowBean);
			editingManager.setTheXML(new XMLBuilder(xwconn.getSingleXMLFromNumDoc(Integer.parseInt(physDoc)), "ISO-8859-1"));
			confBean = editingManager.rewriteMultipleConf(confControl);
			if (httpSession.getAttribute(workFlowBean.getManagingBeanName()) != null) {
				managingBean = ((ManagingBean) httpSession.getAttribute(workFlowBean.getManagingBeanName()));
			} else {
				managingBean = new ManagingBean();
			}
			managingBean.setSelid(selid);

			if (!physDoc.equals("") && makeAction.equals("") && action.equals("")) {

				managingBean.setPhysDoc(Integer.parseInt(physDoc));

				managingBean.setDocLowerBrother(xwconn.getNumDocNextBrother(managingBean.getPhysDoc()));
				managingBean.setDocUpperBrother(xwconn.getNumDocPreviousBrother(managingBean.getPhysDoc()));

				it.highwaytech.db.QueryResult qrSons = xwconn.getSonsFromNumDoc(managingBean.getPhysDoc());
				managingBean.setNumElementiSons(qrSons.elements);

				it.highwaytech.db.QueryResult qrHier = xwconn.getQRFromHier(managingBean.getPhysDoc(), false);
				managingBean.setNumElementiHier(qrHier.elements);

				if (!selid.equals("")) {
					it.highwaytech.db.QueryResult qr = xwconn.getQRFromSelId(selid);
					managingBean.setNumElementi(qr.elements);
				}

				managingBean.setTitle((xwconn.getTitle(xwconn.connection, xwconn.getTheDb(), managingBean.getPhysDoc())).getTitle());
				managingBean.setDocXML(xwconn.getSingleXMLFromNumDoc(managingBean.getPhysDoc()));

				if (httpSession.getAttribute(workFlowBean.getManagingBeanName()) != null) {
					ArrayList listDocs = ((ManagingBean) httpSession.getAttribute(workFlowBean.getManagingBeanName())).getListPhysDoc();
					if (listDocs != null && listDocs.size() > 0) {
						managingBean.setListPhysDoc(listDocs);
					}
				}

				managingBean.setDispatchView("advFindReplaceMenu");
			} else if (!physDoc.equals("") && makeAction.equals("true")) { // DO
				managingBean.setPhysDoc(Integer.parseInt(physDoc));
				ArrayList elementiNum = ExtractDocument.extractDocument(httpSession, xwconn, managingBean, workFlowBean, applyTo, selid);
				managingBean.setNumElementi(elementiNum.size());
				managingBean.setTitle((xwconn.getTitle(xwconn.connection, xwconn.getTheDb(), Integer.parseInt(physDoc))).getTitle());
				managingBean.setDispatchView("advFindReplaceResult");
				int totElementi = elementiNum.size();
				int errori = 0;
				final ArrayList<String> processati = new ArrayList<String>();
				ConfigurationXMLReader configurationXMLReader = new ConfigurationXMLReader(confBean.getTheXMLConfManaging());
				MappingAdv mappingAdv = new MappingAdv();
				for (int i = 0; i < totElementi; i++) {
					int docCorrente = 0;
					try {
						docCorrente = ((Integer) elementiNum.get(i)).intValue();
					} catch (Exception e) {
						docCorrente = (Integer.parseInt((String) elementiNum.get(i)));
					}
					try {
						String docXML = xwconn.getSingleXMLFromNumDoc(docCorrente);
						XMLBuilder xmlBuilder = new XMLBuilder(docXML, "ISO-8859-1");
						String findWhat = MyRequest.getParameter("findWhat", parameterMap);
						System.out.println(".execute() findWhat " + findWhat);
						final String replaceWith = MyRequest.getParameter("replaceWith", parameterMap);
						System.out.println(".execute() replaceWith " + replaceWith);
						final String caseSensitive = MyRequest.getParameter("caseSensitive", parameterMap);
						System.out.println(".execute() caseSensitive " + caseSensitive);
						final String whole_word = MyRequest.getParameter("whole_word", parameterMap);
						System.out.println(".execute() whole_word " + whole_word);
						String regexStr = MyRequest.getParameter("regexStr", parameterMap);
						final Replacer replacer = new Replacer(findWhat);
						if (("on").equals(whole_word)) {
							replacer.addOptions(WHOLE_WORD);
						}
						if (caseSensitive == null) {
							replacer.addOptions(CASE_INSENSITIVE);
						}
						replacer.applyConfiguration();
						for (Entry<String, String[]> entry : parameterMap.entrySet()) {
							String keyValue = entry.getKey();
							if (keyValue.equals("theDeep")) {
								for (final String string : entry.getValue()) {
									ArrayList<Element> arrayList = new ArrayList<Element>();
									Element element = configurationXMLReader.getElementFromDeep(string);
									arrayList.add(element);
									mappingAdv.buildXML(arrayList, xmlBuilder, null, null, new GenericInterface<XMLBuilder>() {
										public void invoke(XMLBuilder builder, Element element) {
											try {
												if (string.equals(element.getDeep()) && (!(element.getFieldValue().trim()).equals(""))) {
													String finalValue = "";
													replacer.setText(StringUtils.defaultIfEmpty(element.getFieldValue(), ""));
													finalValue = replacer.replaceAll(replaceWith).toString();
													if ((!(element.getFieldValue().trim()).equals("")) && !finalValue.equals(element.getFieldValue())) {
														processati.add("");
														if ((element.getCdata()) != null && element.getCdata().equals("true")) {
															builder.insertValueAt(element.getFieldXPath(), finalValue, true);
														} else {
															builder.insertValueAt(element.getFieldXPath(), finalValue);
														}
													}
												}
											} catch (Exception e) {
												e.printStackTrace();
											}
										}
									});
								}
							}
						}
						xwconn.executeUpdateByDocNumber(xmlBuilder.getXML("ISO-8859-1", false), docCorrente);

					} catch (Exception e) {
						managingBean.addErrorMsg(e.getMessage());
						errori++;
					}
				}
				managingBean.setDocSuccessi(processati.size());
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
			connectionManager.closeConnection(xwconn);
		}
		return managingBean;
	}
}
