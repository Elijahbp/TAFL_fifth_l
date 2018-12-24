import generic.Grammar;
import machines.MachineWithMemory;

public class Main {


    public static void main(String[] args) throws Exception {

        Grammar grammar = new Grammar(new ParserAndConverter(new Reader().getUnParserRules()).getRules());

        //Machine machine = new Machine(grammar);
        //System.out.println(machine.checkOnDerivability("baaa"));

//        S->1C|0D
//        D->0N|1A
//        C->1N|0B
//        N->$
//        A->0B|1E
//        B->0K|1A
//        E->0B|1N|$
//        K->0N|1A|$

        MachineWithMemory machineWithMemory = new MachineWithMemory(grammar,"100");

        //TODO сделать проверку терминальных строк в машине

        //A->bA/aV/aB/t/eps
        //B->bD/a
        //V->aB/t
        //-END-




        //цепочка на проверку aaa

//        grammar.getRules().forEach(rule -> S.ystem.out.println(rule.getLeftPart()+ " . "+ rule.getRightPart()));
//        System.out.println(grammar.getTypeGrammar());
//        grammar.getTerminalSymbols().forEach(System.out::println);
//        grammar.getNonTerminalSymbols().forEach(System.out::println);
//
//KEK->LOL/1262
//LOL->dwkJGN124
//-END-
    }
}
//
//        String[] leftPart =  {"S","S","C","C","C","D","D","D"};//"S","AD","F","F","Cb","AB","bBA","CB","Ab","bCD"};"S","CB","BB","BC",
//        String[] rightPart = {"1C","0D","0D","0S","1","1C","1S","0"};//"aaCFD","D","AFB","AB","bC","bBA","C","bA","eps"};"aSBC","BC","bb","bc",
//
//
//
//        int indType = 0;
//        // Тип 0 =0; КЗ = 1; КС = 2; P левая = 3; P правая = 4;
//
//
//        int sizeLP = 0;
//        int indRP = 0;
//        for (int i = 0; i < leftPart.length; i++) {
//            if (leftPart[i].length() > sizeLP) {
//                sizeLP = leftPart[i].length();
//            }
//        }
//        //
//        if (sizeLP == 1){
//            // Проверка на КС, либо на P
//            indType = 2;
//
//            for (int i = 0; i < rightPart.length; i++) {
//                boolean k = checkOnContentRule(leftPart[i],leftPart,rightPart);
//                //Проверка на левое выравнивание
//                if ((checkOnNonTerminal(rightPart[i].charAt(0)) & checkOnTerminal(rightPart[i].charAt(rightPart[i].length()-1))
//                        & k )||(checkOnTerminalString(rightPart[i]) &k) ) {
//                    indType = 3;
//                }
//                else {
//                    indType = 2;
//                    break;
//                }
//            }
//            if (indType !=3 ){
//                for (int i = 0; i < rightPart.length; i++) {
//                    boolean k = checkOnContentRule(leftPart[i],leftPart,rightPart);
//                    //Проверка на правое выравнивание
//                    if ((checkOnNonTerminal( rightPart[i].charAt(rightPart[i].length()-1) ) & checkOnTerminal(rightPart[i].charAt(0))
//                            & k) ||(checkOnTerminalString(rightPart[i]) & k)) {
//                        indType = 4;
//                    }
//                    else {
//                        indType = 2;
//                        break;
//                    }
//                }
//            }
//
//
//        }
//        if (sizeLP>1){
//
//            //Проверка на КЗ, либо 0
//            for (int i = 0; i < leftPart.length; i++) {
//                //Todo проверка на контекст
//                if (leftPart[i].length() <= rightPart[i].length()){
//                    int k = 0;
//                    for (int j = 0; j < leftPart[i].length(); j++) {
//
//                        if (leftPart[i].charAt(j) != rightPart[i].charAt(j) & checkOnNonTerminal(leftPart[i].charAt(j))) {
//
//                            k = rightPart[i].length() - (leftPart[i].length() - j -1);
//                            j++;
//                            if (j == leftPart[i].length()){
//                                break;
//                            }
//                        }
//                        if (leftPart[i].charAt(j) == rightPart[i].charAt(k)) {
//                            indType = 1;
//                        }else{
//                            indType =0;
//                            break;
//                        }
//                        k++;
//                    }
//
//
//                    indType = 0;
//                    break;
//                }
//            }
//        }
//
//        if (indType ==0 ) System.out.println("Грамматика Типа 0");
//        if (indType ==1 )System.out.println("Контекстно-зависимая грамматика");
//        if (indType ==2 )System.out.println("Контекстно-свободная грамматика");
//        if (indType ==3 ) System.out.println("Регулярная грамматика с левым выравниванием");
//        if (indType ==4 )System.out.println("Регулярная грамматика с правым выравниванием");
//
//
//    }
//
//    private static boolean checkOnTerminal(char c){
//        return ((int)c >=97 && (int)c <=122);
//    }
//    private static boolean checkOnNonTerminal(char c){
//        return ((int)c >=65 && (int)c <=90);
//    }
//
//
//
//    //Проверка правил с одной левой частью на содержание в правой части терминальной, и нетерминальной строки.
//    private static boolean checkOnContentRule(String leftRule,String[] leftPart, String[] rightPart ){
//
//        ArrayList<String> strings = new ArrayList<>();
//        for (int i = 0; i < leftPart.length ; i++) {
//            if (leftPart[i] ==  leftRule ){
//                strings.add(rightPart[i]);
//            }
//        }
//        boolean termStr=false;
//        for (String rule:strings) {
//            termStr = checkOnTerminalString(rule);
//        }
//        return termStr;
//
//    }
//    private static boolean checkOnTerminalString(String rule){
//        boolean termStr=false;
//        for (String s1 : rule.split("")) {
//            if (checkOnTerminal(s1.charAt(0))) {
//                termStr =true;
//            }else {
//                termStr =false;
//                break;
//            }
//        }
//        return termStr;
//    }
//
