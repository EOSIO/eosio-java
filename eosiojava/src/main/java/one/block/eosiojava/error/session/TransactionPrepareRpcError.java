package one.block.eosiojava.error.session;

import org.jetbrains.annotations.NotNull;

public class TransactionPrepareRpcError extends TransactionPrepareError {

    public TransactionPrepareRpcError() {
    }

    public TransactionPrepareRpcError(@NotNull String message) {
        super(message);
    }

    public TransactionPrepareRpcError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public TransactionPrepareRpcError(@NotNull Exception exception) {
        super(exception);
    }
}
