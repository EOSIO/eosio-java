package one.block.eosiojava.error.session;

import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception while attempting to call broadCast() of TransactionProcessor
 */
public class TransactionBroadCastError extends TransactionProcessorError {

    public TransactionBroadCastError() {
    }

    public TransactionBroadCastError(@NotNull String message) {
        super(message);
    }

    public TransactionBroadCastError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public TransactionBroadCastError(@NotNull Exception exception) {
        super(exception);
    }
}
