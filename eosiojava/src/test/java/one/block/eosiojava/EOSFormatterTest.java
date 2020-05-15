package one.block.eosiojava;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.CharArrayReader;
import java.io.Reader;
import one.block.eosiojava.error.ErrorConstants;
import one.block.eosiojava.error.utilities.EOSFormatterError;
import one.block.eosiojava.utilities.EOSFormatter;
import org.bouncycastle.util.encoders.Hex;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.junit.Test;

public class EOSFormatterTest {

    //SECP256R1 Private Key Test (EOS to PEM)
    @Test
    public void validatePEMCreationOfSecp256r1PrivateKey() {
        String eosFormattedPrivateKey = "PVT_R1_g6vV9tiGqN3LkhD53pVUbxDn76PuVeR6XfmJzrnLR3PbGWLys";
        String pemFormattedPrivateKey = "-----BEGIN EC PRIVATE KEY-----\n"
                + "MDECAQEEIFjJPuD5efj0AdOolGUxlte5szjCItDfSLDtWjJio4AroAoGCCqGSM49AwEH\n"
                + "-----END EC PRIVATE KEY-----";

        try {
            assertEquals(pemFormattedPrivateKey,
                    EOSFormatter.convertEOSPrivateKeyToPEMFormat(eosFormattedPrivateKey));
        } catch (EOSFormatterError e) {
            fail("Not expecting an EOSFormatterError to be thrown!");
        }

    }

    //SECP256R1 Private Key Test (PEM to EOS)
    @Test
    public void validateEOSCreationOfSecp256r1PrivateKey() {
        String eosFormattedPrivateKey = "PVT_R1_g6vV9tiGqN3LkhD53pVUbxDn76PuVeR6XfmJzrnLR3PbGWLys";
        String pemFormattedPrivateKey = "-----BEGIN EC PRIVATE KEY-----\n"
                + "MDECAQEEIFjJPuD5efj0AdOolGUxlte5szjCItDfSLDtWjJio4AroAoGCCqGSM49AwEH\n"
                + "-----END EC PRIVATE KEY-----";

        try {
            assertEquals(eosFormattedPrivateKey,
                    EOSFormatter.convertPEMFormattedPrivateKeyToEOSFormat(pemFormattedPrivateKey));
        } catch (EOSFormatterError e) {
            fail("Not expecting an EOSFormatterError to be thrown!");
        }

    }

    /*
    SECP256R1 Private Key - Roundtrip Test (EOS to PEM to EOS)
    Test uses output of one way conversion as input for return conversion.
     */
    @Test
    public void validateEOStoPEMtoEOSCreationOfSecp256r1PrivateKey() {
        String eosFormattedPrivateKey = "PVT_R1_g6vV9tiGqN3LkhD53pVUbxDn76PuVeR6XfmJzrnLR3PbGWLys";
        String pemFormattedPrivateKey = "-----BEGIN EC PRIVATE KEY-----\n"
                + "MDECAQEEIFjJPuD5efj0AdOolGUxlte5szjCItDfSLDtWjJio4AroAoGCCqGSM49AwEH\n"
                + "-----END EC PRIVATE KEY-----";

        try {
            String eosToPem = EOSFormatter.convertEOSPrivateKeyToPEMFormat(eosFormattedPrivateKey);
            assertEquals(pemFormattedPrivateKey,eosToPem);
            assertEquals(eosFormattedPrivateKey,EOSFormatter.convertPEMFormattedPrivateKeyToEOSFormat(eosToPem));
        } catch (EOSFormatterError e) {
            fail("Not expecting an EOSFormatterError to be thrown!");
        }

    }



    //SECP256R1 Private Key Test (PEM to EOS) - Invalid Header Throws Exception
    @Test
    public void validateExceptionWhenPEMFormatOfSecp256r1PrivateKeyIsInvalidWrongHeader() {
        String eosFormattedPrivateKey = "PVT_R1_g6vV9tiGqN3LkhD53pVUbxDn76PuVeR6XfmJzrnLR3PbGWLys";
        String pemFormattedPrivateKey = "-----BEGIN PUBLIC KEY-----\n"
                + "MDECAQEEIFjJPuD5efj0AdOolGUxlte5szjCItDfSLDtWjJio4AroAoGCCqGSM49AwEH\n"
                + "-----END EC PRIVATE KEY-----";

        try {
            assertEquals(eosFormattedPrivateKey,
                    EOSFormatter.convertPEMFormattedPrivateKeyToEOSFormat(pemFormattedPrivateKey));
            fail("Expected EOSFormatterError to be thrown!");
        } catch (EOSFormatterError e) {
            assert(e instanceof EOSFormatterError);
        }catch (Exception e){
            fail("Expected EOSFormatterError to be thrown!");
        }

    }

    //SECP256R1 Private Key Test (PEM to EOS) - No Header Throws Exception
    @Test
    public void validateExceptionWhenPEMFormatOfSecp256r1PrivateKeyIsInvalidNoHeader() {
        String eosFormattedPrivateKey = "PVT_R1_g6vV9tiGqN3LkhD53pVUbxDn76PuVeR6XfmJzrnLR3PbGWLys";
        String pemFormattedPrivateKey = "MDECAQEEIFjJPuD5efj0AdOolGUxlte5szjCItDfSLDtWjJio4AroAoGCCqGSM49AwEH\n"
                + "-----END EC PRIVATE KEY-----";

        try {
            assertEquals(eosFormattedPrivateKey,
                    EOSFormatter.convertPEMFormattedPrivateKeyToEOSFormat(pemFormattedPrivateKey));
            fail("Expected EOSFormatterError to be thrown!");
        } catch (EOSFormatterError e) {
            assert(e instanceof EOSFormatterError);
        }catch (Exception e){
            fail("Expected EOSFormatterError to be thrown!");
        }

    }



    //Validate PEM structure from SECP256R1 Private Key Test
    @Test
    public void validatePEMStructureOfSecp256r1PrivateKey() {
        String eosFormattedPrivateKey = "PVT_R1_g6vV9tiGqN3LkhD53pVUbxDn76PuVeR6XfmJzrnLR3PbGWLys";
        String pemFormattedPrivateKey = "-----BEGIN EC PRIVATE KEY-----\n"
                + "MDECAQEEIFjJPuD5efj0AdOolGUxlte5szjCItDfSLDtWjJio4AroAoGCCqGSM49AwEH\n"
                + "-----END EC PRIVATE KEY-----";

        try {
            String pemPrivateKey = EOSFormatter
                    .convertEOSPrivateKeyToPEMFormat(eosFormattedPrivateKey);
            Reader reader = new CharArrayReader(pemPrivateKey.toCharArray());
            PemReader pemReader = new PemReader(reader);
            try {
                /*
                Validate that key type in PEM object is 'EC PRIVATE KEY'
                 */
                PemObject pemObject = pemReader.readPemObject();
                String type = pemObject.getType();
                assertEquals(type, "EC PRIVATE KEY");

            } catch (Exception e) {
                throw new EOSFormatterError(e);
            }

        } catch (EOSFormatterError e) {
            fail("Not expecting an EOSFormatterError or other Exception to be thrown!");
        }

    }

    //SECP256K1 Private Key Test (EOS to PEM)
    @Test
    public void validatePEMCreationOfSecp256k1PrivateKey() {
        String eosFormattedPrivateKey = "5JKVeYzRs42DpnHU1rUeJHPZyXb1pCdhyayx7FD2qKHV63F71zU";
        String pemFormattedPrivateKey = "-----BEGIN EC PRIVATE KEY-----\n"
                + "MC4CAQEEIEJSCKmyR0kmxy2pgkEwkqrodn2jG9mhXRhhxgsneuBsoAcGBSuBBAAK\n"
                + "-----END EC PRIVATE KEY-----";

        try {
            assertEquals(pemFormattedPrivateKey,
                    EOSFormatter.convertEOSPrivateKeyToPEMFormat(eosFormattedPrivateKey));
        } catch (EOSFormatterError e) {
            fail("Not expecting an EOSFormatterError to be thrown!");
        }

    }

    //SECP256K1 Private Key Test (PEM to EOS)
    @Test
    public void validateEOSCreationOfSecp256k1PrivateKey() {
        String eosFormattedPrivateKey = "5JKVeYzRs42DpnHU1rUeJHPZyXb1pCdhyayx7FD2qKHV63F71zU";
        String pemFormattedPrivateKey = "-----BEGIN EC PRIVATE KEY-----\n"
                + "MC4CAQEEIEJSCKmyR0kmxy2pgkEwkqrodn2jG9mhXRhhxgsneuBsoAcGBSuBBAAK\n"
                + "-----END EC PRIVATE KEY-----";

        try {
            assertEquals(eosFormattedPrivateKey,
                    EOSFormatter.convertPEMFormattedPrivateKeyToEOSFormat(pemFormattedPrivateKey));
        } catch (EOSFormatterError e) {
            fail("Not expecting an EOSFormatterError to be thrown!");
        }

    }

    /*
SECP256K1 Private Key - Roundtrip Test (EOS to PEM to EOS)
Test uses output of one way conversion as input for return conversion.
 */
    @Test
    public void validateEOStoPEMtoEOSCreationOfSecp256k1PrivateKey() {
        String eosFormattedPrivateKey = "5JKVeYzRs42DpnHU1rUeJHPZyXb1pCdhyayx7FD2qKHV63F71zU";
        String pemFormattedPrivateKey = "-----BEGIN EC PRIVATE KEY-----\n"
                + "MC4CAQEEIEJSCKmyR0kmxy2pgkEwkqrodn2jG9mhXRhhxgsneuBsoAcGBSuBBAAK\n"
                + "-----END EC PRIVATE KEY-----";

        try {
            String eosToPem = EOSFormatter.convertEOSPrivateKeyToPEMFormat(eosFormattedPrivateKey);
            assertEquals(pemFormattedPrivateKey,eosToPem);
            assertEquals(eosFormattedPrivateKey,EOSFormatter.convertPEMFormattedPrivateKeyToEOSFormat(eosToPem));
        } catch (EOSFormatterError e) {
            fail("Not expecting an EOSFormatterError to be thrown!");
        }

    }


    //SECP256K1 Private Key Test (PEM to EOS) - Mixed case header is invalid
    @Test
    public void validateWhetherMixedCaseHeaderOfSecp256k1PrivateKeyIsValid() {
        String eosFormattedPrivateKey = "5JKVeYzRs42DpnHU1rUeJHPZyXb1pCdhyayx7FD2qKHV63F71zU";
        String pemFormattedPrivateKey = "-----Begin EC Private Key-----\n"
                + "MC4CAQEEIEJSCKmyR0kmxy2pgkEwkqrodn2jG9mhXRhhxgsneuBsoAcGBSuBBAAK\n"
                + "-----END EC PRIVATE KEY-----";

        try {
            assertEquals(eosFormattedPrivateKey,
                    EOSFormatter.convertPEMFormattedPrivateKeyToEOSFormat(pemFormattedPrivateKey));
            fail("Expecting an EOSFormatterError to be thrown!");
        } catch (EOSFormatterError e) {
            assert(e instanceof EOSFormatterError);
        } catch (Exception e){
            fail("Expecting an EOSFormatterError to be thrown!");
        }

    }

    //SECP256K1 Private Key Test (PEM to EOS) - 5 dashes in header is required
    @Test
    public void validateWhether4DashesInHeaderOfSecp256k1PrivateKeyIsValid() {
        String eosFormattedPrivateKey = "5JKVeYzRs42DpnHU1rUeJHPZyXb1pCdhyayx7FD2qKHV63F71zU";
        String pemFormattedPrivateKey = "----BEGIN EC PRIVATE KEY----\n"
                + "MC4CAQEEIEJSCKmyR0kmxy2pgkEwkqrodn2jG9mhXRhhxgsneuBsoAcGBSuBBAAK\n"
                + "-----END EC PRIVATE KEY-----";

        try {
            assertEquals(eosFormattedPrivateKey,
                    EOSFormatter.convertPEMFormattedPrivateKeyToEOSFormat(pemFormattedPrivateKey));
            fail("Expecting an EOSFormatterError to be thrown!");
        } catch (EOSFormatterError e) {
            assert(e instanceof EOSFormatterError);
        } catch (Exception e){
            fail("Expecting an EOSFormatterError to be thrown!");
        }

    }

    //SECP256K1 Private Key Test (PEM to EOS) - Key data is required
    @Test
    public void validateWhetherKeyDataForSecp256k1PrivateKeyIsRequired() {
        String eosFormattedPrivateKey = "5JKVeYzRs42DpnHU1rUeJHPZyXb1pCdhyayx7FD2qKHV63F71zU";
        String pemFormattedPrivateKey = "----BEGIN EC PRIVATE KEY----\n"
                + "-----END EC PRIVATE KEY-----";

        try {
            assertEquals(eosFormattedPrivateKey,
                    EOSFormatter.convertPEMFormattedPrivateKeyToEOSFormat(pemFormattedPrivateKey));
            fail("Expecting an EOSFormatterError to be thrown!");
        } catch (EOSFormatterError e) {
            assert(e instanceof EOSFormatterError);
        } catch (Exception e){
            fail("Expecting an EOSFormatterError to be thrown!");
        }

    }

    //SECP256K1 Private Key Test (PEM to EOS) - Invalid Header Throws Exception
    @Test
    public void validateExceptionWhenPEMFormatOfSecp256k1PrivateKeyIsInvalidWrongHeader() {
        String eosFormattedPrivateKey = "5JKVeYzRs42DpnHU1rUeJHPZyXb1pCdhyayx7FD2qKHV63F71zU";
        String pemFormattedPrivateKey = "-----BEGIN PUBLIC KEY-----\n"
                + "MC4CAQEEIEJSCKmyR0kmxy2pgkEwkqrodn2jG9mhXRhhxgsneuBsoAcGBSuBBAAK\n"
                + "-----END EC PRIVATE KEY-----";

        try {
            assertEquals(eosFormattedPrivateKey,
                    EOSFormatter.convertPEMFormattedPrivateKeyToEOSFormat(pemFormattedPrivateKey));
            fail("Expected EOSFormatterError to be thrown!");
        } catch (EOSFormatterError e) {
            assert(e instanceof EOSFormatterError);
        } catch (Exception e){
            fail("Expected EOSFormatterError to be thrown!");
        }

    }

    //SECP256K1 Private Key Test (PEM to EOS) - No Header Throws Exception
    @Test
    public void validateExceptionWhenPEMFormatOfSecp256k1PrivateKeyIsInvalidNoHeader() {
        String eosFormattedPrivateKey = "5JKVeYzRs42DpnHU1rUeJHPZyXb1pCdhyayx7FD2qKHV63F71zU";
        String pemFormattedPrivateKey = "MC4CAQEEIEJSCKmyR0kmxy2pgkEwkqrodn2jG9mhXRhhxgsneuBsoAcGBSuBBAAK\n"
                + "-----END EC PRIVATE KEY-----";

        try {
            assertEquals(eosFormattedPrivateKey,
                    EOSFormatter.convertPEMFormattedPrivateKeyToEOSFormat(pemFormattedPrivateKey));
            fail("Expected EOSFormatterError to be thrown!");
        } catch (EOSFormatterError e) {
            assert(e instanceof EOSFormatterError);
        }catch (Exception e){
            fail("Expected EOSFormatterError to be thrown!");
        }

    }

    //Passing a SECP256K1 Private Key as a SECP256R1 Private Key
    @Test
    public void validatePassingASecp256k1KeyAsSecp256r1Fails() {
        /*
        Using SECP256K1 key from above with private key prefix.
         */
        String eosFormattedPrivateKey = "PVT_R1_5JKVeYzRs42DpnHU1rUeJHPZyXb1pCdhyayx7FD2qKHV63F71zU";
        String pemFormattedPrivateKey = "-----BEGIN EC PRIVATE KEY-----\n"
                + "MC4CAQEEIEJSCKmyR0kmxy2pgkEwkqrodn2jG9mhXRhhxgsneuBsoAcGBSuBBAAK\n"
                + "-----END EC PRIVATE KEY-----";

        try {
            assertEquals(pemFormattedPrivateKey,
                    EOSFormatter.convertEOSPrivateKeyToPEMFormat(eosFormattedPrivateKey));
            fail("Expected EOSFormatterError to be thrown!");
        } catch (EOSFormatterError e) {
            assertEquals(e.getCause().getLocalizedMessage(), ErrorConstants.BASE58_DECODING_ERROR);
            assertEquals(e.getCause().getCause().getLocalizedMessage(),
                    ErrorConstants.BASE58_INVALID_CHECKSUM);
        } catch (Exception e) {
            fail("Expected EOSFormatterError to be thrown!");
        }

    }

    //SECP256K1 Invalid Checksum Private Key Test
    @Test
    public void verifyInvalidChecksumThrowsExpectedError() {
        String eosFormattedPrivateKey = "4JKVeYzRs42DpnHU1rUeJHPZyXb1pCdhyayx7FD2qKHV63F71zU";
        String pemFormattedPrivateKey = "-----BEGIN EC PRIVATE KEY-----\n"
                + "MC4CAQEEIEJSCKmyR0kmxy2pgkEwkqrodn2jG9mhXRhhxgsneuBsoAcGBSuBBAAK\n"
                + "-----END EC PRIVATE KEY-----";

        try {
            assertEquals(pemFormattedPrivateKey,
                    EOSFormatter.convertEOSPrivateKeyToPEMFormat(eosFormattedPrivateKey));
            fail("Expected EOSFormatterError to be thrown!");
        } catch (EOSFormatterError e) {
            assertEquals(e.getCause().getLocalizedMessage(), ErrorConstants.BASE58_DECODING_ERROR);
            assertEquals(e.getCause().getCause().getLocalizedMessage(),
                    ErrorConstants.BASE58_INVALID_CHECKSUM);
        } catch (Exception e) {
            fail("Expected EOSFormatterError to be thrown!");
        }

    }

    //SECP256R1 Public Key Test (EOS to PEM)
    @Test
    public void validatePEMCreationOfSecp256r1PublicKey() {
        String eosFormattedPublicKey = "PUB_R1_5AvUuRssyb7Z2HgNHVofX5heUV5dk8Gni1BGNMzMRCGbhdhBbu";
        String pemFormattedPublicKey = "-----BEGIN PUBLIC KEY-----\n" +
                "MDkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDIgACJVBOXmBTBSUedKnkv11sD8ZBHVmJN3aCJEk+5aArDhY=\n" +
                "-----END PUBLIC KEY-----";

        try {
            assertEquals(pemFormattedPublicKey,
                    EOSFormatter.convertEOSPublicKeyToPEMFormat(eosFormattedPublicKey));
        } catch (EOSFormatterError e) {
            fail("Not expecting an EOSFormatterError to be thrown!");
        }

    }


    //SECP256K1 Public Key Test (EOS to PEM)
    @Test
    public void validatePEMCreationOfSecp256k1PublicKey() {
        String eosFormattedPublicKey = "PUB_K1_8CbY5PhQZGF2gzPKRBaNG4YzB4AwpmfnDcVZMSPZTqQMn1uFhB";
        String pemFormattedPublicKey = "-----BEGIN PUBLIC KEY-----\n"
                + "MDYwEAYHKoZIzj0CAQYFK4EEAAoDIgADtDOYTgeoDug9OfOI31ILaoR2OiGmTiKXgyu/3J8VNZ4=\n"
                + "-----END PUBLIC KEY-----";

        try {
            assertEquals(pemFormattedPublicKey,
                    EOSFormatter.convertEOSPublicKeyToPEMFormat(eosFormattedPublicKey));
        } catch (EOSFormatterError e) {
            fail("Not expecting an EOSFormatterError to be thrown!");
        }

    }

    //SECP256R1 Public Key Test (PEM to EOS)
    @Test
    public void validateEOSCreationOfSecp256r1PublicKey() {
        String eosFormattedPublicKey = "PUB_R1_8gHbKmPN7YXzYjLKguxABYvNqqxtBg8XJQ5M6ebKvupPsqugqj";
        String pemFormattedPublicKey = "-----BEGIN PUBLIC KEY-----\n"
                + "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAE8xOUetsCa8EfOlDEBAfREhJqspDoyEh6Szz2in47Tv5n52m9dLYyPCbqZkOB5nTSqtscpkQD/HpykCggvx09iQ==\n"
                + "-----END PUBLIC KEY-----";

        try {
            assertEquals(eosFormattedPublicKey,
                    EOSFormatter.convertPEMFormattedPublicKeyToEOSFormat(pemFormattedPublicKey, false));
        } catch (EOSFormatterError e) {
            fail("Not expecting an EOSFormatterError to be thrown!");
        }

    }

    //SECP256R1 Public Key Test - Roundtrip (EOS to PEM to EOS)
    @Test
    public void validateEOStoPEMtoEOSConversionOfSecp256r1PublicKey() {
        String eosFormattedPublicKey = "PUB_R1_5AvUuRssyb7Z2HgNHVofX5heUV5dk8Gni1BGNMzMRCGbhdhBbu";
        String pemFormattedPublicKey = "-----BEGIN PUBLIC KEY-----\n" +
                "MDkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDIgACJVBOXmBTBSUedKnkv11sD8ZBHVmJN3aCJEk+5aArDhY=\n" +
                "-----END PUBLIC KEY-----";

        try {
            String eosToPem = EOSFormatter.convertEOSPublicKeyToPEMFormat(eosFormattedPublicKey);
            assertEquals(pemFormattedPublicKey,eosToPem);
            assertEquals(eosFormattedPublicKey,
                    EOSFormatter.convertPEMFormattedPublicKeyToEOSFormat(eosToPem, false));
        } catch (EOSFormatterError e) {
            fail("Not expecting an EOSFormatterError to be thrown!");
        }

    }

    //SECP256K1 Public Key Test - Roundtrip (EOS to PEM to EOS)
    @Test
    public void validateEOStoPEMtoEOSConversionOfSecp256k1PublicKey() {
        String eosFormattedPublicKey = "PUB_K1_8CbY5PhQZGF2gzPKRBaNG4YzB4AwpmfnDcVZMSPZTqQMn1uFhB";
        String pemFormattedPublicKey = "-----BEGIN PUBLIC KEY-----\n"
                + "MDYwEAYHKoZIzj0CAQYFK4EEAAoDIgADtDOYTgeoDug9OfOI31ILaoR2OiGmTiKXgyu/3J8VNZ4=\n"
                + "-----END PUBLIC KEY-----";

        try {
            String eosToPem = EOSFormatter.convertEOSPublicKeyToPEMFormat(eosFormattedPublicKey);
            assertEquals(pemFormattedPublicKey,eosToPem);
            assertEquals(eosFormattedPublicKey,
                    EOSFormatter.convertPEMFormattedPublicKeyToEOSFormat(eosToPem, false));
        } catch (EOSFormatterError e) {
            fail("Not expecting an EOSFormatterError to be thrown!");
        }

    }

    //SECP256K1 Public Key Test - Roundtrip (EOS to PEM to EOS) Legacy SECP256K1
    @Test
    public void validateEOStoPEMtoEOSConversionOfSecp256k1PublicKeyLegacy() {
        String eosFormattedPublicKey = "EOS5AzPqKAx4caCrRSAuyojY6rRKA3KJf4A1MY3paNVqV5eADEVm2";

        try {
            String eosToPem = EOSFormatter.convertEOSPublicKeyToPEMFormat(eosFormattedPublicKey);
            assertEquals(eosFormattedPublicKey,
                    EOSFormatter.convertPEMFormattedPublicKeyToEOSFormat(eosToPem, true));
        } catch (EOSFormatterError e) {
            fail("Not expecting an EOSFormatterError to be thrown!");
        }

    }

    //SECP256K1 Public Key Test - Roundtrip (EOS to PEM to EOS) Invalid EOS throws error
    @Test
    public void validateEOStoPEMtoEOSConversionOfAnInvalidSecp256k1PublicKeyThrowsError() {
        String eosFormattedPublicKey = "8CbY5PhQZGF2gzPKRBaNG4YzB4AwpmfnDcVZMSPZTqQMn1uFhB";
        String pemFormattedPublicKey = "-----BEGIN PUBLIC KEY-----\n"
                + "MDYwEAYHKoZIzj0CAQYFK4EEAAoDIgADtDOYTgeoDug9OfOI31ILaoR2OiGmTiKXgyu/3J8VNZ4=\n"
                + "-----END PUBLIC KEY-----";

        try {
            String eosToPem = EOSFormatter.convertEOSPublicKeyToPEMFormat(eosFormattedPublicKey);
            assertEquals(pemFormattedPublicKey,eosToPem);
            assertEquals(eosFormattedPublicKey,
                    EOSFormatter.convertPEMFormattedPublicKeyToEOSFormat(eosToPem, false));
            fail("Expected EOSFormatterError to be thrown!");
        } catch (EOSFormatterError e) {
            assert(e instanceof EOSFormatterError);
        }catch (Exception e){
            fail("Expected EOSFormatterError to be thrown!");
        }

    }

    /**
     * Validate positive test for PrepareSerializedTransactionForSigning
     */
    @Test
    public void validatePrepareSerializedTransactionForSigning() {
        String chainId = "687fa513e18843ad3e820744f4ffcf93b1354036d80737db8dc444fe4b15ad17";
        String serializedTransaction = "8BC2A35CF56E6CC25F7F000000000100A6823403EA3055000000572D3CCDCD01000000000000C03400000000A8ED32322A000000000000C034000000000000A682A08601000000000004454F530000000009536F6D657468696E6700";
        String expectedSignableTransaction = chainId + serializedTransaction + Hex.toHexString(new byte[32]);

        try {
            String signableTransaction = EOSFormatter.prepareSerializedTransactionForSigning(serializedTransaction, chainId);
            assertEquals(expectedSignableTransaction, signableTransaction);
        } catch (EOSFormatterError eosFormatterError) {
            eosFormatterError.printStackTrace();
            fail("Should not throw exception here");
        }
    }

    /**
     * Validate positive test for PrepareSerializedTransactionForSigning with ContextFreeData
     */
    @Test
    public void validatePrepareSerializedTransactionForSigningWithContextFreeData() {
        String chainId = "687fa513e18843ad3e820744f4ffcf93b1354036d80737db8dc444fe4b15ad17";
        String serializedTransaction = "8BC2A35CF56E6CC25F7F000000000100A6823403EA3055000000572D3CCDCD01000000000000C03400000000A8ED32322A000000000000C034000000000000A682A08601000000000004454F530000000009536F6D657468696E6700";
        String serializedContextFreeData = "c21bfb5ad4b64bfd04838b3b14f0ce0c7b92136cac69bfed41bef92f95a9bb20";
        String expectedSignableTransaction = chainId + serializedTransaction + serializedContextFreeData;

        try {
            String signableTransaction = EOSFormatter.prepareSerializedTransactionForSigning(serializedTransaction, chainId, serializedContextFreeData);
            assertEquals(expectedSignableTransaction, signableTransaction);
        } catch (EOSFormatterError eosFormatterError) {
            eosFormatterError.printStackTrace();
            fail("Should not throw exception here");
        }
    }

    /**
     * Negative test PrepareSerializedTransactionForSigning with invalid length input
     * Expect to get EosFormatError with message at ErrorConstants.INVALID_INPUT_SIGNABLE_TRANS_LENGTH_EXTRACT_SERIALIZIED_TRANS_FROM_SIGNABLE
     */
    @Test
    public void validatePrepareSerializedTransactionForSigning_thenThrowErrorLengthInput() {
        String chainId = "687fa513e18843ad3e820744f4ffcf9";
        String serializedTransaction = "8";
        String contextFreeData = "4";

        try {
            EOSFormatter.prepareSerializedTransactionForSigning(serializedTransaction, chainId, contextFreeData);
            fail("Expected EOSFormatterError to be thrown!");
        } catch (EOSFormatterError eosFormatterError) {
            assertEquals(String.format(ErrorConstants.INVALID_INPUT_SIGNABLE_TRANS_LENGTH_EXTRACT_SERIALIZIED_TRANS_FROM_SIGNABLE, 129), eosFormatterError.getMessage());
        }
    }

    /**
     * Negative test PrepareSerializedTransactionForSigning with empty chainId
     * Expect to get EosFormatError with message at ErrorConstants.EMPTY_INPUT_PREPARE_SERIALIZIED_TRANS_FOR_SIGNING
     */
    @Test
    public void validatePrepareSerializedTransactionForSigningWithEmptyChainId_thenThrowErrorEmptyInput() {
        String chainId = "";
        String serializedTransaction = "someValue";
        String contextFreeData = "someValue";

        try {
            EOSFormatter.prepareSerializedTransactionForSigning(serializedTransaction, chainId, contextFreeData);
            fail("Expected EOSFormatterError to be thrown!");
        } catch (EOSFormatterError eosFormatterError) {
            assertEquals(ErrorConstants.EMPTY_INPUT_PREPARE_SERIALIZIED_TRANS_FOR_SIGNING, eosFormatterError.getMessage());
        }
    }

    /**
     * Negative test PrepareSerializedTransactionForSigning with empty serializedTransaction
     * Expect to get EosFormatError with message at ErrorConstants.EMPTY_INPUT_PREPARE_SERIALIZIED_TRANS_FOR_SIGNING
     */
    @Test
    public void validatePrepareSerializedTransactionForSigningWithEmptySerializedTransaction_thenThrowErrorEmptyInput() {
        String chainId = "someValue";
        String serializedTransaction = "";
        String contextFreeData = "someValue";

        try {
            EOSFormatter.prepareSerializedTransactionForSigning(serializedTransaction, chainId, contextFreeData);
            fail("Expected EOSFormatterError to be thrown!");
        } catch (EOSFormatterError eosFormatterError) {
            assertEquals(ErrorConstants.EMPTY_INPUT_PREPARE_SERIALIZIED_TRANS_FOR_SIGNING, eosFormatterError.getMessage());
        }
    }

    /**
     * Negative test PrepareSerializedTransactionForSigning with ContextFreeData with empty contextFreeData
     * Expect to get EosFormatError with message at ErrorConstants.EMPTY_INPUT_PREPARE_SERIALIZIED_TRANS_FOR_SIGNING
     */
    @Test
    public void validatePrepareSerializedTransactionForSigningWithContextFreeData_thenThrowErrorEmptyInput() {
        String chainId = "someValue";
        String serializedTransaction = "someValue";
        String contextFreeData = "";

        try {
            EOSFormatter.prepareSerializedTransactionForSigning(serializedTransaction, chainId, contextFreeData);
            fail("Expected EOSFormatterError to be thrown!");
        } catch (EOSFormatterError eosFormatterError) {
            assertEquals(ErrorConstants.EMPTY_INPUT_PREPARE_SERIALIZIED_TRANS_FOR_SIGNING, eosFormatterError.getMessage());
        }
    }

    /**
     * Validate positive ExtractSerializedTransactionFromSignable
     */
    @Test
    public void validateExtractSerializedTransactionFromSignable() {
        String chainId = "687fa513e18843ad3e820744f4ffcf93b1354036d80737db8dc444fe4b15ad17";
        String expectedSerializedTransaction = "8BC2A35CF56E6CC25F7F000000000100A6823403EA3055000000572D3CCDCD01000000000000C03400000000A8ED32322A000000000000C034000000000000A682A08601000000000004454F530000000009536F6D657468696E6700";
        String signableTransaction = chainId + expectedSerializedTransaction + Hex.toHexString(new byte[32]);

        try {
            String serializedTransaction = EOSFormatter.extractSerializedTransactionFromSignable(signableTransaction);
            assertEquals(expectedSerializedTransaction, serializedTransaction);
        } catch (EOSFormatterError eosFormatterError) {
            eosFormatterError.printStackTrace();
            fail("Should not throw exception here");
        }
    }

    /**
     * Validate positive ExtractSerializedTransactionFromSignable with Context Free Data
     */
    @Test
    public void validateExtractSerializedTransactionFromSignableWithContextFreeData() {
        String chainId = "687fa513e18843ad3e820744f4ffcf93b1354036d80737db8dc444fe4b15ad17";
        String expectedSerializedTransaction = "8BC2A35CF56E6CC25F7F000000000100A6823403EA3055000000572D3CCDCD01000000000000C03400000000A8ED32322A000000000000C034000000000000A682A08601000000000004454F530000000009536F6D657468696E6700";
        String contextFreeData = "6595140530fcbd94469196e5e6d73af65693910df8fcf5d3088c3616bff5ee9f";
        String signableTransaction = chainId + expectedSerializedTransaction + contextFreeData;

        try {
            String serializedTransaction = EOSFormatter.extractSerializedTransactionFromSignable(signableTransaction);
            assertEquals(expectedSerializedTransaction, serializedTransaction);
        } catch (EOSFormatterError eosFormatterError) {
            eosFormatterError.printStackTrace();
            fail("Should not throw exception here");
        }
    }

    /**
     * Negative test ExtractSerializedTransactionFromSignable with empty input
     * Expect to get EosFormatError with message at ErrorConstants.EMPTY_INPUT_EXTRACT_SERIALIZIED_TRANS_FROM_SIGNABLE
     */
    @Test
    public void validateExtractSerializedTransactionFromSignable_thenThrowEmptyError() {
        String signableTransaction = "";

        try {
            EOSFormatter.extractSerializedTransactionFromSignable(signableTransaction);
            fail("Expected EOSFormatterError to be thrown!");
        } catch (EOSFormatterError eosFormatterError) {
            assertEquals(ErrorConstants.EMPTY_INPUT_EXTRACT_SERIALIZIED_TRANS_FROM_SIGNABLE, eosFormatterError.getMessage());
        }
    }

    /*
    Validate signature using a signable transaction and public key used to sign the transaction.
    Convert the signature to EOS format.
     */

    @Test
    public void validateEOSSignatureCreationWithSECP256R1GeneratedPublicKey() {
        String publicKey = "PUB_R1_6Aze12hAmj1qWeXpdxsbMMP29NZ7EJhnuNJmDoBgx9xjmyZ8n8";
        String signableTransaction = "687fa513e18843ad3e820744f4ffcf93b1354036d80737db8dc444fe4b15ad17528cab5c770a54cebec1000000000100a6823403ea3055000000572d3ccdcd01000000000000c03400000000a8ed323236000000000000c034000000000000a682102700000000000004454f530000000015426f6e757320666f7220676f6f64206a6f62212121000000000000000000000000000000000000000000000000000000000000000000";
        String derEncodedSignature = "304502202b180ef7236a62ff1e3fd741c3d5ba00cf3d3114a7e038a0730a2f45d1551219022100da335c840a4f42c051c12fed3d7d012bb083c7150c0eb691cac9ad9e898a75f3";
        String eosFormattedSignature = "SIG_R1_KaPKLBn1FnnYDf4E5zmnj7qQWWcN5REJFnadzLUyDp7TEVMAmD1CT15SyGmwdreoYTWSbJzWXayPdsHwLySWviiJoA7W4p";

        try {
            String pemPublicKey = EOSFormatter.convertEOSPublicKeyToPEMFormat(publicKey);
            assertEquals(eosFormattedSignature, EOSFormatter.convertDERSignatureToEOSFormat(Hex.decode(derEncodedSignature),Hex.decode(signableTransaction), pemPublicKey ));
        } catch (EOSFormatterError e) {
            fail("Not expecting an EOSFormatterError to be thrown!");
        }

    }

    /*
    Fail while validating signature using a signable transaction and the wrong public key used to
    sign the transaction.
     */

    @Test
    public void validateEOSSignatureCreationWithWrongPublicKey() {
        String publicKey = "PUB_R1_5AvUuRssyb7Z2HgNHVofX5heUV5dk8Gni1BGNMzMRCGbhdhBbu";
        String signableTransaction = "687fa513e18843ad3e820744f4ffcf93b1354036d80737db8dc444fe4b15ad17528cab5c770a54cebec1000000000100a6823403ea3055000000572d3ccdcd01000000000000c03400000000a8ed323236000000000000c034000000000000a682102700000000000004454f530000000015426f6e757320666f7220676f6f64206a6f62212121000000000000000000000000000000000000000000000000000000000000000000";
        String derEncodedSignature = "304502202b180ef7236a62ff1e3fd741c3d5ba00cf3d3114a7e038a0730a2f45d1551219022100da335c840a4f42c051c12fed3d7d012bb083c7150c0eb691cac9ad9e898a75f3";
        String eosFormattedSignature = "SIG_R1_KaPKLBn1FnnYDf4E5zmnj7qQWWcN5REJFnadzLUyDp7TEVMAmD1CT15SyGmwdreoYTWSbJzWXayPdsHwLySWviiJoA7W4p";

        try {
            String pemPublicKey = EOSFormatter.convertEOSPublicKeyToPEMFormat(publicKey);
            assertEquals(eosFormattedSignature, EOSFormatter.convertDERSignatureToEOSFormat(Hex.decode(derEncodedSignature),Hex.decode(signableTransaction), pemPublicKey ));
            fail("Expected EOSFormatterError to be thrown!");
        }catch (EOSFormatterError e) {
            assert(e instanceof EOSFormatterError);
            assertEquals("Could not recover public key from Signature.", e.getCause().getMessage());
        }catch (Exception e){
            fail("Expected EOSFormatterError to be thrown!");
        }
    }

    /*
    Validate signature using a r and s, signable transaction, and public key used to sign the transaction.
    Convert the signature to EOS format.
     */

    @Test
    public void validateEOSSignatureCreationWithSECP256K1GeneratedPublicKey() {
        String publicKey = "-----BEGIN PUBLIC KEY-----\nMDYwEAYHKoZIzj0CAQYFK4EEAAoDIgADtDOYTgeoDug9OfOI31ILaoR2OiGmTiKXgyu/3J8VNZ4=\n-----END PUBLIC KEY-----";
        String signableTransaction = "687fa513e18843ad3e820744f4ffcf93b1354036d80737db8dc444fe4b15ad1714b9ab5cfb639ca4996b000000000100a6823403ea3055000000572d3ccdcd01000000000000c03400000000a8ed323225000000000000c034000000000000a682e80300000000000004454f5300000000046d656d6f000000000000000000000000000000000000000000000000000000000000000000";
        String rValue = "44170566286458861279997966394284345283382417819099318029887909824085364215455";
        String sValue = "5239695698990405032795389787700069207189254668737611240009161346294735197523";
        String eosFormattedSignature = "SIG_K1_KhXKyTi1h2D3LiX8bp4bhfjNQSaF61keisC6VcoHP16jbtHE4sAAzPTXHVAn3hPLUcvnMgG9bf5bZPvmpHBmQCA83VSz6Z";

        try {
            assertEquals(eosFormattedSignature, EOSFormatter.convertRawRandSofSignatureToEOSFormat(rValue, sValue,Hex.decode(signableTransaction), publicKey ));
        } catch (EOSFormatterError e) {
            fail("Not expecting an EOSFormatterError to be thrown!");
        }

    }

    /*
    Verify failed validation of signature using a incorrect r and s, valid signable transaction, and valid public key used to sign the transaction.
    Changed r and s values slightly from positive test.
     */

    @Test
    public void eosSECP256K1SignatureValidationFailsWithIncorrectRorSvalue() {
        String publicKey = "-----BEGIN PUBLIC KEY-----\nMDYwEAYHKoZIzj0CAQYFK4EEAAoDIgADtDOYTgeoDug9OfOI31ILaoR2OiGmTiKXgyu/3J8VNZ4=\n-----END PUBLIC KEY-----";
        String signableTransaction = "687fa513e18843ad3e820744f4ffcf93b1354036d80737db8dc444fe4b15ad1714b9ab5cfb639ca4996b000000000100a6823403ea3055000000572d3ccdcd01000000000000c03400000000a8ed323225000000000000c034000000000000a682e80300000000000004454f5300000000046d656d6f000000000000000000000000000000000000000000000000000000000000000000";
        String rValue = "44170566286458861279997966394284345283382417819099318029887909824085364215450";
        String sValue = "5239695698990405032795389787700069207189254668737611240009161346294735197520";
        String eosFormattedSignature = "SIG_K1_KhXKyTi1h2D3LiX8bp4bhfjNQSaF61keisC6VcoHP16jbtHE4sAAzPTXHVAn3hPLUcvnMgG9bf5bZPvmpHBmQCA83VSz6Z";

        try {
            assertEquals(eosFormattedSignature, EOSFormatter.convertRawRandSofSignatureToEOSFormat(rValue, sValue,Hex.decode(signableTransaction), publicKey ));
            fail("Expected EOSFormatterError to be thrown!");
        } catch (EOSFormatterError e) {
            assert(e instanceof EOSFormatterError);
            assertEquals("Invalid point compression", e.getCause().getMessage());
        }catch (Exception e){
            fail("Expected EOSFormatterError to be thrown!");
        }

    }


    /**
     * Negative test ExtractSerializedTransactionFromSignable with invalid length input
     * Expect to get EosFormatError with message at ErrorConstants.INVALID_INPUT_SIGNABLE_TRANS_LENGTH_EXTRACT_SERIALIZIED_TRANS_FROM_SIGNABLE
     */
    @Test
    public void validateExtractSerializedTransactionFromSignable_thenThrowLengthError() {
        String signableTransaction = "8BC2A35CF56E6CC25F7F000000000100A6823403EA30550000";

        try {
            EOSFormatter.extractSerializedTransactionFromSignable(signableTransaction);
            fail("Expected EOSFormatterError to be thrown!");
        } catch (EOSFormatterError eosFormatterError) {
            assertEquals(String.format(ErrorConstants.INVALID_INPUT_SIGNABLE_TRANS_LENGTH_EXTRACT_SERIALIZIED_TRANS_FROM_SIGNABLE, 129), eosFormatterError.getMessage());
        }
    }
}
