package gui.screens.newspapers_update;

import domain.modelo.Newspaper;
import domain.services.ServicesNewspapers;
import gui.screens.common.ErrorManager;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.List;

public class NewspaperUpdateViewModel {

    private final ServicesNewspapers servicesNewspapers;
    private final ErrorManager errorManager;
    private final ObjectProperty<NewspaperUpdateState> state;
    private final ObservableList<Newspaper> observableNewspapers;

    @Inject
    public NewspaperUpdateViewModel(ServicesNewspapers servicesNewspapers, ErrorManager errorManager) {
        this.servicesNewspapers = servicesNewspapers;
        this.errorManager = errorManager;
        state = new SimpleObjectProperty<>(new NewspaperUpdateState(null, false));
        observableNewspapers = FXCollections.observableArrayList();
    }

    public ObjectProperty<NewspaperUpdateState> getState() {
        return state;
    }

    public ObservableList<Newspaper> getObservableNewspapers() {
        return FXCollections.unmodifiableObservableList(observableNewspapers);
    }

    public void loadNewspapers() {
        Either<Integer, List<Newspaper>> response = servicesNewspapers.getNewspapers();
        if (response.isRight()) {
            observableNewspapers.setAll(response.get());
        } else {
            state.setValue(new NewspaperUpdateState(errorManager.getErrorMessage(response.getLeft()), false));
        }
    }

    public void updateNewspaper(Newspaper newspaper, String nameInput, LocalDate releaseDate) {
        if (newspaper == null || nameInput.isEmpty() || releaseDate == null) {
            state.set(new NewspaperUpdateState("All fields except password are required", false));
        } else {
            newspaper.setNameNewspaper(nameInput);
            newspaper.setReleaseDate(releaseDate);
            Either<Integer, Newspaper> response = servicesNewspapers.updateNewspaper(newspaper);
            if (response.isRight()) {
                loadNewspapers();
                state.set(new NewspaperUpdateState(null, true));
            } else {
                state.setValue(new NewspaperUpdateState(errorManager.getErrorMessage(response.getLeft()), false));
            }
        }
    }

    public void cleanState() {
        state.set(new NewspaperUpdateState(null, false));
    }
}
