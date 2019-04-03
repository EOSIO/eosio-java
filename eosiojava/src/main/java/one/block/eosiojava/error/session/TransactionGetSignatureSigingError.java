package one.block.eosiojava.error.session;

import org.jetbrains.annotations.NotNull;

public class TransactionGetSignatureSigingError extends TransactionGetSignatureError {

    public TransactionGetSignatureSigingError() {
    }

    public TransactionGetSignatureSigingError(@NotNull String message) {
        super(message);
    }

    public TransactionGetSignatureSigingError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public TransactionGetSignatureSigingError(@NotNull Exception exception) {
        super(exception);
    }
}
