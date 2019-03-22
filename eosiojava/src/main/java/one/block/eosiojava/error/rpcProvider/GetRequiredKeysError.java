package one.block.eosiojava.error.rpcProvider;

import org.jetbrains.annotations.NotNull;

/**
 * Class of Error/Exception for GetRequiredKeys RPC call
 */
public class GetRequiredKeysError extends RpcProviderError {

    public GetRequiredKeysError() {
    }

    public GetRequiredKeysError(@NotNull String message) {
        super(message);
    }

    public GetRequiredKeysError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public GetRequiredKeysError(@NotNull Exception exception) {
        super(exception);
    }
}
