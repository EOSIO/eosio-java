package one.block.eosiojava.error.rpcProvider;

import one.block.eosiojava.error.EosioError;
import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception while attempting to use the RPC call, getRawAbi().
 */
public class GetRawAbiRpcError extends EosioError {

    public GetRawAbiRpcError() {
    }

    public GetRawAbiRpcError(@NotNull String message) {
        super(message);
    }

    public GetRawAbiRpcError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public GetRawAbiRpcError(@NotNull Exception exception) {
        super(exception);
    }
}
