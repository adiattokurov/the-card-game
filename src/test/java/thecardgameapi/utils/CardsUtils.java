package thecardgameapi.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import thecardgameapi.pojo.Cards;
import thecardgameapi.pojo.CardsResponseBody;
import java.util.List;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;

public class CardsUtils {

    /**
     * This method check is site is up and running.
     */
    public static void checkBaseApiEndPointIsUp() {
        // 1. Navigate to https://deckofcardsapi.com/
        // 2.	Confirm the site is up
        given().contentType(JSON).
                when().
                get(ConfigProperties.getProperties("apiBaseUrl")).
                then().
                assertThat().
                statusCode(200);
    }

    /**
     * This method returns a new Deck
     *
     * @throws JsonProcessingException
     * @returns deck_id
     */
    public static String getNewDeck() throws JsonProcessingException {
        ConfigProperties.initConfig();
        Response response = given().contentType(JSON).
                when()
                .get(ConfigProperties.getProperties("getNewDeckUrl"))
                .then()
                .assertThat()
                .statusCode(200)
                .extract().response();

        ObjectMapper objectMapper = new ObjectMapper();
        CardsResponseBody responseBody = objectMapper.readValue(response.asString(), CardsResponseBody.class);
        return responseBody.getDeck_id();
    }

    /**
     * This method takes a new deck as parameter and shuffles it
     *
     * @param deckId
     * @throws JsonProcessingException
     */
    public static void shuffleCards(String deckId) throws JsonProcessingException {

        given().contentType(JSON)
                .when()
                .pathParam("deck_id", deckId)
                .when()
                .post(ConfigProperties.getProperties("apiBaseUrl") + "api/deck/{deck_id}/shuffle/")
                .then()
                .assertThat()
                .statusCode(200)
                .body("shuffled", equalTo(true));

    }

    /**
     * This method deals three cards
     *
     * @param deckId
     * @param count
     * @throws JsonProcessingException
     */
    public static CardsResponseBody dealCards(String deckId, int count) throws JsonProcessingException {
        Response response =
                given().contentType(JSON)
                        .when()
                        .pathParam("deck_id", deckId)
                        .queryParam("count", count)
                        .get(ConfigProperties.getProperties("apiBaseUrl") + "api/deck/{deck_id}/draw/")
                        .then()
                        .assertThat()
                        .statusCode(200)
                        .extract().response();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        CardsResponseBody cards = objectMapper.readValue(response.asString(), CardsResponseBody.class);
        return cards;
    }

    public static boolean isBlackjack(List<Cards> hand) {
        // Check if the hand contains an Ace and a 10-value card
        boolean hasAce = false;
        boolean hasTenValueCard = false;

        for (Cards card : hand) {
            if (card.getValue().equals("ACE")) {
                hasAce = true;
            } else if (card.getValue().equals("10") || card.getValue().equals("JACK") ||
                    card.getValue().equals("QUEEN") || card.getValue().equals("KING")) {
                hasTenValueCard = true;
            }
        }

        // Return true if both conditions are met
        return hasAce && hasTenValueCard;
    }

}
