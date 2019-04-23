package one.block.eosiojava.error.rpcProvider;

import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception while attempting to use the RPC call, getBlock().
 */
public class GetBlockRpcError extends RpcProviderError{

    public GetBlockRpcError() {
    }

    public GetBlockRpcError(@NotNull String message) {
        super(message);
    }

    public GetBlockRpcError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public GetBlockRpcError(@NotNull Exception exception) {
        super(exception);
    }
}
