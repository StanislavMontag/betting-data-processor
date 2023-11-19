package ee.playtech.assignment.utils;

import ee.playtech.assignment.model.Player;
import ee.playtech.assignment.model.Transaction;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ResultWriter {

    public static void writeResults(String filename,
                                    List<Player> legitimatePlayers,
                                    List<Transaction> illegitimatePlayerDetails,
                                    long casinoBalance) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            // Writing data for legitimate players
            for (Player player : legitimatePlayers) {
                String resultLine = String.format("%s %d %.2f\n",
                        player.getId(),
                        player.getBalance(),
                        player.calculateWinRate());
                writer.write(resultLine);
            }

            writer.write("\n");

            // Writing data for illegitimate players
            for (Transaction transaction : illegitimatePlayerDetails) {
                writer.write(transaction + "\n");
            }

            writer.write("\n");

            // final casino balance
            writer.write(String.valueOf(casinoBalance));
        }
    }
}

