package machines;

import bin.TypeGrammar;
import generic.Grammar;
import generic.Rule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class Machine {

    private ArrayList<Character> setOfStatesAutomation_T;//T
    private ArrayList<Character> setOfValidSignals_Q;//Q
    private ArrayList<FunctionTranslation> setOfFunctionTranslations_F;//F
    private ArrayList<Character> setOfStartStates_H;//H
    private ArrayList<Character> setOfFinaleStates_Z;//Z
    private Character startSymbol;
    private final static char talesChar = 'N';



    public Machine(Grammar grammar) throws Exception {
        if (grammar.getTypeGrammar() != TypeGrammar.REGULAR_RIGHT){
            throw new Exception("Из данного типа грамматики " + grammar.getTypeGrammar()+" невозможно выввести автомат");
        }



        setOfStartStates_H = new ArrayList<>();
        setOfStartStates_H.add(grammar.getStartSymbol());

        setOfStatesAutomation_T = grammar.getNonTerminalSymbols();
        setOfStatesAutomation_T.add(talesChar);
        //TODO сделать альтернативную подстановку необходимого символа (N)

        setOfValidSignals_Q = grammar.getTerminalSymbols();


        setOfFunctionTranslations_F = new ArrayList<>();

        setOfFinaleStates_Z = new ArrayList<>();
        setOfFinaleStates_Z.add(talesChar);

        startSymbol = grammar.getStartSymbol();

        for (Rule rule: grammar.getRules()){
            //Статические индексы, из-за того, что мы можем перевести в автомат только
            // Право-выравненную грамматику, а в ней все необходимые позиции одинаковы
            //
            if (!rule.getLeftPart().equals(grammar.getStartSymbol())){
                if(rule.getRightPart().length() == 2) {
                    setOfFunctionTranslations_F.add(new FunctionTranslation(rule.getLeftPart().charAt(0)
                            , rule.getRightPart().charAt(0), rule.getRightPart().charAt(1)));
                }
                else if (rule.getRightPart().length() == 1){
                    setOfFunctionTranslations_F.add(new FunctionTranslation(rule.getLeftPart().charAt(0),
                            rule.getRightPart().charAt(0),talesChar));
                }
            }else {
                if (rule.getRightPart().equals("ε")){
                    setOfFinaleStates_Z.add(rule.getLeftPart().charAt(0));
                }
            }
        }

        if (checkOnNonDeterminateMachine()){
            translateNDMtoDM();
        }
    }
    //Порядок справедливый!!!
    private boolean checkOnNonDeterminateMachine(){
        FunctionTranslation bufFunct = setOfFunctionTranslations_F.get(0);
        for (FunctionTranslation ft: setOfFunctionTranslations_F) {
            if (ft.getStartStates()== bufFunct.getStartStates() && ft.getSignals() == bufFunct.getSignals()){
                return true;
            }
            bufFunct = ft;
        }
        return false;
    }

    private void translateNDMtoDM(){

        Set<ArrayList<FunctionTranslation>> bufStep = new HashSet<>();
        for (Character startSymbol: setOfStatesAutomation_T) {
            for (Character signal: setOfValidSignals_Q) {
                bufStep.add(getSetFunction(startSymbol,signal));
            }
        }
        char bufStart;
        char bufSignal;

        ArrayList<FunctionTranslation> list = new ArrayList<>();

        for (ArrayList<FunctionTranslation> functionTranslations:bufStep) {
            if (functionTranslations.size() >= 2){
                char bufS= getRandomChar();
                bufStart = functionTranslations.get(0).getStartStates();
                bufSignal = functionTranslations.get(0).getSignals();
                list.add(new FunctionTranslation(bufStart,bufSignal,bufS));

                for (FunctionTranslation functionTranslation:functionTranslations) {
                    for (FunctionTranslation ft:getSetFunction(functionTranslation.getEndStates())) {
                        if (ft.getStartStates() != bufStart){
                            list.add(new FunctionTranslation(bufS,ft.getSignals(),ft.getEndStates()));
                        }
                    }
                }

            }else {
                list.addAll(functionTranslations);
            }
        }
        setOfFunctionTranslations_F.clear();
        setOfFunctionTranslations_F.addAll(list);

        list.forEach(functionTranslation -> {
            System.out.println(functionTranslation.getStartStates() +" / "+functionTranslation.getSignals()+" / "+functionTranslation.getEndStates());
        });

    }

    private char getRandomChar(){
        Random random = new Random();

        do {
            int rand =  random.nextInt(25)+65;
            if (Grammar.checkOnNonTerminal((char) rand)){
                if (!setOfStatesAutomation_T.contains((char) rand)){
                    return (char) rand;
                }
            }
        }while (true);

    }

    private ArrayList<FunctionTranslation> getSetFunction(char startSymbol, char signalSymbol){
        return setOfFunctionTranslations_F.stream()
                .filter(functionTranslation -> functionTranslation.getSignals() == signalSymbol)
                .filter(functionTranslation -> functionTranslation.getStartStates() == startSymbol)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<FunctionTranslation> getSetFunction(char startSymbol){
        return setOfFunctionTranslations_F.stream()
                .filter(functionTranslation -> functionTranslation.getStartStates() == startSymbol)
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public boolean checkOnDerivability(String chain){
        int countSteps = chain.length();
        FunctionTranslation bufFunction = new FunctionTranslation();
        String bufStrChain = new String();
        for (int i = 0; i < countSteps ; i++) {
            for (FunctionTranslation ft:setOfFunctionTranslations_F) {
                if (i==0){
                    if (ft.getStartStates() == startSymbol && ft.getSignals() == chain.charAt(i)){
                        if (!setOfFinaleStates_Z.contains(ft.getEndStates())){
                            bufStrChain += ft.getSignals();
                            bufFunction = ft;
                            System.out.println(bufFunction.getStartStates() +" . " +bufFunction.getSignals() +" . "+
                                    bufFunction.getEndStates());
                            break;
                        }
                    }
                }else {
                    if (bufFunction.getEndStates() ==ft.getStartStates() && chain.charAt(i) == ft.getSignals()){
                        bufStrChain += ft.getSignals();
                        bufFunction = ft;
                        System.out.println(bufFunction.getStartStates() +" . " +bufFunction.getSignals() +" . "+
                                bufFunction.getEndStates());
                        break;
                    }
                }
            }
        }
        if (bufStrChain.equals(chain)){
            return true;
        }else{
            return false;
        }


    }


}
