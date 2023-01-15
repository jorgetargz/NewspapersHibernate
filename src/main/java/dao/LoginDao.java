package dao;

import domain.modelo.Login;

public interface LoginDao {

    Login get(String username);

    Login save(Login login);

    void delete(Login login);
}
