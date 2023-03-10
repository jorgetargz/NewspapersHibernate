package gui.screens.welcome;

import domain.modelo.Reader;
import gui.screens.common.BaseScreenController;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class WelcomeScreenController extends BaseScreenController {

    @FXML
    private Label lbBienvenido;


    private void animarPantalla() {
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(lbBienvenido);
        translate.setDuration(Duration.millis(1000));
        translate.setCycleCount(1);
        translate.setInterpolator(Interpolator.LINEAR);
        translate.setFromX(getPrincipalController().getWidth());
        translate.setToX(lbBienvenido.getLayoutX());
        translate.play();
    }

    @Override
    public void principalCargado() {
        Reader reader = getPrincipalController().getReader();
        String welcome = "Welcome to Newspapers DB \n" + reader.getName();
        lbBienvenido.setText(welcome);
        animarPantalla();
    }

}
