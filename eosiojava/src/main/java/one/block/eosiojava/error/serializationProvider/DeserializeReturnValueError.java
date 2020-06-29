package one.block.eosiojava.error.serializationProvider;

import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception while attempting to call deserializeAbi() of
 * Serialization Provider
 */
public class DeserializeReturnValueError extends SerializationProviderError {

    public DeserializeReturnValueError() {
    }

    public DeserializeReturnValueError(@NotNull String message) {
        super(message);
    }

    public DeserializeReturnValueError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public DeserializeReturnValueError(@NotNull Exception exception) {
        super(exception);
    }
}
