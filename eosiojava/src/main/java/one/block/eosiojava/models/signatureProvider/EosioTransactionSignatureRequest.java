package one.block.eosiojava.models.signatureProvider;

import java.util.List;
import one.block.eosiojava.models.rpcProvider.response.GetInfoResponse;

/**
 * The type EOSIO transaction signature request which will be sent to SignatureProvider to sign
 */
public class EosioTransactionSignatureRequest {

    /**
     * The Serialized (Hex) version of {@link one.block.eosiojava.models.rpcProvider.Transaction}.
     * <br>
     * It is the result of {@link one.block.eosiojava.interfaces.ISerializationProvider#serializeTransaction(String)}
     */
    private String serializedTransaction;

    /**
     * The EOSIO public keys which will be used to find the private keys (or key identities) to sign
     * the serialized transaction
     */
    private List<String> signingPublicKeys;

    /**
     * The Chain id of the chain which main library is connecting to
     * <br>
     * Its value comes from {@link GetInfoResponse#getChainId()}
     */
    private String chainId;

    /**
     * The list of ABI which carry abi data from main library to signature provider
     */
    private List<BinaryAbi> abis;

    /**
     * Whether the serialized transaction is modifiable.
     * <br>
     * If signature provider modify the serialized transaction on the response {@link
     * EosioTransactionSignatureResponse#getSerializeTransaction()} but this field is false then
     * {@link one.block.eosiojava.error.session.TransactionGetSignatureNotAllowModifyTransactionError}
     * will be thrown
     */
    private boolean isModifiable;

    /**
     * Instantiates a new Eosio transaction signature request.
     *
     * @param serializedTransaction the serialized transaction
     * @param signingPublicKeys the signing public keys
     * @param chainId the chain id
     * @param abis the abis
     * @param isModifiable the is modifiable
     */
    public EosioTransactionSignatureRequest(String serializedTransaction,
            List<String> signingPublicKeys, String chainId, List<BinaryAbi> abis,
            boolean isModifiable) {
        this.serializedTransaction = serializedTransaction;
        this.signingPublicKeys = signingPublicKeys;
        this.chainId = chainId;
        this.abis = abis;
        this.isModifiable = isModifiable;
    }

    /**
     * Gets serialized transaction.
     * <br>
     * The Serialized (Hex) version of {@link one.block.eosiojava.models.rpcProvider.Transaction}.
     * <br>
     * It is the result of {@link one.block.eosiojava.interfaces.ISerializationProvider#serializeTransaction(String)}
     *
     * @return the serialized transaction
     */
    public String getSerializedTransaction() {
        return serializedTransaction;
    }

    /**
     * Sets serialized transaction.
     * <br>
     * The Serialized (Hex) version of {@link one.block.eosiojava.models.rpcProvider.Transaction}.
     * <br>
     * It is the result of {@link one.block.eosiojava.interfaces.ISerializationProvider#serializeTransaction(String)}
     *
     * @param serializedTransaction the serialized transaction
     */
    public void setSerializedTransaction(String serializedTransaction) {
        this.serializedTransaction = serializedTransaction;
    }

    /**
     * Gets signing public key.
     * <br>
     * The EOSIO public key which will be used to find the private keys (or key identities) to sign
     * the serialized transaction
     *
     * @return the signing public key
     */
    public List<String> getSigningPublicKeys() {
        return signingPublicKeys;
    }

    /**
     * Sets signing public key.
     * <br>
     * The EOSIO public key which will be used to find the private keys (or key identities) to sign
     * the serialized transaction
     *
     * @param signingPublicKeys the signing public key
     */
    public void setSigningPublicKeys(List<String> signingPublicKeys) {
        this.signingPublicKeys = signingPublicKeys;
    }

    /**
     * Gets chain id.
     * <br>
     * The Chain id of the chain which main library is connecting to
     * <br>
     * Its value comes from {@link GetInfoResponse#getChainId()}
     *
     * @return the chain id
     */
    public String getChainId() {
        return chainId;
    }

    /**
     * Sets chain id.
     * <br>
     * The Chain id of the chain which main library is connecting to
     * <br>
     * Its value comes from {@link GetInfoResponse#getChainId()}
     *
     * @param chainId the chain id
     */
    public void setChainId(String chainId) {
        this.chainId = chainId;
    }

    /**
     * Gets abis.
     * <br>
     * The list of ABI which carry abi data from main library to signature provider
     *
     * @return the abis
     */
    public List<BinaryAbi> getAbis() {
        return abis;
    }

    /**
     * Sets abis.
     * <br>
     * The list of ABI which carry abi data from main library to signature provider
     *
     * @param abis the abis
     */
    public void setAbis(List<BinaryAbi> abis) {
        this.abis = abis;
    }

    /**
     * Is modifiable boolean.
     * <br>
     * If signature provider modify the serialized transaction on the response {@link
     * EosioTransactionSignatureResponse#getSerializeTransaction()} but this field is false then
     * {@link one.block.eosiojava.error.session.TransactionGetSignatureNotAllowModifyTransactionError}
     * will be thrown
     *
     * @return the boolean
     */
    public boolean isModifiable() {
        return isModifiable;
    }

    /**
     * Sets modifiable.
     * <br>
     * If signature provider modify the serialized transaction on the response {@link
     * EosioTransactionSignatureResponse#getSerializeTransaction()} but this field is false then
     * {@link one.block.eosiojava.error.session.TransactionGetSignatureNotAllowModifyTransactionError}
     * will be thrown
     *
     * @param modifiable the modifiable
     */
    public void setModifiable(boolean modifiable) {
        isModifiable = modifiable;
    }
}
