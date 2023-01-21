package domain.services;

import domain.modelo.Login;
import io.vavr.control.Either;

public interface ServicesLogin {

    Either<Integer, Login> scLogin(String username, String password);

    Either<Integer, Login> scRegister(Login login);

    Either<Integer, Login> scUpdate(Login login, String password);

    Either<Integer, Boolean> scDelete(Login login);

    Either<Integer, Boolean> scDeleteWithActiveSubscriptions(Login login);
}
