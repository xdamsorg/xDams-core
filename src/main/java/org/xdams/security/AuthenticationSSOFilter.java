package org.xdams.security;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.TextEscapeUtils;
import org.xdams.utility.StringsUtils;


public class AuthenticationSSOFilter extends UsernamePasswordAuthenticationFilter {
	private String SSO_verificaToken_URL = "";
	private String SSO_verificaToken_URL_params = "";
	private String SSO_regSession_URL = "";
	private String SSO_regSession_URL_params = "";
	private String SSO_userInfo_URL = "";
	private String SSO_userInfo_URL_params = "";
	private String SSO_userParam = "";

	@Override
	public Authentication attemptAuthentication(HttpServletRequest arg0, HttpServletResponse arg1) throws AuthenticationException {
		String username = arg0.getHeader(SSO_userParam);
		String errorMsg = "";
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><username = " + username);
		if (username == null) {
			errorMsg = "A Utente disconnesso. <a href=\"javascript:self.close()\">[CHIUDERE]</a> la finestra per tornare al Portale.";
			throw new AuthenticationServiceException(errorMsg);
		}
		System.out.println("AuthenticationSSOFilter.attemptAuthentication() 1");
		username = username.trim();
		String key = arg0.getParameter("key");
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><key = " + key);
		if (key == null) {
			errorMsg = "B Utente disconnesso. <a href=\"javascript:self.close()\">[CHIUDERE]</a> la finestra per tornare al Portale.";
			throw new AuthenticationServiceException(errorMsg);
		}
		System.out.println("AuthenticationSSOFilter.attemptAuthentication() 2");
		String verificaUrl;
		try {
			verificaUrl = StringsUtils.postForString(new URL(SSO_verificaToken_URL), "key=" + key + "&" + SSO_verificaToken_URL_params);
			if (verificaUrl.indexOf("KO") != -1) {
				errorMsg = "Si è verificato un errore nella verifica dell'utente (keytest)";
				throw new AuthenticationServiceException(errorMsg);
			}
			arg0.getSession().setAttribute("alfresco_token", key);
			System.out.println("keykeykeykeykeykeykeykeykeykey "+key);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("AuthenticationSSOFilter.attemptAuthentication() 3");
		try {
			String regSessUrl = StringsUtils.postForString(new URL(SSO_regSession_URL), "key=" + key + "&SESSIONAPL=" + URLEncoder.encode("JSESSIONID=", "utf-8") + arg0.getSession().getId() + "&" + SSO_regSession_URL_params);
			if (regSessUrl.indexOf("KO") != -1) {
				errorMsg = "Si è verificato un errore nella verifica dell'utente (regsess)";
				throw new AuthenticationServiceException(errorMsg);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("AuthenticationSSOFilter.attemptAuthentication() 4");
		AuthenticationSSOToken authenticationSSOToken = new AuthenticationSSOToken(username, "");
		HttpSession session = arg0.getSession(false);
		if (session != null || getAllowSessionCreation()) {
			arg0.getSession().setAttribute(SPRING_SECURITY_LAST_USERNAME_KEY, TextEscapeUtils.escapeEntities(username));
		}
		System.out.println("AuthenticationSSOFilter.attemptAuthentication() 5");
		setDetails(arg0, authenticationSSOToken);
		System.out.println("AuthenticationSSOFilter.attemptAuthentication() 6");
		return this.getAuthenticationManager().authenticate(authenticationSSOToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException, ServletException {
		super.successfulAuthentication(request, response, authResult);

	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
		super.unsuccessfulAuthentication(request, response, failed);
	}

	public String getSSO_verificaToken_URL() {
		return SSO_verificaToken_URL;
	}

	public String getSSO_verificaToken_URL_params() {
		return SSO_verificaToken_URL_params;
	}

	public String getSSO_regSession_URL() {
		return SSO_regSession_URL;
	}

	public String getSSO_regSession_URL_params() {
		return SSO_regSession_URL_params;
	}

	public String getSSO_userInfo_URL() {
		return SSO_userInfo_URL;
	}

	public String getSSO_userInfo_URL_params() {
		return SSO_userInfo_URL_params;
	}

	public void setSSO_verificaToken_URL(String sSOVerificaTokenURL) {
		SSO_verificaToken_URL = sSOVerificaTokenURL;
	}

	public void setSSO_verificaToken_URL_params(String sSOVerificaTokenURLParams) {
		SSO_verificaToken_URL_params = sSOVerificaTokenURLParams;
	}

	public void setSSO_regSession_URL(String sSORegSessionURL) {
		SSO_regSession_URL = sSORegSessionURL;
	}

	public void setSSO_regSession_URL_params(String sSORegSessionURLParams) {
		SSO_regSession_URL_params = sSORegSessionURLParams;
	}

	public void setSSO_userInfo_URL(String sSOUserInfoURL) {
		SSO_userInfo_URL = sSOUserInfoURL;
	}

	public void setSSO_userInfo_URL_params(String sSOUserInfoURLParams) {
		SSO_userInfo_URL_params = sSOUserInfoURLParams;
	}

	public String getSSO_userParam() {
		return SSO_userParam;
	}

	public void setSSO_userParam(String sSOUserParam) {
		SSO_userParam = sSOUserParam;
	}
}
