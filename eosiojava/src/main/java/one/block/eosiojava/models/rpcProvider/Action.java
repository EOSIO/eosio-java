package one.block.eosiojava.models.rpcProvider;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * The Action which has data about action of an account with hex/json data for the detail
 */
public class Action implements Serializable {

    /**
     * The Account.
     */
    @SerializedName("account")
    @NotNull
    private String account;

    /**
     * The Name.
     */
    @SerializedName("name")
    @NotNull
    private String name;

    /**
     * The Authorization.
     */
    @SerializedName("authorization")
    @NotNull
    private List<Authorization> authorization;

    /**
     * The Data. <br/> This field could hold hex or json string depend on the step on the processing
     * flow. <br/> JSON type: it is the un-serialized version which has action data for an action in
     * smart contract Hex type: it is the serialized version of JSON type which is serialized by
     * SerializationProvider <br/> Check "Complete workflow" diagram for more information
     */
    @SerializedName("data")
    @NotNull
    private String data;

    /**
     * Instantiates a new Action.
     *
     * @param account the account
     * @param name the name
     * @param authorization the authorization
     * @param data the data
     */
    public Action(@NotNull String account, @NotNull String name,
            @NotNull List<Authorization> authorization, @NotNull String data) {
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
    @NotNull
    public String getAccount() {
        return account;
    }

    /**
     * Sets account.
     *
     * @param account the account
     */
    public void setAccount(@NotNull String account) {
        this.account = account;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    @NotNull
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(@NotNull String name) {
        this.name = name;
    }

    /**
     * Gets authorization.
     *
     * @return the authorization
     */
    @NotNull
    public List<Authorization> getAuthorization() {
        return authorization;
    }

    /**
     * Sets authorization.
     *
     * @param authorization the authorization
     */
    public void setAuthorization(
            @NotNull List<Authorization> authorization) {
        this.authorization = authorization;
    }

    /**
     * Gets data.
     *
     * @return the data
     */
    @NotNull
    public String getData() {
        return data;
    }

    /**
     * Sets data.
     *
     * @param data the data
     */
    public void setData(@NotNull String data) {
        this.data = data;
    }
}
