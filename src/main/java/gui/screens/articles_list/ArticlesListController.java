package gui.screens.articles_list;

import domain.modelo.Article;
import domain.modelo.Reader;
import gui.screens.common.BaseScreenController;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Optional;

public class ArticlesListController extends BaseScreenController {

    private final ArticlesListViewModel articlesListViewModel;
    @FXML
    private MFXTextField scoreTxt;
    @FXML
    private MFXButton scoreBtn;
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
            if (newState.isArticleScored()) {
                getPrincipalController().showAlert(Alert.AlertType.INFORMATION, "Success", "Article scored");
                articlesListViewModel.cleanState();
            }
            if (newState.getArticleAlreadyScored() != null) {
                showScoreArticleAlredyScoredDialog(newState.getArticleAlreadyScored());
                articlesListViewModel.cleanState();
            }
        });
    }

    private void showScoreArticleAlredyScoredDialog(Article article) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Re-score");
        alert.setHeaderText("Article already scored");
        alert.setContentText("Do you want to re-score the article?");
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            Reader reader = getPrincipalController().getReader();
            if (article != null) {
                articlesListViewModel.updateScoreArticle(article, reader, scoreTxt.getText());
            }
        }
    }

    @Override
    public void principalCargado() {
        if (getPrincipalController().getReader().getId() < 0) {
            scoreTxt.setVisible(false);
            scoreBtn.setVisible(false);
        }
    }

    @FXML
    private void scoreArticle() {
        Article article = tableArticles.getSelectionModel().getSelectedItem();
        Reader reader = getPrincipalController().getReader();
        if (article != null) {
            articlesListViewModel.scoreArticle(article, reader, scoreTxt.getText());
        }
    }
}

