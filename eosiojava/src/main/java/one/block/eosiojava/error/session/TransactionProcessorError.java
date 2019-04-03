package one.block.eosiojava.error.session;

import one.block.eosiojava.error.EosioError;
import org.jetbrains.annotations.NotNull;

public class TransactionProcessorError extends EosioError {

    public TransactionProcessorError() {
    }

    public TransactionProcessorError(@NotNull String message) {
        super(message);
    }

    public TransactionProcessorError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public TransactionProcessorError(@NotNull Exception exception) {
        super(exception);
    }
}
