package org.xdams.xmlengine.connection.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xdams.exception.ConnectionException;
import org.xdams.user.bean.Archive;
import org.xdams.xw.XWConnection;
import org.xdams.xw.XWDriverManager;
import org.xdams.xw.exception.XWException;

@Component
public class ConnectionManager {

	private static final Logger logger = LoggerFactory.getLogger(ConnectionManager.class);

	public ConnectionManager() {
	}

	public XWConnection getConnection(Archive archive) throws ConnectionException {
		XWConnection xwconn = null;
		try {
			System.out.println("ConnectionManager.getConnection() " + archive);
			xwconn = XWDriverManager.getConnection("org.xdams.xw.XWDriver", "regesta://" + archive.getHost() + ":" + archive.getPort() + "/" + archive.getAlias() + "/pne=" + archive.getPne() + ";", "lettore", "");
		} catch (Exception e) {
			logger.debug("ERROR getConnection error=" + e.getMessage(), xwconn);
			throw new ConnectionException("ERROR getConnection error=" + e.getMessage() + "; archive:" + archive);
		}
		return xwconn;
	}

	public XWConnection getConnection(String dbName, String serverExtraway, String portExtraway, String thePne) throws Exception {
		XWConnection xwconn = null;
		try {
			xwconn = XWDriverManager.getConnection("org.xdams.xw.XWDriver", "regesta://" + serverExtraway + ":" + portExtraway + "/" + dbName + "/pne=" + thePne + ";", "lettore", "");
		} catch (Exception e) {
			throw new Exception(" ERRORE XWDriverManager serverExtraway=" + serverExtraway + " portExtraway=" + portExtraway + " dbName=" + dbName + " thePne=" + thePne + " error=" + e.getMessage());
		}
		return xwconn;
	}

	public void closeConnection(XWConnection xwconn) throws Exception {
		try {
			if (xwconn != null && !xwconn.isClosed())
				xwconn.close();
		} catch (XWException e1) {
			throw new Exception(e1.toString());
		}
	}
}
