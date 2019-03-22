package one.block.eosiojava.models.signatureProvider;

import java.util.List;

/**
 * The type EOSIO transaction signature request which will be sent to SignatureProvider to sign
 */
public class EosioTransactionSignatureRequest {

    /**
     * The Serialized transaction.
     */
    private String serializedTransaction;

    /**
     * The Signing public key which will be used to find the private keys (or key identities) to
     * sign the serialized transaciton
     */
    private List<String> signingPublicKey;

    /**
     * The Chain id of the chain which main library is connecting to
     */
    private String chainId;

    /**
     * The Abis which carry abi data from main library to signature provider
     */
    private List<String> abis;

    /**
     * The whether the serialized transaction is modifiable.
     */
    private boolean isModifiable;

    /**
     * Instantiates a new Eosio transaction signature request.
     *
     * @param serializedTransaction the serialized transaction
     * @param signingPublicKey the signing public key
     * @param chainId the chain id
     * @param abis the abis
     * @param isModifiable the is modifiable
     */
    public EosioTransactionSignatureRequest(String serializedTransaction,
            List<String> signingPublicKey, String chainId, List<String> abis,
            boolean isModifiable) {
        this.serializedTransaction = serializedTransaction;
        this.signingPublicKey = signingPublicKey;
        this.chainId = chainId;
        this.abis = abis;
        this.isModifiable = isModifiable;
    }

    /**
     * Gets serialized transaction.
     *
     * @return the serialized transaction
     */
    public String getSerializedTransaction() {
        return serializedTransaction;
    }

    /**
     * Sets serialized transaction.
     *
     * @param serializedTransaction the serialized transaction
     */
    public void setSerializedTransaction(String serializedTransaction) {
        this.serializedTransaction = serializedTransaction;
    }

    /**
     * Gets signing public key.
     *
     * @return the signing public key
     */
    public List<String> getSigningPublicKey() {
        return signingPublicKey;
    }

    /**
     * Sets signing public key.
     *
     * @param signingPublicKey the signing public key
     */
    public void setSigningPublicKey(List<String> signingPublicKey) {
        this.signingPublicKey = signingPublicKey;
    }

    /**
     * Gets chain id.
     *
     * @return the chain id
     */
    public String getChainId() {
        return chainId;
    }

    /**
     * Sets chain id.
     *
     * @param chainId the chain id
     */
    public void setChainId(String chainId) {
        this.chainId = chainId;
    }

    /**
     * Gets abis.
     *
     * @return the abis
     */
    public List<String> getAbis() {
        return abis;
    }

    /**
     * Sets abis.
     *
     * @param abis the abis
     */
    public void setAbis(List<String> abis) {
        this.abis = abis;
    }

    /**
     * Is modifiable boolean.
     *
     * @return the boolean
     */
    public boolean isModifiable() {
        return isModifiable;
    }

    /**
     * Sets modifiable.
     *
     * @param modifiable the modifiable
     */
    public void setModifiable(boolean modifiable) {
        isModifiable = modifiable;
    }
}
