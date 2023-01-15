package domain.services;

import domain.modelo.Newspaper;
import io.vavr.control.Either;

import java.util.List;

public interface ServicesNewspapers {


    Either<String, List<Newspaper>> getNewspapers();

    Either<String, Newspaper> get(int id);

    Either<String, Newspaper> saveNewspaper(Newspaper newspaper);

    Either<String, Newspaper> updateNewspaper(Newspaper newspaper);

    Either<String, Newspaper> deleteNewspaper(Newspaper newspaper);
}
