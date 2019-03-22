package one.block.eosiojava.error.rpcProvider;

import org.jetbrains.annotations.NotNull;

/**
 * Class of Error/Exception for Init method in RpcProvider
 */
public class InitError extends RpcProviderError {

    public InitError() {
    }

    public InitError(@NotNull String message) {
        super(message);
    }

    public InitError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public InitError(@NotNull Exception exception) {
        super(exception);
    }
}
