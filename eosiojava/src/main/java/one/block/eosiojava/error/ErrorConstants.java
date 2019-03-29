

package one.block.eosiojava.error;

public class ErrorConstants {

    //EOSFormatter() Errors
    public static final String INVALID_EOS_PRIVATE_KEY = "The EOS private key provided is invalid!";
    public static final String BASE58_DECODING_ERROR = "An error occured while Base58 decoding the EOS key!";
    public static final String BASE58_EMPTY_KEY = "Input key to decode can't be empty!";
    public static final String BASE58_EMPTY_CHECKSUM_OR_KEY = "Input key, checksum and key type to validate can't be empty!";
    public static final String BASE58_EMPTY_CHECKSUM_OR_KEY_OR_KEY_TYPE = "Input key, checksum and key type to validate can't be empty!";
    public static final String BASE58_INVALID_CHECKSUM = "Input key has invalid checksum!";
    public static final String DER_TO_PEM_CONVERSION = "Error converting DER encoded key to PEM format!";
    public static final String UNSUPPORTED_ALGORITHM = "Unsupported algorithm!";
    public static final String INVALID_PEM_PRIVATE_KEY = "This is not a PEM formatted private key!";
    public static final String INVALID_DER_PRIVATE_KEY = "DER format of private key is incorrect!";
    public static final String CHECKSUM_GENERATION_ERROR = "Could not generate checksum!";
    public static final String BASE58_ENCODING_ERROR = "Unable to Base58 encode object!";
}
