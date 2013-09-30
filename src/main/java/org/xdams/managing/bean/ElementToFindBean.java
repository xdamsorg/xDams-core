package org.xdams.managing.bean;

import org.xdams.adv.configuration.ConfigurationXMLReader;

public class ElementToFindBean {
	String strQuery = "";

	String strPrefix = "";

	String strCode = "";

	String xPathToChange = "";

	boolean advEditing = false;

	String isTextNode = "";

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

}
