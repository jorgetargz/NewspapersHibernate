package gui.screens.readers_delete;

import domain.modelo.Reader;
import gui.screens.common.BaseScreenController;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.Optional;

public class ReadersDeleteController extends BaseScreenController {

    private final ReadersDeleteViewModel readersDeleteViewModel;
    @FXML
    private Label title;
    @FXML
    private TableView<Reader> tableReaders;
    @FXML
    private TableColumn<Reader, Integer> columnId;
    @FXML
    private TableColumn<Reader, String> columnName;
    @FXML
    private TableColumn<Reader, LocalDate> columnDateOfBirth;

    @Inject
    public ReadersDeleteController(ReadersDeleteViewModel readersDeleteViewModel) {
        this.readersDeleteViewModel = readersDeleteViewModel;
    }

    public void initialize() {
        title.setText("Delete a Reader");

        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnDateOfBirth.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));

        tableReaders.setItems(readersDeleteViewModel.getObservableReaders());

        readersDeleteViewModel.loadReaders();

        readersDeleteViewModel.getState().addListener((observableValue, oldState, newState) -> {
            if (newState.getError() != null) {
                getPrincipalController().showAlert(Alert.AlertType.ERROR, "Error", newState.getError());
                readersDeleteViewModel.cleanState();
            }
            if (newState.getReaderWithActiveSubscriptions() != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Has active subscriptions");
                alert.setHeaderText("The reader has active subscriptions");
                alert.setContentText("Do you want to delete the reader and all his subscriptions?");
                alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.YES) {
                    readersDeleteViewModel.deleteReaderConfirmed(newState.getReaderWithActiveSubscriptions());
                    readersDeleteViewModel.cleanState();
                }
                alert.showAndWait();
                readersDeleteViewModel.cleanState();
            }
        });
    }

    @FXML
    private void deleteReader() {
        readersDeleteViewModel.deleteReader(tableReaders.getSelectionModel().getSelectedItem());
    }
}
