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

    /**
     * Create and initialize a ByteFormatter from a Base64 encoded string.  The Base64 string
     * will have its padding checked and adjusted if necessary.
     *
     * @param base64String - Base64 encoded string.
     * @return - Initialized ByteFormatter
     */
    public static ByteFormatter createFromBase64(@NotNull String base64String) {
        // Base64 encoded strings must be an even multiple of 4 if they are handled with padding.
        // The strings that we get back from the blockchain in the JSON do not follow this
        // strictly so we have to adjust the string if necessary before decoding.  The padding
        // character is '='.  So we remove all existing padding characters and then pad the
        // string to the nearest multiple of 4.
        String trimmed = CharMatcher.is(BASE64_PADDING_CHAR).removeFrom(base64String);
        String padded = Strings.padEnd(trimmed,
                (trimmed.length() + BASE64_PADDING - 1) / BASE64_PADDING * BASE64_PADDING,
                BASE64_PADDING_CHAR);
        return new ByteFormatter(Base64.decode(padded));
    }

    /**
     * Create and initialize a ByteFormatter from a hex encoded string.
     *
     * @param hexString - Hex encoded string.
     * @return - Initialized ByteFormatter
     */
    public static ByteFormatter createFromHex(@NotNull String hexString) {
        byte[] data = Hex.decode(hexString);
        return new ByteFormatter(data);
    }

    /**
     * Convert the current ByteFormatter contents to a Hex encoded string and return it.
     * @return - Hex encoded string representation of the current formatter context.
     */
    public String toHex() {
        return Hex.toHexString(this.context);
    }

    /**
     * Calculate the sha256 hash of the current ByteFormatter context and return it as a new
     * ByteFormatter.
     *
     * @return - New ByteFormatter containing the sha256 hash of the current one.
     */
    public ByteFormatter sha256() {
        return new ByteFormatter(Sha256Hash.hash(this.context));
    }
}
