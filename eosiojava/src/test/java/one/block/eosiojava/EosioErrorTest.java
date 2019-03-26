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

package one.block.eosiojava;

import one.block.eosiojava.error.EosioError;
import one.block.eosiojava.error.serializationprovider.SerializationProviderError;
import org.junit.Test;

import java.io.IOError;
import java.io.IOException;

import static org.junit.Assert.*;

//
// EosioErrorTest
// eosio-java
//
// Created by mccoole on 2019-03-11
// Copyright © 2018-2019 block.one.
//

public class EosioErrorTest {

    @Test
    public void errroMessage() {
        EosioError err = new EosioError("Parsing Error!");
        String description = err.getLocalizedMessage();
        assertEquals("Parsing Error!", description);

        Exception origError = new IOException("File Not Found");
        EosioError err2 = new EosioError(
                "Could not find resource.",
                origError);
        assertEquals("Could not find resource.", err2.getLocalizedMessage());

        assertEquals("File Not Found", err2.getCause().getMessage());
    }

    @Test
    public void asJsonString() {
        String jsonResult = "{\n" +
                "  \"errorType\": \"EosioError\",\n" +
                "  \"errorInfo\": {\n" +
                "    \"errorCode\": \"SerializationProviderError\",\n" +
                "    \"reason\": \"Serialization Provider Failure\"\n" +
                "  }\n" +
                "}";
        SerializationProviderError err2 = new SerializationProviderError("Serialization Provider Failure");
        String errJsonString = err2.asJsonString();
        assertEquals(jsonResult, errJsonString);
    }

}