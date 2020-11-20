package one.block.eosiojava.error.session;

import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception while attempting to call sendTransaction() of TransactionProcessor
 */
public class TransactionSendTransactionError extends TransactionProcessorError {

    public TransactionSendTransactionError() {
    }

    public TransactionSendTransactionError(@NotNull String message) {
        super(message);
    }

    public TransactionSendTransactionError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public TransactionSendTransactionError(@NotNull Exception exception) {
        super(exception);
    }
}
