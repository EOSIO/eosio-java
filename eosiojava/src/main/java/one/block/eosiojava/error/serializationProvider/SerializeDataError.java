package one.block.eosiojava.error.serializationProvider;

import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception while attempting to call deserializeAbi() of
 * Serialization Provider
 */
public class SerializeDataError extends SerializationProviderError {

    public SerializeDataError() {
    }

    public SerializeDataError(@NotNull String message) {
        super(message);
    }

    public SerializeDataError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public SerializeDataError(@NotNull Exception exception) {
        super(exception);
    }
}
