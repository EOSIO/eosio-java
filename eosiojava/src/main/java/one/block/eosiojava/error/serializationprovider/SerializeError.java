package one.block.eosiojava.error.serializationprovider;

import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception while attempting to call serialize of Serialization Provider
 */
public class SerializeError extends SerializationProviderError {

    public SerializeError() {
    }

    public SerializeError(@NotNull String message) {
        super(message);
    }

    public SerializeError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public SerializeError(@NotNull Exception exception) {
        super(exception);
    }
}
