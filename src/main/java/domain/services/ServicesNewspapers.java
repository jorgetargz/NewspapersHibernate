package domain.services;

import domain.modelo.Newspaper;
import io.vavr.control.Either;

import java.util.List;

public interface ServicesNewspapers {


    Either<Integer, List<Newspaper>> getNewspapers();

    Either<Integer, Newspaper> get(int id);

    Either<Integer, Newspaper> saveNewspaper(Newspaper newspaper);

    Either<Integer, Newspaper> updateNewspaper(Newspaper newspaper);

    Either<Integer, Boolean> deleteNewspaper(Newspaper newspaper);
}
