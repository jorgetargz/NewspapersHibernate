package gui.screens.newspapers_delete;

import domain.modelo.Newspaper;
import domain.services.ServicesNewspapers;
import gui.screens.common.ErrorManager;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class NewspaperDeleteViewModel {

    private final ServicesNewspapers servicesNewspapers;
    private final ErrorManager errorManager;
    private final ObjectProperty<NewspaperDeleteState> state;
    private final ObservableList<Newspaper> observableNewspapers;

    @Inject
    public NewspaperDeleteViewModel(ServicesNewspapers servicesNewspapers, ErrorManager errorManager) {
        this.servicesNewspapers = servicesNewspapers;
        this.errorManager = errorManager;
        state = new SimpleObjectProperty<>(new NewspaperDeleteState(null));
        observableNewspapers = FXCollections.observableArrayList();
    }

    public ReadOnlyObjectProperty<NewspaperDeleteState> getState() {
        return state;
    }

    public ObservableList<Newspaper> getObservableNewspapers() {
        return FXCollections.unmodifiableObservableList(observableNewspapers);
    }

    public void loadNewspapers() {
        Either<Integer, List<Newspaper>> response = servicesNewspapers.scGetAll();
        if (response.isRight()) {
            observableNewspapers.setAll(response.get());
        } else {
            state.setValue(new NewspaperDeleteState(errorManager.getErrorMessage(response.getLeft())));
        }
    }

    public void deleteNewspaper(Newspaper newspaper) {
        Either<Integer, Boolean> response = servicesNewspapers.scDelete(newspaper);
        if (response.isRight()) {
            loadNewspapers();
            state.set(new NewspaperDeleteState(null));
        } else {
            state.setValue(new NewspaperDeleteState(errorManager.getErrorMessage(response.getLeft())));
        }
    }

    public void cleanState() {
        state.set(new NewspaperDeleteState(null));
    }
}
