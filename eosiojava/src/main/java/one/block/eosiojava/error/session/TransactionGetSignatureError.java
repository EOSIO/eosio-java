package one.block.eosiojava.error.session;

import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception while attempting to call getSignature() of TransactionProcessor
 */
public class TransactionGetSignatureError extends TransactionProcessorError {

    public TransactionGetSignatureError() {
    }

    public TransactionGetSignatureError(@NotNull String message) {
        super(message);
    }

    public TransactionGetSignatureError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public TransactionGetSignatureError(@NotNull Exception exception) {
        super(exception);
    }
}
