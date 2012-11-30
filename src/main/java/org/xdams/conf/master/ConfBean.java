package org.xdams.conf.master;

import javax.servlet.jsp.PageContext;

import org.springframework.web.util.ExpressionEvaluationUtils;
import org.xdams.utility.resource.ConfManager;
import org.xdams.xml.builder.XMLBuilder;

public class ConfBean {
	/**
	 * keyCountIDX: numero elementi per pagina per vocabolario keyCountLookUp: numero elementi per pagina per lookup sorting: ordinamento di default
	 */

	private String keyCountIDX = "32";

	private String keyCountLookUp = "10";

	private String sorting = "";

	private XMLBuilder theXMLConfQuery = ConfManager.getConfXML("query.conf.xml");

	private XMLBuilder theXMLConfTitle = ConfManager.getConfXML("titles.conf.xml");

	private XMLBuilder theXMLConfPresentation = ConfManager.getConfXML("presentation.conf.xml");

	private String presentationXsl = null;

	private XMLBuilder theXMLConfEditing = ConfManager.getConfXML("editing.conf.xml");

	private XMLBuilder theXMLValControllati = ConfManager.getConfXML("valoriControllati.xml");

	private XMLBuilder theXMLConfUpload = ConfManager.getConfXML("upload.conf.xml");

	private XMLBuilder theXMLConfMedia = ConfManager.getConfXML("media.conf.xml");

	private String mediaPath;

	private XMLBuilder theXMLConfManaging = ConfManager.getConfXML("managing.conf.xml");

	private XMLBuilder theXMLConfBarVis = ConfManager.getConfXML("bar-vis.conf.xml");

	private XMLBuilder theXMLConfBarQuery = ConfManager.getConfXML("bar-query.conf.xml");

	private XMLBuilder theXMLConfBarPreInsert = ConfManager.getConfXML("bar-preinsert.conf.xml");

	private XMLBuilder theXMLConfBarNav = ConfManager.getConfXML("bar-nav.conf.xml");

	private XMLBuilder theXMLConfBarManaging = ConfManager.getConfXML("bar-managing.conf.xml");

	private XMLBuilder theXMLConfBarEdt = ConfManager.getConfXML("bar-edt.conf.xml");

	private XMLBuilder theXMLConfBarDocEdit = ConfManager.getConfXML("bar-docedit.conf.xml");

	private PageContext pageContext = null;

	public XMLBuilder getTheXMLConfBarVis() {
		//return theXMLConfBarVis;
		return evaluteXml(theXMLConfBarVis, getPageContext());
	}

	public void setTheXMLConfBarVis(XMLBuilder theXMLConfBarVis) {
		this.theXMLConfBarVis = theXMLConfBarVis;
	}

	public XMLBuilder getTheXMLConfBarQuery() {
		//return theXMLConfBarQuery;
		return evaluteXml(theXMLConfBarQuery, getPageContext());
	}

	public void setTheXMLConfBarQuery(XMLBuilder theXMLConfBarQuery) {
		this.theXMLConfBarQuery = theXMLConfBarQuery;
	}

	public XMLBuilder getTheXMLConfBarPreInsert() {
		//return theXMLConfBarPreInsert;
		return evaluteXml(theXMLConfBarPreInsert, getPageContext());
	}

	public void setTheXMLConfBarPreInsert(XMLBuilder theXMLConfBarPreInsert) {
		this.theXMLConfBarPreInsert = theXMLConfBarPreInsert;
	}

	public XMLBuilder getTheXMLConfBarNav() {
		//return theXMLConfBarNav;
		return evaluteXml(theXMLConfBarNav, getPageContext());
	}

	public void setTheXMLConfBarNav(XMLBuilder theXMLConfBarNav) {
		this.theXMLConfBarNav = theXMLConfBarNav;
	}

	public XMLBuilder getTheXMLConfBarManaging() {
		//return theXMLConfBarManaging;
		return evaluteXml(theXMLConfBarManaging, getPageContext());
	}

	public void setTheXMLConfBarManaging(XMLBuilder theXMLConfBarManaging) {
		this.theXMLConfBarManaging = theXMLConfBarManaging;
	}

	public XMLBuilder getTheXMLConfBarEdt() {
		//return theXMLConfBarEdt;
		return evaluteXml(theXMLConfBarEdt, getPageContext());
	}

	public void setTheXMLConfBarEdt(XMLBuilder theXMLConfBarEdt) {
		this.theXMLConfBarEdt = theXMLConfBarEdt;
	}

	public XMLBuilder getTheXMLConfBarDocEdit() {
		//return theXMLConfBarDocEdit;
		return evaluteXml(theXMLConfBarDocEdit, getPageContext());
	}

	public void setTheXMLConfBarDocEdit(XMLBuilder theXMLConfBarDocEdit) {
		this.theXMLConfBarDocEdit = theXMLConfBarDocEdit;
	}

	public ConfBean() {

	}

	public String getKeyCountIDX() {
		return keyCountIDX;
	}

	public void setKeyCountIDX(String keyCountIDX) {
		this.keyCountIDX = keyCountIDX;
	}

	public XMLBuilder getTheXMLConfTitle() {
		//return theXMLConfTitle;
		return evaluteXml(theXMLConfTitle, getPageContext());
	}

	public void setTheXMLConfTitle(XMLBuilder theXMLConfTitle) {
		this.theXMLConfTitle = theXMLConfTitle;
	}

	public XMLBuilder getTheXMLConfQuery() {
		//return theXMLConfQuery;
		return evaluteXml(theXMLConfQuery, getPageContext());
	}

	public void setTheXMLConfQuery(XMLBuilder theXMLConfQuery) {
		this.theXMLConfQuery = theXMLConfQuery;
	}

	public XMLBuilder getTheXMLConfPresentation() {
		//return theXMLConfPresentation;
		return evaluteXml(theXMLConfPresentation, getPageContext());
	}

	public void setTheXMLConfPresentation(XMLBuilder theXMLConfPresentation) {
		this.theXMLConfPresentation = theXMLConfPresentation;
	}

	public XMLBuilder getTheXMLConfEditing() {
		//return theXMLConfEditing;
		return evaluteXml(theXMLConfEditing, getPageContext());
	}

	public void setTheXMLConfEditing(XMLBuilder theXMLConfEditing) {
		this.theXMLConfEditing = theXMLConfEditing;
	}

	public String getKeyCountLookUp() {
		return keyCountLookUp;
	}

	public void setKeyCountLookUp(String keyCountLookUp) {
		this.keyCountLookUp = keyCountLookUp;
	}

	public XMLBuilder getTheXMLValControllati() {
		//return theXMLValControllati;
		return evaluteXml(theXMLValControllati, getPageContext());
	}

	public void setTheXMLValControllati(XMLBuilder theXMLValControllati) {
		this.theXMLValControllati = theXMLValControllati;
	}

	public XMLBuilder getTheXMLConfUpload() {
		//return theXMLConfUpload;
		return evaluteXml(theXMLConfUpload, getPageContext());
	}

	public void setTheXMLConfUpload(XMLBuilder theXMLConfUpload) {
		this.theXMLConfUpload = theXMLConfUpload;
	}

	public XMLBuilder getTheXMLConfMedia() {
		//return theXMLConfMedia;
		return evaluteXml(theXMLConfMedia, getPageContext());
	}

	public void setTheXMLConfMedia(XMLBuilder theXMLConfMedia) {
		this.theXMLConfMedia = theXMLConfMedia;
	}

	public XMLBuilder getTheXMLConfManaging() {
		return evaluteXml(theXMLConfManaging, getPageContext());
	}

	public void setTheXMLConfManaging(XMLBuilder theXMLConfManaging) {
		this.theXMLConfManaging = theXMLConfManaging;
	}

	public String getSorting() {
		return sorting;
	}

	public void setSorting(String sorting) {
		this.sorting = sorting;
	}

	@Override
	public String toString() {
		return "ConfBean [keyCountIDX=" + keyCountIDX + ", keyCountLookUp=" + keyCountLookUp + ", sorting=" + sorting + ", theXMLConfQuery=" + theXMLConfQuery + ", theXMLConfTitle=" + theXMLConfTitle + ", theXMLConfPresentation=" + theXMLConfPresentation + ", theXMLConfEditing=" + theXMLConfEditing
				+ ", theXMLValControllati=" + theXMLValControllati + ", theXMLConfUpload=" + theXMLConfUpload + ", theXMLConfMedia=" + theXMLConfMedia + ", theXMLConfManaging=" + theXMLConfManaging + ", theXMLConfBarVis=" + theXMLConfBarVis + ", theXMLConfBarQuery=" + theXMLConfBarQuery
				+ ", theXMLConfBarPreInsert=" + theXMLConfBarPreInsert + ", theXMLConfBarNav=" + theXMLConfBarNav + ", theXMLConfBarManaging=" + theXMLConfBarManaging + ", theXMLConfBarEdt=" + theXMLConfBarEdt + ", theXMLConfBarDocEdit=" + theXMLConfBarDocEdit + "]";
	}

	public String getPresentationXsl() {
		return presentationXsl;
	}

	public void setPresentationXsl(String presentationXsl) {
		this.presentationXsl = presentationXsl;
	}

	public String getMediaPath() {
		return mediaPath;
	}

	public void setMediaPath(String mediaPath) {
		this.mediaPath = mediaPath;
	}

	private XMLBuilder evaluteXml(XMLBuilder theXMLconf, PageContext pageContext) {
		if (pageContext != null) {
			try {
				String xmlConfstr = theXMLconf.getXML("ISO-8859-1");
				xmlConfstr = ExpressionEvaluationUtils.evaluateString(xmlConfstr, xmlConfstr, pageContext);
				return new XMLBuilder(xmlConfstr, false);
			} catch (Exception e) {
				return theXMLconf;
			}
		}
		return theXMLconf;
	}

	public PageContext getPageContext() {
		return pageContext;
	}

	public void setPageContext(PageContext pageContext) {
		this.pageContext = pageContext;
	}

}
