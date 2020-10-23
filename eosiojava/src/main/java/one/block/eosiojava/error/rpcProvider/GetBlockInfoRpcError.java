package one.block.eosiojava.error.rpcProvider;

import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception while attempting to use the RPC call, getBlockInfo().
 */
public class GetBlockInfoRpcError extends RpcProviderError{

    public GetBlockInfoRpcError() {
    }

    public GetBlockInfoRpcError(@NotNull String message) {
        super(message);
    }

    public GetBlockInfoRpcError(@NotNull String message,
                            @NotNull Exception exception) {
        super(message, exception);
    }

    public GetBlockInfoRpcError(@NotNull Exception exception) {
        super(exception);
    }
}
