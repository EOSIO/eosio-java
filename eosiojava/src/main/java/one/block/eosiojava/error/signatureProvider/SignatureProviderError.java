package one.block.eosiojava.error.signatureProvider;

import one.block.eosiojava.error.EosioError;
import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception while attempting to call any method of SignatureProvider
 */
public class SignatureProviderError extends EosioError {

    public SignatureProviderError() {
    }

    public SignatureProviderError(@NotNull String message) {
        super(message);
    }

    public SignatureProviderError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public SignatureProviderError(@NotNull Exception exception) {
        super(exception);
    }
}
