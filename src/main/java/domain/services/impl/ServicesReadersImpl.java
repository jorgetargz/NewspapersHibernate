package domain.services.impl;


import dao.ReadersDao;
import domain.modelo.ArticleType;
import domain.modelo.Newspaper;
import domain.modelo.Reader;
import domain.services.ServicesReaders;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class ServicesReadersImpl implements ServicesReaders {

    private final ReadersDao daoReaders;

    @Inject
    public ServicesReadersImpl(ReadersDao daoReaders) {
        this.daoReaders = daoReaders;
    }


    @Override
    public Either<Integer, List<Reader>> scGetAll() {
        return daoReaders.getAll();
    }

    @Override
    public Either<Integer, List<Reader>> scGetAllByArticleType(ArticleType articleType) {
        return daoReaders.getAll(articleType);
    }

    @Override
    public Either<Integer, List<Reader>> scGetAllByNewspaper(Newspaper newspaper) {
        return daoReaders.getAll(newspaper);
    }

    @Override
    public Either<Integer, Reader> scSave(Reader reader) {
        return daoReaders.save(reader);
    }

    @Override
    public Either<Integer, Reader> scUpdate(Reader reader, String password) {
        if (password != null) {
            reader.getLogin().setPassword(password);
        }
        return daoReaders.update(reader);
    }

    @Override
    public Either<Integer, Boolean> scDelete(Reader reader) {
        return daoReaders.delete(reader);
    }
}