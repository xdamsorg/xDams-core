package org.xdams.utility;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.HTML;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;

//import static com.regesta.cms.utility.SimpleLogic.*;
public class CommonUtils
{
	public static final Pattern htmlTagPattern = Pattern.compile("<\\s*\\/?\\s*(\\w*?)((\\s+.*?)|(\\s*))\\/?\\s*>");

	public static final Pattern titleUrlPattern = Pattern.compile("<\\s*\\/?\\s*(\\w*?)((\\s+.*?)|(\\s*))\\/?\\s*>");

	public static final String escapeCharacters = "#;&,.+*~':\"!^$[]()=>|/";

	public static void main(String[] args) {

		System.out.println(CommonUtils.escapeJqueryName(".c.did.dao[@type='documenti grafici'][1].resource.text()"));
	}


	public static String escapeJqueryName(String value)
	{
		for(int i = 0; i<escapeCharacters.length(); i++)
		{
			String replace = Character.toString(escapeCharacters.charAt(i));
			if(value.contains(replace))
				value = value.replace(replace, "\\\\"+replace);
		}
		return value;
	}

	public static String titleUrl(String urlStr, String encodeType, Integer maxChar) {
		if (urlStr == null) {
			return "";
		}
		// System.out.println("CommonUtils.titleUrl() maxChar " + maxChar);
		if (maxChar == null) {
			maxChar = 120;
		}
		if (encodeType == null) {
			encodeType = "UTF-8";
		}

		try {
//		    System.out.println("1 "+urlStr);
		    urlStr = StringEscapeUtils.unescapeXml(urlStr);
//		    System.out.println("2 "+urlStr);
		    urlStr = deleteHTMLTag(urlStr, "");
//		    System.out.println("3 "+urlStr);
		    urlStr = StringUtils.abbreviate(urlStr, maxChar);
//		    System.out.println("4 "+urlStr);
		    urlStr = stripPunctuation(urlStr, '-');
//		    System.out.println("5 "+urlStr);
		    return URLEncoder.encode(String.valueOf(urlStr), encodeType);
		} catch (Exception uee) {
			return "";
		}
	}

	public static String stripPunctuation(String s, char separator) {
		StringBuilder sb = new StringBuilder();
		char[] cs = s.toCharArray();
		for (int i = 0; i < cs.length; i++) {
			if (Character.isLetterOrDigit(cs[i])) {
				sb.append(cs[i]);
			} else {
				if (sb.length() > 1 && sb.charAt(sb.length() - 1) != separator) {
					if(Character.isSpaceChar(cs[i])){
						sb.append(separator);
					}

				}
			}
		}
		return sb.toString();
	}

 	public static String deleteHTMLTag(String str, String replacement) {
		if (str == null) {
			return "";
		}
		Matcher m = htmlTagPattern.matcher(str);
		StringBuffer result = new StringBuffer();

		while (m.find()) {
			if (HTML.getTag(m.group(1).toLowerCase()) != null) {
				m.appendReplacement(result, replacement);
			}
		}
		m.appendTail(result);

		return result.toString();

		// for (HTML.Tag tag : HTML.getAllTags()) {
		// if (str == null) {
		// return "";
		// }
		// String regex = "<\\s*\\/?\\s*" + tag.toString() + "((\\s+.*?)|(\\s*))\\/?\\s*>";
		// Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		// Matcher matcher = p.matcher(str);
		// if (matcher.find()) {
		// str = matcher.replaceAll(replacement);
		// }
		// // var pattern:RegExp = new RegExp("<\\s*\\/?\\s*"+tagsList[i]+"((\\s+.*?)|(\\s*))\\/?\\s*>", "gi");
		// // htmlString = htmlString.replace(pattern, "");
		// }
		// return str;

	}

	public static String join(String sep, String... values) {
		if (values == null || values.length == 0)
			return "";

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < values.length; i++) {
			if (StringUtils.isBlank(values[i]))
				continue;

			sb.append(values[i]).append(sep);
		}

		if (sb.length() > 0) {
			int sepidx = sb.length() - sep.length();
			if (sb.substring(sepidx).equals(sep))
				sb.delete(sepidx, sb.length());
		}

		return sb.toString();
	}

	public static String join(String sep, List<String> values) {
		return join(sep, values.toArray(new String[values.size()]));
	}

	public static String joinMulti(String seps, String... values) {
		if (values == null || values.length == 0)
			return "";

		StringBuilder sb = new StringBuilder();
		Pattern p = Pattern.compile("[^\\\\]?\\((.*?[^\\\\]?)\\)");// revisare.. ce un bug.. esempio ( $\\) ) non prende il parentesi ma ( $\\)) si lo fa...
		Matcher m = p.matcher(seps);

		boolean found = m.find();
		String lastGroup = null;
		for (int i = 0; i < values.length; i++) {
			if (StringUtils.isBlank(values[i]))
				continue;
			if (!found && lastGroup == null)
				throw new RuntimeException("non ci sono sufficenti separatori");
			try {
				m.group(1);
				lastGroup = m.group(1).replaceAll("\\\\\\)", ")").replaceAll("\\\\\\(", "(");
			} catch (java.lang.IllegalStateException e) {
			}

			sb.append(values[i]).append(lastGroup);
			found = m.find();
		}

		if (sb.length() > 0) {
			int sepidx = sb.length() - lastGroup.length();
			if (sb.substring(sepidx).equals(lastGroup))
				sb.delete(sepidx, sb.length());
		}

		return sb.toString();
	}

	public static String joinMulti(String sep, List<String> values) {
		return join(sep, values.toArray(new String[values.size()]));
	}

	public static String formatStringDate(String date, String from, String to) {

		SimpleDateFormat formatter = new SimpleDateFormat(from);
		SimpleDateFormat formatter_2 = new SimpleDateFormat(to, Locale.ITALIAN);
		Date dataConvertita = null;
		String dataString = "";

		try {
			dataConvertita = formatter.parse(date);
			dataString = formatter_2.format(dataConvertita);
		} catch (Exception e) {
			// TODO
		}

		return dataString;
	}

	public static Map<String, String> buildMapFromRequestParameters(HttpServletRequest request) {
		Map<String, String> valuesMap = new HashMap<String, String>();

		for (Enumeration<String> e = request.getParameterNames(); e.hasMoreElements();) {
			String name = e.nextElement();
			valuesMap.put(name, request.getParameter(name));
		}

		return valuesMap;
	}

	public static Map<String, String> buildMapFromRequest(HttpServletRequest request) {
		Map<String, String> valuesMap = new HashMap<String, String>();

		for (Enumeration<String> e = request.getParameterNames(); e.hasMoreElements();) {
			String name = e.nextElement();
			valuesMap.put(name, request.getParameter(name));
		}

		for (Enumeration<Object> e = request.getAttributeNames(); e.hasMoreElements();) {
			String name = e.nextElement().toString();
			valuesMap.put(name, request.getAttribute(name).toString());
		}

		return valuesMap;
	}

	public static String GenericAdvIfNotEmpty(String templateString, String... strings) {
		Map<String, String> valuesMap = new HashMap<String, String>();
		String resolvedString = "";
		try {
			for (int i = 0; i < strings.length; i++) {
				if (StringUtils.substringBetween(strings[i], "${", "}$") != null) {
					// String labelVal = StringUtils.substringBetween(strings[i], "${", "}$");
					String[] strings2 = StringUtils.substringsBetween(strings[i], "${", "}$");
					String beforeVal = "";
					String afterVal = "";
					try {
						beforeVal = strings2[0];
						afterVal = strings2[1];
					} catch (Exception e) {
						// e.printStackTrace();
					}
					try {
						// System.out.println("prim: "+strings[i]);
						strings[i] = strings[i].replaceAll("\\$\\{.*?\\}\\$", "");
						// System.out.println("dopo: "+strings[i]);
						// System.out.println(labelVal);
					} catch (Exception e) {
						e.printStackTrace();
					}

					if (strings[i] != null && !strings[i].trim().equals("null") && !strings[i].trim().equals("")) {
						// System.out.println("UtilityVari.AdvIfNotEmpty()");
						strings[i] = beforeVal + strings[i] + afterVal;
					}
				}
				if (strings[i] == null || strings[i].equals("null")) {
					strings[i] = "";
				}
				valuesMap.put("" + (i) + "", strings[i]);

			}
			// System.out.println(valuesMap);
			boolean isEmptyValue = true;
			for (String string : valuesMap.values()) {
				// System.out.println(string);
				if (!string.equals("")) {
					isEmptyValue = false;
					break;
				}
			}
			if (isEmptyValue) {
				return "";
			}
			StrSubstitutor sub = new StrSubstitutor(valuesMap);
			resolvedString = sub.replace(templateString);
		} catch (Exception e) {
			resolvedString = "";
		}

		return resolvedString;
	}
}