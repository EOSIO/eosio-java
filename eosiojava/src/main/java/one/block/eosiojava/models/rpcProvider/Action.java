package one.block.eosiojava.models.rpcProvider;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

/**
 * The Action which has data about action of an account with hex/json data for the detail
 */
public class Action implements Serializable {

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
     * The Data. <br/> This field could hold hex or json string depend on the step on the processing
     * flow. <br/> JSON type: it is the un-serialized version which has action data for an action in
     * smart contract Hex type: it is the serialized version of JSON type which is serialized by
     * SerializationProvider <br/> Check "Complete workflow" diagram for more information
     */
    @SerializedName("data")
    private String data;

    /**
     * Instantiates a new Action.
     *
     * @param account the account
     * @param name the name
     * @param authorization the authorization
     * @param data the data
     */
    public Action(String account, String name,
            List<Authorization> authorization, String data) {
        this.account = account;
        this.name = name;
        this.authorization = authorization;
        this.data = data;
    }

    /**
     * Gets account.
     *
     * @return the account
     */
    public String getAccount() {
        return account;
    }

    /**
     * Sets account.
     *
     * @param account the account
     */
    public void setAccount(String account) {
        this.account = account;
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
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
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
     * Sets authorization.
     *
     * @param authorization the authorization
     */
    public void setAuthorization(
            List<Authorization> authorization) {
        this.authorization = authorization;
    }

    /**
     * Gets data.
     *
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * Sets data.
     *
     * @param data the data
     */
    public void setData(String data) {
        this.data = data;
    }
}
