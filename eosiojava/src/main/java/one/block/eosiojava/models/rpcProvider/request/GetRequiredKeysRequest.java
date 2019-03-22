package one.block.eosiojava.models.rpcProvider.request;

import java.util.List;
import one.block.eosiojava.models.rpcProvider.Transaction;

/**
 * The request of GetRequiredKeys RPC call.
 */
public class GetRequiredKeysRequest {

    /**
     * The Available keys which come from SignatureProvider or manually set
     */
    private List<String> availableKeys;

    /**
     * The Transaction which will be broadcast to backend.
     * <br/>
     * !!!! action inside actions of the transaction have to be serialized.
     */
    private Transaction transaction;

    /**
     * Instantiates a new Get required keys request.
     *
     * @param availableKeys the available keys
     * @param transaction the transaction
     */
    public GetRequiredKeysRequest(List<String> availableKeys,
            Transaction transaction) {
        this.availableKeys = availableKeys;
        this.transaction = transaction;
    }

    /**
     * Gets available keys.
     *
     * @return the available keys
     */
    public List<String> getAvailableKeys() {
        return availableKeys;
    }

    /**
     * Sets available keys.
     *
     * @param availableKeys the available keys
     */
    public void setAvailableKeys(List<String> availableKeys) {
        this.availableKeys = availableKeys;
    }

    /**
     * Gets transaction.
     *
     * @return the transaction
     */
    public Transaction getTransaction() {
        return transaction;
    }

    /**
     * Sets transaction.
     * The Transaction which will be broadcast to backend.
     * <br/>
     * !!!! action inside actions of the transaction have to be serialized.
     * @param transaction the transaction
     */
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

}
