import ee.playtech.assignment.model.Player;
import ee.playtech.assignment.model.Transaction;
import ee.playtech.assignment.parsing.PlayerDataParser;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PlayerDataParserTest {

    @Test
    public void testParsePlayerDataWithValidInput() {
        List<String> lines = Arrays.asList(
                "player1,DEPOSIT,,100",
                "player1,BET,match1,50,A",
                "player2,WITHDRAW,,30"
        );
        Map<String, Player> players = PlayerDataParser.parsePlayerData(lines);

        assertNotNull(players);
        assertEquals(2, players.size());

        Player player1 = players.get("player1");
        assertNotNull(player1);
        assertEquals("player1", player1.getId());
        assertEquals(2, player1.getTransactionHistory().size());
        assertEquals(Transaction.TransactionType.DEPOSIT, player1.getTransactionHistory().get(0).getType());

        Player player2 = players.get("player2");
        assertNotNull(player2);
        assertEquals("player2", player2.getId());
        assertEquals(1, player2.getTransactionHistory().size());
        assertEquals(Transaction.TransactionType.WITHDRAW, player2.getTransactionHistory().get(0).getType());
    }

    @Test
    public void testParsePlayerDataWithIncompleteData() {
        List<String> lines = List.of(
                "player1,DEPOSIT"
        );
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> PlayerDataParser.parsePlayerData(lines));
    }
}
