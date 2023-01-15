package domain.services;

import domain.modelo.Reader;
import domain.modelo.Subscribe;
import io.vavr.control.Either;

import java.util.List;

public interface ServicesSubscriptions {
    Either<String, List<Subscribe>> scGetAllActiveByReader(Reader reader);
}
