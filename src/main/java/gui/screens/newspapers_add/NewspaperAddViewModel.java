package gui.screens.newspapers_add;

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

import java.time.LocalDate;
import java.util.List;

public class NewspaperAddViewModel {

    private final ServicesNewspapers servicesNewspapers;
    private final ErrorManager errorManager;
    private final ObjectProperty<NewspaperAddState> state;
    private final ObservableList<Newspaper> observableNewspapers;

    @Inject
    public NewspaperAddViewModel(ServicesNewspapers servicesNewspapers, ErrorManager errorManager) {
        this.servicesNewspapers = servicesNewspapers;
        this.errorManager = errorManager;
        state = new SimpleObjectProperty<>(new NewspaperAddState(null, false));
        observableNewspapers = FXCollections.observableArrayList();
    }

    public ReadOnlyObjectProperty<NewspaperAddState> getState() {
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
            state.setValue(new NewspaperAddState(errorManager.getErrorMessage(response.getLeft()), false));
        }
    }

    public void addNewspaper(String name, LocalDate releaseDate) {
        Newspaper newspaper = new Newspaper(name, releaseDate);
        Either<Integer, Newspaper> response = servicesNewspapers.scSave(newspaper);
        if (response.isRight()) {
            observableNewspapers.add(response.get());
            state.set(new NewspaperAddState(null, true));
        } else {
            state.setValue(new NewspaperAddState(errorManager.getErrorMessage(response.getLeft()), false));
        }
    }

    public void cleanState() {
        state.set(new NewspaperAddState(null, false));
    }
}
