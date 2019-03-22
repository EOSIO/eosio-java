package one.block.eosiojava.models.rpcProvider.response;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import one.block.eosiojava.models.rpcProvider.Authorization;

/**
 * The type Action trace action.
 */
public class ActionTraceAction {

    /**
     * The Account.
     */
    @SerializedName("account")
    private String account;

    /**
     * The Name.
     */
    @SerializedName("name")
    private String name;

    /**
     * The Authorization.
     */
    @SerializedName("authorization")
    private List<Authorization> authorization;

    /**
     * The Data.
     */
    @SerializedName("data")
    private Object data;

    /**
     * The Hex data.
     */
    @SerializedName("hex_data")
    private String hexData;

    /**
     * Gets account.
     *
     * @return the account
     */
    public String getAccount() {
        return account;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets authorization.
     *
     * @return the authorization
     */
    public List<Authorization> getAuthorization() {
        return authorization;
    }

    /**
     * Gets data.
     *
     * @return the data
     */
    public Object getData() {
        return data;
    }

    /**
     * Gets hex data.
     *
     * @return the hex data
     */
    public String getHexData() {
        return hexData;
    }
}
