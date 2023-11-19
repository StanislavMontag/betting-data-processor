package ee.playtech.assignment.model;

public class Match {
    private final String id;
    private final double rateA;
    private final double rateB;
    private final MatchResult result;

    public enum MatchResult {
        A, B, DRAW
    }

    public Match(String id, double rateA, double rateB, MatchResult result) {
        this.id = id;
        this.rateA = rateA;
        this.rateB = rateB;
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public double getRateA() {
        return rateA;
    }

    public double getRateB() {
        return rateB;
    }

    public MatchResult getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "Match{" +
                "id='" + id + '\'' +
                ", rateA=" + rateA +
                ", rateB=" + rateB +
                ", result=" + result +
                '}';
    }
}

