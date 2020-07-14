package one.block.eosiojava.models.rpcProvider.response;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

/**
 * The base class for push/send transaction responses
 */
public class TransactionResponse {

    /**
     * The transaction id of the successful transaction.
     */
    @SerializedName("transaction_id")
    private String transactionId;

    /**
     * The processed transaction
     */
    @SerializedName("processed")
    private ProcessedTransactionResponse transactionResponse;

    /**
     * Gets the transaction id of the successful transaction.
     *
     * @return The successful transaction id.
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * Gets the processed transaction
     *
     * @return the processed transaction
     */
    public ProcessedTransactionResponse getProcessed() {
        return transactionResponse;
    }

    /**
     * Gets the processed transaction's action traces
     *
     * @return the processed transaction's action traces
     */
    public List<ActionTrace> getActionTraces() { return transactionResponse != null ? transactionResponse.getActionTraces() : new ArrayList<>(); }
}