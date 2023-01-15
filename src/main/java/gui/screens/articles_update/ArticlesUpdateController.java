package gui.screens.articles_update;

import domain.modelo.Article;
import domain.modelo.ArticleType;
import domain.modelo.Newspaper;
import gui.screens.common.BaseScreenController;
import gui.screens.common.ScreenConstants;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ArticlesUpdateController extends BaseScreenController {

    private final ArticlesUpdateViewModel articlesUpdateViewModel;
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
    @FXML
    private MFXTextField inputName;
    @FXML
    private MFXComboBox<ArticleType> comboType;
    @FXML
    private MFXComboBox<Newspaper> comboNewspaper;

    @Inject
    public ArticlesUpdateController(ArticlesUpdateViewModel articlesUpdateViewModel) {
        this.articlesUpdateViewModel = articlesUpdateViewModel;
    }

    public void initialize() {
        title.setText("Update Article");

        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("nameArticle"));
        columnArticleType.setCellValueFactory(new PropertyValueFactory<>("type"));
        columnNewspaperId.setCellValueFactory(new PropertyValueFactory<>("idNewspaper"));
        tableArticles.setItems(articlesUpdateViewModel.getObservableArticles());
        comboType.setItems(articlesUpdateViewModel.getObservableArticleTypes());
        comboNewspaper.setItems(articlesUpdateViewModel.getObservableNewspapers());

        articlesUpdateViewModel.loadArticles();
        articlesUpdateViewModel.loadArticleTypes();
        articlesUpdateViewModel.loadNewspapers();

        articlesUpdateViewModel.getState().addListener((observableValue, oldState, newState) -> {
            if (newState.getError() != null) {
                getPrincipalController().showAlert(Alert.AlertType.ERROR, "Error", newState.getError());
                articlesUpdateViewModel.cleanState();
            }
            if (newState.isArticleAdded()) {
                getPrincipalController().showAlert(Alert.AlertType.INFORMATION, ScreenConstants.SUCCESS, "Article updated successfully");
                articlesUpdateViewModel.cleanState();
            }
        });
    }

    @FXML
    private void updateArticle() {
        Article article = tableArticles.getSelectionModel().getSelectedItem();
        if (article != null) {
            articlesUpdateViewModel.updateArticle(article, inputName.getText(), comboType.getValue(), comboNewspaper.getValue());
        }
    }

    @FXML
    private void updateFields() {
        Article article = tableArticles.getSelectionModel().getSelectedItem();
        inputName.setText(article.getNameArticle());
        comboType.setValue(article.getType());
        comboNewspaper.setValue(article.getIdNewspaper());
    }
}

