package gui.screens.newspapers_delete;

import domain.modelo.Newspaper;
import domain.services.ServicesNewspapers;
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
    private final ObjectProperty<NewspaperDeleteState> state;
    private final ObservableList<Newspaper> observableNewspapers;

    @Inject
    public NewspaperDeleteViewModel(ServicesNewspapers servicesNewspapers) {
        this.servicesNewspapers = servicesNewspapers;
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
        Either<String, List<Newspaper>> either = servicesNewspapers.getNewspapers();
        if (either.isRight()) {
            observableNewspapers.setAll(either.get());
        } else {
            state.setValue(new NewspaperDeleteState(either.getLeft()));
        }
    }

    public void deleteNewspaper(Newspaper newspaper) {
        Either<String, Newspaper> either = servicesNewspapers.deleteNewspaper(newspaper);
        if (either.isRight()) {
            loadNewspapers();
            state.set(new NewspaperDeleteState(null));
        } else {
            state.set(new NewspaperDeleteState(either.getLeft()));
        }
    }

    public void cleanState() {
        state.set(new NewspaperDeleteState(null));
    }
}
