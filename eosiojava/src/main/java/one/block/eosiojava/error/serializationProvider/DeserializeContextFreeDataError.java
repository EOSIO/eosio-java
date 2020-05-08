package one.block.eosiojava.error.serializationProvider;

import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception while attempting to call deserializeAbi() of
 * Serialization Provider
 */
public class DeserializeContextFreeDataError extends SerializationProviderError {

    public DeserializeContextFreeDataError() {
    }

    public DeserializeContextFreeDataError(@NotNull String message) {
        super(message);
    }

    public DeserializeContextFreeDataError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public DeserializeContextFreeDataError(@NotNull Exception exception) {
        super(exception);
    }
}
