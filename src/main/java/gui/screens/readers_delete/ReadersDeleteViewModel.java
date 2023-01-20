package gui.screens.readers_delete;

import common.Constantes;
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

import java.util.List;

public class ReadersDeleteViewModel {

    private final ServicesReaders servicesReaders;
    private final ServicesLogin servicesLogin;
    private final ErrorManager errorManager;
    private final ObjectProperty<ReadersDeleteState> state;
    private final ObservableList<Reader> observableReaders;

    @Inject
    public ReadersDeleteViewModel(ServicesReaders servicesReaders, ServicesLogin servicesLogin, ErrorManager errorManager) {
        this.servicesReaders = servicesReaders;
        this.servicesLogin = servicesLogin;
        this.errorManager = errorManager;
        state = new SimpleObjectProperty<>(new ReadersDeleteState(null, null));
        observableReaders = FXCollections.observableArrayList();
    }

    public ObjectProperty<ReadersDeleteState> getState() {
        return state;
    }

    public ObservableList<Reader> getObservableReaders() {
        return FXCollections.unmodifiableObservableList(observableReaders);
    }

    public void loadReaders() {
        Either<Integer, List<Reader>> response = servicesReaders.getAll();
        if (response.isRight()) {
            observableReaders.setAll(response.get());
        } else {
            state.setValue(new ReadersDeleteState(errorManager.getErrorMessage(response.getLeft()), null));
        }
    }

    public void deleteReader(Reader reader) {
        if (reader == null) {
            state.set(new ReadersDeleteState("Select a reader", null));
        } else {
            Either<Integer, Boolean> response = servicesLogin.scDelete(reader.getLogin());
            if (response.isRight()) {
                state.set(new ReadersDeleteState("Reader deleted", null));
                loadReaders();
            } else if (response.getLeft() == Constantes.READER_HAS_ACTIVE_SUBSCRIPTIONS_ERROR_CODE) {
                state.set(new ReadersDeleteState(null, reader));
            } else {
                state.set(new ReadersDeleteState(errorManager.getErrorMessage(response.getLeft()), null));
            }
        }
    }

    public void deleteReaderConfirmed(Reader reader) {
        if (reader == null) {
            state.set(new ReadersDeleteState("Select a reader", null));
        } else {
            Either<Integer, Boolean> response = servicesLogin.scDeleteWithActiveSubscriptions(reader.getLogin());
            if (response.isRight()) {
                state.set(new ReadersDeleteState("Reader deleted", null));
                loadReaders();
            } else {
                state.set(new ReadersDeleteState(errorManager.getErrorMessage(response.getLeft()), null));
            }
        }
    }

    public void cleanState() {
        state.set(new ReadersDeleteState(null, null));
    }
}
