package domain.services.impl;

import dao.ReadArticleDao;
import domain.modelo.Readarticle;
import domain.modelo.Reader;
import domain.services.ServicesReadarticles;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class ServicesReadarticlesImpl implements ServicesReadarticles {

    private final ReadArticleDao readArticleDao;

    @Inject
    public ServicesReadarticlesImpl(ReadArticleDao readArticleDao) {
        this.readArticleDao = readArticleDao;
    }

    @Override
    public Either<String, List<Readarticle>> scGetAll() {
        return readArticleDao.getAll();
    }

    @Override
    public Either<String, List<Readarticle>> scGetAllByReader(Reader reader) {
        return readArticleDao.getAll(reader);
    }

    @Override
    public Either<String, Readarticle> scSave(Readarticle readarticle) {
        return readArticleDao.save(readarticle);
    }

    @Override
    public Either<String, Readarticle> scUpdate(Readarticle readarticle) {
        return readArticleDao.update(readarticle);
    }

    @Override
    public Either<String, Boolean> scDelete(Readarticle readarticle) {
        return readArticleDao.delete(readarticle);
    }
}
