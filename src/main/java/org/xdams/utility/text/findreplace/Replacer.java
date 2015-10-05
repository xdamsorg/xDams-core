package org.xdams.utility.text.findreplace;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Arrays;
import java.util.EnumSet;
import static org.xdams.utility.text.findreplace.Options.*;

/**
 * Gestisce il replace di una stringha in base a un pattern e a opzioni globali la stessa istanza puo essere usata per diverse stringhe, pattern, e configurazioni permettendo di risettarle e di effettuare diverse operazioni come il replace incrementale
 * 
 * @author Daniel Camarda
 */
public class Replacer implements IReplacer, Cloneable {
	// #########################
	// VARIABLE DECALARATIONS###
	// #########################

	public static final char[] REGEX_CHARS = { '\\', '*', '+', '?', '|', '.', '{', '}', '(', ')', '[', ']', '^', '$' };

	// in modalita HTML e/o XML probabilmente applicare un'espressione regolare per
	// trovare tutti i blocchi di testo all'interno dei tag ed applicare poi il replace
	// su ogni singolo blocco, nella modalita XML tenendo conto dei CDATA

	private final EnumSet<Options> opts = EnumSet.noneOf(Options.class);

	private String originalPattern = null;

	private Pattern pattern = null;

	private Matcher matcher = null;

	private int replaceCount = 0;

	private int lastReplaceCount = 0;

	private CharSequence text;

	// ###################
	// CONSTRUCTORS#######
	// ###################

	public Replacer() {
		super();
	}

	public Replacer(CharSequence text, String pattern, Options... options) {
		addOptions(options);

		this.originalPattern = pattern;
		this.text = text;
		this.pattern = compilePattern(pattern);
		this.matcher = this.pattern.matcher(text);
	}

	public Replacer(CharSequence text, String pattern) {
		this.originalPattern = pattern;
		this.text = text;
		this.pattern = compilePattern(pattern);
		this.matcher = this.pattern.matcher(text);
	}

	public Replacer(String pattern) {
		this.originalPattern = pattern;
		this.text = "";
		this.pattern = compilePattern(pattern);
	}

	// ###################
	// PUBLIC METHODS#####
	// ###################

	public String replaceFirst(String replacement) {
		return matcher.replaceFirst(replacement);
	}

	public String replaceAllIsolated(String replacement) {
		int start = matcher.regionStart();
		int end = matcher.regionEnd();

		String result = matcher.replaceAll(replacement);
		matcher.region(start, end);

		return result;
	}

	/*
	 * public String replaceAllForward(String replacement){ int regionStart = matcher.regionStart(); //inclusive //int regionEnd = matcher.regionEnd(); //exclusive StringBuffer sb = new StringBuffer();
	 * 
	 * lastReplaceCount = 0; while (matcher.find()) { matcher.appendReplacement(sb, replacement); replaceCount++; lastReplaceCount++; }
	 * 
	 * matcher.appendTail(sb); matcher.reset(sb.toString()); matcher.region(regionStart, matcher.regionEnd());
	 * 
	 * return sb.toString(); }
	 */
	public Replacer replaceAll(String replacement) {
		replaceHelper(replacement, 0, 3, false);
		return this;
	}

	public Replacer replaceAllForward(String replacement) {
		replaceHelper(replacement, 1, 3, false);
		return this;
	}

	public Replacer replaceAllInRegion(String replacement) {
		replaceHelper(replacement, 1, 2, false);
		return this;
	}

	public Replacer replaceNext(String replacement) {
		replaceHelper(replacement, 1, 3, true);
		return this;
	}

	public Replacer replacePrev(String replacement) {
		StringBuffer sb = new StringBuffer();
		CharSequence[] sequence = { text.subSequence(0, matcher.regionStart()), text.subSequence(matcher.regionStart(), matcher.regionEnd()), text.subSequence(matcher.regionEnd(), text.length()) };

		Matcher m = pattern.matcher(sequence[0]);

		int lastMatch = -1;
		while (m.find()) {
			lastMatch = m.start();
		}
		if (lastMatch == -1)
			return this;

		m.region(lastMatch, m.regionEnd());
		m.find();
		m.appendReplacement(sb, replacement);
		m.appendTail(sb);
		sequence[0] = sb.toString();

		lastReplaceCount = 1;
		replaceCount++;

		text = sequence[0].toString() + sequence[1].toString() + sequence[2].toString();
		matcher.reset(text);
		matcher.region(sequence[0].length(), sequence[0].length() + sequence[1].length());

		return this;
	}

	public void setRegion(int start, int end) {
		matcher.region(start, end);
	}

	public int getRegionStart() {
		return matcher.regionStart();
	}

	public int getRegionEnd() {
		return matcher.regionEnd();
	}

	public int getReplaceCount() {
		return replaceCount;
	}

	public int getLastOperationReplaceCount() {
		return lastReplaceCount;
	}

	public int getMatchCount() {
		int matchCount = 0;
		int regionStart = matcher.regionStart(); // inclusive
		int regionEnd = matcher.regionEnd(); // exclusive

		matcher.reset();

		while (matcher.find()) {
			matchCount++;
		}

		matcher.region(regionStart, regionEnd);

		return matchCount;
	}

	public void resetMatcher() {
		matcher.reset();
	}

	public void setPattern(String pattern) {
		this.originalPattern = pattern;
		this.pattern = compilePattern(pattern);
		matcher.usePattern(this.pattern);
	}

	public void setText(CharSequence text) {
		this.text = text;
		if (matcher == null) {
			this.matcher = pattern.matcher(text);
		} else {
			this.matcher.reset(text);
		}

	}

	public CharSequence getCharSequence() {
		return text;
	}

	@Override
	public String toString() {
		return text.toString();
	}

	@Override
	public Replacer clone() throws CloneNotSupportedException {
		Replacer rep = (Replacer) super.clone();
		rep.pattern = Pattern.compile(pattern.pattern());
		rep.matcher = rep.pattern.matcher(rep.text);
		return rep;
	}

	// #########################
	// OPTIONS MANAGEMENT#######
	// #########################

	public void clearOptions() {
		opts.clear();
	}

	public void setOptions(Options... options) {
		clearOptions();
		opts.addAll(Arrays.asList(options));
	}

	public void addOptions(Options... options) {
		opts.addAll(Arrays.asList(options));
	}

	public void removeOptions(Options... options) {
		opts.removeAll(Arrays.asList(options));
	}

	public void applyConfiguration() {
		//System.out.println("pattern  " + pattern);
		pattern = compilePattern(originalPattern);
		//System.out.println("pattern  " + pattern);
		if (matcher != null) {
			matcher.usePattern(pattern);
		}
	}

	// ##################
	// INTERNAL METHODS
	// ##################

	/**
	 * Compila il pattern in base al pattern parziale ricevuto come parametro e alle opzioni settate
	 * 
	 * @param pattern:
	 *            String representin the pattern to merge with options and compile
	 * @return Pattern
	 */
	private Pattern compilePattern(String pattern) {
		if (!opts.contains(REGEX)) {
			pattern = escapeRegexChars(pattern);
		}
		if (opts.contains(WHOLE_WORD)) {
			pattern = "\\b(?:" + pattern + ")\\b";
		}
		if (opts.contains(CASE_INSENSITIVE)) {
			pattern = "(?i)" + pattern;
		}
		if (opts.contains(MULTILINE)) {
			pattern = "(?m)" + pattern;
		}
		if (opts.contains(UNICODE_CASE)) {
			pattern = "(?u)" + pattern;
		}

		return Pattern.compile(pattern);
	}

	private String escapeRegexChars(String pattern) {
		String escapedPattern = "";
		for (int i = 0; i < pattern.length(); i++) {
			char c = pattern.charAt(i);
			if (isRegexChar(c)) {
				escapedPattern += "\\";
			}
			escapedPattern += c;
		}

		return escapedPattern;
	}

	private boolean isRegexChar(char c) {
		for (char regexChar : REGEX_CHARS)
			if (c == regexChar)
				return true;

		return false;
	}

	private void replaceHelper(String replacement, int from, int to, boolean replaceFirst) {
		CharSequence[] sequence = { text.subSequence(0, matcher.regionStart()), text.subSequence(matcher.regionStart(), matcher.regionEnd()), text.subSequence(matcher.regionEnd(), text.length()) };

		lastReplaceCount = 0;
		for (int i = from; i < to; i++) {
			if (sequence[i].length() > 0) {
				matcher.reset(sequence[i]);
				StringBuffer sb = new StringBuffer();
				while (matcher.find()) {
					matcher.appendReplacement(sb, replacement);
					replaceCount++;
					lastReplaceCount++;
					if (replaceFirst)
						break;
				}
				matcher.appendTail(sb);
				sequence[i] = sb.toString();
			}
		}

		text = sequence[0].toString() + sequence[1].toString() + sequence[2].toString();
		matcher.reset(text);
		matcher.region(sequence[0].length(), sequence[0].length() + sequence[1].length());
	}
}