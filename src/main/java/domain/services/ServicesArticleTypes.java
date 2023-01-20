package domain.services;

import domain.modelo.ArticleType;
import io.vavr.control.Either;

import java.util.List;

public interface ServicesArticleTypes {
    Either<Integer, List<ArticleType>> scGetAll();
}
