package ee.playtech.assignment.parsing;

import ee.playtech.assignment.model.Match;

import java.util.ArrayList;
import java.util.List;

public class MatchParser {
    public static List<Match> parseMatches(List<String> lines) {
        List<Match> matches = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(",");
            String id = parts[0];
            double rateA = Double.parseDouble(parts[1]);
            double rateB = Double.parseDouble(parts[2]);
            Match.MatchResult result = Match.MatchResult.valueOf(parts[3]);
            matches.add(new Match(id, rateA, rateB, result));
        }
        return matches;
    }
}

