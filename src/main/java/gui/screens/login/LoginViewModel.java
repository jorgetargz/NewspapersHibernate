package gui.screens.login;

import domain.modelo.Login;
import domain.services.ServicesLogin;
import gui.screens.common.ErrorManager;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class LoginViewModel {

    private final ServicesLogin servicesLogin;
    private final ErrorManager errorManager;
    private final ObjectProperty<LoginState> state;

    @Inject
    public LoginViewModel(ServicesLogin servicesLogin, ErrorManager errorManager) {
        this.servicesLogin = servicesLogin;
        this.errorManager = errorManager;
        state = new SimpleObjectProperty<>(new LoginState(null, null));
    }

    public ReadOnlyObjectProperty<LoginState> getState() {
        return state;
    }

    public void doLogin(String username, String password) {
        Either<Integer, Login> response = servicesLogin.scLogin(username, password);
        if (response.isRight()) {
            state.setValue(new LoginState(null, response.get().getReader()));
        } else {
            state.setValue(new LoginState(errorManager.getErrorMessage(response.getLeft()), null));
        }
    }

    public void clenState() {
        state.setValue(new LoginState(null, null));
    }
}
