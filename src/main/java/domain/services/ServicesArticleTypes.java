package domain.services;

import domain.modelo.ArticleType;
import domain.modelo.Readarticle;
import io.vavr.control.Either;

import java.util.List;

public interface ServicesArticleTypes {
    Either<String, List<ArticleType>> scGetAll();
}
