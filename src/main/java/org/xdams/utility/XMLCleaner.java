package org.xdams.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		docXML = docXML.replaceAll("<\\?xw-nest [^>]*>", "");
		docXML = docXML.replaceAll(" xmlns:xw=\"http://www.3di.it/ns/xw-200303121136\"", "");

		docXML = docXML.replaceAll("<xw_doc nrecord=\"\\d\\d*\">", "");
		docXML = docXML.replaceAll("</xw_doc>", "");

		docXML = docXML.replaceAll("<rsp>", "");
		docXML = docXML.replaceAll("</rsp>", "");

		docXML = docXML.replaceAll("<global_info .*/>", "");
		docXML = docXML.replaceAll("<rsp ack=\"\\d\\d*\" e=\"\\d\\d*\">", "");

		return docXML;
	}

	public static String clearXwFullXMLNew(String docXML, boolean fullMode) {

		StringBuilder sb = new StringBuilder(docXML);
		docXML = null;
		if (fullMode) {
//			docXML = docXML.replaceAll("<.xw-er.>", "");
			replaceAll(sb, "<.xw-er.>", "");
//			docXML = docXML.replaceAll("<.xw-sr.>", "");
			replaceAll(sb, "<.xw-sr.>", "");
		}
//		docXML = docXML.replaceAll("<\\?xw-crc [^>]*>", "");
		replaceAll(sb, "<\\?xw-crc [^>]*>", "");
//		docXML = docXML.replaceAll("<\\?xw-meta [^>]*>", "");
		replaceAll(sb, "<\\?xw-meta [^>]*>", "");

//		docXML = docXML.replaceAll("<\\?xw-nest [^>]*>", "");
		replaceAll(sb, "<\\?xw-nest [^>]*>", "");
//		docXML = docXML.replaceAll(" xmlns:xw=\"http://www.3di.it/ns/xw-200303121136\"", "");
		replaceAll(sb, " xmlns:xw=\"http://www.3di.it/ns/xw-200303121136\"", "");
//		docXML = docXML.replaceAll("<xw_doc nrecord=\"\\d\\d*\">", "");
		replaceAll(sb, "<xw_doc nrecord=\"\\d\\d*\">", "");

//		docXML = docXML.replaceAll("</xw_doc>", "");
		replaceAll(sb, "</xw_doc>", "");
//		docXML = docXML.replaceAll("<rsp>", "");
		replaceAll(sb, "<rsp>", "");

//		docXML = docXML.replaceAll("</rsp>", "");
		replaceAll(sb, "</rsp>", "");


//		docXML = docXML.replaceAll("<global_info .*/>", "");
		replaceAll(sb, "<global_info .*/>", "");
//		docXML = docXML.replaceAll("<rsp ack=\"\\d\\d*\" e=\"\\d\\d*\">", "");
		replaceAll(sb, "<rsp ack=\"\\d\\d*\" e=\"\\d\\d*\">", "");


		return sb.toString();
	}

	public static void replaceAll(StringBuilder sb, String find, String replacement) {
		Pattern pattern = Pattern.compile(find);
		Matcher m = pattern.matcher(sb);
		int start = 0;
		while (m.find(start)) {
			sb.replace(m.start(), m.end(), replacement);
			start = m.start() + replacement.length();
		}
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
