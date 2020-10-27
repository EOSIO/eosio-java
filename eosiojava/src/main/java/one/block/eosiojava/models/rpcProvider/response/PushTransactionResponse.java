package one.block.eosiojava.models.rpcProvider.response;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import one.block.eosiojava.models.rpcProvider.request.PushTransactionRequest;

/**
 * The response of the pushTransaction() RPC call
 */
public class PushTransactionResponse extends SendTransactionResponse {

    /**
     * Gets the transaction id of the successful transaction.
     *
     * @return The successful transaction id.
     */
    public String getTransactionId() {
        return super.getTransactionId();
    }

    /**
     * Gets the processed detail information of the successful transaction.
     *
     * @return The successful processed details of the transaction.
     */
    public Map getProcessed() {
        return super.getProcessed();
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
        return super.getActionValues();
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
        return super.getActionValueAtIndex(index, clazz);
    }
}