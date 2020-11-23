package one.block.eosiojava.error.serializationProvider;

import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception while attempting to call serializeTransaction() of
 * Serialization Provider
 */
public class SerializePackedTransactionError extends SerializationProviderError {

    public SerializePackedTransactionError() {
    }

    public SerializePackedTransactionError(@NotNull String message) {
        super(message);
    }

    public SerializePackedTransactionError(@NotNull String message,
                                           @NotNull Exception exception) {
        super(message, exception);
    }

    public SerializePackedTransactionError(@NotNull Exception exception) {
        super(exception);
    }
}
