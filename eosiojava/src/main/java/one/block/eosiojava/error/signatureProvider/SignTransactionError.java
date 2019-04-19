package one.block.eosiojava.error.signatureProvider;

import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception while attempting to call SignTransaction() of SignatureProvider
 */
public class SignTransactionError extends SignatureProviderError {

    public SignTransactionError() {
    }

    public SignTransactionError(@NotNull String message) {
        super(message);
    }

    public SignTransactionError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public SignTransactionError(@NotNull Exception exception) {
        super(exception);
    }
}
