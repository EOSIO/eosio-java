package one.block.eosiojava.error.abiProvider;

import one.block.eosiojava.error.EosioError;
import org.jetbrains.annotations.NotNull;

/**
 * Class of Error/Exception could be thrown from AbiProvider
 */
public class AbiProviderError extends EosioError {

    public AbiProviderError() {
    }

    public AbiProviderError(@NotNull String message) {
        super(message);
    }

    public AbiProviderError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public AbiProviderError(@NotNull Exception exception) {
        super(exception);
    }
}
