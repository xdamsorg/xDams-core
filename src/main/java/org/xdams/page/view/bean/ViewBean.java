package org.xdams.page.view.bean;

import it.highwaytech.db.HierPath;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.TransformerException;

import org.xdams.xml.builder.XMLBuilder;

public class ViewBean {

	private String docXml = "";

	private int docFather = 0;

	private int docSon = 0;

	private int docUpperBrother = 0;

	private int docLowerBrother = 0;

	private int treePos = 0;

	private HierPath hierPath = null;

	private XMLBuilder xmlBuilder = null;

	private int physDoc = -1;

	private String title = "";

	private String selid = "";

	private String pos = "";

	private int totSelid = -1;

	private String rootElement = "";

	private int physDocNext = -1;

	private int physDocPrev = -1;

	private int posNext = -1;

	private int posPrev = -1;

	private HttpServletRequest httpServletRequest = null;

	private String jspDispatch = null;

	private String pageName = null;

	public ViewBean() {

	}

	public ViewBean(HttpServletRequest request) {
		request.setAttribute("viewBean", this);
	}

	public String getDocXml() {

		return docXml;
	}

	public String getDocXmlEvid() {

		return docXml;
	}

	public void setDocXmlEvid(String docXml) {
		this.docXml = docXml;
	}

	public void setDocXml(String docXml) {
		this.docXml = docXml;
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

	public int getTreePos() {
		return treePos;
	}

	public void setTreePos(int treePos) {
		this.treePos = treePos;
	}

	public HierPath getHierPath() {
		return hierPath;
	}

	public void setHierPath(HierPath hierPath) {
		this.hierPath = hierPath;
	}

	public XMLBuilder getXmlBuilder() {
		return xmlBuilder;
	}

	public void setXmlBuilder(XMLBuilder xmlBuilder) {
		this.xmlBuilder = xmlBuilder;
	}

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

	public String getRootElement() {
//		System.out.println("ViewBean.getRootElement() xmlBuilder " + xmlBuilder);
		return xmlBuilder.getRootElement();
	}

	public void setRootElement(String rootElement) {
		this.rootElement = rootElement;
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

	public String valoreNodo(String xpathValue) throws UnsupportedEncodingException, TransformerException {
		return getXmlBuilder().valoreNodo(xpathValue);
	}

	public String valoreNodoNoHL(String xpathValue) throws UnsupportedEncodingException, TransformerException {
		return getXmlBuilder().valoreNodoNoHL(xpathValue);
	}

	public HttpServletRequest getHttpServletRequest() {
		return httpServletRequest;
	}

	public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
		this.httpServletRequest = httpServletRequest;
	}

	public String getJspDispatch() {
		return jspDispatch;
	}

	public void setJspDispatch(String jspDispatch) {
		this.jspDispatch = jspDispatch;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

}
