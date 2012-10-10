package org.xdams.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.util.WebUtils;
import org.xdams.ajax.bean.AjaxBean;
import org.xdams.conf.master.ConfBean;
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
import org.xdams.page.form.bean.LookupBean;
import org.xdams.page.upload.bean.UploadBean;
import org.xdams.page.upload.command.UploadCommand;
import org.xdams.page.view.bean.ManagingBean;
import org.xdams.page.view.modeling.QueryPageView;
import org.xdams.save.SaveDocumentCommand;
import org.xdams.security.UserDetails;
import org.xdams.security.load.LoadUser;
import org.xdams.user.access.ServiceUser;
import org.xdams.user.bean.Archive;
import org.xdams.user.bean.UserBean;
import org.xdams.utility.request.MyRequest;
import org.xdams.utility.resource.ConfManager;
import org.xdams.workflow.bean.WorkFlowBean;
import org.xdams.xml.builder.XMLBuilder;

@Controller
@SessionAttributes({ "userBean" })
@SuppressWarnings("unchecked")
public class xDamsController {

	private static final Logger logger = LoggerFactory.getLogger(xDamsController.class);

	@Autowired
	ServiceUser serviceUser;

	WorkFlowBean workFlowBean = new WorkFlowBean();

	@Autowired
	ServletContext servletContext;

	@ModelAttribute
	public void userLoad(Model model) throws Exception {
		if (!model.containsAttribute("userBean")) {
			UserDetails userDetails = null;
			try {
				userDetails = (UserDetails) ((SecurityContext) SecurityContextHolder.getContext()).getAuthentication().getPrincipal();
				XMLBuilder xmlUsers = ConfManager.getConfXML(userDetails.getAccount() + "-security/users.xml");
				XMLBuilder xmlArchives = ConfManager.getConfXML(userDetails.getAccount() + "-security/accounts.xml");
				XMLBuilder xmlrole = ConfManager.getConfXML(userDetails.getAccount() + "-security/role.xml");
				UserBean userBean = LoadUser.loadUser(xmlUsers, xmlArchives, xmlrole, userDetails.getId(), userDetails.getAccount());
				model.addAttribute("userBean", userBean);
			} catch (Exception e) {

			}
		}
	}

	@ModelAttribute
	public void frontUrl(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		model.addAttribute("frontUrl", request.getContextPath() + "/resources");
		model.addAttribute("contextPath", request.getContextPath());
		String userAgent = ((HttpServletRequest) request).getHeader("User-Agent");
		if (userAgent.toLowerCase().contains("msie")) {
			response.addHeader("X-UA-Compatible", "IE=8");
		}
	}

	public void common(ConfBean confBean, UserBean userBean, String archive, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// SETTO IL WORKFLOW PER LA NAVIGAZIONE DI xDams
		workFlowBean.setArchive(serviceUser.getArchive(userBean, archive));
		workFlowBean.setRequest(request);
		workFlowBean.setResponse(response);
		modelMap.put("workFlowBean", workFlowBean);
	}

	public void common(ConfBean confBean, UserBean userBean, String archive, String archiveLookup, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		workFlowBean.setArchiveLookup(serviceUser.getArchive(userBean, archiveLookup));
		common(confBean, userBean, archive, modelMap, request, response);
	}

	@RequestMapping(value = { "/home", "/" }, method = RequestMethod.GET)
	public String home(Model model, @ModelAttribute("userBean") UserBean userBean) throws Exception {
		Map<String, List<Archive>> usersArchives = new LinkedHashMap<String, List<Archive>>();
		serviceUser.loadArchives(userBean, usersArchives);
		model.addAttribute("usersArchives", usersArchives);
		return "user/workspace";
	}

	@RequestMapping(value = "/search/{archive}/query", method = RequestMethod.GET)
	public String queryPage(@ModelAttribute("userBean") UserBean userBean, @ModelAttribute("confBean") ConfBean confBean, @PathVariable String archive, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		common(confBean, userBean, archive, modelMap, request, response);
		QueryPageCommand queryPageCommand = new QueryPageCommand(request.getParameterMap(), modelMap);
		queryPageCommand.execute();
		QueryPageView pageView = new QueryPageView();
		pageView.generateView(workFlowBean, confBean, userBean, modelMap);
		modelMap.put("positionMap", pageView.getPositionMap());
		modelMap.put("positionAdminMap", pageView.getPositionAdminMap());
		modelMap.put("outputHourField", pageView.getOutputHourField());
		modelMap.put("outputDataField", pageView.getOutputDataField());
		modelMap.put("outputSortField", pageView.getOutputSortField());
		return "search/query";
	}

	@RequestMapping(value = "/search/{archive}/vocabulary", method = RequestMethod.GET)
	public String vocabularyPage(@ModelAttribute("userBean") UserBean userBean, @ModelAttribute("confBean") ConfBean confBean, @PathVariable String archive, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		common(confBean, userBean, archive, modelMap, request, response);
		VocabolarioMultiCommand vocabolarioMultiCommand = new VocabolarioMultiCommand(request.getParameterMap(), modelMap);
		vocabolarioMultiCommand.execute();
		if (MyRequest.getParameter("isExport", request.getParameterMap()).equalsIgnoreCase("true")) {
			return "search/vocabularyTxt";
		}
		return "search/vocabulary";
	}

	@RequestMapping(value = "/search/{archive}/title")
	public String titlePage(@ModelAttribute("userBean") UserBean userBean, @ModelAttribute("confBean") ConfBean confBean, @PathVariable String archive, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		common(confBean, userBean, archive, modelMap, request, response);
		TitlePageCommand titlePageCommand = new TitlePageCommand(request.getParameterMap(), modelMap);
		titlePageCommand.execute();
		return "search/title";
	}

	@RequestMapping(value = "/hier/{archive}/{pageName}", method = RequestMethod.GET)
	public String hierPage(@ModelAttribute("userBean") UserBean userBean, @ModelAttribute("confBean") ConfBean confBean, @PathVariable String archive, ModelMap modelMap, @PathVariable String pageName, HttpServletRequest request, HttpServletResponse response) throws Exception {
		modelMap.put("pageName", pageName);
		common(confBean, userBean, archive, modelMap, request, response);
		HierBrowserPageCommand hierBrowserPageCommand = new HierBrowserPageCommand(request.getParameterMap(), modelMap);
		hierBrowserPageCommand.execute();

		System.out.println("xDamsController.queryPage() pageName: " + pageName);
		// System.out.println("xDamsController.queryPage() workFlowBean: " + workFlowBean);
		return "hier/" + pageName;
	}

	@RequestMapping(value = "/viewTab/{archive}/{pageName}", method = RequestMethod.GET)
	public String viewTab(@ModelAttribute("userBean") UserBean userBean, @ModelAttribute("confBean") ConfBean confBean, @PathVariable String archive, ModelMap modelMap, @PathVariable String pageName, HttpServletRequest request, HttpServletResponse response) throws Exception {
		modelMap.put("pageName", pageName);
		common(confBean, userBean, archive, modelMap, request, response);
		ViewPageCommand viewPageCommand = new ViewPageCommand(request.getParameterMap(), modelMap);
		viewPageCommand.execute();
		Map<String, String> mapParams = new TreeMap<String, String>();
		mapParams.put("viewTab", pageName);
		mapParams.put("frontUrl", (String) modelMap.get("frontUrl"));
		try {
			mapParams.put("mediaURL", this.getClass().getClassLoader().getResource(confBean.getMediaPath()).getPath());
		} catch (Exception e) {
			mapParams.put("mediaURL", "");
		}
		modelMap.put("mapParams", mapParams);
		// System.out.println("xDamsController.queryPage() workFlowBean: " + workFlowBean);
		return "viewTab/" + pageName;
	}

	@RequestMapping(value = "/infoTab/{archive}/{pageName}", method = RequestMethod.GET)
	public String infoTab(@ModelAttribute("userBean") UserBean userBean, @ModelAttribute("confBean") ConfBean confBean, @PathVariable String archive, ModelMap modelMap, @PathVariable String pageName, HttpServletRequest request, HttpServletResponse response) throws Exception {
		modelMap.put("pageName", pageName);
		common(confBean, userBean, archive, modelMap, request, response);
		InfoTabCommand infoTabCommand = new InfoTabCommand(request.getParameterMap(), modelMap);
		infoTabCommand.execute();
		Map<String, String> mapParams = new TreeMap<String, String>();
		mapParams.put("viewTab", pageName);
		mapParams.put("frontUrl", (String) modelMap.get("frontUrl"));
		try {
			mapParams.put("mediaURL", this.getClass().getClassLoader().getResource(confBean.getMediaPath()).getPath());
		} catch (Exception e) {
			mapParams.put("mediaURL", "");
		}

		modelMap.put("mapParams", mapParams);
		// System.out.println("xDamsController.queryPage() workFlowBean: " + workFlowBean);
		return "viewTab/" + pageName;
	}

	@RequestMapping(value = "/{archive}/managing")
	public String managing(@ModelAttribute("userBean") UserBean userBean, @ModelAttribute("confBean") ConfBean confBean, @PathVariable String archive, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		common(confBean, userBean, archive, modelMap, request, response);
		ManagingFactory managingFactory = new ManagingFactory(request.getParameterMap(), modelMap);
		ManagingBean managingBean = managingFactory.execute();
		System.out.println("xDamsController.managing() " + managingBean);
		return "managing/" + managingBean.getDispatchView();
	}

	@RequestMapping(value = "/{archive}/pdfView")
	public void pdfView(@ModelAttribute("userBean") UserBean userBean, @ModelAttribute("confBean") ConfBean confBean, @PathVariable String archive, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		common(confBean, userBean, archive, modelMap, request, response);
		ManagingFactory managingFactory = new ManagingFactory(request.getParameterMap(), modelMap);
		ManagingBean managingBean = managingFactory.execute();
		// return "managing/" + managingBean.getDispatchView();
	}

	@RequestMapping(value = "/editing/{archive}/docEdit")
	public String editingPage(@ModelAttribute("userBean") UserBean userBean, @ModelAttribute("confBean") ConfBean confBean, @PathVariable String archive, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		common(confBean, userBean, archive, modelMap, request, response);
		EditingPageCommand editingPageCommand = new EditingPageCommand(request.getParameterMap(), modelMap);
		editingPageCommand.execute();
		if ((MyRequest.getParameter("go", request.getParameterMap()).toLowerCase()).indexOf("saveandclose") != -1) {
			return "editing/doSaveAndClose";
		} else {
			return "editing/docEdit";
		}
	}

	@RequestMapping(value = "/editing/{archive}/preInsert")
	public String preInsertPage(@ModelAttribute("userBean") UserBean userBean, @ModelAttribute("confBean") ConfBean confBean, @PathVariable String archive, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		common(confBean, userBean, archive, modelMap, request, response);
		PreInsertPageCommand preInsertPageCommand = new PreInsertPageCommand(request.getParameterMap(), modelMap);
		preInsertPageCommand.execute();
		return "editing/preInsert";
	}

	@RequestMapping(value = "/editing/{archive}/{srcArchive}/preInsert")
	public String preInsertPageLookup(@ModelAttribute("userBean") UserBean userBean, @ModelAttribute("confBean") ConfBean confBean, @PathVariable String archive, @PathVariable String srcArchive, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		common(confBean, userBean, archive, modelMap, request, response);
		modelMap.addAttribute("srcArchive", srcArchive);
		PreInsertPageCommand preInsertPageCommand = new PreInsertPageCommand(request.getParameterMap(), modelMap);
		preInsertPageCommand.execute();
		return "editing/preInsert";
	}

	@RequestMapping(value = "/editing/{archive}/insert")
	public String insertPage(@ModelAttribute("userBean") UserBean userBean, @ModelAttribute("confBean") ConfBean confBean, @PathVariable String archive, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		common(confBean, userBean, archive, modelMap, request, response);
		InsertRecordCommand insertRecordCommand = new InsertRecordCommand(request.getParameterMap(), modelMap);
		insertRecordCommand.execute();
		if ((MyRequest.getParameter("go", request.getParameterMap()).toLowerCase()).indexOf("insertandclose") != -1) {
			return "editing/doSaveAndClose";
		} else if ((MyRequest.getParameter("go", request.getParameterMap()).toLowerCase()).indexOf("insertandmod") != -1) {
			return editingPage(userBean, confBean, archive, modelMap, request, response);
		}
		// if ((go.toLowerCase()).indexOf("insertandclose") != -1) {
		// go = "/public/application/jsp/" + "doSaveAndClose.jsp";
		// } else if ((go.toLowerCase()).indexOf("insertandmod") != -1) {
		// go = "/public/application/jsp/" + "docEdit.jsp";
		// EditingPageCommand editingPageCommand = new EditingPageCommand(req, getServletContext());
		// editingPageCommand.execute();
		// }
		return "editing/preInsert";
	}

	@RequestMapping(value = "/save/{archive}/document")
	public String saveDocument(@ModelAttribute("userBean") UserBean userBean, @ModelAttribute("confBean") ConfBean confBean, @PathVariable String archive, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		common(confBean, userBean, archive, modelMap, request, response);
		SaveDocumentCommand saveDocumentCommand = new SaveDocumentCommand(request.getParameterMap(), modelMap);
		saveDocumentCommand.execute();
		if ((MyRequest.getParameter("go", request.getParameterMap()).toLowerCase()).indexOf("saveandclose") != -1) {
			return editingPage(userBean, confBean, archive, modelMap, request, response);
		} else {
			return viewTab(userBean, confBean, archive, modelMap, MyRequest.getParameter("goServlet", request.getParameterMap()), request, response);
		}
	}

	@RequestMapping(value = "/lookup/{archive}/{archiveLookup}")
	public String lookup(@ModelAttribute("userBean") UserBean userBean, @ModelAttribute("confBean") ConfBean confBean, @PathVariable String archive, @PathVariable String archiveLookup, @ModelAttribute("lookupBean") LookupBean lookupBean, ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		common(confBean, userBean, archive, archiveLookup, modelMap, request, response);
		LookupCommand lookup = new LookupCommand(request.getParameterMap(), modelMap);
		lookup.execute();
		if (!lookupBean.getJspOutPut().equals("")) {
			return "lookup/" + StringUtils.remove(lookupBean.getJspOutPut(), ".jsp");
		} else {
			return "lookup/lookup";
		}
	}

	@RequestMapping(value = "/{archive}/ajax", method = RequestMethod.GET)
	public ResponseEntity<String> ajaxCall(@PathVariable String archive, @ModelAttribute("userBean") UserBean userBean, @ModelAttribute("workFlowBean") WorkFlowBean workFlowBean, @ModelAttribute("confBean") ConfBean confBean, ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		common(confBean, userBean, archive, modelMap, request, response);

		AjaxFactory ajaxFactory = new AjaxFactory(request, response, modelMap);
		AjaxBean ajaxBean = new AjaxBean();
		try {
			ajaxBean = ajaxFactory.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", ajaxBean.getContentType().equals("") ? "text/xml; charset=iso-8859-1" : ajaxBean.getContentType());
		System.out.println("ajaxBean.getStrXmlOutput() " + ajaxBean.getStrXmlOutput());
		System.out.println("ajaxBean.getContentType() " + ajaxBean.getContentType());
		return new ResponseEntity<String>(ajaxBean.getStrXmlOutput(), responseHeaders, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/upload/{archive}/uploadMenu", method = RequestMethod.GET)
	public String openFormUpload(@ModelAttribute("uploadBean") UploadBean uploadBean, @ModelAttribute("userBean") UserBean userBean, @ModelAttribute("confBean") ConfBean confBean, @PathVariable String archive, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		common(confBean, userBean, archive, modelMap, request, response);
		System.out.println("request.getParameterMap() " + request.getParameterMap());
		modelMap.addAttribute("uploadBean", uploadBean);
		return "upload/uploadMenu";
	}

	@RequestMapping(value = "/upload/{archive}/execute", method = RequestMethod.POST)
	public String executeUpload(UploadBean uploadBean, BindingResult result, @PathVariable String archive, ModelMap modelMap, @ModelAttribute("userBean") UserBean userBean, @ModelAttribute("confBean") ConfBean confBean, HttpServletRequest request, HttpServletResponse response) throws Exception {
		common(confBean, userBean, archive, modelMap, request, response);
		if (result.hasErrors()) {
			for (ObjectError error : result.getAllErrors()) {
				System.err.println("Error in uploading: " + error.getCode() + " - " + error.getDefaultMessage());
			}
			return "upload/uploadMenu";
		}
		modelMap.put("realPath", WebUtils.getRealPath(servletContext, ""));
		UploadCommand uploadCommand = new UploadCommand(request.getParameterMap(), modelMap);
		uploadCommand.execute();
		modelMap.put("uploadResponse", uploadBean);

		System.err.println("----------------uploadbean---------------------------" + uploadBean);
		System.err.println("Test uploading file: " + uploadBean.getFiledata().getFileItem().getContentType());
		System.err.println("-------------------------------------------");
		return "upload/uploadResult";
	}

	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public String login(Model model) {
		return "login";
	}

	@RequestMapping(value = { "/test" }, method = RequestMethod.GET)
	public String test(@ModelAttribute("userBean") UserBean userBean, @ModelAttribute("confBean") ConfBean confBean, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		common(confBean, userBean, null, modelMap, request, response);
		return "test";
	}

	@RequestMapping(value = { "/generatePassword" }, method = RequestMethod.GET)
	public ResponseEntity<String> generatePassword(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", MediaType.TEXT_PLAIN.toString());
		return new ResponseEntity<String>(new Md5PasswordEncoder().encodePassword(request.getParameter("passwordField"), null), responseHeaders, HttpStatus.CREATED);
	}

}
