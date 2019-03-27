

package one.block.eosiojava.error.serializationprovider;

import org.jetbrains.annotations.NotNull;

public class DeserializeError extends SerializationProviderError {

    public DeserializeError() {
    }

    public DeserializeError(@NotNull String message) {
        super(message);
    }

    public DeserializeError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public DeserializeError(@NotNull Exception exception) {
        super(exception);
    }
}
