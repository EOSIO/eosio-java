package one.block.eosiojava.error.session;

import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception while attempting to call GetAvailableKey() inside CreateSignatureRequest of TransactionProcessor
 */
public class TransactionCreateSignatureRequestKeyError extends TransactionCreateSignatureRequestError {

    public TransactionCreateSignatureRequestKeyError() {
    }

    public TransactionCreateSignatureRequestKeyError(@NotNull String message) {
        super(message);
    }

    public TransactionCreateSignatureRequestKeyError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public TransactionCreateSignatureRequestKeyError(@NotNull Exception exception) {
        super(exception);
    }
}
