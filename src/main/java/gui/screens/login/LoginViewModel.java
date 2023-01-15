package gui.screens.login;

import domain.modelo.Login;
import domain.modelo.Reader;
import domain.services.ServicesLogin;
import gui.screens.common.ScreenConstants;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class LoginViewModel {

    private final ServicesLogin servicesLogin;
    private final ObjectProperty<LoginState> state;

    @Inject
    public LoginViewModel(ServicesLogin servicesLogin) {
        this.servicesLogin = servicesLogin;
        state = new SimpleObjectProperty<>(new LoginState(null, null));
    }

    public ReadOnlyObjectProperty<LoginState> getState() {
        return state;
    }

    public void doLogin(String username, String password) {
        Login login = servicesLogin.scLogin(username, password);
        if (login != null) {
            state.setValue(new LoginState(login.getReader(), null));
        } else {
            state.setValue(new LoginState(null, ScreenConstants.INVALID_CREDENTIALS));
        }
    }

    public void clenState() {
        state.setValue(new LoginState(null, null));
    }
}
