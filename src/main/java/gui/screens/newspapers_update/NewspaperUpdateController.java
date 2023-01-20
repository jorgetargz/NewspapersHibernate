package gui.screens.newspapers_update;

import domain.modelo.Newspaper;
import gui.screens.common.BaseScreenController;
import gui.screens.common.ScreenConstants;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

public class NewspaperUpdateController extends BaseScreenController {

    private final NewspaperUpdateViewModel newspaperUpdateViewModel;
    @FXML
    private Label title;
    @FXML
    private MFXTextField nameTxt;
    @FXML
    private MFXDatePicker releaseDatePicker;
    @FXML
    private TableView<Newspaper> tableNewspapers;
    @FXML
    private TableColumn<Newspaper, Integer> columnId;
    @FXML
    private TableColumn<Newspaper, String> columnName;
    @FXML
    private TableColumn<Newspaper, LocalDate> columnPublishDate;

    @Inject
    public NewspaperUpdateController(NewspaperUpdateViewModel newspaperUpdateViewModel) {
        this.newspaperUpdateViewModel = newspaperUpdateViewModel;
    }

    public void initialize() {
        title.setText("Update newspaper");

        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("nameNewspaper"));
        columnPublishDate.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        tableNewspapers.setItems(newspaperUpdateViewModel.getObservableNewspapers());

        newspaperUpdateViewModel.loadNewspapers();

        newspaperUpdateViewModel.getState().addListener((observableValue, oldState, newState) -> {
            if (newState.getError() != null) {
                this.getPrincipalController().showAlert(Alert.AlertType.ERROR, ScreenConstants.ERROR, newState.getError());
                newspaperUpdateViewModel.cleanState();
            }
            if (newState.isNewspaerUpdated()) {
                this.getPrincipalController().showAlert(Alert.AlertType.INFORMATION, ScreenConstants.SUCCESS, "Newspaper updated successfully");
                newspaperUpdateViewModel.cleanState();
            }
        });
    }

    @FXML
    private void updateNewspaper() {
        Newspaper newspaper = tableNewspapers.getSelectionModel().getSelectedItem();
        newspaperUpdateViewModel.updateNewspaper(newspaper, nameTxt.getText(), releaseDatePicker.getValue());
    }

    @FXML
    private void updateFields() {
        Newspaper newspaper = tableNewspapers.getSelectionModel().getSelectedItem();
        if (newspaper != null) {
            nameTxt.setText(newspaper.getNameNewspaper());
            releaseDatePicker.setValue(newspaper.getReleaseDate());
        }
    }

}
