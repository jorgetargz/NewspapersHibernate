package gui.screens.readers_update;

import domain.modelo.Login;
import domain.modelo.Reader;
import domain.services.ServicesLogin;
import domain.services.ServicesReaders;
import gui.screens.common.ErrorManager;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.List;

public class ReadersUpdateViewModel {

    private final ServicesReaders servicesReaders;
    private final ServicesLogin servicesLogin;
    private final ErrorManager errorManager;
    private final ObjectProperty<ReadersUpdateState> state;
    private final ObservableList<Reader> observableReaders;

    @Inject
    public ReadersUpdateViewModel(ServicesReaders servicesReaders, ServicesLogin servicesLogin, ErrorManager errorManager) {
        this.servicesReaders = servicesReaders;
        this.servicesLogin = servicesLogin;
        this.errorManager = errorManager;
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
        Either<Integer, List<Reader>> response = servicesReaders.scGetAll();
        if (response.isRight()) {
            observableReaders.setAll(response.get());
        } else {
            state.setValue(new ReadersUpdateState(errorManager.getErrorMessage(response.getLeft()), false));
        }
    }


    public void updateReader(Reader reader, String nameInput, LocalDate birthdayInput, String passwordInput) {
        if (reader == null || nameInput.isEmpty() || birthdayInput == null) {
            state.set(new ReadersUpdateState("All fields except password are required", false));
        } else {
            reader.setName(nameInput);
            reader.setDateOfBirth(birthdayInput);
            Either<Integer, Login> response = servicesLogin.scUpdate(reader.getLogin(), passwordInput);
            if (response.isRight()) {
                state.set(new ReadersUpdateState(null, true));
                loadReaders();
            } else {
                state.set(new ReadersUpdateState(errorManager.getErrorMessage(response.getLeft()), false));
            }
        }
    }

    public void cleanState() {
        state.set(new ReadersUpdateState(null, false));
    }
}
