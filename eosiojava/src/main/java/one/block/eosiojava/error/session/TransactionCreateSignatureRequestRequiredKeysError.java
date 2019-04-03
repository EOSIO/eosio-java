package one.block.eosiojava.error.session;

import org.jetbrains.annotations.NotNull;

public class TransactionCreateSignatureRequestRequiredKeysError extends TransactionCreateSignatureRequestError {

    public TransactionCreateSignatureRequestRequiredKeysError() {
    }

    public TransactionCreateSignatureRequestRequiredKeysError(@NotNull String message) {
        super(message);
    }

    public TransactionCreateSignatureRequestRequiredKeysError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public TransactionCreateSignatureRequestRequiredKeysError(@NotNull Exception exception) {
        super(exception);
    }
}
