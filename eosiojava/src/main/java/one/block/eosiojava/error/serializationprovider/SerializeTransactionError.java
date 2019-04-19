package one.block.eosiojava.error.serializationprovider;

import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception while attempting to call SerializeTransaction of Serialization Provider
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
