package domain.services;

import domain.modelo.ArticleType;
import domain.modelo.Newspaper;
import domain.modelo.Reader;

import java.util.List;

public interface ServicesReaders {

    List<Reader> getAll();

    List<Reader> scGetAllByArticleType(ArticleType value);

    List<Reader> scGetAllByNewspaper(Newspaper newspaper);

    Reader get(int id);

    Reader save(Reader reader);

    Reader update(Reader reader, String password);

    void delete(Reader reader);
}
