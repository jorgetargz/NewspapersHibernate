package gui.screens.articles_add;

import domain.modelo.Article;
import domain.modelo.ArticleType;
import domain.modelo.Newspaper;
import domain.services.ServicesArticleTypes;
import domain.services.ServicesArticles;
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

public class ArticlesAddViewModel {
    private final ServicesArticles servicesArticles;
    private final ServicesArticleTypes servicesArticleTypes;
    private final ServicesNewspapers servicesNewspapers;
    private final ErrorManager errorManager;
    private final ObjectProperty<ArticlesAddState> state;
    private final ObservableList<Article> observableArticles;
    private final ObservableList<ArticleType> observableArticleTypes;
    private final ObservableList<Newspaper> observableNewspapers;

    @Inject
    public ArticlesAddViewModel(ServicesArticles servicesArticles, ServicesArticleTypes servicesArticleTypes, ServicesNewspapers servicesNewspapers, ErrorManager errorManager) {
        this.servicesArticles = servicesArticles;
        this.servicesArticleTypes = servicesArticleTypes;
        this.servicesNewspapers = servicesNewspapers;
        this.errorManager = errorManager;
        state = new SimpleObjectProperty<>(new ArticlesAddState(null, false));
        observableArticles = FXCollections.observableArrayList();
        observableArticleTypes = FXCollections.observableArrayList();
        observableNewspapers = FXCollections.observableArrayList();
    }

    public ReadOnlyObjectProperty<ArticlesAddState> getState() {
        return state;
    }

    public ObservableList<Article> getObservableArticles() {
        return FXCollections.unmodifiableObservableList(observableArticles);
    }

    public ObservableList<ArticleType> getObservableArticleTypes() {
        return FXCollections.unmodifiableObservableList(observableArticleTypes);
    }

    public ObservableList<Newspaper> getObservableNewspapers() {
        return FXCollections.unmodifiableObservableList(observableNewspapers);
    }

    public void loadArticles() {
        Either<Integer, List<Article>> response = servicesArticles.scGetAll();
        if (response.isRight()) {
            observableArticles.clear();
            observableArticles.setAll(response.get());
        } else {
            state.set(new ArticlesAddState(errorManager.getErrorMessage(response.getLeft()), false));
        }
    }

    public void loadArticleTypes() {
        Either<Integer, List<ArticleType>> response = servicesArticleTypes.scGetAll();
        if (response.isRight()) {
            observableArticleTypes.clear();
            observableArticleTypes.setAll(response.get());
        } else {
            state.set(new ArticlesAddState(errorManager.getErrorMessage(response.getLeft()), false));
        }
    }

    public void loadNewspapers() {
        Either<Integer, List<Newspaper>> response = servicesNewspapers.scGetAll();
        if (response.isRight()) {
            observableNewspapers.clear();
            observableNewspapers.setAll(response.get());
        } else {
            state.set(new ArticlesAddState(errorManager.getErrorMessage(response.getLeft()), false));
        }
    }

    public void addArticle(String nameText, ArticleType articleType, Newspaper newspaper) {
        if (nameText.isEmpty() || articleType == null || newspaper == null) {
            state.set(new ArticlesAddState("Please fill all the fields", false));
        } else {
            Article article = new Article(nameText, articleType, newspaper);
            Either<Integer, Article> result = servicesArticles.scSave(article);
            if (result.isRight()) {
                state.set(new ArticlesAddState(null, true));
                loadArticles();
            } else {
                state.set(new ArticlesAddState(errorManager.getErrorMessage(result.getLeft()), false));
            }
        }
    }

    public void cleanState() {
        state.set(new ArticlesAddState(null, false));
    }
}
