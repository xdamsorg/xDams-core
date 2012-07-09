package org.xdams.page.view.bean;

import org.xdams.xml.builder.XMLBuilder;

public class MediaBean {

	private int physDoc = -1;

	// prefisso del nodo xml che si ripete
	private String xPathPrefix = "";

	// gruppo del nodo xml che si ripete
	private String xPathGroupPrefix = "";

	private String xPathHref = "";

	private String xPathTitle = "";

	private String xPathGroupDescr = "";
	
	private String xPathPrefixClipBoard = "";

	private String presentationXpath = "";

	/*
	 * viewImage per le img in generale viewDigital per gli allegati generici viewSound per il sonoro (non implementato) viewVideo per il video (non implementato)
	 */
	private String viewMode = "";

	/*
	 * gestisci la tipologia definita dentro media.conf.xml , high low thumb mp3 per la visualizzazione in schedabreve questi valori devono coincidere sia dentro il media.conf.xml, che nel presentation
	 */
	private String mediaType = "";

	/*
	 * gestisci la tipologia definita dentro media.conf.xml , high low thumb mp3 per la visualizzazione all'interno del flash questi valori devono coincidere sia dentro il media.conf.xml, che nel presentation
	 */
	private String mediaTypeHigh = "";
  	
	private String clipBoardType = "";

	// la porzione di presentation
	private XMLBuilder theXMLMediaBuilder = null;

	// il doc xml
	private XMLBuilder theXMLDoc = null;

	// media.conf.xml
	private XMLBuilder theXMLConfMedia = null;

	// si utilizza dentro ilcustom tag attachment per la visualizzazione in scheda,scheda breve o nella titoli
	private String attachMode = "";

	/*
	 * serve per ridefinire il path (prefix) ser trovo nel prefix getTheArch
	 * http://intranet.santacecilia.regesta.com/dm_0/getTheArch/low
	 * 
	 */
	
	private String digitalPosition="";
	
	public String getDigitalPosition() {
		return digitalPosition;
	}

	public void setDigitalPosition(String digitalPosition) {
		this.digitalPosition = digitalPosition;
	}

	private String theArch = "";

	public String getTheArch() {
		return theArch;
	}

	public void setTheArch(String theArch) {
		this.theArch = theArch;
	}

	public String getClipBoardType() {
		return clipBoardType;
	}

	public void setClipBoardType(String clipBoardType) {
		this.clipBoardType = clipBoardType;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public String getViewMode() {
		return viewMode;
	}

	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}

	public String getXPathGroupDescr() {
		return xPathGroupDescr;
	}

	public void setXPathGroupDescr(String pathGroupDescr) {
		xPathGroupDescr = pathGroupDescr;
	}

	public String getXPathGroupPrefix() {
		return xPathGroupPrefix;
	}

	public void setXPathGroupPrefix(String pathGroupPrefix) {
		xPathGroupPrefix = pathGroupPrefix;
	}

	public String getXPathHref() {
		return xPathHref;
	}

	public void setXPathHref(String pathHref) {
		xPathHref = pathHref;
	}

	public String getXPathPrefix() {
		return xPathPrefix;
	}

	public void setXPathPrefix(String pathPrefix) {
		xPathPrefix = pathPrefix;
	}

	public String getXPathTitle() {
		return xPathTitle;
	}

	public void setXPathTitle(String pathTitle) {
		xPathTitle = pathTitle;
	}

	public XMLBuilder getTheXMLConfMedia() {
		return theXMLConfMedia;
	}

	public void setTheXMLConfMedia(XMLBuilder theXMLConfMedia) {
		this.theXMLConfMedia = theXMLConfMedia;
	}

	public XMLBuilder getTheXMLDoc() {
		return theXMLDoc;
	}

	public void setTheXMLDoc(XMLBuilder theXMLDoc) {
		this.theXMLDoc = theXMLDoc;
	}

	public XMLBuilder getTheXMLMediaBuilder() {
		return theXMLMediaBuilder;
	}

	public void setTheXMLMediaBuilder(XMLBuilder theXMLMediaBuilder) {
		this.theXMLMediaBuilder = theXMLMediaBuilder;
	}

	public int getPhysDoc() {
		return physDoc;
	}

	public void setPhysDoc(int physDoc) {
		this.physDoc = physDoc;
	}

	public String getPresentationXpath() {
		return presentationXpath;
	}

	public void setPresentationXpath(String presentationXpath) {
		this.presentationXpath = presentationXpath;
	}

	public String getMediaTypeHigh() {
		return mediaTypeHigh;
	}

	public void setMediaTypeHigh(String mediaTypeHigh) {
		this.mediaTypeHigh = mediaTypeHigh;
	}

	public String getAttachMode() {
		return attachMode;
	}

	public void setAttachMode(String attachMode) {
		this.attachMode = attachMode;
	}

	public String getXPathPrefixClipBoard() {
		return xPathPrefixClipBoard;
	}

	public void setXPathPrefixClipBoard(String pathPrefixClipBoard) {
		xPathPrefixClipBoard = pathPrefixClipBoard;
	}
	
	public String toString(){
		String ritorno = "";
		ritorno+="getAttachMode() "+ getAttachMode() +"\n";
		ritorno+="getClipBoardType() "+ getClipBoardType() +"\n";
		ritorno+="getMediaType() "+ getMediaType() +"\n";
		ritorno+="getMediaTypeHigh() "+ getMediaTypeHigh()+"\n";
		ritorno+="getPhysDoc() "+ getPhysDoc() +"\n";
		ritorno+="getPresentationXpath() "+ getPresentationXpath() +"\n";
		ritorno+="getTheArch() "+ getTheArch() +"\n";
		ritorno+="getViewMode() "+ getViewMode() +"\n";
		ritorno+="getXPathGroupDescr() "+ getXPathGroupDescr() +"\n";
		ritorno+="getXPathHref() "+ getXPathHref() +"\n";
		ritorno+="getXPathPrefix() "+ getXPathPrefix()+"\n";
		ritorno+="getXPathPrefixClipBoard() "+ getXPathPrefixClipBoard() +"\n";
		
		return ritorno;
		
	}

}
