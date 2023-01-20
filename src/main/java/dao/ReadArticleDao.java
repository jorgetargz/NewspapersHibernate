package dao;

import domain.modelo.Readarticle;
import io.vavr.control.Either;

public interface ReadArticleDao {

    Either<Integer, Readarticle> save(Readarticle readarticle);

    Either<Integer, Readarticle> update(Readarticle readarticle);

}
