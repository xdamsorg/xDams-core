package org.xdams.utility;

import java.io.File;
import java.net.URLEncoder;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
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
public class CommonUtils {
	public static final Pattern htmlTagPattern = Pattern.compile("<\\s*\\/?\\s*(\\w*?)((\\s+.*?)|(\\s*))\\/?\\s*>");

	public static final Pattern titleUrlPattern = Pattern.compile("<\\s*\\/?\\s*(\\w*?)((\\s+.*?)|(\\s*))\\/?\\s*>");

	public static final String escapeCharacters = "#;&,.+*~':\"!^$[]()=>|/";

	public static String OS = System.getProperty("os.name").toLowerCase();

	public static List<String> collectionExt = Arrays.asList(".doc", ".docx", ".log", ".msg", ".pages", ".rtf", ".txt", ".wpd", ".wps", ".csv", ".dat", ".efx", ".gbr", ".key", ".pps", ".ppt", ".pptx", ".sdf", ".tax2010", ".vcf", ".xml", ".aif", ".iff", ".m3u", ".m4a", ".mid", ".mp3", ".mpa", ".ra",
			".wav", ".wma", ".3g2", ".3gp", ".asf", ".asx", ".avi", ".flv", ".mov", ".mp4", ".mpg", ".rm", ".swf", ".vob", ".wmv", ".3dm", ".max", ".bmp", ".gif", ".jpg", ".jpeg", ".png", ".psd", ".pspimage", ".thm", ".tif", ".tiff", ".yuv", ".ai", ".drw", ".eps", ".ps", ".svg", ".indd", ".pct",
			".pdf", ".qxd", ".qxp", ".rels", ".xlr", ".xls", ".xlsx", ".accdb", ".db", ".dbf", ".mdb", ".pdb", ".sql", ".app", ".bat", ".cgi", ".com", ".exe", ".gadget", ".jar", ".pif", ".vb", ".wsf", ".gam", ".nes", ".rom", ".sav", ".dwg", ".dxf", ".gpx", ".kml", ".asp", ".cer", ".csr", ".css",
			".htm", ".html", ".js", ".jsp", ".php", ".rss", ".xhtml", ".8bi", ".plugin", ".xll", ".fnt", ".fon", ".otf", ".ttf", ".cab", ".cpl", ".cur", ".dll", ".dmp", ".drv", ".lnk", ".sys", ".cfg", ".ini", ".keychain", ".prf", ".bin", ".hqx", ".mim", ".uue", ".7z", ".deb", ".gz", ".pkg", ".rar",
			".rpm", ".sit", ".sitx", ".tar.gz", ".zip", ".zipx", ".dmg", ".iso", ".toast", ".vcd", ".c", ".class", ".cpp", ".cs", ".dtd", ".fla", ".java", ".m", ".pl", ".py", ".bak", ".gho", ".ori", ".tmp", ".dbx", ".msi", ".part", ".torrent");

	public static void main(String[] args) {

		// System.out.println(CommonUtils.escapeJqueryName(".c.did.dao[@type='documenti grafici'][1].resource.text()"));
		System.out
				.println(checkExt("<isattach>/alcorn/2.2_UA50.001.jpg¢/alcorn/2.2_UA50.002.jpg¢/alcorn/2.2_UA50.003.jpg¢/alcorn/2.2_UA50.004.jpg¢/alcorn/2.2_UA50.005.jpg¢/alcorn/2.2_UA50.006.jpg¢/alcorn/2.2_UA50.007.jpg¢/alcorn/2.2_UA50.008.jpg¢/alcorn/2.2_UA50.009.jpg¢/alcorn/2.2_UA50.010.jpg¢/alcorn/2.2_UA50.011.jpg¢/alcorn/2.2_UA50.012.jpg¢/alcorn/2.2_UA50.013.jpg¢/alcorn/2.2_UA50.014.jpg¢/alcorn/2.2_UA50.015.jpg¢/alcorn/2.2_UA50.016.jpg¢/alcorn/2.2_UA50.017.jpg¢/alcorn/2.2_UA50.018.jpg¢/alcorn/2.2_UA50.019.jpg¢/alcorn/2.2_UA50.020.jpg¢/alcorn/2.2_UA50.021.jpg¢/alcorn/2.2_UA50.022.jpg¢/alcorn/2.2_UA50.023.jpg¢/alcorn/2.2_UA50.024.jpg¢/alcorn/2.2_UA50.025.jpg¢/alcorn/2.2_UA50.026.jpg¢/alcorn/2.2_UA50.027.jpg¢/alcorn/2.2_UA50.028.jpg¢/alcorn/2.2_UA50.029.jpg¢/alcorn/2.2_UA50.030.jpg¢/alcorn/2.2_UA50.031.jpg¢/alcorn/2.2_UA50.032.jpg¢/alcorn/2.2_UA50.033.jpg¢/alcorn/2.2_UA50.034.jpg¢/alcorn/2.2_UA50.035.jpg¢/alcorn/2.2_UA50.036.jpg¢/alcorn/2.2_UA50.037.jpg¢/alcorn/2.2_UA50.038.jpg¢/alcorn/2.2_UA50.039.jpg¢/alcorn/2.2_UA50.040.jpg¢/alcorn/2.2_UA50.041.jpg¢/alcorn/2.2_UA50.042.jpg¢/alcorn/2.2_UA50.043.jpg¢/alcorn/2.2_UA50.044.jpg¢/alcorn/2.2_UA50.045.jpg¢/alcorn/2.2_UA50.046.jpg¢/alcorn/2.2_UA50.047.jpg¢/alcorn/2.2_UA50.048.jpg¢/alcorn/2.2_UA50.049.jpg¢/alcorn/2.2_UA50.050.jpg¢/alcorn/2.2_UA50.051.jpg¢/alcorn/2.2_UA50.052.jpg¢/alcorn/2.2_UA50.053.jpg¢/alcorn/2.2_UA50.054.jpg¢/alcorn/2.2_UA50.055.jpg¢/alcorn/2.2_UA50.056.jpg¢/alcorn/2.2_UA50.057.jpg¢/alcorn/2.2_UA50.058.jpg¢/alcorn/2.2_UA50.059.jpg¢/alcorn/2.2_UA50.060.jpg¢/alcorn/2.2_UA50.061.jpg¢/alcorn/2.2_UA50.062.jpg¢/alcorn/2.2_UA50.063.jpg¢/alcorn/2.2_UA50.064.jpg¢/alcorn/2.2_UA50.065.jpg¢/alcorn/2.2_UA50.066.jpg¢/alcorn/2.2_UA50.067.jpg¢/alcorn/2.2_UA50.068.jpg¢/alcorn/2.2_UA50.069.jpg¢/alcorn/2.2_UA50.070.jpg¢/alcorn/2.2_UA50.071.jpg¢/alcorn/2.2_UA50.072.jpg¢/alcorn/2.2_UA50.073.jpg¢/alcorn/2.2_UA50.074.jpg¢/alcorn/2.2_UA50.075.jpg¢/alcorn/2.2_UA50.076.jpg¢/alcorn/2.2_UA50.077.jpg¢/alcorn/2.2_UA50.078.jpg¢/alcorn/2.2_UA50.079.jpg¢/alcorn/2.2_UA50.080.jpg¢/alcorn/2.2_UA50.081.jpg¢/alcorn/2.2_UA50.082.jpg¢/alcorn/2.2_UA50.083.jpg¢/alcorn/2.2_UA50.084.jpg¢/alcorn/2.2_UA50.085.jpg¢/alcorn/2.2_UA50.086.jpg¢/alcorn/2.2_UA50.087.jpg¢/alcorn/2.2_UA50.088.jpg¢/alcorn/2.2_UA50.089.jpg¢/alcorn/2.2_UA50.090.jpg¢/alcorn/2.2_UA50.091.jpg¢/alcorn/2.2_UA50.092.jpg¢/alcorn/2.2_UA50.093.jpg¢/alcorn/2.2_UA50.094.jpg¢/alcorn/2.2_UA50.095.jpg¢/alcorn/2.2_UA50.096.jpg¢/alcorn/2.2_UA50.097.jpg¢/alcorn/2.2_UA50.098.jpg¢/alcorn/2.2_UA50.099.jpg¢/alcorn/2.2_UA50.100.jpg¢/alcorn/2.2_UA50.101.jpg¢/alcorn/2.2_UA50.102.jpg¢/alcorn/2.2_UA50.103.jpg¢/alcorn/2.2_UA50.104.jpg¢/alcorn/2.2_UA50.105.jpg¢/alcorn/2.2_UA50.106.jpg¢/alcorn/2.2_UA50.107.jpg¢/alcorn/2.2_UA50.108.jpg¢/alcorn/2.2_UA50.109.jpg¢/alcorn/2.2_UA50.110.jpg¢/alcorn/2.2_UA50.111.jpg¢/alcorn/2.2_UA50.112.jpg¢/alcorn/2.2_UA50.113 copia.jpg¢/alcorn/2.2_UA50.114 copia.jpg¢/alcorn/2.2_UA50.115 copia.jpg¢/alcorn/2.2_UA50.116 copia.jpg¢/alcorn/2.2_UA50.117 copia.jpg¢/alcorn/2.2_UA50.118 copia.jpg¢/alcorn/2.2_UA50.119 copia.jpg¢/alcorn/2.2_UA50.120 copia.jpg¢/alcorn/2.2_UA50.121 copia.jpg¢/alcorn/2.2_UA50.122 copia.jpg¢/alcorn/2.2_UA50.123 copia.jpg¢/alcorn/2.2_UA50.124 copia.jpg¢/alcorn/2.2_UA50.125 copia.jpg¢/alcorn/2.2_UA50.126 copia.jpg¢/alcorn/2.2_UA50.127 copia.jpg¢/alcorn/2.2_UA50.128 copia.jpg</isattach>"));
	}
	public static String getFileSizeMegaBytes(long length) {
		try {
			return (float) length / (1024 * 1024) + " mb";
		} catch (Exception e) {
			return null;
		}
	} 
	
	public static String stripPunctuationAdv(String str, char separator) {
		str = str.replaceAll("/text\\(\\)", "");
		str = str.replaceAll("/", "_");
		str = str.replaceAll("\\[", "_");
		str = str.replaceAll("\\]", "_");
		str = str.replaceAll("=", "_");
		str = str.replaceAll("'", "");
		str = str.replaceAll("@", "");
		str = str.replaceAll("__*", "_");
		if (str.startsWith("_")) {
			str = str.substring(1);
		}

		StringBuilder sb = new StringBuilder();
		char[] cs = str.toCharArray();
		for (int i = 0; i < cs.length; i++) {
			if (Character.isLetterOrDigit(cs[i]) || cs[i] == '.' || cs[i] == '_' || cs[i] == separator) {
				sb.append(cs[i]);
			} else {
				if (sb.length() > 1 && sb.charAt(sb.length() - 1) != separator) {
					if (Character.isSpaceChar(cs[i])) {
						sb.append(separator);
					}
				}
			}
		}
		return removeAccents(sb.toString().replaceAll("\\.", ""));
	}

	public static String removeAccents(String text) {
		return Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
	}

	static public boolean checkExt(String fileName) {
		String extension = "." + StringUtils.substringAfterLast(fileName, ".");
		if (!extension.equals("")) {
			Iterator<String> itr = collectionExt.iterator();
			while (itr.hasNext()) {
				String element = itr.next();
				if (extension.toLowerCase().indexOf(element) != -1) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isWindows() {
		return (OS.indexOf("win") >= 0);
	}

	public static boolean isMac() {
		return (OS.indexOf("mac") >= 0);
	}

	public static boolean isUnix() {
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
	}

	public static boolean isSolaris() {
		return (OS.indexOf("sunos") >= 0);
	}

	public static boolean isValidMD5(String s) {
		return s.matches("[a-fA-F0-9]{32}");
	}

	public static boolean isValidSHA1(String s) {
		return s.matches("[a-fA-F0-9]{40}");
	}

	public static String escapeJqueryName(String value) {
		for (int i = 0; i < escapeCharacters.length(); i++) {
			String replace = Character.toString(escapeCharacters.charAt(i));
			if (value.contains(replace))
				value = value.replace(replace, "\\\\" + replace);
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
			// System.out.println("1 "+urlStr);
			urlStr = StringEscapeUtils.unescapeXml(urlStr);
			// System.out.println("2 "+urlStr);
			urlStr = deleteHTMLTag(urlStr, "");
			// System.out.println("3 "+urlStr);
			urlStr = StringUtils.abbreviate(urlStr, maxChar);
			// System.out.println("4 "+urlStr);
			urlStr = stripPunctuation(urlStr, '-');
			// System.out.println("5 "+urlStr);
			urlStr = removeAccents(urlStr);
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
					if (Character.isSpaceChar(cs[i])) {
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