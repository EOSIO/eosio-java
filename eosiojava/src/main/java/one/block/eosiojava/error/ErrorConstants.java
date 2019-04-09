

package one.block.eosiojava.error;

public class ErrorConstants {

    private ErrorConstants(){

    }

    //EOSFormatter() Errors
    public static final String INVALID_EOS_PRIVATE_KEY = "The EOS private key provided is invalid!";
    public static final String INVALID_EOS_PUBLIC_KEY = "The EOS public key provided is invalid!";
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
    public static final String PUBLIC_KEY_DECOMPRESSION_ERROR = "Problem decompressing public key!";
    public static final String PUBLIC_KEY_COMPRESSION_ERROR = "Problem compressing public key!";
    public static final String PUBLIC_KEY_IS_EMPTY = "Input key to decode can't be empty!";
    public static final String SIGNATURE_FORMATTING_ERROR = "An error occured formating the signature!";
    public static final String COULD_NOT_RECOVER_PUBLIC_KEY_FROM_SIG = "Could not recover public key from Signature.";
    public static final String NON_CANONICAL_SIGNATURE = "Input signature is not canonical.";

    // ABIProviderImpl Errors
    public static final String NO_RESPONSE_RETRIEVING_ABI = "No response retrieving ABI.";
    public static final String MISSING_ABI_FROM_RESPONSE = "Missing ABI from GetRawAbiResponse.";
    public static final String CALCULATED_HASH_NOT_EQUAL_RETURNED = "Calculated ABI hash does not match returned hash.";
    public static final String REQUESTED_ACCCOUNT_NOT_EQUAL_RETURNED = "Requested account name does not match returned account name.";
    public static final String NO_ABI_FOUND = "No ABI found for requested account name.";
    public static final String ERROR_RETRIEVING_ABI = "Error retrieving ABI from the chain.";

    //PEMProcessor Errors
    public static final String ERROR_READING_PEM_OBJECT = "Error reading PEM object!";
    public static final String ERROR_PARSING_PEM_OBJECT = "Error parsing PEM object!";
    public static final String KEY_DATA_NOT_FOUND = "Key data not found in PEM object!";
    public static final String INVALID_PEM_OBJECT = "Cannot read PEM object!";

}
