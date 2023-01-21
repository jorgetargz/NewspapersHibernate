package dao;

import domain.modelo.Article;
import domain.modelo.Newspaper;
import domain.modelo.Reader;
import io.vavr.control.Either;

import java.util.List;

public interface ArticlesDao {
    Either<Integer, List<Article>> getAll();

    Either<Integer, List<Article>> getAll(Reader reader);

    Either<Integer, Article> save(Article article);

    Either<Integer, Article> update(Article article);

    Either<Integer, Boolean> delete(Article article);

    Either<Integer, Boolean> deleteAll(Newspaper newspaper);
}
