package gui.screens.newspapers_list;

import domain.modelo.Article;
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
import java.util.Set;

public class NewspaperListViewModel {

    private final ServicesNewspapers servicesNewspapers;
    private final ObjectProperty<NewspaperListState> state;
    private final ObservableList<Newspaper> observableNewspapers;
    private final ObservableList<Article> observableArticles;

    @Inject
    public NewspaperListViewModel(ServicesNewspapers servicesNewspapers) {
        this.servicesNewspapers = servicesNewspapers;
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
        Either<String, List<Newspaper>> either = servicesNewspapers.getNewspapers();
        if (either.isRight()) {
            observableNewspapers.setAll(either.get());
        } else {
            state.setValue(new NewspaperListState(either.getLeft()));
        }
    }

    public void loadArticles(Newspaper newspaper) {
        Either<String, Newspaper> either = servicesNewspapers.get(newspaper.getId());
        if (either.isRight()) {
            observableArticles.setAll(either.get().getArticles());
        } else {
            state.setValue(new NewspaperListState(either.getLeft()));
        }
    }

    public void cleanState() {
        state.set(new NewspaperListState(null));
    }
}
