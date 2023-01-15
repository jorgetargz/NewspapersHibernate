package dao;

import domain.modelo.Newspaper;
import io.vavr.control.Either;

import java.util.List;

public interface NewspapersDao {
    Either<String, List<Newspaper>> getAll();

    Either<String, Newspaper> get(int id);

    Either<String, Newspaper> save(Newspaper newspaper);

    Either<String, Newspaper> update(Newspaper newspaper);

    Either<String, Newspaper> delete(Newspaper newspaper);
}
