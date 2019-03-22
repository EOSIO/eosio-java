package one.block.eosiojava.models.rpcProvider;

/**
 * The Action data which present json string action data of an action in a smart contract
 */
public class ActionData {

    /**
     * The Json object which will be serialized by SerializationProvider
     */
    private String jsonObject;

    /**
     * Instantiates a new Action data.
     *
     * @param jsonObject the json object
     */
    public ActionData(String jsonObject) {
        this.jsonObject = jsonObject;
    }

    /**
     * Gets json object.
     *
     * @return the json object
     */
    public String getJsonObject() {
        return jsonObject;
    }

    /**
     * Sets json object.
     *
     * @param jsonObject the json object
     */
    public void setJsonObject(String jsonObject) {
        this.jsonObject = jsonObject;
    }
}
