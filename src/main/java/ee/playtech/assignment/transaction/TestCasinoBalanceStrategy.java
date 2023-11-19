package ee.playtech.assignment.transaction;

public class TestCasinoBalanceStrategy implements CasinoBalanceStrategy {
    private long testCasinoBalance = 0;

    @Override
    public void increaseBalance(long amount) {
        testCasinoBalance += amount;
    }

    @Override
    public void decreaseBalance(long amount) {
        testCasinoBalance -= amount;
    }

    @Override
    public long getCasinoBalance() {
        return testCasinoBalance;
    }
}
