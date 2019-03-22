package one.block.eosiojava.error.serializationprovider;
//
// SerializeAbiError
// eosio-java
//
// Created by mccoole on 3/22/19
// Copyright © 2018-2019 block.one.
//

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
