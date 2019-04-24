package one.block.eosiojava.error.serializationProvider;

import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception while attempting to call serializeAbi()
 * of Serialization Provider
 */
public class SerializeAbiError extends SerializationProviderError {

    public SerializeAbiError() {
    }

    public SerializeAbiError(@NotNull String message) {
        super(message);
    }

    public SerializeAbiError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public SerializeAbiError(@NotNull Exception exception) {
        super(exception);
    }
}
