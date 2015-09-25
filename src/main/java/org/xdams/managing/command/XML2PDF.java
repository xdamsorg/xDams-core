package org.xdams.managing.command;

import it.highwaytech.broker.XMLCommand;
import it.highwaytech.db.QueryResult;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.apache.fop.fonts.FontInfo;
import org.springframework.ui.ModelMap;
import org.xdams.conf.master.ConfBean;
import org.xdams.manager.conf.MultiEditingManager;
import org.xdams.page.view.bean.ManagingBean;
import org.xdams.user.bean.UserBean;
import org.xdams.utility.TrasformXslt20;
import org.xdams.utility.XSLLoader;
import org.xdams.utility.request.MyRequest;
import org.xdams.workflow.bean.WorkFlowBean;
import org.xdams.xml.builder.ProblematicCharactersFilter;
import org.xdams.xml.builder.XMLBuilder;
import org.xdams.xmlengine.connection.manager.ConnectionManager;
import org.xdams.xw.XWConnection;
import org.xdams.xw.exception.XWException;

public class XML2PDF {

	// private HttpServletRequest aReq = null;
	//
	// private ServletContext servletContext = null;
	//
	// private HttpServletResponse aRes = null;

	private String XSLDir = "";

	// public XML2PDF(HttpServletRequest req, HttpServletResponse res, ServletContext servletContext) {
	// this.aReq = req;
	// this.servletContext = servletContext;
	// this.aRes = res;
	// }

	private Map<String, String[]> parameterMap = null;

	private ModelMap modelMap = null;

	public XML2PDF(Map<String, String[]> parameterMap, ModelMap modelMap) throws Exception {
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
		String applyTo = MyRequest.getParameter("applyTo", parameterMap);
		String selid = MyRequest.getParameter("selid", parameterMap);
		String formattedText = MyRequest.getParameter("formattedText", parameterMap);
		HttpSession httpSession = null;
		String PDFType = ""; // 4 SELEZIONE
		// 1 UN DOCUMENTO
		// 2 FIGLI DI PRIMO LIVELLO
		// 3 TUTTA LA GERARCHIA
		List<String> confControl = new ArrayList<String>();
		confControl.add("titleManager");
		confControl.add("upload");
		confControl.add("media");

		String PDFXSLType = MyRequest.getParameter("PDFXSLType", parameterMap); // NOME FILE XSL
		InputStream xsltInputStream = null;
		InputStream xmlInputStream = null;
		try {
			// ManagingBean esiste in sessione ma io uso uno nuovo per le funzioni di gestione
			managingBean = new ManagingBean();
			UserBean userBean = (UserBean) modelMap.get("userBean");
			confBean = (ConfBean) modelMap.get("confBean");
			WorkFlowBean workFlowBean = (WorkFlowBean) modelMap.get("workFlowBean");
			httpSession = workFlowBean.getRequest().getSession(false);
			xwconn = connectionManager.getConnection(workFlowBean.getArchive());
			XMLBuilder theXMLDoc = null;
			if (!applyTo.equals("fromQuery")) {
				managingBean.setPhysDoc(Integer.parseInt(physDoc));
				String docXML = xwconn.getSingleXMLFromNumDoc(managingBean.getPhysDoc());
				managingBean.setDocXML(docXML);
				theXMLDoc = new XMLBuilder(managingBean.getDocXML(), "ISO-8859-1");
			} else {
				theXMLDoc = new XMLBuilder("root");
			}
			MultiEditingManager editingManager = new MultiEditingManager(parameterMap, confBean, userBean, workFlowBean);
			editingManager.setTheXML(theXMLDoc);
			confBean = editingManager.rewriteMultipleConf(confControl);
			XSLDir = findXSLPath(userBean, workFlowBean);
			if (XSLDir != null) {
				modelMap.put("XSLBeanArrayList", XSLLoader.loadXSL(XSLDir));
			} else {
				throw new Exception("Impostare pdfPrint.");
			}
			managingBean.setSelid(selid);
			// System.out.println("aaaaaaaaaaaaaaaaa 04");
			if (!physDoc.equals("") && makeAction.equals("")) {
				managingBean.setDocLowerBrother(xwconn.getNumDocNextBrother(managingBean.getPhysDoc()));
				managingBean.setDocUpperBrother(xwconn.getNumDocPreviousBrother(managingBean.getPhysDoc()));
				it.highwaytech.db.QueryResult qrHier = xwconn.getQRFromHier(managingBean.getPhysDoc(), true);
				managingBean.setNumElementiHier(qrHier.elements);
				it.highwaytech.db.QueryResult qrSons = xwconn.getSonsFromNumDoc(managingBean.getPhysDoc());
				managingBean.setNumElementiSons(qrSons.elements);
				if (!selid.equals("")) {
					it.highwaytech.db.QueryResult qr = xwconn.getQRFromSelId(selid);
					managingBean.setNumElementi(qr.elements);
				}
				managingBean.setTitle((xwconn.getTitle(xwconn.connection, xwconn.getTheDb(), managingBean.getPhysDoc())).getTitle());
				managingBean.setDispatchView("pdfMenu");
				if (httpSession.getAttribute(workFlowBean.getManagingBeanName()) != null) {
					ArrayList listDocs = ((ManagingBean) httpSession.getAttribute(workFlowBean.getManagingBeanName())).getListPhysDoc();
					if (listDocs != null && listDocs.size() > 0) {
						managingBean.setListPhysDoc(listDocs);
					}
				}
				// System.out.println("aaaaaaaaaaaaaaaaa 05");
			} else if (!physDoc.equals("") && makeAction.equals("true")) { // DO
				String PDFPhrase = null;
				String PDFNdoc = null;
				ArrayList elementiNum = new ArrayList();
				if (applyTo.equals("selected") || applyTo.equals("prevSibling") || applyTo.equals("nextSibling") || applyTo.equals("this")) {
					if (applyTo.equals("nextSibling")) {
						int theBrother = managingBean.getPhysDoc();
						while (theBrother > 0) {
							theBrother = xwconn.getNumDocNextBrother(theBrother);
							if (theBrother > 0) {
								elementiNum.add(new Integer(theBrother));
							}
						}
					} else if (applyTo.equals("prevSibling")) {
						int theBrother = managingBean.getPhysDoc();
						while (theBrother > 0) {
							theBrother = xwconn.getNumDocPreviousBrother(theBrother);
							if (theBrother > 0) {
								elementiNum.add(new Integer(theBrother));
							}
						}
					} else if (applyTo.equals("this")) {
						elementiNum.add(new Integer(physDoc));
					} else {// DA SELEZIONE MULTIPLA
						elementiNum = ((ManagingBean) httpSession.getAttribute(workFlowBean.getManagingBeanName())).getListPhysDoc();
					}

					// System.out.println("aaaaaaaaaaaaaaaaa 07");
					if (elementiNum.size() > 0) {
						String idXpath = MyRequest.getParameter("idXpath", parameterMap);// "/c/@id";
						if (idXpath == null) {
							throw new Exception("Impostare idXpath");
						}
						xwconn.setTitleRole("XML," + idXpath);
						QueryResult qrTempTo = new QueryResult();
						for (int a = 0; a < elementiNum.size(); a++) {
							try {
								int docCorrente = 0;
								try {
									docCorrente = ((Integer) elementiNum.get(a)).intValue();
								} catch (Exception e) {
									docCorrente = (Integer.parseInt((String) elementiNum.get(a)));
								}
								String idValue = ((xwconn.getTitle(xwconn.connection, xwconn.getTheDb(), docCorrente)).getTitle()).trim();
								if (!idValue.equals("")) {
									QueryResult qrTempFrom = xwconn.getQRfromPhrase("[XML," + idXpath + "]=\"" + idValue + "\"");
									// System.out.println("xdams - [INFO]  :::::::: phrase " + "[XML," + idXpath + "]=\"" + idValue + "\"");
									// System.out.println("qrTempFrom.elements: " + qrTempFrom.elements);
									if (qrTempFrom.elements > 0) {
										xwconn.addToQueryResult(qrTempTo, qrTempFrom);
									}

								}

							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						xwconn.restoreTitleRole();
						PDFType = "4";
						if (qrTempTo != null && qrTempTo.elements > 0)
							PDFPhrase = qrTempTo.id;
					}

				} else {
					// System.out.println("aaaaaaaaaaaaaaaaa 08");
					if (applyTo.equals("selid")) {
						PDFPhrase = (xwconn.getQRFromSelId(selid)).id;
						PDFType = "4";
					} else if (applyTo.equals("hier")) {
						PDFType = "3";
						PDFNdoc = MyRequest.getParameter("physDoc", parameterMap);
					} else if (applyTo.equals("single")) {
						PDFType = "1";
						PDFNdoc = MyRequest.getParameter("physDoc", parameterMap);
					} else if (applyTo.equals("sons")) {
						PDFType = "2";
						PDFNdoc = MyRequest.getParameter("physDoc", parameterMap);
					} else if (applyTo.equals("fromQuery")) {
						if (MyRequest.getParameter("fromQuery", parameterMap).trim().equals("")) {
							throw new Exception("Impostare fromQuery");
						}
						PDFPhrase = (xwconn.getQRfromPhrase(MyRequest.getParameter("fromQuery", parameterMap), MyRequest.getParameter("sortQuery", parameterMap))).id;
						PDFType = "4";
					}
				}

				// System.out.println("aaaaaaaaaaaaaaaaa 09");
				managingBean.setExportXML(executePDFPrint(workFlowBean, xwconn, PDFPhrase, PDFNdoc, PDFType, PDFXSLType));

				String headerPDF = MyRequest.getParameter("headerPDF", parameterMap).trim();
				String footerPDF = MyRequest.getParameter("footerPDF", parameterMap).trim();
				String subtitlePDF = MyRequest.getParameter("subtitlePDF", parameterMap).trim();
				if (!headerPDF.equals("") || !footerPDF.equals("") || !subtitlePDF.equals("")) {
					try {
						StringBuffer stringBuffer = new StringBuffer(managingBean.getExportXML());
						String stringInsert = "<pdfPrint>" + "<header></header>" + "<footer></footer>" + "<subtitle></subtitle>" + "</pdfPrint>";
						stringInsert = StringUtils.replace(stringInsert, "<header></header>", "<header>" + headerPDF + "</header>");
						stringInsert = StringUtils.replace(stringInsert, "<footer></footer>", "<footer>" + footerPDF + "</footer>");
						stringInsert = StringUtils.replace(stringInsert, "<subtitle></subtitle>", "<subtitle>" + subtitlePDF + "</subtitle>");
						// questo indexOf fa cagare
						stringBuffer.insert(managingBean.getExportXML().indexOf("\">") + 2, stringInsert);
						managingBean.setExportXML(stringBuffer.toString());
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
				// System.out.println("aaaaaaaaaaaaaaaaa 10 "+managingBean.getExportXML());
				ManagingBean sessionMenaging = (ManagingBean) httpSession.getAttribute(workFlowBean.getManagingBeanName());
				System.out.println("aaaaaaaaaaaaaaaaa 11");
				// sessionMenaging.setListPhysDoc(new ArrayList()); // SVUOTO GLI ELEMENTI SELEZIONATI
				if (managingBean.getExportXML() != null && !(managingBean.getExportXML()).equals("")) { // STAMPO PDF
					// InputStream
					xsltInputStream = new FileInputStream(new File((XSLDir) + "/" + MyRequest.getParameter("PDFXSLType", parameterMap)));
					// InputStream

					String XMLorigin = managingBean.getExportXML();
					if (formattedText.equals("true")) {
						// XMLorigin = StringUtils.replace(XMLorigin,"<![CDATA[","");
						XMLorigin = XMLorigin.replaceAll("<!\\[CDATA\\[", "");
						XMLorigin = XMLorigin.replaceAll("\\]\\]>", "");
						XMLorigin = XMLorigin.replaceAll("&lt;em&gt;", "<em>");
						XMLorigin = XMLorigin.replaceAll("&lt;/em&gt;", "</em>");
						XMLorigin = XMLorigin.replaceAll("&lt;br /&gt;", "<br />");
						XMLorigin = XMLorigin.replaceAll("&lt;strong&gt;", "<strong>");
						XMLorigin = XMLorigin.replaceAll("&lt;/strong&gt;", "</strong>");
						XMLorigin = XMLorigin.replaceAll("&", "&amp;");
						XMLorigin = XMLorigin.replaceAll("&amp;amp;", "&amp;");
						XMLorigin = XMLorigin.replaceAll("&amp;#", "&#");
					}

					xmlInputStream = new ByteArrayInputStream(XMLorigin.getBytes("iso-8859-1"));

					String mimeConstant = MimeConstants.MIME_PDF;
					String contentType = "pdf";
					String extension = "pdf";

					if (MyRequest.getParameter("PrintType", parameterMap).equals("") || MyRequest.getParameter("PrintType", parameterMap).equals("pdf")) {
						mimeConstant = MimeConstants.MIME_PDF;
						contentType = "application/pdf";
						extension = "pdf";
					} else if (MyRequest.getParameter("PrintType", parameterMap).equals("rtf")) {
						mimeConstant = MimeConstants.MIME_RTF;
						contentType = "application/rtf";
						extension = "rtf";
					} else if (MyRequest.getParameter("PrintType", parameterMap).equals("jpg")) {
						mimeConstant = MimeConstants.MIME_JPEG;
						contentType = "image/jpeg";
						extension = "jpg";
					} else if (MyRequest.getParameter("PrintType", parameterMap).equals("eps")) {
						mimeConstant = MimeConstants.MIME_EPS;
						contentType = "application/postscript";
						extension = "eps";
					} else if (MyRequest.getParameter("PrintType", parameterMap).equals("tif")) {
						mimeConstant = MimeConstants.MIME_TIFF;
						contentType = "image/tiff";
						extension = "tif";
					} else if (MyRequest.getParameter("PrintType", parameterMap).equals("svg")) {
						mimeConstant = MimeConstants.MIME_SVG;
						contentType = "image/svg+xml";
						extension = "svg";
					} else if (MyRequest.getParameter("PrintType", parameterMap).equals("csv")) {
						mimeConstant = MimeConstants.MIME_PLAIN_TEXT;
						contentType = "text/csv";
						extension = "csv";
					} else if (MyRequest.getParameter("PrintType", parameterMap).equals("html")) {
						mimeConstant = MimeConstants.MIME_PLAIN_TEXT;
						contentType = "text/html";
						extension = "html";
					}
					// Systemem.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbb 02");
					// Systemem.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbb "+xmlInputStream);
					// Systemem.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbb "+xsltInputStream);
					// Systemem.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbb "+contentType);
					// Systemem.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbb "+mimeConstant);
					// Systemem.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbb 02");
					managingBean.setDispatchView(null);
					convert(xmlInputStream, xsltInputStream, workFlowBean, contentType, mimeConstant, extension);
				}
			}

			modelMap.put("managingBean", managingBean);
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("managingBean", null);
			throw new Exception(e.toString());
		} finally {
			if (xsltInputStream != null) {
				xsltInputStream.close();
			}
			if (xmlInputStream != null) {
				xmlInputStream.close();
			}

			connectionManager.closeConnection(xwconn);
		}
		return managingBean;
	}

	@SuppressWarnings("unchecked")
	private String findXSLPath(UserBean userBean, WorkFlowBean workFlowBean) {
		String[] extensions = { "xsl", "xslt" };
		String pathPdfPrint = (workFlowBean.getRequest().getRealPath("/WEB-INF/classes/pdfPrint/" + userBean.getAccountRef() + "/" + workFlowBean.getArchive().getType() + "/" + workFlowBean.getArchive().getAlias())).replaceAll("\\\\", "/");
		System.out.println("XML2PDF.findXSLPath() 111 : " + pathPdfPrint);
		File file = new File(pathPdfPrint);
		System.out.println("XML2PDF.findXSLPath() 111aaaa : " + file);
		Collection<File> files = null;
		if (file.exists()) {
			files = FileUtils.listFiles(file, extensions, false);
		}
		if (!file.exists() || (files != null && files.size() == 0)) {
			pathPdfPrint = (workFlowBean.getRequest().getRealPath("/WEB-INF/classes/pdfPrint/" + userBean.getAccountRef() + "/" + workFlowBean.getArchive().getType())).replaceAll("\\\\", "/");
			file = new File(pathPdfPrint);
			if (file.exists()) {
				files = FileUtils.listFiles(file, extensions, false);
			}
			if (!file.exists() || (files != null && files.size() == 0)) {
				System.out.println("XML2PDF.findXSLPath() 222 : " + pathPdfPrint);
				pathPdfPrint = (workFlowBean.getRequest().getRealPath("/WEB-INF/classes/pdfPrint/" + userBean.getAccountRef())).replaceAll("\\\\", "/");
				file = new File(pathPdfPrint);
				if (file.exists()) {
					files = FileUtils.listFiles(file, extensions, false);
				}
				if (!file.exists() || (files != null && files.size() == 0)) {
					System.out.println("XML2PDF.findXSLPath() 333 : " + pathPdfPrint);
					pathPdfPrint = (workFlowBean.getRequest().getRealPath("/WEB-INF/classes/pdfPrint/" + workFlowBean.getArchive().getAlias())).replaceAll("\\\\", "/");
					file = new File(pathPdfPrint);
					if (file.exists()) {
						files = FileUtils.listFiles(file, extensions, false);
					}
					if (!file.exists() || (files != null && files.size() == 0)) {
						System.out.println("XML2PDF.findXSLPath() 444 : " + pathPdfPrint);
						pathPdfPrint = (workFlowBean.getRequest().getRealPath("/WEB-INF/classes/pdfPrint/" + workFlowBean.getArchive().getType())).replaceAll("\\\\", "/");
						file = new File(pathPdfPrint);
						if (file.exists()) {
							files = FileUtils.listFiles(file, extensions, false);
						}
						if (!file.exists() || (files != null && files.size() == 0)) {
							System.out.println("XML2PDF.findXSLPath() 555 : " + pathPdfPrint);
							pathPdfPrint = (workFlowBean.getRequest().getRealPath("/WEB-INF/classes/pdfPrint")).replaceAll("\\\\", "/");
							file = new File(pathPdfPrint);
							if (file.exists()) {
								files = FileUtils.listFiles(file, extensions, false);
							}
							if (!file.exists() || (files != null && files.size() == 0)) {
								System.out.println("XML2PDF.findXSLPath() 666 : " + pathPdfPrint);
								pathPdfPrint = null;
							}
						}
					}
				}
			}
		}
		return pathPdfPrint;
	}

	@Deprecated
	private String findXSLPathOLD(UserBean userBean, WorkFlowBean workFlowBean) {
		/*
		 * String pathPdfPrint = (servletContext.getRealPath("/WEB-INF/classes/pdfPrint")).replaceAll("\\\\", "/"); System.out.println("XML2PDF.execute() "+pathPdfPrint); pathPdfPrint = (servletContext.getRealPath("/WEB-INF/classes/pdfPrint/"+userBean.getIdAccount())).replaceAll("\\\\", "/");
		 * System.out.println("XML2PDF.execute() "+pathPdfPrint); pathPdfPrint = (servletContext.getRealPath("/WEB-INF/classes/pdfPrint/"+userBean.getIdAccount()+"/"+userBean.getTheArch())).replaceAll("\\\\", "/"); System.out.println("XML2PDF.execute() "+pathPdfPrint);
		 */
		// Prima cerco la dir con<IDACCOUNT e DBNAME
		String pathPdfPrint = (workFlowBean.getRequest().getRealPath("/WEB-INF/classes/pdfPrint/" + userBean.getAccountRef() + "/" + workFlowBean.getAlias())).replaceAll("\\\\", "/");
		System.out.println("XML2PDF.execute() " + pathPdfPrint);
		File file = new File(pathPdfPrint);
		if (!file.exists()) {
			// System.out.println("XML2PDF.findXSLPath() 111 Non ho trovato la prima dir, cerco la dir con IDACCOUNT " + pathPdfPrint);
			// Non ho trovato la prima dir, cerco la dir con IDACCOUNT
			pathPdfPrint = (workFlowBean.getRequest().getRealPath("/WEB-INF/classes/pdfPrint/" + userBean.getAccountRef())).replaceAll("\\\\", "/");
			file = new File(pathPdfPrint);
			if (!file.exists()) {
				// System.out.println("XML2PDF.findXSLPath() 333 Non ho trovato la second dir, cerco direttamente sotto pdfPrint nome webapp " + pathPdfPrint);
				System.out.println("AAAAAAAAAAAAA" + "/WEB-INF/classes/pdfPrint/" + workFlowBean.getArchive().getWebapp());
				// Non ho trovato la second dir, cerco direttamente sotto pdfPrint
				pathPdfPrint = (workFlowBean.getRequest().getRealPath("/WEB-INF/classes/pdfPrint/" + workFlowBean.getArchive().getWebapp())).replaceAll("\\\\", "/");
				// System.out.println("XML2PDF.findXSLPath() webapp " + pathPdfPrint);
				file = new File(pathPdfPrint);
				if (!file.exists()) {
					// System.out.println("XML2PDF.findXSLPath() 222 Non ho trovato la second dir, cerco direttamente sotto pdfPrint " + pathPdfPrint);
					// Non ho trovato la second dir, cerco direttamente sotto pdfPrint
					pathPdfPrint = (workFlowBean.getRequest().getRealPath("/WEB-INF/classes/pdfPrint")).replaceAll("\\\\", "/");
					file = new File(pathPdfPrint);
					if (!file.exists()) {
						// System.out.println("XML2PDF.findXSLPath() 444 Non ho trovato la terza dir, cerco torno null " + pathPdfPrint);
						// Non ho trovato la terza dir, cerco torno null
						pathPdfPrint = null;
					}

				}
			}
		}

		System.out.println("XML2PDF.findXSLPath() retunr " + pathPdfPrint);
		return pathPdfPrint;
	}

	private String executePDFPrint(WorkFlowBean workFlowBean, XWConnection xwconn, String PDFPhrase, String PDFNdoc, String PDFType, String PDFXSLType) throws XWException, FOPException, FileNotFoundException, UnsupportedEncodingException {
		/* ON FLY */
		// System.out.println("xdams - [INFO]  :::::::: pdf print started");

		System.out.println("xdams - [INFO]  :::::::: PDFType " + PDFType);
		System.out.println("xdams - [INFO]  :::::::: PDFPhrase " + PDFPhrase);
		System.out.println("xdams - [INFO]  :::::::: PDFNdoc " + PDFNdoc);
		String xmlString = "";
		if (PDFPhrase != null) {
			// System.out.println("aaaaaaaaaaaaaaaaa 20");
			xmlString = XWCommandSender(xwconn, workFlowBean.getAlias(), null, PDFType, PDFPhrase);
			// System.out.println("aaaaaaaaaaaaaaaaa 21");
		} else {
			// System.out.println("aaaaaaaaaaaaaaaaa 22");
			xmlString = XWCommandSender(xwconn, workFlowBean.getAlias(), null, PDFType, PDFNdoc);
			// System.out.println("aaaaaaaaaaaaaaaaa 23");
		}

		try {
			// System.out.println("PDFXSLType " + PDFXSLType);
			// System.out.println("PDFXSLType " + new File((XSLDir) + "/preprocessor_" + aReq.getParameter("PDFXSLType").replaceAll(".xsl", ".xslt")));
			// System.out.println("PDFXSLType " + new File((XSLDir) + "/preprocessor_" + aReq.getParameter("PDFXSLType").replaceAll(".xsl", ".xslt")).exists());
			File preProcessorFile = new File((XSLDir) + "/preprocessor_" + MyRequest.getParameter("PDFXSLType", parameterMap).replaceAll(".xsl", ".xslt"));
			if (preProcessorFile.exists()) {
				TrasformXslt20 trasformXslt = new TrasformXslt20();
				String trasfomed = trasformXslt.xslt(xmlString, new FileInputStream(preProcessorFile));
				// System.out.println("XML2PDF.executePDFPrint()");
				// System.out.println(trasfomed);
				xmlString = trasfomed;
				// System.out.println("XML2PDF.executePDFPrint()");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xmlString;
	}

	public String XWCommandSender(XWConnection xwconn, String PDFXMLArchive, String XWCMDOutputPath, String PDFType, String sel_num) throws XWException {
		String result = String.valueOf(new Date().getTime());
		try {
			String command = "";
			if (PDFType.trim().equals("4")) {
				System.out.println("CMD Esito ricerca");
				if (XWCMDOutputPath == null) {
					command = "<cmd c=\"8\" bits=\"" + (XMLCommand.Export_Full + XMLCommand.Export_Memory) + "\"  sel=\"" + sel_num + "\" />";
				} else {
					command = "<cmd c=\"8\" bits=\"6\" fn=\"" + XWCMDOutputPath + "\\" + result + ".xml\" sel=\"" + sel_num + "\" />";
				}
				System.out.println("command: " + command);
			} else if (PDFType.trim().equals("1")) {
				System.out.println("CMD singola scheda");
				if (XWCMDOutputPath == null) {
					command = "<cmd c=\"8\" bits=\"" + (XMLCommand.Export_Full + XMLCommand.Export_Memory) + "\"  num=\"" + sel_num + "\" num2=\"" + sel_num + "\" />";
				} else {
					command = "<cmd c=\"8\" bits=\"6\" fn=\"" + XWCMDOutputPath + "\\" + result + ".xml\" num=\"" + sel_num + "\" num2=\"" + sel_num + "\" />";
				}
			} else if (PDFType.trim().equals("2")) {
				QueryResult qr = new QueryResult();
				int docFather = Integer.parseInt(sel_num);
				int[] ilPadre = new int[1];
				ilPadre[0] = docFather;
				xwconn.addToQueryResult(xwconn.connection, xwconn.getTheDb(), qr, ilPadre);
				String selid = xwconn.addToQueryResult(qr, xwconn.getSonsFromNumDoc(Integer.parseInt(sel_num))).id;
				System.out.println("CMD padre + figli primo livello");
				if (XWCMDOutputPath == null) {
					command = "<cmd c=\"8\" bits=\"" + (XMLCommand.Export_Full + XMLCommand.Export_Memory) + "\" sel=\"" + selid + "\" />";
				} else {
					command = "<cmd c=\"8\" bits=\"6\" fn=\"" + XWCMDOutputPath + "/" + result + ".xml\" sel=\"" + selid + "\" />";
				}
			} else if (PDFType.trim().equals("3")) {
				System.out.println("CMD tutta la gerarchia");
				if (XWCMDOutputPath == null) {
					command = "<cmd c=\"8\" bits=\"" + (XMLCommand.Export + XMLCommand.Export_Memory) + "\" num=\"" + sel_num + "\" num2=\"" + sel_num + "\"  />";
				} else {
					command = "<cmd c=\"8\" bits=\"8\" fn=\"" + XWCMDOutputPath + "/" + result + ".xml\" num=\"" + sel_num + "\" num2=\"" + sel_num + "\"  />";
				}
			}

			if (XWCMDOutputPath != null) {
				xwconn.executeCMD(command);
				result += ".xml";
			} else {
				try {
					result = xwconn.XMLCommand(xwconn.connection, xwconn.getTheDb(), command);
				} catch (Exception e) {
					// TODO: handle exception
				}

			}

			xwconn.close();
			result = ProblematicCharactersFilter.replaceCharacters(result);
			System.out.println("QUI RESULT " + result + "QUI RESUTL");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (XWException e) {
			e.printStackTrace();
		} finally {
			if (xwconn != null && !xwconn.isClosed()) {
				xwconn.close();
			}
		}
		return result;
	}

	public void convert(InputStream xml, InputStream xslt, WorkFlowBean workFlowBean, String contentType, String mimeConstants, String extension) throws Exception {
		try {

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			workFlowBean.getResponse().setContentType(contentType);
			byte[] content = null;
			if (contentType.indexOf("html") == -1) {
				FopFactory fopFactory = FopFactory.newInstance();
				fopFactory.setUserConfig(workFlowBean.getRequest().getRealPath("/WEB-INF/fop-config/userconfig.xml"));
				FontInfo fontInfo = new FontInfo();
				Map map = fontInfo.getFonts();
				FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
				Fop fop = fopFactory.newFop(mimeConstants, foUserAgent, out);
				TransformerFactory factory = TransformerFactory.newInstance();
				Transformer transformer = factory.newTransformer(new StreamSource(xslt));
				transformer.setParameter("versionParam", "2.0");
				Source src = new StreamSource(xml);
				Result res = new SAXResult(fop.getDefaultHandler());
				transformer.transform(src, res);
				content = out.toByteArray();
			} else {
				String string = TrasformXslt20.xslt(xml, xslt, null);
				// System.out.println("XML2PDF.convert() " + string);
				content = string.getBytes("ISO-8859-1");
			}

			String reportName = StringUtils.substringBeforeLast(MyRequest.getParameter("PDFXSLType", parameterMap), ".") + "." + extension;
			workFlowBean.getResponse().setHeader("Content-Disposition", "attachment; filename=" + reportName);
			workFlowBean.getResponse().setContentLength(content.length);
			workFlowBean.getResponse().getOutputStream().write(content);
			workFlowBean.getResponse().getOutputStream().flush();
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			throw new Exception(ex);
		} catch (OutOfMemoryError ex) {
			ex.printStackTrace();
			throw new Exception(ex);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex);
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new Exception(ex);
		}

	}

	public void convert(InputStream xml, InputStream xslt, OutputStream outputStream, String mimeConstants) throws Exception {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			FopFactory fopFactory = FopFactory.newInstance();
			FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
			Fop fop = fopFactory.newFop(mimeConstants, foUserAgent, out);
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(new StreamSource(xslt));
			transformer.setParameter("versionParam", "2.0");
			Source src = new StreamSource(xml);
			Result res = new SAXResult(fop.getDefaultHandler());
			transformer.transform(src, res);
			byte[] content = out.toByteArray();
			outputStream.write(content);
			outputStream.flush();
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			throw new Exception(ex);
		} catch (OutOfMemoryError ex) {
			ex.printStackTrace();
			throw new Exception(ex);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex);
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new Exception(ex);
		}

	}

}
