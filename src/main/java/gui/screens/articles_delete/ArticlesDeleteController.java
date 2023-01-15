package gui.screens.articles_delete;

import domain.modelo.Article;
import gui.screens.common.BaseScreenController;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ArticlesDeleteController extends BaseScreenController {

    private final ArticlesDeleteViewModel articlesDeleteViewModel;

    @FXML
    private Label title;
    @FXML
    private TableView<Article> tableArticles;
    @FXML
    private TableColumn<Article, Integer> columnId;
    @FXML
    private TableColumn<Article, String> columnName;
    @FXML
    private TableColumn<Article, Integer> columnArticleType;
    @FXML
    private TableColumn<Article, Integer> columnNewspaperId;


    @Inject
    public ArticlesDeleteController(ArticlesDeleteViewModel articlesDeleteViewModel) {
        this.articlesDeleteViewModel = articlesDeleteViewModel;
    }

    public void initialize() {
        title.setText("Delete Articles");

        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("nameArticle"));
        columnArticleType.setCellValueFactory(new PropertyValueFactory<>("type"));
        columnNewspaperId.setCellValueFactory(new PropertyValueFactory<>("idNewspaper"));
        tableArticles.setItems(articlesDeleteViewModel.getObservableArticles());

        articlesDeleteViewModel.loadArticles();

        articlesDeleteViewModel.getState().addListener((observableValue, oldState, newState) -> {
            if (newState.getError() != null) {
                getPrincipalController().showAlert(Alert.AlertType.ERROR, "Error", newState.getError());
                articlesDeleteViewModel.cleanState();
            }
        });
    }

    @FXML
    private void deleteArticle() {
        Article article = tableArticles.getSelectionModel().getSelectedItem();
        if (article != null) {
            articlesDeleteViewModel.deleteArticle(article);
        }
    }

}

