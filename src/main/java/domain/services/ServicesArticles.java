package domain.services;

import domain.modelo.Article;
import domain.modelo.Reader;
import io.vavr.control.Either;

import java.util.List;

public interface ServicesArticles {
    Either<Integer, List<Article>> scGetAll();

    Either<Integer, List<Article>> scGetAllByReader(Reader reader);

    Either<Integer, Article> scSave(Article article);

    Either<Integer, Article> scUpdate(Article article);

    Either<Integer, Boolean> scDelete(Article article);
}
