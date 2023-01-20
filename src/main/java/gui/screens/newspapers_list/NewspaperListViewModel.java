package gui.screens.newspapers_list;

import domain.modelo.Article;
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

public class NewspaperListViewModel {

    private final ServicesNewspapers servicesNewspapers;
    private final ErrorManager errorManager;
    private final ObjectProperty<NewspaperListState> state;
    private final ObservableList<Newspaper> observableNewspapers;
    private final ObservableList<Article> observableArticles;

    @Inject
    public NewspaperListViewModel(ServicesNewspapers servicesNewspapers, ErrorManager errorManager) {
        this.servicesNewspapers = servicesNewspapers;
        this.errorManager = errorManager;
        state = new SimpleObjectProperty<>(new NewspaperListState(null));
        observableNewspapers = FXCollections.observableArrayList();
        observableArticles = FXCollections.observableArrayList();
    }

    public ReadOnlyObjectProperty<NewspaperListState> getState() {
        return state;
    }

    public ObservableList<Newspaper> getObservableNewspapers() {
        return FXCollections.unmodifiableObservableList(observableNewspapers);
    }

    public ObservableList<Article> getObservableArticles() {
        return FXCollections.unmodifiableObservableList(observableArticles);
    }

    public void loadNewspapers() {
        Either<Integer, List<Newspaper>> response = servicesNewspapers.getNewspapers();
        if (response.isRight()) {
            observableNewspapers.setAll(response.get());
        } else {
            state.setValue(new NewspaperListState(errorManager.getErrorMessage(response.getLeft())));
        }
    }

    public void loadArticles(Newspaper newspaper) {
        Either<Integer, Newspaper> response = servicesNewspapers.get(newspaper.getId());
        if (response.isRight()) {
            observableArticles.setAll(response.get().getArticles());
        } else {
            state.setValue(new NewspaperListState(errorManager.getErrorMessage(response.getLeft())));
        }
    }

    public void cleanState() {
        state.set(new NewspaperListState(null));
    }
}
