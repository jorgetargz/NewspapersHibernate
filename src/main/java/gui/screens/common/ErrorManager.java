package gui.screens.common;

public class ErrorManager {

    public String getErrorMessage(Integer errorCode) {
        return switch (errorCode) {
            case -1 -> "DB Error";
            case -2 -> "Not Found";
            case -3 -> "DB Constraint Error";
            case -4 -> "Invalid Credentials";
            case -5 -> "Reader has active subscriptions";
            default -> "Unknown Error";
        };
    }
}
