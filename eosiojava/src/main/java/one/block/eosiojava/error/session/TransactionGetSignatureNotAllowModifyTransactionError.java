package one.block.eosiojava.error.session;

import org.jetbrains.annotations.NotNull;

/**
 * Error would come out of TransactionProcessor#GetSignature
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
