

package one.block.eosiojava.error.utilities;

import one.block.eosiojava.error.EosioError;
import org.jetbrains.annotations.NotNull;

public class EOSFormatterError extends EosioError {

    public EOSFormatterError() {
    }

    public EOSFormatterError(@NotNull String message) {
        super(message);
    }

    public EOSFormatterError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public EOSFormatterError(@NotNull Exception exception) {
        super(exception);
    }
}
