package org.xdams.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.TextEscapeUtils;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	public static final String SQLXDAMS_SECURITY_FORM_COMPANY_KEY = "j_company";
	private String companyParameter = SQLXDAMS_SECURITY_FORM_COMPANY_KEY;
	@Override
	public Authentication attemptAuthentication(HttpServletRequest arg0,HttpServletResponse arg1) throws AuthenticationException {
		if (!arg0.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + arg0.getMethod());
		}
		String username = obtainUsername(arg0);
		String password = obtainPassword(arg0);
		String company  = obtainCompany(arg0);
		if (username == null) {
			username = "";
		}
		if (password == null) {
			password = "";
		}
		if (company == null) {
			company = "";
		}
		username = username.trim();
		AuthenticationToken authenticationToken = new AuthenticationToken(username, password, company);
		HttpSession session = arg0.getSession(false);
		if (session != null || getAllowSessionCreation()) {
			arg0.getSession().setAttribute(SPRING_SECURITY_LAST_USERNAME_KEY,TextEscapeUtils.escapeEntities(username));
		}
		setDetails(arg0, authenticationToken);
		return this.getAuthenticationManager().authenticate(authenticationToken);
	}
	@Override
	 protected void successfulAuthentication(HttpServletRequest request,HttpServletResponse response, Authentication authResult)throws IOException, ServletException {
	     super.successfulAuthentication(request, response, authResult);

	 }

	 @Override
	 protected void unsuccessfulAuthentication(HttpServletRequest request,HttpServletResponse response, AuthenticationException failed)throws IOException, ServletException {
	     super.unsuccessfulAuthentication(request, response, failed);
	 }
	 protected String obtainCompany(HttpServletRequest request) {
         return request.getParameter(companyParameter);
     }

}
