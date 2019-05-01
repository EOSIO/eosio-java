package one.block.eosiojava.models.rpcProvider.request;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import one.block.eosiojava.models.rpcProvider.Transaction;
import org.jetbrains.annotations.NotNull;

/**
 * The request of getRequiredKeys RPC call {@link one.block.eosiojava.interfaces.IRPCProvider#getRequiredKeys(GetRequiredKeysRequest)}
 */
public class GetRequiredKeysRequest {

    /**
     * The available keys which come from a Signature Provider or are manually set.
     */
    @SerializedName("available_keys")
    @NotNull
    private List<String> availableKeys;

    /**
     * The Transaction which will be broadcast to the blockchain. <br> Actions of the
     * transaction have to be serialized.
     */
    @SerializedName("transaction")
    @NotNull
    private Transaction transaction;

    /**
     * Instantiates a new GetRequiredKeysRequest.
     *
     * @param availableKeys the available keys which come from a SignatureProvider or are manually set
     * @param transaction the transaction which will be broadcast to the blockchain. Actions of
     * the transaction have to be serialized.
     */
    public GetRequiredKeysRequest(@NotNull List<String> availableKeys,
            @NotNull Transaction transaction) {
        this.availableKeys = availableKeys;
        this.transaction = transaction;
    }

    /**
     * Gets available keys which come from a Signature Provider or are manually set.
     *
     * @return the available keys
     */
    @NotNull
    public List<String> getAvailableKeys() {
        return availableKeys;
    }

    /**
     * Sets available keys which come from a Signature Provider or are manually set.
     *
     * @param availableKeys the available keys
     */
    public void setAvailableKeys(@NotNull List<String> availableKeys) {
        this.availableKeys = availableKeys;
    }

    /**
     * Gets transaction. The Transaction which will be broadcast to the blockchain.
     * <br>
     *     Actions of the transaction have to be serialized.
     *
     * @return the transaction
     */
    @NotNull
    public Transaction getTransaction() {
        return transaction;
    }

    /**
     * Sets transaction. The Transaction which will be broadcast to the blockchain. <br> Actions
     * of the transaction have to be serialized.
     *
     * @param transaction the transaction
     */
    public void setTransaction(@NotNull Transaction transaction) {
        this.transaction = transaction;
    }

}
