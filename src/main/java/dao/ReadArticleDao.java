package dao;

import domain.modelo.Readarticle;
import domain.modelo.Reader;
import io.vavr.control.Either;

import java.util.List;

public interface ReadArticleDao {

    Either<String, List<Readarticle>> getAll();

    Readarticle save(Readarticle readarticle);

    Readarticle update(Readarticle readarticle);

    Readarticle delete(Readarticle readarticle);

    void deleteAll(Reader reader);
}
