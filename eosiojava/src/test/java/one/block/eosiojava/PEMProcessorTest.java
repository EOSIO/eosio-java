/*
 * Copyright (c) 2017-2019 block.one all rights reserved.
 */

package one.block.eosiojava;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import one.block.eosiojava.enums.AlgorithmEmployed;
import one.block.eosiojava.error.utilities.EOSFormatterError;
import one.block.eosiojava.error.utilities.PEMProcessorError;
import one.block.eosiojava.utilities.PEMProcessor;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;

public class PEMProcessorTest {

    /*
    Verify that the PEMProcessor is able to return the key type, algorithm used, DER format, and key data for
    an SECP256R1 Public Key PEM encoded object.
     */

    @Test
    public void validatePEMProcessorMethodsWorkWithPEMFormattedPublicKey(){
        String keyType = "PUBLIC KEY";
        AlgorithmEmployed algorithmEmployed = AlgorithmEmployed.SECP256R1;
        String derFormat = "3039301306072a8648ce3d020106082a8648ce3d0301070322000225504e5e605305251e74a9e4bf5d6c0fc6411d598937768224493ee5a02b0e16";
        String keyData = "0225504e5e605305251e74a9e4bf5d6c0fc6411d598937768224493ee5a02b0e16";

        String pemFormattedPublicKey = "-----BEGIN PUBLIC KEY-----\n" +
                "MDkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDIgACJVBOXmBTBSUedKnkv11sD8ZBHVmJN3aCJEk+5aArDhY=\n" +
                "-----END PUBLIC KEY-----";

        try {
            PEMProcessor pemProcessor = new PEMProcessor(pemFormattedPublicKey);
            assertEquals(keyType, pemProcessor.getType());
            assertEquals(algorithmEmployed, pemProcessor.getAlgorithm());
            assertEquals(derFormat, pemProcessor.getDERFormat());
            assertEquals(keyData, Hex.toHexString(pemProcessor.getKeyData()));
        } catch (PEMProcessorError pemProcessorError) {
            fail("Not expecting an PEMProcessorError to be thrown!");
        }

    }

    /*
    Verify that the PEMProcessor is able to return the key type, algorithm used, DER format, and key data for
    an SECP256R1 Private Key PEM encoded object.
     */

    @Test
    public void validatePEMProcessorMethodsWorkWithPEMFormattedSECP256R1PrivateKey(){
        String keyType = "EC PRIVATE KEY";
        AlgorithmEmployed algorithmEmployed = AlgorithmEmployed.SECP256R1;
        String derFormat = "3031020101042058c93ee0f979f8f401d3a894653196d7b9b338c222d0df48b0ed5a3262a3802ba00a06082a8648ce3d030107";
        String keyData = "58c93ee0f979f8f401d3a894653196d7b9b338c222d0df48b0ed5a3262a3802b";


        String pemFormattedPrivateKey = "-----BEGIN EC PRIVATE KEY-----\n"
                + "MDECAQEEIFjJPuD5efj0AdOolGUxlte5szjCItDfSLDtWjJio4AroAoGCCqGSM49AwEH\n"
                + "-----END EC PRIVATE KEY-----";

        try {
            PEMProcessor pemProcessor = new PEMProcessor(pemFormattedPrivateKey);
            assertEquals(keyType, pemProcessor.getType());
            assertEquals(algorithmEmployed, pemProcessor.getAlgorithm());
            assertEquals(derFormat, pemProcessor.getDERFormat());
            assertEquals(keyData, Hex.toHexString(pemProcessor.getKeyData()));

        } catch (PEMProcessorError e) {
            fail("Not expecting an PEMProcessorError to be thrown!");
        }

    }

    /*
    Verify that the PEMProcessor is able to return the key type, algorithm used, DER format, and key data for
    an SECP256K1 Private Key PEM encoded object.
     */

    @Test
    public void validatePEMProcessorMethodsWorkWithPEMFormattedSECP256K1PrivateKey(){
        String keyType = "EC PRIVATE KEY";
        AlgorithmEmployed algorithmEmployed = AlgorithmEmployed.SECP256K1;
        String derFormat = "302e0201010420425208a9b2474926c72da982413092aae8767da31bd9a15d1861c60b277ae06ca00706052b8104000a";
        String keyData = "425208a9b2474926c72da982413092aae8767da31bd9a15d1861c60b277ae06c";

        String pemFormattedPrivateKey = "-----BEGIN EC PRIVATE KEY-----\n"
                + "MC4CAQEEIEJSCKmyR0kmxy2pgkEwkqrodn2jG9mhXRhhxgsneuBsoAcGBSuBBAAK\n"
                + "-----END EC PRIVATE KEY-----";

        try {
            PEMProcessor pemProcessor = new PEMProcessor(pemFormattedPrivateKey);
            assertEquals(keyType, pemProcessor.getType());
            assertEquals(algorithmEmployed, pemProcessor.getAlgorithm());
            assertEquals(derFormat, pemProcessor.getDERFormat());
            assertEquals(keyData, Hex.toHexString(pemProcessor.getKeyData()));

        } catch (PEMProcessorError e) {
            fail("Not expecting an PEMProcessorError to be thrown!");
        }

    }

    /*
    Verify that an invalid PEM object throws an exception.
     */
    @Test
    public void validatePEMProcessorFailsWithInvalidPEMFormattedSECP256K1PrivateKey(){
        String keyType = "EC PRIVATE KEY";
        AlgorithmEmployed algorithmEmployed = AlgorithmEmployed.SECP256K1;
        String derFormat = "302e0201010420425208a9b2474926c72da982413092aae8767da31bd9a15d1861c60b277ae06ca00706052b8104000a";
        String keyData = "425208a9b2474926c72da982413092aae8767da31bd9a15d1861c60b277ae06c";

        String pemFormattedPrivateKey = "MC4CAQEEIEJSCKmyR0kmxy2pgkEwkqrodn2jG9mhXRhhxgsneuBsoAcGBSuBBAAK\n"
                + "-----END EC PRIVATE KEY-----";

        try {
            PEMProcessor pemProcessor = new PEMProcessor(pemFormattedPrivateKey);
            assertEquals(keyType, pemProcessor.getType());
            assertEquals(algorithmEmployed, pemProcessor.getAlgorithm());
            assertEquals(derFormat, pemProcessor.getDERFormat());
            assertEquals(keyData, Hex.toHexString(pemProcessor.getKeyData()));
            fail("Expecting a PEMProcessorError to be thrown!");
        } catch (PEMProcessorError e) {
            assert(e instanceof PEMProcessorError);
        } catch (Exception e){
            fail("Expecting a PEMProcessorError to be thrown!");
        }

    }

}
