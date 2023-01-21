package dao;

import domain.modelo.Readarticle;
import io.vavr.control.Either;

import java.util.Map;

public interface ReadArticleDao {

    Either<Integer, Readarticle> save(Readarticle readarticle);

    Either<Integer, Readarticle> update(Readarticle readarticle);

    Either<Integer, Map<Double, String>> getAvgRating(int idReader);

}
