package one.block.eosiojava.models.rpcProvider.response;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import one.block.eosiojava.models.rpcProvider.request.PushTransactionRequest;

/**
 * The response of the pushTransaction() RPC call
 * {@link one.block.eosiojava.interfaces.IRPCProvider#pushTransaction(PushTransactionRequest)}
 */
public class PushTransactionResponse {

    /**
     * The transaction id of the successful transaction.
     */
    @SerializedName("transaction_id")
    private String transactionId;

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

    public ProcessedTransactionResponse getProcessed() {
        return transactionResponse;
    }

    public List<ActionTrace> getActionTraces() { return transactionResponse != null ? transactionResponse.getActionTraces() : new ArrayList<>(); }
}