/*
 * Created on 6-apr-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.xdams.utility;

import java.io.IOException;

public class CompositionRule {

	public CompositionRule() {
	}

	public static void main(String[] args) {
		new CompositionRule().compose("5-3-10-5", "XDAMSSTO0000040558ID001", ".");
	}

	public static String compose(String rule, String name, String separator) {
		System.out.println("regola : " + rule);
		System.out.println("stringaCompleta : " + name);
		StringBuilder result = new StringBuilder();
		String[] ruleSplit = rule.split("-");
		int totRule = 0;
		for (int i = 0; i < ruleSplit.length; i++) {
			System.out.println(ruleSplit[i]);
			totRule += Integer.parseInt(ruleSplit[i]);
		}
		System.out.println(totRule);
		 
		if (totRule <= name.length()) {
			int begin = 0;
			int end = 0;
			for (int i = 0; i < ruleSplit.length; i++) {
				// System.out.println("regolaSplit[i] " + Integer.parseInt(regolaSplit[i]));
				end = ((Integer.parseInt(ruleSplit[i])) + end);
				// System.out.println("end " + end);
				result.append(name.substring(begin, end) + separator);
				// System.out.println("stringaRisultato " + stringaRisultato);
				begin = end;
				// System.out.println("begin " + begin);
			}
		} else {
		}
		System.out.println(result);
		return result.toString().toUpperCase();
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

}
