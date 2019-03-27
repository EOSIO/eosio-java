package one.block.eosiojava.session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import one.block.eosiojava.interfaces.IABIProvider;
import one.block.eosiojava.interfaces.IRPCProvider;
import one.block.eosiojava.interfaces.ISerializationProvider;
import one.block.eosiojava.interfaces.ISignatureProvider;
import one.block.eosiojava.models.rpcProvider.Action;
import one.block.eosiojava.models.rpcProvider.Transaction;
import one.block.eosiojava.models.rpcProvider.response.PushTransactionResponse;
import one.block.eosiojava.models.signatureProvider.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * TransactionProcess present an EOS Transaction.
 * <p>
 * Transaction process role are: - Get input {@link one.block.eosiojava.models.rpcProvider.Action} -
 * Create transaction - Serialize Transaction - Sign Transaction - Broadcast Transaction
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
     */
    @Nullable
    private Transaction transaction;

    /**
     * Transaction instance which keep the original transaction reference after signature provider return signing result
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
     * Serialized version of Transaction which is keeping here for checking if signature update transaction data.
     * <p> Check getSignature() flow in "complete workflow" doc for more detail
     * about value assigned and usages
     */
    @Nullable
    private String serializedTransaction;

    /**
     * List of available key which most likely come out from SignatureProvider.
     * <p>
     * If this list is set, TransactionProcessor won't ask for available keys from Signature Provider and use this list.
     * <p> Check createSignatureRequest() flow in "complete workflow" doc for more
     * detail
     */
    @Nullable
    private List<String> availableKeys;

    /**
     * List of required keys to sign the transaction which came from getting required keys process.
     * <p>
     * If this list is set, TransactionProcessor won't make RPC call for getRequiredKey() to get required keys and use this list.
     * <p>
     * Check createSignatureRequest() flow in "complete workflow" doc for more detail
     */
    @Nullable
    private List<String> requiredKeys;

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
            @NotNull Transaction transaction) {
        this(serializationProvider, rpcProvider, abiProvider, signatureProvider);
        this.transaction = transaction;
    }

    //region public methods

    /**
     * Prepare actions's data from input and create new instance of Transaction if it is not set.
     * <p>
     * Check Prepare() flow in "complete workflow" doc for more detail
     *
     * @param actions - List of action with data
     */
    public void prepare(List<Action> actions) {
        throw new NotImplementedException();
    }

    /**
     * Sign the transaction by passing {@link EosioTransactionSignatureRequest} to signature provider
     * <p>
     * Check sign() flow in "complete workflow" doc for more detail
     *
     * @return success or not
     */
    public boolean sign() {
        throw new NotImplementedException();
    }

    /**
     * Broadcast transaction to chain <p> Check broadcast() flow in "complete workflow" doc for
     * more detail
     *
     * @return broadcast result from chain
     */
    public PushTransactionResponse broadcast() {
        throw new NotImplementedException();
    }

    /**
     * Sign and broadcast the transaction and signature to chain
     *
     * @return broadcast result from chain
     */
    public PushTransactionResponse signAndBroadcast() {
        throw new NotImplementedException();
    }

    /**
     *
     * @return
     */
    public String toJSON() {
        throw new NotImplementedException();
    }

    /**
     * Getting serialized version of Transaction <p> Check serialize() flow in "complete workflow"
     * doc for more detail
     *
     * @return serialized Transaction
     */
    public String serialize() {
        // Return serialized version of the Transaction, otherwise serialize the transaction
        if (this.serializedTransaction != null && !this.serializedTransaction.isEmpty()) {
            return this.serializedTransaction;
        }

        throw new NotImplementedException();
    }

    //endregion

    //region private methods

    /**
     * Create signature request which will be sent to signature provider to be signed.
     * <p>
     * Check createSignatureRequest() flow in "complete workflow" doc for more detail
     */
    private EosioTransactionSignatureRequest createSignatureRequest() {
        throw new NotImplementedException();
    }

    /**
     * Passing {@link EosioTransactionSignatureRequest} to Signature provider to get it to sign.
     * <p>
     * Check getSignature() flow in "complete workflow" doc for more detail
     *
     * @return Response from Signature provider
     */
    private EosioTransactionSignatureResponse getSignature() {
        throw new NotImplementedException();
    }

    /**
     * Push signed transaction to chain
     * <p>
     * Check pushTransaction() flow in "complete workflow" doc for more detail
     *
     * @return Response from chain
     */
    private PushTransactionResponse pushTransaction() {
        throw new NotImplementedException();
    }

    /**
     * Create a deep clone of Transaction
     *
     * @return a deep clone of Transaction
     */
    private Transaction getDeepClone() {
        throw new NotImplementedException();
    }

    //endregion

    //region getters/setters

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

    /**
     * Set value for available keys list.
     * <p>
     *     List of available key which most likely come out
     * from SignatureProvider.
     * <p>
     *     If this list is set, TransactionProcessor won't ask for
     * available keys from Signature Provider and use this list
     * <p>
     *     Check createSignatureRequest()
     * flow in "complete workflow" doc for more detail
     *
     * @param availableKeys the input available keys
     */
    public void setAvailableKeys(@NotNull List<String> availableKeys) {
        this.availableKeys = availableKeys;
    }

    /**
     * Set value for required keys list
     * <p>
     *     List of required keys to sign the transaction which
     * came from getting required keys process.
     * <p>
     *     If this list is set, TransactionProcessor
     * won't make RPC call for getRequiredKey() to get required keys and use this list
     * <p>
     *     Check createSignatureRequest() flow in "complete workflow" doc for more detail
     *
     * @param requiredKeys the input required keys
     */
    public void setRequiredKeys(@NotNull List<String> requiredKeys) {
        this.requiredKeys = requiredKeys;
    }

    //endregion
}
