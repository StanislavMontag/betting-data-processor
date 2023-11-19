package ee.playtech.assignment.transaction;

public class RealCasinoBalanceStrategy implements CasinoBalanceStrategy {
    private long casinoBalance = 0;

    @Override
    public void increaseBalance(long amount) {
        casinoBalance += amount;
    }
    @Override
    public void decreaseBalance(long amount) {
        casinoBalance -= amount;
    }
    @Override
    public long getCasinoBalance() {
        return casinoBalance;
    }
}

