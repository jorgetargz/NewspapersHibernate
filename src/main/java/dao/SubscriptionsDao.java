package dao;

import domain.modelo.Reader;
import domain.modelo.Subscribe;
import io.vavr.control.Either;

import java.util.List;

public interface SubscriptionsDao {
    Either<Integer, List<Subscribe>> getAll(Reader reader);
}
