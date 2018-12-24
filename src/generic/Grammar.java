package generic;

import bin.TypeGrammar;

import java.util.ArrayList;


public class Grammar {
    // Тип 0 =0; КЗ = 1; КС = 2; P левая = 3; P правая = 4;

    private TypeGrammar typeGrammar;
    private ArrayList<Rule> rules;
    private char startSymbol;
    private ArrayList<Character> terminalSymbols;
    private ArrayList<Character> nonTerminalSymbols;

    public Grammar(ArrayList<Rule> rules) {
        this.rules = rules;
        terminalSymbols = new ArrayList<>();
        nonTerminalSymbols = new ArrayList<>();
        identificationCharacter();
        grammarTypeDefinition();
    }

    public Grammar(String[] leftPart, String[] rightPart) throws Exception {
        if (leftPart.length != rightPart.length){
            throw new Exception("Кол-во левых частей правил не равно правым");
        }
        rules = new ArrayList<>();
        for (int i = 0; i < leftPart.length; i++) {
            setRules(leftPart[i],rightPart[i]);
        }
        identificationCharacter();
        grammarTypeDefinition();
    }

    public void setRules(String leftPart, String rightPart){
        setRules(new Rule(leftPart,rightPart));
    }

    public void setRules(Rule rule){
        rules.add(rule);
    }

    public ArrayList<Rule> getRules(){
        return rules;
    }

    public ArrayList<Rule> getRules(String leftPart){
        ArrayList<Rule> bufRules = new ArrayList();
        for (Rule rule:rules) {
            if (rule.getLeftPart().equals(leftPart)){
                bufRules.add(rule);
            }
        }
        return bufRules;
    }

    public TypeGrammar getTypeGrammar() {
        return typeGrammar;
    }

    public char getStartSymbol() {
        return startSymbol;
    }

    public ArrayList<Character> getTerminalSymbols() {
        return terminalSymbols;
    }

    public ArrayList<Character> getNonTerminalSymbols() {
        return nonTerminalSymbols;
    }


    private void grammarTypeDefinition() {
        int sizeLP = 0;
        for (Rule rule : rules) {
            if (rule.getLeftPart().length() > sizeLP) {
                sizeLP = rule.getLeftPart().length();
            }
        }
        if (sizeLP == 1) {
            // Проверка на КС, либо на P
            typeGrammar = TypeGrammar.CONTEXT_FREE;
            for (Rule rule : rules) {
                boolean k = checkOnContentRule(rule);
                if ((checkOnNonTerminal(rule.getRightPart().charAt(0)) & checkOnTerminal(rule.getRightPart().charAt(rule.getRightPart().length() - 1))
                        & k) || (checkOnTerminalString(rule.getRightPart()) & k)) {
                    typeGrammar = TypeGrammar.REGULAR_LEFT;
                } else {
                    typeGrammar = TypeGrammar.CONTEXT_FREE;
                    break;
                }
            }
            if (typeGrammar != TypeGrammar.REGULAR_LEFT) {
                //Проверка на правое выравнивание
                for (Rule rule : rules) {
                    boolean k = checkOnContentRule(rule);
                    if ((checkOnNonTerminal(rule.getRightPart().charAt(rule.getRightPart().length() - 1))
                            & checkOnTerminal(rule.getRightPart().charAt(0))
                            & k) || (checkOnTerminalString(rule.getRightPart()) & k)) {
                        typeGrammar = TypeGrammar.REGULAR_RIGHT;
                    } else {
                        typeGrammar = TypeGrammar.CONTEXT_FREE;
                        break;
                    }
                }
            }


        }
        if (sizeLP > 1) {
            //Проверка на КЗ, либо 0
            for (Rule rule : rules) {
                if (rule.getLeftPart().length() <= rule.getRightPart().length()) {
                    int k = 0;
                    for (int j = 0; j < rule.getLeftPart().length(); j++) {
                        // Проверка на контекст
                        if (rule.getLeftPart().charAt(j) != rule.getRightPart().charAt(j)
                                & checkOnNonTerminal(rule.getLeftPart().charAt(j))) {
                            k = rule.getRightPart().length() - (rule.getLeftPart().length() - j - 1);
                            j++;
                            if (j == rule.getLeftPart().length()) {
                                break;
                            }
                        }
                        if (rule.getLeftPart().charAt(j) == rule.getRightPart().charAt(k)) {
                            typeGrammar = TypeGrammar.CONTEXT_SENSITIVE;
                        } else {
                            typeGrammar = TypeGrammar.ZERO;
                            break;
                        }
                        k++;
                    }
                } else {
                    typeGrammar = TypeGrammar.ZERO;
                    break;
                }
            }
        }
    }

    private void identificationCharacter(){
        terminalSymbols = new ArrayList<>();
        nonTerminalSymbols = new ArrayList<>();
        StringBuilder stringBuilder= new StringBuilder();
        char c;

        for (Rule rule: rules) {
            stringBuilder.append(rule.getLeftPart()).append(rule.getRightPart());
            for (int i = 0; i < stringBuilder.length(); i++) {
                c = stringBuilder.charAt(i);
                if (checkOnNonTerminal(c) && !nonTerminalSymbols.contains(c)){
                    nonTerminalSymbols.add(c);
                }else if (checkOnTerminal(c) && !terminalSymbols.contains(c)){
                    terminalSymbols.add(c);
                }
            }
            stringBuilder.delete(0,stringBuilder.length()-1);
        }
        if (checkOnNonTerminal(rules.get(0).getLeftPart().charAt(0))){
            startSymbol = rules.get(0).getLeftPart().charAt(0);
        }else startSymbol = '-';
    }


    private boolean checkOnTerminal(char c){
        return !checkOnNonTerminal(c);
   //     return ((int)c >=97 && (int)c <=122);
    }
    public static boolean checkOnNonTerminal(char c){
        return ((int)c >=65 && (int)c <=90);
    }



    //Проверка правил с одной левой частью на содержание в правой части терминальной, и нетерминальной строки.
    private  boolean checkOnContentRule(Rule rule){

        boolean termStr=false;
        for (Rule r:getRules(rule.getLeftPart())) {
            termStr = (checkOnTerminalString(r.getRightPart()) && r.getRightPart().length() == 1);
        }
        return termStr;
    }

    private boolean checkOnTerminalString(String rightPart){
        boolean termStr=false;
        for (String s1 : rightPart.split("")) {
            if (checkOnTerminal(s1.charAt(0))) {
                termStr =true;
            }else {
                termStr =false;
                break;
            }
        }
        return termStr;
    }


}
