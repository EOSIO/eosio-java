package one.block.eosiojava.error.serializationProvider;

import one.block.eosiojava.interfaces.ISerializationProvider;
import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception while attempting to call {@link ISerializationProvider#deserializeContextFreeData(String)}
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
