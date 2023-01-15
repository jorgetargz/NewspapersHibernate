package dao;

import domain.modelo.ArticleType;
import domain.modelo.Readarticle;
import io.vavr.control.Either;

import java.util.List;

public interface ArticleTypesDao {
    Either<String, List<ArticleType>> getAll();
}
