

package one.block.eosiojava.error.serializationprovider;

import org.jetbrains.annotations.NotNull;

public class DeserializeTransactionError extends SerializationProviderError {

    public DeserializeTransactionError() {
    }

    public DeserializeTransactionError(@NotNull String message) {
        super(message);
    }

    public DeserializeTransactionError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public DeserializeTransactionError(@NotNull Exception exception) {
        super(exception);
    }
}
