package org.xdams.user.bean;

public class Archive {

	private String archiveDescr = null;

	private String alias = null;

	private String host = null;

	private String ico = null;

	private String pne = null;

	private String port = null;

	private String webapp = null;

	private String role = null;

	private String groupName = null;

	private String type = null;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getArchiveDescr() {
		return archiveDescr;
	}

	public void setArchiveDescr(String archiveDescr) {
		this.archiveDescr = archiveDescr;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getIco() {
		return ico;
	}

	public void setIco(String ico) {
		this.ico = ico;
	}

	public String getPne() {
		return pne;
	}

	public void setPne(String pne) {
		this.pne = pne;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getWebapp() {
		return webapp;
	}

	public void setWebapp(String webapp) {
		this.webapp = webapp;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Archive [archiveDescr=" + archiveDescr + ", alias=" + alias + ", host=" + host + ", ico=" + ico + ", pne=" + pne + ", port=" + port + ", webapp=" + webapp + ", role=" + role + ", groupName=" + groupName + ", type=" + type + "]";
	}

}
