package one.block.eosiojava.error.session;

import org.jetbrains.annotations.NotNull;

/**
 * Error would come out of TransactionProcessor#SignAndBroadCast
 */
public class TransactionSignAndBroadCastError extends TransactionProcessorError {

    public TransactionSignAndBroadCastError() {
    }

    public TransactionSignAndBroadCastError(@NotNull String message) {
        super(message);
    }

    public TransactionSignAndBroadCastError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public TransactionSignAndBroadCastError(@NotNull Exception exception) {
        super(exception);
    }
}
