package gui.screens.readers_list;


import domain.modelo.ArticleType;
import domain.modelo.Newspaper;
import domain.modelo.Reader;
import domain.services.ServicesArticleTypes;
import domain.services.ServicesNewspapers;
import domain.services.ServicesReadarticles;
import domain.services.ServicesReaders;
import gui.screens.common.ErrorManager;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ReadersListViewModel {

    private final ServicesReaders servicesReaders;
    private final ServicesNewspapers servicesNewspapers;
    private final ServicesArticleTypes servicesArticleTypes;
    private final ServicesReadarticles servicesReadarticles;
    private final ErrorManager errorManager;
    private final ObjectProperty<ReadersListState> state;
    private final ObservableList<Reader> observableReaders;
    private final ObservableList<ArticleType> observableArticleTypes;
    private final ObservableList<Newspaper> observableNewspapers;
    private final ObservableList<AvgRating> observableAvgRatings;


    @Inject
    public ReadersListViewModel(ServicesReaders servicesReaders, ServicesNewspapers servicesNewspapers, ServicesArticleTypes servicesArticleTypes, ServicesReadarticles servicesReadarticles, ErrorManager errorManager) {
        this.servicesReaders = servicesReaders;
        this.servicesNewspapers = servicesNewspapers;
        this.servicesArticleTypes = servicesArticleTypes;
        this.servicesReadarticles = servicesReadarticles;
        this.errorManager = errorManager;
        state = new SimpleObjectProperty<>(new ReadersListState(null));
        observableReaders = FXCollections.observableArrayList();
        observableArticleTypes = FXCollections.observableArrayList();
        observableNewspapers = FXCollections.observableArrayList();
        observableAvgRatings = FXCollections.observableArrayList();
    }

    public ObjectProperty<ReadersListState> getState() {
        return state;
    }

    public ObservableList<Reader> getObservableReaders() {
        return FXCollections.unmodifiableObservableList(observableReaders);
    }

    public ObservableList<ArticleType> getObservableArticleTypes() {
        return FXCollections.unmodifiableObservableList(observableArticleTypes);
    }

    public ObservableList<Newspaper> getObservableNewspapers() {
        return FXCollections.unmodifiableObservableList(observableNewspapers);
    }

    public ObservableList<AvgRating> getObservableAvgRatings() {
        return FXCollections.unmodifiableObservableList(observableAvgRatings);
    }


    public void loadReaders() {
        Either<Integer, List<Reader>> response = servicesReaders.scGetAll();
        if (response.isRight()) {
            observableReaders.setAll(response.get());
        } else {
            state.setValue(new ReadersListState(errorManager.getErrorMessage(response.getLeft())));
        }
    }

    public void loadArticleTypes() {
        Either<Integer, List<ArticleType>> response = servicesArticleTypes.scGetAll();
        if (response.isRight()) {
            observableArticleTypes.setAll(response.get());
        } else {
            state.setValue(new ReadersListState(errorManager.getErrorMessage(response.getLeft())));
        }
    }

    public void loadNewspapers() {
        Either<Integer, List<Newspaper>> response = servicesNewspapers.scGetAll();
        if (response.isRight()) {
            observableNewspapers.setAll(response.get());
        } else {
            state.setValue(new ReadersListState(errorManager.getErrorMessage(response.getLeft())));
        }
    }

    public void filterByArticleType(ArticleType articleType) {
        Either<Integer, List<Reader>> response = servicesReaders.scGetAllByArticleType(articleType);
        if (response.isRight()) {
            observableReaders.setAll(response.get());
        } else {
            state.setValue(new ReadersListState(errorManager.getErrorMessage(response.getLeft())));
        }
    }

    public void filterByNewspaper(Newspaper newspaper) {
        Either<Integer, List<Reader>> response = servicesReaders.scGetAllByNewspaper(newspaper);
        if (response.isRight()) {
            observableReaders.setAll(response.get());
        } else {
            state.setValue(new ReadersListState(errorManager.getErrorMessage(response.getLeft())));
        }
    }

    public void cleanState() {
        state.set(new ReadersListState(null));
    }

    public void updateAvgRatingsMap(Reader reader) {
        Either<Integer, Map<Double, String>> response = servicesReadarticles.scGetAvgRating(reader.getId());
        if (response.isRight()) {
            observableAvgRatings.setAll(getAvgRatingsListFromMap(response.get()));
        } else {
            state.setValue(new ReadersListState(errorManager.getErrorMessage(response.getLeft())));
        }
    }

    private List<AvgRating> getAvgRatingsListFromMap(Map<Double, String> doubleStringMap) {
        Iterator<Map.Entry<Double, String>> iterator = doubleStringMap.entrySet().iterator();
        List<AvgRating> avgRatings = new ArrayList<>();
        while (iterator.hasNext()) {
            Map.Entry<Double, String> entry = iterator.next();
            avgRatings.add(new AvgRating(entry.getValue(), entry.getKey()));
        }
        return avgRatings;
    }
}
