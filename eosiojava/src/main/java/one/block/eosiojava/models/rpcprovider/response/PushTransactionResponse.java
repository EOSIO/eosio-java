package one.block.eosiojava.models.rpcprovider.response;

import com.google.gson.annotations.SerializedName;
import java.util.Map;
import one.block.eosiojava.models.rpcprovider.request.PushTransactionRequest;

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
    private Map processed;

    /**
     * Gets the transaction id of the successful transaction.
     *
     * @return The successful transaction id.
     */
    public String getTransactionId() {
        return transactionId;
    }

    public Map getProcessed() {
        return processed;
    }
}