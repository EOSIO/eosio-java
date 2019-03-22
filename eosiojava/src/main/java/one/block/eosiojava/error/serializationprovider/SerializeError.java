package one.block.eosiojava.error.serializationprovider;
//
// SerializeError
// eosio-java
//
// Created by mccoole on 2019-03-21
// Copyright © 2018-2019 block.one.
//

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
