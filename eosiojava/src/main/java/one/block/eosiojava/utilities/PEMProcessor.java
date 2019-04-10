/*
 * Copyright (c) 2017-2019 block.one all rights reserved.
 */

package one.block.eosiojava.utilities;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;
import one.block.eosiojava.enums.AlgorithmEmployed;
import one.block.eosiojava.error.ErrorConstants;
import one.block.eosiojava.error.utilities.PEMProcessorError;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DLSequence;
import org.bouncycastle.asn1.sec.SECObjectIdentifiers;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.encoders.Hex;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.jetbrains.annotations.NotNull;

/**
 * This is a wrapper class for PEMObjects that throws a {@link PEMProcessorError} if an invalid
 * PEMObject is passed into the constructor.  Once initialized the PEMProcessor can be used to
 * return the type, DER format, or alogorithm used to create the PEMObject.
 */
public class PEMProcessor {

    private static final int PRIVATE_KEY_START_INDEX = 2;

    private PemObject pemObject;
    private String pemObjectString;

    public PEMProcessor(String pemObject) throws PEMProcessorError {
        this.pemObjectString = pemObject;
        try (Reader reader = new CharArrayReader(this.pemObjectString.toCharArray());
                PemReader pemReader = new PemReader(reader);) {
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
     * @throws {@link PEMProcessorError}
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
     * @throws {@link PEMProcessorError}
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
     * Parses PEM object.
     *
     * @return Parsed PEM object as Object.
     * @throws {@link PEMProcessorError}
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
