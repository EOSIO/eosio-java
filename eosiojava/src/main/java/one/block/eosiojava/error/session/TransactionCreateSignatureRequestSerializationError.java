package one.block.eosiojava.error.session;

import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception while attempting to call Serialization method
 * inside createSignatureRequest() of TransactionProcessor
 */
public class TransactionCreateSignatureRequestSerializationError extends TransactionCreateSignatureRequestError {

    public TransactionCreateSignatureRequestSerializationError() {
    }

    public TransactionCreateSignatureRequestSerializationError(@NotNull String message) {
        super(message);
    }

    public TransactionCreateSignatureRequestSerializationError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public TransactionCreateSignatureRequestSerializationError(@NotNull Exception exception) {
        super(exception);
    }
}
