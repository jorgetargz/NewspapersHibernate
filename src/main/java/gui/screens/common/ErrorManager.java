package gui.screens.common;
import common.Constantes;

public class ErrorManager {

    public String getErrorMessage(Integer errorCode) {
        return switch (errorCode) {
            case Constantes.DB_ERROR_CODE -> "DB Error";
            case Constantes.DB_NOT_FOUND_CODE -> "Not Found";
            case Constantes.DB_CONSTRAINT_VIOLATION_CODE -> "DB Constraint Error";
            case Constantes.BAD_CREDENTIALS_ERROR_CODE -> "Invalid Credentials";
            case Constantes.READER_HAS_ACTIVE_SUBSCRIPTIONS_ERROR_CODE -> "Reader has active subscriptions";
            default -> "Unknown Error";
        };
    }
}
