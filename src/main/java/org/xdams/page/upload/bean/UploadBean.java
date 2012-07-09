package org.xdams.page.upload.bean;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class UploadBean {
	private CommonsMultipartFile filedata;

	private String name;

	private String idRecord;

	private String destField;

	private String uploadName;

	private String uploadType;

	private String renameFile;

	private String compositionRuleFile;

	private String compositionRuleDir;

	private String compositionReplaceName;

	private List<UploadCommandLine> commandLine = new ArrayList<UploadCommandLine>();

	private StringBuilder result = new StringBuilder();
	
	private StringBuilder resultOriginalFileName = new StringBuilder();
	
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
		return "UploadBean [filedata=" + filedata + ", name=" + name + ", idRecord=" + idRecord + ", destField=" + destField + ", uploadName=" + uploadName + ", uploadType=" + uploadType + ", renameFile=" + renameFile + ", compositionRuleFile=" + compositionRuleFile + ", compositionRuleDir="
				+ compositionRuleDir + ", compositionReplaceName=" + compositionReplaceName + ", commandLine=" + commandLine + ", result=" + result + ", resultError=" + resultError + "]";
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
}
