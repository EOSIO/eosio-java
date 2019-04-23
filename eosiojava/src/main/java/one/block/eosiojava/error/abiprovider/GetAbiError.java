package one.block.eosiojava.error.abiprovider;

import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception while attempting to call the GetAbi or GetAbis methods
 * of IABIProvider {@link one.block.eosiojava.interfaces.IABIProvider}.
 */
public class GetAbiError extends AbiProviderError {

    public GetAbiError() {
    }

    public GetAbiError(@NotNull String message) {
        super(message);
    }

    public GetAbiError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public GetAbiError(@NotNull Exception exception) {
        super(exception);
    }
}
