package ee.playtech.assignment;

import ee.playtech.assignment.transaction.Casino;
import ee.playtech.assignment.model.Match;
import ee.playtech.assignment.model.Player;

import ee.playtech.assignment.parsing.MatchParser;
import ee.playtech.assignment.parsing.PlayerDataParser;

import ee.playtech.assignment.utils.DataReader;
import ee.playtech.assignment.utils.ResultWriter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
    private final static String PATH = "src/main/resources/";
    public static void main(String[] args) {
        try {
            List<String> matchLines = DataReader.readFile(PATH + "match_data.txt");
            List<Match> matches = MatchParser.parseMatches(matchLines);

            List<String> playerLines = DataReader.readFile(PATH + "player_data.txt");
            Map<String, Player> players = PlayerDataParser.parsePlayerData(playerLines);

            Casino casino = new Casino(players, matches);
            casino.processTransactions();

            ResultWriter.writeResults(PATH + "result.txt", casino.getLegitimatePlayers(), casino.getIllegitimateTransactions(), casino.getCasinoBalance());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}