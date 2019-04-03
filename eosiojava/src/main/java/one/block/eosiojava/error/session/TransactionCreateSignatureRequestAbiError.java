package one.block.eosiojava.error.session;

import org.jetbrains.annotations.NotNull;

public class TransactionCreateSignatureRequestAbiError extends TransactionCreateSignatureRequestError{

    public TransactionCreateSignatureRequestAbiError() {
    }

    public TransactionCreateSignatureRequestAbiError(@NotNull String message) {
        super(message);
    }

    public TransactionCreateSignatureRequestAbiError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public TransactionCreateSignatureRequestAbiError(@NotNull Exception exception) {
        super(exception);
    }
}
