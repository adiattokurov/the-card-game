package thecardgameapi.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardsResponseBody {

    private boolean success;
    private String deck_id;
    private boolean shuffled;
    private int remaining;
    private List<Cards> cards;


}
