/*
 * Copyright (c) 2017-2019 block.one all rights reserved.
 */

package one.block.eosiojava.utilities;

import one.block.eosiojava.enums.AlgorithmEmployed;
import one.block.eosiojava.error.ErrorConstants;
import one.block.eosiojava.error.utilities.Base58ManipulationError;
import one.block.eosiojava.error.utilities.EOSFormatterError;
import one.block.eosiojava.error.utilities.PEMProcessorError;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DLSequence;
import org.bouncycastle.asn1.sec.SECObjectIdentifiers;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.ec.CustomNamedCurves;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.ec.FixedPointCombMultiplier;
import org.bouncycastle.math.ec.FixedPointUtil;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.encoders.Hex;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.jetbrains.annotations.NotNull;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigInteger;

/**
 * This is a wrapper class for PEMObjects that throws a {@link PEMProcessorError} if an invalid
 * PEMObject is passed into the constructor.  Once initialized the PEMProcessor can be used to
 * return the type, DER format, or algorithm used to create the PEMObject.
 */
public class PEMProcessor {

    /**
     * PEM private key type on header
     */
    private static final String PRIVATE_KEY_TYPE = "EC PRIVATE KEY";

    /**
     * Private key start index on ASN.1 sequence
     */
    private static final int PRIVATE_KEY_START_INDEX = 2;

    //region CURVE Constants
    /**
     * Constant name of secp256r1 curves
     */
    private static final String SECP256_R1 = "secp256r1";

    /**
     * Constant name of secp256k1 curves
     */
    private static final String SECP256_K1 = "secp256k1";

    /**
     * EC parameters holder of secp256r1 key type
     */
    private static final X9ECParameters CURVE_PARAMS_R1 = CustomNamedCurves.getByName(SECP256_R1);

    /**
     * EC parameters holder of secp256k1 key type
     */
    private static final X9ECParameters CURVE_PARAMS_K1 = CustomNamedCurves.getByName(SECP256_K1);

    /**
     * EC holder of secp256r1 key type
     */
    private static final ECDomainParameters CURVE_R1;

    /**
     * EC holder of secp256k1 key type
     */
    private static final ECDomainParameters CURVE_K1;


    static {
        // secp256r1
        FixedPointUtil.precompute(CURVE_PARAMS_R1.getG());
        CURVE_R1 = new ECDomainParameters(
                CURVE_PARAMS_R1.getCurve(),
                CURVE_PARAMS_R1.getG(),
                CURVE_PARAMS_R1.getN(),
                CURVE_PARAMS_R1.getH());

        // secp256k1
        CURVE_K1 = new ECDomainParameters(
                CURVE_PARAMS_K1.getCurve(),
                CURVE_PARAMS_K1.getG(),
                CURVE_PARAMS_K1.getN(),
                CURVE_PARAMS_K1.getH());
    }
    //endregion

    private PemObject pemObject;
    private String pemObjectString;

    /**
     * Initialize PEMProcessor with PEM content in String format.
     *
     * @param pemObject - input PEM content in String format.
     * @throws PEMProcessorError When failing to read pem data from the input.
     */
    public PEMProcessor(String pemObject) throws PEMProcessorError {
        this.pemObjectString = pemObject;
        try (Reader reader = new CharArrayReader(this.pemObjectString.toCharArray());
                PemReader pemReader = new PemReader(reader)) {
            this.pemObject = pemReader.readPemObject();
            if (this.pemObject == null) {
                throw new PEMProcessorError(ErrorConstants.INVALID_PEM_OBJECT);
            }

        } catch (Exception e) {
            throw new PEMProcessorError(ErrorConstants.ERROR_PARSING_PEM_OBJECT, e);
        }

    }

    /**
     * Gets the PEM Object key type (i.e. PRIVATE KEY, PUBLIC KEY).
     *
     * @return key type as string
     */
    @NotNull
    public String getType() {
        return pemObject.getType();
    }

    /**
     * Gets the DER encoded format of the key from its PEM format.
     *
     * @return DER format of key as string
     */
    @NotNull
    public String getDERFormat() {
        return Hex.toHexString(pemObject.getContent());
    }

    /**
     * Gets the algorithm used to generate the key from its PEM format.
     *
     * @return The algorithm used to generate the key.
     * @throws PEMProcessorError
     */
    @NotNull
    public AlgorithmEmployed getAlgorithm() throws PEMProcessorError {

        Object pemObjectParsed = parsePEMObject();

        String oid;
        if (pemObjectParsed instanceof SubjectPublicKeyInfo) {
            oid = ((SubjectPublicKeyInfo) pemObjectParsed).getAlgorithm().getParameters()
                    .toString();
        } else if (pemObjectParsed instanceof PEMKeyPair) {
            oid = ((PEMKeyPair) pemObjectParsed).getPrivateKeyInfo().getPrivateKeyAlgorithm()
                    .getParameters().toString();
        } else {
            throw new PEMProcessorError(ErrorConstants.DER_TO_PEM_CONVERSION);
        }

        if (SECObjectIdentifiers.secp256r1.getId().equals(oid)) {
            return AlgorithmEmployed.SECP256R1;
        } else if (SECObjectIdentifiers.secp256k1.getId().equals(oid)) {
            return AlgorithmEmployed.SECP256K1;
        } else {
            throw new PEMProcessorError(ErrorConstants.UNSUPPORTED_ALGORITHM + oid);
        }
    }

    /**
     * Gets the key as a byte array from its PEM format.
     *
     * @return key as byte[]
     * @throws PEMProcessorError
     */
    @NotNull
    public byte[] getKeyData() throws PEMProcessorError {

        Object pemObjectParsed = parsePEMObject();

        if (pemObjectParsed instanceof SubjectPublicKeyInfo) {
            return ((SubjectPublicKeyInfo) pemObjectParsed).getPublicKeyData().getBytes();
        } else if (pemObjectParsed instanceof PEMKeyPair) {

            DLSequence sequence;
            try (ASN1InputStream asn1InputStream = new ASN1InputStream(
                    Hex.decode(this.getDERFormat()))) {
                sequence = (DLSequence) asn1InputStream.readObject();
            } catch (IOException e) {
                throw new PEMProcessorError(e);
            }
            for (Object obj : sequence) {
                if (obj instanceof DEROctetString) {
                    byte[] key = new byte[0];
                    try {
                        key = ((DEROctetString) obj).getEncoded();
                    } catch (IOException e) {
                        throw new PEMProcessorError(e);
                    }
                    return Arrays.copyOfRange(key, PRIVATE_KEY_START_INDEX, key.length);
                }
            }
            throw new PEMProcessorError(ErrorConstants.KEY_DATA_NOT_FOUND);

        } else {
            throw new PEMProcessorError(ErrorConstants.DER_TO_PEM_CONVERSION);
        }
    }

    /**
     * Extract EOS public key
     *
     * @param isLegacy - Set to true if the legacy format of the key is desired.  This uses "EOS"
     * to prefix the key data and only applies to keys generated with the secp256k1 algorithm.  The
     * new format prefixes the key data with "PUB_K1_".
     * @return EOS format public key of the current private key
     * @throws PEMProcessorError
     */
    public String extractEOSPublicKeyFromPrivateKey(boolean isLegacy) throws PEMProcessorError {
        if (!this.getType().equals(PRIVATE_KEY_TYPE)) {
            throw new PEMProcessorError(ErrorConstants.PUBLIC_KEY_COULD_NOT_BE_EXTRACTED_FROM_PRIVATE_KEY);
        }

        AlgorithmEmployed keyCurve = this.getAlgorithm();
        BigInteger privateKeyBI = new BigInteger(this.getKeyData());
        BigInteger n;
        ECPoint g;

        switch (keyCurve) {
            case SECP256R1:
                n = CURVE_R1.getN();
                g = CURVE_R1.getG();
                break;

            default:
                n = CURVE_K1.getN();
                g = CURVE_K1.getG();
                break;
        }

        if (privateKeyBI.bitLength() > n.bitLength()) {
            privateKeyBI = privateKeyBI.mod(n);
        }

        byte[] publicKeyByteArray = new FixedPointCombMultiplier().multiply(g, privateKeyBI).getEncoded(true);

        try {
            return EOSFormatter.encodePublicKey(publicKeyByteArray, keyCurve, isLegacy);
        } catch (Base58ManipulationError e) {
            throw new PEMProcessorError(e);
        }
    }

    /**
     * Extract PEM public key
     *
     * @param isLegacy Whether to return the legacy format of the key.  This uses "EOS"
     * to prefix the key data and only applies to keys generated with the secp256k1 algorithm.  The
     * new format prefixes the key data with "PUB_K1_".
     * @return EOS format public key of the current private key
     */
    public String extractPEMPublicKeyFromPrivateKey(boolean isLegacy) throws PEMProcessorError {
        try {
            return EOSFormatter.convertEOSPublicKeyToPEMFormat(extractEOSPublicKeyFromPrivateKey(isLegacy));
        } catch (EOSFormatterError e) {
            throw new PEMProcessorError(e);
        }
    }

    /**
     * Gets EC Curve's domain parameter by curve type
     *
     * @param curve - type
     * @return ECDomainParameters of input curve
     * @throws PEMProcessorError would be throw if input curve is not supported.
     */
    public static ECDomainParameters getCurveDomainParameters(AlgorithmEmployed curve) throws PEMProcessorError {
        switch (curve) {
            case SECP256R1:
            case PRIME256V1:
                return CURVE_R1;
            case SECP256K1:
                return CURVE_K1;
            default:
                throw new PEMProcessorError(ErrorConstants.UNSUPPORTED_ALGORITHM);
        }
    }

    /**
     * Parses PEM object.
     *
     * @return Parsed PEM object as Object.
     * @throws PEMProcessorError
     */
    @NotNull
    private Object parsePEMObject() throws PEMProcessorError {
        try (Reader reader = new CharArrayReader(this.pemObjectString.toCharArray());
                PEMParser pemParser = new PEMParser(reader);) {
            return pemParser.readObject();
        } catch (IOException e) {
            throw new PEMProcessorError(ErrorConstants.ERROR_READING_PEM_OBJECT, e);
        }
    }
}
