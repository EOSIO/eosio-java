package one.block.eosiojava.models.rpcProvider.request;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import one.block.eosiojava.models.rpcProvider.Transaction;
import org.jetbrains.annotations.NotNull;

/**
 * The request of GetRequiredKeys RPC call.
 */
public class GetRequiredKeysRequest {

    /**
     * The Available keys which come from SignatureProvider or manually set
     */
    @SerializedName("available_keys")
    @NotNull
    private List<String> availableKeys;

    /**
     * The Transaction which will be broadcast to backend.
     * <br> !!!! action inside actions of the transaction have to be serialized.
     */
    @SerializedName("transaction")
    @NotNull
    private Transaction transaction;

    /**
     * Instantiates a new Get required keys request.
     *
     * @param availableKeys the available keys
     * @param transaction the transaction
     */
    public GetRequiredKeysRequest(@NotNull List<String> availableKeys,
            @NotNull Transaction transaction) {
        this.availableKeys = availableKeys;
        this.transaction = transaction;
    }

    /**
     * Gets available keys.
     *
     * @return the available keys
     */
    @NotNull
    public List<String> getAvailableKeys() {
        return availableKeys;
    }

    /**
     * Sets available keys.
     *
     * @param availableKeys the available keys
     */
    public void setAvailableKeys(@NotNull List<String> availableKeys) {
        this.availableKeys = availableKeys;
    }

    /**
     * Gets transaction.
     *
     * @return the transaction
     */
    @NotNull
    public Transaction getTransaction() {
        return transaction;
    }

    /**
     * Sets transaction. The Transaction which will be broadcast to backend. <br> !!!! action
     * inside actions of the transaction have to be serialized.
     *
     * @param transaction the transaction
     */
    public void setTransaction(@NotNull Transaction transaction) {
        this.transaction = transaction;
    }

}
