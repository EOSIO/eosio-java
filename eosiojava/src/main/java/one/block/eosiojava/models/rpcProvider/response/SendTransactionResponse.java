package one.block.eosiojava.models.rpcProvider.response;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import one.block.eosiojava.models.rpcProvider.request.SendTransactionRequest;

/**
 * The response of the sendTransaction() RPC call
 * {@link one.block.eosiojava.interfaces.IRPCProvider#sendTransaction(SendTransactionRequest)}
 */
public class SendTransactionResponse {

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

    /**
     * Gets the processed detail information of the successful transaction.
     *
     * @return The successful processed details of the transaction.
     */
    public Map getProcessed() {
        return processed;
    }

    /**
     * Return action values, if any.  The returned values are placed in their respective actions.
     * The array must contain null for the actions that do not return action values.
     * There may be more action values than input actions due to inline actions or notifications
     * but input (request) actions are always returned first and in the same order as they were
     * submitted.
     *
     * @return ArrayList of Objects containing the return values from the response.
     */
    public ArrayList<Object> getActionValues() {
        ArrayList<Object> returnValues = new ArrayList<Object>();
        if (processed == null) { return returnValues; }
        if (!processed.containsKey("action_traces")) { return returnValues; }
        for (Map trace : (List<Map>) processed.get("action_traces")) {
            returnValues.add(trace.getOrDefault("return_value_data", null));
        }
        return returnValues;
    }

    /**
     * Get the action value at the specified index, if it exists and return it as the passed in type.
     * @param index The index of the action value returns to retrieve.
     * @param clazz The class type to cast the action value to, if found.
     * @return The action value as the desired type or null if not found or is the wrong type.
     * @throws ClassCastException if the value cannot be cast to the requested type.
     * @throws IndexOutOfBoundsException if an incorrect index is requested.
     */
    public <T> T getActionValueAtIndex(int index, Class<T> clazz) throws IndexOutOfBoundsException, ClassCastException {
        ArrayList<Object> actionValues = getActionValues();
        if (actionValues == null) { return null; }
        Object actionValuesObj = actionValues.get(index);
        return clazz.cast(actionValuesObj);
    }
}