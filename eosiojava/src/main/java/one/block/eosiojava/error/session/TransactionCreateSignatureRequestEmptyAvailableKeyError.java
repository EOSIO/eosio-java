package one.block.eosiojava.error.session;

import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception while attempting to call GetAvailableKey() inside CreateSignatureRequest() of TransactionProcessor
 * <br>
 *     Get thrown when result of GetAvailableKey() is empty
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
