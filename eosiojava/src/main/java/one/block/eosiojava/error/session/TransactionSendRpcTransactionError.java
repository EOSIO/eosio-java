package one.block.eosiojava.error.session;

import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception while attempting to call sendTransaction() of TransactionProcessor
 */
public class TransactionSendRpcTransactionError extends TransactionProcessorError {

    public TransactionSendRpcTransactionError() {
    }

    public TransactionSendRpcTransactionError(@NotNull String message) {
        super(message);
    }

    public TransactionSendRpcTransactionError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public TransactionSendRpcTransactionError(@NotNull Exception exception) {
        super(exception);
    }
}
