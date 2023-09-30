package thecardgameapi.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import thecardgameapi.pojo.CardsResponseBody;
import thecardgameapi.utils.CardsUtils;
import thecardgameapi.utils.ConfigProperties;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static thecardgameapi.utils.CardsUtils.*;
public class TheCardGameTest {

    @BeforeTest
    public void setUp() {
        ConfigProperties.initConfig();
        CardsUtils.checkBaseApiEndPointIsUp();
    }
    @Test
    public void testCards() throws JsonProcessingException {
//       	Get a new deck
        String deckId = getNewDeck();
        System.out.println(deckId);
//      	Shuffle it
        shuffleCards(deckId);
//       Deal three cards to each of two players
        CardsResponseBody player1 = dealCards(deckId, 3);
        CardsResponseBody player2 = dealCards(deckId, 3);

//      Check whether either has blackjack
        boolean hasBlackjack1 = isBlackjack(player1.getCards());
        boolean hasBlackjack2 = isBlackjack(player2.getCards());

//  	If either has, write out which one does
        if (hasBlackjack1) {
            System.out.println("Player 1 has blackjack");
        } else if (hasBlackjack2) {
            System.out.println("Player 2 has blackjack");
        } else {
            System.out.println("No Player has blackjack");
        }
    }

}