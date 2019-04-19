/*
 * Copyright (c) 2017-2019 block.one all rights reserved.
 */

package one.block.eosiojava.error.utilities;

import org.jetbrains.annotations.NotNull;

/**
 * Error class is used when there is an exception while attempting to convert DER/Raw R and S signature to EOS format signature but the signature is not canonical.
 * <br>
 *     * This exception only and randomly happen with signature signed with SECP256K1 curve key
 * <br>
 *     * The flow need to resign the signature to pass this exception.
 */
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
