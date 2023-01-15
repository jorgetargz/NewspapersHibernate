package dao;

import domain.modelo.Article;
import io.vavr.control.Either;

import java.util.List;

public interface ArticlesDao {
    Either<String, List<Article>> getAll();

    Either<String, Article> save(Article article);

    Either<String, Article> update(Article article);

    Either<String, Boolean> delete(Article article);
}
