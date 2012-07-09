package org.xdams.xw.paging;

import it.highwaytech.db.QueryResult;
import it.highwaytech.db.Title;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.xdams.page.view.bean.TitleBean;
import org.xdams.xw.XWConnection;
import org.xdams.xw.exception.XWException;


public class QRPage implements Serializable {

	private List elements = new ArrayList();

	private List indexes = new ArrayList();

	private List pageToShow = new ArrayList();

	private int numPage;

	public QRPage() {
		elements = new ArrayList();
		indexes = new ArrayList();
		pageToShow = new ArrayList();
		numPage = 0;
		indexes = new ArrayList();
	}

	public List loadElements(QueryResult qr, XWConnection xwConn) throws SQLException {
		elements = new ArrayList();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < indexes.size(); i++) {
			int num = ((Integer) indexes.get(i)).intValue();
			int numDoc = xwConn.getNumDoc(xwConn.connection, xwConn.getTheDb(), qr, num, buffer);
			Title titArch = xwConn.getTitle(xwConn.connection, xwConn.getTheDb(), qr, num);
			// elements.addElement(String.valueOf(numDoc) + "\260" + buffer.toString() + "[@archive@]" + titArch.getTitle());
			elements.add(String.valueOf(numDoc) + "\260" + titArch.getTitle());
		}

		return elements;
	}

	/* DIEGO: AGGIUNTO PRIMO FIGLIO E GERARCHIA! */
	public List loadTitlesElements(QueryResult qr, XWConnection xwConn) throws SQLException, XWException {
		elements = new ArrayList();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < indexes.size(); i++) {
			int num = ((Integer) indexes.get(i)).intValue();
			int numDoc = xwConn.getNumDoc(xwConn.connection, xwConn.getTheDb(), qr, num, buffer);
			Title titArch = xwConn.getTitle(xwConn.connection, xwConn.getTheDb(), qr, num);
			int firstSon = xwConn.getNumDocFirstSon(numDoc);
			String hierTitles = "";
			try {
				it.highwaytech.db.HierPath thePath = xwConn.getHierPath(numDoc);
				for (int a = 1; a < thePath.depth(); a++) {
					String titlePath = thePath.getTitle(a);
					hierTitles += "|@|" + titlePath;
				}
			} catch (Exception e) {
			}
			elements.add(String.valueOf(numDoc) + "\260" + titArch.getTitle() + "@firstSon@" + firstSon + "@hierPath@" + hierTitles);
		}
		return elements;
	}

	public List loadTitleBean(QueryResult qr, XWConnection xwConn) throws SQLException, XWException {
		elements = new ArrayList();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < indexes.size(); i++) {
			TitleBean titleBean = new TitleBean();
			int num = ((Integer) indexes.get(i)).intValue();
			int numDoc = xwConn.getNumDoc(xwConn.connection, xwConn.getTheDb(), qr, num, buffer);
			titleBean.setPhysDoc(String.valueOf(numDoc));
			Title titArch = xwConn.getTitle(xwConn.connection, xwConn.getTheDb(), qr, num);
			titleBean.setTitle(titArch.getTitle());
			int firstSon = xwConn.getNumDocFirstSon(numDoc);
			titleBean.setFirstSon(firstSon);
			try {
				it.highwaytech.db.HierPath thePath = xwConn.getHierPath(numDoc);
				titleBean.setHierPath(thePath);
			} catch (Exception e) {
			}
			elements.add(titleBean);
		}
		return elements;
	}

	public List loadElementsXML(QueryResult qr, XWConnection xwConn) throws SQLException, XWException {
		elements = new ArrayList();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < indexes.size(); i++) {

			int num = ((Integer) indexes.get(i)).intValue();
			int numDoc = xwConn.getNumDoc(xwConn.connection, xwConn.getTheDb(), qr, num, buffer);
			String titArch = xwConn.getSingleXMLFromNumDoc(numDoc);
			if (i == 0) {
				System.out.println("QRPage.loadElementsXML() indexes.size() " + indexes.size());
				System.out.println("QRPage.loadElementsXML() iiiiiiiiiiiiii " + i);
				System.out.println("QRPage.loadElementsXML() titArch " + titArch);
			}
			elements.add(String.valueOf(numDoc) + "\260" + String.valueOf(i) + "@@position@@" + titArch);
		}

		return elements;
	}

	public void addElement(Object index) {
		indexes.add(index);
	}

	public void setPageToShow(List pageToShow) {
		this.pageToShow = pageToShow;
	}

	public int getNumPage() {
		return numPage;
	}

	public List getPageToShow() {
		return pageToShow;
	}

	public void setNumPage(int i) {
		numPage = i;
	}

	public List getElements() {
		return elements;
	}
}
