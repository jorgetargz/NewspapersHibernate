package gui.screens.common;

public enum Screens {

    WELCOME(ScreenConstants.FXML_WELCOME_SCREEN_FXML),
    LOGIN(ScreenConstants.FXML_LOGIN_SCREEN_FXML),
    NEWSPAPER_LIST(ScreenConstants.FXML_NEWSPAPER_LIST_SCREEN_FXML),
    NEWSPAPER_ADD(ScreenConstants.FXML_NEWSPAPER_ADD_SCREEN_FXML),
    NEWSPAPER_UPDATE(ScreenConstants.FXML_NEWSPAPER_UPDATE_SCREEN_FXML),
    NEWSPAPER_DELETE(ScreenConstants.FXML_NEWSPAPER_DELETE_SCREEN_FXML),
    READER_LIST(ScreenConstants.FXML_READER_LIST_SCREEN_FXML),
    READER_DELETE(ScreenConstants.FXML_READER_DELETE_SCREEN_FXML),
    READERS_UPDATE(ScreenConstants.FXML_READERS_UPDATE_SCREEN_FXML),
    READERS_ADD(ScreenConstants.FXML_READERS_ADD_SCREEN_FXML),
    ARTICLE_LIST(ScreenConstants.FXML_ARTICLE_LIST_SCREEN_FXML),
    ARTICLE_ADD(ScreenConstants.FXML_ARTICLE_ADD_SCREEN_FXML),
    ARTICLE_DELETE(ScreenConstants.FXML_ARTICLE_DELETE_SCREEN_FXML);

    private final String path;

    Screens(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }


}
