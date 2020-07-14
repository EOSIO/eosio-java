package one.block.eosiojava.error.rpcProvider;

import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception while attempting to use the RPC call,
 * sendTransaction().
 */
public class SendTransactionRpcError extends RpcProviderError {

    public SendTransactionRpcError() {
    }

    public SendTransactionRpcError(@NotNull String message) {
        super(message);
    }

    public SendTransactionRpcError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public SendTransactionRpcError(@NotNull Exception exception) {
        super(exception);
    }
}
