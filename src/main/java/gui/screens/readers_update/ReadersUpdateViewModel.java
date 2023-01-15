package gui.screens.readers_update;

import domain.modelo.Reader;
import domain.services.ServicesReaders;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class ReadersUpdateViewModel {

    private final ServicesReaders servicesReaders;
    private final ObjectProperty<ReadersUpdateState> state;
    private final ObservableList<Reader> observableReaders;

    @Inject
    public ReadersUpdateViewModel(ServicesReaders servicesReaders) {
        this.servicesReaders = servicesReaders;
        state = new SimpleObjectProperty<>(new ReadersUpdateState(null, false));
        observableReaders = FXCollections.observableArrayList();
    }

    public ObjectProperty<ReadersUpdateState> getState() {
        return state;
    }

    public ObservableList<Reader> getObservableReaders() {
        return FXCollections.unmodifiableObservableList(observableReaders);
    }

    public void loadReaders() {
        observableReaders.clear();
        observableReaders.setAll(servicesReaders.getAll());
    }


    public void updateReader(Reader reader, String nameInput, LocalDate birthdayInput, String passwordInput) {
        if (reader == null || nameInput.isEmpty() || birthdayInput == null) {
            state.set(new ReadersUpdateState("All fields except password are required", false));
        } else {
            reader.setName(nameInput);
            reader.setDateOfBirth(birthdayInput);
            Reader result = servicesReaders.update(reader, passwordInput);
            if (result == null) {
                state.set(new ReadersUpdateState("Error updating reader", false));
            } else {
                state.set(new ReadersUpdateState("Reader updated successfully", true));
               loadReaders();
            }
        }
    }

    public void cleanState() {
        state.set(new ReadersUpdateState(null, false));
    }
}
