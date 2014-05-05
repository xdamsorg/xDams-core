package org.xdams.user.bean;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UserBean {

	private boolean active = true;

	private String name = null;

	private String lastName = null;

	private String id = null;

	private String accountRef = null;

	private String fatherAccountRef = null;

	private String pwd = null;

	private String email = null;

	private String language = null;

	private String role = null;

	private Account account = null;

	private List<Archive> archives = new ArrayList<Archive>();

	private Map<String, Archive> archivesMap = new LinkedHashMap<String, Archive>();

	// private Archive workArchive = null;

	public Map<String, Archive> getArchivesMap() {
		return archivesMap;
	}

	public void setArchivesMap(Map<String, Archive> archivesMap) {
		this.archivesMap = archivesMap;
	}

	// public Archive getWorkArchive() {
	// return workArchive;
	// }
	//
	// public void setWorkArchive(Archive workArchive) {
	// this.workArchive = workArchive;
	// }

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<Archive> getArchives() {
		return archives;
	}

	public void setArchives(List<Archive> archives) {
		this.archives = archives;
	}

	public void addArchives(Archive archive) {
		archives.add(archive);
	}

	public void putArchives(String key, Archive archive) {
		archivesMap.put(key, archive);
	}

	public String getAccountRef() {
		return accountRef;
	}

	public void setAccountRef(String accountRef) {
		this.accountRef = accountRef;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@Override
	public String toString() {
		return "UserBean [active=" + active + ", name=" + name + ", lastName=" + lastName + ", id=" + id + ", accountRef=" + accountRef + ", fatherAccountRef=" + fatherAccountRef + ", pwd=" + pwd + ", email=" + email + ", language=" + language + ", role=" + role + ", account=" + account
				+ ", archives=" + archives + ", archivesMap=" + archivesMap + "]";
	}

	public String getFatherAccountRef() {
		return fatherAccountRef;
	}

	public void setFatherAccountRef(String fatherAccountRef) {
		this.fatherAccountRef = fatherAccountRef;
	}

}
