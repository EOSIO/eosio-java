package one.block.eosiojava.error.rpcProvider;

import org.jetbrains.annotations.NotNull;

/**
 * Class of Error/Exception for GetRequiredKeys RPC call
 */
public class GetRequiredKeysRpcError extends RpcProviderError {

    public GetRequiredKeysRpcError() {
    }

    public GetRequiredKeysRpcError(@NotNull String message) {
        super(message);
    }

    public GetRequiredKeysRpcError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public GetRequiredKeysRpcError(@NotNull Exception exception) {
        super(exception);
    }
}
