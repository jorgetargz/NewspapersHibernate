package dao;

import domain.modelo.ArticleType;
import domain.modelo.Newspaper;
import domain.modelo.Reader;
import io.vavr.control.Either;

import java.util.List;

public interface ReadersDao {
    Either<Integer, List<Reader>> getAll();

    Either<Integer, List<Reader>> getAll(ArticleType articleType);

    Either<Integer, List<Reader>> getAll(Newspaper newspaper);

    Either<Integer, Reader> save(Reader reader);

    Either<Integer, Reader> update(Reader reader);

    Either<Integer, Boolean> delete(Reader reader);
}
