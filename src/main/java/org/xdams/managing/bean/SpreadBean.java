package org.xdams.managing.bean;

import java.util.ArrayList;

public class SpreadBean {

	String xPathToRead = "";

	String xPathToWrite = "";

	String name = "";

	String refElementNames = "";

	String arch_ref = "";

	String insertXPath = "";

	String deleteXPath = "";

	String insertPrefixXPath = "";

	String deletePrefixXPath = "";

	String query = "";

	String queryDelete = "";

	String firstValue = "";

	String secondValue = "";

	String mode = "";

	@Deprecated
	String thirdValue = "";

	String value = "";

	String externalPath = "";

	String insertXslt = "";

	String deleteXslt = "";

	String modifyXslt = "";

	ArrayList<String> values = new ArrayList<String>();

	public String getxPathToRead() {
		return xPathToRead;
	}

	public void setxPathToRead(String xPathToRead) {
		this.xPathToRead = xPathToRead;
	}

	public String getxPathToWrite() {
		return xPathToWrite;
	}

	public void setxPathToWrite(String xPathToWrite) {
		this.xPathToWrite = xPathToWrite;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRefElementNames() {
		return refElementNames;
	}

	public void setRefElementNames(String refElementNames) {
		this.refElementNames = refElementNames;
	}

	public String getArch_ref() {
		return arch_ref;
	}

	public void setArch_ref(String archRef) {
		arch_ref = archRef;
	}

	public ArrayList<String> getValues() {
		return values;
	}

	public void setValues(ArrayList<String> values) {
		this.values = values;
	}

	@Override
	public String toString() {
		return "SpreadBean [arch_ref=" + arch_ref + ", deletePrefixXPath=" + deletePrefixXPath + ", deleteXPath=" + deleteXPath + ", externalPath=" + externalPath + ", firstValue=" + firstValue + ", insertPrefixXPath=" + insertPrefixXPath + ", insertXPath=" + insertXPath + ", name=" + name
				+ ", query=" + query + ", queryDelete=" + queryDelete + ", refElementNames=" + refElementNames + ", secondValue=" + secondValue + ", thirdValue=" + thirdValue + ", value=" + value + ", values=" + values + ", xPathToRead=" + xPathToRead + ", xPathToWrite=" + xPathToWrite + "]";
	}

	public String getInsertXPath() {
		return insertXPath;
	}

	public void setInsertXPath(String insertXPath) {
		this.insertXPath = insertXPath;
	}

	public String getInsertPrefixXPath() {
		return insertPrefixXPath;
	}

	public void setInsertPrefixXPath(String insertPrefixXPath) {
		this.insertPrefixXPath = insertPrefixXPath;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getDeletePrefixXPath() {
		return deletePrefixXPath;
	}

	public void setDeletePrefixXPath(String deletePrefixXPath) {
		this.deletePrefixXPath = deletePrefixXPath;
	}

	public String getSecondValue() {
		return secondValue;
	}

	public void setSecondValue(String secondValue) {
		this.secondValue = secondValue;
	}

	public String getFirstValue() {
		return firstValue;
	}

	public void setFirstValue(String firstValue) {
		this.firstValue = firstValue;
	}

	public String getDeleteXPath() {
		return deleteXPath;
	}

	public void setDeleteXPath(String deleteXPath) {
		this.deleteXPath = deleteXPath;
	}

	public String getQueryDelete() {
		return queryDelete;
	}

	public void setQueryDelete(String queryDelete) {
		this.queryDelete = queryDelete;
	}

	@Deprecated
	public String getThirdValue() {
		return thirdValue;
	}

	@Deprecated
	public void setThirdValue(String thirdValue) {
		this.thirdValue = thirdValue;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Deprecated
	public String getExternalPath() {
		return externalPath;
	}

	@Deprecated
	public void setExternalPath(String externalPath) {
		this.externalPath = externalPath;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getInsertXslt() {
		return insertXslt;
	}

	public void setInsertXslt(String insertXslt) {
		this.insertXslt = insertXslt;
	}

	public String getDeleteXslt() {
		return deleteXslt;
	}

	public void setDeleteXslt(String deleteXslt) {
		this.deleteXslt = deleteXslt;
	}

	public String getModifyXslt() {
		return modifyXslt;
	}

	public void setModifyXslt(String modifyXslt) {
		this.modifyXslt = modifyXslt;
	}

}
