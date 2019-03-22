package one.block.eosiojava.error.serializationprovider;
//
// DeserializeAbiError
// eosio-java
//
// Created by mccoole on 3/22/19
// Copyright © 2018-2019 block.one.
//

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
