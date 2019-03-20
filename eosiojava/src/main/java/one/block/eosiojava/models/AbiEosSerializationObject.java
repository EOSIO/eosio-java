package one.block.eosiojava.models;
//
// AbiEosSerializationObject
// eosio-java
//
// Created by mccoole on 2019-03-20
// Copyright © 2018-2019 block.one.
//

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AbiEosSerializationObject {

    public @Nullable String contract = null;
    public @NotNull String name = "";
    public @Nullable String type = "null";
    public @NotNull String hex = "";
    public @NotNull String json = "";
    public @NotNull String abi = "";

    public AbiEosSerializationObject(@Nullable String contract,
            @NotNull String name, @Nullable String type,
            @NotNull String abi) {
        this.contract = contract;
        this.name = name;
        this.type = type;
        this.abi = abi;
    }

}
