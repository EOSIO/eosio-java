

package one.block.eosiojava.error.serializationprovider;

import org.jetbrains.annotations.NotNull;

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
