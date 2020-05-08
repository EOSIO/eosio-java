package one.block.eosiojava.models.signatureProvider;

import java.util.List;
import one.block.eosiojava.models.rpcProvider.response.GetInfoResponse;

/**
 * The request object that will be sent to SignatureProvider.  It contains the transaction that will
 * need to be signed.
 */
public class EosioTransactionSignatureRequest {

    /**
     * The serialized (Hex) version of {@link one.block.eosiojava.models.rpcProvider.Transaction}.
     * <br>
     * It is the result of {@link one.block.eosiojava.interfaces.ISerializationProvider#serializeTransaction(String)}
     */
    private String serializedTransaction;

    /**
     * The serialized (Hex) version of all concatenated context free data
     */
    private String contextFreeData;

    /**
     * The EOSIO public keys which will be used to find the private keys (or key identities) to sign
     * the serialized transaction.
     */
    private List<String> signingPublicKeys;

    /**
     * The chain id of the target blockchain.
     * <br>
     * Its value comes from {@link GetInfoResponse#getChainId()}
     */
    private String chainId;

    /**
     * The list of ABIs passed to the signature provider.
     */
    private List<BinaryAbi> abis;

    /**
     * Whether the serialized transaction is modifiable.
     * <br>
     * If the signature provider modifies the serialized transaction and returns it in the response {@link
     * EosioTransactionSignatureResponse#getSerializeTransaction()} and this field is false then
     * {@link one.block.eosiojava.error.session.TransactionGetSignatureNotAllowModifyTransactionError}
     * will be thrown.
     */
    private boolean isModifiable;

    /**
     * Instantiates a new Eosio transaction signature request.
     *
     * @param serializedTransaction the serialized transaction
     * @param signingPublicKeys the signing public keys
     * @param chainId the chain id
     * @param abis the ABIs
     * @param isModifiable boolean to indicate whether the signature provider is able to modify the
     * transaction
     * @param contextFreeData the serialized contextFreeData
     */
    public EosioTransactionSignatureRequest(String serializedTransaction,
            List<String> signingPublicKeys, String chainId, List<BinaryAbi> abis,
            boolean isModifiable, String contextFreeData) {
        this.serializedTransaction = serializedTransaction;
        this.signingPublicKeys = signingPublicKeys;
        this.chainId = chainId;
        this.abis = abis;
        this.isModifiable = isModifiable;
        this.contextFreeData = contextFreeData;
    }

    /**
     * Instantiates a new Eosio transaction signature request.
     *
     * @param serializedTransaction the serialized transaction
     * @param signingPublicKeys the signing public keys
     * @param chainId the chain id
     * @param abis the ABIs
     * @param isModifiable boolean to indicate whether the signature provider is able to modify the
     * transaction
     */
    public EosioTransactionSignatureRequest(String serializedTransaction,
            List<String> signingPublicKeys, String chainId, List<BinaryAbi> abis,
            boolean isModifiable) {
        this(serializedTransaction, signingPublicKeys, chainId, abis, isModifiable, "");
    }

    /**
     * Gets the serialized transaction.
     * <br>
     * The serialized (Hex) version of {@link one.block.eosiojava.models.rpcProvider.Transaction}.
     * <br>
     * It is the result of {@link one.block.eosiojava.interfaces.ISerializationProvider#serializeTransaction(String)}
     *
     * @return the serialized transaction
     */
    public String getSerializedTransaction() {
        return serializedTransaction;
    }

    /**
     * Sets the serialized transaction.
     * <br>
     * The serialized (Hex) version of {@link one.block.eosiojava.models.rpcProvider.Transaction}.
     * <br>
     * It is the result of {@link one.block.eosiojava.interfaces.ISerializationProvider#serializeTransaction(String)}
     *
     * @param serializedTransaction the serialized transaction
     */
    public void setSerializedTransaction(String serializedTransaction) {
        this.serializedTransaction = serializedTransaction;
    }

    /**
     * Gets the serialized contextFreeData.
     * It is the result of {@link one.block.eosiojava.interfaces.ISerializationProvider#serializeContextFreeData(List)}
     *
     * @return the serialized contextFreeData
     */
    public String getContextFreeData() {
        return contextFreeData;
    }

    /**
     * Sets the serialized contextFreeData.
     * It is the result of {@link one.block.eosiojava.interfaces.ISerializationProvider#serializeContextFreeData(List)}
     *
     * @param contextFreeData the serialized contextFreeData
     */
    public void setContextFreeData(String contextFreeData) {
        this.contextFreeData = contextFreeData;
    }

    /**
     * Gets the signing public key.
     * <br>
     * The EOSIO public key that will be used to find the private keys (or key identities) to sign
     * the serialized transaction.
     *
     * @return the signing public key
     */
    public List<String> getSigningPublicKeys() {
        return signingPublicKeys;
    }

    /**
     * Sets the signing public key.
     * <br>
     * The EOSIO public key that will be used to find the private keys (or key identities) to sign
     * the serialized transaction.
     *
     * @param signingPublicKeys the signing public key
     */
    public void setSigningPublicKeys(List<String> signingPublicKeys) {
        this.signingPublicKeys = signingPublicKeys;
    }

    /**
     * Gets the chain id.
     * <br>
     * The chain id of the target blockchain.
     * <br>
     * Its value comes from {@link GetInfoResponse#getChainId()}
     *
     * @return the chain id
     */
    public String getChainId() {
        return chainId;
    }

    /**
     * Sets the chain id.
     * <br>
     * The chain id of the target blockchain.
     * <br>
     * Its value comes from {@link GetInfoResponse#getChainId()}
     *
     * @param chainId the chain id
     */
    public void setChainId(String chainId) {
        this.chainId = chainId;
    }

    /**
     * Gets ABIs.
     * <br>
     * The list of ABIs that is passed to the signature provider.
     *
     * @return the abis
     */
    public List<BinaryAbi> getAbis() {
        return abis;
    }

    /**
     * Sets ABIs.
     * <br>
     * The list of ABIs that are passed to the signature provider.
     *
     * @param abis the abis
     */
    public void setAbis(List<BinaryAbi> abis) {
        this.abis = abis;
    }

    /**
     * isModifiable boolean.
     * <br>
     * If the signature provider modifies the serialized transaction and returns it in the response {@link
     * EosioTransactionSignatureResponse#getSerializeTransaction()} but this field is false then
     * {@link one.block.eosiojava.error.session.TransactionGetSignatureNotAllowModifyTransactionError}
     * will be thrown.
     *
     * @return the boolean
     */
    public boolean isModifiable() {
        return isModifiable;
    }

    /**
     * Sets isModifiable.
     * <br>
     * If the signature provider modifies the serialized transaction and returns it in the response {@link
     * EosioTransactionSignatureResponse#getSerializeTransaction()} but this field is false then
     * {@link one.block.eosiojava.error.session.TransactionGetSignatureNotAllowModifyTransactionError}
     * will be thrown.
     *
     * @param modifiable the modifiable
     */
    public void setModifiable(boolean modifiable) {
        isModifiable = modifiable;
    }
}
