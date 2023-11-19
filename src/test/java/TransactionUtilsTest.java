import ee.playtech.assignment.model.Match;
import ee.playtech.assignment.model.Player;
import ee.playtech.assignment.model.Transaction;
import ee.playtech.assignment.transaction.CasinoBalanceStrategy;
import ee.playtech.assignment.transaction.RealCasinoBalanceStrategy;
import ee.playtech.assignment.utils.TransactionUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

public class TransactionUtilsTest {

    @Test
    public void testIsTransactionLegalForDeposit() {
        Player player = new Player("player1");
        Transaction deposit = new Transaction("player1", Transaction.TransactionType.DEPOSIT, null, 100, null);
        assertTrue(TransactionUtils.isTransactionLegal(deposit, player, null));
    }

    @Test
    public void testIsTransactionLegalForWithdrawWithSufficientBalance() {
        Player player = new Player("player1");
        player.deposit(100); // Предполагая, что метод deposit корректно изменяет баланс
        Transaction withdraw = new Transaction("player1", Transaction.TransactionType.WITHDRAW, null, 50, null);
        assertTrue(TransactionUtils.isTransactionLegal(withdraw, player, null));
    }

    @Test
    public void testIsTransactionLegalForWithdrawWithInsufficientBalance() {
        Player player = new Player("player1");
        player.deposit(30);
        Transaction withdraw = new Transaction("player1", Transaction.TransactionType.WITHDRAW, null, 50, null);
        assertFalse(TransactionUtils.isTransactionLegal(withdraw, player, null));
    }

    @Test
    public void testIsTransactionLegalForBetWithSufficientBalanceAndValidMatch() {
        Player player = new Player("player1");
        player.deposit(100);
        List<Match> matches = Arrays.asList(new Match("match1", 1.5, 2.5, Match.MatchResult.A));
        Transaction bet = new Transaction("player1", Transaction.TransactionType.BET, "match1", 50, "A");
        assertTrue(TransactionUtils.isTransactionLegal(bet, player, matches));
    }

    @Test
    public void testIsTransactionLegalForBetWithInsufficientBalanceOrInvalidMatch() {
        Player player = new Player("player1");
        player.deposit(30);
        List<Match> matches = Arrays.asList(new Match("match1", 1.5, 2.5, Match.MatchResult.A));
        Transaction bet = new Transaction("player1", Transaction.TransactionType.BET, "match1", 50, "A");
        assertFalse(TransactionUtils.isTransactionLegal(bet, player, matches));

        bet = new Transaction("player1", Transaction.TransactionType.BET, "nonexistentMatch", 20, "A");
        assertFalse(TransactionUtils.isTransactionLegal(bet, player, matches));
    }

    @Test
    public void testProcessTransactionDeposit() {
        Player player = new Player("player1");
        Transaction deposit = new Transaction("player1", Transaction.TransactionType.DEPOSIT, null, 100, null);
        TransactionUtils.processTransaction(deposit, player, null, null);

        assertEquals(100, player.getBalance());
    }

    @Test
    public void testProcessTransactionWithdrawWithSufficientFunds() {
        Player player = new Player("player1");
        player.deposit(200);
        Transaction withdraw = new Transaction("player1", Transaction.TransactionType.WITHDRAW, null, 50, null);
        TransactionUtils.processTransaction(withdraw, player, null, null);

        assertEquals(150, player.getBalance());
    }

    @Test
    public void testProcessTransactionWithdrawWithInsufficientFunds() {
        Player player = new Player("player1");
        player.deposit(30);
        Transaction withdraw = new Transaction("player1", Transaction.TransactionType.WITHDRAW, null, 50, null);
        TransactionUtils.processTransaction(withdraw, player, null, null);

        assertEquals(30, player.getBalance());
    }

    @Test
    public void testProcessTransactionWithWinningBet() {
        Player player = new Player("player1");
        player.deposit(100);
        Match match = new Match("match1", 2.0, 1.5, Match.MatchResult.A);
        List<Match> matches = Arrays.asList(match);
        Transaction bet = new Transaction("player1", Transaction.TransactionType.BET, "match1", 50, "A");

        CasinoBalanceStrategy balanceStrategy = new RealCasinoBalanceStrategy();
        TransactionUtils.processTransaction(bet, player, matches, balanceStrategy);

        assertEquals(200, player.getBalance());
        assertEquals(-100, balanceStrategy.getCasinoBalance());
    }

    @Test
    public void testProcessTransactionWithLosingBet() {
        Player player = new Player("player1");
        player.deposit(100);
        Match match = new Match("match1", 2.0, 1.5, Match.MatchResult.B);
        List<Match> matches = Arrays.asList(match);
        Transaction bet = new Transaction("player1", Transaction.TransactionType.BET, "match1", 50, "A");

        CasinoBalanceStrategy balanceStrategy = new RealCasinoBalanceStrategy();
        TransactionUtils.processTransaction(bet, player, matches, balanceStrategy);

        assertEquals(50, player.getBalance());
        assertEquals(50, balanceStrategy.getCasinoBalance());
    }
}

