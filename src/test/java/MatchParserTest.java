import ee.playtech.assignment.model.Match;
import ee.playtech.assignment.parsing.MatchParser;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MatchParserTest {

    @Test
    public void testParseMatchesWithValidInput() {
        List<String> lines = Arrays.asList(
                "match1,1.5,2.5,A",
                "match2,1.8,1.8,DRAW"
        );
        List<Match> matches = MatchParser.parseMatches(lines);

        assertNotNull(matches);
        assertEquals(2, matches.size());

        Match match1 = matches.get(0);
        assertEquals("match1", match1.getId());
        assertEquals(1.5, match1.getRateA());
        assertEquals(2.5, match1.getRateB());
        assertEquals(Match.MatchResult.A, match1.getResult());

        Match match2 = matches.get(1);
        assertEquals("match2", match2.getId());
        assertEquals(1.8, match2.getRateA());
        assertEquals(1.8, match2.getRateB());
        assertEquals(Match.MatchResult.DRAW, match2.getResult());
    }

    @Test
    public void testParseMatchesWithInvalidInput() {
        List<String> lines = Arrays.asList(
                "match1,notANumber,2.5,A"
        );

        assertThrows(NumberFormatException.class, () -> {
            MatchParser.parseMatches(lines);
        });
    }
    @Test
    public void testParseMatchesWithEmptyList() {
        List<String> emptyLines = Collections.emptyList();
        List<Match> matches = MatchParser.parseMatches(emptyLines);

        assertNotNull(matches, "The result mustn't be null");
        assertTrue(matches.isEmpty(), "The result must be an empty list");
    }
}
