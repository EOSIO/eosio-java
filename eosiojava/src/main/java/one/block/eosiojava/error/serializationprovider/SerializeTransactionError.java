package one.block.eosiojava.error.serializationprovider;

//
// SerializeTransactionError
// eosio-java
//
// Created by mccoole on 3/22/19
// Copyright © 2018-2019 block.one.
//

import org.jetbrains.annotations.NotNull;

public class SerializeTransactionError extends SerializationProviderError {

    public SerializeTransactionError() {
    }

    public SerializeTransactionError(@NotNull String message) {
        super(message);
    }

    public SerializeTransactionError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public SerializeTransactionError(@NotNull Exception exception) {
        super(exception);
    }
}
