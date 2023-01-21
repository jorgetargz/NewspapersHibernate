package domain.services;

import domain.modelo.Newspaper;
import io.vavr.control.Either;

import java.util.List;
import java.util.Map;

public interface ServicesNewspapers {


    Either<Integer, List<Newspaper>> scGetAll();

    Either<Integer, Newspaper> scGet(int id);

    Either<Integer, Newspaper> scSave(Newspaper newspaper);

    Either<Integer, Newspaper> scUpdate(Newspaper newspaper);

    Either<Integer, Boolean> scDelete(Newspaper newspaper);

    Either<Integer, Boolean> scDeleteArticles(Newspaper newspaper);

    Either<Integer, Map<String, Integer>> scGetNbrArticlesByType(int idNewspaper);
}
