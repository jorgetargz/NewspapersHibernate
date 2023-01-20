package domain.services;

import domain.modelo.Readarticle;
import io.vavr.control.Either;

public interface ServicesReadarticles {

    Either<Integer, Readarticle> scSave(Readarticle readarticle);

    Either<Integer, Readarticle> scUpdate(Readarticle readarticle);

}
