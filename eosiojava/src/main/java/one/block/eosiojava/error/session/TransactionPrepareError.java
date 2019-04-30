package one.block.eosiojava.error.session;

import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception while attempting to call prepare() of TransactionProcessor
 */
public class TransactionPrepareError extends TransactionProcessorError{

    public TransactionPrepareError() {
    }

    public TransactionPrepareError(@NotNull String message) {
        super(message);
    }

    public TransactionPrepareError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public TransactionPrepareError(@NotNull Exception exception) {
        super(exception);
    }
}
