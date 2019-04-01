

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
    public static final String TRANSACTION_PROCESSOR_ACTIONS_EMPTY_ERROR_MSG = "Action list can't be empty!";
    public static final String TRANSACTION_PROCESSOR_RPC_GET_INFO = "Error happened on calling GetInfo RPC.";
    public static final String TRANSACTION_PROCESSOR_PREPARE_RPC_GET_BLOCK = "Error happened on calling GetBlock RPC.";
    public static final String TRANSACTION_PROCESSOR_HEAD_BLOCK_TIME_PARSE_ERROR = "Failed to parse head block time";
    public static final String TRANSACTION_PROCESSOR_HEAD_BLOCK_TIME_PARSE_NULL_ERROR = "NullPointerException happened on parsing head block time";
    public static final String TRANSACTION_PROCESSOR_PREPARE_CLONE_ERROR = "Error happened on cloning transaction.";
    public static final String TRANSACTION_PROCESSOR_PREPARE_CLONE_CLASS_NOT_FOUND = "Transaction class was not found";
    public static final String TRANSACTION_PROCESSOR_TRANSACTION_HAS_TO_BE_INITIALIZED = "Transaction must be initialized before this method could be called! call prepare for initialize Transaction";
    public static final String TRANSACTION_PROCESSOR_GET_ABI_ERROR = "Error happened on getting abi for contract [%s]";
    public static final String TRANSACTION_PROCESSOR_SERIALIZE_ACTION_WORKED_BUT_EMPTY_RESULT = "Serialization of action worked fine but got back empty result!";
    public static final String TRANSACTION_PROCESSOR_SERIALIZE_TRANSACTION_WORKED_BUT_EMPTY_RESULT = "Serialization of transaction worked fine but got back empty result!";
    public static final String TRANSACTION_PROCESSOR_SERIALIZE_ACTION_ERROR = "Error happened on serializing action [%s]";
    public static final String TRANSACTION_PROCESSOR_SERIALIZE_TRANSACTION_ERROR = "Error happened on serializing transaction";
    public static final String TRANSACTION_PROCESSOR_GET_AVAILABLE_KEY_ERROR = "Error happened on getAvailableKeys from SignatureProvider!";
    public static final String TRANSACTION_PROCESSOR_GET_AVAILABLE_KEY_EMPTY = "Signature provider return no available key";
    public static final String TRANSACTION_PROCESSOR_RPC_GET_REQUIRED_KEYS = "Error happened on calling getRequiredKeys RPC call.";
    public static final String GET_REQUIRED_KEY_RPC_EMPTY_RESULT = "GetRequiredKeys RPC returned no required keys";
    public static final String TRANSACTION_PROCESSOR_SIGN_TRANSACTION_ERROR = "Error happened on calling sign transaction of Signature provider";
    public static final String TRANSACTION_IS_NOT_ALLOWED_TOBE_MODIFIED = "The transaction is not allowed to be modified but was modified by signature provider!";
    public static final String TRANSACTION_PROCESSOR_GET_SIGN_DESERIALIZE_TRANS_ERROR = "Error happened on calling deserializeTransaction to refresh transaction object with new values";
    public static final String TRANSACTION_PROCESSOR_RPC_PUSH_TRANSACTION = "Error happened on calling pushTransaction RPC call";
    public static final String TRANSACTION_PROCESSOR_SERIALIZE_ERROR = "Error happened on calling serializeTransaction";
    public static final String TRANSACTION_PROCESSOR_SIGN_CREATE_SIGN_REQUEST_ERROR = "Error happened on creating signature request for Signature Provider to sign!";
    public static final String TRANSACTION_PROCESSOR_BROADCAST_TRANS_ERROR = "Error happened on pushing transaction to chain!";
}
