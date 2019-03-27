

package one.block.eosiojava.error.serializationprovider;

import org.jetbrains.annotations.NotNull;

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
