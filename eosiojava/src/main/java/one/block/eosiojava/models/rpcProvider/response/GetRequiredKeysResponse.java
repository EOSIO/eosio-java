package one.block.eosiojava.models.rpcProvider.response;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * The response of GetRequiredKeys RPC call.
 */
public class GetRequiredKeysResponse {

    /**
     * The Required keys to sign the transaction
     */
    @SerializedName("required_keys")
    private List<String> requiredKeys;

    /**
     * Gets required keys.
     *
     * @return the required keys
     */
    public List<String> getRequiredKeys() {
        return requiredKeys;
    }
}
