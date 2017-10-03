package org.xdams.controller;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.xdams.admin.command.AdminCommand;
import org.xdams.conf.master.ConfBean;
import org.xdams.page.view.bean.ManagingBean;
import org.xdams.security.AuthenticationType;
import org.xdams.security.UserDetails;
import org.xdams.security.load.LoadUserManager;
import org.xdams.user.access.ServiceUser;
import org.xdams.user.bean.UserBean;
import org.xdams.utility.CommonUtils;
import org.xdams.workflow.bean.WorkFlowBean;

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

	@ModelAttribute
	public void workFlowBean(Model model) {
		model.addAttribute("workFlowBean", new WorkFlowBean());
	}

	//@ModelAttribute
	public void userLoad(ModelMap model) {
//		System.out.println("xDamsController.userLoad() model.containsAttribute(\"userBean\"): " + model.containsAttribute("userBean"));
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
//		System.out.println("xDamsController.frontUrl() multiAccount: " + multiAccount);
//		System.out.println("xDamsController.frontUrl() model.get(\"userBean\"): " + model.get("userBean"));
		if (multiAccount && model.get("userBean") != null) {
			model.addAttribute("frontUrl", request.getContextPath() + "/resources/" + ((UserBean) model.get("userBean")).getAccountRef());
		}

//		System.out.println("xDamsController.frontUrl() model.get(\"frontUrl\"): " + model.get("frontUrl"));
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
		System.out.println(json);
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
		System.out.println(bundle);
		return null;
	}



	@RequestMapping(value = "/admin/{archive}/exportMenu", method = RequestMethod.GET, produces = "text/html")
	public String consoleMenu(@ModelAttribute("userBean") UserBean userBean, @ModelAttribute("confBean") ConfBean confBean, @PathVariable String archive, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		common(confBean, userBean, archive, modelMap, request, response);
		AdminCommand adminCommand = new AdminCommand(request.getParameterMap(), modelMap);
		ManagingBean managingBean = adminCommand.execute();
 		return "admin/"+managingBean.getDispatchView();
	}

}
