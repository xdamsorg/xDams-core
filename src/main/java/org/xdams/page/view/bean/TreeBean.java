package org.xdams.page.view.bean;

import java.util.Vector;

public class TreeBean {

	int docStart = 1;

	int docToggle = -1;

	int docCount = 25;

	String hierStatus = "0,1,0"; // 2,0,0

	boolean backward = false;

	boolean upEnabled = false;

	boolean downEnabled = false;

	private int firstDocNumber = -1;

	private int lastDocNumber = -1;

	Vector vectHierTitle = new Vector();

	Vector vectHierBrowserBean = new Vector();

	

	public boolean isBackward() { 
		return backward;
	}

	public void setBackward(boolean backward) {
		this.backward = backward;
	}

	public int getDocCount() {
		return docCount;
	}

	public void setDocCount(int docCount) {
		this.docCount = docCount;
	}

	public int getDocStart() {
		return docStart;
	}

	public void setDocStart(int docStart) {
		this.docStart = docStart;
	}

	public int getDocToggle() {
		return docToggle;
	}

	public void setDocToggle(int docToggle) {
		this.docToggle = docToggle;
	}

	public String getHierStatus() {
		return hierStatus;
	}

	public void setHierStatus(String hierStatus) {
		this.hierStatus = hierStatus;
	}

	public Vector getVectHierTitle() {
		return vectHierTitle;
	}

	public void setVectHierTitle(Vector vectHierTitle) {
		this.vectHierTitle = vectHierTitle;
	}

	public boolean isDownEnabled() {
		return downEnabled;
	}

	public void setDownEnabled(boolean downEnabled) {
		this.downEnabled = downEnabled;
	}

	public boolean isUpEnabled() {
		return upEnabled;
	}

	public void setUpEnabled(boolean upEnabled) {
		this.upEnabled = upEnabled;
	}

	public Vector getVectHierBrowserBean() {
		return vectHierBrowserBean;
	}

	public void setVectHierBrowserBean(Vector vectHierBrowserBean) {
		this.vectHierBrowserBean = vectHierBrowserBean;
	}

	public int getFirstDocNumber() {
		return firstDocNumber;
	}

	public void setFirstDocNumber(int firstDocNumber) {
		this.firstDocNumber = firstDocNumber;
	}

	public int getLastDocNumber() {
		return lastDocNumber;
	}

	public void setLastDocNumber(int lastDocNumber) {
		this.lastDocNumber = lastDocNumber;
	}

	 

}
