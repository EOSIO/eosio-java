package one.block.eosiojava.error.rpcProvider;

import org.jetbrains.annotations.NotNull;

/**
 * Class of Error/Exception for GetBlock RPC call
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
