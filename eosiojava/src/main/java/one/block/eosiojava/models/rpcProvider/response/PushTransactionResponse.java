package one.block.eosiojava.models.rpcProvider.response;

import com.google.gson.annotations.SerializedName;
import java.util.Map;
import one.block.eosiojava.models.rpcProvider.request.PushTransactionRequest;

/**
 * The response of PushTransaction RPC call {@link one.block.eosiojava.interfaces.IRPCProvider#pushTransaction(PushTransactionRequest)}
 */
public class PushTransactionResponse {

    /**
     * The Transaction id of the success transaction.
     */
    @SerializedName("transaction_id")
    private String transactionId;

    @SerializedName("processed")
    private Map processed;

    /**
     * Gets the Transaction id of the success transaction.
     *
     * @return the success transaction id.
     */
    public String getTransactionId() {
        return transactionId;
    }

    public Map getProcessed() {
        return processed;
    }
}