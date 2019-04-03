package one.block.eosiojava.error.session;

import org.jetbrains.annotations.NotNull;

public class TransactionPushTransactionError extends TransactionProcessorError {

    public TransactionPushTransactionError() {
    }

    public TransactionPushTransactionError(@NotNull String message) {
        super(message);
    }

    public TransactionPushTransactionError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public TransactionPushTransactionError(@NotNull Exception exception) {
        super(exception);
    }
}
