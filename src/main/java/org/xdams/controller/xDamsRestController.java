package org.xdams.controller;

import it.highwaytech.broker.XMLCommand;
import it.highwaytech.db.QueryResult;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.util.WebUtils;
import org.xdams.ajax.bean.AjaxBean;
import org.xdams.conf.master.ConfBean;
import org.xdams.manager.conf.MultiEditingManager;
import org.xdams.page.command.EditingPageCommand;
import org.xdams.page.command.HierBrowserPageCommand;
import org.xdams.page.command.InfoTabCommand;
import org.xdams.page.command.InsertRecordCommand;
import org.xdams.page.command.LookupCommand;
import org.xdams.page.command.PreInsertPageCommand;
import org.xdams.page.command.QueryPageCommand;
import org.xdams.page.command.TitlePageCommand;
import org.xdams.page.command.ViewPageCommand;
import org.xdams.page.command.VocabolarioMultiCommand;
import org.xdams.page.factory.AjaxFactory;
import org.xdams.page.factory.ManagingFactory;
import org.xdams.page.form.bean.CustomPageBean;
import org.xdams.page.form.bean.LookupBean;
import org.xdams.page.upload.bean.UploadBean;
import org.xdams.page.upload.command.AssociateCommand;
import org.xdams.page.upload.command.UploadCommand;
import org.xdams.page.view.bean.ManagingBean;
import org.xdams.page.view.modeling.QueryPageView;
import org.xdams.save.SaveDocumentCommand;
import org.xdams.security.AuthenticationType;
import org.xdams.security.UserDetails;
import org.xdams.security.load.LoadUserManager;
import org.xdams.security.load.LoadUserSpeedUp;
import org.xdams.user.access.ServiceUser;
import org.xdams.user.bean.Account;
import org.xdams.user.bean.Archive;
import org.xdams.user.bean.UserBean;
import org.xdams.utility.XMLCleaner;
import org.xdams.utility.request.MyRequest;
import org.xdams.utility.resource.ConfManager;
import org.xdams.workflow.bean.WorkFlowBean;
import org.xdams.xml.builder.XMLBuilder;
import org.xdams.xmlengine.connection.manager.ConnectionManager;
import org.xdams.xw.XWConnection;
import org.xdams.xw.exception.XWException;

@Controller
@SessionAttributes({ "userBean" })
@SuppressWarnings("unchecked")
public class xDamsRestController {

	private static final Logger logger = LoggerFactory.getLogger(xDamsRestController.class);

	@Autowired
	ServiceUser serviceUser;

	@Autowired
	ServletContext servletContext;

	@Autowired
	AuthenticationType authenticationType;

	@Autowired
	Boolean multiAccount;

	// @ModelAttribute
	// public void workFlowBean(Model model) {
	// model.addAttribute("workFlowBean", new WorkFlowBean());
	// }
	//
	// @ModelAttribute
	// public void userLoad(Model model) {
	// if (!model.containsAttribute("userBean")) {
	// UserDetails userDetails = null;
	// try {
	// userDetails = (UserDetails) ((SecurityContext) SecurityContextHolder.getContext()).getAuthentication().getPrincipal();
	// UserBean userBean = LoadUserManager.executeLoad(userDetails, authenticationType);
	// model.addAttribute("userBean", userBean);
	// } catch (Exception e) {
	//
	// }
	// }
	// }
	//
	// @ModelAttribute
	// public void frontUrl(ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
	// // model.addAttribute("frontUrl", request.getContextPath() + "/resources");
	// model.addAttribute("frontUrl", request.getContextPath() + "/resources");
	// if (multiAccount && model.get("userBean") != null) {
	// model.addAttribute("frontUrl", request.getContextPath() + "/resources/" + ((UserBean) model.get("userBean")).getAccountRef());
	// }
	// model.addAttribute("contextPath", request.getContextPath());
	// String userAgent = ((HttpServletRequest) request).getHeader("User-Agent");
	// if (userAgent.toLowerCase().contains("msie")) {
	// response.addHeader("X-UA-Compatible", "IE=edge");
	// }
	// }
	//
	// public void common(ConfBean confBean, UserBean userBean, String archive, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
	// WorkFlowBean workFlowBean = (WorkFlowBean) modelMap.get("workFlowBean");
	// // SETTO IL WORKFLOW PER LA NAVIGAZIONE DI xDams
	// workFlowBean.setArchive(serviceUser.getArchive(userBean, archive));
	// workFlowBean.setRequest(request);
	// workFlowBean.setResponse(response);
	// modelMap.put("workFlowBean", workFlowBean);
	// }
	//
	// public void common(ConfBean confBean, UserBean userBean, String archive, String archiveLookup, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
	// WorkFlowBean workFlowBean = (WorkFlowBean) modelMap.get("workFlowBean");
	// workFlowBean.setArchiveLookup(serviceUser.getArchive(userBean, archiveLookup));
	// common(confBean, userBean, archive, modelMap, request, response);
	// }

	@RequestMapping(value = "/rest/{accountID}/{archive}/{secretKey}", method = RequestMethod.GET, produces = "text/xml")
	public @ResponseBody
	String restCall(@PathVariable String archive, @PathVariable String secretKey, @PathVariable String accountID, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {

		XMLBuilder builder = ConfManager.getConfXML("rest-conf/rest-conf.xml");
		String validSecretKey = builder.valoreNodo("/root/secretKey/text()");
		String alias = builder.valoreNodo("/root/secretKey/@alias");
		String xsltType = MyRequest.getParameter("xsltType", "", request);
		StringBuilder outputBuilder = new StringBuilder();

		if (!secretKey.equals(validSecretKey)) {
			return createErrorResponse("Access denied, wrong api key.");
		}
		if (alias.indexOf(archive) == -1) {
			return createErrorResponse("Access denied, can't access this archive.");
		}

		String xsltPath = "";
		if (!xsltType.trim().equals("")) {
			xsltPath = builder.valoreNodo("/root/xslt[@type='" + xsltType + "']/text()");
		}
		String xslt = "";
		if (!xsltPath.trim().equals("")) {
			xslt = "<bst>" + ConfManager.getConfString(xsltPath) + "</bst>";
		}

		Account accountBean = new Account();
		// System.out.println("xDamsRestController.restCall() " + accountID);
		String xmlArchives = ConfManager.getConfString(accountID + "-security/accounts.xml");
		// System.out.println("xDamsRestController.restCall() " + xmlArchives);
		Map<String, Archive> archiveAllMap = LoadUserSpeedUp.extractArchiveList(accountID, xmlArchives, accountBean);

		ConnectionManager connectionManager = new ConnectionManager();
		XWConnection xwconn = null;

		String id = request.getParameter("id");
		String physDoc = request.getParameter("physDoc");
		String xwQuery = request.getParameter("xwQuery");
		String valueQuery = request.getParameter("valueQuery");

		int perpage = 10;
		int pageToShow = 1;
		try {
			pageToShow = Integer.parseInt(request.getParameter("pageToShow"));
		} catch (Exception e) {
			pageToShow = 1;
		}
		try {
			perpage = Integer.parseInt(request.getParameter("perpage"));
		} catch (Exception e) {
			perpage = 10;
		}

		try {
			xwconn = connectionManager.getConnection(archiveAllMap.get(archive));
			String command = "";
			QueryResult qr = null;
			List<String> result = null;
			if (id != null && id.trim() != null) {
				qr = xwconn.getQRfromPhrase("[XML,/c/@id]=" + id);
				command = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<cmd c=\"8\" bits=\"" + (XMLCommand.Export_Full + XMLCommand.Export_Memory) + "\" sel=\"" + qr.id + "\">" + xslt + "</cmd>";
				result = new ArrayList<String>();
				result.add(qr.id);
				result.add("1");
				result.add(pageToShow + "");
				result.add(pageToShow + "");
			} else if (xwQuery != null && xwQuery.trim() != null) {
				String queryFind = "";
				if (valueQuery != null && valueQuery.trim() != null) {
					queryFind = "[" + xwQuery + "]=" + valueQuery;
				} else {
					queryFind = "[" + xwQuery + "]=*";
				}
				qr = xwconn.getQRfromPhrase(queryFind);
				command = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<cmd c=\"8\" bits=\"" + (XMLCommand.Export_Full + XMLCommand.Export_Memory) + "\" sel=\"" + qr.id + "\">" + xslt + "</cmd>";
			} else if (physDoc != null) {
				qr = xwconn.getQRFromHier(Integer.parseInt(physDoc), true);
				command = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<cmd c=\"8\" bits=\"" + (XMLCommand.Export_Full + XMLCommand.Export_Memory) + "\" sel=\"" + qr.id + "\">" + xslt + "</cmd>";
			} else {
				result = findAll(xwconn, archiveAllMap.get(archive), pageToShow, perpage);
				command = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<cmd c=\"8\" bits=\"" + (XMLCommand.Export_Full + XMLCommand.Export_Memory) + "\" sel=\"" + result.get(0) + "\">" + xslt + "</cmd>";
			}
			String trasform = xwconn.XMLCommand(xwconn.connection, xwconn.getTheDb(), command);
			trasform = XMLCleaner.clearXwFullXML(trasform, true);

			if (result != null) {
				trasform = trasform.replaceAll("(?i)<\\?xml version=\"1.0\" encoding=\"ISO-8859-1\"\\?>", "");
				outputBuilder.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n");
				outputBuilder.append("<response found=\"" + result.get(1) + "\" page=\"" + result.get(2) + "\" totPages=\"" + result.get(3) + "\">\n");
				outputBuilder.append(trasform);
				outputBuilder.append("</response>");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return createErrorResponse(e.getMessage());
		} finally {
			try {
				xwconn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return outputBuilder.toString();
	}

	private String createErrorResponse(String message) {
		StringBuilder outputBuilder = new StringBuilder();
		outputBuilder.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n");
		outputBuilder.append("<response found=\"0\" page=\"0\" totPages=\"0\">\n");
		outputBuilder.append("<error>");
		outputBuilder.append(message);
		outputBuilder.append("</error>");
		outputBuilder.append("</response>");
		return outputBuilder.toString();
	}

	private static List<String> findAll(XWConnection xwConnection, Archive archive, int pageToShow, int perpage) throws SQLException, XWException {
		String query = "([UD,/xw/@UdType]=\"" + archive.getPne() + "\")";
		QueryResult qr = new QueryResult();
		QueryResult qrTemp = null;
		qrTemp = xwConnection.getQRfromPhrase(query);
		int found = qrTemp.elements;

		// controllo sulle variabili
		if (pageToShow < 1) {
			pageToShow = 1;
		}
		if (perpage < 1) {
			perpage = 1;
		}
		int totPages = found / perpage;
		if (found % perpage > 0) {
			totPages++;
		}

		// se la pagina richiesta supera il numero di pagine
		if (pageToShow > totPages) {
			pageToShow = totPages;
		}

		int start = (pageToShow - 1) * perpage;
		Vector<Integer> numDocs = new Vector<Integer>();
		for (int i = start; i < start + perpage; i++) {
			int numDoc = xwConnection.getNumDocFromQRElement(qrTemp, i);
			if (numDoc != 0) {
				numDocs.add(numDoc);
			}
		}
		xwConnection.addToQueryResult(xwConnection.connection, xwConnection.getTheDb(), qr, numDocs);
		System.out.println("found: " + found + "; start: " + start + "; totPages: " + totPages);
		List<String> result = new ArrayList<String>();
		result.add(qr.id);
		result.add(found + "");
		result.add(pageToShow + "");
		result.add(totPages + "");
		return result;
	}

}
