package org.xdams.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.WebUtils;
import org.xdams.conf.master.ConfBean;
import org.xdams.importcsv.ImportCSV;
import org.xdams.security.AuthenticationType;
import org.xdams.security.UserDetails;
import org.xdams.security.load.LoadUserManager;
import org.xdams.user.access.ServiceUser;
import org.xdams.user.bean.Archive;
import org.xdams.user.bean.UserBean;
import org.xdams.workflow.bean.WorkFlowBean;

@Controller
public class xDamsControllerAsync {

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
	ImportCSV importCSV;

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

	@RequestMapping(value = "/importCSV/{archive}", method = RequestMethod.POST)
	public String importCSV(@RequestParam("file") CommonsMultipartFile commonsMultipartFile, @ModelAttribute("userBean") UserBean userBean, @ModelAttribute("confBean") ConfBean confBean, @PathVariable String archive, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		common(confBean, userBean, archive, modelMap, request, response);
		WorkFlowBean workFlowBean = (WorkFlowBean) modelMap.get("workFlowBean");
		try {
			String numDocFather = request.getParameter("numDocFather");
			String mailTo = request.getParameter("mailTo");
			Archive archiveBean = workFlowBean.getArchive();
			Map<String, String[]> mapRequ = request.getParameterMap();
			Map<String, String> mapextraCVSXML = new LinkedHashMap<>();
			for (Entry<String, String[]> entry : mapRequ.entrySet()) {
				if(entry.getKey().startsWith("extraCVSXML_")){
					mapextraCVSXML.put(entry.getKey().replaceAll("extraCVSXML_", "").replace('.', '/'), entry.getValue()[0]);
				}
			}
			importCSV.execute(commonsMultipartFile, userBean, archiveBean,mapextraCVSXML, numDocFather, mailTo);
			modelMap.put("importCSVOp", "success");
		} catch (Exception e) {
			modelMap.put("importCSVOp", "error");
			modelMap.put("importCSVOpMsg", e.getMessage());
		}
		return "importCSV/importCSVOp";
	}
}
