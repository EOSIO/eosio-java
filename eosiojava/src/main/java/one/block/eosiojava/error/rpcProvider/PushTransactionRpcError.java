package one.block.eosiojava.error.rpcProvider;

import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception while attempting to use the RPC call,
 * pushTransaction().
 */
public class PushTransactionRpcError extends RpcProviderError {

    public PushTransactionRpcError() {
    }

    public PushTransactionRpcError(@NotNull String message) {
        super(message);
    }

    public PushTransactionRpcError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public PushTransactionRpcError(@NotNull Exception exception) {
        super(exception);
    }
}
