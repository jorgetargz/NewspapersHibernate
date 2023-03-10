package gui.screens.readers_delete;

import domain.modelo.Reader;
import lombok.Data;

@Data
public class ReadersDeleteState {
    private final String error;
    private final Reader readerWithActiveSubscriptions;
}
