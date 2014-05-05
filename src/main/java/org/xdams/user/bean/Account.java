package org.xdams.user.bean;


public class Account {

	private String descrAccount = null;

	private String id = null;
	
	private String fatherAccount = null;

	public String getDescrAccount() {
		return descrAccount;
	}

	public void setDescrAccount(String descrAccount) {
		this.descrAccount = descrAccount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Account [descrAccount=" + descrAccount + ", id=" + id + ", fatherAccount=" + fatherAccount + "]";
	}

	public String getFatherAccount() {
		return fatherAccount;
	}

	public void setFatherAccount(String fatherAccount) {
		this.fatherAccount = fatherAccount;
	}

}
