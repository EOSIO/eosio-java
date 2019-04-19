package one.block.eosiojava.error.session;

import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception while attempting to call any RPC call inside CreateSignatureRequest() of TransactionProcessor
 */
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
