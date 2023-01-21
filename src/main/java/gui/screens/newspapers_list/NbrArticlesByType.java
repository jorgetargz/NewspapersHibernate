package gui.screens.newspapers_list;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NbrArticlesByType {
    private final String articleType;
    private final Integer nbrArticles;
}
