package one.block.eosiojava.error.rpcProvider;

import one.block.eosiojava.error.EosioError;
import org.jetbrains.annotations.NotNull;

/**
 * Class of Error/Exception for GetRawAbi RPC call
 */
public class GetRawAbiError extends EosioError {

    public GetRawAbiError() {
    }

    public GetRawAbiError(@NotNull String message) {
        super(message);
    }

    public GetRawAbiError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public GetRawAbiError(@NotNull Exception exception) {
        super(exception);
    }
}
