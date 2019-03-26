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

package one.block.eosiojava.error;
//
// EosioError
// eosio-java
//
// Created by mccoole on 2019-03-08
// Copyright © 2018-2019 block.one.
//

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

public class EosioError extends Exception {

    /**
     * Create an EosioError with a null message and original exception.
     */
    public EosioError() {
        super();
    }

    /**
     * Construct an EosioError with the given message.
     *
     * @param message - Message text for the exception.
     */
    public EosioError(@NotNull String message) {
        super(message);
    }

    /**
     * Construct an EosioError with the given message and original exception.
     *
     * @param message - Message text for the exception.
     * @param exception - Original root exception for the error.
     */
    public EosioError(@NotNull String message, @NotNull Exception exception) {
        super(message, exception);
    }

    /**
     * Construct an EosioError with the given original exception.
     *
     * @param exception - Original root exception for the error.
     */
    public EosioError(@NotNull Exception exception) {
        super(exception);
    }

    /**
     * @return A JSON formatted string describing the error code, and reason.
     */
    @NotNull
    public String asJsonString() {
            JsonObject errInfo = new JsonObject();
            errInfo.addProperty("errorCode", this.getClass().getSimpleName());
            errInfo.addProperty("reason", this.getLocalizedMessage());
            JsonObject err = new JsonObject();
            err.addProperty("errorType", "EosioError");
            err.add("errorInfo", errInfo);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonString = gson.toJson(err);
            return jsonString;
    }

}

