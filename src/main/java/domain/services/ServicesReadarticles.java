package domain.services;

import domain.modelo.Readarticle;
import domain.modelo.Reader;
import io.vavr.control.Either;

import java.util.List;

public interface ServicesReadarticles {
    Either<String, List<Readarticle>> scGetAll();

    Either<String, List<Readarticle>> scGetAllByReader(Reader reader);

    Either<String, Readarticle> scSave(Readarticle readarticle);

    Either<String, Readarticle> scUpdate(Readarticle readarticle);

    Either<String, Boolean> scDelete(Readarticle readarticle);
}
