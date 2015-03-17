package org.xdams.managing.bean;

import java.io.Serializable;

import org.xdams.adv.configuration.ConfigurationXMLReader;

public class ElementToFindBean implements Serializable {
	String strQuery = "";

	String strPrefix = "";

	String strCode = "";

	String xPathToChange = "";

	boolean advEditing = false;

	String isTextNode = "";

	String alias = "";

	int numTotXPathToChange = 0;

	ConfigurationXMLReader configurationXMLReader = null;

	public String getStrCode() {
		return strCode;
	}

	public void setStrCode(String strCode) {
		this.strCode = strCode;
	}

	public String getStrPrefix() {
		return strPrefix;
	}

	public void setStrPrefix(String strPrefix) {
		this.strPrefix = strPrefix;
	}

	public String getStrQuery() {
		return strQuery;
	}

	public void setStrQuery(String strQuery) {
		this.strQuery = strQuery;
	}

	public String getXPathToChange() {
		return xPathToChange;
	}

	public void setXPathToChange(String pathToChange) {
		xPathToChange = pathToChange;
	}

	/**
	 * @return Returns the numTotXPathToChange.
	 */
	public int getNumTotXPathToChange() {
		return numTotXPathToChange;
	}

	/**
	 * @param numTotXPathToChange
	 *            The numTotXPathToChange to set.
	 */
	public void setNumTotXPathToChange(int numTotXPathToChange) {
		this.numTotXPathToChange = numTotXPathToChange;
	}

	public boolean isAdvEditing() {
		return advEditing;
	}

	public void setAdvEditing(boolean advEditing) {
		this.advEditing = advEditing;
	}

	public ConfigurationXMLReader getConfigurationXMLReader() {
		return configurationXMLReader;
	}

	public void setConfigurationXMLReader(ConfigurationXMLReader configurationXMLReader) {
		this.configurationXMLReader = configurationXMLReader;
	}

	public String getIsTextNode() {
		return isTextNode;
	}

	public void setIsTextNode(String isTextNode) {
		this.isTextNode = isTextNode;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	@Override
	public String toString() {
		return "ElementToFindBean [strQuery=" + strQuery + ", strPrefix=" + strPrefix + ", strCode=" + strCode + ", xPathToChange=" + xPathToChange + ", advEditing=" + advEditing + ", isTextNode=" + isTextNode + ", alias=" + alias + ", numTotXPathToChange=" + numTotXPathToChange
				+ ", configurationXMLReader=" + configurationXMLReader + "]";
	}

}
