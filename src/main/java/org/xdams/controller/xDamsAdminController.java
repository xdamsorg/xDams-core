package org.xdams.controller;

import it.highwaytech.db.QueryResult;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.WebUtils;
import org.xdams.admin.bean.CreateArchiveResultBean;
import org.xdams.admin.command.AdminCommand;
import org.xdams.admin.command.CreateArchive;
import org.xdams.conf.master.ConfBean;
import org.xdams.page.view.bean.ManagingBean;
import org.xdams.security.AuthenticationType;
import org.xdams.security.UserDetails;
import org.xdams.security.load.LoadUserManager;
import org.xdams.security.load.LoadUserSpeedUp;
import org.xdams.user.access.ServiceAccount;
import org.xdams.user.access.ServiceUser;
import org.xdams.user.bean.Account;
import org.xdams.user.bean.Archive;
import org.xdams.user.bean.UserBean;
import org.xdams.utility.CommonUtils;
import org.xdams.utility.request.MyRequest;
import org.xdams.workflow.bean.WorkFlowBean;
import org.xdams.xmlengine.connection.manager.ConnectionManager;
import org.xdams.xw.XWConnection;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@SessionAttributes({ "userBean" })
@SuppressWarnings("unchecked")
public class xDamsAdminController {

	private static final Logger logger = LoggerFactory.getLogger(xDamsAdminController.class);

	@Autowired
	ServiceUser serviceUser;

	@Autowired
	ServletContext servletContext;

	@Autowired
	AuthenticationType authenticationType;

	@Autowired
	Boolean multiAccount;

	@Value("#{mapExtraParam}")
	HashMap mapExtraParam;

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	CreateArchive createArchive;

	@ModelAttribute
	public void workFlowBean(Model model) {
		model.addAttribute("workFlowBean", new WorkFlowBean());
	}

	// @ModelAttribute
	public void userLoad(ModelMap model) {
		// System.out.println("xDamsController.userLoad() model.containsAttribute(\"userBean\"): " + model.containsAttribute("userBean"));
		if (!model.containsAttribute("userBean")) {
			UserDetails userDetails = null;
			try {
				userDetails = (UserDetails) ((SecurityContext) SecurityContextHolder.getContext()).getAuthentication().getPrincipal();
				UserBean userBean = LoadUserManager.executeLoad(userDetails, authenticationType);
				model.addAttribute("userBean", userBean);

			} catch (Exception e) {
			}
		}
	}

	@ModelAttribute
	public void frontUrl(ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// model.addAttribute("frontUrl", request.getContextPath() + "/resources");
		userLoad(model);
		model.addAttribute("frontUrl", request.getContextPath() + "/resources");
		// System.out.println("xDamsController.frontUrl() multiAccount: " + multiAccount);
		// System.out.println("xDamsController.frontUrl() model.get(\"userBean\"): " + model.get("userBean"));
		if (multiAccount && model.get("userBean") != null) {
			model.addAttribute("frontUrl", request.getContextPath() + "/resources/" + ((UserBean) model.get("userBean")).getAccountRef());
		}

		// System.out.println("xDamsController.frontUrl() model.get(\"frontUrl\"): " + model.get("frontUrl"));
		model.addAttribute("contextPath", request.getContextPath());
		String userAgent = ((HttpServletRequest) request).getHeader("User-Agent");
		if (userAgent.toLowerCase().contains("msie")) {
			response.addHeader("X-UA-Compatible", "IE=edge");
		}

		try {
			Locale locale = RequestContextUtils.getLocale(request);
			((UserBean) model.get("userBean")).setLanguage(locale.getLanguage());
		} catch (Exception e) {
			// TODO: handle exception
		}
		model.put("realPath", WebUtils.getRealPath(servletContext, ""));
	}

	public void common(ConfBean confBean, UserBean userBean, String archive, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		WorkFlowBean workFlowBean = (WorkFlowBean) modelMap.get("workFlowBean");
		// SETTO IL WORKFLOW PER LA NAVIGAZIONE DI xDams
		workFlowBean.setArchive(serviceUser.getArchive(userBean, archive));
		workFlowBean.setRequest(request);
		workFlowBean.setResponse(response);
		workFlowBean.setApplicationContext(applicationContext);
		modelMap.put("workFlowBean", workFlowBean);
	}

	public void common(ConfBean confBean, UserBean userBean, String archive, String archiveLookup, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		WorkFlowBean workFlowBean = (WorkFlowBean) modelMap.get("workFlowBean");
		workFlowBean.setArchiveLookup(serviceUser.getArchive(userBean, archiveLookup));
		common(confBean, userBean, archive, modelMap, request, response);
	}

	@RequestMapping(value = { "/resources/langForJS" }, method = RequestMethod.GET)
	public ResponseEntity<String> langForJS(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Locale locale = RequestContextUtils.getLocale(request);
		ResourceBundle bundle = ResourceBundle.getBundle("xdams_messages", locale);
		Map<String, String> bundleMap = new LinkedHashMap<String, String>();
		for (String key : bundle.keySet()) {
			String value = bundle.getString(key);
			bundleMap.put(key, value);
		}
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(bundleMap);
		String globalLangOption = "var globalOption = " + json;
		// System.out.println(json);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", MediaType.APPLICATION_JSON.toString());
		return new ResponseEntity<String>(globalLangOption, responseHeaders, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/admin/createMessage", method = RequestMethod.GET, produces = "text/html")
	public @ResponseBody
	String createMessage(HttpServletRequest request) {
		return CommonUtils.stripPunctuationAdv(request.getParameter("messageVal"), '_');
	}

	@RequestMapping(value = "/message_rest", method = RequestMethod.GET, produces = { "application/json" })
	@ResponseStatus(HttpStatus.OK)
	public String messageLang(HttpServletRequest request) {
		Locale locale = RequestContextUtils.getLocale(request);
		ResourceBundle bundle = ResourceBundle.getBundle("xdams_messages", locale);
		// System.out.println(bundle);
		return null;
	}

	@RequestMapping(value = "/admin/{archive}/exportMenu", method = RequestMethod.GET, produces = "text/html")
	public String consoleMenu(@ModelAttribute("userBean") UserBean userBean, @ModelAttribute("confBean") ConfBean confBean, @PathVariable String archive, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		common(confBean, userBean, archive, modelMap, request, response);
		AdminCommand adminCommand = new AdminCommand(request.getParameterMap(), modelMap);
		ManagingBean managingBean = adminCommand.execute();
		return "admin/" + managingBean.getDispatchView();
	}

	@RequestMapping(value = "/admin/generateArchive", method = RequestMethod.GET)
	public String createArchive(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String xDamsType = MyRequest.getParameter("xDamsType", request.getParameterMap());
		CreateArchiveResultBean createArchiveResultBean = createArchive.execute(xDamsType);
		modelMap.put("opExit", createArchiveResultBean);
		return "admin/createArchiveResult";
	}

	@RequestMapping(value = "/admin/{account}/executeCreateArchive")
	public String executeCreateArchive(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @PathVariable String account, Archive archive) throws Exception {
		try {
			XWConnection xwconn = null;
			ConnectionManager connectionManager = new ConnectionManager();
			try {
				xwconn = connectionManager.getConnection(authenticationType.getArchiveXWAY());

				QueryResult queryResult = xwconn.getQRfromPhrase("([XML,/account/@id]=\"" + account + "\")");
				int numDoc = xwconn.getNumDocFromQRElement(queryResult, 0);
				String xmlArchives = xwconn.getSingleXMLFromNumDoc(numDoc).replaceAll("(?s)<!--.*?-->", "");
				Account accountBean = new Account();
				Pattern patternArchiveGroup = Pattern.compile("((?i)<archiveGroup\\s*name=\"([^>]+)\">(.+?)</archiveGroup>)", Pattern.DOTALL);
				Matcher matcherArchiveGroup = patternArchiveGroup.matcher(xmlArchives);
				String templateArchive = "<archive alias=\"${alias}\" host=\"${hostname}\" ico=\"${ico}\" pne=\"${pne}\" port=\"${port}\" type=\"${type}\">${descr}</archive>";
				Map<String, String> valuesMap = new HashMap<String, String>();
				valuesMap.put("descr", archive.getArchiveDescr());
				valuesMap.put("type", archive.getType());
				valuesMap.put("ico", archive.getIco());
				valuesMap.put("hostname", archive.getHost());
				valuesMap.put("pne", archive.getPne());
				valuesMap.put("port", archive.getPort());

				if (request.getParameter("dbDir") == null || request.getParameter("dbDir").equals("")) {
					return createArchiveMenu(modelMap, request, response, account);
				} else {
					createArchive.setDbDir(request.getParameter("dbDir"));
				}

				CreateArchiveResultBean createArchiveResultBean = createArchive.execute(archive.getType());
				if (createArchiveResultBean.isCreated()) {
					valuesMap.put("alias", createArchiveResultBean.getAlias());
					archive.setAlias(createArchiveResultBean.getAlias());
					boolean isNewGroup = true;
					while (matcherArchiveGroup.find()) {
						String accountStr = matcherArchiveGroup.group(2);
						if (accountStr.equalsIgnoreCase(archive.getGroupName())) {
							StrSubstitutor strSubstitutor = new StrSubstitutor(valuesMap);
							String resolvedString = strSubstitutor.replace(templateArchive);
							String temp = matcherArchiveGroup.group(1).replaceAll("</archiveGroup>", resolvedString + "</archiveGroup>");
							xmlArchives = StringUtils.replace(xmlArchives, matcherArchiveGroup.group(1), temp);
							isNewGroup = false;
						}
					}

					if (isNewGroup) {
						StrSubstitutor strSubstitutor = new StrSubstitutor(valuesMap);
						String resolvedString = strSubstitutor.replace(templateArchive);
						String grpNew = "<archiveGroup name=\"" + archive.getGroupName() + "\">" + resolvedString + "</archiveGroup>";
						xmlArchives = xmlArchives.replaceAll("</account>", grpNew + "</account>");
					}

				}

				xwconn.executeUpdateByDocNumber(xmlArchives, numDoc);
				Map<String, Archive> archivesMap = LoadUserSpeedUp.extractArchiveList(account, xmlArchives, accountBean);
				ServiceAccount serviceAccount = new ServiceAccount();
				modelMap.put("archiveByGroup", serviceAccount.getArchiveByGroup(archivesMap));
				modelMap.put("archivesMap", archivesMap);
				modelMap.put("msgSuccess", "ok");
				modelMap.put("createArchiveResultBean", createArchiveResultBean);
			} catch (Exception e) {
				e.printStackTrace();
				modelMap.put("msgSuccess", "notOk");
			} finally {
				connectionManager.closeConnection(xwconn);
			}
		} catch (Exception e) {
			modelMap.put("msgSuccess", "notOk");
		}

		return "admin/createArchiveResult";
	}

	@RequestMapping(value = "/admin/{account}/createArchiveMenu", method = RequestMethod.GET)
	public String createArchiveMenu(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @PathVariable String account) throws Exception {

		XWConnection xwconn = null;
		ConnectionManager connectionManager = new ConnectionManager();
		try {
			xwconn = connectionManager.getConnection(authenticationType.getArchiveXWAY());
			QueryResult queryResult = xwconn.getQRfromPhrase("([XML,/account/@id]=\"" + account + "\")");
			int numDoc = xwconn.getNumDocFromQRElement(queryResult, 0);
			String xmlArchives = xwconn.getSingleXMLFromNumDoc(numDoc).replaceAll("(?s)<!--.*?-->", "");
			Account accountBean = new Account();
			// System.out.println(xmlArchives);
			Map<String, Archive> archivesMap = LoadUserSpeedUp.extractArchiveList(account, xmlArchives, accountBean);
			ServiceAccount serviceAccount = new ServiceAccount();
			modelMap.put("archiveByGroup", serviceAccount.getArchiveByGroup(archivesMap));
			modelMap.put("archivesMap", archivesMap);
			modelMap.put("account", account);
			modelMap.put("dbDir", createArchive.getDbDir());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			connectionManager.closeConnection(xwconn);
		}

		return "admin/createArchiveMenu";
	}

}
