package org.xdams.conf.master;

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

	public XMLBuilder getTheXMLConfBarVis() {
		return theXMLConfBarVis;
	}

	public void setTheXMLConfBarVis(XMLBuilder theXMLConfBarVis) {
		this.theXMLConfBarVis = theXMLConfBarVis;
	}

	public XMLBuilder getTheXMLConfBarQuery() {
		return theXMLConfBarQuery;
	}

	public void setTheXMLConfBarQuery(XMLBuilder theXMLConfBarQuery) {
		this.theXMLConfBarQuery = theXMLConfBarQuery;
	}

	public XMLBuilder getTheXMLConfBarPreInsert() {
		return theXMLConfBarPreInsert;
	}

	public void setTheXMLConfBarPreInsert(XMLBuilder theXMLConfBarPreInsert) {
		this.theXMLConfBarPreInsert = theXMLConfBarPreInsert;
	}

	public XMLBuilder getTheXMLConfBarNav() {
		return theXMLConfBarNav;
	}

	public void setTheXMLConfBarNav(XMLBuilder theXMLConfBarNav) {
		this.theXMLConfBarNav = theXMLConfBarNav;
	}

	public XMLBuilder getTheXMLConfBarManaging() {
		return theXMLConfBarManaging;
	}

	public void setTheXMLConfBarManaging(XMLBuilder theXMLConfBarManaging) {
		this.theXMLConfBarManaging = theXMLConfBarManaging;
	}

	public XMLBuilder getTheXMLConfBarEdt() {
		return theXMLConfBarEdt;
	}

	public void setTheXMLConfBarEdt(XMLBuilder theXMLConfBarEdt) {
		this.theXMLConfBarEdt = theXMLConfBarEdt;
	}

	public XMLBuilder getTheXMLConfBarDocEdit() {
		return theXMLConfBarDocEdit;
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
		return theXMLConfTitle;
	}

	public void setTheXMLConfTitle(XMLBuilder theXMLConfTitle) {
		this.theXMLConfTitle = theXMLConfTitle;
	}

	public XMLBuilder getTheXMLConfQuery() {
		return theXMLConfQuery;
	}

	public void setTheXMLConfQuery(XMLBuilder theXMLConfQuery) {
		this.theXMLConfQuery = theXMLConfQuery;
	}

	public XMLBuilder getTheXMLConfPresentation() {
		return theXMLConfPresentation;
	}

	public void setTheXMLConfPresentation(XMLBuilder theXMLConfPresentation) {
		this.theXMLConfPresentation = theXMLConfPresentation;
	}

	public XMLBuilder getTheXMLConfEditing() {
		return theXMLConfEditing;
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
		// return ConfManager.getConfXML("valoriControllati.xml");
		return theXMLValControllati;
	}

	public void setTheXMLValControllati(XMLBuilder theXMLValControllati) {
		this.theXMLValControllati = theXMLValControllati;
	}

	public XMLBuilder getTheXMLConfUpload() {
		return theXMLConfUpload;
	}

	public void setTheXMLConfUpload(XMLBuilder theXMLConfUpload) {
		this.theXMLConfUpload = theXMLConfUpload;
	}

	public XMLBuilder getTheXMLConfMedia() {
		return theXMLConfMedia;
	}

	public void setTheXMLConfMedia(XMLBuilder theXMLConfMedia) {
		this.theXMLConfMedia = theXMLConfMedia;
	}

	public XMLBuilder getTheXMLConfManaging() {
		return theXMLConfManaging;
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

}
