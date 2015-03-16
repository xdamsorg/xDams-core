package org.xdams.xw;

import it.highwaytech.broker.Broker;
import it.highwaytech.broker.ServerCommand;
import it.highwaytech.broker.XMLCommand;
import it.highwaytech.db.DbKey;
import it.highwaytech.db.Doc;
import it.highwaytech.db.HierPath;
import it.highwaytech.db.QueryResult;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;

import org.xdams.xw.exception.XWException;
import org.xdams.xw.utility.Key;
import org.xdams.xw.utility.Xml_NumDoc;

public class XWConnection extends Broker {
	private String theHost = "";

	private String theDb = "";

	private String theUserDB = "";

	private String thePasswordDB = "";

	private int thePort = -1;

	private String thePne = "";

	private String thePnce = "";

	public int connection = -1;

	private boolean isClosed = true;

	private Doc doc = null;

	private int adjacency = -1;

	public XWConnection() throws XWException {

	}

	public XWConnection(Properties prop) throws XWException {
		try {
			theHost = prop.getProperty(XWConstant.XW_HOST);
			theDb = prop.getProperty(XWConstant.XW_DB);
			theUserDB = prop.getProperty(XWConstant.XW_USER);
			thePasswordDB = prop.getProperty(XWConstant.XW_PASSWORD);
			thePort = Integer.parseInt(prop.getProperty(XWConstant.XW_PORT));
			thePne = prop.getProperty(XWConstant.XW_PNE);
			thePnce = prop.getProperty(XWConstant.XW_PNCE);
			XWconnect();
		} catch (XWException e) {
			throw e;
		}
	}

	private void XWconnect() throws XWException {

		try {
			connection = acquireConnection(theHost, thePort, theDb, "", "", -1);
			boolean esitoConn = Connect(connection, theHost, thePort, theDb, -1, theUserDB, thePasswordDB);
			isClosed = false;

		} catch (SQLException e) {
			isClosed = true;
			try {
				if (connection > 0)
					releaseConnection(connection);
				connection = -1;
			} catch (SQLException ex) {
				connection = -1;
				throw new XWException(ex.getMessage());
			} finally {
				if (isClosed == false) {
					connection = -1;
					isClosed = true;
				}

			}
			throw new XWException(e.getMessage());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#close()
	 */
	public void close() throws XWException {
		try {
			releaseConnection(connection);
			connection = -1;
			isClosed = true;
		} catch (SQLException ex) {
			throw new XWException(ex.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#isClosed()
	 */
	public boolean isClosed() {
		return isClosed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#executeUpdate(java.lang.String, java.lang.String)
	 */
	public void executeUpdate(String xmlToInsert, String xQuery) throws SQLException, XWException {
		QueryResult qr = null;
		int docNumber = -1;
		String theLock = "";
		if (xQuery != null) {
			qr = find(connection, theDb, xQuery, "", ServerCommand.find_SORT, adjacency, 0, null);
			docNumber = getNumDoc(connection, theDb, qr, 0);
		}
		if (docNumber != -1) {
			XMLCommand theCommandLoad = new XMLCommand(XMLCommand.LoadDocument, XMLCommand.LoadDocument_Lock, docNumber);
			String xDoc = XMLCommand(connection, theDb, theCommandLoad.toString());
			theLock = XMLCommand.getLockCode(xDoc);
		}
		// encoding remove
		xmlToInsert = xmlToInsert.replaceAll("\\<\\?xml(.+?)\\?\\>", "").trim();
		XMLCommand theCommand = new XMLCommand(XMLCommand.SaveDocument, XMLCommand.SaveDocument_Save, docNumber, xmlToInsert, thePne, thePnce, theLock);
		String result = "";
		result = XMLCommand(connection, theDb, theCommand.toString());
		if (result.indexOf("dtl") == -1) {
			throw new XWException("An Error occurred while processing insert/update");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#insert(java.lang.String)
	 */
	public int insert(String xmlToInsert) throws SQLException, XWException {
		QueryResult qr = null;
		int docNumber = -1;
		String theLock = "";
		// encoding remove
		xmlToInsert = xmlToInsert.replaceAll("\\<\\?xml(.+?)\\?\\>", "").trim();
		XMLCommand theCommand = new XMLCommand(XMLCommand.SaveDocument, XMLCommand.SaveDocument_Save, docNumber, xmlToInsert, thePne, thePnce, theLock);
		String result = "";
		result = XMLCommand(connection, theDb, theCommand.toString());
		int newNrecord = Integer.parseInt(XMLCommand.getDval(result, "ndoc"));
		if (result.indexOf("dtl") == -1) {
			throw new XWException("An Error occurred while processing insert");
		}
		return newNrecord;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#executeUpdateByDocNumber(java.lang.String, int)
	 */
	public void executeUpdateByDocNumber(String xmlToInsert, int docNumber) throws SQLException, XWException {
		String theLock = "";
		if (docNumber != -1) {
			XMLCommand theCommandLoad = new XMLCommand(XMLCommand.LoadDocument, XMLCommand.LoadDocument_Lock, docNumber);
			String xDoc = XMLCommand(connection, theDb, theCommandLoad.toString());
			theLock = XMLCommand.getLockCode(xDoc);
		}
		// encoding remove
		xmlToInsert = xmlToInsert.replaceAll("\\<\\?xml(.+?)\\?\\>", "").trim();
		// System.out.println("xmlToInsert: "+xmlToInsert);
		XMLCommand theCommand = new XMLCommand(XMLCommand.SaveDocument, XMLCommand.SaveDocument_Save, docNumber, xmlToInsert, thePne, thePnce, theLock);
		// System.out.println("theCommand: " + theCommand);
		String result = "";
		result = XMLCommand(connection, theDb, theCommand.toString());
		if (result.indexOf("dtl") == -1) {
			throw new XWException("An Error occurred while processing insert/update");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#resetDB(java.lang.String, boolean)
	 */
	public void resetDB(String theDbPath, boolean reloadDB) throws SQLException, XWException {
		if (theDbPath != null) {
			String verbo_crea_azzera_archivio = "genera/inizializza archivio";
			String verbo = "genera/inizializza archivio";
			boolean createDb = true;
			int iPort = thePort;
			if (connection >= 0 && Connect(connection, theHost, iPort, createDb ? null : theDb, -1, theUserDB, thePasswordDB)) {
				createDb(connection, theDbPath, theDb, true);
				if (reloadDB)
					reloadDb(connection, theDb);
			}
		} else {
			throw new XWException("theDbPath must not be null");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#reloadDB()
	 */
	public void reloadDB() throws SQLException, XWException {
		reloadDb(connection, theDb);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#select(java.lang.String)
	 */
	public String[] select(String xQuery) throws SQLException, XWException {
		String[] result = null;
		QueryResult qr = null;
		if (xQuery != null) {
			qr = find(connection, theDb, xQuery, "", ServerCommand.find_SORT, adjacency, 0, null);
			if (qr != null) {
				result = new String[qr.elements];
				for (int i = 0; i < qr.elements; i++) {
					Doc doc = getDoc(connection, theDb, qr, i, 0, ServerCommand.load_noHilight);
					result[i] = doc.XML();
				}
			}
		} else {
			throw new XWException("xQuery must not be null");
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#select(java.lang.String, java.lang.String)
	 */
	public String[] select(String xQuery, String sortParamethers) throws SQLException, XWException {
		String[] result = null;
		QueryResult qr = null;
		if (xQuery != null) {
			qr = find(connection, theDb, xQuery, sortParamethers, ServerCommand.find_SORT, adjacency, 0, null);
			if (qr != null) {
				result = new String[qr.elements];
				for (int i = 0; i < qr.elements; i++) {
					Doc doc = getDoc(connection, theDb, qr, i, 0, ServerCommand.load_noHilight);
					result[i] = doc.XML();
				}
			}
		} else {
			throw new XWException("xQuery must not be null");
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#toStringArray(it.highwaytech.db.QueryResult)
	 */
	public String[] toStringArray(QueryResult qr) throws SQLException, XWException {
		String[] result = null;
		if (qr != null) {
			result = new String[qr.elements];
			for (int i = 0; i < qr.elements; i++) {
				Doc doc = getDoc(connection, theDb, qr, i, 0, ServerCommand.load_noHilight);
				result[i] = doc.XML();
			}
		} else {
			throw new XWException("xQuery must not be null");
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#selectQR(java.lang.String)
	 */
	public QueryResult selectQR(String xQuery) throws SQLException, XWException {
		QueryResult qr = selectQR(xQuery, "", ServerCommand.find_SORT, adjacency);
		return qr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#selectQR(java.lang.String, java.lang.String, byte, int)
	 */
	public QueryResult selectQR(String xQuery, String sort, byte serverCommand, int adj) throws SQLException, XWException {
		QueryResult qr = null;
		if (xQuery != null) {
			qr = find(connection, theDb, xQuery, sort, serverCommand, adj, 0, null);
		} else {
			throw new XWException("xQuery must not be null");
		}
		return qr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#getNumDocFromQRElement(it.highwaytech.db.QueryResult, int)
	 */
	public int getNumDocFromQRElement(QueryResult qr, int index) throws SQLException, XWException {
		if (qr != null) {
			return getNumDoc(connection, theDb, qr, index);
		} else {
			throw new XWException("QueryResult must not be null");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#getSonsFromNumDoc(int)
	 */
	public QueryResult getSonsFromNumDoc(int numDoc) throws SQLException, XWException {

		QueryResult qr = null;

		qr = findSons(connection, theDb, numDoc);

		return qr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#selectObject(java.lang.String)
	 */
	public Vector selectObject(String xQuery) throws SQLException, XWException {
		Vector result = null;
		QueryResult qr = null;
		if (xQuery != null) {
			qr = find(connection, theDb, xQuery, "", ServerCommand.find_SORT, adjacency, 0, null);
			if (qr != null) {
				result = new Vector();
				for (int i = 0; i < qr.elements; i++) {
					Doc doc = getDoc(connection, theDb, qr, i, 0, ServerCommand.load_noHilight);
					result.addElement(new Xml_NumDoc(doc.XML(), doc.numDoc));
				}
			}
		} else {
			throw new XWException("xQuery must not be null");
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#delete(java.lang.String)
	 */
	public void delete(String xQuery) throws SQLException, XWException {

		QueryResult qr = null;
		if (xQuery != null) {
			qr = find(connection, theDb, xQuery, "", ServerCommand.find_SORT, adjacency, 0, null);
			if (qr != null) {

				for (int i = 0; i < qr.elements; i++) {
					Doc doc = getDoc(connection, theDb, qr, i, 0, null);
					removeDoc(connection, theDb, doc);
				}
			}
		} else {
			throw new XWException("xQuery must not be null");
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#getQRFromSelId(java.lang.String)
	 */
	public QueryResult getQRFromSelId(String selId) throws XWException, SQLException {
		QueryResult qr = null;
		if (selId != null) {

			qr = getQuery(connection, theDb, selId);

		} else {
			throw new XWException("xQuery must not be null");
		}
		return qr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#setCurrentSet(it.highwaytech.db.QueryResult)
	 */
	public void setCurrentSet(QueryResult qr) throws XWException, SQLException {

		if (qr != null) {
			setCurrentSet(connection, theDb, qr);

		} else {
			throw new XWException("QueryResult must not be null");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#addToQueryResult(it.highwaytech.db.QueryResult, it.highwaytech.db.QueryResult)
	 */
	public QueryResult addToQueryResult(QueryResult qrTo, QueryResult qrFrom) throws XWException, SQLException {
		QueryResult result = null;
		if (qrTo != null && qrFrom != null) {
			result = addToQueryResult(connection, theDb, qrTo, qrFrom, null);
		} else {
			throw new XWException("QueryResult must not be null");
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#removeDocFormQR(java.lang.String, int)
	 */
	public void removeDocFormQR(String selId, int index) throws SQLException, XWException {

		QueryResult qr = null;
		if (selId != null) {

			qr = getQuery(connection, theDb, selId);
			if (qr != null) {
				int docNumber = getNumDoc(connection, theDb, qr, index);
				removeDoc(connection, theDb, docNumber);
			}
		} else {
			throw new XWException("xQuery must not be null");
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#deleteFormQR(java.lang.String, int)
	 */
	public QueryResult deleteFormQR(String selId, int index) throws SQLException, XWException {

		QueryResult qr = null;
		if (selId != null) {

			qr = getQuery(connection, theDb, selId);
			if (qr != null) {

				removeDocFromQueryResult(connection, theDb, qr, index);

			}
		} else {
			throw new XWException("xQuery must not be null");
		}
		return qr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#deleteFormQR(it.highwaytech.db.QueryResult, int)
	 */
	public QueryResult deleteFormQR(QueryResult qr, int index) throws SQLException, XWException {
		if (qr != null) {
			removeDocFromQueryResult(connection, theDb, qr, index);
		}
		return qr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#delete(int)
	 */
	public void delete(int docNumber) throws SQLException, XWException {
		removeDoc(connection, theDb, docNumber);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#buildStoredCollection(java.lang.String)
	 */
	public String buildStoredCollection(String collectionName) throws XWException, SQLException {

		String selid = null;
		if (collectionName != null) {

			selid = collectionOpen(connection, theDb, collectionName);
		} else {
			throw new XWException("xQuery must not be null");
		}
		return selid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#createCollection(java.lang.String, java.lang.String)
	 */
	public void createCollection(String selId, String collectionName) throws SQLException, XWException {

		if (selId != null && collectionName != null) {

			collectionCreate(connection, theDb, collectionName);
			collectionSave(connection, theDb, selId, collectionName);

		} else {
			throw new XWException("xQuery must not be null");
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#getSelectionId(java.lang.String)
	 */
	public String getSelectionId(String phrase) throws SQLException, XWException {

		QueryResult qr = null;
		String result = null;
		if (phrase != null) {

			qr = find(connection, theDb, phrase, "", ServerCommand.find_SORT, adjacency, 0, null);
			result = qr.id;

		} else {
			throw new XWException("xQuery must not be null");
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#getQRfromPhrase(java.lang.String)
	 */
	public QueryResult getQRfromPhrase(String phrase) throws SQLException, XWException {

		QueryResult qr = null;

		if (phrase != null) {

			qr = find(connection, theDb, phrase, "", ServerCommand.find_SORT, adjacency, 0, null);

		} else {
			throw new XWException("xQuery must not be null");
		}
		return qr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#getQRfromPhrase(java.lang.String, java.lang.String)
	 */
	public QueryResult getQRfromPhrase(String phrase, String sortCriteria) throws SQLException, XWException {

		QueryResult qr = null;

		if (phrase != null) {

			qr = find(connection, theDb, phrase, sortCriteria, ServerCommand.find_SORT, adjacency, 0, null);

		} else {
			throw new XWException("xQuery must not be null");
		}
		return qr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#getSelectionIdFormHier(int)
	 */
	public String getSelectionIdFormHier(int nDoc) throws SQLException, XWException {

		QueryResult qr = null;
		String result = null;
		if (nDoc != 0) {

			qr = findHier(connection, theDb, nDoc, ServerCommand.findhier_DESCENDANT_OR_SELF);
			result = qr.id;

		} else {
			throw new XWException("nDoc must not be null");
		}
		return result;
	}

	/**
	 * @deprecated
	 * */
	public QueryResult getQRFormHier(int nDoc, boolean includeSelf) throws SQLException, XWException {

		return getQRFromHier(nDoc, includeSelf);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#getQRFromHier(int, boolean)
	 */
	public QueryResult getQRFromHier(int nDoc, boolean includeSelf) throws SQLException, XWException {

		QueryResult qr = null;
		byte mode = ServerCommand.findhier_DESCENDANT;
		if (includeSelf) {
			mode = ServerCommand.findhier_DESCENDANT_OR_SELF;
		}
		if (nDoc != 0) {

			qr = findHier(connection, theDb, nDoc, mode);

		} else {
			throw new XWException("nDoc must not be null");
		}
		return qr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#executeCMD(java.lang.String)
	 */
	public void executeCMD(String cmd) throws SQLException, XWException {
		if (cmd != null) {
			this.XMLCommand(connection, theDb, cmd);
		} else {
			throw new XWException("cmd must not be null");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#selectSingleKey(java.lang.String, int, java.lang.String, java.lang.String)
	 */
	public Vector selectSingleKey(String searchAlias, int totResult, String orientation, String startParam) throws SQLException, XWException {

		Vector keys = null;

		if (searchAlias != null) {

			byte prev_next = ServerCommand.btree_NEXTKEY;

			if (orientation.equalsIgnoreCase("up"))
				prev_next = ServerCommand.btree_NEXTKEY;
			else if (orientation.equalsIgnoreCase("down"))
				prev_next = ServerCommand.btree_PREVKEY;

			int maxResult = 10;

			if (totResult > 0)
				maxResult = totResult;

			String start = "";

			if (startParam != null)
				start = startParam;
			try {

				Vector result = getIdxKeys(connection, theDb, "TABELLA", searchAlias, start, "", maxResult, prev_next, ServerCommand.btree_FREQUENZE);

				if (result != null && result.size() > 0) {
					keys = new Vector();
					for (int i = 0; i < result.size(); i++) {

						keys.addElement(new Key(result.elementAt(i), getFrequency(result, i)));
					}
				}
			} catch (UnsupportedEncodingException e) {
				throw new SQLException(e.getMessage());
			}

			return keys;

		} else {
			throw new XWException("searchAlias must not be null");
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#selectSingleKey(java.lang.String, int, java.lang.String, java.lang.String, java.lang.String)
	 */
	public Vector selectSingleKey(String searchAlias, int totResult, String orientation, String startParam, String part) throws SQLException, XWException {

		Vector keys = null;
		if (searchAlias != null) {

			byte prev_next = ServerCommand.btree_NEXTKEY;

			if (orientation.equalsIgnoreCase("up"))
				prev_next = ServerCommand.btree_NEXTKEY;
			else if (orientation.equalsIgnoreCase("down"))
				prev_next = ServerCommand.btree_PREVKEY;

			int maxResult = 10;

			if (totResult > 0)
				maxResult = totResult;

			String start = "";

			if (startParam != null)
				start = startParam;
			try {

				Vector result = getIdxKeys(connection, theDb, "TABELLA", searchAlias, start, part, maxResult, prev_next, ServerCommand.btree_FREQUENZE);
				// Vector result = getIdxKeys(connection,theDb,"TABELLA",searchAlias,"",start,maxResult,prev_next,ServerCommand.btree_FREQUENZE);
				if (result != null && result.size() > 0) {
					keys = new Vector();
					for (int i = 0; i < result.size(); i++) {
						// System.out.println(result.elementAt(i));
						keys.addElement(new Key(result.elementAt(i), getFrequency(result, i)));
					}
				}
			} catch (UnsupportedEncodingException e) {
				throw new SQLException(e.getMessage());
			}

			return keys;

		} else {
			throw new XWException("searchAlias must not be null");
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#selectSingleKey(java.lang.String, int, java.lang.String, java.lang.String, int, int)
	 */
	public Vector selectSingleKey(String searchAlias, int totResult, String orientation, String startParam, int minFr, int maxFr) throws SQLException, XWException {

		Vector keys = null;
		int min = 1;
		int max = 9999;
		if (minFr > 0)
			min = minFr;
		if (maxFr > 0)
			max = maxFr;
		if (searchAlias != null) {

			byte prev_next = ServerCommand.btree_NEXTKEY;

			if (orientation.equalsIgnoreCase("up"))
				prev_next = ServerCommand.btree_NEXTKEY;
			else if (orientation.equalsIgnoreCase("down"))
				prev_next = ServerCommand.btree_PREVKEY;

			int maxResult = 10;

			if (totResult > 0)
				maxResult = totResult;

			String start = "";

			if (startParam != null)
				start = startParam;
			try {

				Vector result = getIdxKeys(connection, theDb, "TABELLA", searchAlias, start, "", maxResult, prev_next, ServerCommand.btree_FREQUENZE, min, max, null);
				// Vector result = getIdxKeys(connection,theDb,"TABELLA",searchAlias,"",start,maxResult,prev_next,ServerCommand.btree_FREQUENZE);
				if (result != null && result.size() > 0) {
					keys = new Vector();
					for (int i = 0; i < result.size(); i++) {
						// System.out.println(result.elementAt(i));
						keys.addElement(new Key(result.elementAt(i), getFrequency(result, i)));
					}
				}
			} catch (UnsupportedEncodingException e) {
				throw new SQLException(e.getMessage());
			}

			return keys;

		} else {
			throw new XWException("searchAlias must not be null");
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#selectSingleKey(java.lang.String, int, java.lang.String, java.lang.String, int, int, java.lang.String)
	 */
	public Vector selectSingleKey(String searchAlias, int totResult, String orientation, String startParam, int minFr, int maxFr, String part) throws SQLException, XWException {

		Vector keys = null;
		int min = 1;
		int max = 9999;
		if (minFr > 0)
			min = minFr;
		if (maxFr > 0)
			max = maxFr;
		if (searchAlias != null) {

			byte prev_next = ServerCommand.btree_NEXTKEY;

			if (orientation.equalsIgnoreCase("up"))
				prev_next = ServerCommand.btree_NEXTKEY;
			else if (orientation.equalsIgnoreCase("down"))
				prev_next = ServerCommand.btree_PREVKEY;

			int maxResult = 10;

			if (totResult > 0)
				maxResult = totResult;

			String start = "";

			if (startParam != null)
				start = startParam;
			try {

				Vector result = getIdxKeys(connection, theDb, "TABELLA", searchAlias, start, part, maxResult, prev_next, ServerCommand.btree_FREQUENZE, min, max, null);
				// Vector result = getIdxKeys(connection,theDb,"TABELLA",searchAlias,"",start,maxResult,prev_next,ServerCommand.btree_FREQUENZE);
				if (result != null && result.size() > 0) {
					keys = new Vector();
					for (int i = 0; i < result.size(); i++) {
						// System.out.println(result.elementAt(i));
						keys.addElement(new Key(result.elementAt(i), getFrequency(result, i)));
					}
				}
			} catch (UnsupportedEncodingException e) {
				throw new SQLException(e.getMessage());
			}

			return keys;

		} else {
			throw new XWException("searchAlias must not be null");
		}

	}

	/**
	 * @deprecated
	 * */
	public Vector selectFilteredKey(String filterXQuery, String searchAlias, int totResult, String orientation, String startParam) throws SQLException, XWException {
		return selectFilteredKey(filterXQuery, searchAlias, totResult, orientation, startParam, "");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#selectFilteredKey(java.lang.String, java.lang.String, int, java.lang.String, java.lang.String, java.lang.String)
	 */
	public Vector selectFilteredKey(String filterXQuery, String searchAlias, int totResult, String orientation, String startParam, String part) throws SQLException, XWException {

		Vector keys = null;
		if (searchAlias != null) {

			byte prev_next = ServerCommand.btree_NEXTKEY;

			if (orientation.equalsIgnoreCase("up"))
				prev_next = ServerCommand.btree_NEXTKEY;
			else if (orientation.equalsIgnoreCase("down"))
				prev_next = ServerCommand.btree_PREVKEY;

			int maxResult = 10;

			if (totResult > 0)
				maxResult = totResult;

			String start = "";

			if (startParam != null)
				start = startParam;

			// byte mode = ServerCommand.btree_FREQUENZE;
			// mode |=ServerCommand.btree_SPETTRALE;

			byte mode = 1;
			mode |= 0x80;

			try {
				setCurrentSet(connection, theDb, find(connection, theDb, filterXQuery, "", 16, adjacency, 0, null));
				Vector result = getIdxKeys(connection, theDb, "TABELLA", searchAlias, start, part, maxResult, prev_next, mode, 1, getTotNumDoc(), null);

				// setCurrentSet(connection,theDb,find(connection, theDb, filterXQuery , "", ServerCommand.find_SORT, adjacency, 0, null));aaaaaa
				// Vector result = getIdxKeys(connection,theDb,"TABELLA",searchAlias,start,part,maxResult,prev_next,mode);

				if (result != null && result.size() > 0) {
					keys = new Vector();
					for (int i = 0; i < result.size(); i++) {

						keys.addElement(new Key(result.elementAt(i), getFrequency(result, i)));
					}
				}
			} catch (UnsupportedEncodingException e) {
				throw new SQLException(e.getMessage());
			}

			return keys;

		} else {
			throw new XWException("searchAlias must not be null");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#selectFilteredKey(java.lang.String, java.lang.String, int, java.lang.String, java.lang.String, int, int)
	 */
	public Vector selectFilteredKey(String filterXQuery, String searchAlias, int totResult, String orientation, String startParam, int minFr, int maxFr) throws SQLException, XWException {

		Vector keys = null;
		int min = 1;
		int max = 9999;
		if (minFr > 0)
			min = minFr;
		if (maxFr > 0)
			max = maxFr;
		if (searchAlias != null) {

			byte prev_next = ServerCommand.btree_NEXTKEY;

			if (orientation.equalsIgnoreCase("up"))
				prev_next = ServerCommand.btree_NEXTKEY;
			else if (orientation.equalsIgnoreCase("down"))
				prev_next = ServerCommand.btree_PREVKEY;

			int maxResult = 10;

			if (totResult > 0)
				maxResult = totResult;

			String start = "";

			if (startParam != null)
				start = startParam;

			byte mode = 1;
			mode |= 0x80;

			// byte mode = ServerCommand.btree_FREQUENZE;
			// mode |=ServerCommand.btree_SPETTRALE;
			try {

				// setCurrentSet(connection,theDb,find(connection, theDb, filterXQuery , "", ServerCommand.find_SORT, adjacency, 0, null));
				// Vector result = getIdxKeys(connection,theDb,"TABELLA",searchAlias,start,"",maxResult,prev_next,mode ,min,max,null);
				setCurrentSet(connection, theDb, find(connection, theDb, filterXQuery, "", 16, adjacency, 0, null));
				Vector result = getIdxKeys(connection, theDb, "TABELLA", searchAlias, start, "", maxResult, prev_next, mode, min, max, null);
				if (result != null && result.size() > 0) {
					keys = new Vector();
					for (int i = 0; i < result.size(); i++) {
						// System.out.println(result.elementAt(i));
						keys.addElement(new Key(result.elementAt(i), getFrequency(result, i)));
					}
				}
			} catch (UnsupportedEncodingException e) {
				throw new SQLException(e.getMessage());
			}

			return keys;

		} else {
			throw new XWException("searchAlias must not be null");
		}

	}

	/**
	 * @deprecated
	 * */
	public Vector selectFilteredKey(QueryResult qr, String searchAlias, int totResult, String orientation, String startParam, int minFr, int maxFr) throws SQLException, XWException {

		Vector keys = null;
		int min = 1;
		int max = 9999;
		if (minFr > 0)
			min = minFr;
		if (maxFr > 0)
			max = maxFr;
		if (searchAlias != null) {

			byte prev_next = ServerCommand.btree_NEXTKEY;

			if (orientation.equalsIgnoreCase("up"))
				prev_next = ServerCommand.btree_NEXTKEY;
			else if (orientation.equalsIgnoreCase("down"))
				prev_next = ServerCommand.btree_PREVKEY;

			int maxResult = 10;

			if (totResult > 0)
				maxResult = totResult;

			String start = "";

			if (startParam != null)
				start = startParam;

			// byte mode = ServerCommand.btree_FREQUENZE;
			// mode |=ServerCommand.btree_SPETTRALE;
			byte mode = 1;
			mode |= 0x80;
			try {

				setCurrentSet(connection, theDb, qr);
				Vector result = getIdxKeys(connection, theDb, "TABELLA", searchAlias, start, "", maxResult, prev_next, mode, min, max, null);
				// Vector result = getIdxKeys(connection,theDb,"TABELLA",searchAlias,"",start,maxResult,prev_next,ServerCommand.btree_FREQUENZE);
				if (result != null && result.size() > 0) {
					keys = new Vector();
					for (int i = 0; i < result.size(); i++) {
						// System.out.println(result.elementAt(i));
						keys.addElement(new Key(result.elementAt(i), getFrequency(result, i)));
					}
				}
			} catch (UnsupportedEncodingException e) {
				throw new SQLException(e.getMessage());
			}

			return keys;

		} else {
			throw new XWException("searchAlias must not be null");
		}

	}

	/**
	 * @deprecated
	 * */
	public Vector selectFilteredKey(QueryResult qr, String searchAlias, int totResult, String orientation, String startParam) throws SQLException, XWException {
		return selectFilteredKey(qr, searchAlias, totResult, orientation, startParam, "");
	}

	/**
	 * @deprecated
	 * */
	public Vector selectFilteredKey(QueryResult qr, String searchAlias, int totResult, String orientation, String startParam, String part) throws SQLException, XWException {

		Vector keys = null;
		if (searchAlias != null) {

			byte prev_next = ServerCommand.btree_NEXTKEY;

			if (orientation.equalsIgnoreCase("up"))
				prev_next = ServerCommand.btree_NEXTKEY;
			else if (orientation.equalsIgnoreCase("down"))
				prev_next = ServerCommand.btree_PREVKEY;
			int maxResult = 10;

			if (totResult > 0)
				maxResult = totResult;

			String start = "";

			if (startParam != null)
				start = startParam;

			// byte mode = ServerCommand.btree_FREQUENZE;
			// mode |=ServerCommand.btree_SPETTRALE;
			byte mode = 1;
			mode |= 0x80;
			try {

				setCurrentSet(connection, theDb, qr);
				Vector result = getIdxKeys(connection, theDb, "TABELLA", searchAlias, start, part, maxResult, prev_next, mode);

				// Vector result = getIdxKeys(connection,theDb,"TABELLA",searchAlias,"",start,maxResult,prev_next,ServerCommand.btree_FREQUENZE);
				if (result != null && result.size() > 0) {
					keys = new Vector();
					for (int i = 0; i < result.size(); i++) {
						// System.out.println(result.elementAt(i));
						keys.addElement(new Key(result.elementAt(i), getFrequency(result, i)));
					}
				}
			} catch (UnsupportedEncodingException e) {
				throw new SQLException(e.getMessage());
			}

			return keys;

		} else {
			throw new XWException("searchAlias must not be null");
		}

	}

	private int getFrequency(Vector keys, int i) {
		DbKey key = (DbKey) keys.get(i);

		return key.getKeyFrequency();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#sort(int, java.lang.String)
	 */
	public void sort(int parentNumDoc, String sortCriteria) throws SQLException, XWException {

		QueryResult qr = null;
		if (sortCriteria != null) {

			qr = findSons(connection, theDb, parentNumDoc);
			if (qr != null) {

				qr = sortQueryResult(connection, theDb, qr, sortCriteria, ServerCommand.subcmd_NONE);

				for (int i = 0; i < qr.elements; i++) {

					int numDoc = getNumDoc(connection, theDb, qr, i);
					docRelDelete(connection, theDb, ServerCommand.navigarel_PADREFIGLIO, parentNumDoc, numDoc);
					docRelInsert(connection, theDb, ServerCommand.navigarel_PADREFIGLIO, parentNumDoc, numDoc);
					// System.out.println(doc.XML());

				}

			}
		} else {
			throw new XWException("Sort criteria must not be null");
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#cut_paste(int, int, byte)
	 */
	public void cut_paste(int NumDoc01, int NumDoc02, byte navigationRule) throws SQLException, XWException {

		int numDoc = docRelNavigate(connection, theDb, ServerCommand.navigarel_FIGLIOPADRE, NumDoc01);
		try {
			docRelDelete(connection, theDb, ServerCommand.navigarel_PADREFIGLIO, numDoc, NumDoc01);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		docRelInsert(connection, theDb, navigationRule, NumDoc01, NumDoc02);

	}

	// docRelInsert(numdoc01,numdoc02,relazione)
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#docRelInsert(int, int, byte)
	 */
	public void docRelInsert(int NumDoc01, int NumDoc02, byte navigationRule) throws SQLException, XWException {
		docRelInsert(connection, theDb, navigationRule, NumDoc01, NumDoc02);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#deleteHier(int)
	 */
	public void deleteHier(int docNumber) throws SQLException, XWException {
		removeHier(connection, theDb, docNumber);
	}

	/**
	 * @deprecated
	 * */
	public String getXMLFromNumDoc(int numDoc) throws SQLException, XWException {

		XMLCommand theCommandLoad = new XMLCommand(XMLCommand.LoadDocument, XMLCommand.LoadDocument, numDoc);
		String xDoc = XMLCommand(connection, theDb, theCommandLoad.toString());
		return xDoc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#getSingleXMLFromNumDoc(int)
	 */
	public String getSingleXMLFromNumDoc(int numDoc) throws SQLException, XWException {
		// senza i commenti XW
		Doc doc = getDoc(connection, theDb, numDoc, 0, ServerCommand.load_noHilight);
		String xDoc = doc.XML();
		return xDoc;
	}

	// public String getSingleXMLFromNumDoc(int numDoc) throws SQLException, XWException {
	// String ilComando = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<cmd c=\"2\" bits=\"0\" d=\""+numDoc+"\"/></cmd>";
	// System.out.println("ilComando: " + ilComando);
	// String result = XMLCommand(connection, theDb, ilComando);
	// System.out.println("PRIMA result: " + result);
	// result = XMLCommand.getBstContent(result);
	// System.out.println("DOPO result: " + result);
	// return result;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#getSingleXMLFromQr(it.highwaytech.db.QueryResult, int, boolean)
	 */
	public String getSingleXMLFromQr(QueryResult qr, int index, boolean highlight) throws SQLException, XWException {
		String high = "";
		if (highlight)
			high = ServerCommand.load_hilight;
		else
			high = ServerCommand.load_noHilight;
		Doc doc = getDoc(connection, theDb, qr, index, 0, high);
		String xDoc = doc.XML();
		return xDoc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#getTitles(java.lang.String, java.lang.String)
	 */
	public Vector getTitles(String xQuery, String sortCriteria) throws SQLException, XWException {
		Vector result = null;
		if (xQuery != null && !xQuery.trim().equals("")) {
			QueryResult qr = getQRfromPhrase(xQuery, sortCriteria);
			if (qr != null && qr.elements != 0) {
				result = getTitles(connection, theDb, qr, 0, qr.elements);
			}
		} else {
			throw new XWException("xQuery must not be null");
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#getTitles(it.highwaytech.db.QueryResult)
	 */
	public Vector getTitles(QueryResult qr) throws SQLException, XWException {
		Vector result = null;
		if (qr != null && qr.elements != 0) {
			result = getTitles(connection, theDb, qr, 0, qr.elements);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#setTitleRole(java.lang.String)
	 */
	public void setTitleRole(String compositionRole) throws SQLException, XWException {
		if (compositionRole != null && !compositionRole.trim().equals(""))
			setTitleRule(connection, theDb, compositionRole);
		else
			throw new XWException("compositionRole must not be null");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#restoreTitleRole()
	 */
	public void restoreTitleRole() throws SQLException {
		restoreTitleRule(connection, theDb);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#getHierPath(int)
	 */
	public HierPath getHierPath(int numDoc) throws SQLException, XWException {
		return hierPath(connection, theDb, numDoc);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#getTotNumDoc()
	 */
	public int getTotNumDoc() throws SQLException {
		return getDb(connection, theDb).catDocs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#getTheDb()
	 */
	public String getTheDb() {
		return theDb;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#getTheHost()
	 */
	public String getTheHost() {
		return theHost;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#getThePort()
	 */
	public int getThePort() {
		return thePort;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#getNumDocFather(int)
	 */
	public int getNumDocFather(int docNumberChild) throws SQLException, XWException {
		return docRelNavigate(connection, theDb, ServerCommand.navigarel_FIGLIOPADRE, docNumberChild);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#getNumDocFirstSon(int)
	 */
	public int getNumDocFirstSon(int docNumberFather) throws SQLException, XWException {
		return docRelNavigate(connection, theDb, ServerCommand.navigarel_PADREFIGLIO, docNumberFather);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#getNumDocPreviousBrother(int)
	 */
	public int getNumDocPreviousBrother(int docNumber) throws SQLException, XWException {
		return docRelNavigate(connection, theDb, ServerCommand.navigarel_MAGGIOREMINORE, docNumber);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#getNumDocNextBrother(int)
	 */
	public int getNumDocNextBrother(int docNumber) throws SQLException, XWException {
		return docRelNavigate(connection, theDb, ServerCommand.navigarel_MINOREMAGGIORE, docNumber);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#getLastUpdate()
	 */
	public String getLastUpdate() throws XWException {
		String formatterDataInput = "yyyyMMdd";
		String formatterOutput = "EEEE d MMMM yyyy";
		DateFormat formatter = new SimpleDateFormat(formatterDataInput);
		java.text.SimpleDateFormat formatData = new java.text.SimpleDateFormat(formatterOutput, java.util.Locale.ITALIAN);
		String ultimoAgg = "";
		try {
			Vector leChiavi = selectSingleKey("UD,/xw/UserId/@DateMod", 1, "down", "9999");
			ultimoAgg = ((Key) leChiavi.get(0)).key.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Date date = (Date) formatter.parse(ultimoAgg);
			ultimoAgg = formatData.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ultimoAgg;
	}

	private Vector getKeys(QueryResult qr, String searchAlias, int totResult, String orientation, String startParam, int minFr, int maxFr, String part, String modeParam, boolean nullKey) throws SQLException, XWException {

		Vector keys = null;
		byte prev_next = ServerCommand.btree_NEXTKEY;
		String start = "";
		byte mode = ServerCommand.btree_FREQUENZE;
		if (searchAlias != null) {
			if (orientation.equalsIgnoreCase("up"))
				prev_next = ServerCommand.btree_NEXTKEY;
			else if (orientation.equalsIgnoreCase("down"))
				prev_next = ServerCommand.btree_PREVKEY;
			if (startParam != null)
				start = startParam;
			if (modeParam != null) {
				if (modeParam.equalsIgnoreCase("spettrale")) {
					mode = ServerCommand.btree_SPETTRALE;
				} else if (modeParam.equalsIgnoreCase("frequenze")) {
					mode = ServerCommand.btree_FREQUENZE;
				} else if (modeParam.equalsIgnoreCase("frequenze|spettrale")) {
					mode = ServerCommand.btree_SPETTRALE;
					mode |= ServerCommand.btree_FREQUENZE;
				}
			}
			try {
				if (qr != null) {
					setCurrentSet(connection, theDb, qr);
				}
				Vector result = getIdxKeys(connection, theDb, "TABELLA", searchAlias, startParam, part, totResult, prev_next, mode, minFr, maxFr, null);
				if (result != null && result.size() > 0) {
					keys = new Vector();
					boolean hasNullKey = false;
					for (int i = 0; i < result.size(); i++) {
						if (nullKey == false && ((DbKey) result.elementAt(i)).toString().equalsIgnoreCase("&null;")) {
							hasNullKey = true;
							break;
						}
						keys.addElement(new Key(result.elementAt(i), getFrequency(result, i)));
					}
					if (hasNullKey) {
						keys.removeAllElements();
						result = getIdxKeys(connection, theDb, "TABELLA", searchAlias, startParam, part, totResult + 1, prev_next, mode, minFr, maxFr, null);
						for (int i = 0; i < result.size(); i++) {
							if (!((DbKey) result.elementAt(i)).toString().equalsIgnoreCase("&null;")) {
								keys.addElement(new Key(result.elementAt(i), getFrequency(result, i)));
							}
						}
					}

				}
			} catch (UnsupportedEncodingException e) {
				throw new SQLException(e.getMessage());
			}
		} else {
			throw new SQLException("searchAlias must not be null");
		}
		return keys;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#getFilteredKeys(java.lang.String, java.lang.String, int, java.lang.String, java.lang.String)
	 */
	public Vector getFilteredKeys(String filterXQuery, String searchAlias, int totResult, String orientation, String startParam) throws SQLException, XWException {
		QueryResult qr = find(connection, theDb, filterXQuery, "", ServerCommand.find_SORT, adjacency, 0, null);
		return getKeys(qr, searchAlias, totResult, orientation, startParam, 1, getTotNumDoc(), "", null, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#getFilteredKeys(java.lang.String, java.lang.String, int, java.lang.String, java.lang.String, java.lang.String)
	 */
	public Vector getFilteredKeys(String filterXQuery, String searchAlias, int totResult, String orientation, String startParam, String part) throws SQLException, XWException {
		QueryResult qr = find(connection, theDb, filterXQuery, "", ServerCommand.find_SORT, adjacency, 0, null);
		return getKeys(qr, searchAlias, totResult, orientation, startParam, 1, getTotNumDoc(), part, null, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#getFilteredKeys(java.lang.String, java.lang.String, int, java.lang.String, java.lang.String, int, int)
	 */
	public Vector getFilteredKeys(String filterXQuery, String searchAlias, int totResult, String orientation, String startParam, int minFr, int maxFr) throws SQLException, XWException {
		QueryResult qr = find(connection, theDb, filterXQuery, "", ServerCommand.find_SORT, adjacency, 0, null);
		return getKeys(qr, searchAlias, totResult, orientation, startParam, minFr, maxFr, "", null, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#getFilteredKeys(java.lang.String, java.lang.String, int, java.lang.String, java.lang.String, int, int, java.lang.String)
	 */
	public Vector getFilteredKeys(String filterXQuery, String searchAlias, int totResult, String orientation, String startParam, int minFr, int maxFr, String part) throws SQLException, XWException {
		QueryResult qr = find(connection, theDb, filterXQuery, "", ServerCommand.find_SORT, adjacency, 0, null);
		return getKeys(qr, searchAlias, totResult, orientation, startParam, minFr, maxFr, part, null, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#getFilteredKeys(java.lang.String, java.lang.String, int, java.lang.String, java.lang.String, int, int, java.lang.String, java.lang.String)
	 */
	public Vector getFilteredKeys(String filterXQuery, String searchAlias, int totResult, String orientation, String startParam, int minFr, int maxFr, String part, String modeParam) throws SQLException, XWException {
		QueryResult qr = find(connection, theDb, filterXQuery, "", ServerCommand.find_SORT, adjacency, 0, null);
		return getKeys(qr, searchAlias, totResult, orientation, startParam, minFr, maxFr, part, modeParam, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#getFilteredKeys(java.lang.String, java.lang.String, int, java.lang.String, java.lang.String, boolean)
	 */
	public Vector getFilteredKeys(String filterXQuery, String searchAlias, int totResult, String orientation, String startParam, boolean nullKey) throws SQLException, XWException {
		QueryResult qr = find(connection, theDb, filterXQuery, "", ServerCommand.find_SORT, adjacency, 0, null);
		return getKeys(qr, searchAlias, totResult, orientation, startParam, 1, getTotNumDoc(), "", null, nullKey);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#getFilteredKeys(it.highwaytech.db.QueryResult, java.lang.String, int, java.lang.String, java.lang.String)
	 */
	public Vector getFilteredKeys(QueryResult qr, String searchAlias, int totResult, String orientation, String startParam) throws SQLException, XWException {
		return getKeys(qr, searchAlias, totResult, orientation, startParam, 1, getTotNumDoc(), "", null, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#getFilteredKeys(it.highwaytech.db.QueryResult, java.lang.String, int, java.lang.String, java.lang.String, java.lang.String)
	 */
	public Vector getFilteredKeys(QueryResult qr, String searchAlias, int totResult, String orientation, String startParam, String part) throws SQLException, XWException {
		return getKeys(qr, searchAlias, totResult, orientation, startParam, 1, getTotNumDoc(), part, null, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#getFilteredKeys(it.highwaytech.db.QueryResult, java.lang.String, int, java.lang.String, java.lang.String, int, int)
	 */
	public Vector getFilteredKeys(QueryResult qr, String searchAlias, int totResult, String orientation, String startParam, int minFr, int maxFr) throws SQLException, XWException {
		return getKeys(qr, searchAlias, totResult, orientation, startParam, minFr, maxFr, "", null, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#getFilteredKeys(it.highwaytech.db.QueryResult, java.lang.String, int, java.lang.String, java.lang.String, int, int, java.lang.String)
	 */
	public Vector getFilteredKeys(QueryResult qr, String searchAlias, int totResult, String orientation, String startParam, int minFr, int maxFr, String part) throws SQLException, XWException {
		return getKeys(qr, searchAlias, totResult, orientation, startParam, minFr, maxFr, part, null, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#getFilteredKeys(it.highwaytech.db.QueryResult, java.lang.String, int, java.lang.String, java.lang.String, int, int, java.lang.String, java.lang.String)
	 */
	public Vector getFilteredKeys(QueryResult qr, String searchAlias, int totResult, String orientation, String startParam, int minFr, int maxFr, String part, String modeParam) throws SQLException, XWException {
		return getKeys(qr, searchAlias, totResult, orientation, startParam, minFr, maxFr, part, modeParam, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#getFilteredKeys(it.highwaytech.db.QueryResult, java.lang.String, int, java.lang.String, java.lang.String, boolean)
	 */
	public Vector getFilteredKeys(QueryResult qr, String searchAlias, int totResult, String orientation, String startParam, boolean nullKey) throws SQLException, XWException {
		return getKeys(qr, searchAlias, totResult, orientation, startParam, 1, getTotNumDoc(), "", null, nullKey);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#getSingleKeys(java.lang.String, int, java.lang.String, java.lang.String)
	 */
	public Vector getSingleKeys(String searchAlias, int totResult, String orientation, String startParam) throws SQLException, XWException {
		return getKeys(null, searchAlias, totResult, orientation, startParam, 1, getTotNumDoc(), "", null, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#getSingleKeys(java.lang.String, int, java.lang.String, java.lang.String, java.lang.String)
	 */
	public Vector getSingleKeys(String searchAlias, int totResult, String orientation, String startParam, String part) throws SQLException, XWException {
		return getKeys(null, searchAlias, totResult, orientation, startParam, 1, getTotNumDoc(), part, null, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#getSingleKeys(java.lang.String, int, java.lang.String, java.lang.String, int, int)
	 */
	public Vector getSingleKeys(String searchAlias, int totResult, String orientation, String startParam, int minFr, int maxFr) throws SQLException, XWException {
		return getKeys(null, searchAlias, totResult, orientation, startParam, minFr, maxFr, "", null, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#getSingleKeys(java.lang.String, int, java.lang.String, java.lang.String, int, int, java.lang.String)
	 */
	public Vector getSingleKeys(String searchAlias, int totResult, String orientation, String startParam, int minFr, int maxFr, String part) throws SQLException, XWException {
		return getKeys(null, searchAlias, totResult, orientation, startParam, minFr, maxFr, part, null, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#getSingleKeys(java.lang.String, int, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public Vector getSingleKeys(String searchAlias, int totResult, String orientation, String startParam, String part, String modeParam) throws SQLException, XWException {
		return getKeys(null, searchAlias, totResult, orientation, startParam, 1, getTotNumDoc(), part, modeParam, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#getSingleKeys(java.lang.String, int, java.lang.String, java.lang.String, int, int, java.lang.String, java.lang.String)
	 */
	public Vector getSingleKeys(String searchAlias, int totResult, String orientation, String startParam, int minFr, int maxFr, String part, String modeParam) throws SQLException, XWException {
		return getKeys(null, searchAlias, totResult, orientation, startParam, minFr, maxFr, part, modeParam, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#getSingleKeys(java.lang.String, int, java.lang.String, java.lang.String, boolean)
	 */
	public Vector getSingleKeys(String searchAlias, int totResult, String orientation, String startParam, boolean nullKey) throws SQLException, XWException {
		return getKeys(null, searchAlias, totResult, orientation, startParam, 1, getTotNumDoc(), "", null, nullKey);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#getTitleFromNumDoc(int)
	 */
	public String getTitleFromNumDoc(int numdoc) throws SQLException, XWException {
		return getTitle(connection, theDb, numdoc).getTitle();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#getPnce()
	 */
	public String getPnce() {
		return thePnce;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#getPne()
	 */
	public String getPne() {
		return thePne;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#changeArchive(java.lang.String, java.lang.String)
	 */
	public void changeArchive(String logicDBName, String optionalPne) {
		this.theDb = logicDBName;
		if (optionalPne != null && !optionalPne.trim().equals("")) {
			this.thePne = optionalPne;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#getAdjacency()
	 */
	public int getAdjacency() {
		return adjacency;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.regesta.framework.xw.XMLEngine#setAdjacency(int)
	 */
	public void setAdjacency(int i) {
		adjacency = i;
	}
}
