package dao;

import domain.modelo.Login;
import io.vavr.control.Either;

public interface LoginDao {

    Either<Integer, Login> get(String username);

    Either<Integer, Login> save(Login login);

    Either<Integer, Boolean> delete(Login login);
}
