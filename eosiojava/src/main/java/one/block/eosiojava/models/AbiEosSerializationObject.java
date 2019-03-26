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

    private @Nullable String contract;
    private @NotNull String name;
    private @Nullable String type;
    private @NotNull String hex = "";
    private @NotNull String json = "";
    private @NotNull String abi;

    @Nullable
    public String getContract() {
        return contract;
    }

    @NotNull
    public String getName() {
        return name;
    }

    @Nullable
    public String getType() {
        return type;
    }

    @NotNull
    public String getHex() {
        return hex;
    }

    @NotNull
    public String getJson() {
        return json;
    }

    @NotNull
    public String getAbi() {
        return abi;
    }

    public void setHex(@NotNull String hex) {
        this.hex = hex;
    }

    public void setJson(@NotNull String json) {
        this.json = json;
    }

    public AbiEosSerializationObject(@Nullable String contract,
            @NotNull String name, @Nullable String type,
            @NotNull String abi) {
        this.contract = contract;
        this.name = name;
        this.type = type;
        this.abi = abi;
    }

}
