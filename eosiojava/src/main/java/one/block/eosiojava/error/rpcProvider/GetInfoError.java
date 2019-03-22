package one.block.eosiojava.error.rpcProvider;

import org.jetbrains.annotations.NotNull;

/**
 * Class of Error/Exception for GetInfo RPC call
 */
public class GetInfoError extends RpcProviderError{

    public GetInfoError() {
    }

    public GetInfoError(@NotNull String message) {
        super(message);
    }

    public GetInfoError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public GetInfoError(@NotNull Exception exception) {
        super(exception);
    }
}
