package org.xdams.xw;

import java.util.Properties;

import org.xdams.xw.exception.XWException;


public class XWConnectionManager {

	private String aFileProp = null;

	private Properties aProp = null;

	public XWConnectionManager(String fileProp) {
		aFileProp = fileProp;
	}

	public XWConnectionManager(Properties prop) {
		aProp = prop;
	}

	public XWConnection getXWConnection() throws XWException {
		XWConnection result = null;
		 if (aProp != null)
			result = new XWConnection(aProp);

		return result;
	}
}
