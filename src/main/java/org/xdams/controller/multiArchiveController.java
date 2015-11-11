package org.xdams.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.WebUtils;
import org.xdams.conf.master.ConfBean;
import org.xdams.page.multiarchive.command.MultiQueryPageCommand;
import org.xdams.page.multiarchive.command.MultiTitlePageCommand;
import org.xdams.page.view.modeling.QueryPageView;
import org.xdams.security.AuthenticationType;
import org.xdams.security.UserDetails;
import org.xdams.security.load.LoadUser;
import org.xdams.security.load.LoadUserManager;
import org.xdams.security.load.LoadUserSpeedUp;
import org.xdams.user.access.ServiceUser;
import org.xdams.user.bean.Archive;
import org.xdams.user.bean.UserBean;
import org.xdams.utility.resource.ConfManager;
import org.xdams.workflow.bean.WorkFlowBean;
import org.xdams.xml.builder.XMLBuilder;

@Controller
@SessionAttributes({ "userBean" })
@SuppressWarnings("unchecked")
public class multiArchiveController {

	@Autowired
	ServiceUser serviceUser;

	@Autowired
	ServletContext servletContext;

	@Autowired
	AuthenticationType authenticationType;

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	Boolean multiAccount;

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

	@RequestMapping(value = "/search/{archive}/query-multiarchive", method = RequestMethod.GET)
	public String queryPageMulti(@ModelAttribute("userBean") UserBean userBean, @ModelAttribute("confBean") ConfBean confBean, @PathVariable String archive, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		common(confBean, userBean, archive, modelMap, request, response);
		Map<String, List<Archive>> usersArchives = new LinkedHashMap<String, List<Archive>>();
		serviceUser.loadArchives(userBean, usersArchives);
		modelMap.addAttribute("usersArchives", usersArchives);
		MultiQueryPageCommand queryPageCommand = new MultiQueryPageCommand(request.getParameterMap(), modelMap);
		queryPageCommand.execute();
		QueryPageView pageView = new QueryPageView();
		WorkFlowBean workFlowBean = (WorkFlowBean) modelMap.get("workFlowBean");
		pageView.generateView(workFlowBean, confBean, userBean, modelMap);
		modelMap.put("positionMap", pageView.getPositionMap());
		modelMap.put("positionAdminMap", pageView.getPositionAdminMap());
		modelMap.put("outputHourField", pageView.getOutputHourField());
		modelMap.put("outputDataField", pageView.getOutputDataField());
		modelMap.put("outputSortField", pageView.getOutputSortField());
		return "multiArchive/search/query-multiarchive";
	}

	@RequestMapping(value = "/search/{archive}/title-multiarchive")
	public String titlePage(@ModelAttribute("userBean") UserBean userBean, @ModelAttribute("confBean") ConfBean confBean, @PathVariable String archive, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		common(confBean, userBean, archive, modelMap, request, response);
		MultiTitlePageCommand multiTitlePageCommand = new MultiTitlePageCommand(request.getParameterMap(), modelMap);
		multiTitlePageCommand.execute();
		return "multiArchive/search/title-multiarchive";
	}

}
