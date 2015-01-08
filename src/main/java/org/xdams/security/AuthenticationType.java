package org.xdams.security;

import org.xdams.user.bean.Archive;

public class AuthenticationType {
	private boolean loadUserSpeedUp = false;
	
	private boolean allowAutoLogin = false;

	private String loadUserType;

	private Archive archiveXWAY;

	public boolean isLoadUserSpeedUp() {
		return loadUserSpeedUp;
	}

	public void setLoadUserSpeedUp(boolean loadUserSpeedUp) {
		this.loadUserSpeedUp = loadUserSpeedUp;
	}

	public String getLoadUserType() {
		return loadUserType;
	}

	public void setLoadUserType(String loadUserType) {
		this.loadUserType = loadUserType;
	}

	public Archive getArchiveXWAY() {
		return archiveXWAY;
	}

	public void setArchiveXWAY(Archive archiveXWAY) {
		this.archiveXWAY = archiveXWAY;
	}

	public boolean isAllowAutoLogin() {
		return allowAutoLogin;
	}

	public void setAllowAutoLogin(boolean allowAutoLogin) {
		this.allowAutoLogin = allowAutoLogin;
	}
}
