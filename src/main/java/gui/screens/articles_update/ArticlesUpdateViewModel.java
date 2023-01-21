package gui.screens.articles_update;

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

public class ArticlesUpdateViewModel {
    private final ServicesArticles servicesArticles;
    private final ServicesArticleTypes servicesArticleTypes;
    private final ErrorManager errorManager;
    private final ServicesNewspapers servicesNewspapers;
    private final ObjectProperty<ArticlesUpdateState> state;
    private final ObservableList<Article> observableArticles;
    private final ObservableList<ArticleType> observableArticleTypes;
    private final ObservableList<Newspaper> observableNewspapers;

    @Inject
    public ArticlesUpdateViewModel(ServicesArticles servicesArticles, ServicesArticleTypes servicesArticleTypes, ErrorManager errorManager, ServicesNewspapers servicesNewspapers) {
        this.servicesArticles = servicesArticles;
        this.servicesArticleTypes = servicesArticleTypes;
        this.errorManager = errorManager;
        this.servicesNewspapers = servicesNewspapers;
        state = new SimpleObjectProperty<>(new ArticlesUpdateState(null, false));
        observableArticles = FXCollections.observableArrayList();
        observableArticleTypes = FXCollections.observableArrayList();
        observableNewspapers = FXCollections.observableArrayList();
    }

    public ReadOnlyObjectProperty<ArticlesUpdateState> getState() {
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
            state.set(new ArticlesUpdateState(errorManager.getErrorMessage(response.getLeft()), false));
        }
    }

    public void loadArticleTypes() {
        Either<Integer, List<ArticleType>> response = servicesArticleTypes.scGetAll();
        if (response.isRight()) {
            observableArticleTypes.clear();
            observableArticleTypes.setAll(response.get());
        } else {
            state.set(new ArticlesUpdateState(errorManager.getErrorMessage(response.getLeft()), false));
        }
    }

    public void loadNewspapers() {
        Either<Integer, List<Newspaper>> response = servicesNewspapers.scGetAll();
        if (response.isRight()) {
            observableNewspapers.clear();
            observableNewspapers.setAll(response.get());
        } else {
            state.set(new ArticlesUpdateState(errorManager.getErrorMessage(response.getLeft()), false));
        }
    }

    public void updateArticle(Article articleDB, String nameText, ArticleType articleType, Newspaper newspaper) {
        if (nameText.isEmpty() || articleType == null || newspaper == null) {
            state.set(new ArticlesUpdateState("Please fill all the fields", false));
        } else {
            articleDB.setNameArticle(nameText);
            articleDB.setType(articleType);
            articleDB.setIdNewspaper(newspaper);
            Either<Integer, Article> response = servicesArticles.scUpdate(articleDB);
            if (response.isRight()) {
                state.set(new ArticlesUpdateState(null, true));
                loadArticles();
            } else {
                state.set(new ArticlesUpdateState(errorManager.getErrorMessage(response.getLeft()), false));
            }
        }
    }

    public void cleanState() {
        state.set(new ArticlesUpdateState(null, false));
    }
}
