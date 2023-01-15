package domain.services;

import domain.modelo.Article;
import io.vavr.control.Either;

import java.util.List;

public interface ServicesArticles {
    Either<String, List<Article>> scGetAll();

    Either<String, Article> scSave(Article article);

    Either<String, Article> scUpdate(Article article);

    Either<String, Boolean> scDelete(Article article);
}
