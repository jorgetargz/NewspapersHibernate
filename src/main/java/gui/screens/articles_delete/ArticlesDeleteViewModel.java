package gui.screens.articles_delete;

import domain.modelo.Article;
import domain.services.ServicesArticles;
import gui.screens.common.ErrorManager;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ArticlesDeleteViewModel {

    private final ServicesArticles servicesArticles;
    private final ErrorManager errorManager;
    private final ObjectProperty<ArticlesDeleteState> state;
    private final ObservableList<Article> observableArticles;

    @Inject
    public ArticlesDeleteViewModel(ServicesArticles servicesArticles, ErrorManager errorManager) {
        this.servicesArticles = servicesArticles;
        this.errorManager = errorManager;
        state = new SimpleObjectProperty<>(new ArticlesDeleteState(null));
        observableArticles = FXCollections.observableArrayList();
    }

    public ReadOnlyObjectProperty<ArticlesDeleteState> getState() {
        return state;
    }

    public ObservableList<Article> getObservableArticles() {
        return FXCollections.unmodifiableObservableList(observableArticles);
    }

    public void loadArticles() {
        Either<Integer, List<Article>> response = servicesArticles.scGetAll();
        if (response.isRight()) {
            observableArticles.clear();
            observableArticles.setAll(response.get());
        } else {
            state.set(new ArticlesDeleteState(errorManager.getErrorMessage(response.getLeft())));
        }
    }

    public void cleanState() {
        state.set(new ArticlesDeleteState(null));
    }

    public void deleteArticle(Article article) {
        Either<Integer, Boolean> response = servicesArticles.scDelete(article);
        if (response.isRight()) {
            observableArticles.remove(article);
        } else {
            state.set(new ArticlesDeleteState(errorManager.getErrorMessage(response.getLeft())));
        }
    }
}
