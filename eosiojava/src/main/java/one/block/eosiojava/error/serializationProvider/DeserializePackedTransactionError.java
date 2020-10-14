package one.block.eosiojava.error.serializationProvider;

import org.jetbrains.annotations.NotNull;

/**
 *  Error class is used when there is an exception while attempting to call deserializeTransaction()
 *  of Serialization Provider
 */
public class DeserializePackedTransactionError extends SerializationProviderError {

    public DeserializePackedTransactionError() {
    }

    public DeserializePackedTransactionError(@NotNull String message) {
        super(message);
    }

    public DeserializePackedTransactionError(@NotNull String message,
                                             @NotNull Exception exception) {
        super(message, exception);
    }

    public DeserializePackedTransactionError(@NotNull Exception exception) {
        super(exception);
    }
}
