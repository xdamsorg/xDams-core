package org.xdams.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.apache.commons.lang3.text.StrMatcher;
import org.apache.commons.lang3.text.StrTokenizer;
import org.xdams.xml.builder.XMLBuilder;

public class AdvancedUtility {

	public static boolean testNode(String name, String value, XMLBuilder xmlBuilder) {
		try {
			// System.out.println("AddNodeCommandNew.testNode() " + xmlBuilder.valoreNodo(name) + "==" + value);
			if (!xmlBuilder.valoreNodo(name).equals(value)) {
				// System.out.println("AddNodeCommandNew.testNode() -----------> differente ");
				return true;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	private static ArrayList getFieldName(String genericField, String fieldValue, XMLBuilder builder, String prefixCount) {
		String fieldName = StringUtils.substringBetween(fieldValue, "$", "$");
		StrBuilder bufFieldName = new StrBuilder(fieldName);
		ArrayList arrActionResult = new ArrayList();
		analizeAction(fieldName, bufFieldName, arrActionResult);
		fieldName = bufFieldName.toString();
		// System.out.println("fieldName " + fieldName);
		// System.out.println("actionResult " + arrActionResult);
		ArrayList fieldResult = new ArrayList();
		try {
			if (fieldName != null) {
				if (!StringUtils.defaultString(fieldValue).equals("")) {
					String[] splitCampiVal = (fieldName).split(",");
					// System.out.println("aaaaaaaaaaaaaaaaa " + splitCampiVal.length);
					// System.out.println("--------------------------------------");
					if (arrActionResult.size() > 0) {
						for (int x = 0; x < splitCampiVal.length; x++) {
							// questo e customizabile
							if (StringUtils.defaultString(splitCampiVal[x]).startsWith("*")) {
								fieldResult.add(makeAction((String) arrActionResult.get(x), (genericField.substring(0, genericField.lastIndexOf("/")) + splitCampiVal[x].replace('*', ' ').trim()), builder));
							} else if (StringUtils.defaultString(splitCampiVal[x]).startsWith("/*/")) {
								fieldResult.add(makeAction((String) arrActionResult.get(x), prefixCount + splitCampiVal[x].replaceAll("/\\*", ""), builder));
								// appo=prefix+prefix_count+element.getText().replaceAll("/\\*","");
							} else if (StringUtils.defaultString(splitCampiVal[x]).contains("/*/")) {
								String appoSt = splitCampiVal[x];
								int count = 0;
								StrTokenizer strTokenizerGenericField = new StrTokenizer(genericField, StrMatcher.charSetMatcher("[]"));
								while (strTokenizerGenericField.hasNext()) {
									String tokenStr2 = strTokenizerGenericField.nextToken();
									try {
										Integer.parseInt(tokenStr2);
										appoSt = appoSt.replaceFirst("/\\*", "[" + tokenStr2 + "]");
									} catch (Exception e) {
										// TODO: handle exception
									}
								}
								fieldResult.add(makeAction((String) arrActionResult.get(x), appoSt, builder));
							} else {
								// for (int z = 0; z < splitCampiVal.length; z++) {
								fieldResult.add(makeAction((String) arrActionResult.get(x), splitCampiVal[x].trim(), builder));
								// }
							}
						}
					} else if (splitCampiVal.length == 0) {
						// nn lo so ancora
					}
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return fieldResult;
	}

	private static String makeAction(String actionValue, String fieldValue, XMLBuilder builder) {
		String returnValue = "";
		try {
			if (actionValue.equals("name")) {
				returnValue = fieldValue.replaceAll("/", ".");
			} else if (actionValue.equals("value")) {
				returnValue = builder.valoreNodo(fieldValue);
			} else if (actionValue.equals("xpath")) {
				returnValue = fieldValue;
			} else {
				returnValue = "actionValue non contemplato { " + actionValue + " }";
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return returnValue;
	}

	private static void analizeAction(String fieldName, StrBuilder bufFieldName, ArrayList arrActionResult) {
		StrTokenizer strTokenizer = new StrTokenizer(fieldName, ",");
		while (strTokenizer.hasNext()) {
			String tokenStr = strTokenizer.nextToken();
			try {
				String parseAct = tokenStr.substring(0, tokenStr.indexOf(":"));
				if (parseAct.equals("name")) {
					bufFieldName.replaceAll("name:", "");// (0,fieldName.substring(fieldName.indexOf(":")+1));
					arrActionResult.add("name");
				} else if (parseAct.equals("value")) {
					bufFieldName.replaceAll("value:", "");
					arrActionResult.add("value");
				} else if (parseAct.equals("xpath")) {
					bufFieldName.replaceAll("xpath:", "");
					arrActionResult.add("xpath");
				} else {
					bufFieldName.replaceAll("name:", "");// (0,fieldName.substring(fieldName.indexOf(":")+1));
					arrActionResult.add("name");
				}
			} catch (Exception e) {
				bufFieldName.replaceAll("name:", "");// (0,fieldName.substring(fieldName.indexOf(":")+1));
				arrActionResult.add("name");
			}
		}
	}

	public static String scriptingResolver(String genericField, String fieldValue, XMLBuilder builder) {
		StringBuffer stringBuffer = new StringBuffer();
		StrTokenizer strTokenizer = new StrTokenizer(fieldValue, StrMatcher.charSetMatcher("{}"));
		while (strTokenizer.hasNext()) {
			String tokenStr = strTokenizer.nextToken();
			ArrayList arrayList = getFieldName(genericField, tokenStr, builder, "");
			if (arrayList.size() > 0) {
				for (int x = 0; x < arrayList.size(); x++) {
					stringBuffer.append(StringEscapeUtils.escapeEcmaScript((String) arrayList.get(x)));
					if (arrayList.size() - 1 != x) {
						stringBuffer.append("','");
					}
				}
			} else {
				stringBuffer.append(tokenStr);
			}
		}
		return stringBuffer.toString();
	}

	public static String scriptingResolver(String genericField, String fieldValue, XMLBuilder builder, String prefixCount) {
		StringBuffer stringBuffer = new StringBuffer();
		StrTokenizer strTokenizer = new StrTokenizer(fieldValue, StrMatcher.charSetMatcher("{}"));
		while (strTokenizer.hasNext()) {
			String tokenStr = strTokenizer.nextToken();
			ArrayList arrayList = getFieldName(genericField, tokenStr, builder, prefixCount);
			if (arrayList.size() > 0) {
				for (int x = 0; x < arrayList.size(); x++) {
					stringBuffer.append(StringEscapeUtils.escapeEcmaScript((String) arrayList.get(x)));
					if (arrayList.size() - 1 != x) {
						stringBuffer.append("','");
					}
				}
			} else {
				stringBuffer.append(tokenStr);
			}
		}
		return stringBuffer.toString();
	}

	public static void extractValue(List<String> listRead, List<String> listPrepXwQuery, XMLBuilder builder) {
		for (int i = 0; i < listRead.size(); i++) {
			String string = listRead.get(i);
			String valueStr = "";
			if (string.startsWith("value:")) {
				System.out.println(listRead.get(i));
				string = StringUtils.remove(string, "value:");
				System.out.println(string);
				try {
					valueStr = builder.valoreNodo(string);
				} catch (Exception e) {
				}

				listPrepXwQuery.add(i, valueStr);
			} else {
				listPrepXwQuery.add(i, "");
			}
		}
	}

	public static void compileString(String string, StringBuffer buffer, List<String> list) {
		try {
			Pattern regex = Pattern.compile("\\$\\{(.*?)\\}");
			Matcher regexMatcher = regex.matcher(string);
			int count = 0;
			while (regexMatcher.find()) {
				// System.out.println(regexMatcher.group(1));
				list.add(regexMatcher.group(1));
				regexMatcher.appendReplacement(buffer, "\\$\\{" + (count++) + "\\}");
			}
			regexMatcher.appendTail(buffer);

		} catch (PatternSyntaxException ex) {
			// Syntax error in the regular expression
		}
	}

}
