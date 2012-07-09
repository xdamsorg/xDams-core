package org.xdams.xml.builder;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ProblematicCharactersFilter {

	public static String replaceCharacters(String input) {
		Map<String, String> problematicCharacters = new HashMap<String, String>();
		problematicCharacters.put("&#x80;", "&#x20ac;");
		problematicCharacters.put("&#128;", "&#x20ac;");
		problematicCharacters.put("&#x82;", "&#x201a;");
		problematicCharacters.put("&#130;", "&#x201a;");
		problematicCharacters.put("&#x83;", "&#x192;");
		problematicCharacters.put("&#131;", "&#x192;");
		problematicCharacters.put("&#x84;", "&#x201e;");
		problematicCharacters.put("&#132;", "&#x201e;");
		problematicCharacters.put("&#x85;", "&#x2026;");
		problematicCharacters.put("&#133;", "&#x2026;");
		problematicCharacters.put("&#x86;", "&#x2020;");
		problematicCharacters.put("&#134;", "&#x2020;");
		problematicCharacters.put("&#x87;", "&#x2021;");
		problematicCharacters.put("&#135;", "&#x2021;");
		problematicCharacters.put("&#x88;", "&#x2c6;");
		problematicCharacters.put("&#136;", "&#x2c6;");
		problematicCharacters.put("&#x89;", "&#x2030;");
		problematicCharacters.put("&#137;", "&#x2030;");
		problematicCharacters.put("&#x8a;", "&#x160;");
		problematicCharacters.put("&#138;", "&#x160;");
		problematicCharacters.put("&#x8b;", "&#x2039;");
		problematicCharacters.put("&#139;", "&#x2039;");
		problematicCharacters.put("&#x8c;", "&#x152;");
		problematicCharacters.put("&#140;", "&#x152;");
		problematicCharacters.put("&#x8e;", "&#x17d;");
		problematicCharacters.put("&#142;", "&#x17d;");
		problematicCharacters.put("&#x91;", "&#x2018;");
		problematicCharacters.put("&#145;", "&#x2018;");
		problematicCharacters.put("&#x92;", "&#x2019;");
		problematicCharacters.put("&#146;", "&#x2019;");
		problematicCharacters.put("&#x93;", "&#x201c;");
		problematicCharacters.put("&#147;", "&#x201c;");
		problematicCharacters.put("&#x94;", "&#x201d;");
		problematicCharacters.put("&#148;", "&#x201d;");
		problematicCharacters.put("&#x95;", "&#x2022;");
		problematicCharacters.put("&#149;", "&#x2022;");
		problematicCharacters.put("&#x96;", "&#x2013;");
		problematicCharacters.put("&#150;", "&#x2013;");
		problematicCharacters.put("&#x97;", "&#x2014;");
		problematicCharacters.put("&#151;", "&#x2014;");
		problematicCharacters.put("&#x98;", "&#x2dc;");
		problematicCharacters.put("&#152;", "&#x2dc;");
		problematicCharacters.put("&#x99;", "&#x2122;");
		problematicCharacters.put("&#153;", "&#x2122;");
		problematicCharacters.put("&#x9a;", "&#x161;");
		problematicCharacters.put("&#154;", "&#x161;");
		problematicCharacters.put("&#x9b;", "&#x203a;");
		problematicCharacters.put("&#155;", "&#x203a;");
		problematicCharacters.put("&#x9c;", "&#x153;");
		problematicCharacters.put("&#156;", "&#x153;");
		problematicCharacters.put("&#x9e;", "&#x17e;");
		problematicCharacters.put("&#158;", "&#x17e;");
		problematicCharacters.put("&#x9f;", "&#x178;");
		problematicCharacters.put("&#159;", "&#x178;");
		Set<Entry<String, String>> iterable = problematicCharacters.entrySet();
		for (Entry<String, String> entry : iterable) {
			System.out.println(entry.getKey()+" - "+entry.getValue());
			input = input.replaceAll(entry.getKey(), entry.getValue());
		}
		return input;
	}

	public static void main(String[] args) {
		replaceCharacters("");
	}

}
