package org.xdams.page.upload.bean;

public class UploadCommandLine {
	String commandLine;

	String uploadTempPath;

	String uploadMode;

	String uploadPath;
	
	String uploadNameDir;

	public String getCommandLine() {
		return commandLine;
	}

	public void setCommandLine(String commandLine) {
		this.commandLine = commandLine;
	}

	public String getUploadTempPath() {
		return (uploadTempPath != null) ? uploadTempPath.replace("\\", System.getProperty("file.separator")).replace("/", System.getProperty("file.separator")) : null;
	}

	public void setUploadTempPath(String uploadTempPath) {
		this.uploadTempPath = uploadTempPath;
	}

	public String getUploadMode() {
		return uploadMode;
	}

	public void setUploadMode(String uploadMode) {
		this.uploadMode = uploadMode;
	}

	public String getUploadPath() {
		return (uploadPath != null) ? uploadPath.replace("\\", System.getProperty("file.separator")).replace("/", System.getProperty("file.separator")) : null;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	@Override
	public String toString() {
		return "UploadCommandLine [commandLine=" + commandLine + ", uploadTempPath=" + uploadTempPath + ", uploadMode=" + uploadMode + ", uploadPath=" + uploadPath + "]";
	}

	public String getUploadNameDir() {
		return (uploadNameDir != null) ? uploadNameDir.replace("\\", System.getProperty("file.separator")).replace("/", System.getProperty("file.separator")) : null;
	}

	public void setUploadNameDir(String uploadNameDir) {
		this.uploadNameDir = uploadNameDir;
	}
}
