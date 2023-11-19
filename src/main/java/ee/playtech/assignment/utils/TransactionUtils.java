package ee.playtech.assignment.utils;

import ee.playtech.assignment.model.Match;
import ee.playtech.assignment.model.Player;
import ee.playtech.assignment.model.Transaction;
import ee.playtech.assignment.transaction.CasinoBalanceStrategy;

import java.util.List;

/**
 * The TransactionUtils class provides utility methods for processing transactions in a casino game.
 * It includes methods for checking the legality of transactions and processing them accordingly.
 */
public class TransactionUtils {

    /**
     * Checks if a given transaction is legal based on the player's balance and the matches available for betting.
     *
     * @param transaction The transaction to be checked.
     * @param player The player performing the transaction.
     * @param matches The list of matches available for betting.
     * @return true if the transaction is legal, false otherwise.
     */
    public static boolean isTransactionLegal(Transaction transaction, Player player, List<Match> matches) {
        return switch (transaction.getType()) {
            case DEPOSIT -> true;
            case WITHDRAW -> player.getBalance() >= transaction.getAmount();
            case BET -> player.getBalance() >= transaction.getAmount() &&
                    matches.stream().anyMatch(m -> m.getId().equals(transaction.getMatchId()));
        };
    }

    /**
     * Processes a given transaction by modifying the player's balance or handling bets based on the transaction type.
     *
     * @param transaction The transaction to be processed.
     * @param player The player performing the transaction.
     * @param matches The list of matches available for betting.
     * @param balanceStrategy The strategy for updating the casino's balance.
     */
    public static void processTransaction(Transaction transaction, Player player, List<Match> matches, CasinoBalanceStrategy balanceStrategy) {
        switch (transaction.getType()) {
            case DEPOSIT:
                player.deposit(transaction.getAmount());
                break;
            case WITHDRAW:
                player.withdraw(transaction.getAmount());
                break;
            case BET:
                handleBet(transaction, player, matches, balanceStrategy);
                break;
        }
    }

    /**
     * Handles a betting transaction by placing a bet and calculating the outcome based on the match result.
     *
     * @param transaction The betting transaction to be handled.
     * @param player The player making the bet.
     * @param matches The list of matches available for betting.
     * @param balanceStrategy The strategy for updating the casino's balance.
     */
    private static void handleBet(Transaction transaction, Player player, List<Match> matches, CasinoBalanceStrategy balanceStrategy) {
        Match match = matches.stream()
                .filter(m -> m.getId().equals(transaction.getMatchId()))
                .findFirst()
                .orElse(null);

        if (match != null) {
            player.placeBet(transaction.getAmount());
            calculateBetOutcome(player, transaction, match, balanceStrategy);
        }
    }

    /**
     * Calculates the outcome of a bet and updates the player's balance and the casino's balance accordingly.
     *
     * @param player The player who placed the bet.
     * @param transaction The betting transaction.
     * @param match The match on which the bet was placed.
     * @param balanceStrategy The strategy for updating the casino's balance.
     */
    private static void calculateBetOutcome(Player player, Transaction transaction, Match match, CasinoBalanceStrategy balanceStrategy) {
        if (match.getResult() == Match.MatchResult.valueOf(transaction.getBetSide())) {
            double rate = transaction.getBetSide().equals("A") ? match.getRateA() : match.getRateB();
            int winnings = (int) (transaction.getAmount() * rate);
            player.winBet(winnings + transaction.getAmount());
            balanceStrategy.decreaseBalance(winnings);
        } else if (match.getResult().equals(Match.MatchResult.DRAW)) {
            player.refund(transaction.getAmount());
        } else {
            balanceStrategy.increaseBalance(transaction.getAmount());
        }
    }
}
