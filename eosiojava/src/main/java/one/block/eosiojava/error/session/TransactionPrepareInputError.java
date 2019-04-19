package one.block.eosiojava.error.session;

import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception while attempting to call Prepare() inside TransactionProcessor
 * <br>
 *     Get thrown if input for prepare() is invalid
 */
public class TransactionPrepareInputError extends TransactionPrepareError {

    public TransactionPrepareInputError() {
    }

    public TransactionPrepareInputError(@NotNull String message) {
        super(message);
    }

    public TransactionPrepareInputError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public TransactionPrepareInputError(@NotNull Exception exception) {
        super(exception);
    }
}
