package org.xdams.utility.xw.query;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.text.StrTokenizer;

/*
 * Prepara una query string per il motore ExtraWay
 * usa una stringa come template per la query sul quale vengono poi settati dinamicamnte i valori necessari per completare la query
 * permettendo anche l'eliminazione automatica delle parti della query carenti di valore
 * 
 * @author Daniel Camarda
 */
public class NewPreparedXwQuery {
	public static void main(String[] args) {
		// NewPreparedXwQuery xwq = new NewPreparedXwQuery("111111=${var1} AND 222222=${var2} OR NOT 333333=${var1} AND 00000=staticVal XOR 44444=${var3} OR 555555=${var2}");
		try {
			NewPreparedXwQuery xwq = new NewPreparedXwQuery(" AND ([XML,/c/did/unittitle]=${?}) AND NOT ([XML,/c/did/unittitle/unitdate/@normal]={${?},${?}}) AND ([XML,/c/scopecontent/p/]=${?})");
			/*
			 * java.util.Map map = new java.util.HashMap(); map.put("var1", "[valore 1]"); map.put("var2", "[valore 2]"); map.put("var3", "[valore 3]"); xwq.setMapValues(map);
			 */

			java.util.List lista = new java.util.ArrayList();
			lista.add("");
			lista.add("valore2");
			lista.add("");
			lista.add("valore4");
			// lista.add(null);
			// lista.add("[valore 5]");
			// lista.add("[valore 6]");
			// lista.add("[valore 7]");

			xwq.setProgressiveValues(lista);
xwq.setDeleteFirstEmptyPart(true);
			System.out.println(xwq.compile());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// xwq.debug();

	}

	// MEMBER CLASS VARIABLES
	private String originalQuery = null;

	private List queryParts = null;

	private HashMap logicOperators = null;

	// INNER CLASSES
	/*
	 * Inner class che rappresenta una porzione della query, facilitando le modifiche su ogni parte della stessa. Ogni parte della query(QueryPart) e rappresentato da una parte statica, una o piu parti variabili da rimpiazare con l'appropiato valore, e un operatore logico que la relaziona con la
	 * parte previa della query
	 */
	private class QueryPart {
		private String staticPart = "";

		private List replacePartList = null;

		private String logicOperator = "";

		// INNER CLASSES
		private class ReplacePart {
			private String key = null;

			private String value = null;

			private int distance = -1;
		}

		public QueryPart() {
			replacePartList = new ArrayList();
		}

		// GETTERS
		public String getStaticPart() {
			return staticPart;
		}

		public String compileStaticPart() {
			StringBuilder result = new StringBuilder(staticPart.toString());
			int prevIndex = 0;
			for (Iterator iter = replacePartList.iterator(); iter.hasNext();) {
				ReplacePart part = (ReplacePart) iter.next();
				int index = prevIndex + part.distance;
				String value = part.value == null ? "" : part.value;
				result.insert(index, value);
				prevIndex = index + value.length();
			}
			return result.toString();
		}

		public ReplacePart getReplacePart(int idx) {
			if (idx < getReplacePartsLength()) {
				return (ReplacePart) replacePartList.get(idx);
			} else {
				return null;
			}
		}

		public int getReplacePartsLength() {
			return replacePartList.size();
		}

		public String getLogicOperator() {
			return logicOperator;
		}

		public boolean isStatic() {
			return !(getReplacePartsLength() > 0);
		}

		// SETTERS
		public void setStaticPart(String staticPart) {
			this.staticPart = staticPart;
		}

		public void addReplacePart(String key, String value, int distance) {
			ReplacePart replace = new ReplacePart();
			replace.key = key;
			replace.value = value;
			replace.distance = distance;

			replacePartList.add(replace);
		}

		public void setLogicOperator(String logicOperator) {
			this.logicOperator = logicOperator;
		}

		// PUBLIC METHODS
		public boolean hasEmptyValues() {
			for (Iterator iter = replacePartList.iterator(); iter.hasNext();) {
				ReplacePart part = (ReplacePart) iter.next();
				if (part.value == null || part.value.equals(""))
					return true;
			}

			return false;
		}
	}

	// CONSTRUCTORS
	public NewPreparedXwQuery(String query) {
		originalQuery = query;
		queryParts = new ArrayList();
		logicOperators = new HashMap();
		// il valore corrispondente a ogni chiave(operatore logico) sta a indicare se la parte associata a quel operatore dev'essere cancellata quando e vuota
		logicOperators.put("AND", Boolean.TRUE);
		logicOperators.put("OR", Boolean.TRUE);
		logicOperators.put("XOR", Boolean.TRUE);
		logicOperators.put("NOT", Boolean.TRUE);
		decomponeQuery();
	}

	// PRIVATE METHODS
	private void decomponeQuery() {
		StrTokenizer tokenizer = new StrTokenizer(originalQuery);
		Pattern pattern = Pattern.compile("\\$\\{.*?\\}");

		String partialStr = "";
		String logicOperator = "";
		String prevOperator = "";
		while (tokenizer.hasNext()) {
			String elem = (String) tokenizer.next();

			if (logicOperators.containsKey(elem)) {
				logicOperator += elem + " ";
			} else {
				int iterations = 1;
				if (!tokenizer.hasNext())
					iterations = 2;

				for (int i = 0; i < iterations; i++) {
					if (!logicOperator.equals("") || !tokenizer.hasNext()) {
						StringBuilder partStr = new StringBuilder(partialStr);

						QueryPart queryPart = new QueryPart();

						Matcher matcher = pattern.matcher(partStr.toString());

						int deletedLength = 0;
						int prevIndex = 0;

						while (matcher.find()) {
							int replaceLength = matcher.end() - matcher.start();
							String keyPart = partStr.substring(matcher.start() - deletedLength, matcher.end() - deletedLength);
							String key = keyPart.substring(2, keyPart.length() - 1);

							int distance = matcher.start() - prevIndex;

							queryPart.addReplacePart(key, "", distance);

							prevIndex = matcher.end();

							partStr.delete(matcher.start() - deletedLength, matcher.end() - deletedLength);
							deletedLength += replaceLength;
						}

						queryPart.setStaticPart(partStr.toString().trim());// trim?

						if (!logicOperator.equals("") || !prevOperator.equals("")) {
							if (!prevOperator.equals("")) {
								queryPart.setLogicOperator(prevOperator.trim());
								prevOperator = "";
							}

							prevOperator = logicOperator;
							logicOperator = "";
						}

						queryParts.add(queryPart);

						partialStr = "";
					}

					partialStr += elem + " ";
				}
			}
		}
	}

	private boolean deleteThisEmptyPart(String operators) {
		boolean returnValue = false;
		String[] logicOp = operators.split("\\s");
		for (int i = 0; i < logicOp.length; i++) {
			String operator = logicOp[i];
			operator = operator.equals("") ? null : operator;
			returnValue = returnValue || (!logicOperators.containsKey(operator) ? true : ((Boolean) logicOperators.get(operator)).booleanValue());
		}
		return returnValue;
	}

	// PUBLIC METHODS
	public void setDeleteANDEmptyPart(boolean delete) {
		logicOperators.put("AND", Boolean.valueOf(delete));
	}

	public void setDeleteOREmptyPart(boolean delete) {
		logicOperators.put("OR", Boolean.valueOf(delete));
	}

	public void setDeleteXOREmptyPart(boolean delete) {
		logicOperators.put("XOR", Boolean.valueOf(delete));
	}

	public void setDeleteNOTEmptyPart(boolean delete) {
		logicOperators.put("NOT", Boolean.valueOf(delete));
	}

	public void setDeleteFirstEmptyPart(boolean delete) {
		logicOperators.put(null, Boolean.valueOf(delete));
	}

	public void setDeleteEmptyParts(boolean deleteAND, boolean deleteOR, boolean deleteXOR, boolean deleteNOT, boolean deleteFirst) {
		logicOperators.put("AND", Boolean.valueOf(deleteAND));
		logicOperators.put("OR", Boolean.valueOf(deleteOR));
		logicOperators.put("XOR", Boolean.valueOf(deleteXOR));
		logicOperators.put("NOT", Boolean.valueOf(deleteNOT));
		logicOperators.put(null, Boolean.valueOf(deleteFirst));
	}

	public void setMapValues(Map map) {

		for (Iterator iter = queryParts.iterator(); iter.hasNext();) {
			QueryPart part = (QueryPart) iter.next();
			for (int i = 0; i < part.getReplacePartsLength(); i++) { // REVISARE
				String key = part.getReplacePart(i).key;
				String value = (String) map.get(key);

				if (value != null && !value.equals("")) {
					part.getReplacePart(i).value = value;
				}
			}
		}

	}

	public void setProgressiveValues(String list[]) {
		int listIndex = 0;

		try {
			for (Iterator iter = queryParts.iterator(); iter.hasNext();) {
				QueryPart part = (QueryPart) iter.next();
				for (int j = 0; j < part.getReplacePartsLength(); j++) {
					String value = (String) list[listIndex];
					if (value != null && !value.equals("")) {
						part.getReplacePart(j).value = value;
					}
					++listIndex;
				}
			}
			// for (QueryPart part : queryParts) {
			//
			// }
		} catch (IndexOutOfBoundsException e) {
		}
	}

	public void setProgressiveValues(List list) {
		setProgressiveValues((String[]) list.toArray(new String[list.size()]));
	}

	public String compile() {
		StringBuilder result = new StringBuilder();
		for (Iterator iter = queryParts.iterator(); iter.hasNext();) {
			QueryPart part = (QueryPart) iter.next();
			if (!part.isStatic() && deleteThisEmptyPart(part.getLogicOperator()) && part.hasEmptyValues()) {
				continue;
			}

			if (result.length() > 0) {
				result.append(part.getLogicOperator());
				if (!part.getLogicOperator().equals(""))
					result.append(" ");
			}

			if (!part.isStatic()) {
				result.append(part.compileStaticPart()).append(" ");
			} else {
				result.append(part.getStaticPart()).append(" ");
			}
		}
		return result.toString();
	}

	public void debug() {

		for (Iterator iter = queryParts.iterator(); iter.hasNext();) {
			QueryPart part = (QueryPart) iter.next();
			System.out.print(part.getLogicOperator() + " ");
			System.out.print(part.getStaticPart());
			if (part.getReplacePartsLength() > 0) {
				System.out.print(part.getReplacePart(0).value + " ");
			} else {
				System.out.print(" ");
			}
		}
	}
}
