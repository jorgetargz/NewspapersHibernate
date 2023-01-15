package domain.services.impl;


import dao.ReadersDao;
import domain.modelo.ArticleType;
import domain.modelo.Reader;
import domain.services.ServicesReaders;
import jakarta.inject.Inject;

import java.util.List;

public class ServicesReadersImpl implements ServicesReaders {

    private final ReadersDao daoReaders;

    @Inject
    public ServicesReadersImpl(ReadersDao daoReaders) {
        this.daoReaders = daoReaders;
    }


    @Override
    public List<Reader> getAll() {
        return daoReaders.getAll();
    }

    @Override
    public List<Reader> scGetAllByArticleType(ArticleType articleType) {
        return daoReaders.getAll(articleType);
    }

    @Override
    public Reader get(int id) {
        return daoReaders.get(id);
    }

    @Override
    public Reader save(Reader reader) {
        return daoReaders.save(reader);
    }

    @Override
    public Reader update(Reader reader, String password) {
        if (password != null) {
            reader.getLogin().setPassword(password);
        }
        return daoReaders.update(reader);
    }

    @Override
    public void delete(Reader reader) {
        daoReaders.delete(reader);
    }
}