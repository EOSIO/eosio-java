package one.block.eosiojava.error.serializationProvider;

import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception while attempting to call deserializeAbi() of
 * Serialization Provider
 */
public class DeserializeAbiError extends SerializationProviderError {

    public DeserializeAbiError() {
    }

    public DeserializeAbiError(@NotNull String message) {
        super(message);
    }

    public DeserializeAbiError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public DeserializeAbiError(@NotNull Exception exception) {
        super(exception);
    }
}
