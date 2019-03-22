package one.block.eosiojava.error.serializationprovider;

//
// DeserializeError
// eosio-java
//
// Created by mccoole on 3/22/19
// Copyright © 2018-2019 block.one.
//

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
