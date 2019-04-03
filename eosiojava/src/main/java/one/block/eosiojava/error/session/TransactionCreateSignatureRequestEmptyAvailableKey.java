package one.block.eosiojava.error.session;

import org.jetbrains.annotations.NotNull;

public class TransactionCreateSignatureRequestEmptyAvailableKey extends TransactionCreateSignatureRequestError {

    public TransactionCreateSignatureRequestEmptyAvailableKey() {
    }

    public TransactionCreateSignatureRequestEmptyAvailableKey(@NotNull String message) {
        super(message);
    }

    public TransactionCreateSignatureRequestEmptyAvailableKey(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public TransactionCreateSignatureRequestEmptyAvailableKey(@NotNull Exception exception) {
        super(exception);
    }
}
