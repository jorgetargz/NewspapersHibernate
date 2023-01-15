package gui.screens.readers_list;


import domain.modelo.ArticleType;
import domain.modelo.Reader;
import domain.services.ServicesArticleTypes;
import domain.services.ServicesReaders;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ReadersListViewModel {

    private final ServicesReaders servicesReaders;
    private final ServicesArticleTypes servicesArticleTypes;
    private final ObjectProperty<ReadersListState> state;
    private final ObservableList<Reader> observableReaders;
    private final ObservableList<ArticleType> observableArticleTypes;


    @Inject
    public ReadersListViewModel(ServicesReaders servicesReaders, ServicesArticleTypes servicesArticleTypes) {
        this.servicesReaders = servicesReaders;
        this.servicesArticleTypes = servicesArticleTypes;
        state = new SimpleObjectProperty<>(new ReadersListState(null));
        observableReaders = FXCollections.observableArrayList();
        observableArticleTypes = FXCollections.observableArrayList();
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

    public void loadReaders() {
        observableReaders.clear();
        observableReaders.setAll(servicesReaders.getAll());
    }

    public void loadArticleTypes() {
        Either<String, List<ArticleType>> result = servicesArticleTypes.scGetAll();
        if (result.isRight()) {
            observableArticleTypes.clear();
            observableArticleTypes.setAll(result.get());
        } else {
            state.setValue(new ReadersListState(result.getLeft()));
        }

    }

    public void cleanState() {
        state.set(new ReadersListState(null));
    }

    public void filterByArticleType(ArticleType articleType) {
        observableReaders.clear();
        observableReaders.setAll(servicesReaders.scGetAllByArticleType(articleType));
    }
}
