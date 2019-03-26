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

package one.block.eosiojava;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.CharArrayReader;
import java.io.Reader;
import one.block.eosiojava.error.ErrorConstants;
import one.block.eosiojava.error.utilities.EOSFormatterError;
import one.block.eosiojava.utilities.EOSFormatter;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.junit.Test;

public class EOSFormatterTest {

    //SECP256R1 Private Key Test
    @Test
    public void validatePEMCreationOfSecp256r1PrivateKey() {
        String eosFormattedPrivateKey = "PVT_R1_g6vV9tiGqN3LkhD53pVUbxDn76PuVeR6XfmJzrnLR3PbGWLys";
        String pemFormattedPrivateKey = "-----BEGIN EC PRIVATE KEY-----\n"
                + "MDECAQEEIFjJPuD5efj0AdOolGUxlte5szjCItDfSLDtWjJio4AroAoGCCqGSM49AwEH\n"
                + "-----END EC PRIVATE KEY-----";

        try{
            assertEquals(pemFormattedPrivateKey, EOSFormatter.convertEOSPrivateKeyToPEMFormat(eosFormattedPrivateKey));
        }catch (EOSFormatterError e){
            fail("Not expecting an EOSFormatterError to be thrown!");
        }


    }

    //Validate PEM structure from SECP256R1 Private Key Test
    @Test
    public void validatePEMStructureOfSecp256r1PrivateKey() {
        String eosFormattedPrivateKey = "PVT_R1_g6vV9tiGqN3LkhD53pVUbxDn76PuVeR6XfmJzrnLR3PbGWLys";
        String pemFormattedPrivateKey = "-----BEGIN EC PRIVATE KEY-----\n"
                + "MDECAQEEIFjJPuD5efj0AdOolGUxlte5szjCItDfSLDtWjJio4AroAoGCCqGSM49AwEH\n"
                + "-----END EC PRIVATE KEY-----";

        try{
            String pemPrivateKey = EOSFormatter.convertEOSPrivateKeyToPEMFormat(eosFormattedPrivateKey);
            Reader reader = new CharArrayReader(pemPrivateKey.toCharArray());
            PemReader pemReader = new PemReader(reader);
            try {
                /*
                Validate that key type in PEM object is 'EC PRIVATE KEY'
                 */
                PemObject pemObject = pemReader.readPemObject();
                String type = pemObject.getType();
                assertEquals(type, "EC PRIVATE KEY");

            }catch (Exception e){
                throw new EOSFormatterError(e);
            }

        }catch (EOSFormatterError e){
            fail("Not expecting an EOSFormatterError or other Exception to be thrown!");
        }


    }


    //SECP256K1 Private Key Test
    @Test
    public void validatePEMCreationOfSecp256k1PrivateKey() {
        String eosFormattedPrivateKey = "5JKVeYzRs42DpnHU1rUeJHPZyXb1pCdhyayx7FD2qKHV63F71zU";
        String pemFormattedPrivateKey = "-----BEGIN EC PRIVATE KEY-----\n"
                + "MC4CAQEEIEJSCKmyR0kmxy2pgkEwkqrodn2jG9mhXRhhxgsneuBsoAcGBSuBBAAK\n"
                + "-----END EC PRIVATE KEY-----";

        try{
            assertEquals(pemFormattedPrivateKey, EOSFormatter.convertEOSPrivateKeyToPEMFormat(eosFormattedPrivateKey));
        }catch (EOSFormatterError e){
            fail("Not expecting an EOSFormatterError to be thrown!");
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

        try{
            assertEquals(pemFormattedPrivateKey, EOSFormatter.convertEOSPrivateKeyToPEMFormat(eosFormattedPrivateKey));
            fail("Expected EOSFormatterError to be thrown!");
        }catch (EOSFormatterError e){
            assertEquals(e.getCause().getLocalizedMessage(),ErrorConstants.BASE58_DECODING_ERROR);
            assertEquals(e.getCause().getCause().getLocalizedMessage(),ErrorConstants.BASE58_INVALID_CHECKSUM);
        }catch (Exception e){
            fail("Expected EOSFormatterError to be thrown!");
        }


    }

    //SECP256K1 Invalid Checksum Private Key Test
    @Test
    public void verifyInvalidChecksumThrowsExpectedError(){
        String eosFormattedPrivateKey = "4JKVeYzRs42DpnHU1rUeJHPZyXb1pCdhyayx7FD2qKHV63F71zU";
        String pemFormattedPrivateKey = "-----BEGIN EC PRIVATE KEY-----\n"
                + "MC4CAQEEIEJSCKmyR0kmxy2pgkEwkqrodn2jG9mhXRhhxgsneuBsoAcGBSuBBAAK\n"
                + "-----END EC PRIVATE KEY-----";

        try{
            assertEquals(pemFormattedPrivateKey, EOSFormatter.convertEOSPrivateKeyToPEMFormat(eosFormattedPrivateKey));
            fail("Expected EOSFormatterError to be thrown!");
        }catch (EOSFormatterError e){
            assertEquals(e.getCause().getLocalizedMessage(),ErrorConstants.BASE58_DECODING_ERROR);
            assertEquals(e.getCause().getCause().getLocalizedMessage(),ErrorConstants.BASE58_INVALID_CHECKSUM);
        }catch (Exception e){
            fail("Expected EOSFormatterError to be thrown!");
        }

    }

}
