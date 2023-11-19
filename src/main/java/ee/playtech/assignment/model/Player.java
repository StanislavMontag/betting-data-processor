package ee.playtech.assignment.model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String id;
    private long balance;
    private final List<Transaction> transactionHistory;
    private int wins;
    private int bets;

    public Player(String id) {
        this.id = id;
        this.balance = 0;
        this.transactionHistory = new ArrayList<>();
        this.wins = 0;
        this.bets = 0;
    }

    public Player(Player player) {
        this.id = player.getId();
        this.balance = 0;
        this.transactionHistory = player.getTransactionHistory();
        this.wins = 0;
        this.bets = 0;
    }

    public String getId() {
        return id;
    }

    public long getBalance() {
        return balance;
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public void incrementWins() {
        this.wins++;
    }

    public void incrementBets() {
        this.bets++;
    }

    public void addTransaction(Transaction transaction) {
        transactionHistory.add(transaction);
    }

    public double calculateWinRate() {
        if (bets == 0) return 0;
        return Double.parseDouble(String.format("%.2f", (double) wins / bets));
    }

    public void deposit(int amount) {
        this.balance += amount;
    }

    public void withdraw(int amount) {
        if (this.balance >= amount) {
            this.balance -= amount;
        }
    }

    public void placeBet(int amount) {
        if (this.balance >= amount) {
            this.balance -= amount;
            incrementBets();
        }
    }

    public void refund(int amount) {
        this.balance += amount;
    }

    public void winBet(int winnings) {
        this.balance += winnings;
        incrementWins();
    }
    @Override
    public String toString() {
        return "Player{" +
                "id='" + id + '\'' +
                ", balance=" + balance +
                ", wins=" + wins +
                ", bets=" + bets +
                ", winRate=" + calculateWinRate() +
                '}';
    }
}
