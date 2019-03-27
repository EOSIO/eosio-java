package one.block.eosiojava.error.serializationprovider;

import org.jetbrains.annotations.NotNull;

public class SerializeTransactionError extends SerializationProviderError {

    public SerializeTransactionError() {
    }

    public SerializeTransactionError(@NotNull String message) {
        super(message);
    }

    public SerializeTransactionError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public SerializeTransactionError(@NotNull Exception exception) {
        super(exception);
    }
}
