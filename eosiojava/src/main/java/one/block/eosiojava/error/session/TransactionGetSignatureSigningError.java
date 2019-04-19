package one.block.eosiojava.error.session;

import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception while attempting to call GetSignature() of TransactionProcessor
 */
public class TransactionGetSignatureSigningError extends TransactionGetSignatureError {

    public TransactionGetSignatureSigningError() {
    }

    public TransactionGetSignatureSigningError(@NotNull String message) {
        super(message);
    }

    public TransactionGetSignatureSigningError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public TransactionGetSignatureSigningError(@NotNull Exception exception) {
        super(exception);
    }
}
