import generic.Rule;

import java.util.ArrayList;

public class ParserAndConverter {

    ArrayList<Rule> rules;
    public ParserAndConverter(ArrayList<String> rulesUnParse){

        String[] bufStr;
        String leftPart;
        String rightPart;
        rules = new ArrayList<>();

        for (String s:rulesUnParse) {
            bufStr = s.split("->");
            leftPart = bufStr[0];
            rightPart = bufStr[1];
            bufStr = rightPart.split("\\|");
            for (int i = 0; i < bufStr.length; i++) {
                if (bufStr[i].equals("eps")){
                    bufStr[i] = "ε";
                }
                rules.add(new Rule(leftPart,bufStr[i]));
            }
        }
    }

    public ArrayList<Rule> getRules() {
        return rules;
    }
}
