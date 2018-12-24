package bin;

public enum TypeGrammar {
    ZERO(0),
    CONTEXT_SENSITIVE(1),
    CONTEXT_FREE(2),
    REGULAR_LEFT(3),
    REGULAR_RIGHT(4);

    private int type;

    TypeGrammar(int type) {
        this.type = type;
    }
    public int getType() {
        return type;
    }
}
