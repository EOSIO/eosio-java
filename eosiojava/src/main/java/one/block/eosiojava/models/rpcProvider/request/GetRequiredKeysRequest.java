package one.block.eosiojava.models.rpcProvider.request;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import one.block.eosiojava.models.rpcProvider.Transaction;
import org.jetbrains.annotations.NotNull;

/**
 * The request of GetRequiredKeys RPC call {@link one.block.eosiojava.interfaces.IRPCProvider#getRequiredKeys(GetRequiredKeysRequest)}
 */
public class GetRequiredKeysRequest {

    /**
     * The Available keys which come from SignatureProvider or manually set
     */
    @SerializedName("available_keys")
    @NotNull
    private List<String> availableKeys;

    /**
     * The Transaction which will be broadcast to backend. <br/> Action inside actions of the
     * transaction have to be serialized.
     */
    @SerializedName("transaction")
    @NotNull
    private Transaction transaction;

    /**
     * Instantiates a new GetRequiredKeysRequest.
     *
     * @param availableKeys the available keys which come from SignatureProvider or manually set
     * @param transaction the transaction which will be broadcast to backend. Action inside actions of the transaction have to be serialized.
     */
    public GetRequiredKeysRequest(@NotNull List<String> availableKeys,
            @NotNull Transaction transaction) {
        this.availableKeys = availableKeys;
        this.transaction = transaction;
    }

    /**
     * Gets available keys which come from SignatureProvider or manually set
     *
     * @return the available keys
     */
    @NotNull
    public List<String> getAvailableKeys() {
        return availableKeys;
    }

    /**
     * Sets available keys which come from SignatureProvider or manually set
     *
     * @param availableKeys the available keys
     */
    public void setAvailableKeys(@NotNull List<String> availableKeys) {
        this.availableKeys = availableKeys;
    }

    /**
     * Gets transaction. The Transaction which will be broadcast to backend.
     * <br>
     *     Action inside actions of the transaction have to be serialized.
     *
     * @return the transaction
     */
    @NotNull
    public Transaction getTransaction() {
        return transaction;
    }

    /**
     * Sets transaction. The Transaction which will be broadcast to backend. <br/> Action
     * inside actions of the transaction have to be serialized.
     *
     * @param transaction the transaction
     */
    public void setTransaction(@NotNull Transaction transaction) {
        this.transaction = transaction;
    }

}
