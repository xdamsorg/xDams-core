package org.xdams.page.view.bean;

import org.xdams.xml.builder.XMLBuilder;

public class PreInsertBean {

	private String docXmlRoot = "";

	private int physDocRoot = -1;

	private String titleRoot = "";

	private XMLBuilder xmlBuilderRoot = null;

	private int depthRoot = -1;

	private String docXmlSelected = "";

	private int physDocSelected = -1;

	private String titleSelected = "";

	private XMLBuilder xmlBuilderSelected = null;

	private int depthSelected = -1;

	private String docXmlFather = "";

	private int physDocFather = -1;

	private String titleFather = "";

	private XMLBuilder xmlBuilderFather = null;

	private int depthFather = -1;
	
	private String xPathHierValues = "";
	
	private XMLBuilder xmlBuilderEmpty = null;
	
	private String srcArchive="";

	public String getDocXmlFather() {
		return docXmlFather;
	}

	public void setDocXmlFather(String docXmlFather) {
		this.docXmlFather = docXmlFather;
	}

	public String getDocXmlRoot() {
		return docXmlRoot;
	}

	public void setDocXmlRoot(String docXmlRoot) {
		this.docXmlRoot = docXmlRoot;
	}

	public String getDocXmlSelected() {
		return docXmlSelected;
	}

	public void setDocXmlSelected(String docXmlSelected) {
		this.docXmlSelected = docXmlSelected;
	}

	public int getPhysDocFather() {
		return physDocFather;
	}

	public void setPhysDocFather(int physDocFather) {
		this.physDocFather = physDocFather;
	}

	public int getPhysDocRoot() {
		return physDocRoot;
	}

	public void setPhysDocRoot(int physDocRoot) {
		this.physDocRoot = physDocRoot;
	}

	public int getPhysDocSelected() {
		return physDocSelected;
	}

	public void setPhysDocSelected(int physDocSelected) {
		this.physDocSelected = physDocSelected;
	}

	public String getTitleFather() {
		return titleFather;
	}

	public void setTitleFather(String titleFather) {
		this.titleFather = titleFather;
	}

	public String getTitleRoot() {
		return titleRoot;
	}

	public void setTitleRoot(String titleRoot) {
		this.titleRoot = titleRoot;
	}

	public String getTitleSelected() {
		return titleSelected;
	}

	public void setTitleSelected(String titleSelected) {
		this.titleSelected = titleSelected;
	}

	public XMLBuilder getXmlBuilderFather() {
		return xmlBuilderFather;
	}

	public void setXmlBuilderFather(XMLBuilder xmlBuilderFather) {
		this.xmlBuilderFather = xmlBuilderFather;
	}

	public XMLBuilder getXmlBuilderRoot() {
		return xmlBuilderRoot;
	}

	public void setXmlBuilderRoot(XMLBuilder xmlBuilderRoot) {
		this.xmlBuilderRoot = xmlBuilderRoot;
	}

	public XMLBuilder getXmlBuilderSelected() {
		return xmlBuilderSelected;
	}

	public void setXmlBuilderSelected(XMLBuilder xmlBuilderSelected) {
		this.xmlBuilderSelected = xmlBuilderSelected;
	}

	public int getDepthFather() {
		return depthFather;
	}

	public void setDepthFather(int depthFather) {
		this.depthFather = depthFather;
	}

	public int getDepthRoot() {
		return depthRoot;
	}

	public void setDepthRoot(int depthRoot) {
		this.depthRoot = depthRoot;
	}

	public int getDepthSelected() {
		return depthSelected;
	}

	public void setDepthSelected(int depthSelected) {
		this.depthSelected = depthSelected;
	}

	public String getXPathHierValues() {
		return xPathHierValues;
	}

	public void setXPathHierValues(String pathHierValues) {
		xPathHierValues = pathHierValues;
	}

	public XMLBuilder getXmlBuilderEmpty() {
		return xmlBuilderEmpty;
	}

	public void setXmlBuilderEmpty(XMLBuilder xmlBuilderEmpty) {
		this.xmlBuilderEmpty = xmlBuilderEmpty;
	}

	public String getxPathHierValues() {
		return xPathHierValues;
	}

	public void setxPathHierValues(String xPathHierValues) {
		this.xPathHierValues = xPathHierValues;
	}

	public String getSrcArchive() {
		return srcArchive;
	}

	public void setSrcArchive(String srcArchive) {
		this.srcArchive = srcArchive;
	}

	@Override
	public String toString() {
		return "PreInsertBean [docXmlRoot=" + docXmlRoot + ", physDocRoot=" + physDocRoot + ", titleRoot=" + titleRoot + ", xmlBuilderRoot=" + xmlBuilderRoot + ", depthRoot=" + depthRoot + ", docXmlSelected=" + docXmlSelected + ", physDocSelected=" + physDocSelected + ", titleSelected="
				+ titleSelected + ", xmlBuilderSelected=" + xmlBuilderSelected + ", depthSelected=" + depthSelected + ", docXmlFather=" + docXmlFather + ", physDocFather=" + physDocFather + ", titleFather=" + titleFather + ", xmlBuilderFather=" + xmlBuilderFather + ", depthFather=" + depthFather
				+ ", xPathHierValues=" + xPathHierValues + ", xmlBuilderEmpty=" + xmlBuilderEmpty + ", srcArchive=" + srcArchive + "]";
	}

}
