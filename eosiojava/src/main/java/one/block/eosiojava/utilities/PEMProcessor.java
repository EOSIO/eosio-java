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
import org.bouncycastle.asn1.sec.SECObjectIdentifiers;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
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

    private PemObject pemObject;
    private PemReader pemReader;
    private Reader reader;

    public PEMProcessor(String pemObject) throws PEMProcessorError, IOException {

        try {
            reader = new CharArrayReader(pemObject.toCharArray());
            pemReader = new PemReader(reader);
            this.pemObject = pemReader.readPemObject();
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
    public AlgorithmEmployed getAlgorithm() throws PEMProcessorError, IOException {
        reader.reset();
        PEMParser pemParser = new PEMParser(reader);
        Object pemObject2 = null;
        try {
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
    public byte[] getKeyData() throws PEMProcessorError, IOException {
        reader.reset();
        PEMParser pemParser = new PEMParser(reader);
        Object pemObject2 = null;
        try {
            pemObject2 = pemParser.readObject();
        } catch (IOException e) {
            throw new PEMProcessorError(ErrorConstants.ERROR_READING_PEM_OBJECT, e);
        }

        if(pemObject2 instanceof SubjectPublicKeyInfo){
            return ((SubjectPublicKeyInfo) pemObject2).getPublicKeyData().getBytes();
        }else if(pemObject2 instanceof PEMKeyPair) {
            return ((PEMKeyPair) pemObject2).getPrivateKeyInfo().getPublicKeyData().getBytes();
        }else{
            throw new PEMProcessorError(ErrorConstants.DER_TO_PEM_CONVERSION);
        }


    }
}
