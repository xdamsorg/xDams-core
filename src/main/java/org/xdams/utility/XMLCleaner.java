package org.xdams.utility;

import org.apache.commons.lang3.StringUtils;

public class XMLCleaner {

	/**
	 * @param fullMode
	 *            true = per eliminare dall'xml i metatag <?xw-er?> e <?xw-sr?>
	 * 
	 */
	public static void main(String[] args) {
		System.out.println(clearIso("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<root><managing><?xml version=\"1.0\" encoding=\"ISO-8859-1\"?></root><?xml version=\"1.0\" encoding=\"ISO-8859-1\"?></managing><?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>"));
	}

	public static String clearXwXML(String docXML, boolean fullMode) {
		if (fullMode) {
			docXML = docXML.replaceAll("<.xw-er.>", "");
			docXML = docXML.replaceAll("<.xw-sr.>", "");
		}
		// docXML = docXML.replaceAll("<.xw-nest [^>]*.>","");
		// docXML = docXML.replaceAll("<.xw-meta DbmsVer=\".*.\".*.><.xw-crc [^>]*.>","");
		// docXML = docXML.replaceAll("<.xw-crc [^>]*.>","");
		// docXML = docXML.replaceAll("<.xw-meta Dbms.*.><.xw-crc [^>]*.>","");
		// docXML = docXML.replaceAll("<.xw-ar [^>]*.>","");
		docXML = docXML.replaceAll("<\\?xw-crc [^>]*>", "");
		docXML = docXML.replaceAll("<\\?xw-meta [^>]*>", "");

		return docXML;
	}
	
	
	public static String clearXwFullXML(String docXML, boolean fullMode) {
		if (fullMode) {
			docXML = docXML.replaceAll("<.xw-er.>", "");
			docXML = docXML.replaceAll("<.xw-sr.>", "");
		}
		docXML = docXML.replaceAll("<\\?xw-crc [^>]*>", "");
		docXML = docXML.replaceAll("<\\?xw-meta [^>]*>", "");
		docXML = docXML.replaceAll(" xmlns:xw=\"http://www.3di.it/ns/xw-200303121136\"", "");

		docXML = docXML.replaceAll("<xw_doc nrecord=\"\\d\\d*\">", "");
		docXML = docXML.replaceAll("</xw_doc>", "");

		docXML = docXML.replaceAll("<rsp>", "");
		docXML = docXML.replaceAll("</rsp>", "");

		docXML = docXML.replaceAll("<global_info .*/>", "");
		docXML = docXML.replaceAll("<rsp ack=\"\\d\\d*\" e=\"\\d\\d*\">", "");

		return docXML;
	}
	

	public static String clearMultipleIso(String docXML) {
		if (docXML.startsWith("<?xml")) {
			String isoDeclared = docXML.substring(0, docXML.indexOf("?>") + 2);
			// System.err.println(isoDeclared);
			docXML = StringUtils.replace(docXML, isoDeclared, "");
			// docXML = docXML.replaceAll(isoDeclared,"");
			docXML = isoDeclared + docXML;
		}
		return docXML;
	}

	public static String clearIso(String docXML) {
		if (docXML.startsWith("<?xml")) {
			docXML = docXML.substring(docXML.indexOf("?>") + 2).trim();
		}
		return docXML;
	}

}
