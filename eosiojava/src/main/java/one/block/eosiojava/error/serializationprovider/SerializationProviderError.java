package one.block.eosiojava.error.serializationprovider;

import one.block.eosiojava.error.EosioError;
import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception while attempting to call any method of Serialization Provider
 * <br>
 *     Any class which is used for Serialization Provider must extend this Error class
 */
public class SerializationProviderError extends EosioError {

    public SerializationProviderError() {
    }

    public SerializationProviderError(@NotNull String message) {
        super(message);
    }

    public SerializationProviderError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public SerializationProviderError(@NotNull Exception exception) {
        super(exception);
    }
}
