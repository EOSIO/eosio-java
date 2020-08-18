package one.block.eosiojava.error.session;

import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception while attempting to call pushTransaction() of TransactionProcessor
 */
public class TransactionSendAmqpTransactionError extends TransactionProcessorError {

    public TransactionSendAmqpTransactionError() {
    }

    public TransactionSendAmqpTransactionError(@NotNull String message) {
        super(message);
    }

    public TransactionSendAmqpTransactionError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public TransactionSendAmqpTransactionError(@NotNull Exception exception) {
        super(exception);
    }
}
