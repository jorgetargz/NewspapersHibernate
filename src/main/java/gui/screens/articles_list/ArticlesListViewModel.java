package gui.screens.articles_list;

import common.Constantes;
import domain.modelo.Article;
import domain.modelo.ArticleType;
import domain.modelo.Readarticle;
import domain.modelo.Reader;
import domain.services.ServicesArticleTypes;
import domain.services.ServicesArticles;
import domain.services.ServicesReadarticles;
import gui.screens.common.ErrorManager;
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
    private final ServicesArticleTypes servicesArticleTypes;
    private final ServicesReadarticles servicesReadarticles;
    private final ErrorManager errorManager;
    private final ObjectProperty<ArticlesListState> state;
    private final ObservableList<Article> observableArticles;

    @Inject
    public ArticlesListViewModel(ServicesArticles servicesArticles, ServicesArticleTypes servicesArticleTypes, ServicesReadarticles servicesReadarticles, ErrorManager errorManager) {
        this.servicesArticles = servicesArticles;
        this.servicesArticleTypes = servicesArticleTypes;
        this.servicesReadarticles = servicesReadarticles;
        this.errorManager = errorManager;
        state = new SimpleObjectProperty<>(new ArticlesListState(null, false, null, null));
        observableArticles = FXCollections.observableArrayList();
    }

    public ReadOnlyObjectProperty<ArticlesListState> getState() {
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
            state.set(new ArticlesListState(errorManager.getErrorMessage(response.getLeft()), false, null, null));
        }
    }

    public void loadArticles(Reader reader) {
        Either<Integer, List<Article>> response = servicesArticles.scGetAllByReader(reader);
        if (response.isRight()) {
            observableArticles.clear();
            observableArticles.setAll(response.get());
        } else {
            state.set(new ArticlesListState(errorManager.getErrorMessage(response.getLeft()), false, null, null));
        }

    }

    public void scoreArticle(Article article, Reader reader, String scoreTxt) {
        try {
            int score = Integer.parseInt(scoreTxt);
            Readarticle readarticle = new Readarticle();
            readarticle.setArticleById(article);
            readarticle.setReaderById(reader);
            readarticle.setRating(score);
            Either<Integer, Readarticle> response = servicesReadarticles.scSave(readarticle);
            if (response.isRight()) {
                state.set(new ArticlesListState(null, true, null, null));
            } else if (response.getLeft() == Constantes.DB_CONSTRAINT_VIOLATION_CODE) {
                state.set(new ArticlesListState(null, false, article, null));
            } else {
                state.set(new ArticlesListState(errorManager.getErrorMessage(response.getLeft()), false, null, null));
            }
        } catch (NumberFormatException e) {
            state.set(new ArticlesListState("The score must be a number", false, null, null));
        }
    }

    public void updateScoreArticle(Article article, Reader reader, String scoreTxtText) {
        try {
            int score = Integer.parseInt(scoreTxtText);
            Readarticle readarticle = new Readarticle();
            readarticle.setArticleById(article);
            readarticle.setReaderById(reader);
            readarticle.setRating(score);
            Either<Integer, Readarticle> response = servicesReadarticles.scUpdate(readarticle);
            if (response.isRight()) {
                state.set(new ArticlesListState(null, true, null, null));
            } else {
                state.set(new ArticlesListState(errorManager.getErrorMessage(response.getLeft()), false, null, null));
            }
        } catch (NumberFormatException e) {
            state.set(new ArticlesListState("The score must be a number", false, null, null));
        }
    }

    public void loadMostReadArticleType() {
        Either<Integer, ArticleType> response = servicesArticleTypes.scGetMostRead();
        if (response.isRight()) {
            state.set(new ArticlesListState(null, false, null, response.get()));
        } else {
            state.set(new ArticlesListState(errorManager.getErrorMessage(response.getLeft()), false, null, null));
        }
    }

    public void cleanState() {
        state.set(new ArticlesListState(null, false, null, null));
    }
}
