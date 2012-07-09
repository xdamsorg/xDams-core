package org.xdams.workflow.bean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.xdams.user.bean.Archive;

/**
 * @author simoneAdm WorkFlowBean è delegato alla navigazione dell'utente Al suo interno sono presenti gli archivi su cui sta lavorando l'utente
 * 
 */
public class WorkFlowBean {

	private Archive archive = null;

	private Archive archiveLookup = null;

	private HttpServletRequest request = null;

	private HttpServletResponse response = null;

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

	//da trasferire
	public String getManagingBeanName() {
		return "managingBean" + getAlias();
	}
	
	//da trasferire	
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

}
