package one.block.eosiojava.interfaces;
//
// ISerializationProvider
// eosio-java
//
// Created by mccoole on 2019-03-20
// Copyright © 2018-2019 block.one.
//

import one.block.eosiojava.EosioError;
import one.block.eosiojava.models.AbiEosSerializationObject;

public interface ISerializationProvider {

    public void deserialize(AbiEosSerializationObject serializationObject) throws EosioError;
    public void serialize(AbiEosSerializationObject serializationObject) throws EosioError;

    public String deserializeTransaction(String hex) throws EosioError;
    public String serializeTransaction(String json) throws EosioError;

    public String deserializeAbi(String hex) throws EosioError;
    public String serializeAbi(String json) throws EosioError;
}
