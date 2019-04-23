package one.block.eosiojava.error.session;

import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception while attempting to call getRequiredKeys()
 * inside createSignatureRequest() of TransactionProcessor.
 * <br>
 *     Gets thrown if GetRequiredKeys() returns an empty list.
 */
public class TransactionCreateSignatureRequestRequiredKeysEmptyError extends TransactionCreateSignatureRequestError {

    public TransactionCreateSignatureRequestRequiredKeysEmptyError() {
    }

    public TransactionCreateSignatureRequestRequiredKeysEmptyError(@NotNull String message) {
        super(message);
    }

    public TransactionCreateSignatureRequestRequiredKeysEmptyError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public TransactionCreateSignatureRequestRequiredKeysEmptyError(@NotNull Exception exception) {
        super(exception);
    }
}
