package dao;

import domain.modelo.ArticleType;
import domain.modelo.Newspaper;
import domain.modelo.Reader;

import java.util.List;

public interface ReadersDao {
    List<Reader> getAll();

    List<Reader> getAll(ArticleType articleType);

    List<Reader> getAll(Newspaper newspaper);

    Reader get(int id);

    Reader save(Reader reader);

    Reader update(Reader reader);

    void delete(Reader reader);
}
