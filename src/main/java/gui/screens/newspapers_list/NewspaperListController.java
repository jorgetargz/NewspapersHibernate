package gui.screens.newspapers_list;

import domain.modelo.Article;
import domain.modelo.ArticleType;
import domain.modelo.Newspaper;
import gui.screens.common.BaseScreenController;
import gui.screens.common.ScreenConstants;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

public class NewspaperListController extends BaseScreenController {

    private final NewspaperListViewModel newspaperListViewModel;
    @FXML
    private TableView<NbrArticlesByType> tableNbrArticlesByType;
    @FXML
    private TableColumn<NbrArticlesByType, Integer> columnNbrArticles;
    @FXML
    private TableColumn<NbrArticlesByType, String> columnType;
    @FXML
    private TableView<Article> tableArticles;
    @FXML
    private TableColumn<Article, Integer> columnIdArticle;
    @FXML
    private TableColumn<Article, String> columnNameArticle;
    @FXML
    private TableColumn<Article, ArticleType> columnArticleType;
    @FXML
    private Label title;
    @FXML
    private TableView<Newspaper> tableNewspapers;
    @FXML
    private TableColumn<Newspaper, Integer> columnId;
    @FXML
    private TableColumn<Newspaper, String> columnName;
    @FXML
    private TableColumn<Newspaper, LocalDate> columnPublishDate;

    @Inject
    public NewspaperListController(NewspaperListViewModel newspaperListViewModel) {
        this.newspaperListViewModel = newspaperListViewModel;
    }

    public void initialize() {
        title.setText("List of newspapers");

        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("nameNewspaper"));
        columnPublishDate.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));

        columnIdArticle.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnNameArticle.setCellValueFactory(new PropertyValueFactory<>("nameArticle"));
        columnArticleType.setCellValueFactory(new PropertyValueFactory<>("type"));

        columnNbrArticles.setCellValueFactory(new PropertyValueFactory<>("nbrArticles"));
        columnType.setCellValueFactory(new PropertyValueFactory<>("articleType"));

        tableNewspapers.setItems(newspaperListViewModel.getObservableNewspapers());
        tableArticles.setItems(newspaperListViewModel.getObservableArticles());
        tableNbrArticlesByType.setItems(newspaperListViewModel.getObservableNbrArticlesByType());

        newspaperListViewModel.loadNewspapers();

        newspaperListViewModel.getState().addListener((observableValue, oldState, newState) -> {
            if (newState.getError() != null) {
                this.getPrincipalController().showAlert(Alert.AlertType.ERROR, ScreenConstants.ERROR, newState.getError());
                newspaperListViewModel.cleanState();
            }
        });
    }

    @FXML
    private void updateArticlesTables() {
        Newspaper newspaper = tableNewspapers.getSelectionModel().getSelectedItem();
        newspaperListViewModel.loadArticles(newspaper);
        newspaperListViewModel.loadNbrArticlesByType(newspaper);
    }

    @FXML
    private void deleteArticles() {
        Newspaper newspaper = tableNewspapers.getSelectionModel().getSelectedItem();
        newspaperListViewModel.deleteArticles(newspaper);
    }
}
