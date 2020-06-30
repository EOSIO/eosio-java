package one.block.eosiojava.error.session;

import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception while attempting to call broadCast() of TransactionProcessor
 */
public class TransactionFormatPushTransactionResponseError extends TransactionProcessorError {

    public TransactionFormatPushTransactionResponseError() {
    }

    public TransactionFormatPushTransactionResponseError(@NotNull String message) {
        super(message);
    }

    public TransactionFormatPushTransactionResponseError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public TransactionFormatPushTransactionResponseError(@NotNull Exception exception) {
        super(exception);
    }
}
