package ee.playtech.assignment.parsing;

import ee.playtech.assignment.model.Player;
import ee.playtech.assignment.model.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerDataParser {
    public static Map<String, Player> parsePlayerData(List<String> lines) {
        Map<String, Player> players = new HashMap<>();
        for (String line : lines) {
            String[] parts = line.split(",");
            String playerId = parts[0];
            Player player = players.getOrDefault(playerId, new Player(playerId));
            players.putIfAbsent(playerId, player);

            Transaction.TransactionType type = Transaction.TransactionType.valueOf(parts[1]);
            String matchId = parts[2].length() > 2 ? parts[2] : null;
            int amount = parts.length > 3 && !parts[3].isEmpty() ? Integer.parseInt(parts[3]) : 0;
            String betSide = parts.length > 4 ? parts[4] : null;
            player.addTransaction(new Transaction(playerId, type, matchId, amount, betSide));

        }
        return players;
    }
}

