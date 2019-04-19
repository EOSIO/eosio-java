package one.block.eosiojava.error.rpcProvider;

import one.block.eosiojava.error.EosioError;
import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception while attempting to call any RPC call
 * <br>
 *     Any class which is used for RPC Provider must extend this Error class
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
