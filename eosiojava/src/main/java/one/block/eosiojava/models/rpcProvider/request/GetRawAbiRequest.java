package one.block.eosiojava.models.rpcProvider.request;

import com.google.gson.annotations.SerializedName;

/**
 * The request class of GetRawAbi RPC call
 */
public class GetRawAbiRequest {

    /**
     * Instantiates a new Get raw abi request.
     *
     * @param accountName the account name
     */
    public GetRawAbiRequest(String accountName) {
        this.accountName = accountName;
    }

    /**
     * The Account name.
     */
    @SerializedName("account_name")
    private String accountName;

    /**
     * Gets account name.
     *
     * @return the account name
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * Sets account name.
     *
     * @param accountName the account name
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
