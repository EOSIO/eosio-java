package one.block.eosiojava;
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
import org.jetbrains.annotations.Nullable;

public class EosioError extends Exception {

    public @NotNull EosioErrorCode errorCode = EosioErrorCode.unexpectedError;
    public @NotNull String reason = "";
    public @NotNull String context = "";
    public @Nullable Error originalError = null;
    public boolean isReturnable = true;

    /**
     * Create an EosioError to be returned to a consuming method with a default context and returnable flag.
     *
     * @param errorCode - Enumerated EosioErrorCode for the exception that occured.
     * @param reason - Human readable reason for the error's occurance.
     */
    public EosioError(@NotNull EosioErrorCode errorCode, @NotNull String reason) {
        this(errorCode, reason, "", null, true);
    }

    /**
     * Create an EosioError to be returned to a consuming method.
     *
     * @param errorCode - Enumerated EosioErrorCode for the exception that occured.
     * @param reason - Human readable reason for the error's occurance.
     * @param context - Human readable string describing the process context in which the error occured.
     * @param isReturnable - Flag indicating whether the EosioError should be returned to a calling client application.
     */
    public EosioError(@NotNull EosioErrorCode errorCode, @NotNull String reason, @NotNull String context, boolean isReturnable) {
        this(errorCode, reason, context, null, isReturnable);
    }

    /**
     * Create an EosioError to be returned to a consuming method that includes an original system error as its
     * root cause.
     *
     * @param errorCode - Enumerated EosioErrorCode for the exception that occured.
     * @param reason - Human readable reason for the error's occurance.
     * @param context - Human readable string describing the process context in which the error occured.
     * @param originalError - The original root error.
     * @param isReturnable - Flag indicating whether the EosioError should be returned to a calling client application.
     */
    public EosioError(@NotNull EosioErrorCode errorCode,
                      @NotNull String reason,
                      @NotNull String context,
                      @Nullable Error originalError,
                      boolean isReturnable) {
        super(reason, originalError);
        this.errorCode = errorCode;
        this.reason = reason;
        this.context = context;
        this.originalError = originalError;
        this.isReturnable = isReturnable;
    }

    /**
     * @return A formatted string describing the error code, reason and context (if available)
     */
    @NotNull
    public String errorDescription() {
        if(this.context.isEmpty()) {
            return String.format("%s: %s", this.errorCode.toString(), this.reason);
        } else {
            return String.format("%s: %s: context: %s", this.errorCode.toString(), reason, context);
        }
    }

    /**
     * @return A string describing the reason for the error in the default localized language.
     */
    @NotNull
    public String description() {
        return this.getLocalizedMessage();
    }


    /**
     * @return A JSON formatted string describing the error code, reason and contextual information.
     */
    @NotNull
    public String asJsonString() {
            JsonObject errInfo = new JsonObject();
            errInfo.addProperty("errorCode", this.errorCode.toString());
            errInfo.addProperty("reason", this.reason);
            errInfo.addProperty("contextualInfo", this.context);
            JsonObject err = new JsonObject();
            err.addProperty("errorType", "EosioError");
            err.add("errorInfo", errInfo);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonString = gson.toJson(err);
            return jsonString;
    }

    /**
     * Create an EosioError out of any exception.  By default the EosioErrorCode will be set to unexpectedError and the
     * reason will be set to the original exception's getLocalizedMessage() return string.  If the exception is already
     * an EosioError then it will be returned as-is.
     *
     * @param ex - The original exception to wrap if it is not already an EosioError.
     * @return - The original EosioError or a new unexpectedError EosioError.
     */
    @NotNull
    public static EosioError asEosioError(@NotNull Exception ex) {
        return (ex instanceof EosioError) ? (EosioError) ex : new EosioError(EosioErrorCode.unexpectedError, ex.getLocalizedMessage());
    }
}

