package one.block.eosiojava.error.rpcProvider;

import org.jetbrains.annotations.NotNull;

/**
 * Class of Error/Exception for GetBlock RPC call
 */
public class GetBlockError extends RpcProviderError{

    public GetBlockError() {
    }

    public GetBlockError(@NotNull String message) {
        super(message);
    }

    public GetBlockError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public GetBlockError(@NotNull Exception exception) {
        super(exception);
    }
}
