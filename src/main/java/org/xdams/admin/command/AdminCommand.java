package org.xdams.admin.command;

import it.highwaytech.broker.XMLCommand;
import it.highwaytech.db.QueryResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.ui.ModelMap;
import org.xdams.conf.master.ConfBean;
import org.xdams.manager.conf.MultiEditingManager;
import org.xdams.page.view.bean.ManagingBean;
import org.xdams.user.bean.UserBean;
import org.xdams.utility.CommonUtils;
import org.xdams.utility.ExtractDocument;
import org.xdams.utility.TrasformXslt20;
import org.xdams.utility.XMLCleaner;
import org.xdams.utility.request.MyRequest;
import org.xdams.utility.resource.ConfManager;
import org.xdams.workflow.bean.WorkFlowBean;
import org.xdams.xml.builder.XMLBuilder;
import org.xdams.xmlengine.connection.manager.ConnectionManager;
import org.xdams.xw.XWConnection;

public class AdminCommand {

	private Map<String, String[]> parameterMap = null;

	private ModelMap modelMap = null;

	public AdminCommand(Map<String, String[]> parameterMap, ModelMap modelMap) throws Exception {
		this.parameterMap = parameterMap;
		this.modelMap = modelMap;
	}

	public ManagingBean execute() throws Exception {
		XWConnection xwconn = null;
		ConnectionManager connectionManager = new ConnectionManager();
		ConfBean confBean = null;
		ManagingBean managingBean = null;
		String physDoc = MyRequest.getParameter("physDoc", parameterMap); // myRequest.getParameter("physDoc");
		String makeAction = MyRequest.getParameter("makeAction", parameterMap); // myRequest.getParameter("makeAction");
		String applyTo = MyRequest.getParameter("applyTo", parameterMap); // myRequest.getParameter("applyTo");
		String selid = MyRequest.getParameter("selid", parameterMap); // myRequest.getParameter("selid");
		String exportType = MyRequest.getParameter("exportType", parameterMap);
		String flagAudience = MyRequest.getParameter("flagAudience", parameterMap);

		HttpSession httpSession = null;
		List<String> confControl = new ArrayList<String>();
		String titleRole = "";
		confControl.add("titleManager");
		try {
			managingBean = new ManagingBean();
			UserBean userBean = (UserBean) modelMap.get("userBean");
			confBean = (ConfBean) modelMap.get("confBean");
			WorkFlowBean workFlowBean = (WorkFlowBean) modelMap.get("workFlowBean");
			httpSession = workFlowBean.getRequest().getSession(false);
			xwconn = connectionManager.getConnection(workFlowBean.getArchive());
			MultiEditingManager editingManager = new MultiEditingManager(parameterMap, confBean, userBean, workFlowBean);
			try {
				editingManager.setTheXML(new XMLBuilder(xwconn.getSingleXMLFromNumDoc(Integer.parseInt(physDoc)), "ISO-8859-1"));
			} catch (Exception e) {
				editingManager.setTheXML(new XMLBuilder("root"));
			}
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

			managingBean.setSelid(selid);
			if (!physDoc.equals("") && makeAction.equals("")) {
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
				managingBean.setDispatchView("exportMenu");
				if (httpSession.getAttribute(workFlowBean.getManagingBeanName()) != null) {
					ArrayList listDocs = ((ManagingBean) httpSession.getAttribute(workFlowBean.getManagingBeanName())).getListPhysDoc();
					if (listDocs != null && listDocs.size() > 0) {
						managingBean.setListPhysDoc(listDocs);
					}
				}

			} else if (!physDoc.equals("") && makeAction.equals("true")) { // DO
				managingBean.setPhysDoc(Integer.parseInt(physDoc));
				ArrayList elementiNum = ExtractDocument.extractDocument(httpSession, xwconn, managingBean, workFlowBean, applyTo, selid);
				managingBean.setNumElementi(elementiNum.size());
				managingBean.setTitle((xwconn.getTitle(xwconn.connection, xwconn.getTheDb(), Integer.parseInt(physDoc))).getTitle());
				managingBean.setDispatchView("exportResult");
				int totElementi = elementiNum.size();
				int processati = 0;
				int errori = 0;
				String exportCmd = "";
				System.out.println("exportType: " + exportType);
				System.out.println("applyTo: " + applyTo);
				if (exportType.equals("hier")) {
					exportCmd = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<cmd c=\"8\" bits=\"" + (XMLCommand.Export_Memory + XMLCommand.Export) + "\"  num2=\"" + managingBean.getPhysDoc() + "\" num=\"" + managingBean.getPhysDoc() + "\"></cmd>";

				} else if (exportType.equals("flat")) {
					it.highwaytech.db.QueryResult queryResult = null;
					if (applyTo.equals("selid")) {
						queryResult = xwconn.getQRFromSelId(selid);
					} else if (applyTo.equals("sons")) {
						queryResult = xwconn.getSonsFromNumDoc(managingBean.getPhysDoc());
					} else if (applyTo.equals("hier")) {
						queryResult = xwconn.getQRFromHier(managingBean.getPhysDoc(), false);
					} else if (applyTo.equals("prevSibling") || applyTo.equals("nextSibling")) {
						QueryResult queryResultColl = new QueryResult();
						for (Object object : elementiNum) {
							queryResult = xwconn.getQRfromPhrase("[NRECORD]=" + object);
							xwconn.addToQueryResult(queryResultColl, queryResult);
						}
						queryResult = queryResultColl;

					} else if (applyTo.toLowerCase().equals("xquery")) {
						String xwQu = MyRequest.getParameter("query", parameterMap);
						System.out.println("AdminCommand.execute() xwQu: "+xwQu);
						queryResult = xwconn.getQRfromPhrase(xwQu);
						System.out.println("AdminCommand.execute() queryResult: "+queryResult.elements);
					}
					exportCmd = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<cmd c=\"8\" bits=\"" + (XMLCommand.Export_Memory + XMLCommand.Export_Full) + "\" sel=\"" + queryResult.id + "\"></cmd>";
				}

				String result = xwconn.XMLCommand(xwconn.connection, workFlowBean.getAlias(), exportCmd);
				result = XMLCleaner.clearXwFullXML(result, true);
				// System.out.println(result);
				String realPath = (String) modelMap.get("realPath");
				System.out.println("realPath: " + realPath);
				System.out.println("flagAudience: " + flagAudience);

				if (flagAudience.equals("on")) {
					String xsltFiltra = ConfManager.getConfString("export-xsl/hierAudience.xsl");
					xsltFiltra = xsltFiltra.replaceAll("rootElement", workFlowBean.getArchive().getPne());
					result = TrasformXslt20.xslt(result, xsltFiltra);
				}

				String fileNameExport = "export" + "_" + CommonUtils.stripPunctuation(workFlowBean.getArchive().getArchiveDescr(), '-') + "_" + CommonUtils.stripPunctuation(SimpleDateFormat.getInstance().format(new Date()).replaceAll(" ", "_").replaceAll("/", "_").replaceAll("\\.", "_"), '-');
				System.out.println("fileNameExport: " + fileNameExport);
				try {
					FileUtils.writeStringToFile(new File(realPath + "export" + System.getProperty("file.separator") + fileNameExport + ".xml"), result, "ISO-8859-1");
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					OutputStream zip_output = new FileOutputStream(new File(realPath + "export" + System.getProperty("file.separator") + fileNameExport + ".zip"));
					ArchiveOutputStream logical_zip = new ArchiveStreamFactory().createArchiveOutputStream(ArchiveStreamFactory.ZIP, zip_output);
					logical_zip.putArchiveEntry(new ZipArchiveEntry(fileNameExport + ".xml"));
					IOUtils.copy(new FileInputStream(new File(realPath + "export" + System.getProperty("file.separator") + fileNameExport + ".xml")), logical_zip);
					logical_zip.closeArchiveEntry();
					logical_zip.finish();
					zip_output.close();
					modelMap.put("fileNameExport", fileNameExport + ".zip");
				} catch (Exception e) {
					modelMap.put("fileNameExport", fileNameExport + ".xml");
				}

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
