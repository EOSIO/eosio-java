

package one.block.eosiojava;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
        String pemFormattedPrivateKey = "-----BEGIN EC PUBLIC KEY-----\n"
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
        String pemFormattedPrivateKey = "-----BEGIN EC PUBLIC KEY-----\n"
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
        String pemFormattedPublicKey = "-----BEGIN EC PUBLIC KEY-----\n" +
                "MDkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDIgACJVBOXmBTBSUedKnkv11sD8ZBHVmJN3aCJEk+5aArDhY=\n" +
                "-----END EC PUBLIC KEY-----";

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
        String pemFormattedPublicKey = "-----BEGIN EC PUBLIC KEY-----\n"
                + "MDYwEAYHKoZIzj0CAQYFK4EEAAoDIgADtDOYTgeoDug9OfOI31ILaoR2OiGmTiKXgyu/3J8VNZ4=\n"
                + "-----END EC PUBLIC KEY-----";

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
        String pemFormattedPublicKey = "-----BEGIN EC PUBLIC KEY-----\n"
                + "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAE8xOUetsCa8EfOlDEBAfREhJqspDoyEh6Szz2in47Tv5n52m9dLYyPCbqZkOB5nTSqtscpkQD/HpykCggvx09iQ==\n"
                + "-----END EC PUBLIC KEY-----";

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
        String pemFormattedPublicKey = "-----BEGIN EC PUBLIC KEY-----\n" +
                "MDkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDIgACJVBOXmBTBSUedKnkv11sD8ZBHVmJN3aCJEk+5aArDhY=\n" +
                "-----END EC PUBLIC KEY-----";

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
        String pemFormattedPublicKey = "-----BEGIN EC PUBLIC KEY-----\n"
                + "MDYwEAYHKoZIzj0CAQYFK4EEAAoDIgADtDOYTgeoDug9OfOI31ILaoR2OiGmTiKXgyu/3J8VNZ4=\n"
                + "-----END EC PUBLIC KEY-----";

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
        String pemFormattedPublicKey = "-----BEGIN EC PUBLIC KEY-----\n"
                + "MDYwEAYHKoZIzj0CAQYFK4EEAAoDIgADtDOYTgeoDug9OfOI31ILaoR2OiGmTiKXgyu/3J8VNZ4=\n"
                + "-----END EC PUBLIC KEY-----";

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
     * Negative test PrepareSerializedTransactionForSigning with invalid length input
     * Expect to get EosFormatError with message at ErrorConstants.INVALID_INPUT_SIGNABLE_TRANS_LENGTH_EXTRACT_SERIALIZIED_TRANS_FROM_SIGNABLE
     */
    @Test
    public void validatePrepareSerializedTransactionForSigning_thenThrowErrorLengthInput() {
        String chainId = "687fa513e18843ad3e820744f4ffc";
        String serializedTransaction = "8BC2A35CF56E6CC25F7F000000000100A6823403EA3055000000572D3CCDCD01000000000000C03400000000A8ED32322A000000000000C034000000000000A682A08601000000000004454F530000000009536F6D657468696E6700";

        try {
            EOSFormatter.prepareSerializedTransactionForSigning(serializedTransaction, chainId);
        } catch (EOSFormatterError eosFormatterError) {
            assertEquals(String.format(ErrorConstants.INVALID_INPUT_SIGNABLE_TRANS_LENGTH_EXTRACT_SERIALIZIED_TRANS_FROM_SIGNABLE, 128), eosFormatterError.getMessage());
        }
    }

    /**
     * Negative test PrepareSerializedTransactionForSigning with empty input
     * Expect to get EosFormatError with message at ErrorConstants.EMPTY_INPUT_PREPARE_SERIALIZIED_TRANS_FOR_SIGNING
     */
    @Test
    public void validatePrepareSerializedTransactionForSigning_thenThrowErrorEmptyInput() {
        String chainId = "";
        String serializedTransaction = "";

        try {
            EOSFormatter.prepareSerializedTransactionForSigning(serializedTransaction, chainId);
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
     * Negative test ExtractSerializedTransactionFromSignable with empty input
     * Expect to get EosFormatError with message at ErrorConstants.EMPTY_INPUT_EXTRACT_SERIALIZIED_TRANS_FROM_SIGNABLE
     */
    @Test
    public void validateExtractSerializedTransactionFromSignable_thenThrowEmptyError() {
        String signableTransaction = "";

        try {
            EOSFormatter.extractSerializedTransactionFromSignable(signableTransaction);
        } catch (EOSFormatterError eosFormatterError) {
            assertEquals(ErrorConstants.EMPTY_INPUT_EXTRACT_SERIALIZIED_TRANS_FROM_SIGNABLE, eosFormatterError.getMessage());
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
        } catch (EOSFormatterError eosFormatterError) {
            assertEquals(String.format(ErrorConstants.INVALID_INPUT_SIGNABLE_TRANS_LENGTH_EXTRACT_SERIALIZIED_TRANS_FROM_SIGNABLE, 128), eosFormatterError.getMessage());
        }
    }

    /**
     * Negative test ExtractSerializedTransactionFromSignable with invalid structure input
     * Expect to get EosFormatError with message at ErrorConstants.INVALID_INPUT_SIGNABLE_TRANS_EXTRACT_SERIALIZIED_TRANS_FROM_SIGNABLE
     */
    @Test
    public void validateExtractSerializedTransactionFromSignable_thenThrowInvalidStructureError() {
        String chainId = "687fa513e18843ad3e820744f4ffcf93b1354036d80737db8dc444fe4b15ad17";
        String expectedSerializedTransaction = "8BC2A35CF56E6CC25F7F000000000100A6823403EA3055000000572D3CCDCD01000000000000C03400000000A8ED32322A000000000000C034000000000000A682A08601000000000004454F530000000009536F6D657468696E6700";
        String signableTransaction = chainId + expectedSerializedTransaction + Hex.toHexString(new byte[31]);

        try {
            EOSFormatter.extractSerializedTransactionFromSignable(signableTransaction);
        } catch (EOSFormatterError eosFormatterError) {
            assertEquals(ErrorConstants.INVALID_INPUT_SIGNABLE_TRANS_EXTRACT_SERIALIZIED_TRANS_FROM_SIGNABLE, eosFormatterError.getMessage());
        }
    }
}
