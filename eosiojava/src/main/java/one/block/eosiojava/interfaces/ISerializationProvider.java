/*
 * Copyright (c) 2017-2019 block.one all rights reserved.
 *
 * The MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package one.block.eosiojava.interfaces;
//
// ISerializationProvider
// eosio-java
//
// Created by mccoole on 2019-03-20
// Copyright © 2018-2019 block.one.
//

import one.block.eosiojava.error.serializationprovider.DeserializeAbiError;
import one.block.eosiojava.error.serializationprovider.DeserializeError;
import one.block.eosiojava.error.serializationprovider.DeserializeTransactionError;
import one.block.eosiojava.error.serializationprovider.SerializeAbiError;
import one.block.eosiojava.error.serializationprovider.SerializeError;
import one.block.eosiojava.error.serializationprovider.SerializeTransactionError;
import one.block.eosiojava.models.AbiEosSerializationObject;

public interface ISerializationProvider {

    public void deserialize(AbiEosSerializationObject serializationObject) throws DeserializeError;
    public void serialize(AbiEosSerializationObject serializationObject) throws SerializeError;

    public String deserializeTransaction(String hex) throws DeserializeTransactionError;
    public String serializeTransaction(String json) throws SerializeTransactionError;

    public String deserializeAbi(String hex) throws DeserializeAbiError;
    public String serializeAbi(String json) throws SerializeAbiError;
}
