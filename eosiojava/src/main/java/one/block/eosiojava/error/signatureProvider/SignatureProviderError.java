package one.block.eosiojava.error.signatureProvider;

import one.block.eosiojava.error.EosioError;
import org.jetbrains.annotations.NotNull;

/**
 * Error class which could be thrown from SignatureProvider
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
