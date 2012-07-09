package org.xdams.page.view.bean;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.TransformerException;

import org.xdams.xml.builder.XMLBuilder;

public class EditingBean {

	private String docXml = "";

	private int physDoc = -1;

	private String title = "";

	private String thePne = "";

	private String ilPath = "";

	private String selid = "";

	private int totSelid = -1;

	private String pos = "";

	private int docFather = 0;

	private int docSon = 0;

	private int docUpperBrother = 0;

	private int docLowerBrother = 0;

	private XMLBuilder xmlBuilder = null;
	
	private XMLBuilder xmlBuilderQuickEdit = null;
	
	private int physDocNext = -1;

	private int physDocPrev = -1;

	private int posNext = -1;

	private int posPrev = -1;

	private HttpServletRequest httpServletRequest = null;

	public int getPhysDoc() {
		return physDoc;
	}

	public void setPhysDoc(int physDoc) {
		this.physDoc = physDoc;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDocXml() {
		return docXml;
	}

	public void setDocXml(String docXml) {
		this.docXml = docXml;
	}

	public XMLBuilder getXmlBuilder() {
		return xmlBuilder;
	}

	public void setXmlBuilder(XMLBuilder xmlBuilder) {
		this.xmlBuilder = xmlBuilder;
	}

	public String getIlPath() {
		return ilPath;
	}

	public void setIlPath(String ilPath) {
		this.ilPath = ilPath;
	}

	public int getDocFather() {
		return docFather;
	}

	public void setDocFather(int docFather) {
		this.docFather = docFather;
	}

	public int getDocLowerBrother() {
		return docLowerBrother;
	}

	public void setDocLowerBrother(int docLowerBrother) {
		this.docLowerBrother = docLowerBrother;
	}

	public int getDocSon() {
		return docSon;
	}

	public void setDocSon(int docSon) {
		this.docSon = docSon;
	}

	public int getDocUpperBrother() {
		return docUpperBrother;
	}

	public void setDocUpperBrother(int docUpperBrother) {
		this.docUpperBrother = docUpperBrother;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public String getSelid() {
		return selid;
	}

	public void setSelid(String selid) {
		this.selid = selid;
	}

	public int getTotSelid() {
		return totSelid;
	}

	public void setTotSelid(int totSelid) {
		this.totSelid = totSelid;
	}

	public int getPhysDocNext() {
		return physDocNext;
	}

	public void setPhysDocNext(int physDocNext) {
		this.physDocNext = physDocNext;
	}

	public int getPhysDocPrev() {
		return physDocPrev;
	}

	public void setPhysDocPrev(int physDocPrev) {
		this.physDocPrev = physDocPrev;
	}

	public int getPosNext() {
		return posNext;
	}

	public void setPosNext(int posNext) {
		this.posNext = posNext;
	}

	public int getPosPrev() {
		return posPrev;
	}

	public void setPosPrev(int posPrev) {
		this.posPrev = posPrev;
	}

	public String getThePne() {
		return thePne;
	}

	public void setThePne(String thePne) {
		this.thePne = thePne;
	}

	public String valoreNodo(String xpathValue) throws UnsupportedEncodingException, TransformerException {
		return getXmlBuilder().valoreNodo(xpathValue);
	}

	public HttpServletRequest getHttpServletRequest() {
		return httpServletRequest;
	}

	public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
		this.httpServletRequest = httpServletRequest;
	}

	public XMLBuilder getXmlBuilderQuickEdit() {
		return xmlBuilderQuickEdit;
	}

	public void setXmlBuilderQuickEdit(XMLBuilder xmlBuilderQuickEdit) {
		this.xmlBuilderQuickEdit = xmlBuilderQuickEdit;
	}
}
