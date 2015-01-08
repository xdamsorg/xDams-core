package org.xdams.workflow.bean;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.WebUtils;
import org.xdams.user.bean.Archive;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author simoneAdm WorkFlowBean è delegato alla navigazione dell'utente Al suo interno sono presenti gli archivi su cui sta lavorando l'utente
 * 
 */
public class WorkFlowBean {

	private Archive archive = null;

	private Archive archiveLookup = null;

	private HttpServletRequest request = null;

	private HttpServletResponse response = null;

	private ApplicationContext applicationContext = null;

	public Archive getArchive() {
		return archive;
	}

	public void setArchive(Archive archive) {
		this.archive = archive;
	}

	public Archive getArchiveLookup() {
		return archiveLookup;
	}

	public void setArchiveLookup(Archive archiveLookup) {
		this.archiveLookup = archiveLookup;
	}

	public String getAlias() {
		return archive.getAlias();
	}

	// da trasferire
	public String getManagingBeanName() {
		return "managingBean" + getAlias();
	}

	// da trasferire
	public String getQueryBeanName() {
		return "arrQueryBean" + getAlias();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WorkFlowBean [archive=" + archive + ", archiveLookup=" + archiveLookup + "]";
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public String getLocalizedString(String key, String defaultKey) {
		String value = "";
		try {
			Locale locale = RequestContextUtils.getLocale(request);
			value = applicationContext.getMessage(key, null, defaultKey, locale);
		} catch (Exception e) {
			value = defaultKey;
		}
		return value;
	}

	public String getGlobalLangOption() {
		String globalLangOption = "";
		try {
			Locale locale = RequestContextUtils.getLocale(request);
			ResourceBundle bundle = ResourceBundle.getBundle("xdams_messages", locale);
			Map<String, String> bundleMap = new LinkedHashMap<String, String>();
			for (String key : bundle.keySet()) {
				String value = bundle.getString(key);
				bundleMap.put(key, value);
			}
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(bundleMap);
			globalLangOption = "var globalLangOption = jQuery.parseJSON('" + json+"')";
		} catch (Exception e) {
			
		}
		return globalLangOption;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
}
