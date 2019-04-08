

package one.block.eosiojava;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.CharArrayReader;
import java.io.Reader;
import one.block.eosiojava.error.ErrorConstants;
import one.block.eosiojava.error.utilities.EOSFormatterError;
import one.block.eosiojava.error.utilities.PEMProcessorError;
import one.block.eosiojava.utilities.EOSFormatter;
import one.block.eosiojava.utilities.PEMProcessor;
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

    //TODO: Change to K1 Test
    @Test
    public void validatePEMProcessor() {
        String derEncodedSignatureI = "303c021c7eeb0b2596f74344b3d7b046ea0bd17c4461fc277658ce93509f1674021c4f5dbfb30d994664da80528847a767f0194876b068e5958161797991";
        //String derEncodedSignatureII = "303e021d0080f20b82d407ae663f010f4990f12073631d653ea1d65dc75ebd4293021d00880db667ef51aea8e7c9bb012496c7c9ece3bc5829b82b692b9211c3";
        //String eosFormattedSignature = "";
        String privateKey = "PVT_R1_g6vV9tiGqN3LkhD53pVUbxDn76PuVeR6XfmJzrnLR3PbGWLys";
        String publicKey = "PUB_R1_6Aze12hAmj1qWeXpdxsbMMP29NZ7EJhnuNJmDoBgx9xjmyZ8n8";
        String signableTransaction = "687fa513e18843ad3e820744f4ffcf93b1354036d80737db8dc444fe4b15ad17528cab5c770a54cebec1000000000100a6823403ea3055000000572d3ccdcd01000000000000c03400000000a8ed323236000000000000c034000000000000a682102700000000000004454f530000000015426f6e757320666f7220676f6f64206a6f62212121000000000000000000000000000000000000000000000000000000000000000000";
        String derEncodedSignature = "304502202b180ef7236a62ff1e3fd741c3d5ba00cf3d3114a7e038a0730a2f45d1551219022100da335c840a4f42c051c12fed3d7d012bb083c7150c0eb691cac9ad9e898a75f3";
        String eosFormattedSignature = "SIG_R1_KaPKLBn1FnnYDf4E5zmnj7qQWWcN5REJFnadzLUyDp7TEVMAmD1CT15SyGmwdreoYTWSbJzWXayPdsHwLySWviiJoA7W4p";

        try {
            String pemPublicKey = EOSFormatter.convertEOSPublicKeyToPEMFormat(publicKey);
            String pemPrivateKey = EOSFormatter.convertEOSPrivateKeyToPEMFormat(privateKey);
            assertEquals(eosFormattedSignature, EOSFormatter.convertDERSignatureToEOSFormat(Hex.decode(derEncodedSignature),Hex.decode(signableTransaction), pemPublicKey ));
        } catch (EOSFormatterError e) {
            fail("Not expecting an EOSFormatterError to be thrown!");
        }

    }


}
