package domain.services.impl;

import dao.ArticlesDao;
import dao.NewspapersDao;
import domain.modelo.Newspaper;
import domain.services.ServicesNewspapers;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Map;

public class ServicesNewspapersImpl implements ServicesNewspapers {

    private final NewspapersDao daoNewspapers;
    private final ArticlesDao daoArticles;

    @Inject
    public ServicesNewspapersImpl(NewspapersDao daoNewspapers, ArticlesDao daoArticles) {
        this.daoNewspapers = daoNewspapers;
        this.daoArticles = daoArticles;
    }

    @Override
    public Either<Integer, List<Newspaper>> getNewspapers() {
        return daoNewspapers.getAll();
    }

    @Override
    public Either<Integer, Newspaper> get(int id) {
        return daoNewspapers.get(id);
    }

    @Override
    public Either<Integer, Newspaper> saveNewspaper(Newspaper newspaper) {
        return daoNewspapers.save(newspaper);
    }

    @Override
    public Either<Integer, Newspaper> updateNewspaper(Newspaper newspaper) {
        return daoNewspapers.update(newspaper);
    }

    @Override
    public Either<Integer, Boolean> deleteNewspaper(Newspaper newspaper) {
        return daoNewspapers.delete(newspaper);
    }

    @Override
    public Either<Integer, Boolean> deleteArticles(Newspaper newspaper) {
        return daoArticles.deleteAll(newspaper);
    }

    @Override
    public Either<Integer, Map<String, Integer>> getNbrArticles(int idNewspaper) {
        return daoNewspapers.getNbrArticles(idNewspaper);
    }

}
