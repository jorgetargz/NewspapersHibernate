package gui.screens.articles_list;

import domain.modelo.Article;
import domain.services.ServicesArticles;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ArticlesListViewModel {

    private final ServicesArticles servicesArticles;
    private final ObjectProperty<ArticlesListState> state;
    private final ObservableList<Article> observableArticles;

    @Inject
    public ArticlesListViewModel(ServicesArticles servicesArticles) {
        this.servicesArticles = servicesArticles;
        state = new SimpleObjectProperty<>(new ArticlesListState(null));
        observableArticles = FXCollections.observableArrayList();
    }

    public ReadOnlyObjectProperty<ArticlesListState> getState() {
        return state;
    }

    public ObservableList<Article> getObservableArticles() {
        return FXCollections.unmodifiableObservableList(observableArticles);
    }

    public void loadArticles() {
        Either<String, List<Article>> response = servicesArticles.scGetAll();
        if (response.isRight()) {
            observableArticles.clear();
            observableArticles.setAll(response.get());
        } else {
            state.set(new ArticlesListState(response.getLeft()));
        }
    }

    public void cleanState() {
        state.set(new ArticlesListState(null));
    }

}
