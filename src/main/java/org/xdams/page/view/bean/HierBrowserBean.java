package org.xdams.page.view.bean;

import java.util.Arrays;

import it.highwaytech.db.HierPath;

import org.xdams.xml.builder.XMLBuilder;

public class HierBrowserBean {

	private String hier = "";

	private int docNumber = -1;

	private int depth = 0;

	private boolean hasSons = false;

	private int firstDocNumber = -1;

	private int lastDocNumber = -1;

	private boolean opened = false;

	private String title = "";

	private boolean lastChild = false;

	private boolean fatherLastChild = false;

	private boolean[] fathersLastChild = null;

	private HierPath hierPath = null;

	private XMLBuilder xmlBuilder = null;

	public XMLBuilder getXmlBuilder() {
		return xmlBuilder;
	}

	public void setXmlBuilder(XMLBuilder xmlBuilder) {
		this.xmlBuilder = xmlBuilder;
	}

	public HierPath getHierPath() {
		return hierPath;
	}

	public void setHierPath(HierPath hierPath) {
		this.hierPath = hierPath;
	}

	public String getHier() {
		return hier;
	}

	public void setHier(String hier) {
		this.hier = hier;
	}

	public int getDocNumber() {
		return docNumber;
	}

	public void setDocNumber(int docNumber) {
		this.docNumber = docNumber;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public boolean isHasSons() {
		return hasSons;
	}

	public void setHasSons(boolean hasSons) {
		this.hasSons = hasSons;
	}

	public int getFirstDocNumber() {
		return firstDocNumber;
	}

	public void setFirstDocNumber(int firstDocNumber) {
		this.firstDocNumber = firstDocNumber;
	}

	public boolean isOpened() {
		return opened;
	}

	public void setOpened(boolean opened) {
		this.opened = opened;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getLastDocNumber() {
		return lastDocNumber;
	}

	public void setLastDocNumber(int lastDocNumber) {
		this.lastDocNumber = lastDocNumber;
	}

	public boolean isFatherLastChild() {
		return fatherLastChild;
	}

	public void setFatherLastChild(boolean fatherLastChild) {
		this.fatherLastChild = fatherLastChild;
	}

	public boolean[] getFathersLastChild() {
		return fathersLastChild;
	}

	public void setFathersLastChild(boolean[] fathersLastChild) {
		this.fathersLastChild = fathersLastChild;
	}

	public boolean isLastChild() {
		return lastChild;
	}

	public void setLastChild(boolean lastChild) {
		this.lastChild = lastChild;
	}

	@Override
	public String toString() {
		return "HierBrowserBean [hier=" + hier + ", docNumber=" + docNumber + ", depth=" + depth + ", hasSons=" + hasSons + ", firstDocNumber=" + firstDocNumber + ", lastDocNumber=" + lastDocNumber + ", opened=" + opened + ", title=" + title + ", lastChild=" + lastChild + ", fatherLastChild="
				+ fatherLastChild + ", fathersLastChild=" + Arrays.toString(fathersLastChild) + ", hierPath=" + hierPath + ", xmlBuilder=" + xmlBuilder + "]";
	}

}
