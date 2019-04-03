package one.block.eosiojava.error.session;

import org.jetbrains.annotations.NotNull;

public class TransactionCreateSignatureRequestRpcError extends TransactionCreateSignatureRequestError {

    public TransactionCreateSignatureRequestRpcError() {
    }

    public TransactionCreateSignatureRequestRpcError(@NotNull String message) {
        super(message);
    }

    public TransactionCreateSignatureRequestRpcError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public TransactionCreateSignatureRequestRpcError(@NotNull Exception exception) {
        super(exception);
    }
}
