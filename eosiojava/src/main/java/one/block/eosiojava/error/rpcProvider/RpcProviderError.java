package one.block.eosiojava.error.rpcProvider;

import one.block.eosiojava.error.EosioError;
import org.jetbrains.annotations.NotNull;

/**
 * Class of Error/Exception for all RPC error/exception
 */
public class RpcProviderError extends EosioError {

    public RpcProviderError() {
    }

    public RpcProviderError(@NotNull String message) {
        super(message);
    }

    public RpcProviderError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public RpcProviderError(@NotNull Exception exception) {
        super(exception);
    }
}
