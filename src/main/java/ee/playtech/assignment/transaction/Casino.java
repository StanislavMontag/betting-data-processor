package ee.playtech.assignment.transaction;

import ee.playtech.assignment.model.Match;
import ee.playtech.assignment.model.Player;
import ee.playtech.assignment.model.Transaction;
import ee.playtech.assignment.utils.TransactionUtils;

import java.util.*;

/**
 * The Casino class represents a casino with a collection of players and matches.
 * It is responsible for processing transactions of all players, determining
 * the legitimacy of these transactions, and maintaining an updated balance for
 * the casino based on the outcomes of the transactions.
 */
public class Casino {
    private final Map<String, Player> players;
    private final List<Match> matches;
    private final List<Player> legitimatePlayers;
    private final List<Transaction> illegitimateTransactions;
    private final CasinoBalanceStrategy testBalanceStrategy;
    private final CasinoBalanceStrategy realBalanceStrategy;

    public Casino(Map<String, Player> players, List<Match> matches) {
        this.players = players;
        this.matches = matches;
        this.testBalanceStrategy = new TestCasinoBalanceStrategy();
        this.realBalanceStrategy = new RealCasinoBalanceStrategy();
        this.legitimatePlayers = new ArrayList<>();
        this.illegitimateTransactions = new ArrayList<>();
    }

    /**
     * Processes all transactions for each player in the casino. It first tests the transactions
     * for their legitimacy and then applies them to update the player's and casino's balance.
     */
    public void processTransactions() {
        for (Player player : players.values()) {
            Player testPlayer = clonePlayer(player);
            boolean isLegitimate = true;
            Transaction firstIllegalTransaction = null;

            for (Transaction transaction : player.getTransactionHistory()) {
                if (!TransactionUtils.isTransactionLegal(transaction, testPlayer, matches)) {
                    isLegitimate = false;
                    firstIllegalTransaction = transaction;
                    break;
                }
                TransactionUtils.processTransaction(transaction, testPlayer, matches, testBalanceStrategy);
            }
            if (isLegitimate) {
                legitimatePlayers.add(player);
            } else {
                illegitimateTransactions.add(firstIllegalTransaction);
            }
        }
        for (Player player : legitimatePlayers) {
            for (Transaction transaction : player.getTransactionHistory()) {
                TransactionUtils.processTransaction(transaction, player, matches, realBalanceStrategy);
            }
        }
    }

    /**
     * Creates a deep copy of the given player.
     *
     * @param original The player to be cloned.
     * @return A new Player object that is a deep copy of the original player.
     */
    private Player clonePlayer(Player original) {
        return new Player(original);
    }

    /**
     * Gets the list of players who have only legitimate transactions.
     *
     * @return A list of players with legitimate transactions.
     */
    public List<Player> getLegitimatePlayers() {
        return legitimatePlayers;
    }

    /**
     * Gets the list of transactions that are deemed illegitimate.
     *
     * @return A list of illegitimate transactions.
     */
    public List<Transaction> getIllegitimateTransactions() {
        return illegitimateTransactions;
    }

    /**
     * Gets the current balance of the casino.
     *
     * @return The balance of the casino.
     * @throws IllegalStateException If the real balance strategy is not set for the casino.
     */
    public long getCasinoBalance() {
        if (realBalanceStrategy instanceof RealCasinoBalanceStrategy)
            return realBalanceStrategy.getCasinoBalance();
        throw new IllegalStateException("No real balance strategy set for the casino.");
    }
}


