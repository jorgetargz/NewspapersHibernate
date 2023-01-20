package dao;

import domain.modelo.Newspaper;
import io.vavr.control.Either;

import java.util.List;

public interface NewspapersDao {
    Either<Integer, List<Newspaper>> getAll();

    Either<Integer, Newspaper> get(int id);

    Either<Integer, Newspaper> save(Newspaper newspaper);

    Either<Integer, Newspaper> update(Newspaper newspaper);

    Either<Integer, Boolean> delete(Newspaper newspaper);
}
