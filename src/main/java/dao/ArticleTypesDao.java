package dao;

import domain.modelo.ArticleType;
import io.vavr.control.Either;

import java.util.List;

public interface ArticleTypesDao {
    Either<Integer, List<ArticleType>> getAll();

    Either<Integer, ArticleType> getMostRead();
}
