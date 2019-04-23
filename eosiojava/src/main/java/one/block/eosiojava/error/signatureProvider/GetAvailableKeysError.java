package one.block.eosiojava.error.signatureProvider;

import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception while attempting to call getAvailableKeys() of SignatureProvider
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
