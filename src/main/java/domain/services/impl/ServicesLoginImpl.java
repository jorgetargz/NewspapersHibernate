package domain.services.impl;

import common.Constantes;
import dao.LoginDao;
import dao.SubscriptionsDao;
import domain.modelo.Login;
import domain.modelo.Subscribe;
import domain.services.ServicesLogin;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class ServicesLoginImpl implements ServicesLogin {

    private final LoginDao daoLogin;
    private final SubscriptionsDao daoSubscriptions;

    @Inject
    public ServicesLoginImpl(LoginDao daoLogin, SubscriptionsDao daoSubscriptions) {
        this.daoLogin = daoLogin;
        this.daoSubscriptions = daoSubscriptions;
    }

    @Override
    public Either<Integer, Login> scLogin(String username, String password) {
        Either<Integer, Login> result = daoLogin.get(username);
        if (result.isRight() && !result.get().getPassword().equals(password)) {
            result = Either.left(Constantes.BAD_CREDENTIALS_ERROR_CODE);
        }
        return result;
    }

    @Override
    public Either<Integer, Login> scRegister(Login login) {
        login.setRole(Constantes.ROLE_READER);
        return daoLogin.save(login);
    }

    @Override
    public Either<Integer, Login> scUpdate(Login login, String password) {
        if (password != null) {
            login.setPassword(password);
        }
        return daoLogin.update(login);
    }

    @Override
    public Either<Integer, Boolean> scDelete(Login login) {
        Either<Integer, List<Subscribe>> activeSubsResponse = daoSubscriptions.getAll(login.getReader());
        if (activeSubsResponse.isRight() && !activeSubsResponse.get().isEmpty()) {
            return Either.left(Constantes.READER_HAS_ACTIVE_SUBSCRIPTIONS_ERROR_CODE);
        }
        return daoLogin.delete(login);
    }

    @Override
    public Either<Integer, Boolean> scDeleteWithActiveSubscriptions(Login login) {
        return daoLogin.delete(login);
    }

}
