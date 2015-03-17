package org.xdams.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.lang3.StringEscapeUtils;

public class StringsUtils {
	public static String escapeJson(String value) {
		String s = value;
		s = StringEscapeUtils.escapeXml(s);
		s = s.replaceAll("\r", "");
		s = s.replaceAll("\n", " ");
		s = s.replaceAll("&gt;", ">");
		s = s.replaceAll("&lt;", "<");
		s = s.replaceAll("&apos;", "'");
		s = s.replaceAll("\\\\", "\\\\\\\\");
		s = s.replaceAll("&quot;", "\\\\\"");
		return "\"" + s + "\"";
	}

	public static String trimAll(String input) {
		try {
			String outPut = input;
			if (outPut.indexOf("  ") != -1 || outPut.indexOf(" \r ") != -1 || outPut.indexOf(" \n ") != -1 || outPut.indexOf(" \r") != -1 || outPut.indexOf(" \n") != -1) {
				while (outPut.indexOf(" \r ") != -1) {
					outPut = outPut.replaceAll(" \r ", "\r ");
				}
				while (outPut.indexOf(" \n ") != -1) {
					outPut = outPut.replaceAll(" \n ", "\n ");
				}
				while (outPut.indexOf(" \r") != -1) {
					outPut = outPut.replaceAll(" \r", "\r ");
				}
				while (outPut.indexOf(" \n") != -1) {
					outPut = outPut.replaceAll(" \n", "\n ");
				}
				while (outPut.indexOf("  ") != -1) {
					outPut = outPut.replaceAll("  ", " ");
				}
				return outPut.trim();
			} else {
				return outPut.trim();
			}
		} catch (Exception e) {
			return null;
		}
	}

	public static String escapeSingleApex(String s) {
		String result = "";
		try {
			if (s == null || s.trim().equals(""))
				return "";
			StringBuffer sb = new StringBuffer();
			char[] a = s.toCharArray();
			for (int n = 0; n < a.length; n++) {
				if (a[n] == '\'') {
					sb.append("\\");
				}
				sb.append(a[n]);
			}
			result = sb.toString().replaceAll("\"", "&quot;");
		} catch (Exception e) {

		}
		return result;
	}

	public static String postForString(URL url, String data) throws IOException {
		String result = "";
		// System.out.println();
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);
		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		wr.write(data);
		wr.flush();

		// Get the response
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = rd.readLine()) != null) {
			result += line + "\n";
		}
		wr.close();
		rd.close();

		return result;
	}

	public static String[] estraiUrl(String input) {
		Pattern p = Pattern.compile("<a(.*?)href=\"(.*?)\"(.*)?");
		Matcher m = p.matcher(input);

		List<String> output = new ArrayList<String>();
		while (m.find()) {
			output.add(m.group(2).replaceAll("&", "&amp;"));
			input = m.group(3);
			m = p.matcher(input);
		}
		if (output.size() > 0)
			return output.toArray(new String[output.size()]);
		else
			return null;
	}

	static public String clearMessyCode(String text) {
		// System.out.println(text);
		StringBuffer stringBuffer = new StringBuffer();

		try {
			Pattern regex = Pattern.compile("(href=\"[^\"]*)<a .*?>(.*?)</a>(.*?>)(.*?</a>)");
			Matcher regexMatcher = regex.matcher(text);
			while (regexMatcher.find()) {
				regexMatcher.appendReplacement(stringBuffer, "$1$2$3$4");
			}
			regexMatcher.appendTail(stringBuffer);
		} catch (PatternSyntaxException ex) {
		}
		text = stringBuffer.toString();
		stringBuffer = new StringBuffer();

		try {
			Pattern regex = Pattern.compile("(<a [^<]*?>[^<]*?)(<a [^<]*?>)([^<]*?)(</a>)([^<]*?</a>)");
			Matcher regexMatcher = regex.matcher(text);
			while (regexMatcher.find()) {
				regexMatcher.appendReplacement(stringBuffer, "$1$3$5");
			}
			regexMatcher.appendTail(stringBuffer);
		} catch (PatternSyntaxException ex) {
		}
		text = stringBuffer.toString();

		return text;
	}
}
