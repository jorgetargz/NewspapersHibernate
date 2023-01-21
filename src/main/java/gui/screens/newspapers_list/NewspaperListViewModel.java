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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class NewspaperListViewModel {

    private final ServicesNewspapers servicesNewspapers;
    private final ErrorManager errorManager;
    private final ObjectProperty<NewspaperListState> state;
    private final ObservableList<Newspaper> observableNewspapers;
    private final ObservableList<Article> observableArticles;
    private final ObservableList<NbrArticlesByType> observableNbrArticlesByType;

    @Inject
    public NewspaperListViewModel(ServicesNewspapers servicesNewspapers, ErrorManager errorManager) {
        this.servicesNewspapers = servicesNewspapers;
        this.errorManager = errorManager;
        state = new SimpleObjectProperty<>(new NewspaperListState(null));
        observableNewspapers = FXCollections.observableArrayList();
        observableArticles = FXCollections.observableArrayList();
        observableNbrArticlesByType = FXCollections.observableArrayList();
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

    public ObservableList<NbrArticlesByType> getObservableNbrArticlesByType() {
        return FXCollections.unmodifiableObservableList(observableNbrArticlesByType);
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
        if (newspaper != null) {
            Either<Integer, Newspaper> response = servicesNewspapers.get(newspaper.getId());
            if (response.isRight()) {
                observableArticles.setAll(response.get().getArticles());
            } else {
                state.setValue(new NewspaperListState(errorManager.getErrorMessage(response.getLeft())));
            }
        } else {
            observableArticles.clear();
        }
    }

    public void cleanState() {
        state.set(new NewspaperListState(null));
    }

    public void deleteArticles(Newspaper newspaper) {
        if (newspaper != null) {
            Either<Integer, Boolean> response = servicesNewspapers.deleteArticles(newspaper);
            if (response.isRight()) {
                observableArticles.clear();
            } else {
                state.setValue(new NewspaperListState(errorManager.getErrorMessage(response.getLeft())));
            }
        } else {
            state.setValue(new NewspaperListState("No newspaper selected"));
        }
    }

    public void loadNbrArticlesByType(Newspaper newspaper) {
        if (newspaper != null) {
            Either<Integer, Map<String, Integer>> response = servicesNewspapers.getNbrArticles(newspaper.getId());
            if (response.isRight()) {
                observableNbrArticlesByType.setAll(getListOfNbrArticlesByTypeFromMap(response.get()));
            } else {
                state.setValue(new NewspaperListState(errorManager.getErrorMessage(response.getLeft())));
            }
        } else {
            state.setValue(new NewspaperListState("No newspaper selected"));
        }
    }

    private List<NbrArticlesByType> getListOfNbrArticlesByTypeFromMap(Map<String, Integer> stringIntegerMap) {
        Iterator<Map.Entry<String, Integer>> iterator = stringIntegerMap.entrySet().iterator();
        List<NbrArticlesByType> nbrArticlesByTypes = new ArrayList<>();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            nbrArticlesByTypes.add(new NbrArticlesByType(entry.getKey(), entry.getValue()));
        }
        return nbrArticlesByTypes;
    }
}
