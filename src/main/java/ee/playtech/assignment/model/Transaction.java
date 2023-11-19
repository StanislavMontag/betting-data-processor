package ee.playtech.assignment.model;

public class Transaction {
    private final String playerId;
    private final TransactionType type;
    private final String matchId;
    private final int amount;
    private final String betSide;

    public enum TransactionType {
        BET, DEPOSIT, WITHDRAW
    }

    public Transaction(String playerId, TransactionType type, String matchId, int amount, String betSide) {
        this.playerId = playerId;
        this.type = type;
        this.matchId = matchId;
        this.amount = amount;
        this.betSide = betSide;
    }

    public TransactionType getType() {
        return type;
    }

    public String getMatchId() {
        return matchId;
    }

    public int getAmount() {
        return amount;
    }

    public String getBetSide() {
        return betSide;
    }

    @Override
    public String toString() {
        return playerId + " " + type + " " + matchId + " " + amount + " " + betSide;
    }
}
