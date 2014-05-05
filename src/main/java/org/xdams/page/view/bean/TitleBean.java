package org.xdams.page.view.bean;

import java.io.Serializable;

import it.highwaytech.db.HierPath;

public class TitleBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;

	private String title = "";

	private HierPath hierPath = null;

	private int firstSon = -1;

	private String physDoc = "";
	
	private String archive = "";
	
	private String xmlDoc = "";
	/**
	 * @return Returns the firstSon. 
	 */
	public int getFirstSon() {
		return firstSon;
	}

	/**
	 * @param firstSon
	 *            The firstSon to set.
	 */
	public void setFirstSon(int firstSon) {
		this.firstSon = firstSon;
	}

	/**
	 * @return Returns the hierPath.
	 */
	public HierPath getHierPath() {
		return hierPath;
	}

	/**
	 * @param hierPath
	 *            The hierPath to set.
	 */
	public void setHierPath(HierPath hierPath) {
		this.hierPath = hierPath;
	}

	/**
	 * @return Returns the physDoc.
	 */
	public String getPhysDoc() {
		return physDoc;
	}

	/**
	 * @param physDoc
	 *            The physDoc to set.
	 */
	public void setPhysDoc(String physDoc) {
		this.physDoc = physDoc;
	}

	/**
	 * @return Returns the title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            The title to set.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return Returns the archive.
	 */
	public String getArchive() {
		return archive;
	}

	/**
	 * @param archive The archive to set.
	 */
	public void setArchive(String archive) {
		this.archive = archive;
	}

	public String getXmlDoc() {
		return xmlDoc;
	}

	public void setXmlDoc(String xmlDoc) {
		this.xmlDoc = xmlDoc;
	}

	@Override
	public String toString() {
		return "TitleBean [title=" + title + ", hierPath=" + hierPath + ", firstSon=" + firstSon + ", physDoc=" + physDoc + ", archive=" + archive + ", xmlDoc=" + xmlDoc + "]";
	}

}
