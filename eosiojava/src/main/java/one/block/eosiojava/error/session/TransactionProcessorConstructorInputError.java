package one.block.eosiojava.error.session;

import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception while attempting to initialize TransactionProcessor
 */
public class TransactionProcessorConstructorInputError extends TransactionProcessorError {

    public TransactionProcessorConstructorInputError() {
    }

    public TransactionProcessorConstructorInputError(@NotNull String message) {
        super(message);
    }

    public TransactionProcessorConstructorInputError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public TransactionProcessorConstructorInputError(@NotNull Exception exception) {
        super(exception);
    }
}
