package one.block.eosiojava.error.serializationProvider;

import org.jetbrains.annotations.NotNull;

/**
 *  Error class is used when there is an exception while attempting to call deserializeTransaction()
 *  of Serialization Provider
 */
public class DeserializeTransactionError extends SerializationProviderError {

    public DeserializeTransactionError() {
    }

    public DeserializeTransactionError(@NotNull String message) {
        super(message);
    }

    public DeserializeTransactionError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public DeserializeTransactionError(@NotNull Exception exception) {
        super(exception);
    }
}
