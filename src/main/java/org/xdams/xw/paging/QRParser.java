package org.xdams.xw.paging;

import it.highwaytech.db.QueryResult;

import java.io.Serializable;
import java.util.Vector;

public class QRParser implements Serializable {

	private int pageCount;

	private int eleXpage;

	private Vector pageList;

	private int primaPagina;

	private int ultimaPagina;

	private String idArchive;

	private String idQR = "";

	//private QueryResult queryResult = null;

	private String physDoc = "";

	private int tot;
	
	private int perpage = 10;
	
	private int qrElements = 0;
	
	public QRParser(QueryResult qr, int eleXpage, int from, int to) {
		pageCount = 0;
		this.eleXpage = 0; 
		pageList = null;
		primaPagina = 0;
		ultimaPagina = 0;
		idArchive = "";
		idQR = "";
		tot = 0;
		tot = to - from;
		this.eleXpage = eleXpage;
		if (qr.elements > 0) {
			pageList = new Vector();
		} else {
			pageList = new Vector();
		}
		if (tot % eleXpage == 0) {
			pageCount = tot / eleXpage;
		} else {
			pageCount = tot / eleXpage + 1;
		}
		QRPage qRPage = null;
		int pagerCount = 0;
		int y = 0;
		int index = from;
		for (y = 0; y < tot; y++) {
			if (y == 0 || y % eleXpage == 0) {
				qRPage = new QRPage();
				pagerCount++;
				qRPage.setNumPage(pagerCount);
			}
			qRPage.addElement(new Integer(index));
			if (y % eleXpage == 0) {
				pageList.addElement(qRPage);
			}
			index++;
		}

		int appo = 0;
		int gap = 10;
		if (pageList.size() < 10) {
			gap = pageList.size();
		}
		for (int i = 0; i < pageList.size(); i++) {
			int numPage = ((QRPage) pageList.elementAt(i)).getNumPage();
			int totPage = pageList.size();
			Vector pageToShow = new Vector();
			for (int x = appo; x < appo + gap; x++) {
				String pToShow = Integer.toString(x + 1);
				pageToShow.addElement(pToShow);
				if (x + 1 < totPage) {
					continue;
				}
				if (numPage == totPage && !((String) pageToShow.lastElement()).equals(Integer.toString(numPage))) {
					pageToShow.addElement(Integer.toString(numPage));
				}
				break;
			}

			if (numPage % gap == 0) {
				appo += gap;
			}
			((QRPage) pageList.elementAt(i)).setPageToShow(pageToShow);
		}

	}

	public QRPage getPage(int nPage) {
		try {
			return (QRPage) pageList.elementAt(nPage - 1);
		} catch (Exception e) {
			return new QRPage();
		}

	}

	public QRPage getNext10Page(int thisPage) {
		try {
			return (QRPage) pageList.elementAt((thisPage - 1) + 10);
		} catch (ArrayIndexOutOfBoundsException e) {
			return (QRPage) pageList.lastElement();
		}
	}

	public QRPage getNextPage(int thisPage) {
		try {
			return (QRPage) pageList.elementAt((thisPage - 1) + 1);
		} catch (ArrayIndexOutOfBoundsException e) {
			return (QRPage) pageList.lastElement();
		}
	}

	public QRPage getBefore10Page(int thisPage) {
		try {
			return (QRPage) pageList.elementAt(thisPage - 1 - 10);
		} catch (ArrayIndexOutOfBoundsException e) {
			return (QRPage) pageList.firstElement();
		}
	}

	public QRPage getBeforePage(int thisPage) {
		try {
			return (QRPage) pageList.elementAt(thisPage - 1 - 1);
		} catch (ArrayIndexOutOfBoundsException e) {
			return (QRPage) pageList.firstElement();
		}
	}

	public QRPage getLastPage() {
		return (QRPage) pageList.lastElement();
	}

	public QRPage getFirsPage() {
		return (QRPage) pageList.firstElement();
	}

	private void getPageToShow(int nPage) {
		Vector pageToShow = new Vector();
	}

	public String getIdArchive() {
		return idArchive;
	}

	public void setIdArchive(String string) {
		idArchive = string;
	}

	public String getIdQR() {
		return idQR;
	}

	public void setIdQR(String string) {
		idQR = string;
	}

	public int getTot() {
		return tot;
	}
 
	public String getPhysDoc() {
		return physDoc;
	}

	public void setPhysDoc(String physDoc) {
		this.physDoc = physDoc;
	}

	public int getPerpage() {
		return perpage;
	}

	public void setPerpage(int perpage) {
		this.perpage = perpage;
	}

	public int getQrElements() {
		return qrElements;
	}

	public void setQrElements(int qrElements) {
		this.qrElements = qrElements;
	}
}