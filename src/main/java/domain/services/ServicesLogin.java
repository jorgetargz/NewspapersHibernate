package domain.services;

import domain.modelo.Login;
import domain.modelo.Reader;

public interface ServicesLogin {

    Login scLogin(String username, String password);

    Login scRegister(Login login);

    void scDelete(Login login);
}
