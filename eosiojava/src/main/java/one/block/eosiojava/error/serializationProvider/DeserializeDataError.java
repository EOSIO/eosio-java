package one.block.eosiojava.error.serializationProvider;

import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception while attempting to call deserializeAbi() of
 * Serialization Provider
 */
public class DeserializeDataError extends SerializationProviderError {

    public DeserializeDataError() {
    }

    public DeserializeDataError(@NotNull String message) {
        super(message);
    }

    public DeserializeDataError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public DeserializeDataError(@NotNull Exception exception) {
        super(exception);
    }
}
