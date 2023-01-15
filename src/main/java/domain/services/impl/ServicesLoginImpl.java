package domain.services.impl;

import dao.LoginDao;
import domain.modelo.Login;
import domain.modelo.Reader;
import domain.services.ServicesLogin;
import jakarta.inject.Inject;

public class ServicesLoginImpl implements ServicesLogin {

    private final LoginDao daoLogin;

    @Inject
    public ServicesLoginImpl(LoginDao daoLogin) {
        this.daoLogin = daoLogin;
    }

    @Override
    public Login scLogin(String username, String password) {
        Login login = daoLogin.get(username);
        if (login != null && login.getPassword().equals(password)) {
            return login;
        } else {
            return null;
        }
    }

    @Override
    public Login scRegister(Login login) {
        return daoLogin.save(login);
    }

    @Override
    public void scDelete(Login login) {
        daoLogin.delete(login);
    }

}
