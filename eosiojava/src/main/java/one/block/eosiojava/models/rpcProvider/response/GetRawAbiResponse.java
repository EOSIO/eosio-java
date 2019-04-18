package one.block.eosiojava.models.rpcProvider.response;

import com.google.gson.annotations.SerializedName;

/**
 * The response of GetRawAbi RPC call.
 */
public class GetRawAbiResponse {

    /**
     * The Account name (Contract name)
     */
    @SerializedName("account_name")
    private String accountName;

    /**
     * The Code hash.
     */
    @SerializedName("code_hash")
    private String codeHash;

    /**
     * The Abi hash.
     */
    @SerializedName("abi_hash")
    private String abiHash;

    /**
     * The Abi (Raw abi) of the account name (Contract name)
     * <br>
     * This abi is used to serialize contract action's data.
     */
    @SerializedName("abi")
    private String abi;


    /**
     * Gets account name.
     *
     * @return the account name
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * Gets code hash.
     *
     * @return the code hash
     */
    public String getCodeHash() {
        return codeHash;
    }

    /**
     * Gets abi hash.
     *
     * @return the abi hash
     */
    public String getAbiHash() {
        return abiHash;
    }

    /**
     * Gets abi.
     *
     * @return the abi
     */
    public String getAbi() {
        return abi;
    }
}
