package one.block.eosiojava.error.rpcProvider;

import one.block.eosiojava.error.EosioError;
import org.jetbrains.annotations.NotNull;

/**
 * Class of Error/Exception for GetRawAbi RPC call
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
