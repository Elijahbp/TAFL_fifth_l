package generic;

public class Rule {

    private String leftPart;
    private String rightPart;

    public Rule(String leftPart, String rightPart) {
        this.leftPart = leftPart;
        this.rightPart = rightPart;
    }

    public String getLeftPart() {
        return leftPart;
    }

    public String getRightPart() {
        return rightPart;
    }
}
