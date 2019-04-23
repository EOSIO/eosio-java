package one.block.eosiojava.models.rpcProvider.response;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import one.block.eosiojava.models.rpcProvider.request.GetRequiredKeysRequest;

/**
 * The response of getRequiredKeys() RPC call {@link one.block.eosiojava.interfaces.IRPCProvider#getRequiredKeys(GetRequiredKeysRequest)}
 */
public class GetRequiredKeysResponse {

    /**
     * The required public EOSIO keys to sign the transaction. It gets assigned to {@link
     * one.block.eosiojava.models.signatureProvider.EosioTransactionSignatureRequest#setSigningPublicKeys(List)},
     * which is passed to a Signature Provider to sign a transaction.
     */
    @SerializedName("required_keys")
    private List<String> requiredKeys;

    /**
     * Gets the required public EOSIO keys to sign the transaction. It gets assigned to {@link
     * one.block.eosiojava.models.signatureProvider.EosioTransactionSignatureRequest#setSigningPublicKeys(List)},
     * which is passed to a Signature Provider to sign a transaction.
     * @return The required public EOSIO keys.
     */
    public List<String> getRequiredKeys() {
        return requiredKeys;
    }
}
