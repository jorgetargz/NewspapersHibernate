package domain.services;

import domain.modelo.Readarticle;
import io.vavr.control.Either;

import java.util.Map;

public interface ServicesReadarticles {

    Either<Integer, Readarticle> scSave(Readarticle readarticle);

    Either<Integer, Readarticle> scUpdate(Readarticle readarticle);

    Either<Integer, Map<Double, String>> scGetAvgRating(int idReader);

}
