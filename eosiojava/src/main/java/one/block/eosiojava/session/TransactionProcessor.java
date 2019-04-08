package one.block.eosiojava.session;

import com.google.common.base.Strings;
import java.io.IOException;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import one.block.eosiojava.error.ErrorConstants;
import one.block.eosiojava.error.abiProvider.GetAbiError;
import one.block.eosiojava.error.rpcProvider.GetBlockRpcError;
import one.block.eosiojava.error.rpcProvider.GetInfoRpcError;
import one.block.eosiojava.error.rpcProvider.GetRequiredKeysRpcError;
import one.block.eosiojava.error.rpcProvider.PushTransactionRpcError;
import one.block.eosiojava.error.serializationprovider.DeserializeTransactionError;
import one.block.eosiojava.error.serializationprovider.SerializeError;
import one.block.eosiojava.error.serializationprovider.SerializeTransactionError;
import one.block.eosiojava.error.session.TransactionBroadCastEmptySignatureError;
import one.block.eosiojava.error.session.TransactionBroadCastError;
import one.block.eosiojava.error.session.TransactionCreateSignatureRequestAbiError;
import one.block.eosiojava.error.session.TransactionCreateSignatureRequestEmptyAvailableKeyError;
import one.block.eosiojava.error.session.TransactionCreateSignatureRequestError;
import one.block.eosiojava.error.session.TransactionCreateSignatureRequestKeyError;
import one.block.eosiojava.error.session.TransactionCreateSignatureRequestRequiredKeysEmptyError;
import one.block.eosiojava.error.session.TransactionCreateSignatureRequestRpcError;
import one.block.eosiojava.error.session.TransactionCreateSignatureRequestSerializationError;
import one.block.eosiojava.error.session.TransactionGetSignatureDeserializationError;
import one.block.eosiojava.error.session.TransactionGetSignatureError;
import one.block.eosiojava.error.session.TransactionGetSignatureNotAllowModifyTransactionError;
import one.block.eosiojava.error.session.TransactionGetSignatureSigningError;
import one.block.eosiojava.error.session.TransactionPrepareError;
import one.block.eosiojava.error.session.TransactionPrepareInputError;
import one.block.eosiojava.error.session.TransactionPrepareRpcError;
import one.block.eosiojava.error.session.TransactionProcessorConstructorInputError;
import one.block.eosiojava.error.session.TransactionPushTransactionError;
import one.block.eosiojava.error.session.TransactionSerializeError;
import one.block.eosiojava.error.session.TransactionSignAndBroadCastError;
import one.block.eosiojava.error.session.TransactionSignError;
import one.block.eosiojava.error.signatureProvider.GetAvailableKeysError;
import one.block.eosiojava.error.signatureProvider.SignatureProviderError;
import one.block.eosiojava.interfaces.IABIProvider;
import one.block.eosiojava.interfaces.IRPCProvider;
import one.block.eosiojava.interfaces.ISerializationProvider;
import one.block.eosiojava.interfaces.ISignatureProvider;
import one.block.eosiojava.models.AbiEosSerializationObject;
import one.block.eosiojava.models.EOSIOName;
import one.block.eosiojava.models.rpcProvider.Action;
import one.block.eosiojava.models.rpcProvider.Transaction;
import one.block.eosiojava.models.rpcProvider.TransactionConfig;
import one.block.eosiojava.models.rpcProvider.request.GetBlockRequest;
import one.block.eosiojava.models.rpcProvider.request.GetRequiredKeysRequest;
import one.block.eosiojava.models.rpcProvider.request.PushTransactionRequest;
import one.block.eosiojava.models.rpcProvider.response.GetBlockResponse;
import one.block.eosiojava.models.rpcProvider.response.GetInfoResponse;
import one.block.eosiojava.models.rpcProvider.response.GetRequiredKeysResponse;
import one.block.eosiojava.models.rpcProvider.response.PushTransactionResponse;
import one.block.eosiojava.models.signatureProvider.EosioTransactionSignatureRequest;
import one.block.eosiojava.models.signatureProvider.EosioTransactionSignatureResponse;
import one.block.eosiojava.utilities.DateFormatter;
import one.block.eosiojava.utilities.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * TransactionProcess present an EOS Transaction.
 * <p>
 * Transaction process role are:
 * <p>
 * - Get input {@link one.block.eosiojava.models.rpcProvider.Action}
 * <p>
 * - Create transaction
 * <p>
 * - Serialize Transaction
 * <p>
 * - Sign Transaction
 * <p>
 * - Broadcast Transaction
 */
public class TransactionProcessor {

    /**
     * Reference of Serialization Provider from TransactionSession
     */
    @NotNull
    private ISerializationProvider serializationProvider;

    /**
     * Reference of RPC Provider from TransactionSession
     */
    @NotNull
    private IRPCProvider rpcProvider;

    /**
     * Reference of ABI Provider from TransactionSession
     */
    @NotNull
    private IABIProvider abiProvider;

    /**
     * Reference of Signature Provider from TransactionSession
     */
    @NotNull
    private ISignatureProvider signatureProvider;

    /**
     * Transaction instance which keep all data relating to EOS Transaction
     * <p/>
     * This object hold the non-serialized version of transaction
     */
    @Nullable
    private Transaction transaction;

    /**
     * Transaction instance which keep the original transaction reference after signature provider
     * return signing result
     *
     * Check getSignature() flow in "complete workflow" doc for more detail
     */
    @Nullable
    private Transaction originalTransaction;

    /**
     * List of signature which will be filled after signature provider return signing result.
     * <p>
     * Check getSignature() flow in "complete workflow" doc for more detail
     */
    @NotNull
    private List<String> signatures = new ArrayList<>();

    /**
     * Serialized version of Transaction which is keeping here for checking if signature update
     * transaction data.
     * <p/>
     *     If the transaction is updated, this object need to be cleared or up-to-date
     * <p/>
     *     Check getSignature() flow in "complete workflow" doc for more detail
     * about value assigned and usages
     */
    @Nullable
    private String serializedTransaction;

    /**
     * List of available key which most likely come out from SignatureProvider.
     * <p>
     * If this list is set, TransactionProcessor won't ask for available keys from Signature
     * Provider and use this list.
     * <p> Check createSignatureRequest() flow in "complete workflow" doc for more
     * detail
     */
    @Nullable
    private List<String> availableKeys;

    /**
     * List of required keys to sign the transaction which came from getting required keys process.
     * <p>
     * If this list is set, TransactionProcessor won't make RPC call for getRequiredKey() to get
     * required keys and use this list.
     * <p>
     * Check createSignatureRequest() flow in "complete workflow" doc for more detail
     */
    @Nullable
    private List<String> requiredKeys;

    /**
     * Configuration for transaction which offers ability to set:
     * <p/>
     * - The expiration period for the transaction in second
     * <p/>
     * - How many blocks behind
     */
    @NotNull
    private TransactionConfig transactionConfig = new TransactionConfig();

    /**
     * Chain id which will be assigned value in prepare/createSignatureRequest and get used in
     * createSignatureRequest
     */
    @Nullable
    private String chainId;

    /**
     * Whether allow signature provider to modify the transaction or not.
     * <p/>
     * Default is false.
     */
    private boolean isTransactionModificationAllowed;

    /**
     * Constructor with all provider references from {@link TransactionSession}
     */
    public TransactionProcessor(
            @NotNull ISerializationProvider serializationProvider,
            @NotNull IRPCProvider rpcProvider,
            @NotNull IABIProvider abiProvider,
            @NotNull ISignatureProvider signatureProvider) {
        this.serializationProvider = serializationProvider;
        this.rpcProvider = rpcProvider;
        this.abiProvider = abiProvider;
        this.signatureProvider = signatureProvider;
    }

    /**
     * Constructor with all provider references from {@link TransactionSession} and preset
     * Transaction
     *
     * @param transaction - preset Transaction
     */
    public TransactionProcessor(
            @NotNull ISerializationProvider serializationProvider,
            @NotNull IRPCProvider rpcProvider,
            @NotNull IABIProvider abiProvider,
            @NotNull ISignatureProvider signatureProvider,
            @NotNull Transaction transaction) throws TransactionProcessorConstructorInputError {
        this(serializationProvider, rpcProvider, abiProvider, signatureProvider);
        this.transaction = transaction;
        if (this.transaction.getActions().isEmpty()) {
            throw new TransactionProcessorConstructorInputError(
                    ErrorConstants.TRANSACTION_PROCESSOR_ACTIONS_EMPTY_ERROR_MSG);
        }
    }

    //region public methods

    /**
     * Prepare actions's data from input and create new instance of Transaction if it is not set.
     * <p>
     * Check Prepare() flow in "complete workflow" doc for more detail
     *
     * @param actions - List of action with data. If the transaction is preset or has value and it
     * has its own actions, that list will be override by this input list
     */
    public void prepare(@NotNull List<Action> actions) throws TransactionPrepareError {
        if (actions.isEmpty()) {
            throw new TransactionPrepareInputError(
                    ErrorConstants.TRANSACTION_PROCESSOR_ACTIONS_EMPTY_ERROR_MSG);
        }

        /*
         Create new instance of Transaction.
         Final value will be assigned to transaction object in class level and override preset Transaction if it was set by constructor.
         The reason for that is for avoiding trash/haft way data when error happens.
        */
        Transaction preparingTransaction = new Transaction("", BigInteger.ZERO, BigInteger.ZERO,
                BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, new ArrayList<Action>(), actions,
                new ArrayList<String>());

        // Filling expiration, refBlockNum and refBlockPrefix
        GetInfoResponse getInfoResponse;

        try {
            getInfoResponse = this.rpcProvider.getInfo();
        } catch (GetInfoRpcError getInfoRpcError) {
            throw new TransactionPrepareRpcError(ErrorConstants.TRANSACTION_PROCESSOR_RPC_GET_INFO,
                    getInfoRpcError);
        }

        if (Strings.isNullOrEmpty(this.chainId)) {
            if (Strings.isNullOrEmpty(getInfoResponse.getChainId())) {
                // Throw exception if both provided chain id and RPC chain id are empty
                throw new TransactionPrepareError(
                        ErrorConstants.TRANSACTION_PROCESSOR_PREPARE_CHAINID_RPC_EMPTY);
            }

            // Assign value to chain id only if chain id is not provided and rpc's chain id is fine
            this.chainId = getInfoResponse.getChainId();
        } else if (!Strings.isNullOrEmpty(getInfoResponse.getChainId()) && !getInfoResponse
                .getChainId().equals(chainId)) {
            // Throw error if both are not empty but one does not match with another
            throw new TransactionPrepareError(
                    String.format(ErrorConstants.TRANSACTION_PROCESSOR_PREPARE_CHAINID_NOT_MATCH,
                            this.chainId,
                            getInfoResponse.getChainId()));
        }

        if (preparingTransaction.getExpiration().isEmpty()) {
            String strHeadBlockTime = getInfoResponse.getHeadBlockTime();

            long headBlockTime;

            try {
                headBlockTime = DateFormatter.convertBackendTimeToMilli(strHeadBlockTime);
            } catch (ParseException e) {
                throw new TransactionPrepareError(
                        ErrorConstants.TRANSACTION_PROCESSOR_HEAD_BLOCK_TIME_PARSE_ERROR, e);
            }

            int expiresSeconds = this.transactionConfig.getExpiresSeconds();

            long expirationTimeInMilliseconds = headBlockTime + expiresSeconds * 1000;
            preparingTransaction.setExpiration(DateFormatter
                    .convertMilliSecondToBackendTimeString(expirationTimeInMilliseconds));
        }

        // Filling refBlockNum and refBlockPrefix

        BigInteger headBlockNum;

        int blockBehindConfig = this.transactionConfig.getBlocksBehind();

        if (getInfoResponse.getHeadBlockNum().compareTo(BigInteger.valueOf(blockBehindConfig))
                > 0) {
            headBlockNum = getInfoResponse.getHeadBlockNum()
                    .subtract(BigInteger.valueOf(blockBehindConfig));
        } else {
            headBlockNum = BigInteger.valueOf(blockBehindConfig);
        }

        GetBlockResponse getBlockResponse;
        try {
            getBlockResponse = this.rpcProvider
                    .getBlock(new GetBlockRequest(headBlockNum.toString()));
        } catch (GetBlockRpcError getBlockRpcError) {
            throw new TransactionPrepareRpcError(
                    ErrorConstants.TRANSACTION_PROCESSOR_PREPARE_RPC_GET_BLOCK, getBlockRpcError);
        }

        // Restrict the refBlockNum to 32 bit unsigned value
        BigInteger refBlockNum = getBlockResponse.getBlockNum().and(BigInteger.valueOf(0xffff));
        BigInteger refBlockPrefix = getBlockResponse.getRefBlockPrefix();

        preparingTransaction.setRefBlockNum(refBlockNum);
        preparingTransaction.setRefBlockPrefix(refBlockPrefix);

        this.finishPreparing(preparingTransaction);
    }

    /**
     * Sign the transaction by passing {@link EosioTransactionSignatureRequest} to signature
     * provider
     * <p>
     * Check sign() flow in "complete workflow" doc for more detail
     *
     * @return success or not
     */
    public boolean sign() throws TransactionSignError {
        EosioTransactionSignatureRequest eosioTransactionSignatureRequest;
        try {
            eosioTransactionSignatureRequest = this.createSignatureRequest();
        } catch (TransactionCreateSignatureRequestError transactionCreateSignatureRequestError) {
            throw new TransactionSignError(
                    ErrorConstants.TRANSACTION_PROCESSOR_SIGN_CREATE_SIGN_REQUEST_ERROR,
                    transactionCreateSignatureRequestError);
        }

        EosioTransactionSignatureResponse eosioTransactionSignatureResponse;
        try {
            eosioTransactionSignatureResponse = this.getSignature(eosioTransactionSignatureRequest);
            if (eosioTransactionSignatureResponse.getError() != null) {
                throw eosioTransactionSignatureResponse.getError();
            }
        } catch (TransactionGetSignatureError transactionGetSignatureError) {
            throw new TransactionSignError(transactionGetSignatureError);
        } catch (@Nullable SignatureProviderError signatureProviderError) {
            throw new TransactionSignError(
                    ErrorConstants.TRANSACTION_PROCESSOR_SIGN_SIGNATURE_RESPONSE_ERROR,
                    signatureProviderError);
        }

        return true;
    }

    /**
     * Broadcast transaction to chain <p> Check broadcast() flow in "complete workflow" doc for more
     * detail
     *
     * @return broadcast result from chain
     */
    @NotNull
    public PushTransactionResponse broadcast() throws TransactionBroadCastError {
        if (this.serializedTransaction == null || this.serializedTransaction.isEmpty()) {
            throw new TransactionBroadCastError(
                    ErrorConstants.TRANSACTION_PROCESSOR_BROADCAST_SERIALIZED_TRANSACTION_EMPTY);
        }

        if (this.signatures.isEmpty()) {
            throw new TransactionBroadCastEmptySignatureError(
                    ErrorConstants.TRANSACTION_PROCESSOR_BROADCAST_SIGN_EMPTY);
        }

        PushTransactionRequest pushTransactionRequest = new PushTransactionRequest(this.signatures,
                0, "", this.serializedTransaction);
        try {
            return this.pushTransaction(pushTransactionRequest);
        } catch (TransactionPushTransactionError transactionPushTransactionError) {
            throw new TransactionBroadCastError(
                    ErrorConstants.TRANSACTION_PROCESSOR_BROADCAST_TRANS_ERROR,
                    transactionPushTransactionError);
        }
    }

    /**
     * Sign and broadcast the transaction and signature to chain
     *
     * @return broadcast result from chain
     */
    @NotNull
    public PushTransactionResponse signAndBroadcast() throws TransactionSignAndBroadCastError {
        EosioTransactionSignatureRequest eosioTransactionSignatureRequest;
        try {
            eosioTransactionSignatureRequest = this.createSignatureRequest();
        } catch (TransactionCreateSignatureRequestError transactionCreateSignatureRequestError) {
            throw new TransactionSignAndBroadCastError(transactionCreateSignatureRequestError);
        }

        try {
            this.getSignature(eosioTransactionSignatureRequest);
        } catch (TransactionGetSignatureError transactionGetSignatureError) {
            throw new TransactionSignAndBroadCastError(transactionGetSignatureError);
        }

        if (this.serializedTransaction == null || this.serializedTransaction.isEmpty()) {
            throw new TransactionSignAndBroadCastError(
                    ErrorConstants.TRANSACTION_PROCESSOR_SIGN_BROADCAST_SERIALIZED_TRANSACTION_EMPTY);
        }

        if (this.signatures.isEmpty()) {
            throw new TransactionSignAndBroadCastError(
                    ErrorConstants.TRANSACTION_PROCESSOR_SIGN_BROADCAST_SIGN_EMPTY);
        }

        // Signatures and serializedTransaction are assigned and finalized in getSignature method
        PushTransactionRequest pushTransactionRequest = new PushTransactionRequest(this.signatures,
                0, "", this.serializedTransaction);
        try {
            return this.pushTransaction(pushTransactionRequest);
        } catch (TransactionPushTransactionError transactionPushTransactionError) {
            throw new TransactionSignAndBroadCastError(transactionPushTransactionError);
        }
    }

    /**
     * Return JSON String of transaction
     *
     * @return JSON String of transaction
     */
    @Nullable
    public String toJSON() {
        return Utils.getDefaultGson().toJson(this.transaction);
    }

    /**
     * Getting serialized version of Transaction <p> Check serialize() flow in "complete workflow"
     * doc for more detail
     *
     * @return serialized Transaction
     */
    @Nullable
    public String serialize() throws TransactionSerializeError {
        // Return serialized version of the Transaction, otherwise serialize the transaction
        if (this.serializedTransaction != null && !this.serializedTransaction.isEmpty()) {
            return this.serializedTransaction;
        }

        try {
            return this.serializeTransaction();
        } catch (TransactionCreateSignatureRequestError transactionCreateSignatureRequestError) {
            throw new TransactionSerializeError(
                    ErrorConstants.TRANSACTION_PROCESSOR_SERIALIZE_ERROR,
                    transactionCreateSignatureRequestError);
        }
    }

    //endregion

    //region private methods

    /**
     * Create signature request which will be sent to signature provider to be signed.
     * <p>
     * Check createSignatureRequest() flow in "complete workflow" doc for more detail
     */
    @NotNull
    private EosioTransactionSignatureRequest createSignatureRequest()
            throws TransactionCreateSignatureRequestError {
        if (this.transaction == null) {
            throw new TransactionCreateSignatureRequestError(
                    ErrorConstants.TRANSACTION_PROCESSOR_TRANSACTION_HAS_TO_BE_INITIALIZED);
        }

        if (this.transaction.getActions().isEmpty()) {
            throw new TransactionCreateSignatureRequestError(
                    ErrorConstants.TRANSACTION_PROCESSOR_ACTIONS_EMPTY_ERROR_MSG);
        }

        // Cache the serialized version of transaction to processor
        this.serializedTransaction = this.serializeTransaction();

        EosioTransactionSignatureRequest eosioTransactionSignatureRequest = new EosioTransactionSignatureRequest(
                this.serializedTransaction,
                null,
                this.chainId,
                null,
                this.isTransactionModificationAllowed);

        // Assign required keys to signing public keys if it was set.
        if (this.requiredKeys != null && !this.requiredKeys.isEmpty()) {
            eosioTransactionSignatureRequest.setSigningPublicKey(this.requiredKeys);
            return eosioTransactionSignatureRequest;
        }

        // Getting required keys
        // 1.Getting available key
        if (this.availableKeys == null || this.availableKeys.isEmpty()) {
            try {
                this.availableKeys = this.signatureProvider.getAvailableKeys();
            } catch (GetAvailableKeysError getAvailableKeysError) {
                throw new TransactionCreateSignatureRequestKeyError(
                        ErrorConstants.TRANSACTION_PROCESSOR_GET_AVAILABLE_KEY_ERROR,
                        getAvailableKeysError);
            }

            if (this.availableKeys.isEmpty()) {
                throw new TransactionCreateSignatureRequestEmptyAvailableKeyError(
                        ErrorConstants.TRANSACTION_PROCESSOR_GET_AVAILABLE_KEY_EMPTY);
            }
        }

        // 2.Getting required keys by GetRequiredKeys RPC call
        try {
            GetRequiredKeysResponse getRequiredKeysResponse = this.rpcProvider
                    .getRequiredKeys(
                            new GetRequiredKeysRequest(
                                    this.availableKeys,
                                    this.transaction));
            if (getRequiredKeysResponse.getRequiredKeys() == null || getRequiredKeysResponse
                    .getRequiredKeys().isEmpty()) {
                throw new TransactionCreateSignatureRequestRequiredKeysEmptyError(
                        ErrorConstants.GET_REQUIRED_KEY_RPC_EMPTY_RESULT);
            }

            List<String> backendRequiredKeys = getRequiredKeysResponse.getRequiredKeys();
            if (!this.availableKeys.containsAll(backendRequiredKeys)) {
                throw new TransactionCreateSignatureRequestRequiredKeysEmptyError(
                        ErrorConstants.TRANSACTION_PROCESSOR_REQUIRED_KEY_NOT_SUBSET);
            }

            this.requiredKeys = backendRequiredKeys;
        } catch (GetRequiredKeysRpcError getRequiredKeysRpcError) {
            throw new TransactionCreateSignatureRequestRpcError(
                    ErrorConstants.TRANSACTION_PROCESSOR_RPC_GET_REQUIRED_KEYS,
                    getRequiredKeysRpcError);
        }

        eosioTransactionSignatureRequest.setSigningPublicKey(this.requiredKeys);
        return eosioTransactionSignatureRequest;
    }

    /**
     * Passing {@link EosioTransactionSignatureRequest} to Signature provider to get it to sign.
     * <p>
     * Check getSignature() flow in "complete workflow" doc for more detail
     *
     * @param eosioTransactionSignatureRequest The request for signature
     * @return Response from Signature provider
     */
    @NotNull
    private EosioTransactionSignatureResponse getSignature(
            EosioTransactionSignatureRequest eosioTransactionSignatureRequest)
            throws TransactionGetSignatureError {
        EosioTransactionSignatureResponse eosioTransactionSignatureResponse;
        try {
            eosioTransactionSignatureResponse = this.signatureProvider
                    .signTransaction(eosioTransactionSignatureRequest);
            if (eosioTransactionSignatureResponse.getError() != null) {
                throw eosioTransactionSignatureResponse.getError();
            }
        } catch (SignatureProviderError signatureProviderError) {
            throw new TransactionGetSignatureSigningError(
                    ErrorConstants.TRANSACTION_PROCESSOR_SIGN_TRANSACTION_ERROR,
                    signatureProviderError);
        }

        if (Strings.isNullOrEmpty(eosioTransactionSignatureResponse.getSerializeTransaction())) {
            throw new TransactionGetSignatureSigningError(
                    ErrorConstants.TRANSACTION_PROCESSOR_SIGN_TRANSACTION_TRANS_EMPTY_ERROR);
        }

        if (eosioTransactionSignatureResponse.getSignatures().isEmpty()) {
            throw new TransactionGetSignatureSigningError(
                    ErrorConstants.TRANSACTION_PROCESSOR_SIGN_TRANSACTION_SIGN_EMPTY_ERROR);
        }

        // Set current transaction to original transaction
        this.originalTransaction = this.transaction;

        if (this.serializedTransaction != null
                && !this.serializedTransaction
                .equals(eosioTransactionSignatureResponse.getSerializeTransaction())) {
            // Throw error if transaction is modified but it has not been allowed to do that
            if (!this.isTransactionModificationAllowed) {
                throw new TransactionGetSignatureNotAllowModifyTransactionError(
                        ErrorConstants.TRANSACTION_IS_NOT_ALLOWED_TOBE_MODIFIED);
            }

            // Deserialize and update new transaction to the current transaction if it was updated and is allowed
            String transactionJSON;
            try {
                transactionJSON = this.serializationProvider
                        .deserializeTransaction(
                                eosioTransactionSignatureResponse.getSerializeTransaction());
                if (transactionJSON == null || transactionJSON.isEmpty()) {
                    throw new DeserializeTransactionError(
                            ErrorConstants.TRANSACTION_PROCESSOR_GET_SIGN_DESERIALIZE_TRANS_EMPTY_ERROR);
                }
            } catch (DeserializeTransactionError deserializeTransactionError) {
                throw new TransactionGetSignatureDeserializationError(
                        ErrorConstants.TRANSACTION_PROCESSOR_GET_SIGN_DESERIALIZE_TRANS_ERROR,
                        deserializeTransactionError);
            }

            this.transaction = Utils.getDefaultGson().fromJson(transactionJSON, Transaction.class);
        }

        this.signatures = new ArrayList<>();
        this.signatures.addAll(eosioTransactionSignatureResponse.getSignatures());
        this.serializedTransaction = eosioTransactionSignatureResponse.getSerializeTransaction();
        return eosioTransactionSignatureResponse;
    }

    /**
     * Push signed transaction to chain
     * <p>
     * Check pushTransaction() flow in "complete workflow" doc for more detail
     *
     * @param pushTransactionRequest the request
     * @return Response from chain
     */
    @NotNull
    private PushTransactionResponse pushTransaction(PushTransactionRequest pushTransactionRequest)
            throws TransactionPushTransactionError {
        try {
            return this.rpcProvider.pushTransaction(pushTransactionRequest);
        } catch (PushTransactionRpcError pushTransactionRpcError) {
            throw new TransactionPushTransactionError(
                    ErrorConstants.TRANSACTION_PROCESSOR_RPC_PUSH_TRANSACTION,
                    pushTransactionRpcError);
        }
    }

    /**
     * Serialize current transaction
     *
     * @return serialized transaction in Hex
     */
    @NotNull
    private String serializeTransaction() throws TransactionCreateSignatureRequestError {
        Transaction clonedTransaction;
        try {
            clonedTransaction = this.getDeepClone();
        } catch (IOException e) {
            throw new TransactionCreateSignatureRequestError(
                    ErrorConstants.TRANSACTION_PROCESSOR_PREPARE_CLONE_ERROR, e);
        } catch (ClassNotFoundException e) {
            throw new TransactionCreateSignatureRequestError(
                    ErrorConstants.TRANSACTION_PROCESSOR_PREPARE_CLONE_CLASS_NOT_FOUND, e);
        }

        if (clonedTransaction == null) {
            throw new TransactionCreateSignatureRequestError(
                    ErrorConstants.TRANSACTION_PROCESSOR_PREPARE_CLONE_ERROR);
        }

        // Check for chain id
        // Call getInfo if chainId is NULL or Empty
        if (this.chainId == null || this.chainId.isEmpty()) {
            try {
                GetInfoResponse getInfoResponse = this.rpcProvider.getInfo();
                this.chainId = getInfoResponse.getChainId();
            } catch (GetInfoRpcError getInfoRpcError) {
                throw new TransactionCreateSignatureRequestRpcError(
                        ErrorConstants.TRANSACTION_PROCESSOR_RPC_GET_INFO, getInfoRpcError);
            }
        }

        for (Action action : clonedTransaction.getActions()) {
            String actionAbiJSON;
            try {
                actionAbiJSON = this.abiProvider
                        .getAbi(this.chainId, new EOSIOName(action.getAccount()));
            } catch (GetAbiError getAbiError) {
                throw new TransactionCreateSignatureRequestAbiError(
                        String.format(ErrorConstants.TRANSACTION_PROCESSOR_GET_ABI_ERROR,
                                action.getAccount()), getAbiError);
            }

            AbiEosSerializationObject actionAbiEosSerializationObject = new AbiEosSerializationObject(
                    action.getAccount(), action.getName(),
                    null, actionAbiJSON);
            actionAbiEosSerializationObject.setHex("");

            // !!! At this step, data field of action still have JSON type value
            actionAbiEosSerializationObject.setJson(action.getData());

            try {
                this.serializationProvider.serialize(actionAbiEosSerializationObject);
                if (actionAbiEosSerializationObject.getHex().isEmpty()) {
                    throw new TransactionCreateSignatureRequestSerializationError(
                            ErrorConstants.TRANSACTION_PROCESSOR_SERIALIZE_ACTION_WORKED_BUT_EMPTY_RESULT);
                }
            } catch (SerializeError serializeError) {
                throw new TransactionCreateSignatureRequestSerializationError(
                        String.format(ErrorConstants.TRANSACTION_PROCESSOR_SERIALIZE_ACTION_ERROR,
                                action.getAccount()), serializeError);
            }

            // !!! Set serialization result to data field of the action
            action.setData(actionAbiEosSerializationObject.getHex());
        }

        // Serialize the whole transaction
        String _serializedTransaction;
        try {
            String clonedTransactionToJSON = Utils.getDefaultGson().toJson(clonedTransaction);
            _serializedTransaction = this.serializationProvider
                    .serializeTransaction(clonedTransactionToJSON);
            if (_serializedTransaction == null || _serializedTransaction.isEmpty()) {
                throw new TransactionCreateSignatureRequestSerializationError(
                        ErrorConstants.TRANSACTION_PROCESSOR_SERIALIZE_TRANSACTION_WORKED_BUT_EMPTY_RESULT);
            }

        } catch (SerializeTransactionError serializeTransactionError) {
            throw new TransactionCreateSignatureRequestSerializationError(
                    ErrorConstants.TRANSACTION_PROCESSOR_SERIALIZE_TRANSACTION_ERROR,
                    serializeTransactionError);
        }

        return _serializedTransaction;
    }

    /**
     * Getting deep clone of the transaction
     */
    private Transaction getDeepClone() throws IOException, ClassNotFoundException {
        if (this.transaction == null) {
            return null;
        }

        return Utils.clone(this.transaction);
    }

    /**
     * Called when prepare is finished
     *
     * @param preparingTransaction - prepared transaction from prepared
     */
    private void finishPreparing(Transaction preparingTransaction) {
        this.transaction = preparingTransaction;
        // Clear serialized transaction if it was serialized.
        if (!Strings.isNullOrEmpty(this.serializedTransaction)) {
            this.serializedTransaction = "";
        }
    }

    //endregion

    //region getters/setters
    @Nullable
    public Transaction getTransaction() {
        return transaction;
    }

    @Nullable
    public Transaction getOriginalTransaction() {
        return originalTransaction;
    }

    @NotNull
    public List<String> getSignatures() {
        return signatures;
    }

    @Nullable
    public String getSerializedTransaction() {
        return serializedTransaction;
    }

    @NotNull
    public TransactionConfig getTransactionConfig() {
        return transactionConfig;
    }

    public void setTransactionConfig(@NotNull TransactionConfig transactionConfig) {
        this.transactionConfig = transactionConfig;
    }

    /**
     * Sets chain id value. If the value has not set yet, then the code will call getInfo in Rpc
     * provider to get it.
     */
    public void setChainId(@Nullable String chainId) {
        this.chainId = chainId;
    }

    /**
     * Set value for available keys list.
     * <p>
     * List of available key which most likely come out from SignatureProvider.
     * <p>
     * If this list is set, TransactionProcessor won't ask for available keys from Signature
     * Provider and use this list
     * <p>
     * Check createSignatureRequest() flow in "complete workflow" doc for more detail
     *
     * @param availableKeys the input available keys
     */
    public void setAvailableKeys(@NotNull List<String> availableKeys) {
        this.availableKeys = availableKeys;
    }

    /**
     * Set value for required keys list
     * <p>
     * List of required keys to sign the transaction which came from getting required keys process.
     * <p>
     * If this list is set, TransactionProcessor won't make RPC call for getRequiredKey() to get
     * required keys and use this list
     * <p>
     * Check createSignatureRequest() flow in "complete workflow" doc for more detail
     *
     * @param requiredKeys the input required keys
     */
    public void setRequiredKeys(@NotNull List<String> requiredKeys) {
        this.requiredKeys = requiredKeys;
    }

    /**
     * Whether allow transaction to be modified by Signature Provider.
     */
    public boolean isTransactionModificationAllowed() {
        return isTransactionModificationAllowed;
    }

    /**
     * True: The transaction could be modified and updated to current transaction by Signature
     * provider.
     * <p/>
     * False: No modification. {@link TransactionGetSignatureNotAllowModifyTransactionError} will be
     * thrown if transaction is modified.
     */
    public void setIsTransactionModificationAllowed(boolean isTransactionModificationAllowed) {
        this.isTransactionModificationAllowed = isTransactionModificationAllowed;
    }

    //endregion
}
