/*
 * Copyright (c) 2017-2019 block.one all rights reserved.
 */

package one.block.eosiojava.error.utilities;

import org.jetbrains.annotations.NotNull;

public class EosFormatterSignatureIsNotCanonicalError extends EOSFormatterError{
    public EosFormatterSignatureIsNotCanonicalError() {
    }

    public EosFormatterSignatureIsNotCanonicalError(@NotNull String message) {
        super(message);
    }

    public EosFormatterSignatureIsNotCanonicalError(@NotNull String message, @NotNull Exception exception) {
        super(message, exception);
    }

    public EosFormatterSignatureIsNotCanonicalError(@NotNull Exception exception) {
        super(exception);
    }
}
