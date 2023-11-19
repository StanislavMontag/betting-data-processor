import ee.playtech.assignment.model.Match;
import ee.playtech.assignment.model.Player;
import ee.playtech.assignment.model.Transaction;
import ee.playtech.assignment.transaction.Casino;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CasinoTest {
    private Map<String, Player> players;
    private List<Match> matches;
    private Casino casino;

    @BeforeEach
    public void setUp() {
        players = new HashMap<>();
        matches = Arrays.asList(new Match("match1", 1.5, 2.0, Match.MatchResult.A));
        casino = new Casino(players, matches);
    }

    @Test
    public void testProcessTransactionsWithLegitimateAndIllegitimateTransactions() {
        Player player1 = new Player("player1");
        player1.addTransaction(new Transaction("player1", Transaction.TransactionType.DEPOSIT, null, 100, null));
        player1.addTransaction(new Transaction("player1", Transaction.TransactionType.BET, "match1", 50, "A"));
        players.put("player1", player1);

        Player player2 = new Player("player2");
        player2.addTransaction(new Transaction("player2", Transaction.TransactionType.WITHDRAW, null, 100, null)); // Нелегитимная транзакция
        players.put("player2", player2);

        casino.processTransactions();

        assertEquals(1, casino.getLegitimatePlayers().size());
        assertEquals(1, casino.getIllegitimateTransactions().size());
        assertTrue(casino.getLegitimatePlayers().contains(player1));
        assertTrue(casino.getIllegitimateTransactions().contains(player2.getTransactionHistory().get(0)));
    }
}
