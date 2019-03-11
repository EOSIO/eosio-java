package one.block.eosiojava;

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
    public void errorDescription() {
        EosioError err = new EosioError(EosioErrorCode.parsingError, "Parsing Error!");
        String description = err.errorDescription();
        assertEquals("parsingError: Parsing Error!", description);

        EosioError err2 = new EosioError(EosioErrorCode.parsingError, "Parsing Error!", "Parsing JSON Response", true);
        String description2 = err2.errorDescription();
        assertEquals("parsingError: Parsing Error!: context: Parsing JSON Response", description2);
    }

    @Test
    public void description() {
        EosioError err = new EosioError(EosioErrorCode.parsingError, "Parsing Error!");
        String description = err.description();
        assertEquals("Parsing Error!", description);

        EosioError err2 = new EosioError(EosioErrorCode.parsingError, "Parsing Error!", "Parsing JSON Response", true);
        String description2 = err2.description();
        assertEquals("Parsing Error!", description2);

        Error origError = new IOError(new IOException("File Not Found"));
        EosioError err3 = new EosioError(EosioErrorCode.resourceRetrievalError,
                "Could not find resource.",
                "Attempting to open cached resource.",
                origError,
                true);
        assertEquals("Could not find resource.", err3.description());
    }

    @Test
    public void asJsonString() {
        String jsonResult = "{\n" +
                "  \"errorType\": \"EosioError\",\n" +
                "  \"errorInfo\": {\n" +
                "    \"errorCode\": \"parsingError\",\n" +
                "    \"reason\": \"Parsing Error!\",\n" +
                "    \"contextualInfo\": \"Parsing JSON Response\"\n" +
                "  }\n" +
                "}";
        EosioError err2 = new EosioError(EosioErrorCode.parsingError, "Parsing Error!", "Parsing JSON Response", true);
        String errJsonString = err2.asJsonString();
        assertEquals(jsonResult, errJsonString);
    }

    @Test
    public void asEosioError() {
        EosioError err = EosioError.asEosioError(new IOException("File Not Found"));
        assertEquals(EosioErrorCode.unexpectedError, err.errorCode);
        assertEquals("unexpectedError: File Not Found", err.errorDescription());
        assertEquals("File Not Found", err.description());

        EosioError err2 = EosioError.asEosioError(new EosioError(EosioErrorCode.parsingError, "Parsing Error!", "Parsing JSON Response", true));
        assertEquals(EosioErrorCode.parsingError, err2.errorCode);
        assertEquals("parsingError: Parsing Error!: context: Parsing JSON Response", err2.errorDescription());
        assertEquals("Parsing Error!", err2.description());
    }
}