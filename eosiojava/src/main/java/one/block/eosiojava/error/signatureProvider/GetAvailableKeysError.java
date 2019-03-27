package one.block.eosiojava.error.signatureProvider;

import org.jetbrains.annotations.NotNull;

/**
 * Error class which could be thrown from SignatureProvider#GetAvailableKeys
 */
public class GetAvailableKeysError extends SignatureProviderError {

    public GetAvailableKeysError() {
    }

    public GetAvailableKeysError(@NotNull String message) {
        super(message);
    }

    public GetAvailableKeysError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public GetAvailableKeysError(@NotNull Exception exception) {
        super(exception);
    }
}
