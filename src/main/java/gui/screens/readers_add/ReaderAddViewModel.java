package gui.screens.readers_add;

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

public class ReaderAddViewModel {

    private final ServicesReaders servicesReaders;
    private final ServicesLogin servicesLogin;
    private final ErrorManager errorManager;
    private final ObjectProperty<ReaderAddState> state;
    private final ObservableList<Reader> observableReaders;

    @Inject
    public ReaderAddViewModel(ServicesReaders servicesReaders, ServicesLogin servicesLogin, ErrorManager errorManager) {
        this.servicesReaders = servicesReaders;
        this.servicesLogin = servicesLogin;
        this.errorManager = errorManager;
        state = new SimpleObjectProperty<>(new ReaderAddState(null, false));
        observableReaders = FXCollections.observableArrayList();
    }

    public ObjectProperty<ReaderAddState> getState() {
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
            state.setValue(new ReaderAddState(errorManager.getErrorMessage(response.getLeft()), false));
        }
    }

    public void addReader(String nameInput, LocalDate birthdayInput, String usernameInput, String passwordInput, String emailInput) {
        if (nameInput.isEmpty() || birthdayInput == null || usernameInput.isEmpty() || passwordInput.isEmpty() || emailInput.isEmpty()) {
            state.set(new ReaderAddState("All fields are required", false));
        } else {
            Login login = new Login(usernameInput, passwordInput, emailInput, new Reader(nameInput, birthdayInput));
            Either<Integer, Login> response = servicesLogin.scRegister(login);
            if (response.isRight()) {
                observableReaders.add(login.getReader());
                state.set(new ReaderAddState(null, true));
            } else {
                state.set(new ReaderAddState(errorManager.getErrorMessage(response.getLeft()), false));
            }
        }
    }

    public void cleanState() {
        state.set(new ReaderAddState(null, false));
    }
}

