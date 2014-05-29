package org.xdams.managing.bean;

import java.io.Serializable;
import java.util.ArrayList;

import org.xdams.page.view.bean.ManagingBean;

public class ModifyAutherBean extends ManagingBean implements Serializable{

	private String jspDispatch = "";

	private ElementToFindBean elementToFindBean = new ElementToFindBean();

	private String archivioAlias = "";

	private String query = "";

	private String archivioDescr = "";

	private ArrayList arrModifyAutherBean = new ArrayList();

	public ElementToFindBean getElementToFindBean() {
		return elementToFindBean;
	}

	public void setElementToFindBean(ElementToFindBean elementToFindBean) {
		this.elementToFindBean = elementToFindBean;
	}

	public String getJspDispatch() {
		return jspDispatch;
	}

	public void setJspDispatch(String jspDispatch) {
		this.jspDispatch = jspDispatch;
	}

	/**
	 * @return Returns the archivioAlias.
	 */
	public String getArchivioAlias() {
		return archivioAlias;
	}

	/**
	 * @param archivioAlias
	 *            The archivioAlias to set.
	 */
	public void setArchivioAlias(String archivioAlias) {
		this.archivioAlias = archivioAlias;
	}

	/**
	 * @return Returns the archivioDescr.
	 */
	public String getArchivioDescr() {
		return archivioDescr;
	}

	/**
	 * @param archivioDescr
	 *            The archivioDescr to set.
	 */
	public void setArchivioDescr(String archivioDescr) {
		this.archivioDescr = archivioDescr;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	/**
	 * @return Returns the arrModifyAutherBean.
	 */
	public ArrayList getArrModifyAutherBean() {
		return arrModifyAutherBean;
	}

	/**
	 * @param arrModifyAutherBean
	 *            The arrModifyAutherBean to set.
	 */
	public void setArrModifyAutherBean(ArrayList arrModifyAutherBean) {
		this.arrModifyAutherBean = arrModifyAutherBean;
	}

	/**
	 * @param addArrModifyAutherBean
	 *            to add element in arrModifyAutherBean
	 * 
	 */
	public void addArrModifyAutherBean(ModifyAutherBean modifyAutherBean) {
		arrModifyAutherBean.add(modifyAutherBean);
	}

}
