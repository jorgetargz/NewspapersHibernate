package domain.services.impl;

import dao.ReadArticleDao;
import domain.modelo.Readarticle;
import domain.services.ServicesReadarticles;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.Map;

public class ServicesReadarticlesImpl implements ServicesReadarticles {

    private final ReadArticleDao readArticleDao;

    @Inject
    public ServicesReadarticlesImpl(ReadArticleDao readArticleDao) {
        this.readArticleDao = readArticleDao;
    }

    @Override
    public Either<Integer, Readarticle> scSave(Readarticle readarticle) {
        return readArticleDao.save(readarticle);
    }

    @Override
    public Either<Integer, Readarticle> scUpdate(Readarticle readarticle) {
        return readArticleDao.update(readarticle);
    }

    @Override
    public Either<Integer, Map<Double, String>> scGetAvgRating(int idReader) {
        return readArticleDao.getAvgRating(idReader);
    }

}
