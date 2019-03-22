package one.block.eosiojava.error.serializationprovider;
//
// SerializationProviderError
// eosio-java
//
// Created by mccoole on 2019-03-21
// Copyright © 2018-2019 block.one.
//

import one.block.eosiojava.error.EosioError;
import org.jetbrains.annotations.NotNull;

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
