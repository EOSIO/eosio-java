package one.block.eosiojava.error.rpcProvider;

import org.jetbrains.annotations.NotNull;

/**
 * Class of Error/Exception for PushTransaction RPC call
 */
public class PushTransactionError extends RpcProviderError {

    public PushTransactionError() {
    }

    public PushTransactionError(@NotNull String message) {
        super(message);
    }

    public PushTransactionError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public PushTransactionError(@NotNull Exception exception) {
        super(exception);
    }
}
