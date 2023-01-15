package gui.screens.readers_delete;

import domain.modelo.Reader;
import domain.services.ServicesLogin;
import domain.services.ServicesReaders;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ReadersDeleteViewModel {

    private final ServicesReaders servicesReaders;
    private final ServicesLogin servicesLogin;
    private final ObjectProperty<ReadersDeleteState> state;
    private final ObservableList<Reader> observableReaders;

    @Inject
    public ReadersDeleteViewModel(ServicesReaders servicesReaders, ServicesLogin servicesLogin) {
        this.servicesReaders = servicesReaders;
        this.servicesLogin = servicesLogin;
        state = new SimpleObjectProperty<>(new ReadersDeleteState(null));
        observableReaders = FXCollections.observableArrayList();
    }

    public ObjectProperty<ReadersDeleteState> getState() {
        return state;
    }

    public ObservableList<Reader> getObservableReaders() {
        return FXCollections.unmodifiableObservableList(observableReaders);
    }

    public void loadReaders() {
        observableReaders.clear();
        observableReaders.setAll(servicesReaders.getAll());
    }

    public void deleteReader(Reader reader) {
        if (reader == null) {
            state.set(new ReadersDeleteState("Select a reader"));
        } else {
            servicesLogin.scDelete(reader.getLogin());
            loadReaders();
        }

    }

    public void cleanState() {
        state.set(new ReadersDeleteState(null));
    }
}
