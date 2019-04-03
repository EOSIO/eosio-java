package one.block.eosiojava.error.session;

import org.jetbrains.annotations.NotNull;

public class TransactionGetSignatureError extends TransactionProcessorError {

    public TransactionGetSignatureError() {
    }

    public TransactionGetSignatureError(@NotNull String message) {
        super(message);
    }

    public TransactionGetSignatureError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public TransactionGetSignatureError(@NotNull Exception exception) {
        super(exception);
    }
}
