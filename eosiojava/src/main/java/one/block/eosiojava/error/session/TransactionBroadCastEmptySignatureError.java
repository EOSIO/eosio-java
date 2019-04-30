package one.block.eosiojava.error.session;

import org.jetbrains.annotations.NotNull;

/**
 * Error would be thrown from TransactionProcessor#BroadCast if signatures is empty
 */
public class TransactionBroadCastEmptySignatureError extends TransactionBroadCastError {

    public TransactionBroadCastEmptySignatureError() {
    }

    public TransactionBroadCastEmptySignatureError(@NotNull String message) {
        super(message);
    }

    public TransactionBroadCastEmptySignatureError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public TransactionBroadCastEmptySignatureError(@NotNull Exception exception) {
        super(exception);
    }
}
