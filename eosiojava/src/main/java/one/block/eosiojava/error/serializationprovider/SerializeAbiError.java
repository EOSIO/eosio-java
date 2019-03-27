package one.block.eosiojava.error.serializationprovider;

import org.jetbrains.annotations.NotNull;

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
