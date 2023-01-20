package gui.screens.newspapers_delete;

import domain.modelo.Newspaper;
import gui.screens.common.BaseScreenController;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

public class NewspaperDeleteController extends BaseScreenController {

    private final NewspaperDeleteViewModel newspaperDeleteViewModel;
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
    public NewspaperDeleteController(NewspaperDeleteViewModel newspaperDeleteViewModel) {
        this.newspaperDeleteViewModel = newspaperDeleteViewModel;
    }

    public void initialize() {
        title.setText("Delete newspaper");

        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("nameNewspaper"));
        columnPublishDate.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        tableNewspapers.setItems(newspaperDeleteViewModel.getObservableNewspapers());

        newspaperDeleteViewModel.loadNewspapers();

        newspaperDeleteViewModel.getState().addListener((observableValue, oldState, newState) -> {
            if (newState.getError() != null) {
                getPrincipalController().showAlert(Alert.AlertType.ERROR, "Error", newState.getError());
                newspaperDeleteViewModel.cleanState();
            }
        });
    }

    @FXML
    private void deleteNewspaper() {
        Newspaper newspaper = tableNewspapers.getSelectionModel().getSelectedItem();
        newspaperDeleteViewModel.deleteNewspaper(newspaper);
    }

}
