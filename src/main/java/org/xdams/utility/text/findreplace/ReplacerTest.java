package org.xdams.utility.text.findreplace;

import static org.xdams.utility.text.findreplace.Options.CASE_INSENSITIVE;


 
public class ReplacerTest {

    
    public static void main(String[] args) {
        String str = "\"mario ";

        /*Replacer replacer = new Replacer("mario.");
        replacer.setText(StringUtils.defaultIfEmpty(str, ""));
		replacer.setOptions(CASE_INSENSITIVE);
		replacer.applyConfiguration();
		String finalValue = replacer.replaceAll("archivio,").toString();
		System.out.println(finalValue);*/
        //no regex, case insensitive
        Replacer rep = new Replacer(str, "\"", CASE_INSENSITIVE);
        rep.applyConfiguration();
        String replaced = rep.replaceAll("'").toString();
        System.out.println(replaced);
        
/*
        rep.resetMatcher();
        //regex, case insensitive
        rep.setOptions(REGEX, CASE_INSENSITIVE);
        rep.setPattern("<convenient>(.*?)<allows>");
        replaced = rep.replaceAll("<strong>$1</strong>").toString();
        System.out.println(replaced);

        str = "ClaSS. una nuova stringha";
        rep.setText(str);
        replaced = rep.replaceAll("CLASSE ").toString();
        System.out.println(replaced);

        rep.resetMatcher();
        rep.setPattern(" ");
        System.out.println("#########################################");
        System.out.println(rep.getMatchCount());
        System.out.println("#########################################");
        replaced = rep.replaceAll(".$0").toString();
        System.out.println(replaced);

        rep.resetMatcher();
        //no regex, whole word, case insensitive
        rep.setOptions(WHOLE_WORD, CASE_INSENSITIVE);
        rep.setPattern("quickly check");
        rep.setText("The Pattern class. defines a <convenient> matches method that <allows> you to claSS. quickly check if a pattern is present in a given input string. As with all public static methods, you should invoke matches by its class name, such as Pattern.matches(\"\\\\d\",\"1\");");
        replaced = rep.replaceAll("controlla VELOCEMENTE").toString();
        System.out.println(replaced);

        rep.setPattern("uickly check");
        rep.setText("The Pattern class. defines a <convenient> matches method that <allows> you to claSS. quickly check if a pattern is present in a given input string. As with all public static methods, you should invoke matches by its class name, such as Pattern.matches(\"\\\\d\",\"1\");");
        replaced = rep.replaceAll("controlla VELOCEMENTE").toString();
        System.out.println(replaced);

        System.out.println("#########################################");

        //PatternOptionsTest po = MULTILINE;
        //po.setEnabled(true);
        //enumtest(MULTILINE, CASE_INSENSITIVE, REGEX);
        Pattern pat = Pattern.compile(" ");
        Matcher match = pat.matcher("hola que tal?");
        StringBuffer sb = new StringBuffer();
        if(match.find()){
            match.appendReplacement(sb, ".");
        }
        match.appendTail(sb);
        System.out.println(sb);
        sb = new StringBuffer();
        if(match.find()){
            match.appendReplacement(sb, ".");
        }
        match.appendTail(sb);
        System.out.println(sb);
        sb = new StringBuffer();
        match.reset();
        if(match.find()){
            match.appendReplacement(sb, ".");
        }
        match.appendTail(sb);
        System.out.println(sb);
        */
    }
}
