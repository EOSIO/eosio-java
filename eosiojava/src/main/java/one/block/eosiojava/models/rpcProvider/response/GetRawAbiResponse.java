package one.block.eosiojava.models.rpcProvider.response;

import com.google.gson.annotations.SerializedName;
import one.block.eosiojava.models.rpcProvider.request.GetRawAbiRequest;

/**
 * The response of GetRawAbi RPC call {@link one.block.eosiojava.interfaces.IRPCProvider#getRawAbi(GetRawAbiRequest)}
 */
public class GetRawAbiResponse {

    /**
     * The Account name (Contract name) which in EOSIO name type
     */
    @SerializedName("account_name")
    private String accountName;

    @SerializedName("code_hash")
    private String codeHash;

    @SerializedName("abi_hash")
    private String abiHash;

    /**
     * The Abi (Raw abi) of the account name (Contract name)
     * <br/>
     * This abi is used to serialize contract action's data.
     */
    @SerializedName("abi")
    private String abi;

    /**
     * Gets the Account name (Contract name) which in EOSIO name type
     *
     * @return the account name.
     */
    public String getAccountName() {
        return accountName;
    }

    public String getCodeHash() {
        return codeHash;
    }

    public String getAbiHash() {
        return abiHash;
    }

    /**
     * Gets The Abi (Raw abi) of the account name (Contract name).
     * <br/>
     *      This abi is used to serialize contract action's data.
     *
     * @return the raw abi
     */
    public String getAbi() {
        return abi;
    }
}
