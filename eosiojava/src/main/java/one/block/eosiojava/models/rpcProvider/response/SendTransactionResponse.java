package one.block.eosiojava.models.rpcProvider.response;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

/**
 * The base class for send transaction responses
 */
public class SendTransactionResponse {

    /**
     * The transaction id of the successful transaction.
     */
    @SerializedName("transaction_id")
    private String transactionId;

    /**
     * The processed transaction
     */
    @SerializedName("processed")
    private Map processed;

    /**
     * Gets the transaction id of the successful transaction.
     *
     * @return The successful transaction id.
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * Gets the processed information of the successful transaction.
     *
     * @return The processed information.
     */
    public Map getProcessed() {
        return processed;
    }
}