package dao;

import domain.modelo.ArticleType;
import domain.modelo.Reader;

import java.util.List;

public interface ReadersDao {
    List<Reader> getAll();

    List<Reader> getAll(ArticleType articleType);

    Reader get(int id);

    Reader save(Reader reader);

    Reader update(Reader reader);

    void delete(Reader reader);
}
