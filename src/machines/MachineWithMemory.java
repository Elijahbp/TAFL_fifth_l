package machines;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import generic.Grammar;
import generic.Rule;

import java.util.ArrayList;
import java.util.Stack;

public class MachineWithMemory {
    private Grammar grm;
    private Table<Character, Character, String> transMatrix = HashBasedTable.create();
    private char[] inputString;
    private Stack<Character> stack;
    String str;

    public MachineWithMemory(Grammar grm, String buffer) {
        str=buffer;
        this.grm = grm;
        this.inputString = (buffer + "$").toCharArray();
        this.stack = new Stack<>();
        this.stack.push('$');
        this.stack.push(grm.getNonTerminalSymbols().get(0));
        System.out.printf("%-10s ",buffer);
        outputStack(stack);
        initMatrix();
        initMachine();
    }

    private void initMatrix() {
        ArrayList<Rule> rules = grm.getRules();
        ArrayList<Rule>rParts;
        for (Rule rule :
                rules) {
            rParts  = grm.getRules(rule.getLeftPart());
            for (Rule part :
                    rParts) {
                transMatrix.put(rule.getLeftPart().charAt(0), part.getRightPart().charAt(0), part.getRightPart());
            }
        }
    }

    private void initMachine() {
        for (int i = 0; i < inputString.length; ) {
            if (stack.peek() == inputString[i]) {
                stack.pop();
                str="";
                for (int j = i+1; j < inputString.length; j++) {
                    str+=inputString[j];
                }
                System.out.printf("%-10s ",str);
                outputStack(stack);
                i++;
            } else {
                tranStack(stack, inputString[i]);
            }
        }
    }

    private void tranStack(Stack<Character> stack, char symbol) {
        if (stack.peek() == symbol)
            return;
        String rule;
        if (transMatrix.contains(stack.peek(), symbol)) {
            rule = transMatrix.get(stack.peek(), symbol);
            stack.pop();
            for (int i = rule.length() - 1; i >= 0; i--) {
                stack.push(rule.charAt(i));
            }
            System.out.printf("%-10s ",str);
            outputStack(stack);
        } else throw new IndexOutOfBoundsException("Невозможно вывести цепочку!");
    }

    private void outputStack(Stack<Character> stk){
        StringBuilder stringBuilder = new StringBuilder();
        for (char c :
                stk) {
            stringBuilder.append(c);
        }
        System.out.println(stringBuilder.reverse().toString());
    }
}
