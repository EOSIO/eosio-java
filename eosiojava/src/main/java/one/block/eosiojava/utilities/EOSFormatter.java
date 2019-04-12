

package one.block.eosiojava.utilities;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.primitives.Bytes;
import java.io.CharArrayReader;
import java.io.Reader;
import java.math.BigInteger;
import java.util.Arrays;
import javax.xml.bind.DatatypeConverter;
import one.block.eosiojava.enums.AlgorithmEmployed;
import one.block.eosiojava.error.ErrorConstants;
import one.block.eosiojava.error.utilities.*;
import org.bitcoinj.core.Base58;
import org.bitcoinj.core.Sha256Hash;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.DLSequence;
import org.bouncycastle.asn1.sec.SECNamedCurves;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.asn1.x9.X9IntegerConverter;
import org.bouncycastle.crypto.digests.RIPEMD160Digest;
import org.bouncycastle.crypto.ec.CustomNamedCurves;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.math.ec.ECAlgorithms;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.ec.FixedPointUtil;
import org.bouncycastle.util.encoders.Hex;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.jetbrains.annotations.NotNull;

/**
 * This class provides a number of helper methods that can be used to convert certain objects to and
 * from formats that are germane to EOS blockchain transactions and the PEM (Privacy Enhanced Mail)
 * format for those objects.  It also provides methods to format a serialized transaction into a
 * format that can be submitted to an EOS blockchain.
 */
public class EOSFormatter {

    /*
    EOS Format Prefixes - The prefixes below are all used to preface the EOS format of certain types
    of keys and signatures.  For instance, 'EOS' is used to preface a legacy form of a public key
    that was generated using the secp256k1 algorithm.  The prefixes and there associated objects are
    as follows:
    EOS - Public Key generated with secp256k1 algorithm formatted for use on EOS blockchain.
    PUB_R1_ - Public Key generated with secp256r1 or prime256v1 algorithm formatted for use on EOS blockchain.
    PUB_K1_ - Public Key generated with secp256k1 algorithm formatted for use on EOS blockchain.
    PVT_R1_ - Private Key generated with secp256r1 algorithm formatted for use on EOS blockchain.
    SIG_R1_ - Signature signed with key generated with secp256r1 algorithm.
    SIG_K1_ - Signature signed with key generated with secp256k1 algorithm.
     */
    private static final String PATTERN_STRING_EOS_PREFIX_EOS = "EOS";
    private static final String PATTERN_STRING_EOS_PREFIX_PUB_R1 = "PUB_R1_";
    private static final String PATTERN_STRING_EOS_PREFIX_PUB_K1 = "PUB_K1_";
    private static final String PATTERN_STRING_EOS_PREFIX_PVT_R1 = "PVT_R1_";
    private static final String PATTERN_STRING_EOS_PREFIX_SIG_R1 = "SIG_R1_";
    private static final String PATTERN_STRING_EOS_PREFIX_SIG_K1 = "SIG_K1_";

    //PEM FORMAT PREFIXES
    private static final String PATTERN_STRING_PEM_PREFIX_PRIVATE_KEY_SECP256R1 = "30770201010420";
    private static final String PATTERN_STRING_PEM_PREFIX_PRIVATE_KEY_SECP256K1 = "302E0201010420";
    private static final String PATTERN_STRING_PEM_PREFIX_PUBLIC_KEY_SECP256R1_UNCOMPRESSED = "3059301306072a8648ce3d020106082a8648ce3d030107034200";
    private static final String PATTERN_STRING_PEM_PREFIX_PUBLIC_KEY_SECP256K1_UNCOMPRESSED = "3056301006072a8648ce3d020106052b8104000a034200";
    private static final String PATTERN_STRING_PEM_PREFIX_PUBLIC_KEY_SECP256R1_COMPRESSED = "3039301306072a8648ce3d020106082a8648ce3d030107032200";
    private static final String PATTERN_STRING_PEM_PREFIX_PUBLIC_KEY_SECP256K1_COMPRESSED = "3036301006072a8648ce3d020106052b8104000a032200";

    //PEM FORMAT SUFFIXES
    private static final String PATTERN_STRING_PEM_SUFFIX_PRIVATE_KEY_SECP256K1 = "A00706052B8104000A";
    private static final String PATTERN_STRING_PEM_SUFFIX_PRIVATE_KEY_SECP256R1 = "A00A06082A8648CE3D030107";

    //PEM HEADERS & FOOTERS
    private static final String PEM_HEADER_PUBLIC_KEY = "-----BEGIN PUBLIC KEY-----";
    private static final String PEM_FOOTER_PUBLIC_KEY = "-----END PUBLIC KEY-----";
    private static final String PEM_HEADER_PRIVATE_KEY = "-----BEGIN EC PRIVATE KEY-----";
    private static final String PEM_FOOTER_PRIVATE_KEY = "-----END EC PRIVATE KEY-----";
    private static final String PEM_HEADER_EC_PRIVATE_KEY = "EC PRIVATE KEY";
    private static final String PEM_HEADER_EC_PUBLIC_KEY = "PUBLIC KEY";

    //CHECKSUM RELATED
    private static final String SECP256R1_AND_PRIME256V1_CHECKSUM_VALIDATION_SUFFIX = "R1";
    private static final String SECP256K1_CHECKSUM_VALIDATION_SUFFIX = "K1";
    private static final String LEGACY_CHECKSUM_VALIDATION_SUFFIX = "";

    //CONSTANTS USED DURING DECODING AND CHECKSUM VALIDATION
    private static final int STANDARD_KEY_LENGTH = 32;
    private static final int CHECKSUM_BYTES = 4;
    private static final int FIRST_TWO_BYTES_OF_KEY = 4;
    private static final int DATA_SEQUENCE_LENGTH_BYTE_POSITION = 2;

    //CONSTANTS USED DURING EOS ENCODING
    private static final int EOS_SECP256K1_HEADER_BYTE = 0x80;

    //CONSTANTS USED DURING EOS DECODING
    private static final byte UNCOMPRESSED_PUBLIC_KEY_BYTE_INDICATOR = 0x04;
    private static final byte COMPRESSED_PUBLIC_KEY_BYTE_INDICATOR_POSITIVE_Y = 0x02;
    private static final byte COMPRESSED_PUBLIC_KEY_BYTE_INDICATOR_NEGATIVE_Y = 0x03;

    private static final int CHAIN_ID_LENGTH = 64;
    /**
     * Minimum length of signable transaction: Chain id length + 32 bytes of 0's length + 1 (minimum length for serialized transaction)
     */
    private static final int MINIMUM_SIGNABLE_TRANSACTION_LENGTH = CHAIN_ID_LENGTH + 64 + 1;

    //SIGNATURE RELATED CONSTANTS
    private static final int VALUE_TO_ADD_TO_SIGNATURE_HEADER = 31;
    private static final int EXPECTED_R_OR_S_LENGTH = 32;
    private static final int NUMBER_OF_POSSIBLE_PUBLIC_KEYS = 4;

    /*
    Covers the PEM objects currently supported by this class (i.e. The class allows for PEM
    formatting of public keys, private keys, and signatures).
     */
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

    /**
     * Const name of secp256r1 curves
     */
    private static final String SECP256_R1 = "secp256r1";

    /**
     * Const name of secp256k1 curves
     */
    private static final String SECP256_K1 = "secp256k1";

    /**
     * EC domain parameters of R1 key
     */
    private static final ECDomainParameters ecParamsR1;

    /**
     * EC domain parameters of K1 key
     */
    private static final ECDomainParameters ecParamsK1;

    /**
     * EC parameters holder of R1 key type
     */
    private static final X9ECParameters CURVE_PARAMS_R1 = CustomNamedCurves.getByName(SECP256_R1);

    /**
     * EC parameters holder of K1 key type
     */
    private static final X9ECParameters CURVE_PARAMS_K1 = CustomNamedCurves.getByName(SECP256_K1);

    /**
     * EC holder of R1 key type
     */
    private static final ECDomainParameters CURVE_R1;

    /**
     * Half curve value of R1 key type (to calculate low S)
     */
    private static final BigInteger HALF_CURVE_ORDER_R1;

    /**
     * EC holder of K1 key type
     */
    private static final ECDomainParameters CURVE_K1;

    /**
     * Half curve value of K1 key type (to calculate low S)
     */
    private static final BigInteger HALF_CURVE_ORDER_K1;


    static {
        X9ECParameters paramsR1 = SECNamedCurves.getByName(SECP256_R1);
        ecParamsR1 = new ECDomainParameters(paramsR1.getCurve(), paramsR1.getG(), paramsR1.getN(),
                paramsR1.getH());

        X9ECParameters paramsK1 = SECNamedCurves.getByName(SECP256_K1);
        ecParamsK1 = new ECDomainParameters(paramsK1.getCurve(), paramsK1.getG(), paramsK1.getN(),
                paramsK1.getH());

        // secp256r1
        FixedPointUtil.precompute(CURVE_PARAMS_R1.getG());
        CURVE_R1 = new ECDomainParameters(
                CURVE_PARAMS_R1.getCurve(),
                CURVE_PARAMS_R1.getG(),
                CURVE_PARAMS_R1.getN(),
                CURVE_PARAMS_R1.getH());
        HALF_CURVE_ORDER_R1 = CURVE_PARAMS_R1.getN().shiftRight(1);

        // secp256k1
        CURVE_K1 = new ECDomainParameters(
                CURVE_PARAMS_K1.getCurve(),
                CURVE_PARAMS_K1.getG(),
                CURVE_PARAMS_K1.getN(),
                CURVE_PARAMS_K1.getH());
        HALF_CURVE_ORDER_K1 = CURVE_PARAMS_K1.getN().shiftRight(1);
    }

    /**
     * This method converts a PEM formatted public key to the EOS format.
     *
     * @param publicKeyPEM Public key in the PEM format
     * @param requireLegacyFormOfSecp256k1Key - If the developer prefers a legacy version of a
     * secp256k1 key that uses a "EOS" prefix.
     * @return EOS formatted public key as string
     */
    @NotNull
    public static String convertPEMFormattedPublicKeyToEOSFormat(@NotNull String publicKeyPEM,
            boolean requireLegacyFormOfSecp256k1Key)
            throws EOSFormatterError {
        String eosFormattedPublicKey = publicKeyPEM;
        AlgorithmEmployed algorithmEmployed;
        PemObject pemObject;

        /*
         Validate that key type in PEM object is 'EC PUBLIC KEY'.
         */
        String type;
        try (Reader reader = new CharArrayReader(eosFormattedPublicKey.toCharArray());
                PemReader pemReader = new PemReader(reader);) {
            pemObject = pemReader.readPemObject();
            type = pemObject.getType();
        } catch (Exception e) {
            throw new EOSFormatterError(ErrorConstants.INVALID_PEM_PRIVATE_KEY, e);
        }

        //Perform a case-insensitive search for the 'EC PRIVATE KEY' string
        if (type.matches("(?i:.*" + PEM_HEADER_EC_PUBLIC_KEY + ".*)")) {

            //Get Base64 encoded public key from PEM object
            eosFormattedPublicKey = Hex.toHexString(pemObject.getContent());

            //Determine algorithm used to generate key and remove DER header
            if (eosFormattedPublicKey
                    .toUpperCase().contains(
                            PATTERN_STRING_PEM_PREFIX_PUBLIC_KEY_SECP256R1_UNCOMPRESSED
                                    .toUpperCase())) {
                eosFormattedPublicKey = eosFormattedPublicKey.toUpperCase()
                        .replace(
                                PATTERN_STRING_PEM_PREFIX_PUBLIC_KEY_SECP256R1_UNCOMPRESSED
                                        .toUpperCase(),
                                "");
                algorithmEmployed = AlgorithmEmployed.SECP256R1;
            } else if (eosFormattedPublicKey
                    .toUpperCase().contains(
                            PATTERN_STRING_PEM_PREFIX_PUBLIC_KEY_SECP256R1_COMPRESSED
                                    .toUpperCase())) {
                eosFormattedPublicKey = eosFormattedPublicKey.toUpperCase()
                        .replace(PATTERN_STRING_PEM_PREFIX_PUBLIC_KEY_SECP256R1_COMPRESSED
                                        .toUpperCase(),
                                "");
                algorithmEmployed = AlgorithmEmployed.SECP256R1;
            } else if (eosFormattedPublicKey
                    .toUpperCase().contains(
                            PATTERN_STRING_PEM_PREFIX_PUBLIC_KEY_SECP256K1_UNCOMPRESSED
                                    .toUpperCase())) {
                eosFormattedPublicKey = eosFormattedPublicKey.toUpperCase()
                        .replace(
                                PATTERN_STRING_PEM_PREFIX_PUBLIC_KEY_SECP256K1_UNCOMPRESSED
                                        .toUpperCase(),
                                "");
                algorithmEmployed = AlgorithmEmployed.SECP256K1;
            } else if (eosFormattedPublicKey
                    .toUpperCase().contains(
                            PATTERN_STRING_PEM_PREFIX_PUBLIC_KEY_SECP256K1_COMPRESSED
                                    .toUpperCase())) {
                eosFormattedPublicKey = eosFormattedPublicKey.toUpperCase()
                        .replace(PATTERN_STRING_PEM_PREFIX_PUBLIC_KEY_SECP256K1_COMPRESSED
                                        .toUpperCase(),
                                "");
                algorithmEmployed = AlgorithmEmployed.SECP256K1;
            } else {
                throw new EOSFormatterError(ErrorConstants.INVALID_DER_PRIVATE_KEY);
            }

            /*
            Compress the public key if necessary.
            Compression is only necessary if the key has a value of 0x04 for the first byte, which
            indicates it is uncompressed.
            */
            byte[] eosFormattedPublicKeyBytes = Hex.decode(eosFormattedPublicKey);
            if (eosFormattedPublicKeyBytes[0] == UNCOMPRESSED_PUBLIC_KEY_BYTE_INDICATOR) {
                try {
                    eosFormattedPublicKey = Hex.toHexString(
                            compressPublickey(Hex.decode(eosFormattedPublicKey),
                                    algorithmEmployed));
                } catch (Exception e) {
                    throw new EOSFormatterError(e);
                }
            }

            try {
                //Add checksum,Base58 encode key, and add prefix
                eosFormattedPublicKey = encodePublicKey(Hex.decode(eosFormattedPublicKey),
                        algorithmEmployed, requireLegacyFormOfSecp256k1Key);
            } catch (Base58ManipulationError e) {
                throw new EOSFormatterError(e);
            }


        } else {
            throw new EOSFormatterError(ErrorConstants.INVALID_PEM_PRIVATE_KEY);
        }

        return eosFormattedPublicKey;
    }

    /**
     * This method converts an EOS formatted public key to the PEM format.
     *
     * @param publicKeyEOS Public key in the EOS format
     * @return PEM formatted public key as string
     */
    @NotNull
    public static String convertEOSPublicKeyToPEMFormat(@NotNull String publicKeyEOS)
            throws EOSFormatterError {
        String pemFormattedPublickKey = publicKeyEOS;
        AlgorithmEmployed algorithmEmployed;
        String keyPrefix;

        /*
        The public key will contain an EOS prefix indicating which algorithm was used to generate it.
        Below we split the prefix from the key.
         */
        if (pemFormattedPublickKey.toUpperCase()
                .contains(PATTERN_STRING_EOS_PREFIX_PUB_R1.toUpperCase())) {
            algorithmEmployed = AlgorithmEmployed.SECP256R1;
            keyPrefix = PATTERN_STRING_EOS_PREFIX_PUB_R1;
            pemFormattedPublickKey = pemFormattedPublickKey
                    .replace(PATTERN_STRING_EOS_PREFIX_PUB_R1, "");
        } else if (pemFormattedPublickKey.toUpperCase()
                .contains(PATTERN_STRING_EOS_PREFIX_PUB_K1.toUpperCase())) {
            algorithmEmployed = AlgorithmEmployed.SECP256K1;
            keyPrefix = PATTERN_STRING_EOS_PREFIX_PUB_K1;
            pemFormattedPublickKey = pemFormattedPublickKey
                    .replace(PATTERN_STRING_EOS_PREFIX_PUB_K1, "");
        } else if (pemFormattedPublickKey.toUpperCase()
                .contains(PATTERN_STRING_EOS_PREFIX_EOS.toUpperCase())) {
            algorithmEmployed = AlgorithmEmployed.SECP256K1;
            keyPrefix = PATTERN_STRING_EOS_PREFIX_EOS;
            pemFormattedPublickKey = pemFormattedPublickKey
                    .replace(PATTERN_STRING_EOS_PREFIX_EOS, "");
        } else {
            throw new EOSFormatterError(ErrorConstants.INVALID_EOS_PUBLIC_KEY);
        }

        //Base58 decode the key
        byte[] base58DecodedPublicKey;
        try {
            base58DecodedPublicKey = decodePublicKey(pemFormattedPublickKey,
                    keyPrefix);
        } catch (Exception e) {
            throw new EOSFormatterError(ErrorConstants.BASE58_DECODING_ERROR, e);
        }

        //Convert decoded array to string
        pemFormattedPublickKey = Hex.toHexString(base58DecodedPublicKey);

        /*
        Compress the public key if necessary.
        Compression is only necessary if the key has a value of 0x04 for the first byte, which
        indicates it is uncompressed.
         */
        if (base58DecodedPublicKey[0] == UNCOMPRESSED_PUBLIC_KEY_BYTE_INDICATOR) {
            try {
                pemFormattedPublickKey = Hex.toHexString(
                        compressPublickey(Hex.decode(pemFormattedPublickKey), algorithmEmployed));
            } catch (Exception e) {
                throw new EOSFormatterError(e);
            }
        }

        //Add DER header
        switch (algorithmEmployed) {
            case SECP256R1:
                pemFormattedPublickKey =
                        PATTERN_STRING_PEM_PREFIX_PUBLIC_KEY_SECP256R1_COMPRESSED
                                + pemFormattedPublickKey;
                break;
            case SECP256K1:
                pemFormattedPublickKey =
                        PATTERN_STRING_PEM_PREFIX_PUBLIC_KEY_SECP256K1_COMPRESSED
                                + pemFormattedPublickKey;
                break;
            default:
                throw new EOSFormatterError(ErrorConstants.UNSUPPORTED_ALGORITHM);
        }

        /*
        Correct the sequence length value.  According to the ASN.1 specification.  For a DER encoded public key the second byte reflects the number of bytes following
        the second byte.  Here we take the length of the entire string, subtract 4 to remove the first two bytes, divide by 2 (i.e. two characters per byte) and replace
        the second byte in the string with the corrected length.
         */
        if (pemFormattedPublickKey.length() > FIRST_TWO_BYTES_OF_KEY) {
            int i = (pemFormattedPublickKey.length() - FIRST_TWO_BYTES_OF_KEY) / 2;
            String correctedLength = Integer.toHexString(i);
            pemFormattedPublickKey =
                    pemFormattedPublickKey.substring(0, DATA_SEQUENCE_LENGTH_BYTE_POSITION)
                            + correctedLength
                            + pemFormattedPublickKey.substring(FIRST_TWO_BYTES_OF_KEY);
        } else {
            throw new EOSFormatterError(ErrorConstants.INVALID_EOS_PUBLIC_KEY);
        }

        try {
            pemFormattedPublickKey = derToPEM(Hex.decode(pemFormattedPublickKey),
                    PEMObjectType.PUBLICKEY);
        } catch (Exception e) {
            throw new EOSFormatterError(e);
        }

        return pemFormattedPublickKey;
    }

    /**
     * This method converts a signature to a EOS compliant form.  The signature to be converted must
     * be an The ECDSA signature that is a DER encoded ASN.1 sequence of two integer fields (see
     * ECDSA-Sig-Value in rfc3279 section 2.2.3).
     *
     * The DER encoded ECDSA signature follows the following format: Byte 1 - Sequence (Should be
     * 30) Byte 2 - Signature length Byte 3 - R Marker (0x02) Byte 4 - R length Bytes 5 to 37 or 38-
     * R Byte After R - S Marker (0x02) Byte After S Marker - S Length Bytes After S Length - S
     * (always 32-33 bytes) Byte Final - Hash Type
     *
     * @param signatureDER ECDSA DER encoded signature as byte array
     * @param signableTransaction Transaction in signable format
     * @return EOS format of signature
     */
    @NotNull
    public static String convertDERSignatureToEOSFormat(@NotNull byte[] signatureDER,
            @NotNull byte[] signableTransaction, @NotNull String publicKeyPEM)
            throws EOSFormatterError {
        String eosFormattedSignature = "";

        try (ASN1InputStream asn1InputStream = new ASN1InputStream(signatureDER)) {

            PEMProcessor publicKey = new PEMProcessor(publicKeyPEM);
            AlgorithmEmployed algorithmEmployed = publicKey.getAlgorithm();
            byte[] keyData = publicKey.getKeyData();
            DLSequence sequence = (DLSequence) asn1InputStream.readObject();
            BigInteger r = ((ASN1Integer) sequence.getObjectAt(0)).getPositiveValue();
            BigInteger s = ((ASN1Integer) sequence.getObjectAt(1)).getPositiveValue();

            s = checkAndHandleLowS(s, algorithmEmployed);

            /*
            Get recovery ID.  This is the index of the public key (0-3) that represents the
            expected public key used to sign the transaction.
             */
            int recoverId = getRecoveryId(r, s, Sha256Hash.of(signableTransaction), keyData,
                    algorithmEmployed);

            if (recoverId < 0) {
                throw new IllegalStateException(
                        ErrorConstants.COULD_NOT_RECOVER_PUBLIC_KEY_FROM_SIG);
            }

            //Add RecoveryID + 27 + 4 to create the header byte
            recoverId += VALUE_TO_ADD_TO_SIGNATURE_HEADER;
            byte headerByte = ((Integer) recoverId).byteValue();



            byte[] decodedSignature = Bytes
                    .concat(new byte[]{headerByte}, org.bitcoinj.core.Utils.bigIntegerToBytes(r,EXPECTED_R_OR_S_LENGTH), org.bitcoinj.core.Utils.bigIntegerToBytes(s,EXPECTED_R_OR_S_LENGTH));
            if (algorithmEmployed.equals(AlgorithmEmployed.SECP256K1) &&
                    !isCanonical(decodedSignature)) {
                throw new IllegalArgumentException(ErrorConstants.NON_CANONICAL_SIGNATURE);
            }

            //Add checksum to signature
            byte[] signatureWithCheckSum;
            String signaturePrefix;
            switch (algorithmEmployed) {
                case SECP256R1:
                    signatureWithCheckSum = addCheckSumToSignature(decodedSignature,
                            SECP256R1_AND_PRIME256V1_CHECKSUM_VALIDATION_SUFFIX.getBytes());
                    signaturePrefix = PATTERN_STRING_EOS_PREFIX_SIG_R1;
                    break;
                case SECP256K1:
                    signatureWithCheckSum = addCheckSumToSignature(decodedSignature,
                            SECP256K1_CHECKSUM_VALIDATION_SUFFIX.getBytes());
                    signaturePrefix = PATTERN_STRING_EOS_PREFIX_SIG_K1;
                    break;
                default:
                    throw new EOSFormatterError(ErrorConstants.UNSUPPORTED_ALGORITHM);

            }

            //Base58 encode signature and add pertinent EOS prefix
            eosFormattedSignature = signaturePrefix.concat(Base58.encode(signatureWithCheckSum));

        } catch (Exception e) {
            throw new EOSFormatterError(ErrorConstants.SIGNATURE_FORMATTING_ERROR, e);
        }

        return eosFormattedSignature;
    }

    /**
     * This method converts a signature to a EOS compliant form.  The signature to be converted must
     * be an The ECDSA signature that is a DER encoded ASN.1 sequence of two integer fields (see
     * ECDSA-Sig-Value in rfc3279 section 2.2.3).  This method should be used when only the R and S
     * values of the signature are available.
     *
     * The DER encoded ECDSA signature follows the following format: Byte 1 - Sequence (Should be
     * 30) Byte 2 - Signature length Byte 3 - R Marker (0x02) Byte 4 - R length Bytes 5 to 37 or 38-
     * R Byte After R - S Marker (0x02) Byte After S Marker - S Length Bytes After S Length - S
     * (always 32-33 bytes) Byte Final - Hash Type
     *
     * @param signatureR R value as BigInteger in string format
     * @param signatureS S value as BigInteger in string format
     * @param signableTransaction Transaction in signable format
     * @param publicKeyPEM Public Key used to sign in PEM format
     * @return EOS format of signature
     */
    @NotNull
    public static String convertRawRandSofSignatureToEOSFormat(@NotNull String signatureR,
            String signatureS,
            @NotNull byte[] signableTransaction, @NotNull String publicKeyPEM)
            throws EOSFormatterError {
        String eosFormattedSignature = "";

        try {
            PEMProcessor publicKey = new PEMProcessor(publicKeyPEM);
            AlgorithmEmployed algorithmEmployed = publicKey.getAlgorithm();
            byte[] keyData = publicKey.getKeyData();

            BigInteger r = new BigInteger(signatureR);
            BigInteger s = new BigInteger(signatureS);

            s = checkAndHandleLowS(s, algorithmEmployed);

            /*
            Get recovery ID.  This is the index of the public key (0-3) that represents the
            expected public key used to sign the transaction.
             */
            int recoverId = getRecoveryId(r, s, Sha256Hash.of(signableTransaction), keyData,
                    algorithmEmployed);

            if (recoverId < 0) {
                throw new IllegalStateException(
                        ErrorConstants.COULD_NOT_RECOVER_PUBLIC_KEY_FROM_SIG);
            }

            //Add RecoveryID + 27 + 4 to create the header byte
            recoverId += VALUE_TO_ADD_TO_SIGNATURE_HEADER;
            byte headerByte = ((Integer) recoverId).byteValue();

            byte[] decodedSignature = Bytes
                    .concat(new byte[]{headerByte}, org.bitcoinj.core.Utils.bigIntegerToBytes(r,EXPECTED_R_OR_S_LENGTH), org.bitcoinj.core.Utils.bigIntegerToBytes(s,EXPECTED_R_OR_S_LENGTH));
            if (algorithmEmployed.equals(AlgorithmEmployed.SECP256K1) &&
                    !isCanonical(decodedSignature)) {
                throw new EosFormatterSignatureIsNotCanonicalError(ErrorConstants.NON_CANONICAL_SIGNATURE);
            }

            //Add checksum to signature
            byte[] signatureWithCheckSum;
            String signaturePrefix;
            switch (algorithmEmployed) {
                case SECP256R1:
                    signatureWithCheckSum = addCheckSumToSignature(decodedSignature,
                            SECP256R1_AND_PRIME256V1_CHECKSUM_VALIDATION_SUFFIX.getBytes());
                    signaturePrefix = PATTERN_STRING_EOS_PREFIX_SIG_R1;
                    break;
                case SECP256K1:
                    signatureWithCheckSum = addCheckSumToSignature(decodedSignature,
                            SECP256K1_CHECKSUM_VALIDATION_SUFFIX.getBytes());
                    signaturePrefix = PATTERN_STRING_EOS_PREFIX_SIG_K1;
                    break;
                default:
                    throw new EOSFormatterError(ErrorConstants.UNSUPPORTED_ALGORITHM);

            }

            //Base58 encode signature and add pertinent EOS prefix
            eosFormattedSignature = signaturePrefix.concat(Base58.encode(signatureWithCheckSum));

        } catch (Exception e) {
            throw new EOSFormatterError(ErrorConstants.SIGNATURE_FORMATTING_ERROR, e);
        }

        return eosFormattedSignature;
    }

    public static String convertEOSSignatureToDERFormat(@NotNull String signatureEOS)
            throws EOSFormatterError {
        String pemFormattedSignature = signatureEOS;

        return pemFormattedSignature;
    }

    /**
     * This method converts a PEM formatted private key to the EOS format.
     *
     * @param privateKeyPEM Private key in PEM format
     * @return EOS formatted private key as string
     */
    @NotNull
    public static String convertPEMFormattedPrivateKeyToEOSFormat(@NotNull String privateKeyPEM)
            throws EOSFormatterError {
        String eosFormattedPrivateKey = privateKeyPEM;
        AlgorithmEmployed algorithmEmployed;
        PemObject pemObject;

        /*
         Validate that key type in PEM object is 'EC PRIVATE KEY'.
         */
        String type;
        try (Reader reader = new CharArrayReader(eosFormattedPrivateKey.toCharArray());
                PemReader pemReader = new PemReader(reader);) {
            pemObject = pemReader.readPemObject();
            type = pemObject.getType();
        } catch (Exception e) {
            throw new EOSFormatterError(ErrorConstants.INVALID_PEM_PRIVATE_KEY, e);
        }

        //Perform a case-insensitive search for the 'EC PRIVATE KEY' string
        if (type.matches("(?i:.*" + PEM_HEADER_EC_PRIVATE_KEY + ".*)")) {

            //Get Base64 encoded private key from PEM object
            eosFormattedPrivateKey = Hex.toHexString(pemObject.getContent());

            //Determine algorithm used to generate key
            if (eosFormattedPrivateKey
                    .matches("(?i:.*" + PATTERN_STRING_PEM_SUFFIX_PRIVATE_KEY_SECP256R1 + ".*)")) {
                algorithmEmployed = AlgorithmEmployed.SECP256R1;
            } else if (eosFormattedPrivateKey
                    .matches("(?i:.*" + PATTERN_STRING_PEM_SUFFIX_PRIVATE_KEY_SECP256K1 + ".*)")) {
                algorithmEmployed = AlgorithmEmployed.SECP256K1;
            } else {
                throw new EOSFormatterError(ErrorConstants.INVALID_DER_PRIVATE_KEY);
            }

            //Strip away the DER header and footer
            switch (algorithmEmployed) {
                case SECP256R1:
                    eosFormattedPrivateKey = eosFormattedPrivateKey
                            .substring(PATTERN_STRING_PEM_PREFIX_PRIVATE_KEY_SECP256R1.length(),
                                    eosFormattedPrivateKey.length()
                                            - PATTERN_STRING_PEM_SUFFIX_PRIVATE_KEY_SECP256R1
                                            .length());
                    break;
                case SECP256K1:
                    eosFormattedPrivateKey = eosFormattedPrivateKey
                            .substring(PATTERN_STRING_PEM_PREFIX_PRIVATE_KEY_SECP256K1.length(),
                                    eosFormattedPrivateKey.length()
                                            - PATTERN_STRING_PEM_SUFFIX_PRIVATE_KEY_SECP256K1
                                            .length());
                    break;
                default:
                    throw new EOSFormatterError(ErrorConstants.UNSUPPORTED_ALGORITHM);
            }

            try {
                //Add checksum and Base58 encode key
                eosFormattedPrivateKey = encodePrivateKey(Hex.decode(eosFormattedPrivateKey),
                        algorithmEmployed);
            } catch (Base58ManipulationError e) {
                throw new EOSFormatterError(e);
            }

            //Add prefix
            StringBuilder builder = new StringBuilder(eosFormattedPrivateKey);
            switch (algorithmEmployed) {
                case SECP256K1:
                    //K1 keys do not currently use prefixes
                    break;
                case SECP256R1:
                    builder.insert(0, PATTERN_STRING_EOS_PREFIX_PVT_R1);
                    break;
                default:
                    break;
            }
            eosFormattedPrivateKey = builder.toString();

        } else {
            throw new EOSFormatterError(ErrorConstants.INVALID_PEM_PRIVATE_KEY);
        }

        return eosFormattedPrivateKey;
    }

    /**
     * This method converts an EOS formatted private key to the PEM format.
     *
     * @param privateKeyEOS Private key in EOS format
     * @return PEM formatted private key as a string
     */
    @NotNull
    public static String convertEOSPrivateKeyToPEMFormat(@NotNull String privateKeyEOS)
            throws EOSFormatterError {
        String pemFormattedPrivateKey = privateKeyEOS;
        AlgorithmEmployed algorithmEmployed;

        /*
        If the private key was encrypted using the secp256R1 algorithm it will have a 'PVT_R1_' prefix
        that needs to be removed.
         */
        if (pemFormattedPrivateKey.toUpperCase()
                .contains(PATTERN_STRING_EOS_PREFIX_PVT_R1.toUpperCase())) {
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
            case SECP256K1:
                pemFormattedPrivateKey =
                        PATTERN_STRING_PEM_PREFIX_PRIVATE_KEY_SECP256K1 + pemFormattedPrivateKey
                                + PATTERN_STRING_PEM_SUFFIX_PRIVATE_KEY_SECP256K1;
                break;
            default:
                throw new EOSFormatterError(ErrorConstants.UNSUPPORTED_ALGORITHM);
        }

        /*
        Correct the sequence length value.  According to the ASN.1 specification.  For a DER
        encoded private key the second byte reflects the number of bytes following
        the second byte.  Here we take the length of the entire string, subtract 4 to remove
        the first two bytes, divide by 2 (i.e. two characters per byte) and replace
        the second byte in the string with the corrected length.
         */
        if (pemFormattedPrivateKey.length() > FIRST_TWO_BYTES_OF_KEY) {
            int i = (pemFormattedPrivateKey.length() - FIRST_TWO_BYTES_OF_KEY) / 2;
            String correctedLength = Integer.toHexString(i);
            pemFormattedPrivateKey =
                    pemFormattedPrivateKey.substring(0, DATA_SEQUENCE_LENGTH_BYTE_POSITION)
                            + correctedLength
                            + pemFormattedPrivateKey.substring(FIRST_TWO_BYTES_OF_KEY);
        } else {
            throw new EOSFormatterError(ErrorConstants.INVALID_EOS_PRIVATE_KEY);
        }

        try {
            pemFormattedPrivateKey = derToPEM(Hex.decode(pemFormattedPrivateKey),
                    PEMObjectType.PRIVATEKEY);
        } catch (DerToPemConversionError e) {
            throw new EOSFormatterError(e);
        }

        return pemFormattedPrivateKey;
    }

    /**
     * Extract serialized transaction from a signable transaction
     * <p/>
     * Signable signature structure:
     * <p/>
     * chainId (64 characters) + serialized transaction + 32 bytes of 0
     *
     * @param eosTransaction - the input signable transaction
     * @return - extracted serialized transaction from the input signable transaction
     * @throws EOSFormatterError if input is invalid
     */
    public static String extractSerializedTransactionFromSignable(@NotNull String eosTransaction)
            throws EOSFormatterError {
        if (eosTransaction.isEmpty()) {
            throw new EOSFormatterError(ErrorConstants.EMPTY_INPUT_EXTRACT_SERIALIZIED_TRANS_FROM_SIGNABLE);
        }

        if (eosTransaction.length() <= MINIMUM_SIGNABLE_TRANSACTION_LENGTH) {
            throw new EOSFormatterError(String.format(ErrorConstants.INVALID_INPUT_SIGNABLE_TRANS_LENGTH_EXTRACT_SERIALIZIED_TRANS_FROM_SIGNABLE, MINIMUM_SIGNABLE_TRANSACTION_LENGTH));
        }

        if (!eosTransaction.endsWith(Hex.toHexString(new byte[32]))) {
            throw new EOSFormatterError(ErrorConstants.INVALID_INPUT_SIGNABLE_TRANS_EXTRACT_SERIALIZIED_TRANS_FROM_SIGNABLE);
        }

        try {
            String cutChainId = eosTransaction.substring(CHAIN_ID_LENGTH);
            return cutChainId.substring(0, cutChainId.length() - Hex.toHexString(new byte[32]).length());
        } catch (Exception ex) {
            throw new EOSFormatterError(ErrorConstants.EXTRACT_SERIALIZIED_TRANS_FROM_SIGNABLE_ERROR, ex);
        }
    }

    /**
     * Preparing signable transaction for signing.
     * <p/>
     * Signable signature structure:
     * <p/>
     * chainId + serialized transaction + 32 bytes of 0
     *
     * @param serializedTransaction - the serialized transaction to be converted to signable transaction
     * @param chainId - the chain id will be used inside the signature transaction structure.
     * @return - Signable transaction
     * @throws EOSFormatterError if inputs are invalid
     */
    public static String prepareSerializedTransactionForSigning(@NotNull String serializedTransaction,
            @NotNull String chainId) throws EOSFormatterError {
        if (serializedTransaction.isEmpty() || chainId.isEmpty()) {
            throw new EOSFormatterError(ErrorConstants.EMPTY_INPUT_PREPARE_SERIALIZIED_TRANS_FOR_SIGNING);
        }

        String signableTransaction = chainId + serializedTransaction + Hex.toHexString(new byte[32]);
        if (signableTransaction.length() <= MINIMUM_SIGNABLE_TRANSACTION_LENGTH) {
            throw new EOSFormatterError(String.format(ErrorConstants.INVALID_INPUT_SIGNABLE_TRANS_LENGTH_EXTRACT_SERIALIZIED_TRANS_FROM_SIGNABLE, MINIMUM_SIGNABLE_TRANSACTION_LENGTH));
        }

        return signableTransaction;
    }

    /**
     * This method converts a DER encoded private key, public key, or signature into the PEM
     * format.
     *
     * Example of a PEM formatted private key: -----BEGIN EC PRIVATE KEY-----
     * MDECAQEEIEJSCKmyR0kmxy2pgkEwkqrodn2jG9mhXRhhxgsneuBsoAoGCCqGSM49AwEH -----END EC PRIVATE
     * KEY-----
     *
     * The key data between the header and footer is Base64 encoded.
     *
     * @param derEncodedByteArray DER encoded byte array to convert to PEM format
     * @param pemObjectType The type of PEM object being created (i.e. Private Key, Public Key,
     * Signature).
     * @return PEM format as string.
     */
    @NotNull
    private static String derToPEM(@NotNull byte[] derEncodedByteArray,
            @NotNull PEMObjectType pemObjectType) throws DerToPemConversionError {
        StringBuilder pemForm = new StringBuilder();
        try {
            //Build Header
            if (pemObjectType.equals(PEMObjectType.PRIVATEKEY)) {
                pemForm.append(PEM_HEADER_PRIVATE_KEY);
            } else if (pemObjectType.equals(PEMObjectType.PUBLICKEY)) {
                pemForm.append(PEM_HEADER_PUBLIC_KEY);
            } else {
                throw new DerToPemConversionError(ErrorConstants.DER_TO_PEM_CONVERSION);
            }
            pemForm.append("\n");

            //Base64 Encode DER Encoded Byte Array And Add to PEM Object
            String base64EncodedByteArray = DatatypeConverter.printBase64Binary(derEncodedByteArray);
            pemForm.append(base64EncodedByteArray);
            pemForm.append("\n");

            //Build Footer
            if (pemObjectType.equals(PEMObjectType.PRIVATEKEY)) {
                pemForm.append(PEM_FOOTER_PRIVATE_KEY);
            } else if (pemObjectType.equals(PEMObjectType.PUBLICKEY)) {
                pemForm.append(PEM_FOOTER_PUBLIC_KEY);
            } else {
                throw new DerToPemConversionError(ErrorConstants.DER_TO_PEM_CONVERSION);
            }

        } catch (Exception e) {
            throw new DerToPemConversionError(ErrorConstants.DER_TO_PEM_CONVERSION, e);
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
    @NotNull
    private static byte[] decodePrivateKey(@NotNull String strKey, AlgorithmEmployed keyType)
            throws Base58ManipulationError {
        if (strKey.isEmpty()) {
            throw new IllegalArgumentException(ErrorConstants.BASE58_EMPTY_KEY);
        }

        byte[] decodedKey;

        try {
            byte[] base58Decoded = Base58.decode(strKey);
            byte[] firstCheckSum = Arrays
                    .copyOfRange(base58Decoded, base58Decoded.length - CHECKSUM_BYTES,
                            base58Decoded.length);
            decodedKey = Arrays
                    .copyOfRange(base58Decoded, 0, base58Decoded.length - CHECKSUM_BYTES);

            switch (keyType) {
                case SECP256R1:
                    byte[] secp256r1Suffix = SECP256R1_AND_PRIME256V1_CHECKSUM_VALIDATION_SUFFIX
                            .getBytes();
                    if (invalidRipeMD160CheckSum(decodedKey, firstCheckSum, secp256r1Suffix)) {
                        throw new IllegalArgumentException(ErrorConstants.BASE58_INVALID_CHECKSUM);
                    }
                    break;
                case PRIME256V1:
                    byte[] prime256v1Suffix = SECP256R1_AND_PRIME256V1_CHECKSUM_VALIDATION_SUFFIX
                            .getBytes();
                    if (invalidRipeMD160CheckSum(decodedKey, firstCheckSum, prime256v1Suffix)) {
                        throw new IllegalArgumentException(ErrorConstants.BASE58_INVALID_CHECKSUM);
                    }
                    break;
                case SECP256K1:
                    if (invalidSha256x2CheckSum(decodedKey, firstCheckSum)) {
                        throw new IllegalArgumentException(ErrorConstants.BASE58_INVALID_CHECKSUM);
                    }
                    break;
                default:
                    throw new Base58ManipulationError(ErrorConstants.UNSUPPORTED_ALGORITHM);

            }

            // trim 0x80 out if the key size is more than 32 bytes
            // this code apply for key has more than 32 byte and non R1 key
            if (decodedKey.length > STANDARD_KEY_LENGTH && keyType != AlgorithmEmployed.SECP256R1) {
                // Slice out the first byte
                decodedKey = Arrays.copyOfRange(decodedKey, 1, decodedKey.length);
                if (decodedKey.length > STANDARD_KEY_LENGTH
                        && decodedKey[STANDARD_KEY_LENGTH] == ((Integer) 1).byteValue()) {
                    // Slice out last byte
                    decodedKey = Arrays.copyOfRange(decodedKey, 0, decodedKey.length - 1);
                }
            }
        } catch (Exception ex) {
            throw new Base58ManipulationError(ErrorConstants.BASE58_DECODING_ERROR, ex);
        }

        return decodedKey;
    }

    /**
     * Base58 encodes a private key after calculating and appending the checksum.
     *
     * @param pemKey -  Private key as byte[] to encode
     * @param keyType - input key type
     * @return Base58 encoded private key as byte[]
     */
    @NotNull
    public static String encodePrivateKey(@NotNull byte[] pemKey,
            @NotNull AlgorithmEmployed keyType) throws Base58ManipulationError {
        byte[] checkSum;
        String base58Key = "";

        switch (keyType) {
            case SECP256R1:
                checkSum = extractCheckSumRIPEMD160(pemKey,
                        SECP256R1_AND_PRIME256V1_CHECKSUM_VALIDATION_SUFFIX.getBytes());
                break;
            case PRIME256V1:
                checkSum = extractCheckSumRIPEMD160(pemKey,
                        SECP256R1_AND_PRIME256V1_CHECKSUM_VALIDATION_SUFFIX.getBytes());
                break;
            case SECP256K1:
                pemKey = Bytes.concat(new byte[]{((Integer) EOS_SECP256K1_HEADER_BYTE).byteValue()},
                        pemKey);
                checkSum = extractCheckSumSha256x2(pemKey);
                break;
            default:
                throw new Base58ManipulationError(ErrorConstants.CHECKSUM_GENERATION_ERROR);

        }

        base58Key = Base58.encode(Bytes.concat(pemKey, checkSum));

        if (base58Key.isEmpty()) {
            throw new Base58ManipulationError(ErrorConstants.BASE58_ENCODING_ERROR);
        } else {
            return base58Key;
        }

    }

    /**
     * Encoding PEM public key to EOS format.
     *
     * @param pemKey -  PEM key as byte[] to encode
     * @param keyType - Algorithm type used to create key
     * @param isLegacy - If the developer prefers a legacy version of a secp256k1 key that uses an
     * "EOS" prefix.
     * @return - EOS format of public key
     */
    @NotNull
    public static String encodePublicKey(@NotNull byte[] pemKey, @NotNull AlgorithmEmployed keyType,
            boolean isLegacy)
            throws Base58ManipulationError {
        String base58Key = "";
        if (pemKey.length == 0) {
            throw new IllegalArgumentException(ErrorConstants.PUBLIC_KEY_IS_EMPTY);
        }

        try {
            byte[] checkSum;
            switch (keyType) {
                case SECP256K1:
                    if (isLegacy) {
                        checkSum = extractCheckSumRIPEMD160(pemKey,
                                LEGACY_CHECKSUM_VALIDATION_SUFFIX.getBytes());
                    } else {
                        checkSum = extractCheckSumRIPEMD160(pemKey,
                                SECP256K1_CHECKSUM_VALIDATION_SUFFIX.getBytes());
                    }
                    break;
                case SECP256R1:
                    checkSum = extractCheckSumRIPEMD160(pemKey,
                            SECP256R1_AND_PRIME256V1_CHECKSUM_VALIDATION_SUFFIX.getBytes());
                    break;
                default:
                    throw new Base58ManipulationError(ErrorConstants.UNSUPPORTED_ALGORITHM);

            }

            base58Key = Base58.encode(Bytes.concat(pemKey, checkSum));

            if (base58Key.equals("")) {
                throw new Base58ManipulationError(ErrorConstants.BASE58_ENCODING_ERROR);
            }

        } catch (Exception ex) {
            throw new Base58ManipulationError(ErrorConstants.BASE58_ENCODING_ERROR, ex);
        }

        //Add prefix
        StringBuilder builder = new StringBuilder(base58Key);
        switch (keyType) {
            case SECP256K1:
                if (isLegacy) {
                    builder.insert(0, PATTERN_STRING_EOS_PREFIX_EOS);
                } else {
                    builder.insert(0, PATTERN_STRING_EOS_PREFIX_PUB_K1);
                }
                break;
            case SECP256R1:
                builder.insert(0, PATTERN_STRING_EOS_PREFIX_PUB_R1);
                break;
            default:
                break;
        }
        base58Key = builder.toString();

        return base58Key;
    }

    /**
     * Base58 decodes a public key and validates checksum.
     *
     * @param strKey Base58 encoded public key in string format.
     * @param keyPrefix EOS specific key type prefix (i.e. PUB_R1_, PUB_K1_, or EOS).
     * @return Base58 decoded public key as byte[]
     */
    @NotNull
    public static byte[] decodePublicKey(@NotNull String strKey, String keyPrefix)
            throws Base58ManipulationError {
        if (strKey.isEmpty()) {
            throw new IllegalArgumentException("Input key to decode can't be empty.");
        }

        byte[] decodedKey = null;

        try {
            byte[] base58Decoded = Base58.decode(strKey);
            byte[] firstCheckSum = Arrays
                    .copyOfRange(base58Decoded, base58Decoded.length - CHECKSUM_BYTES,
                            base58Decoded.length);
            decodedKey = Arrays
                    .copyOfRange(base58Decoded, 0, base58Decoded.length - CHECKSUM_BYTES);

            switch (keyPrefix) {
                case PATTERN_STRING_EOS_PREFIX_PUB_R1:
                    if (invalidRipeMD160CheckSum(decodedKey, firstCheckSum,
                            SECP256R1_AND_PRIME256V1_CHECKSUM_VALIDATION_SUFFIX.getBytes())) {
                        throw new IllegalArgumentException(
                                ErrorConstants.BASE58_INVALID_CHECKSUM);
                    }
                    break;

                case PATTERN_STRING_EOS_PREFIX_PUB_K1:
                    if (invalidRipeMD160CheckSum(decodedKey, firstCheckSum,
                            SECP256K1_CHECKSUM_VALIDATION_SUFFIX.getBytes())) {
                        throw new IllegalArgumentException(
                                ErrorConstants.BASE58_INVALID_CHECKSUM);
                    }
                    break;

                case PATTERN_STRING_EOS_PREFIX_EOS:
                    if (invalidRipeMD160CheckSum(decodedKey, firstCheckSum,
                            LEGACY_CHECKSUM_VALIDATION_SUFFIX.getBytes())) {
                        throw new IllegalArgumentException(
                                ErrorConstants.BASE58_INVALID_CHECKSUM);
                    }
                    break;

                default:
                    break;
            }

        } catch (Exception ex) {
            throw new Base58ManipulationError(ErrorConstants.BASE58_DECODING_ERROR, ex);
        }

        return decodedKey;
    }

    /**
     * Validate checksum by RipeMD160 digestion
     *
     * @param inputKey - input key to validate
     * @param checkSumToValidate - checksum to validate with the checksum inside input key
     * @param keyTypeByteArray - byte[] of key type used for checksum validation (e.g.
     * "K1".getBytes())
     * @return This checksum returns whether the checksum comparison was invalid.
     */
    private static boolean invalidRipeMD160CheckSum(@NotNull byte[] inputKey,
            @NotNull byte[] checkSumToValidate, @NotNull byte[] keyTypeByteArray) {
        if (inputKey.length == 0 || checkSumToValidate.length == 0
        ) {
            throw new IllegalArgumentException(
                    ErrorConstants.BASE58_EMPTY_CHECKSUM_OR_KEY_OR_KEY_TYPE);
        }

        byte[] keyWithType = Bytes.concat(inputKey, keyTypeByteArray);
        byte[] digestRIPEMD160 = digestRIPEMD160(keyWithType);
        byte[] checkSumFromInputKey = Arrays.copyOfRange(digestRIPEMD160, 0, CHECKSUM_BYTES);

        //This checksum returns whether the checksum comparison was invalid.
        return !Arrays.equals(checkSumToValidate, checkSumFromInputKey);
    }

    /**
     * Validate checksum by double Sha256
     *
     * @param inputKey - input key to validate
     * @param checkSumToValidate - checksum to validate with the checksum inside input key
     * @return This checksum returns whether the checksum comparison was invalid.
     */
    private static boolean invalidSha256x2CheckSum(@NotNull byte[] inputKey,
            @NotNull byte[] checkSumToValidate) {
        if (inputKey.length == 0 || checkSumToValidate.length == 0) {
            throw new IllegalArgumentException(ErrorConstants.BASE58_EMPTY_CHECKSUM_OR_KEY);
        }

        byte[] sha256x2 = Sha256Hash.hashTwice(inputKey);
        byte[] checkSumFromInputKey = Arrays.copyOfRange(sha256x2, 0, CHECKSUM_BYTES);

        //This checksum returns whether the checksum comparison was invalid.
        return !Arrays.equals(checkSumToValidate, checkSumFromInputKey);
    }

    /**
     * Digesting input byte[] to RIPEMD160 format
     *
     * @param input - input byte[]
     * @return RIPEMD160 format
     */
    @NotNull
    private static byte[] digestRIPEMD160(@NotNull byte[] input) {
        RIPEMD160Digest digest = new RIPEMD160Digest();
        byte[] output = new byte[digest.getDigestSize()];
        digest.update(input, 0, input.length);
        digest.doFinal(output, 0);

        return output;
    }

    /**
     * Extracting Checksum for RIPEMD160 digest format
     *
     * @param pemKey - input PEM key
     * @return checksum
     */
    @NotNull
    private static byte[] extractCheckSumRIPEMD160(@NotNull byte[] pemKey,
            byte[] keyTypeByteArray) {
        if (keyTypeByteArray != null) {
            pemKey = Bytes.concat(pemKey, keyTypeByteArray);
        }

        byte[] ripemd160Digest = digestRIPEMD160(pemKey);

        return Arrays.copyOfRange(ripemd160Digest, 0, CHECKSUM_BYTES);
    }

    /**
     * Extracting checksum for Sha256x2 format
     *
     * @param pemKey - input pem key
     * @return checksum
     */
    @NotNull
    private static byte[] extractCheckSumSha256x2(@NotNull byte[] pemKey) {
        byte[] sha256x2 = Sha256Hash.hashTwice(pemKey);

        return Arrays.copyOfRange(sha256x2, 0, CHECKSUM_BYTES);
    }

    /**
     * Decompresses a public key based on the algorithm used to generate it.
     *
     * @param compressedPublicKey Compressed public key as byte[]
     * @param algorithmEmployed Algorithm used during key creation
     * @return Decompressed public key as byte[]
     */
    @NotNull
    private static byte[] decompressPublickey(byte[] compressedPublicKey,
            AlgorithmEmployed algorithmEmployed)
            throws EOSFormatterError {
        try {
            ECParameterSpec parameterSpec = ECNamedCurveTable
                    .getParameterSpec(algorithmEmployed.getString());
            ECPoint ecPoint = parameterSpec.getCurve().decodePoint(compressedPublicKey);
            byte[] x = ecPoint.getXCoord().getEncoded();
            byte[] y = ecPoint.getYCoord().getEncoded();
            if (y.length > STANDARD_KEY_LENGTH) {
                y = Arrays.copyOfRange(y, 1, y.length);
            }
            return Bytes.concat(new byte[]{UNCOMPRESSED_PUBLIC_KEY_BYTE_INDICATOR}, x, y);
        } catch (Exception e) {
            throw new EOSFormatterError(ErrorConstants.PUBLIC_KEY_DECOMPRESSION_ERROR, e);
        }
    }

    /**
     * Compresses a public key based on the algorithm used to generate it.
     *
     * @param compressedPublicKey Decompressed public key as byte[]
     * @param algorithmEmployed Algorithm used during key creation
     * @return Compressed public key as byte[]
     */
    @NotNull
    private static byte[] compressPublickey(byte[] compressedPublicKey,
            AlgorithmEmployed algorithmEmployed)
            throws EOSFormatterError {
        byte compressionPrefix;
        try {
            ECParameterSpec parameterSpec = ECNamedCurveTable
                    .getParameterSpec(algorithmEmployed.getString());
            ECPoint ecPoint = parameterSpec.getCurve().decodePoint(compressedPublicKey);
            byte[] x = ecPoint.getXCoord().getEncoded();
            byte[] y = ecPoint.getYCoord().getEncoded();

            //Check whether y is negative(odd in field) or positive(even in field) and assign compressionPrefix
            BigInteger bigIntegerY = new BigInteger(Hex.toHexString(y), 16);
            BigInteger bigIntegerTwo = BigInteger.valueOf(2);
            BigInteger remainder = bigIntegerY.mod(bigIntegerTwo);

            if (remainder.equals(BigInteger.ZERO)) {
                compressionPrefix = COMPRESSED_PUBLIC_KEY_BYTE_INDICATOR_POSITIVE_Y;
            } else {
                compressionPrefix = COMPRESSED_PUBLIC_KEY_BYTE_INDICATOR_NEGATIVE_Y;
            }

            return Bytes.concat(new byte[]{compressionPrefix}, x);
        } catch (Exception e) {
            throw new EOSFormatterError(ErrorConstants.PUBLIC_KEY_COMPRESSION_ERROR, e);
        }
    }

    /**
     * Takes the S value of an ECDSA DER encoded signature and converts it to a low value.
     *
     * @param s S value from signature
     * @param keyType Algorithm used to generate private key that signed the message.
     * @return Low S value
     */
    private static BigInteger checkAndHandleLowS(BigInteger s, AlgorithmEmployed keyType)
            throws LowSVerificationError {
        if (!isLowS(s, keyType)) {
            switch (keyType) {
                case SECP256R1:
                    return CURVE_R1.getN().subtract(s);

                default:
                    return CURVE_K1.getN().subtract(s);
            }
        }

        return s;
    }

    /**
     * Takes the S value of an ECDSA DER encoded signature and determines whether the value is low.
     *
     * @param s S value from signature
     * @param keyType Algorithm used to generate private key that signed the message.
     * @return boolean indicating whether S value is low
     */
    private static boolean isLowS(BigInteger s, AlgorithmEmployed keyType)
            throws LowSVerificationError {
        int compareResult;

        switch (keyType) {
            case SECP256R1:
                compareResult = s.compareTo(HALF_CURVE_ORDER_R1);
                break;

            case SECP256K1:
                compareResult = s.compareTo(HALF_CURVE_ORDER_K1);
                break;

            default:
                throw new LowSVerificationError(ErrorConstants.UNSUPPORTED_ALGORITHM);
        }

        return compareResult == 0 || compareResult == -1;
    }

    /**
     * Adding checksum to signature
     *
     * @param signature - signature to get checksum added
     */
    private static byte[] addCheckSumToSignature(byte[] signature, byte[] keyTypeByteArray) {
        byte[] signatureWithKeyType = Bytes.concat(signature, keyTypeByteArray);
        byte[] signatureRipemd160 = digestRIPEMD160(signatureWithKeyType);
        byte[] checkSum = Arrays.copyOfRange(signatureRipemd160, 0, CHECKSUM_BYTES);
        return Bytes.concat(signature, checkSum);
    }

    /**
     * Check if the input signature is canonical
     *
     * @param signature - signature to check for canonical
     * @return whether the input signature is canonical
     */
    private static boolean isCanonical(byte[] signature) {
        return (signature[1] & ((Integer) 0x80).byteValue()) == ((Integer) 0x00).byteValue()
                && !(signature[1] == ((Integer) 0x00).byteValue()
                && ((signature[2] & ((Integer) 0x80).byteValue()) == ((Integer) 0x00).byteValue()))
                && (signature[33] & ((Integer) 0x80).byteValue()) == ((Integer) 0x00).byteValue()
                && !(signature[33] == ((Integer) 0x00).byteValue()
                && ((signature[34] & ((Integer) 0x80).byteValue()) == ((Integer) 0x00)
                .byteValue()));
    }

    /**
     * Getting recovery id from R and S
     *
     * @param r - R in DER of Signature
     * @param s - S in DER of Signature
     * @param sha256HashMessage - Sha256Hash of signed message
     * @param publicKey - public key to validate
     * @param keyType - key type
     * @return - Recovery id of the signature. From 0 to 3. Return -1 if find nothing.
     */
    private static int getRecoveryId(BigInteger r, BigInteger s, Sha256Hash sha256HashMessage,
            byte[] publicKey, AlgorithmEmployed keyType) {
        for (int i = 0; i < NUMBER_OF_POSSIBLE_PUBLIC_KEYS; i++) {
            byte[] recoveredPublicKey = recoverPublicKeyFromSignature(i, r, s, sha256HashMessage,
                    true, keyType);

            if (Arrays.equals(publicKey, recoveredPublicKey)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * * Copyright 2011 Google Inc. * Copyright 2014 Andreas Schildbach * Copyright 2014-2016 the
     * libsecp256k1 contributors * * Licensed under the Apache License, Version 2.0 (the "License");
     * * you may not use this file except in compliance with the License. * You may obtain a copy of
     * the License at * *    http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by
     * applicable law or agreed to in writing, software * distributed under the License is
     * distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
     * express or implied. * See the License for the specific language governing permissions and *
     * limitations under the License.
     * <p>
     * The method was modified to match what we need
     *
     * <p>Given the components of a signature and a selector value, recover and return the public
     * key that generated the signature according to the algorithm in SEC1v2 section 4.1.6.</p>
     *
     * <p>The recId is an index from 0 to 3 which indicates which of the 4 possible keys is the
     * correct one. Because the key recovery operation yields multiple potential keys, the correct
     * key must either be stored alongside the signature, or you must be willing to try each recId
     * in turn until you find one that outputs the key you are expecting.</p>
     *
     * <p>If this method returns null it means recovery was not possible and recId should be
     * iterated.</p>
     *
     * <p>Given the above two points, a correct usage of this method is inside a for loop from 0 to
     * 3, and if the output is null OR a key that is not the one you expect, you try again with the
     * next recId.</p>
     *
     * @param recId Which possible key to recover.
     * @param r the R components of the signature, wrapped.
     * @param s the S components of the signature, wrapped.
     * @param message Hash of the data that was signed.
     * @param compressed Whether or not the original pubkey was compressed.
     * @param keyType key type
     * @return An ECKey containing only the public part, or null if recovery wasn't possible.
     */
    private static byte[] recoverPublicKeyFromSignature(int recId, BigInteger r, BigInteger s,
            @NotNull Sha256Hash message, boolean compressed, AlgorithmEmployed keyType) {
        checkArgument(recId >= 0, "recId must be positive");
        checkArgument(r.signum() >= 0, "r must be positive");
        checkArgument(s.signum() >= 0, "s must be positive");

        // 1.0 For j from 0 to h   (h == recId here and the loop is outside this function)
        //   1.1 Let x = r + jn

        BigInteger n; // Curve order.
        ECPoint g;
        ECCurve.Fp curve;

        switch (keyType) {
            case SECP256R1:
                n = ecParamsR1.getN();
                g = ecParamsR1.getG();
                curve = (ECCurve.Fp) ecParamsR1.getCurve();
                break;

            default:
                n = ecParamsK1.getN();
                g = ecParamsK1.getG();
                curve = (ECCurve.Fp) ecParamsK1.getCurve();
                break;
        }

        BigInteger i = BigInteger.valueOf((long) recId / 2);
        BigInteger x = r.add(i.multiply(n));

        //   1.2. Convert the integer x to an octet string X of length mlen using the conversion routine
        //        specified in Section 2.3.7, where mlen = ⌈(log2 p)/8⌉ or mlen = ⌈m/8⌉.
        //   1.3. Convert the octet string (16 set binary digits)||X to an elliptic curve point R using the
        //        conversion routine specified in Section 2.3.4. If this conversion routine outputs “invalid”, then
        //        do another iteration of Step 1.
        //
        // More concisely, what these points mean is to use X as a compressed public key.
        BigInteger prime = curve.getQ();
        if (x.compareTo(prime) >= 0) {
            // Cannot have point co-ordinates larger than this as everything takes place modulo Q.
            return null;
        }
        // Compressed keys require you to know an extra bit of data about the y-coord as there are two possibilities.
        // So it's encoded in the recId.
        ECPoint R = decompressKey(x, (recId & 1) == 1, keyType);
        //   1.4. If nR != point at infinity, then do another iteration of Step 1 (callers responsibility).
        if (!R.multiply(n).isInfinity()) {
            return null;
        }
        //   1.5. Compute e from M using Steps 2 and 3 of ECDSA signature verification.
        BigInteger e = message.toBigInteger();
        //   1.6. For k from 1 to 2 do the following.   (loop is outside this function via iterating recId)
        //   1.6.1. Compute a candidate public key as:
        //               Q = mi(r) * (sR - eG)
        //
        // Where mi(x) is the modular multiplicative inverse. We transform this into the following:
        //               Q = (mi(r) * s ** R) + (mi(r) * -e ** G)
        // Where -e is the modular additive inverse of e, that is z such that z + e = 0 (mod n). In the above equation
        // ** is point multiplication and + is point addition (the EC group operator).
        //
        // We can find the additive inverse by subtracting e from zero then taking the mod. For example the additive
        // inverse of 3 modulo 11 is 8 because 3 + 8 mod 11 = 0, and -3 mod 11 = 8.
        BigInteger eInv = BigInteger.ZERO.subtract(e).mod(n);
        BigInteger rInv = r.modInverse(n);
        BigInteger srInv = rInv.multiply(s).mod(n);
        BigInteger eInvrInv = rInv.multiply(eInv).mod(n);
        ECPoint q = ECAlgorithms.sumOfTwoMultiplies(g, eInvrInv, R, srInv);
        return q.getEncoded(compressed);
    }

    /**
     * * Copyright 2011 Google Inc. * Copyright 2014 Andreas Schildbach * Copyright 2014-2016 the
     * libsecp256k1 contributors * * Licensed under the Apache License, Version 2.0 (the "License");
     * * you may not use this file except in compliance with the License. * You may obtain a copy of
     * the License at * *    http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by
     * applicable law or agreed to in writing, software * distributed under the License is
     * distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
     * express or implied. * See the License for the specific language governing permissions and *
     * limitations under the License.
     * <p>
     * The method was modified to match what we need
     * <p>
     * Decompress a compressed public key (x co-ord and low-bit of y-coord).
     */
    private static ECPoint decompressKey(BigInteger xBN, boolean yBit, AlgorithmEmployed keyType) {
        ECCurve.Fp curve;

        switch (keyType) {
            case SECP256R1:
                curve = (ECCurve.Fp) ecParamsR1.getCurve();
                break;

            default:
                curve = (ECCurve.Fp) ecParamsK1.getCurve();
                break;
        }

        X9IntegerConverter x9 = new X9IntegerConverter();
        byte[] compEnc = x9.integerToBytes(xBN, 1 + x9.getByteLength(curve));
        compEnc[0] = (byte) (yBit ? COMPRESSED_PUBLIC_KEY_BYTE_INDICATOR_NEGATIVE_Y
                : COMPRESSED_PUBLIC_KEY_BYTE_INDICATOR_POSITIVE_Y);
        return curve.decodePoint(compEnc);
    }


}
