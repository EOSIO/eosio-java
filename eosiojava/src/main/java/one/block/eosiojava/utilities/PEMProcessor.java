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
    private Reader reader;

    public PEMProcessor(String pemObject) throws PEMProcessorError {

        try {
            reader = new CharArrayReader(pemObject.toCharArray());
            PemReader pemReader = new PemReader(reader);
            this.pemObject = pemReader.readPemObject();
            if(this.pemObject == null) throw new PEMProcessorError(ErrorConstants.INVALID_PEM_OBJECT);
        } catch (Exception e) {
            throw new PEMProcessorError(ErrorConstants.ERROR_PARSING_PEM_OBJECT, e);
        }

    }

    @NotNull
    public String getType() {
        return pemObject.getType();
    }

    @NotNull
    public String getDERFormat() {
        return Hex.toHexString(pemObject.getContent());
    }

    @NotNull
    public AlgorithmEmployed getAlgorithm() throws PEMProcessorError {
        PEMParser pemParser;
        Object pemObject2 = null;
        try {
            reader.reset();
            pemParser = new PEMParser(reader);
            pemObject2 = pemParser.readObject();
        } catch (IOException e) {
            throw new PEMProcessorError(ErrorConstants.ERROR_READING_PEM_OBJECT, e);
        }

        String oid;
        if(pemObject2 instanceof SubjectPublicKeyInfo){
            oid = ((SubjectPublicKeyInfo) pemObject2).getAlgorithm().getParameters().toString();
        }else if(pemObject2 instanceof PEMKeyPair) {
            oid = ((PEMKeyPair) pemObject2).getPrivateKeyInfo().getPrivateKeyAlgorithm()
                    .getParameters().toString();
        }else{
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

    @NotNull
    public byte[] getKeyData() throws PEMProcessorError {
        try {
            PEMParser pemParser;
            Object pemObject2 = null;

            reader.reset();
            pemParser = new PEMParser(reader);
            pemObject2 = pemParser.readObject();


            if(pemObject2 instanceof SubjectPublicKeyInfo){
                return ((SubjectPublicKeyInfo) pemObject2).getPublicKeyData().getBytes();
            }else if(pemObject2 instanceof PEMKeyPair) {

                DLSequence sequence;
                try (ASN1InputStream asn1InputStream = new ASN1InputStream(
                        Hex.decode(this.getDERFormat()))) {
                    sequence = (DLSequence) asn1InputStream.readObject();
                }
                for(Object obj: sequence){
                    if(obj instanceof DEROctetString){
                        byte[] key = ((DEROctetString) obj).getEncoded();
                        return Arrays.copyOfRange(key,PRIVATE_KEY_START_INDEX, key.length);
                    }
                }
                throw new PEMProcessorError(ErrorConstants.KEY_DATA_NOT_FOUND);

            }else{
                throw new PEMProcessorError(ErrorConstants.DER_TO_PEM_CONVERSION);
            }

        } catch (IOException e) {
            throw new PEMProcessorError(ErrorConstants.ERROR_READING_PEM_OBJECT, e);
        }

    }
}
