package ee.playtech.assignment.transaction;

public interface CasinoBalanceStrategy {
    void increaseBalance(long amount);
    void decreaseBalance(long amount);
    long getCasinoBalance();
}
