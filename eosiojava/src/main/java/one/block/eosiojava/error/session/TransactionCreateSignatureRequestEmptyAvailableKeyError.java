package one.block.eosiojava.error.session;

import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception while attempting to call getAvailableKeys()
 * inside createSignatureRequest() of TransactionProcessor.
 * <br>
 *     Gets thrown when the result of GetAvailableKeys() is empty.
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
