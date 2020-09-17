

package one.block.eosiojava.error;

import java.util.List;
import one.block.eosiojava.interfaces.IRPCProvider;
import one.block.eosiojava.interfaces.ISignatureProvider;
import one.block.eosiojava.models.AbiEosSerializationObject;
import one.block.eosiojava.models.EOSIOName;
import one.block.eosiojava.models.rpcProvider.request.GetBlockRequest;
import one.block.eosiojava.models.rpcProvider.request.GetRequiredKeysRequest;
import one.block.eosiojava.models.rpcProvider.request.PushTransactionRequest;
import one.block.eosiojava.models.rpcProvider.response.ActionTrace;
import one.block.eosiojava.models.rpcProvider.response.GetInfoResponse;
import one.block.eosiojava.models.signatureProvider.EosioTransactionSignatureRequest;
import one.block.eosiojava.session.TransactionProcessor;
import one.block.eosiojava.interfaces.IABIProvider;
import one.block.eosiojava.interfaces.ISerializationProvider;
import one.block.eosiojava.models.signatureProvider.EosioTransactionSignatureResponse;
import one.block.eosiojava.models.rpcProvider.response.GetRequiredKeysResponse;

public class ErrorConstants {
    private ErrorConstants(){

    }

    //EOSFormatter() Errors
    /**
     * The private key provided is not in the EOS format.
     */
    public static final String INVALID_EOS_PRIVATE_KEY = "The EOS private key provided is invalid!";
    /**
     * The public key provided is not in the EOS format.
     */
    public static final String INVALID_EOS_PUBLIC_KEY = "The EOS public key provided is invalid!";
    /**
     * An error occurred while Base58 decoding the EOS key.
     */
    public static final String BASE58_DECODING_ERROR = "An error occurred while Base58 decoding the EOS key!";
    /**
     * The key provided for Base58 decoding was empty.
     */
    public static final String BASE58_EMPTY_KEY = "Input key to decode can't be empty!";
    /**
     * Input key, checksum or key type were empty and are needed for validation.
     */
    public static final String BASE58_EMPTY_CHECKSUM_OR_KEY = "Input key, checksum and key type to validate can't be empty!";
    /**
     * Input key, checksum or key type were empty and are needed for validation.
     */
    public static final String BASE58_EMPTY_CHECKSUM_OR_KEY_OR_KEY_TYPE = "Input key, checksum and key type to validate can't be empty!";
    /**
     * Input key has invalid checksum.
     */
    public static final String BASE58_INVALID_CHECKSUM = "Input key has invalid checksum!";
    /**
     * Error converting DER encoded key to PEM format.
     */
    public static final String DER_TO_PEM_CONVERSION = "Error converting DER encoded key to PEM format!";
    /**
     * The algorithm used to generate the object is unsupported.
     */
    public static final String UNSUPPORTED_ALGORITHM = "Unsupported algorithm!";
    /**
     * The private key is not in PEM format.
     */
    public static final String INVALID_PEM_PRIVATE_KEY = "This is not a PEM formatted private key!";
    /**
     * The private key is not in DER format.
     */
    public static final String INVALID_DER_PRIVATE_KEY = "DER format of private key is incorrect!";
    /**
     * Checksum generation failed.
     */
    public static final String CHECKSUM_GENERATION_ERROR = "Could not generate checksum!";
    /**
     * The object could not be Base58 encoded.
     */
    public static final String BASE58_ENCODING_ERROR = "Unable to Base58 encode object!";
    /**
     * The public key could not be decompressed.
     */
    public static final String PUBLIC_KEY_DECOMPRESSION_ERROR = "Problem decompressing public key!";
    /**
     * The public key could not be compressed.
     */
    public static final String PUBLIC_KEY_COMPRESSION_ERROR = "Problem compressing public key!";
    /**
     * The public key provided for decoding was empty.
     */
    public static final String PUBLIC_KEY_IS_EMPTY = "Input key to decode can't be empty!";
    /**
     * Chain id or serialized transaction parameter was empty.
     */
    public static final String EMPTY_INPUT_PREPARE_SERIALIZIED_TRANS_FOR_SIGNING = "Chain id, serialized transaction, and serialized context free data can't be empty!";
    /**
     * The signable transaction parameter was empty.
     */
    public static final String EMPTY_INPUT_EXTRACT_SERIALIZIED_TRANS_FROM_SIGNABLE = "Signable transaction can't be empty!";
    /**
     * The length of the signable transaction was incorrect and the serialized transaction could not
     * be extracted.
     */
    public static final String INVALID_INPUT_SIGNABLE_TRANS_LENGTH_EXTRACT_SERIALIZIED_TRANS_FROM_SIGNABLE = "Length of the signable transaction must be larger than %s";
    /**
     * Unable to extract the serialized transaction from the signable transaction.
     */
    public static final String EXTRACT_SERIALIZIED_TRANS_FROM_SIGNABLE_ERROR = "Something went wrong when trying to extract serialized transaction from signable transaction.";
    /**
     * Signature formatting failed.
     */
    public static final String SIGNATURE_FORMATTING_ERROR = "An error occured formating the signature!";
    /**
     * A public key could not be recovered from the signature.
     */
    public static final String COULD_NOT_RECOVER_PUBLIC_KEY_FROM_SIG = "Could not recover public key from Signature.";
    /**
     * The signature provided failed the canonical check.
     */
    public static final String NON_CANONICAL_SIGNATURE = "Input signature is not canonical.";
    /**
     * The public key could not be extracted from the provided private key.  The private key is most
     * likely invalid.
     */
    public static final String PUBLIC_KEY_COULD_NOT_BE_EXTRACTED_FROM_PRIVATE_KEY = "This is not a private key!";

    // ABIProviderImpl Errors
    public static final String NO_RESPONSE_RETRIEVING_ABI = "No response retrieving ABI.";
    public static final String MISSING_ABI_FROM_RESPONSE = "Missing ABI from GetRawAbiResponse.";
    public static final String CALCULATED_HASH_NOT_EQUAL_RETURNED = "Calculated ABI hash does not match returned hash.";
    public static final String REQUESTED_ACCCOUNT_NOT_EQUAL_RETURNED = "Requested account name does not match returned account name.";
    public static final String NO_ABI_FOUND = "No ABI found for requested account name.";
    public static final String ERROR_RETRIEVING_ABI = "Error retrieving ABI from the chain.";

    //PEMProcessor Errors
    /**
     * The object provided is not in the PEM format.
     */
    public static final String ERROR_READING_PEM_OBJECT = "Error reading PEM object!";
    /**
     * The PEM object could not be parsed.
     */
    public static final String ERROR_PARSING_PEM_OBJECT = "Error parsing PEM object!";
    /**
     * There was no key data in the PEM object.
     */
    public static final String KEY_DATA_NOT_FOUND = "Key data not found in PEM object!";
    /**
     * PEM object could not be read.
     */
    public static final String INVALID_PEM_OBJECT = "Cannot read PEM object!";

    //TransactionProcessor Errors
    /**
     * Error message get thrown if actions list is empty during processes of {@link TransactionProcessor}.
     */
    public static final String TRANSACTION_PROCESSOR_ACTIONS_EMPTY_ERROR_MSG = "Action list can't be empty!";

    /**
     * Error message get thrown if {@link IRPCProvider#getInfo()} thrown exception during processes of {@link TransactionProcessor}
     */
    public static final String TRANSACTION_PROCESSOR_RPC_GET_INFO = "Error happened on calling GetInfo RPC.";

    /**
     * Error message get thrown if {@link IRPCProvider#getBlock(GetBlockRequest)} thrown exception during process of {@link TransactionProcessor#prepare(List)}
     */
    public static final String TRANSACTION_PROCESSOR_PREPARE_RPC_GET_BLOCK = "Error happened on calling GetBlock RPC.";

    /**
     * Error message get thrown if chain id from {@link GetInfoResponse#getChainId()} does not match with the input chain id
     */
    public static final String TRANSACTION_PROCESSOR_PREPARE_CHAINID_NOT_MATCH = "Provided chain id %s does not match chain id %s";

    /**
     * Error message get thrown if chain id from {@link GetInfoResponse#getChainId()} is empty.
     */
    public static final String TRANSACTION_PROCESSOR_PREPARE_CHAINID_RPC_EMPTY = "Chain id from back end is empty!";

    /**
     * Error message get thrown if parsing head block time from {@link GetInfoResponse#getHeadBlockTime()} get error
     */
    public static final String TRANSACTION_PROCESSOR_HEAD_BLOCK_TIME_PARSE_ERROR = "Failed to parse head block time";

    /**
     * Error message get thrown if making clone version of transaction is failed.
     */
    public static final String TRANSACTION_PROCESSOR_PREPARE_CLONE_ERROR = "Error happened on cloning transaction.";

    /**
     * Error message get thrown if making clone version of transaction is failed by {@link ClassNotFoundException}
     */
    public static final String TRANSACTION_PROCESSOR_PREPARE_CLONE_CLASS_NOT_FOUND = "Transaction class was not found";

    /**
     * Error message get thrown if the current transaction inside {@link TransactionProcessor} has not been initialized or empty.
     */
    public static final String TRANSACTION_PROCESSOR_TRANSACTION_HAS_TO_BE_INITIALIZED = "Transaction must be initialized before this method could be called! call prepare for initialize Transaction";

    /**
     * Error message get thrown if {@link IABIProvider#getAbi(String, EOSIOName)} get error.
     */
    public static final String TRANSACTION_PROCESSOR_GET_ABI_ERROR = "Error happened on getting abi for contract [%s]";

    /**
     * Error message get thrown if Action's serialization process execute successfully but its result is empty.
     */
    public static final String TRANSACTION_PROCESSOR_SERIALIZE_ACTION_WORKED_BUT_EMPTY_RESULT = "Serialization of action worked fine but got back empty result!";

    /**
     * Error message get thrown if Transaction's serialization process execute successfully but its result is empty.
     */
    public static final String TRANSACTION_PROCESSOR_SERIALIZE_TRANSACTION_WORKED_BUT_EMPTY_RESULT = "Serialization of transaction worked fine but got back empty result!";

    /**
     * Error message get thrown if Action's serialization process get error by calling {@link ISerializationProvider#serialize(AbiEosSerializationObject)}
     */
    public static final String TRANSACTION_PROCESSOR_SERIALIZE_ACTION_ERROR = "Error happened on serializing action [%s]";

    /**
     * Error message get thrown if Transaction's serialization process get error by calling {@link ISerializationProvider#serializeTransaction(String)}
     */
    public static final String TRANSACTION_PROCESSOR_SERIALIZE_TRANSACTION_ERROR = "Error happened on serializing transaction";

    /**
     * Error message get thrown if {@link ISignatureProvider#getAvailableKeys()} returns error.
     */
    public static final String TRANSACTION_PROCESSOR_GET_AVAILABLE_KEY_ERROR = "Error happened on getAvailableKeys from SignatureProvider!";

    /**
     * Error message get thrown if {@link ISignatureProvider#getAvailableKeys()} returns no key.
     */
    public static final String TRANSACTION_PROCESSOR_GET_AVAILABLE_KEY_EMPTY = "Signature provider return no available key";

    /**
     * Error message get thrown if {@link IRPCProvider#getRequiredKeys(GetRequiredKeysRequest)} get error.
     */
    public static final String TRANSACTION_PROCESSOR_RPC_GET_REQUIRED_KEYS = "Error happened on calling getRequiredKeys RPC call.";

    /**
     * Error message get thrown if {@link IRPCProvider#getRequiredKeys(GetRequiredKeysRequest)} returns no key.
     */
    public static final String GET_REQUIRED_KEY_RPC_EMPTY_RESULT = "GetRequiredKeys RPC returned no required keys";

    /**
     * Error message get thrown if {@link ISignatureProvider#signTransaction(EosioTransactionSignatureRequest)} returns error
     */
    public static final String TRANSACTION_PROCESSOR_SIGN_TRANSACTION_ERROR = "Error happened on calling sign transaction of Signature provider";

    /**
     * Error message get thrown if {@link ISignatureProvider#signTransaction(EosioTransactionSignatureRequest)} return empty serialized transaction.
     */
    public static final String TRANSACTION_PROCESSOR_SIGN_TRANSACTION_TRANS_EMPTY_ERROR = "Serialized transaction come back empty from Signature Provider";

    /**
     * Error message get thrown if {@link ISignatureProvider#signTransaction(EosioTransactionSignatureRequest)} return no signature.
     */
    public static final String TRANSACTION_PROCESSOR_SIGN_TRANSACTION_SIGN_EMPTY_ERROR = "Signatures come back empty from Signature Provider";

    /**
     * Error message get thrown if {@link EosioTransactionSignatureResponse} which return from {@link ISignatureProvider#signTransaction(EosioTransactionSignatureRequest)} has modified serialized transaction but {@link TransactionProcessor#isTransactionModificationAllowed()} is false
     */
    public static final String TRANSACTION_IS_NOT_ALLOWED_TOBE_MODIFIED = "The transaction is not allowed to be modified but was modified by signature provider!";

    /**
     * Error message get thrown if {@link ISerializationProvider#deserializeTransaction} returns error during deserialize modified serialized transaction inside {@link EosioTransactionSignatureResponse} which return from {@link ISignatureProvider#signTransaction(EosioTransactionSignatureRequest)}
     */
    public static final String TRANSACTION_PROCESSOR_GET_SIGN_DESERIALIZE_TRANS_ERROR = "Error happened on calling deserializeTransaction to refresh transaction object with new values";

    /**
     * Error message get thrown if {@link IRPCProvider#pushTransaction(PushTransactionRequest)} returns error.
     */
    public static final String TRANSACTION_PROCESSOR_RPC_PUSH_TRANSACTION = "Error happened on calling pushTransaction RPC call";

    /**
     * Error message get thrown if {@link TransactionProcessor#serialize()}
     */
    public static final String TRANSACTION_PROCESSOR_SERIALIZE_ERROR = "Error happened on calling serializeTransaction";

    /**
     * Error message get thrown if error happens during creating signature process of {@link TransactionProcessor#sign()}
     */
    public static final String TRANSACTION_PROCESSOR_SIGN_CREATE_SIGN_REQUEST_ERROR = "Error happened on creating signature request for Signature Provider to sign!";

    /**
     * Error message get thrown if error happens during pushing transaction to backend
     */
    public static final String TRANSACTION_PROCESSOR_BROADCAST_TRANS_ERROR = "Error happened on pushing transaction to chain!";

    /**
     * Error message get thrown if required keys from {@link GetRequiredKeysResponse} is not subset of keys from {@link ISignatureProvider#getAvailableKeys()}
     */
    public static final String TRANSACTION_PROCESSOR_REQUIRED_KEY_NOT_SUBSET = "Required keys from back end are not available in available keys from Signature Provider.";

    /**
     * Error message get thrown if serialized transaction is empty or has not been populated during process of {@link TransactionProcessor#broadcast()}
     */
    public static final String TRANSACTION_PROCESSOR_BROADCAST_SERIALIZED_TRANSACTION_EMPTY = "Serialized Transaction is empty or has not been populated. Make sure to call prepare then sign before calling broadcast";

    /**
     * Error message get thrown if serialized transaction is empty or has not been populated during process of {@link TransactionProcessor#signAndBroadcast()} ()}
     */
    public static final String TRANSACTION_PROCESSOR_SIGN_BROADCAST_SERIALIZED_TRANSACTION_EMPTY = "Serialized Transaction is empty or has not been populated. Make sure to call prepare then sign before calling sign and broadcast";

    /**
     * Error message get thrown if {@link ISignatureProvider#signTransaction(EosioTransactionSignatureRequest)} return error during process of {@link TransactionProcessor#sign()}
     */
    public static final String TRANSACTION_PROCESSOR_SIGN_SIGNATURE_RESPONSE_ERROR = "Error happened on the response of getSignature.";

    /**
     * Error message get thrown if {@link ISerializationProvider#deserializeTransaction} returns empty result during deserialize modified serialized transaction inside {@link EosioTransactionSignatureResponse} which return from {@link ISignatureProvider#signTransaction(EosioTransactionSignatureRequest)}
     */
    public static final String TRANSACTION_PROCESSOR_GET_SIGN_DESERIALIZE_TRANS_EMPTY_ERROR = "Deserialized transaction is null or empty";

    /**
     * Error message get thrown if {@link TransactionProcessor#getSignatures()} is empty during process of {@link TransactionProcessor#broadcast()}
     */
    public static final String TRANSACTION_PROCESSOR_BROADCAST_SIGN_EMPTY = "Can't call broadcast because Signature is empty. Make sure of calling sign before calling broadcast.";

    /**
     * Error message get thrown if {@link TransactionProcessor#getSignatures()} is empty during process of {@link TransactionProcessor#signAndBroadcast()}
     */
    public static final String TRANSACTION_PROCESSOR_SIGN_BROADCAST_SIGN_EMPTY = "Can't call sign and broadcast because Signature is empty. Make sure of calling sign before calling sign and broadcast.";

    /**
     * Error message get thrown if return value's deserialization process gets an error after calling {@link ISerializationProvider#deserialize(AbiEosSerializationObject)}
     */
    public static final String TRANSACTION_PROCESSOR_DESERIALIZE_RETURN_VALUE_ERROR = "Error happened on deserializing return value [%s]";

    /**
     * Error message thrown if return value's deserialization process is empty after calling {@link ISerializationProvider#deserialize(AbiEosSerializationObject)}
     */
    public static final String TRANSACTION_PROCESSOR_DESERIALIZE_RETURN_VALUE_EMPTY = "Deserialization of return value expected result, but was empty.";
}
