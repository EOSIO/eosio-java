package one.block.eosiojava.models.rpcprovider.response;

import com.google.gson.annotations.SerializedName;
import one.block.eosiojava.models.rpcprovider.request.GetRawAbiRequest;

/**
 * The response of getRawAbi() RPC call {@link one.block.eosiojava.interfaces.IRPCProvider#getRawAbi(GetRawAbiRequest)}
 */
public class GetRawAbiResponse {

    /**
     * The account name (contract name) found in {@link one.block.eosiojava.models.EOSIOName}
     */
    @SerializedName("account_name")
    private String accountName;

    @SerializedName("code_hash")
    private String codeHash;

    @SerializedName("abi_hash")
    private String abiHash;

    /**
     * The ABI (raw ABI) of the account name (contract name)
     * <br/>
     * This ABI is used to serialize a contract's action data.
     */
    @SerializedName("abi")
    private String abi;

    /**
     * Gets the account name (contract name) found in {@link one.block.eosiojava.models.EOSIOName}
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
     * Gets The ABI (raw ABI) of the account name (contract name).
     * <br/>
     *      This ABI is used to serialize a contract's action data.
     *
     * @return the raw ABI
     */
    public String getAbi() {
        return abi;
    }
}
