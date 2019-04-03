package one.block.eosiojava.error.session;

import org.jetbrains.annotations.NotNull;

public class TransactionSignError extends TransactionProcessorError {

    public TransactionSignError() {
    }

    public TransactionSignError(@NotNull String message) {
        super(message);
    }

    public TransactionSignError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public TransactionSignError(@NotNull Exception exception) {
        super(exception);
    }
}