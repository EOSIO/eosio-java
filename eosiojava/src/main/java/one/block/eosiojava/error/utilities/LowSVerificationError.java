/*
 * Copyright (c) 2017-2019 block.one all rights reserved.
 */

package one.block.eosiojava.error.utilities;

import org.jetbrains.annotations.NotNull;

/**
 * Error thrown when exception occurs during signature manipulations.  Specifically, this
 * error indicates that a failure occurred while verifying whether the value of S was low.
 */
public class LowSVerificationError extends EOSFormatterError {

    public LowSVerificationError() {
    }

    public LowSVerificationError(@NotNull String message) {
        super(message);
    }

    public LowSVerificationError(@NotNull String message,
            @NotNull Exception exception) {
        super(message, exception);
    }

    public LowSVerificationError(@NotNull Exception exception) {
        super(exception);
    }
}
