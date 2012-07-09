package org.xdams.utility;

import java.util.HashMap;

public class SharpIncrementTool {

	private HashMap hashMap = new HashMap();

	public SharpIncrementTool() {

	}

	public static String incrementValue(String nameStr, String valueStr, HashMap<String, Integer> map) {

		String ilValoreIni = valueStr.substring(0, valueStr.indexOf("[#"));
		String ilValoreFin = valueStr.substring(valueStr.indexOf("#]") + 2);
		String bloccoValore = valueStr.substring(valueStr.indexOf("[#") + 1, valueStr.indexOf("#]") + 1);

		String indexValue = "";

		try {
			ilValoreFin = valueStr.substring(valueStr.indexOf("]}") + 2);
			indexValue = valueStr.substring(valueStr.indexOf("{[") + 2, valueStr.indexOf("]}"));
			new Integer(indexValue);

		} catch (Exception e) {
			// e.printStackTrace();
			ilValoreFin = valueStr.substring(valueStr.indexOf("#]") + 2);
			indexValue = "1";
		}

		// System.out.println(indexValue+" "+indexValue.length());
		// System.out.println(bloccoValore);
		// System.out.println(ilValoreFin);

		if (map.get(nameStr) == null) {
			map.put(nameStr, new Integer(indexValue));
		} else {
			map.put(nameStr, new Integer(((Integer) map.get(nameStr)).intValue() + 1));
		}

		String contatoreMultiplo = String.valueOf(((Integer) map.get(nameStr)).intValue());
		while (contatoreMultiplo.length() < bloccoValore.length()) {
			contatoreMultiplo = "0" + contatoreMultiplo;
		}

		return ilValoreIni + contatoreMultiplo + ilValoreFin;
	}

	public String incrementValue(String nameStr, String valueStr) {

		String ilValoreIni = valueStr.substring(0, valueStr.indexOf("[#"));
		String ilValoreFin = valueStr.substring(valueStr.indexOf("#]") + 2);
		String bloccoValore = valueStr.substring(valueStr.indexOf("[#") + 1, valueStr.indexOf("#]") + 1);

		String indexValue = "";

		try {
			ilValoreFin = valueStr.substring(valueStr.indexOf("]}") + 2);
			indexValue = valueStr.substring(valueStr.indexOf("{[") + 2, valueStr.indexOf("]}"));
			new Integer(indexValue);

		} catch (Exception e) {
			// e.printStackTrace();
			ilValoreFin = valueStr.substring(valueStr.indexOf("#]") + 2);
			indexValue = "1";
		}

		// System.out.println(indexValue+" "+indexValue.length());
		// System.out.println(bloccoValore);
		// System.out.println(ilValoreFin);

		if (getHashMap().get(nameStr) == null) {
			getHashMap().put(nameStr, new Integer(indexValue));
		} else {
			getHashMap().put(nameStr, new Integer(((Integer) getHashMap().get(nameStr)).intValue() + 1));
		}

		String contatoreMultiplo = String.valueOf(((Integer) getHashMap().get(nameStr)).intValue());
		while (contatoreMultiplo.length() < bloccoValore.length()) {
			contatoreMultiplo = "0" + contatoreMultiplo;
		}

		return ilValoreIni + contatoreMultiplo + ilValoreFin;
	}

	public static void main(String[] args) {
		String ilValore = "simone [####]{[1111]} pasquini [5]";
		SharpIncrementTool incrementTool = new SharpIncrementTool();
		for (int i = 0; i < 10; i++) {
			if ((ilValore.indexOf("[#") != -1) && (ilValore.indexOf("#]") > 0)) {
				String aaa = incrementTool.incrementValue("aaa", ilValore);
				System.out.println("PRIMO " + aaa);
			}
		}

	}

	public HashMap getHashMap() {
		return hashMap;
	}

	public void setHashMap(HashMap hashMap) {
		this.hashMap = hashMap;
	}
}
