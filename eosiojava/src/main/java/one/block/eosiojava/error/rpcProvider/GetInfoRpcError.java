package one.block.eosiojava.error.rpcProvider;

import org.jetbrains.annotations.NotNull;

/**
 * Class of Error/Exception for GetInfo RPC call
 */
public class GetInfoRpcError extends RpcProviderError{

    public GetInfoRpcError() {
    }

    public GetInfoRpcError(@NotNull String message) {
        super(message);
    }

    public GetInfoRpcError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public GetInfoRpcError(@NotNull Exception exception) {
        super(exception);
    }
}
