package one.block.eosiojava.error.session;

import org.jetbrains.annotations.NotNull;

/**
 * Error would come out of TransactionProcessor#CreateSignatureRequest#GetAvailableKey
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
