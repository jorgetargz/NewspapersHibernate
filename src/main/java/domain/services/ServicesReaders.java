package domain.services;

import domain.modelo.ArticleType;
import domain.modelo.Newspaper;
import domain.modelo.Reader;
import io.vavr.control.Either;

import java.util.List;

public interface ServicesReaders {

    Either<Integer, List<Reader>> scGetAll();

    Either<Integer, List<Reader>> scGetAllByArticleType(ArticleType value);

    Either<Integer, List<Reader>> scGetAllByNewspaper(Newspaper newspaper);

}
