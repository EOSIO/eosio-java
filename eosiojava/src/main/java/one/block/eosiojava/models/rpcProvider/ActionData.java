package one.block.eosiojava.models.rpcProvider;

import org.jetbrains.annotations.NotNull;

/**
 * The Action data which present json string action data of an action in a smart contract
 */
public class ActionData {

    /**
     * The Json object which will be serialized by SerializationProvider
     */
    @NotNull
    private String json;

    /**
     * Instantiates a new Action data.
     *
     * @param json the json
     */
    public ActionData(@NotNull String json) {
        this.json = json;
    }

    /**
     * Gets json object.
     *
     * @return the json object
     */
    @NotNull
    public String getJsonObject() {
        return json;
    }

    /**
     * Sets json object.
     *
     * @param json the json object
     */
    public void setJsonObject(@NotNull String json) {
        this.json = json;
    }
}
