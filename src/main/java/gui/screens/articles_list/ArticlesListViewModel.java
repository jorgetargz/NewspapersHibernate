package gui.screens.articles_list;

import domain.modelo.Article;
import domain.modelo.Readarticle;
import domain.modelo.Reader;
import domain.services.ServicesArticles;
import domain.services.ServicesReadarticles;
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
    private final ServicesReadarticles servicesReadarticles;
    private final ObjectProperty<ArticlesListState> state;
    private final ObservableList<Article> observableArticles;

    @Inject
    public ArticlesListViewModel(ServicesArticles servicesArticles, ServicesReadarticles servicesReadarticles) {
        this.servicesArticles = servicesArticles;
        this.servicesReadarticles = servicesReadarticles;
        state = new SimpleObjectProperty<>(new ArticlesListState(null, false, null));
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
            state.set(new ArticlesListState(response.getLeft(), false, null));
        }
    }

    public void cleanState() {
        state.set(new ArticlesListState(null, false, null));
    }

    public void scoreArticle(Article article, Reader reader, String scoreTxt) {
        try {
            int score = Integer.parseInt(scoreTxt);
            Readarticle readarticle = new Readarticle();
            readarticle.setArticleById(article);
            readarticle.setReaderById(reader);
            readarticle.setRating(score);
            Either<String, Readarticle> response = servicesReadarticles.scSave(readarticle);
            if (response.isRight()) {
                state.set(new ArticlesListState(null, true, null));
            } else if (response.getLeft().contains("already scored")) {
                state.set(new ArticlesListState(null, false, article));
            } else {
                state.set(new ArticlesListState(response.getLeft(), false, null));
            }
        } catch (NumberFormatException e) {
            state.set(new ArticlesListState("The score must be a number", false, null));
        }
    }

    public void updateScoreArticle(Article article, Reader reader, String scoreTxtText) {
        try {
            int score = Integer.parseInt(scoreTxtText);
            Readarticle readarticle = new Readarticle();
            readarticle.setArticleById(article);
            readarticle.setReaderById(reader);
            readarticle.setRating(score);
            Either<String, Readarticle> response = servicesReadarticles.scUpdate(readarticle);
            if (response.isRight()) {
                state.set(new ArticlesListState(null, true, null));
            } else {
                state.set(new ArticlesListState(response.getLeft(), false, null));
            }
        } catch (NumberFormatException e) {
            state.set(new ArticlesListState("The score must be a number", false, null));
        }
    }
}
