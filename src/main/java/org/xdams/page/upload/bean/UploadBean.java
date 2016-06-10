package org.xdams.page.upload.bean;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class UploadBean {
	private CommonsMultipartFile filedata;

	private String name;

	private String idRecord;

	private int physDoc;

	private String destField;

	private String uploadName;

	private String uploadType;

	private String renameFile;

	private String renameDir;

	private String compositionRuleFile;

	private String compositionRuleDir;

	private String compositionReplaceName;

	private String associatePathDir;

	private boolean overWrite = false;

	private boolean fileExist = false;

	private List<UploadCommandLine> commandLine = new ArrayList<UploadCommandLine>();

	private StringBuilder result = new StringBuilder();

	@Deprecated
	private StringBuilder resultOriginalFileName = new StringBuilder();

	String flagOriginalFileName;

	String destOriginalFileName;

	String xPathPrefix;

	private StringBuilder resultError = new StringBuilder();

	public CommonsMultipartFile getFiledata() {
		return filedata;
	}

	public void setFiledata(CommonsMultipartFile filedata) {
		this.filedata = filedata;
	}

	public String getName() {
		return getFiledata().getOriginalFilename();
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUploadName() {
		return uploadName;
	}

	public void setUploadName(String uploadName) {
		this.uploadName = uploadName;
	}

	public String getUploadType() {
		return uploadType;
	}

	public void setUploadType(String uploadType) {
		this.uploadType = uploadType;
	}

	public String getRenameFile() {
		return renameFile;
	}

	public void setRenameFile(String renameFile) {
		this.renameFile = renameFile;
	}

	public String getCompositionRuleFile() {
		return compositionRuleFile;
	}

	public void setCompositionRuleFile(String compositionRuleFile) {
		this.compositionRuleFile = compositionRuleFile;
	}

	public String getCompositionRuleDir() {
		return compositionRuleDir;
	}

	public void setCompositionRuleDir(String compositionRuleDir) {
		this.compositionRuleDir = compositionRuleDir;
	}

	public String getCompositionReplaceName() {
		return compositionReplaceName;
	}

	public void setCompositionReplaceName(String compositionReplaceName) {
		this.compositionReplaceName = compositionReplaceName;
	}

	public List<UploadCommandLine> getCommandLine() {
		return commandLine;
	}

	public void setCommandLine(List<UploadCommandLine> commandLine) {
		this.commandLine = commandLine;
	}

	public String getIdRecord() {
		return idRecord;
	}

	public void setIdRecord(String idRecord) {
		this.idRecord = idRecord;
	}

	public String getDestField() {
		return destField;
	}

	public void setDestField(String destField) {
		this.destField = destField;
	}

	@Override
	public String toString() {
		return "UploadBean [filedata=" + filedata + ", name=" + name + ", idRecord=" + idRecord + ", destField=" + destField + ", uploadName=" + uploadName + ", uploadType=" + uploadType + ", renameFile=" + renameFile + ", renameDir=" + renameDir + ", compositionRuleFile=" + compositionRuleFile
				+ ", compositionRuleDir=" + compositionRuleDir + ", compositionReplaceName=" + compositionReplaceName + ", associatePathDir=" + associatePathDir + ", overWrite=" + overWrite + ", fileExist=" + fileExist + ", commandLine=" + commandLine + ", result=" + result
				+ ", resultOriginalFileName=" + resultOriginalFileName + ", flagOriginalFileName=" + flagOriginalFileName + ", destOriginalFileName=" + destOriginalFileName + ", xPathPrefix=" + xPathPrefix + ", resultError=" + resultError + "]";
	}

	public StringBuilder getResult() {
		return result;
	}

	public void setResult(StringBuilder result) {
		this.result = result;
	}

	public StringBuilder getResultError() {
		return resultError;
	}

	public void setResultError(StringBuilder resultError) {
		this.resultError = resultError;
	}

	public String getRenameDir() {
		return renameDir;
	}

	public void setRenameDir(String renameDir) {
		this.renameDir = renameDir;
	}

	public String getFlagOriginalFileName() {
		return flagOriginalFileName;
	}

	public void setFlagOriginalFileName(String flagOriginalFileName) {
		this.flagOriginalFileName = flagOriginalFileName;
	}

	public String getDestOriginalFileName() {
		return destOriginalFileName;
	}

	public void setDestOriginalFileName(String destOriginalFileName) {
		this.destOriginalFileName = destOriginalFileName;
	}

	public String getxPathPrefix() {
		return xPathPrefix;
	}

	public void setxPathPrefix(String xPathPrefix) {
		this.xPathPrefix = xPathPrefix;
	}

	public String getAssociatePathDir() {
		return associatePathDir;
	}

	public void setAssociatePathDir(String associatePathDir) {
		this.associatePathDir = associatePathDir;
	}

	public boolean isOverWrite() {
		return overWrite;
	}

	public void setOverWrite(boolean overWrite) {
		this.overWrite = overWrite;
	}

	public boolean isFileExist() {
		return fileExist;
	}

	public void setFileExist(boolean fileExist) {
		this.fileExist = fileExist;
	}

	public int getPhysDoc() {
		return physDoc;
	}

	public void setPhysDoc(int physDoc) {
		this.physDoc = physDoc;
	}

}
