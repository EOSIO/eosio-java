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

package one.block.eosiojava.utilities;

import com.google.common.primitives.Bytes;
import java.util.Arrays;
import java.util.Base64;
import one.block.eosiojava.enums.AlgorithmEmployed;
import one.block.eosiojava.error.ErrorConstants;
import one.block.eosiojava.error.utilities.EOSFormatterError;
import org.bitcoinj.core.Base58;
import org.bitcoinj.core.Sha256Hash;
import org.bouncycastle.crypto.digests.RIPEMD160Digest;
import org.bouncycastle.util.encoders.Hex;
import org.jetbrains.annotations.NotNull;

public class EOSFormatter {

    //EOS FORMAT PREFIXES
    private static final String PATTERN_STRING_EOS_PREFIX_EOS = "EOS";
    private static final String PATTERN_STRING_EOS_PREFIX_PUB_R1 = "PUB_R1_";
    private static final String PATTERN_STRING_EOS_PREFIX_PUB_K1 = "PUB_K1_";
    private static final String PATTERN_STRING_EOS_PREFIX_PVT_R1 = "PVT_R1_";
    private static final String PATTERN_STRING_EOS_PREFIX_SIG_R1 = "SIG_R1_";
    private static final String PATTERN_STRING_EOS_PREFIX_SIG_K1 = "SIG_K1_";

    //PEM FORMAT PREFIXES
    private static final String PATTERN_STRING_PEM_PREFIX_PRIVATE_KEY_SECP256R1 = "30770201010420";
    private static final String PATTERN_STRING_PEM_PREFIX_PRIVATE_KEY_SECP256K1 = "302E0201010420";
    private static final String PATTERN_STRING_PEM_PREFIX_PUBLIC_KEY_SECP256R1 = "3059301306072a8648ce3d020106082a8648ce3d030107034200";
    private static final String PATTERN_STRING_PEM_PREFIX_PUBLIC_KEY_SECP256K1 = "3056301006072a8648ce3d020106052b8104000a034200";

    //PEM FORMAT SUFFIXES
    private static final String PATTERN_STRING_PEM_SUFFIX_PRIVATE_KEY_SECP256K1 = "A00706052B8104000A";
    private static final String PATTERN_STRING_PEM_SUFFIX_PRIVATE_KEY_SECP256R1 = "A00A06082A8648CE3D030107";

    //PEM HEADERS & FOOTERS
    private static final String PEM_HEADER_PREFIX = "-----BEGIN EC ";
    private static final String PEM_HEADER_SUFFIX = "-----";
    private static final String PEM_FOOTER_PREFIX = "-----END EC ";
    private static final String PEM_FOOTER_SUFFIX = "-----";

    private enum PEMObjectType {
        PUBLICKEY("PUBLIC KEY"),
        PRIVATEKEY("PRIVATE KEY"),
        SIGNATURE("SIGNATURE");

        private String value;

        PEMObjectType(String value) {
            this.value = value;
        }

        public String getString() {
            return value;
        }
    }

    public String convertPEMFormattedPublicKeyToEOSFormat(@NotNull String publicKeyPEM)
            throws EOSFormatterError {
        String eosFormattedPublicKey = publicKeyPEM;

        return eosFormattedPublicKey;
    }

    public String convertEOSPublicKeyToPEMFormat(@NotNull String publicKeyEOS)
            throws EOSFormatterError {
        String pemFormattedPublickKey = publicKeyEOS;

        return pemFormattedPublickKey;
    }

    public String convertPEMSignatureToEOSFormat(@NotNull String signaturePEM)
            throws EOSFormatterError {
        String eosFormattedSignature = signaturePEM;

        return eosFormattedSignature;
    }

    public String convertEOSSignatureToPEMFormat(@NotNull String signatureEOS)
            throws EOSFormatterError {
        String pemFormattedSignature = signatureEOS;

        return pemFormattedSignature;
    }

    public String convertPEMFormattedPrivateKeyToEOSFormat(@NotNull String privateKeyPEM)
            throws EOSFormatterError {
        String eosFormattedPrivateKey = privateKeyPEM;

        return eosFormattedPrivateKey;
    }

    /**
     * This method converts an EOS formatted private key to the PEM format.
     *
     * @param privateKeyEOS Private key in PEM format
     * @return PEM formatted private key as a string
     */
    public static String convertEOSPrivateKeyToPEMFormat(@NotNull String privateKeyEOS)
            throws EOSFormatterError {
        String pemFormattedPrivateKey = privateKeyEOS;
        AlgorithmEmployed algorithmEmployed;

        /*
        If the private key was encrypted using the secp256R1 algorithm it will have a 'PVT_R1_' prefix
        that needs to be removed.
         */
        if (pemFormattedPrivateKey.matches(".*" + PATTERN_STRING_EOS_PREFIX_PVT_R1 + ".*")) {
            algorithmEmployed = AlgorithmEmployed.SECP256R1;
            /*
            Split the prefix from the key and take the second half of string.  The second half contains
            the key minus the prefix.
             */
            pemFormattedPrivateKey = pemFormattedPrivateKey
                    .split(PATTERN_STRING_EOS_PREFIX_PVT_R1)[1];
        } else {
            algorithmEmployed = AlgorithmEmployed.SECP256K1;
        }

        //Base58 decode the key
        byte[] base58DecodedPrivateKey;
        try {
            base58DecodedPrivateKey = decodePrivateKey(pemFormattedPrivateKey,
                    algorithmEmployed);
        } catch (Exception e) {
            throw new EOSFormatterError(ErrorConstants.BASE58_DECODING_ERROR, e);
        }

        //Convert decoded array to string
        pemFormattedPrivateKey = Hex.toHexString(base58DecodedPrivateKey);

        //Add header and footer
        switch (algorithmEmployed) {
            case SECP256R1:
                pemFormattedPrivateKey =
                        PATTERN_STRING_PEM_PREFIX_PRIVATE_KEY_SECP256R1 + pemFormattedPrivateKey
                                + PATTERN_STRING_PEM_SUFFIX_PRIVATE_KEY_SECP256R1;
                break;
            case PRIME256V1:
                pemFormattedPrivateKey =
                        PATTERN_STRING_PEM_PREFIX_PRIVATE_KEY_SECP256R1 + pemFormattedPrivateKey
                                + PATTERN_STRING_PEM_SUFFIX_PRIVATE_KEY_SECP256R1;
                break;
            case SECP256K1:
                pemFormattedPrivateKey =
                        PATTERN_STRING_PEM_PREFIX_PRIVATE_KEY_SECP256K1 + pemFormattedPrivateKey
                                + PATTERN_STRING_PEM_SUFFIX_PRIVATE_KEY_SECP256K1;
                break;
            default:
                break;
        }

        /*
        Correct the sequence length value.  According to the ASN.1 specification.  For a DER encoded private key the second byte reflects the number of bytes following
        the second byte.  Here we take the length of the entire string, subtract 4 to remove the first two bytes, divide by 2 (i.e. two characters per byte) and replace
        the second byte in the string with the corrected length.
         */
        if (pemFormattedPrivateKey.length() > 4) {
            int i = (pemFormattedPrivateKey.length() - 4) / 2;
            String correctedLength = Integer.toHexString(i);
            pemFormattedPrivateKey = pemFormattedPrivateKey.substring(0, 2) + correctedLength
                    + pemFormattedPrivateKey.substring(4);
        } else {
            throw new EOSFormatterError(ErrorConstants.INVALID_EOS_PRIVATE_KEY);
        }

        try {
            pemFormattedPrivateKey = derToPEM(Hex.decode(pemFormattedPrivateKey),
                    PEMObjectType.PRIVATEKEY);
        } catch (Exception e) {
            throw new EOSFormatterError(e);
        }
        return pemFormattedPrivateKey;
    }

    public String extractSerializedTransactionFromSignable(@NotNull String eosTransaction)
            throws EOSFormatterError {
        String serializedTransaction = eosTransaction;

        return eosTransaction;
    }

    public String prepareSerializedTransactionForSigning(@NotNull String serializedTransaction,
            @NotNull String chainId) throws EOSFormatterError {
        String signable = serializedTransaction;

        return signable;
    }

    /**
     * This method converts a DER encoded private key, public key, or signature into the PEM format.
     * Example of a PEM formatted private key: -----BEGIN EC PRIVATE KEY-----
     * MDECAQEEIEJSCKmyR0kmxy2pgkEwkqrodn2jG9mhXRhhxgsneuBsoAoGCCqGSM49AwEH -----END EC PRIVATE
     * KEY----- The key data between the header and footer is Base64 encoded.
     *
     * @param derEncodedByteArray DER encoded byte array to convert to PEM format
     * @param pemObjectType The type of PEM object being created (i.e. Private Key, Public Key,
     * Signature).
     * @return PEM format as string.
     */
    private static String derToPEM(@NotNull byte[] derEncodedByteArray,
            @NotNull PEMObjectType pemObjectType) {
        StringBuilder pemForm = new StringBuilder();
        try {
            //Build Header
            pemForm.append(PEM_HEADER_PREFIX);
            pemForm.append(pemObjectType.getString());
            pemForm.append(PEM_HEADER_SUFFIX);
            pemForm.append("\n");

            //Base64 Encode DER Encoded Byte Array And Add to PEM Object
            Base64.Encoder encoder = Base64.getEncoder();
            String base64EncodedByteArray = encoder.encodeToString(derEncodedByteArray);
            pemForm.append(base64EncodedByteArray);
            pemForm.append("\n");

            //Build Footer
            pemForm.append(PEM_FOOTER_PREFIX);
            pemForm.append(pemObjectType.getString());
            pemForm.append(PEM_FOOTER_SUFFIX);
        } catch (Exception e) {
            throw new RuntimeException(ErrorConstants.DER_TO_PEM_CONVERSION, e);
        }
        return pemForm.toString();
    }

    /**
     * This method Base58 decodes the private key and validates its checksum.
     *
     * @param strKey Base58 value of the key
     * @param keyType key type
     * @return Base58 decoded key minus checksum
     */
    private static byte[] decodePrivateKey(@NotNull String strKey, AlgorithmEmployed keyType) {
        if (strKey.isEmpty()) {
            throw new IllegalArgumentException(ErrorConstants.BASE58_EMPTY_KEY);
        }

        byte[] decodedKey;

        try {
            byte[] base58Decoded = Base58.decode(strKey);
            byte[] firstCheckSum = Arrays
                    .copyOfRange(base58Decoded, base58Decoded.length - 4, base58Decoded.length);
            decodedKey = Arrays.copyOfRange(base58Decoded, 0, base58Decoded.length - 4);

            switch (keyType) {
                case SECP256R1:
                    byte[] keyTypeByteArray = "R1".getBytes();
                    if (!validateRipeMD160CheckSum(decodedKey, firstCheckSum, keyTypeByteArray)) {
                        throw new IllegalArgumentException(ErrorConstants.BASE58_INVALID_CHECKSUM);
                    }
                    break;
                case SECP256K1:
                    if (!validateSha256x2CheckSum(decodedKey, firstCheckSum)) {
                        throw new IllegalArgumentException(ErrorConstants.BASE58_INVALID_CHECKSUM);
                    }
                    break;
                default:
                    break;
            }

            // trim 0x80 out if the key size is more than 32 bytes
            // this code apply for key has more than 32 byte and non R1 key
            if (decodedKey.length > 32 && keyType != AlgorithmEmployed.SECP256R1) {
                // Slice out the first byte
                decodedKey = Arrays.copyOfRange(decodedKey, 1, decodedKey.length);
                if (decodedKey.length == 33 && decodedKey[32] == ((Integer) 1).byteValue()) {
                    // Slice out last byte
                    decodedKey = Arrays.copyOfRange(decodedKey, 0, decodedKey.length - 1);
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ErrorConstants.BASE58_DECODING_ERROR, ex);
        }

        return decodedKey;
    }

    /**
     * Validate checksum by RipeMD160 digestion
     *
     * @param inputKey - input key to validate
     * @param checkSumToValidate - checksum to validate with the checksum inside input key
     * @param keyTypeByteArray -
     */
    private static boolean validateRipeMD160CheckSum(@NotNull byte[] inputKey,
            @NotNull byte[] checkSumToValidate, @NotNull byte[] keyTypeByteArray) {
        if (inputKey.length == 0 || checkSumToValidate.length == 0
                || keyTypeByteArray.length == 0) {
            throw new IllegalArgumentException(
                    ErrorConstants.BASE58_EMPTY_CHECKSUM_OR_KEY_OR_KEY_TYPE);
        }

        byte[] keyWithType = Bytes.concat(inputKey, keyTypeByteArray);
        byte[] digestRIPEMD160 = digestRIPEMD160(keyWithType);
        byte[] checkSumFromInputKey = Arrays.copyOfRange(digestRIPEMD160, 0, 4);
        return Arrays.equals(checkSumToValidate, checkSumFromInputKey);
    }

    /**
     * Validate checksum by double Sha256
     *
     * @param inputKey - input key to validate
     * @param checkSumToValidate - checksum to validate with the checksum inside input key
     * @return whether the given checksum equal to the checksum inside input key
     */
    private static boolean validateSha256x2CheckSum(@NotNull byte[] inputKey,
            @NotNull byte[] checkSumToValidate) {
        if (inputKey.length == 0 || checkSumToValidate.length == 0) {
            throw new IllegalArgumentException(ErrorConstants.BASE58_EMPTY_CHECKSUM_OR_KEY);
        }

        byte[] sha256x2 = Sha256Hash.hashTwice(inputKey);
        byte[] checkSumFromInputKey = Arrays.copyOfRange(sha256x2, 0, 4);
        return Arrays.equals(checkSumToValidate, checkSumFromInputKey);
    }

    /**
     * Digesting input byte[] to RIPEMD160 format
     *
     * @param input - input byte[]
     * @return RIPEMD160 format
     */
    private static byte[] digestRIPEMD160(@NotNull byte[] input) {
        RIPEMD160Digest digest = new RIPEMD160Digest();
        byte[] output = new byte[digest.getDigestSize()];
        digest.update(input, 0, input.length);
        digest.doFinal(output, 0);
        return output;
    }

}
