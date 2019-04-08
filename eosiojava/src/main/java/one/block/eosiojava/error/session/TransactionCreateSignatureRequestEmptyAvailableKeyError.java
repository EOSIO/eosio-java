package one.block.eosiojava.error.session;

import org.jetbrains.annotations.NotNull;

/**
 * Error would come out of TransactionProcessor#CreateSignatureRequest#GetAvailableKey
 */
public class TransactionCreateSignatureRequestEmptyAvailableKeyError extends TransactionCreateSignatureRequestError {

    public TransactionCreateSignatureRequestEmptyAvailableKeyError() {
    }

    public TransactionCreateSignatureRequestEmptyAvailableKeyError(@NotNull String message) {
        super(message);
    }

    public TransactionCreateSignatureRequestEmptyAvailableKeyError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public TransactionCreateSignatureRequestEmptyAvailableKeyError(@NotNull Exception exception) {
        super(exception);
    }
}
