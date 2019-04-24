package one.block.eosiojava.error.serializationProvider;

import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception while attempting to call serializeTransaction() of
 * Serialization Provider
 */
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
