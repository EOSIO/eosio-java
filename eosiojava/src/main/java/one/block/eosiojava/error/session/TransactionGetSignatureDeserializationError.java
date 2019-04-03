package one.block.eosiojava.error.session;

import org.jetbrains.annotations.NotNull;

public class TransactionGetSignatureDeserializationError extends TransactionGetSignatureError {

    public TransactionGetSignatureDeserializationError() {
    }

    public TransactionGetSignatureDeserializationError(@NotNull String message) {
        super(message);
    }

    public TransactionGetSignatureDeserializationError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public TransactionGetSignatureDeserializationError(@NotNull Exception exception) {
        super(exception);
    }
}
