package org.xdams.utility.text.findreplace;

import java.util.LinkedList;

public abstract class ATransactionalReplacer implements ITransactionalReplacer {
	IReplacer replacer = null;

	public String replaceFirst(String replacement) {
		return replacer.replaceFirst(replacement);
	}

	public String replaceAllIsolated(String replacement) {
		return replacer.replaceAllIsolated(replacement);
	}

	public ITransactionalReplacer replaceAll(String replacement) {
		replacer.replaceAll(replacement);

		return this;
	}

	public ITransactionalReplacer replaceAllForward(String replacement) {
		replacer.replaceAllForward(replacement);

		return this;
	}

	public ITransactionalReplacer replaceAllInRegion(String replacement) {
		replacer.replaceAllInRegion(replacement);

		return this;
	}

	public ITransactionalReplacer replaceNext(String replacement) {
		replacer.replaceNext(replacement);

		return this;
	}

	public ITransactionalReplacer replacePrev(String replacement) {
		replacer.replacePrev(replacement);

		return this;
	}

	public CharSequence getCharSequence() {
		return replacer.getCharSequence();
	}

	public int getReplaceCount() {
		return replacer.getReplaceCount();
	}

	public int getLastOperationReplaceCount() {
		return replacer.getLastOperationReplaceCount();
	}

	public int getMatchCount() {
		return replacer.getMatchCount();
	}

	public void setPattern(String pattern) {
		replacer.setPattern(pattern);
	}

	public void setText(CharSequence text) {
		replacer.setText(text);
	}

	public void setRegion(int start, int end) {
		replacer.setRegion(start, end);
	}

	public int getRegionStart() {
		return replacer.getRegionStart();
	}

	public int getRegionEnd() {
		return replacer.getRegionEnd();
	}

	public void clearOptions() {
		replacer.clearOptions();
	}

	public void setOptions(Options... options) {
		replacer.setOptions(options);
	}

	public void addOptions(Options... options) {
		replacer.addOptions(options);
	}

	public void removeOptions(Options... options) {
		replacer.removeOptions(options);
	}

	public void applyConfiguration() {
		replacer.applyConfiguration();
	}

	public IReplacer clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}
