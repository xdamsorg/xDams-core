package org.xdams.xw.utility;

import java.text.CollationKey;
import java.text.Collator;
import java.text.ParseException;
import java.text.RuleBasedCollator;
import java.util.Locale;

public class XWComparator extends Collator {

	/**
	 * 
	 */
	public XWComparator() throws ParseException {
	}

	/*
	 * (non Javadoc)
	 * 
	 * @see java.text.Collator#hashCode()
	 */
	public int hashCode() {
		return 0;
	}

	/*
	 * (non Javadoc)
	 * 
	 * @see java.text.Collator#compare(java.lang.String, java.lang.String)
	 */
	public int compare(String param0, String param1) {

		RuleBasedCollator it_ITCollator = (RuleBasedCollator) Collator.getInstance(Locale.ITALIAN);
		String jaString = "& \u2212 < \u3042";
		RuleBasedCollator xwCollator;
		String arg0 = param0;
		String arg1 = param1;
		if (param0.indexOf("~") != -1)
			arg0 = param0.substring(0, param0.lastIndexOf("~"));
		if (param1.indexOf("~") != -1)
			arg1 = param1.substring(0, param1.lastIndexOf("~"));
		try {
			xwCollator = new RuleBasedCollator(it_ITCollator.getRules() + jaString);
			xwCollator.setStrength(Collator.PRIMARY);
			return xwCollator.compare(arg0.replaceAll(" ", "\u3042"), arg1.replaceAll(" ", "\u3042"));
		} catch (ParseException e) {
			return 0;
		}

	}

	/*
	 * (non Javadoc)
	 * 
	 * @see java.text.Collator#getCollationKey(java.lang.String)
	 */
	public CollationKey getCollationKey(String arg0) {
		return null;
	}

}
