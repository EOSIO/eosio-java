package one.block.eosiojava.models.rpcProvider.response;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

/**
 * The response of PushTransaction RPC call
 */
public class PushTransactionResponse {

    /**
     * The Transaction id.
     */
    @SerializedName("transaction_id")
    private String transactionId;

    /**
     * The Processed.
     */
    @SerializedName("processed")
    private Map processed;

    /**
     * Gets transaction id.
     *
     * @return the transaction id
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * Gets processed.
     *
     * @return the processed
     */
    public Map getProcessed() {
        return processed;
    }
}