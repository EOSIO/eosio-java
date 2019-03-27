package one.block.eosiojava.error.abiProvider;

import org.jetbrains.annotations.NotNull;

/**
 * Class of Error/Exception could be thrown from AbiProvider#GetAbi and AbiProvider#GetAbis methods
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
