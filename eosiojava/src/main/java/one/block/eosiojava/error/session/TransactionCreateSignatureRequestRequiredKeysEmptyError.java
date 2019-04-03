package one.block.eosiojava.error.session;

import org.jetbrains.annotations.NotNull;

public class TransactionCreateSignatureRequestRequiredKeysEmptyError extends TransactionCreateSignatureRequestError {

    public TransactionCreateSignatureRequestRequiredKeysEmptyError() {
    }

    public TransactionCreateSignatureRequestRequiredKeysEmptyError(@NotNull String message) {
        super(message);
    }

    public TransactionCreateSignatureRequestRequiredKeysEmptyError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public TransactionCreateSignatureRequestRequiredKeysEmptyError(@NotNull Exception exception) {
        super(exception);
    }
}
