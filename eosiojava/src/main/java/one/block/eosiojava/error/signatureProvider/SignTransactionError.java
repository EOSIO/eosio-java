package one.block.eosiojava.error.signatureProvider;

import org.jetbrains.annotations.NotNull;

/**
 * Error class which could be thrown from SignatureProvider#SignTransaction
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
