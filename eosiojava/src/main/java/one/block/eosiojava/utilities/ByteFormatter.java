/*
 * Copyright (c) 2017-2019 block.one all rights reserved.
 */

package one.block.eosiojava.utilities;

import com.google.common.base.CharMatcher;
import com.google.common.base.Strings;
import org.bitcoinj.core.Sha256Hash;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;
import org.jetbrains.annotations.NotNull;

/**
 * This class provides methods for transforming and formatting byte data to and from different
 * formats in use on the blockchain.
 */
public class ByteFormatter {

    private static final int BASE64_PADDING = 4;
    private static final char BASE64_PADDING_CHAR = '=';

    @NotNull
    private byte[] context;

    public ByteFormatter(@NotNull byte[] context) {
        this.context = context;
    }

    public static ByteFormatter createFromBase64(@NotNull String base64String) {
        String trimmed = CharMatcher.is(BASE64_PADDING_CHAR).removeFrom(base64String);
        String padded = Strings.padEnd(trimmed,
                (trimmed.length() + BASE64_PADDING - 1) / BASE64_PADDING * BASE64_PADDING,
                BASE64_PADDING_CHAR);
        return new ByteFormatter(Base64.decode(padded));
    }

    public static ByteFormatter createFromHex(@NotNull String hexString) {
        byte[] data = Hex.decode(hexString);
        return new ByteFormatter(data);
    }

    public String toHex() {
        return Hex.toHexString(this.context);
    }

    public ByteFormatter sha256() {
        return new ByteFormatter(Sha256Hash.hash(this.context));
    }
}
