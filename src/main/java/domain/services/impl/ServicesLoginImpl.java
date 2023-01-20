package domain.services.impl;

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
            result = Either.left(-4);
        }
        return result;
    }

    @Override
    public Either<Integer, Login> scRegister(Login login) {
        return daoLogin.save(login);
    }

    @Override
    public Either<Integer, Boolean> scDelete(Login login) {
        Either<Integer, List<Subscribe>> activeSubsResponse = daoSubscriptions.getAll(login.getReader());
        if (activeSubsResponse.isRight() && !activeSubsResponse.get().isEmpty()) {
            return Either.left(-5);
        }
        return daoLogin.delete(login);
    }

    @Override
    public Either<Integer, Boolean> scDeleteWithActiveSubscriptions(Login login) {
        return daoLogin.delete(login);
    }

}
