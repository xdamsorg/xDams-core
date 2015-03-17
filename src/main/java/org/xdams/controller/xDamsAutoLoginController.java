package org.xdams.controller;

import it.highwaytech.db.QueryResult;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.util.TextEscapeUtils;
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
import org.xdams.security.AuthenticationFilter;
import org.xdams.security.AuthenticationToken;
import org.xdams.security.AuthenticationType;
import org.xdams.security.UserDetails;
import org.xdams.security.load.LoadUserManager;
import org.xdams.user.access.ServiceUser;
import org.xdams.user.bean.Archive;
import org.xdams.user.bean.UserBean;
import org.xdams.utility.XMLCleaner;
import org.xdams.utility.request.MyRequest;
import org.xdams.workflow.bean.WorkFlowBean;
import org.xdams.xml.builder.XMLBuilder;
import org.xdams.xmlengine.connection.manager.ConnectionManager;
import org.xdams.xw.XWConnection;

@Controller
@SessionAttributes({ "userBean" })
@SuppressWarnings("unchecked")
public class xDamsAutoLoginController {

	private static final Logger logger = LoggerFactory.getLogger(xDamsAutoLoginController.class);

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
	AuthenticationFilter formLoginFilter;

	@RequestMapping(value = "/autologin/{account}/{userGuest}/signup", method = RequestMethod.GET)
	public String createNewUser(@PathVariable String account, @PathVariable String userGuest, HttpServletRequest request, HttpServletResponse response) {
		if (authenticationType.isAllowAutoLogin()) {
			authenticateUserAndSetSession(account, userGuest, request);
		}
		return "redirect:/home";
	}

	private void authenticateUserAndSetSession(String account, String userGuest, HttpServletRequest request) {
		Authentication authenticatedUser = formLoginFilter.attemptAuthentication(request, userGuest, userGuest, account);
		SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
	}

}
