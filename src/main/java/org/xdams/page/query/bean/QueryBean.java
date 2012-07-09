package org.xdams.page.query.bean;

import java.io.Serializable;

public class QueryBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String db = "";

	private String query = "";

	private String tot = "";

	private String lastUpdate = "";

	private int totNumDoc = -1;
	
	private boolean lastQuery = false;

	/**
	 * @return Returns the lastUpdate.
	 */
	public String getLastUpdate() {
		return lastUpdate;
	}

	/**
	 * @param lastUpdate
	 *            The lastUpdate to set.
	 */
	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	/**
	 * @return Returns the totNumDoc.
	 */
	public int getTotNumDoc() {
		return totNumDoc;
	}

	/**
	 * @param totNumDoc
	 *            The totNumDoc to set.
	 */
	public void setTotNumDoc(int totNumDoc) {
		this.totNumDoc = totNumDoc;
	}

	public String getDb() {
		return db;
	}

	public void setDb(String db) {
		this.db = db;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getTot() {
		return tot;
	}

	public void setTot(String tot) {
		this.tot = tot;
	}

	@Override
	public String toString() {
		return "QueryBean [db=" + db + ", query=" + query + ", tot=" + tot + ", lastUpdate=" + lastUpdate + ", totNumDoc=" + totNumDoc + ", lastQuery=" + lastQuery + "]";
	}

	public boolean isLastQuery() {
		return lastQuery;
	}

	public void setLastQuery(boolean lastQuery) {
		this.lastQuery = lastQuery;
	}

}
