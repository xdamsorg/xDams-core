package org.xdams.utility;

import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;

public class RenumberUtils {
	public abstract static class Renumber {
		public String renumber() {
			return null;
		};

		public String renumber(int level, boolean countBlock) {
			return null;
		};
	}

	private static class IncrementRenumber extends Renumber {
		private String code;

		private int aux = 0;

		private IncrementRenumber(String code, int startIndex) {
			this.code = StringUtils.remove(code, '[');
			this.code = StringUtils.remove(this.code, ']');

			if (startIndex > 0) {
				this.aux = startIndex;
			}

		}

		public String renumber() {
			aux++;
			return fillWithZeros(String.valueOf(aux), code.length());
		}

		public String renumber(int level) {
			throw new RuntimeException("Il metodo non e supportato in quest'implementazione");
		}
	}

	private static class SiblingRenumber extends Renumber {
		private String code;

		private int increment;

		private int numChars;

		private SiblingRenumber(String code, int increment, int numChars) {
			this.code = code;
			this.increment = increment;
			if (numChars >= 0) {
				this.numChars = numChars;
			} else {
				this.numChars = this.code.substring(code.lastIndexOf('.') + 1).length();
			}
		}

		public String renumber() {
			String lastPart = "";
			try {
				lastPart = code.substring(code.lastIndexOf('.') + 1);
			} catch (StringIndexOutOfBoundsException e) {
				lastPart = code;
			}
			String newCode = Integer.toString((Integer.parseInt(lastPart) + increment));
			newCode = fillWithZeros(newCode, numChars);
			try {
				code = code.substring(0, code.lastIndexOf('.')) + "." + newCode;
			} catch (StringIndexOutOfBoundsException e) {
				code = newCode;
			}
			return code;
		}

		public String renumber(int level) {
			throw new RuntimeException("Il metodo non e supportato in quest'implementazione");
		}
	}

	private static class SonsRenumber extends Renumber {
		private String code;

		private int increment;

		private int numChars;

		private int startIdx;

		private String buffer;

		private SonsRenumber(String code, int increment, int numChars, int startIdx) {
			this.code = code;
			this.increment = increment;
			this.numChars = numChars;
			this.startIdx = startIdx;
			this.buffer = "";
		}

		public String renumber() {
			String newCode = "";
			if (buffer.length() > 0) {
				newCode = buffer;
				code = newCode.substring(0, newCode.lastIndexOf('.'));
				newCode = newCode.substring(newCode.lastIndexOf('.') + 1);
				newCode = Integer.toString(Integer.parseInt(newCode) + increment);
			} else {
				newCode = "" + startIdx;
			}
			newCode = fillWithZeros(newCode, numChars);
			newCode = code + "." + newCode;
			buffer = newCode;
			return newCode;
		}

		public String renumber(int level) {
			throw new RuntimeException("Il metodo non e supportato in quest'implementazione");
		}
	}

	private static class HierRenumber extends Renumber {
		private int prevLevel = -1;

		private int numChars = 0;

		private HashMap levels = null;

		private SiblingRenumber siblingRenumber = null;

		private HierRenumber(String code, int numChars) {
			this.levels = new HashMap();
			this.levels.put("root-code", code);
			this.numChars = numChars;
		}

		public String renumber() {
			throw new RuntimeException("Il metodo non e supportato in quest'implementazione");
		}

		public String renumber(int lvl, boolean countBlock) {
			Integer level = new Integer(lvl);
			String currentCode = null;

			if (!levels.containsKey(level)) {
				if (levels.size() == 1) {
					levels.put(level, levels.get("root-code"));
					prevLevel = lvl;
					return (String) levels.get("root-code");
				} else {
					String newCode = (String) levels.get(new Integer(lvl - 1));
					if (countBlock) {
						newCode = newCode + "." + fillWithZeros("1", numChars, lvl-1);
					} else {
						newCode = newCode + "." + fillWithZeros("1", numChars);
					}
					levels.put(level, newCode);
					prevLevel = lvl;
					// da applicare solo se SiblingRenumber garantisce che settare code direttamente e sempre sicuro
					/*
					 * if(siblingRenumber == null){ siblingRenumber = (SiblingRenumber)getSiblingRenumber(newCode, 1); }else{ siblingRenumber.code = newCode; }
					 */
					siblingRenumber = (SiblingRenumber) getSiblingRenumber(newCode, 1); // ***1
					return newCode;
				}
			}

			currentCode = (String) levels.get(level);

			if (prevLevel > lvl) {
				int count = lvl;
				while (levels.remove(new Integer(count)) != null) {
					count++;
				}
			}

			if (prevLevel != lvl || siblingRenumber == null) { // ***1
				siblingRenumber = (SiblingRenumber) getSiblingRenumber(currentCode, 1);
			}

			// da applicare solo se SiblingRenumber garantisce che settare code direttamente e sempre sicuro
			/*
			 * if(siblingRenumber == null){ siblingRenumber = (SiblingRenumber)getSiblingRenumber(currentCode, 1); }else if(prevLevel != lvl){ siblingRenumber.code = currentCode; }
			 */
			String newCode = siblingRenumber.renumber();

			levels.put(level, newCode);

			prevLevel = lvl;

			return newCode;
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// System.out.println(nextStringNumber("00001.00003.00004.00005"));
		// RenumberUtils ru = new RenumberUtils();
		/*
		 * Renumber rn = RenumberUtils.getSiblingRenumber("", 1);
		 * 
		 * System.out.println(rn.renumber()); System.out.println(rn.renumber()); System.out.println(rn.renumber()); System.out.println(rn.renumber()); System.out.println(rn.renumber()); System.out.println(rn.renumber());
		 */

		StopWatch sw = new StopWatch();
		RenumberUtils.Renumber hierRenumber = RenumberUtils.getHierRenumber("001.001", 1);
		sw.start();
		// System.out.println("4: " + hierRenumber.renumber(4));
		// System.out.println("\t5: " + hierRenumber.renumber(5));
		// System.out.println("\t5: " + hierRenumber.renumber(5));
		// System.out.println("\t\t6: " + hierRenumber.renumber(6));
		// System.out.println("\t5: " + hierRenumber.renumber(5));
		// System.out.println("\t\t6: " + hierRenumber.renumber(6));
		// System.out.println("\t\t\t7: " + hierRenumber.renumber(7));
		// System.out.println("\t\t\t\t8: " + hierRenumber.renumber(8));
		// System.out.println("\t\t\t7: " + hierRenumber.renumber(7));
		// System.out.println("\t\t\t7: " + hierRenumber.renumber(7));
		// System.out.println("\t5: " + hierRenumber.renumber(5));
		// System.out.println("\t5: " + hierRenumber.renumber(5));
		// System.out.println("\t\t6: " + hierRenumber.renumber(6));
		// System.out.println("\t\t6: " + hierRenumber.renumber(6));
		// System.out.println("\t\t6: " + hierRenumber.renumber(6));
		// System.out.println("\t\t\t7: " + hierRenumber.renumber(7));
		// System.out.println("\t\t\t7: " + hierRenumber.renumber(7));
		sw.stop();
		System.out.println(sw.toString());
		sw.reset();
		hierRenumber = RenumberUtils.getIncrementRenumber("[###]", 0);
		System.out.println("\t\t\t7: " + hierRenumber.renumber());
		System.out.println("\t\t\t7: " + hierRenumber.renumber());
		System.out.println("\t\t\t7: " + hierRenumber.renumber());
		System.out.println("\t\t\t7: " + hierRenumber.renumber());
	}

	public static String fillWithZeros(String str, int numChars) {
		for (int i = str.length(); i < numChars; i++) {
			str = "0" + str;
		}

		return str;
	}

	public static String fillWithZeros(String str, int numChars, int lvl) {
		for (int i = str.length(); i < numChars - 1; i++) {
			str = "0" + str;
		}
		str = lvl + str;
		return str;
	}

	public static Renumber getSiblingRenumber(String code, int increment, int numChars) {
		return new RenumberUtils.SiblingRenumber(code, increment, numChars);
	}

	public static Renumber getSiblingRenumber(String code, int increment) {
		return new RenumberUtils.SiblingRenumber(code, increment, -1);
	}

	public static Renumber getSonsRenumber(String code, int increment, int numChars, int startIdx) {
		return new RenumberUtils.SonsRenumber(code, increment, numChars, startIdx);
	}

	public static Renumber getSonsRenumber(String code, int numChars, int startIdx) {
		return new RenumberUtils.SonsRenumber(code, 1, numChars, startIdx);
	}

	public static Renumber getSonsRenumber(String code, int numChars) {
		return new RenumberUtils.SonsRenumber(code, 1, numChars, 1);
	}

	public static Renumber getHierRenumber(String code, int numChars) {
		return new RenumberUtils.HierRenumber(code, numChars);
	}

	public static Renumber getIncrementRenumber(String code, int increment) {
		return new RenumberUtils.IncrementRenumber(code, increment);
	}
}
