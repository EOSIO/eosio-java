package one.block.eosiojava.error.session;

import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception while attempting to call GetSignature() inside TransactionProcessor
 * <br>
 *     Get thrown when Signature provider modify returned back transaction but TransactionProcessor is not set to allow that
 */
public class TransactionGetSignatureNotAllowModifyTransactionError extends TransactionGetSignatureError {

    public TransactionGetSignatureNotAllowModifyTransactionError() {
    }

    public TransactionGetSignatureNotAllowModifyTransactionError(@NotNull String message) {
        super(message);
    }

    public TransactionGetSignatureNotAllowModifyTransactionError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public TransactionGetSignatureNotAllowModifyTransactionError(@NotNull Exception exception) {
        super(exception);
    }
}
