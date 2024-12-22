package org.xdams.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.TextEscapeUtils;

public class AuthenticationSSOFilter extends UsernamePasswordAuthenticationFilter {

	public static final String SQLXDAMS_SECURITY_FORM_COMPANY_KEY = "j_company";

	private String companyParameter = SQLXDAMS_SECURITY_FORM_COMPANY_KEY;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest arg0, HttpServletResponse arg1) throws AuthenticationException {
		if (!arg0.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + arg0.getMethod());
		}
		String username = obtainUsername(arg0);
		String password = obtainPassword(arg0);
		//		String company = obtainCompany(arg0);

		//		System.out.println("AuthenticationFilter.attemptAuthentication() username: "+username);
		//		System.out.println("AuthenticationFilter.attemptAuthentication() password: " + password);
		//		System.out.println("AuthenticationFilter.attemptAuthentication() company: "+company);

		if (username == null) {
			username = "";
		}
		if (password == null) {
			password = "";
		}

		username = username.trim();

		AuthenticationToken authenticationToken = new AuthenticationToken(username, password);
		HttpSession session = arg0.getSession(false);
		if (session != null || getAllowSessionCreation()) {
			arg0.getSession().setAttribute(SPRING_SECURITY_FORM_USERNAME_KEY, TextEscapeUtils.escapeEntities(username));
		}
		setDetails(arg0, authenticationToken);
		return this.getAuthenticationManager().authenticate(authenticationToken);
	}

	public Authentication attemptAuthentication(HttpServletRequest arg0, String username, String password) throws AuthenticationException {

		username = username.trim();
		AuthenticationToken authenticationToken = new AuthenticationToken(username, password);
		HttpSession session = arg0.getSession(false);
		if (session != null || getAllowSessionCreation()) {
			arg0.getSession().setAttribute(SPRING_SECURITY_FORM_USERNAME_KEY, TextEscapeUtils.escapeEntities(username));
		}
		setDetails(arg0, authenticationToken);
		return this.getAuthenticationManager().authenticate(authenticationToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
//		System.out.println("AuthenticationFilter.successfulAuthentication()");
//		System.out.println("AuthenticationFilter.successfulAuthentication()");
//		System.out.println("AuthenticationFilter.successfulAuthentication()" + messages);
//		System.out.println("AuthenticationFilter.successfulAuthentication()");
//		System.out.println("AuthenticationFilter.successfulAuthentication()");
//		System.out.println("AuthenticationFilter.successfulAuthentication()");

		super.successfulAuthentication(request, response, chain, authResult);

	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
//		System.out.println("AuthenticationFilter.unsuccessfulAuthentication()");
//		System.out.println("AuthenticationFilter.unsuccessfulAuthentication()");
//		System.out.println("AuthenticationFilter.unsuccessfulAuthentication()");
//		System.out.println("AuthenticationFilter.unsuccessfulAuthentication()" + failed);
//		System.out.println("AuthenticationFilter.unsuccessfulAuthentication()" + request.getRemoteAddr());
//		System.out.println("AuthenticationFilter.unsuccessfulAuthentication()" + request.getParameter("username"));
//		System.out.println("AuthenticationFilter.unsuccessfulAuthentication()");
//		System.out.println("AuthenticationFilter.unsuccessfulAuthentication()");

		//        String username = (String) event.getAuthentication().getPrincipal();
		super.unsuccessfulAuthentication(request, response, failed);
	}

	protected String obtainCompany(HttpServletRequest request) {
		return request.getParameter(companyParameter);
	}

}
