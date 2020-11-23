package one.block.eosiojava.models.rpcProvider;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * The action to execute on the contract.
 */
public class Action implements Serializable {

    /**
     * The contract account name.
     */
    @SerializedName("account")
    @NotNull
    private String account;

    /**
     * The Contract action name.
     */
    @SerializedName("name")
    @NotNull
    private String name;

    /**
     * The Authorization (actor and permission) to make transaction
     */
    @SerializedName("authorization")
    @NotNull
    private List<Authorization> authorization;

    /**
     * The Action Data are the arguments to the contract.<br> This field could hold hex or json
     * string depending on the step in the processing flow. <br> JSON type: This is the un-serialized
     * version which has action data for an action in smart contract. Hex type: This is the serialized
     * version of JSON type which is serialized by SerializationProvider. <br> Check the
     * "Complete Workflow" diagram for more information.
     */
    @SerializedName("data")
    @NotNull
    private String data;

    /**
     * Whether or not this action is a contextFree action.
     */
    @SerializedName("isContextFree")
    @NotNull
    private boolean isContextFree;

    /**
     * Action return value after transaction processing.
     */
    private transient Object returnValue;

    /**
     * Instantiates a new action.
     *
     * @param account the Contract account name.
     * @param name the Contract action name.
     * @param authorization the Authorization (actor and permission) to make transaction
     * @param data the action data (arguments to the contract)
     * @param isContextFree whether or not the action is context free
     */
    public Action(@NotNull String account, @NotNull String name,
            @NotNull List<Authorization> authorization, @NotNull String data,
            @NotNull boolean isContextFree) {
        this.account = account;
        this.name = name;
        this.authorization = authorization;
        this.data = data;
        this.isContextFree = isContextFree;
    }

    public Action(@NotNull String account, @NotNull String name,
            @NotNull List<Authorization> authorization, @NotNull String data) {
        this(account, name, authorization, data, false);
    }

    /**
     * Gets contract account name.
     *
     * @return the contract account name.
     */
    @NotNull
    public String getAccount() {
        return account;
    }

    /**
     * Sets contract account name.
     *
     * @param account the contract account name.
     */
    public void setAccount(@NotNull String account) {
        this.account = account;
    }

    /**
     * Gets contract action name.
     *
     * @return the contact action name.
     */
    @NotNull
    public String getName() {
        return name;
    }

    /**
     * Sets contact action name.
     *
     * @param name the contact action name.
     */
    public void setName(@NotNull String name) {
        this.name = name;
    }

    /**
     * Gets authorization (actor and permission) to make transaction.
     *
     * @return the authorization (actor and permission) to make transaction.
     */
    @NotNull
    public List<Authorization> getAuthorization() {
        return authorization;
    }

    /**
     * Sets authorization (actor and permission) to make transaction
     *
     * @param authorization the authorization (actor and permission) to make transaction.
     */
    public void setAuthorization(
            @NotNull List<Authorization> authorization) {
        this.authorization = authorization;
    }

    /**
     * Gets action data.
     *
     * @return the action data.
     */
    @NotNull
    public String getData() {
        return data;
    }

    /**
     * Sets action data.
     *
     * @param data the action data.
     */
    public void setData(@NotNull String data) {
        this.data = data;
    }

    /**
     * Gets whether or not this action has data
     *
     * @return whether or not this action has data
     */
    public boolean hasData() { return !this.data.isEmpty(); }

    /**
     * Gets whether or not this action is context free.
     *
     * @return whether or not this action is context free.
     */
    @NotNull
    public boolean getIsContextFree() { return this.isContextFree; }

    /**
     * Sets whether or not this action is context free.
     *
     * @param isContextFree whether or not this action is context free.
     */
    public void setIsContextFree(@NotNull boolean isContextFree) { this.isContextFree = isContextFree; }

    /**
     * Gets the action's return value after transaction processing.
     * @return returnValue action return value after transaction processing.  If there wasn't one, returns null.
     */
    public Object getReturnValue() {
        return returnValue;
    }

    /**
     * Sets the action return value.
     * @param returnValue return value of the action after transaction processing, if there is one.  If there is not,
     *                    the value should be null.
     */
    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
    }

}
