package gui.screens.common;

import common.Constantes;

public class ErrorManager {

    public String getErrorMessage(Integer errorCode) {
        return switch (errorCode) {
            case Constantes.DB_ERROR_CODE -> "Database Error";
            case Constantes.DB_NOT_FOUND_CODE -> "Not Found";
            case Constantes.DB_CONSTRAINT_VIOLATION_CODE -> "Object already exists or is being used by another object";
            case Constantes.BAD_CREDENTIALS_ERROR_CODE -> "Invalid Credentials";
            case Constantes.READER_HAS_ACTIVE_SUBSCRIPTIONS_ERROR_CODE -> "Reader has active subscriptions";
            default -> "Unknown Error";
        };
    }
}
