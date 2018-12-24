package machines;

public class FunctionTranslation {
    private char startStates;
    private char signals;
    private char endStates;

    public FunctionTranslation() {
    }

    public FunctionTranslation(char startStates, char signals, char endStates) {
        this.startStates = startStates;
        this.signals = signals;
        this.endStates = endStates;
    }

    public char getStartStates() {
        return startStates;
    }

    public char getSignals() {
        return signals;
    }

    public char getEndStates() {
        return endStates;
    }
}
