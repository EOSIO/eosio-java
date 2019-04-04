package one.block.eosiojava.error.rpcProvider;

import org.jetbrains.annotations.NotNull;

/**
 * Class of Error/Exception for PushTransaction RPC call
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
