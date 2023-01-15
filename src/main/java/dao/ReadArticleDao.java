package dao;

import domain.modelo.Readarticle;
import domain.modelo.Reader;
import io.vavr.control.Either;

import java.util.List;

public interface ReadArticleDao {

    Either<String, List<Readarticle>> getAll();

    Either<String, List<Readarticle>> getAll(Reader reader);

    Either<String, Readarticle> save(Readarticle readarticle);

    Either<String, Readarticle> update(Readarticle readarticle);

    Either<String, Boolean> delete(Readarticle readarticle);

    Either<String, Boolean> deleteAll(Reader reader);
}
