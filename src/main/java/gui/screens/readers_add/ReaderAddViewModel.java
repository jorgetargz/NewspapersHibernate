package gui.screens.readers_add;

import domain.modelo.Login;
import domain.modelo.Reader;
import domain.services.ServicesLogin;
import domain.services.ServicesReaders;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class ReaderAddViewModel {

    private final ServicesReaders servicesReaders;
    private final ServicesLogin servicesLogin;
    private final ObjectProperty<ReaderAddState> state;
    private final ObservableList<Reader> observableReaders;

    @Inject
    public ReaderAddViewModel(ServicesReaders servicesReaders, ServicesLogin servicesLogin) {
        this.servicesReaders = servicesReaders;
        this.servicesLogin = servicesLogin;
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
        observableReaders.clear();
        observableReaders.setAll(servicesReaders.getAll());
    }

    public void addReader(String nameInput, LocalDate birthdayInput, String usernameInput, String passwordInput, String emailInput) {
        if (nameInput.isEmpty() || birthdayInput == null || usernameInput.isEmpty() || passwordInput.isEmpty() || emailInput.isEmpty()) {
            state.set(new ReaderAddState("All fields are required", false));
        } else {
            Login login = new Login(usernameInput, passwordInput, emailInput, new Reader(nameInput, birthdayInput));
            Login result = servicesLogin.scRegister(login);
            if (result != null) {
                observableReaders.add(login.getReader());
                state.set(new ReaderAddState(null, true));
            } else {
                state.set(new ReaderAddState("Reader could not be added", false));
            }
        }
    }

    public void cleanState() {
        state.set(new ReaderAddState(null, false));
    }
}

