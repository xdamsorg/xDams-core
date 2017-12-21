package org.xdams.admin.bean;


public class CreateArchiveResultBean {
	boolean created;

	String msg;

	String alias;
	
	String xDamsType;

	public boolean isCreated() {
		return created;
	}

	public void setCreated(boolean created) {
		this.created = created;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getxDamsType() {
		return xDamsType;
	}

	public void setxDamsType(String xDamsType) {
		this.xDamsType = xDamsType;
	}

	@Override
	public String toString() {
		return "CreateArchiveResultBean [created=" + created + ", msg=" + msg + ", alias=" + alias + ", xDamsType=" + xDamsType + "]";
	}
}
