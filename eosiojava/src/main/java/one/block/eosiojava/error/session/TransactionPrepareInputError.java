package one.block.eosiojava.error.session;

import org.jetbrains.annotations.NotNull;

public class TransactionPrepareInputError extends TransactionPrepareError {

    public TransactionPrepareInputError() {
    }

    public TransactionPrepareInputError(@NotNull String message) {
        super(message);
    }

    public TransactionPrepareInputError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public TransactionPrepareInputError(@NotNull Exception exception) {
        super(exception);
    }
}