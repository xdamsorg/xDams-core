package org.xdams.jstl;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.xdams.conf.master.ConfBean;
import org.xdams.page.view.bean.MediaBean;
import org.xdams.user.bean.UserBean;
import org.xdams.xml.builder.XMLBuilder;


public class AttachmentViewer extends TagSupport {

	private String attachMode = "";

	private String titleString = "";

	private int physDoc = -1;
	// lo xpath del presentation, mi serve per il visualizzatore, per evitare di dover ricalcolare tutto..
	private String presentationXpath = "";
	// la porzione di presentation per la vis dentro la scheda breve
	private XMLBuilder mediaBuilder = null;
	// il doc xml
	private XMLBuilder theXMLDoc = null;

	public int doStartTag() {
		try {
			ConfBean confBean = (ConfBean) pageContext.findAttribute("confBean");
			UserBean userBean = (UserBean) pageContext.findAttribute("userBean");
			JspWriter out = pageContext.getOut();
			// media.conf.xml
			XMLBuilder theXMLConfMedia = confBean.getTheXMLConfMedia();
			MediaBean mediaBean = new MediaBean();
			//System.out.println("AttachmentViewer.doStartTag() "+getMediaBuilder().getXML("ISO-8859-1"));
			//System.out.println("AttachmentViewer.doStartTag() "+theXMLConfMedia.getXML("ISO-8859-1"));
			mediaBean.setPhysDoc(getPhysDoc());
			mediaBean.setClipBoardType(getMediaBuilder().valoreNodo("/elemento/@clipBoardType"));
			mediaBean.setMediaType(getMediaBuilder().valoreNodo("/elemento/@mediaType"));
			mediaBean.setViewMode(getMediaBuilder().valoreNodo("/elemento/@viewMode"));
			mediaBean.setXPathGroupDescr(getMediaBuilder().valoreNodo("/elemento/@xPathGroupDescr"));
			mediaBean.setXPathGroupPrefix(getMediaBuilder().valoreNodo("/elemento/@xPathGroupPrefix"));
			mediaBean.setXPathHref(getMediaBuilder().valoreNodo("/elemento/@xPathHref"));
			mediaBean.setXPathPrefix(getMediaBuilder().valoreNodo("/elemento/@xPathPrefix"));
			mediaBean.setXPathTitle(getMediaBuilder().valoreNodo("/elemento/@xPathTitle"));
			mediaBean.setXPathPrefixClipBoard(getMediaBuilder().valoreNodo("/elemento/@xPathPrefixClipBoard"));
			mediaBean.setMediaTypeHigh(mediaBuilder.valoreNodo("/elemento/@mediaTypeHigh"));
			mediaBean.setDigitalPosition(mediaBuilder.valoreNodo("/elemento/@digitalPosition"));
			mediaBean.setPresentationXpath(presentationXpath);
			mediaBean.setTheXMLConfMedia(theXMLConfMedia);
			mediaBean.setTheXMLDoc(theXMLDoc);
			mediaBean.setTheXMLMediaBuilder(getMediaBuilder());
			mediaBean.setAttachMode(getAttachMode());
			//TODO
//			mediaBean.setTheArch(userBean.getTheArch());
////			System.out.println("AttachmentViewer.doStartTag() "+mediaBean.toString());
//			MediaFactory mediaFactory = new MediaFactory();
//			StringBuffer stringBuffer = mediaFactory.execute(mediaBean);
//			out.println(stringBuffer.toString());

		} catch (Exception ex) {
			throw new Error("Errore in AttachmentViewer.1 ("+ex.getMessage()+")");
		}
		return SKIP_BODY;
	}

	public int doEndTag() {
		try {
			JspWriter out = pageContext.getOut();
		} catch (Exception ex) {
			throw new Error("Errore nella AttachmentViewer.0");
		}
		return EVAL_PAGE;
	}

	public String getAttachMode() {
		return attachMode;
	}

	public void setAttachMode(String attachMode) {
		this.attachMode = attachMode;
	}

	public String getTitleString() {
		return titleString;
	}

	public void setTitleString(String titleString) {
		this.titleString = titleString;
	}

	public XMLBuilder getMediaBuilder() {
		return mediaBuilder;
	}

	public void setMediaBuilder(XMLBuilder mediaBuilder) {
		this.mediaBuilder = mediaBuilder;
	}

	public XMLBuilder getTheXMLDoc() {
		return theXMLDoc;
	}

	public void setTheXMLDoc(XMLBuilder theXMLDoc) {
		this.theXMLDoc = theXMLDoc;
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

}
