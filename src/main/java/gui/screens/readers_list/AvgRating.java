package gui.screens.readers_list;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AvgRating {
    private final String newspaper;
    private final Double rating;
}
