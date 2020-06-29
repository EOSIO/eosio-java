package one.block.eosiojava.models.abiProvider;

import com.google.gson.annotations.SerializedName;

public class ActionResult {

    /**
     * The name
     */
    @SerializedName("name")
    private String name;

    /**
     * The return value type
     */
    @SerializedName("result_type")
    private String resultType;

    /**
     * Gets the name
     * @return the name
     */
    public String getName() { return this.name; }

    /**
     * Gets the return value type
     * @return the return value type
     */
    public String getReturnType() { return this.resultType; }

    /**
     * Determines if this action's name matches
     * @return true if action name matches; false otherwise
     */
    public Boolean hasActionName(String actionName) { return this.name.equals(actionName); }
}
