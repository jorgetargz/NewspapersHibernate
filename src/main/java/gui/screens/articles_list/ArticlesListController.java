package gui.screens.articles_list;

import domain.modelo.Article;
import gui.screens.common.BaseScreenController;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ArticlesListController extends BaseScreenController {

    private final ArticlesListViewModel articlesListViewModel;

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
    public ArticlesListController(ArticlesListViewModel articlesListViewModel) {
        this.articlesListViewModel = articlesListViewModel;
    }

    public void initialize() {
        title.setText("List all Articles");

        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("nameArticle"));
        columnArticleType.setCellValueFactory(new PropertyValueFactory<>("type"));
        columnNewspaperId.setCellValueFactory(new PropertyValueFactory<>("idNewspaper"));
        tableArticles.setItems(articlesListViewModel.getObservableArticles());

        articlesListViewModel.loadArticles();

        articlesListViewModel.getState().addListener((observableValue, oldState, newState) -> {
            if (newState.getError() != null) {
                getPrincipalController().showAlert(Alert.AlertType.ERROR, "Error", newState.getError());
                articlesListViewModel.cleanState();
            }
        });
    }

}

