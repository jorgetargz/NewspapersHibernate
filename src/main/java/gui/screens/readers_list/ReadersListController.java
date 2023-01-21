package gui.screens.readers_list;

import domain.modelo.ArticleType;
import domain.modelo.Newspaper;
import domain.modelo.Reader;
import gui.screens.common.BaseScreenController;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

public class ReadersListController extends BaseScreenController {


    private final ReadersListViewModel readersListViewModel;
    @FXML
    private TableView<AvgRating> tableAvgRatings;
    @FXML
    private TableColumn<AvgRating, String> columnNewspaer;
    @FXML
    private TableColumn<AvgRating, Double> columnAvgRating;
    @FXML
    private Label title;
    @FXML
    private MFXComboBox<Newspaper> newspapersCombo;
    @FXML
    private MFXComboBox<ArticleType> articleTypesCombo;
    @FXML
    private TableView<Reader> tableReaders;
    @FXML
    private TableColumn<Reader, Integer> columnId;
    @FXML
    private TableColumn<Reader, String> columnName;
    @FXML
    private TableColumn<Reader, LocalDate> columnDateOfBirth;

    @Inject
    public ReadersListController(ReadersListViewModel readersListViewModel) {
        this.readersListViewModel = readersListViewModel;
    }

    public void initialize() {
        title.setText("List all Readers");

        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnDateOfBirth.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));

        columnNewspaer.setCellValueFactory(new PropertyValueFactory<>("newspaper"));
        columnAvgRating.setCellValueFactory(new PropertyValueFactory<>("rating"));

        tableReaders.setItems(readersListViewModel.getObservableReaders());
        tableAvgRatings.setItems(readersListViewModel.getObservableAvgRatings());
        articleTypesCombo.setItems(readersListViewModel.getObservableArticleTypes());
        newspapersCombo.setItems(readersListViewModel.getObservableNewspapers());

        readersListViewModel.loadReaders();
        readersListViewModel.loadArticleTypes();
        readersListViewModel.loadNewspapers();

        readersListViewModel.getState().addListener((observableValue, oldState, newState) -> {
            if (newState.getError() != null) {
                getPrincipalController().showAlert(Alert.AlertType.ERROR, "Error", newState.getError());
                readersListViewModel.cleanState();
            }
        });
    }

    @FXML
    private void filterByArticleType() {
        if (articleTypesCombo.getValue() != null) {
            readersListViewModel.filterByArticleType(articleTypesCombo.getValue());
        }
    }

    @FXML
    private void filterByNewspaper() {
        if (newspapersCombo.getValue() != null) {
            readersListViewModel.filterByNewspaper(newspapersCombo.getValue());
        }
    }

    @FXML
    private void updateAvgRatingsTable() {
        Reader reader = tableReaders.getSelectionModel().getSelectedItem();
        if (reader != null) {
            readersListViewModel.updateAvgRatingsMap(reader);
        }
    }
}
