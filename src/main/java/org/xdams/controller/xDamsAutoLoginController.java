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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.xdams.security.AuthenticationFilter;
import org.xdams.security.AuthenticationType;
import org.xdams.user.access.ServiceUser;

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
