package org.xdams.page.view.bean;

public class MailBean {
	private String host = "";
	private String port = "";
	private String auth = "";
	private String user = "";
	private String pass = "";
	private String to = "";
	private String subject = "";
	private String from = "";
	private String mimeContent = "";
	
	
	public String getMimeContent() {
		return mimeContent;
	}
	public void setMimeContent(String mimeContent) {
		this.mimeContent = mimeContent;
	}
	public String getHost() {
		return host;
	}
	public String getPort() {
		return port;
	}
	public String getAuth() {
		return auth;
	}
	public String getUser() {
		return user;
	}
	public String getPass() {
		return pass;
	}
	public String getTo() {
		return to;
	}
	public String getSubject() {
		return subject;
	}
	public String getFrom() {
		return from;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public void setFrom(String from) {
		this.from = from;
	}
}
