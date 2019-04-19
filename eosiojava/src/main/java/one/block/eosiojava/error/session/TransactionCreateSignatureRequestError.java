package one.block.eosiojava.error.session;

import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception while attempting to call any method relate to signing process inside getSignature() of TransactionProcessor
 */
public class TransactionCreateSignatureRequestError extends TransactionProcessorError {

    public TransactionCreateSignatureRequestError() {
    }

    public TransactionCreateSignatureRequestError(@NotNull String message) {
        super(message);
    }

    public TransactionCreateSignatureRequestError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public TransactionCreateSignatureRequestError(@NotNull Exception exception) {
        super(exception);
    }
}
