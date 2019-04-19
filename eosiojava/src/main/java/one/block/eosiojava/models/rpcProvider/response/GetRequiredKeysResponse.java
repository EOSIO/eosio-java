package one.block.eosiojava.models.rpcProvider.response;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import one.block.eosiojava.models.rpcProvider.request.GetRequiredKeysRequest;

/**
 * The response of GetRequiredKeys RPC call {@link one.block.eosiojava.interfaces.IRPCProvider#getRequiredKeys(GetRequiredKeysRequest)}
 */
public class GetRequiredKeysResponse {

    /**
     * The Required public EOSIO keys to sign the transaction. It get assigned to {@link
     * one.block.eosiojava.models.signatureProvider.EosioTransactionSignatureRequest#setSigningPublicKey(List)}
     * which passed to Signature Provider top sign a transaction
     */
    @SerializedName("required_keys")
    private List<String> requiredKeys;

    /**
     * Gets the Required public EOSIO keys to sign the transaction. It get assigned to {@link
     * one.block.eosiojava.models.signatureProvider.EosioTransactionSignatureRequest#setSigningPublicKey(List)}
     * which passed to Signature Provider top sign a transaction
     * @return the required public EOSIO keys.
     */
    public List<String> getRequiredKeys() {
        return requiredKeys;
    }
}
