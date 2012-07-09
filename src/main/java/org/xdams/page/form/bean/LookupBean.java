package org.xdams.page.form.bean;

public class LookupBean {
	String inputQuery = "";

	String inputExtraQuery = "";

	String inputStrQuery = "";

	String inputSort = "";

	String inputTitleRule = "";

	String inputMultiArchivio = "";

	String inputUdType = "";
	
	String jspOutPut = "";

	public String getInputQuery() {
		return inputQuery;
	}

	public void setInputQuery(String inputQuery) {
		this.inputQuery = inputQuery;
	}

	public String getInputExtraQuery() {
		return inputExtraQuery;
	}

	public void setInputExtraQuery(String inputExtraQuery) {
		this.inputExtraQuery = inputExtraQuery;
	}

	public String getInputStrQuery() {
		return inputStrQuery;
	}

	public void setInputStrQuery(String inputStrQuery) {
		this.inputStrQuery = inputStrQuery;
	}

	public String getInputSort() {
		return inputSort;
	}

	public void setInputSort(String inputSort) {
		this.inputSort = inputSort;
	}

	public String getInputTitleRule() {
		return inputTitleRule;
	}

	public void setInputTitleRule(String inputTitleRule) {
		this.inputTitleRule = inputTitleRule;
	}

	public String getInputMultiArchivio() {
		return inputMultiArchivio;
	}

	public void setInputMultiArchivio(String inputMultiArchivio) {
		this.inputMultiArchivio = inputMultiArchivio;
	}

	public String getInputUdType() {
		return inputUdType;
	}

	public void setInputUdType(String inputUdType) {
		this.inputUdType = inputUdType;
	}

	public String getJspOutPut() {
		return jspOutPut;
	}

	public void setJspOutPut(String jspOutPut) {
		this.jspOutPut = jspOutPut;
	}

	@Override
	public String toString() {
		return "LookupBean [inputQuery=" + inputQuery + ", inputExtraQuery=" + inputExtraQuery + ", inputStrQuery=" + inputStrQuery + ", inputSort=" + inputSort + ", inputTitleRule=" + inputTitleRule + ", inputMultiArchivio=" + inputMultiArchivio + ", inputUdType=" + inputUdType + ", jspOutPut="
				+ jspOutPut + "]";
	}
}
