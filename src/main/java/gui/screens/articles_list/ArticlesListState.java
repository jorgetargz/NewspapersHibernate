package gui.screens.articles_list;

import domain.modelo.Article;
import lombok.Data;

@Data
public class ArticlesListState {
    private final String error;
    private final boolean articleScored;
    private final Article articleAlreadyScored;
}
