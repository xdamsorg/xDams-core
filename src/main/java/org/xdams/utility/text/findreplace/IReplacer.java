package org.xdams.utility.text.findreplace;

public interface IReplacer{
    String replaceFirst(String replacement);
    String replaceAllIsolated(String replacement);
    IReplacer replaceAll(String replacement);
    IReplacer replaceAllForward(String replacement);
    IReplacer replaceAllInRegion(String replacement);
    IReplacer replaceNext(String replacement);
    IReplacer replacePrev(String replacement);
    CharSequence getCharSequence();
    int getReplaceCount();
    int getLastOperationReplaceCount();
    int getMatchCount();
    void setPattern(String pattern);
    void setText(CharSequence text);
    void setRegion(int start, int end);
    int getRegionStart();
    int getRegionEnd();
    void clearOptions();
    void setOptions(Options... options);
    void addOptions(Options... options);
    void removeOptions(Options... options);
    void applyConfiguration();
    IReplacer clone() throws CloneNotSupportedException;
}
