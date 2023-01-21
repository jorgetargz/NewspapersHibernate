package gui.screens.main;


import common.Constantes;
import domain.modelo.Reader;
import gui.screens.common.BaseScreenController;
import gui.screens.common.ScreenConstants;
import gui.screens.common.Screens;
import io.github.palexdev.materialfx.font.MFXFontIcon;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

@Log4j2
public class MainController {

    final Instance<Object> instance;
    private final Alert alert;
    @FXML
    private Menu menuNewspapers;
    @FXML
    private Menu menuArticles;
    @FXML
    private MenuItem menuItemListArticles;
    @FXML
    private MenuItem menuItemAddArticles;
    @FXML
    private MenuItem menuItemUpdateArticles;
    @FXML
    private MenuItem menuItemDeleteArticles;
    @FXML
    private Menu menuReaders;

    private Stage primaryStage;
    private double xOffset;
    private double yOffset;
    @FXML
    private BorderPane root;
    @FXML
    private HBox windowHeader;
    @FXML
    private MFXFontIcon closeIcon;
    @FXML
    private MFXFontIcon minimizeIcon;
    @FXML
    private MFXFontIcon alwaysOnTopIcon;
    @FXML
    private MenuBar menuPrincipal;

    private Reader reader;

    @Inject
    public MainController(Instance<Object> instance) {
        this.instance = instance;
        alert = new Alert(Alert.AlertType.NONE);
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
        if (reader != null) {
            if (reader.getLogin().getRole().equals(Constantes.ROLE_READER)) {
                menuNewspapers.setVisible(false);
                menuArticles.setVisible(true);
                menuItemListArticles.setVisible(true);
                menuItemAddArticles.setVisible(false);
                menuItemUpdateArticles.setVisible(false);
                menuItemDeleteArticles.setVisible(false);
                menuReaders.setVisible(false);
            } else if (reader.getLogin().getRole().equals(Constantes.ROLE_ADMIN)) {
                menuNewspapers.setVisible(true);
                menuArticles.setVisible(true);
                menuItemListArticles.setVisible(true);
                menuItemAddArticles.setVisible(true);
                menuItemUpdateArticles.setVisible(true);
                menuItemDeleteArticles.setVisible(true);
                menuReaders.setVisible(true);
            }
        }
    }

    public void setStage(Stage stage) {
        primaryStage = stage;
    }

    public void initialize() {
        closeIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> Platform.exit());
        minimizeIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> ((Stage) root.getScene().getWindow()).setIconified(true));
        alwaysOnTopIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            boolean newVal = !primaryStage.isAlwaysOnTop();
            alwaysOnTopIcon.pseudoClassStateChanged(PseudoClass
                    .getPseudoClass(ScreenConstants.ALWAYS_ON_TOP), newVal);
            primaryStage.setAlwaysOnTop(newVal);
        });

        windowHeader.setOnMousePressed(event -> {
            xOffset = primaryStage.getX() - event.getScreenX();
            yOffset = primaryStage.getY() - event.getScreenY();
        });
        windowHeader.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() + xOffset);
            primaryStage.setY(event.getScreenY() + yOffset);
        });
        menuPrincipal.setVisible(false);
        cargarPantalla(Screens.LOGIN);
    }

    public double getWidth() {
        return root.getScene().getWindow().getWidth();
    }

    public void showAlert(Alert.AlertType alertType, String titulo, String mensaje) {
        alert.setAlertType(alertType);
        alert.setTitle(titulo);
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void cargarPantalla(Screens pantalla) {
        Pane panePantalla;
        ResourceBundle r = ResourceBundle.getBundle(ScreenConstants.I_18_N_TEXTS_UI, Locale.ENGLISH);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(controller -> instance.select(controller).get());
            fxmlLoader.setResources(r);
            panePantalla = fxmlLoader.load(getClass().getResourceAsStream(pantalla.getPath()));
            BaseScreenController pantallaController = fxmlLoader.getController();
            pantallaController.setPrincipalController(this);
            pantallaController.principalCargado();
            root.setCenter(panePantalla);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @FXML
    private void menuOnClick(ActionEvent actionEvent) {
        switch (((MenuItem) actionEvent.getSource()).getId()) {
            case ScreenConstants.MENU_ITEM_PANTALLA_INICIO -> cargarPantalla(Screens.WELCOME);
            case ScreenConstants.MENU_ITEM_LIST_NEWSPAPERS -> cargarPantalla(Screens.NEWSPAPER_LIST);
            case ScreenConstants.MENU_ITEM_ADD_NEWSPAPER -> cargarPantalla(Screens.NEWSPAPER_ADD);
            case ScreenConstants.MENU_ITEM_UPDATE_NEWSPAPER -> cargarPantalla(Screens.NEWSPAPER_UPDATE);
            case ScreenConstants.MENU_ITEM_DELETE_NEWSPAPERS -> cargarPantalla(Screens.NEWSPAPER_DELETE);
            case ScreenConstants.MENU_ITEM_LIST_READERS -> cargarPantalla(Screens.READER_LIST);
            case ScreenConstants.MENU_ITEM_DELETE_READERS -> cargarPantalla(Screens.READER_DELETE);
            case ScreenConstants.MENU_ITEM_ADD_READERS -> cargarPantalla(Screens.READERS_ADD);
            case ScreenConstants.MENU_ITEM_UPDATE_READERS -> cargarPantalla(Screens.READERS_UPDATE);
            case ScreenConstants.MENU_ITEM_LIST_ARTICLES -> cargarPantalla(Screens.ARTICLE_LIST);
            case ScreenConstants.MENU_ITEM_ADD_ARTICLES -> cargarPantalla(Screens.ARTICLE_ADD);
            case ScreenConstants.MENU_ITEM_UPDATE_ARTICLES -> cargarPantalla(Screens.ARTICLES_UPDATE);
            case ScreenConstants.MENU_ITEM_DELETE_ARTICLES -> cargarPantalla(Screens.ARTICLE_DELETE);
            default -> cargarPantalla(Screens.LOGIN);
        }
    }

    @FXML
    private void cambiarcss() {
        if (primaryStage.getScene().getRoot().getStylesheets().stream().findFirst().isEmpty()
                || (primaryStage.getScene().getRoot().getStylesheets().stream().findFirst().isPresent()
                && primaryStage.getScene().getRoot().getStylesheets().stream().findFirst().get().contains(ScreenConstants.STYLE))) {
            try {
                primaryStage.getScene().getRoot().getStylesheets().clear();
                primaryStage.getScene().getRoot().getStylesheets().add(getClass().getResource(ScreenConstants.CSS_DARKMODE_CSS).toExternalForm());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        } else {
            try {
                primaryStage.getScene().getRoot().getStylesheets().clear();
                primaryStage.getScene().getRoot().getStylesheets().add(getClass().getResource(ScreenConstants.CSS_STYLE_CSS).toExternalForm());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    @FXML
    private void acercaDe() {
        showAlert(Alert.AlertType.INFORMATION, ScreenConstants.ABOUT, ScreenConstants.AUTHOR_DATA);
    }

    @FXML
    private void logout() {
        menuPrincipal.setVisible(false);
        cargarPantalla(Screens.LOGIN);
    }

    @FXML
    private void exit() {
        Platform.exit();
    }

    //events launched on other screens
    public void onLoginHecho() {
        menuPrincipal.setVisible(true);
        cargarPantalla(Screens.WELCOME);
    }


}
