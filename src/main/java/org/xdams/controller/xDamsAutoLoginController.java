package org.xdams.controller;

import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.xdams.security.AuthenticationFilter;
import org.xdams.security.AuthenticationType;
import org.xdams.security.UserDetails;
import org.xdams.security.load.LoadUserManager;
import org.xdams.user.access.ServiceUser;
import org.xdams.user.bean.Archive;
import org.xdams.user.bean.UserBean;

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
	public String signup(@PathVariable String account, @PathVariable String userGuest, HttpServletRequest request, HttpServletResponse response) {
		if (authenticationType.isAllowAutoLogin()) {
			authenticateUserAndSetSession(account, userGuest, request);
		}
		return "redirect:/home";
	}

	@RequestMapping(value = "/autologin/{account}/{userGuest}/{archive}/signup", method = RequestMethod.GET)
	public String signupQuery(@PathVariable String account, @PathVariable String userGuest, @PathVariable String archive, HttpServletRequest request, HttpServletResponse response) {
		if (authenticationType.isAllowAutoLogin()) {
			authenticateUserAndSetSession(account, userGuest, request);
		}
		UserDetails userDetails = null;
		try {
			userDetails = (UserDetails) ((SecurityContext) SecurityContextHolder.getContext()).getAuthentication().getPrincipal();
			UserBean userBean = LoadUserManager.executeLoad(userDetails, authenticationType);
			Archive a = ServiceUser.getArchive(userBean, archive);
		} catch (Exception e) {
			SecurityContextHolder.getContext().setAuthentication(null);
			return "redirect:/login";
		}
		return "redirect:/search/" + archive + "/query.html";
	}

	private void authenticateUserAndSetSession(String account, String userGuest, HttpServletRequest request) {
		Authentication authenticatedUser = formLoginFilter.attemptAuthentication(request, userGuest, userGuest, account);
		SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
	}

}
