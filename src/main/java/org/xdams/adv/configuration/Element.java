package org.xdams.adv.configuration;

import java.util.ArrayList;

public class Element {

	private String name = null;

	private String layer = null;

	private String label = null;

	private String htmlExtra = null;

	private String extra = null;

	private String input_type = null;

	private String htmlClass = null;

	private String type = null;

	private String code = null;

	private String web_app = null;

	private String arch_ref = null;

	private String ud_type = null;

	private String extraQuery = null;

	private String sort = null;

	private String title_rule = null;

	private String title_format = null;

	private String query = null;

	private String flagXML = null;

	private String jspOutPut = null;

	private String fieldToFill = null;

	private String value = null;

	private String externalPath = null;

	private String text = null;

	private String opened = null;

	private String repetable = null;

	private String prefix = null;

	private String lookupPrefix = null;

	private String rich = null;

	private String cols = null;

	private String rows = null;

	private String cdata = null;

	private String defaultValue = null;

	private String showdoc = null;

	private String summary = null;

	private String multiMod = null;

	private String spreadMod = null;

	private String refElementNames = null;

	private String deep = null;

	private String xPathMapping = null;

	private String alias = null;

	private String insertXPath = null;

	private String deleteXPath = null;

	private String insertPrefixXPath = null;

	private String deletePrefixXPath = null;

	private String queryDelete = null;

	private String firstValue = "";

	private String secondValue = "";

	private String mode = null;

	private String insertXslt = null;

	private String deleteXslt = null;

	private String modifyXslt = null;

	// questo sono attributi di sistema, mi servono per impostare onfly il valore del nodo selezionato e il valore dell'XPATH da ricercare
	private String fieldValue = null;

	private String fieldXPath = null;

	private String isPreinsert = null;

	private ArrayList elements = null;

	public Element() {
	}

	/**
	 * @return
	 */
	public String getArch_ref() {
		return arch_ref;
	}

	/**
	 * @return
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return
	 */
	public String getExternalPath() {
		return externalPath;
	}

	/**
	 * @return
	 */
	public String getExtra() {
		return extra;
	}

	/**
	 * @return
	 */
	public String getExtraQuery() {
		return extraQuery;
	}

	/**
	 * @return
	 */
	public String getHtmlClass() {
		return htmlClass;
	}

	/**
	 * @return
	 */
	public String getHtmlExtra() {
		return htmlExtra;
	}

	/**
	 * @return
	 */
	public String getInput_type() {
		return input_type;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * @return
	 */
	public String getSort() {
		return sort;
	}

	/**
	 * @return
	 */
	public String getText() {
		return text;
	}

	/**
	 * @return
	 */
	public String getTitle_format() {
		return title_format;
	}

	/**
	 * @return
	 */
	public String getTitle_rule() {
		return title_rule;
	}

	/**
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return
	 */
	public String getUd_type() {
		return ud_type;
	}

	/**
	 * @return
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @return
	 */
	public String getWeb_app() {
		return web_app;
	}

	/**
	 * @param string
	 */
	public void setArch_ref(String string) {
		arch_ref = string;
	}

	/**
	 * @param string
	 */
	public void setCode(String string) {
		code = string;
	}

	/**
	 * @param string
	 */
	public void setExternalPath(String string) {
		externalPath = string;
	}

	/**
	 * @param string
	 */
	public void setExtra(String string) {
		extra = string;
	}

	/**
	 * @param string
	 */
	public void setExtraQuery(String string) {
		extraQuery = string;
	}

	/**
	 * @param string
	 */
	public void setClass(String string) {
		htmlClass = string;
	}

	/**
	 * @param string
	 */
	public void setHtmlExtra(String string) {
		htmlExtra = string;
	}

	/**
	 * @param string
	 */
	public void setInput_type(String string) {
		input_type = string;
	}

	/**
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}

	/**
	 * @param string
	 */
	public void setQuery(String string) {
		query = string;
	}

	/**
	 * @param string
	 */
	public void setSort(String string) {
		sort = string;
	}

	/**
	 * @param string
	 */
	public void setText(String string) {
		text = string;
	}

	/**
	 * @param string
	 */
	public void setTitle_format(String string) {
		title_format = string;
	}

	/**
	 * @param string
	 */
	public void setTitle_rule(String string) {
		title_rule = string;
	}

	/**
	 * @param string
	 */
	public void setType(String string) {
		type = string;
	}

	/**
	 * @param string
	 */
	public void setUd_type(String string) {
		ud_type = string;
	}

	/**
	 * @param string
	 */
	public void setValue(String string) {
		value = string;
	}

	/**
	 * @param string
	 */
	public void setWeb_app(String string) {
		web_app = string;
	}

	/**
	 * @return
	 */
	public String getOpened() {
		return opened;
	}

	/**
	 * @param string
	 */
	public void setOpened(String string) {
		opened = string;
	}

	/**
	 * @return
	 */
	public String getRepetable() {
		return repetable;
	}

	/**
	 * @param string
	 */
	public void setRepetable(String string) {
		repetable = string;
	}

	/**
	 * @return
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * @param string
	 */
	public void setPrefix(String string) {
		prefix = string;
	}

	/**
	 * @return
	 */
	public ArrayList getElemets() {
		return elements;
	}

	/**
	 * @param list
	 */
	public void addElement(Object object) {
		if (elements == null)
			elements = new ArrayList();
		elements.add(object);
	}

	/**
	 * @return
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @return
	 */
	public String getLayer() {
		return layer;
	}

	/**
	 * @param string
	 */
	public void setLabel(String string) {
		label = string;
	}

	/**
	 * @param string
	 */
	public void setLayer(String string) {
		layer = string;
	}

	/**
	 * @return
	 */
	public String getRich() {
		return rich;
	}

	/**
	 * @param string
	 */
	public void setRich(String string) {
		rich = string;
	}

	public String getCols() {
		return cols;
	}

	public void setCols(String cols) {
		this.cols = cols;
	}

	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	public String getFlagXML() {
		return flagXML;
	}

	public void setFlagXML(String flagXML) {
		this.flagXML = flagXML;
	}

	public String getFieldToFill() {
		return fieldToFill;
	}

	public void setFieldToFill(String fieldToFill) {
		this.fieldToFill = fieldToFill;
	}

	public String getJspOutPut() {
		return jspOutPut;
	}

	public void setJspOutPut(String jspOutPut) {
		this.jspOutPut = jspOutPut;
	}

	public String getCdata() {
		return cdata;
	}

	public void setCdata(String cdata) {
		this.cdata = cdata;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getShowdoc() {
		return showdoc;
	}

	public void setShowdoc(String showdoc) {
		this.showdoc = showdoc;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getMultiMod() {
		return multiMod;
	}

	public void setMultiMod(String multiMod) {
		this.multiMod = multiMod;
	}

	public String getDeep() {
		return deep;
	}

	public void setDeep(String deep) {
		this.deep = deep;
	}

	public String getXPathMapping() {
		return xPathMapping;
	}

	public void setXPathMapping(String pathMapping) {
		xPathMapping = pathMapping;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public String getFieldXPath() {
		return fieldXPath;
	}

	public void setFieldXPath(String fieldXPath) {
		this.fieldXPath = fieldXPath;
	}

	public String getLookupPrefix() {
		return lookupPrefix;
	}

	public void setLookupPrefix(String lookupPrefix) {
		this.lookupPrefix = lookupPrefix;
	}

	public String getIsPreinsert() {
		return isPreinsert;
	}

	public void setIsPreinsert(String isPreinsert) {
		this.isPreinsert = isPreinsert;
	}

	public String getSpreadMod() {
		return spreadMod;
	}

	public void setSpreadMod(String spreadMod) {
		this.spreadMod = spreadMod;
	}

	public String getRefElementNames() {
		return refElementNames;
	}

	public void setRefElementNames(String refElementNames) {
		this.refElementNames = refElementNames;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
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

	public String getDeletePrefixXPath() {
		return deletePrefixXPath;
	}

	public void setDeletePrefixXPath(String deletePrefixXPath) {
		this.deletePrefixXPath = deletePrefixXPath;
	}

	public String getFirstValue() {
		return firstValue;
	}

	public void setFirstValue(String firstValue) {
		this.firstValue = firstValue;
	}

	public String getSecondValue() {
		return secondValue;
	}

	public void setSecondValue(String secondValue) {
		this.secondValue = secondValue;
	}

	public String getDeleteXPath() {
		return deleteXPath;
	}

	public void setDeleteXPath(String deleteXPath) {
		this.deleteXPath = deleteXPath;
	}

	@Override
	public String toString() {
		return "Element [alias=" + alias + ", arch_ref=" + arch_ref + ", cdata=" + cdata + ", code=" + code + ", cols=" + cols + ", deep=" + deep + ", defaultValue=" + defaultValue + ", deletePrefixXPath=" + deletePrefixXPath + ", deleteXPath=" + deleteXPath + ", elements=" + elements
				+ ", externalPath=" + externalPath + ", extra=" + extra + ", extraQuery=" + extraQuery + ", fieldToFill=" + fieldToFill + ", fieldValue=" + fieldValue + ", fieldXPath=" + fieldXPath + ", firstValue=" + firstValue + ", flagXML=" + flagXML + ", htmlClass=" + htmlClass + ", htmlExtra="
				+ htmlExtra + ", input_type=" + input_type + ", insertPrefixXPath=" + insertPrefixXPath + ", insertXPath=" + insertXPath + ", isPreinsert=" + isPreinsert + ", jspOutPut=" + jspOutPut + ", label=" + label + ", layer=" + layer + ", lookupPrefix=" + lookupPrefix + ", multiMod="
				+ multiMod + ", name=" + name + ", opened=" + opened + ", prefix=" + prefix + ", query=" + query + ", refElementNames=" + refElementNames + ", repetable=" + repetable + ", rich=" + rich + ", rows=" + rows + ", secondValue=" + secondValue + ", showdoc=" + showdoc + ", sort=" + sort
				+ ", spreadMod=" + spreadMod + ", summary=" + summary + ", text=" + text + ", title_format=" + title_format + ", title_rule=" + title_rule + ", type=" + type + ", ud_type=" + ud_type + ", value=" + value + ", web_app=" + web_app + ", xPathMapping=" + xPathMapping + "]";
	}

	public String getQueryDelete() {
		return queryDelete;
	}

	public void setQueryDelete(String queryDelete) {
		this.queryDelete = queryDelete;
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
