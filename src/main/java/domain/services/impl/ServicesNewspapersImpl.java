package domain.services.impl;

import dao.NewspapersDao;
import domain.modelo.Newspaper;
import domain.services.ServicesNewspapers;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class ServicesNewspapersImpl implements ServicesNewspapers {

    private final NewspapersDao daoNewspapers;

    @Inject
    public ServicesNewspapersImpl(NewspapersDao daoNewspapers) {
        this.daoNewspapers = daoNewspapers;
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

}
