package one.block.eosiojava.models.abiProvider;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;

public class Abi {
    /**
     * The comment
     */
    @SerializedName("____comment")
    private String comment;

    /**
     * The version
     */
    @SerializedName("version")
    private String version;

    /**
     * The types
     */
    @SerializedName("types")
    private List<Map> types;

    /**
     * The structs
     */
    @SerializedName("structs")
    private List<Map> structs;

    /**
     * The actions
     */
    @SerializedName("actions")
    private List<Map> actions;

    /**
     * The tables
     */
    @SerializedName("tables")
    private List<Map> tables;

    /**
     * The ricardian clauses
     */
    @SerializedName("ricardian_clauses")
    private List<Map> ricardianClauses;

    /**
     * The variants
     */
    @SerializedName("variants")
    private List<Map> variants;

    /**
     * The action results
     */
    @SerializedName("action_results")
    private List<ActionResult> actionResults;

    /**
     * Gets the comment
     * @return the comment
     */
    public String getComment() { return this.comment; }

    /**
     * Gets the version
     * @return the version
     */
    public String getVersion() { return this.version; }

    /**
     * Gets the types
     * @return the types
     */
    public List<Map> getTypes() { return this.types; }

    /**
     * Gets the structs
     * @return the structs
     */
    public List<Map> getStructs() { return this.structs; }

    /**
     * Gets the actions
     * @return the actions
     */
    public List<Map> getActions() { return this.actions; }

    /**
     * Gets the tables
     * @return the tables
     */
    public List<Map> getTables() { return this.tables; }

    /**
     * Gets the ricardianClauses
     * @return the ricardianClauses
     */
    public List<Map> getRicardianClauses() { return this.ricardianClauses; }

    /**
     * Gets the variants
     * @return the variants
     */
    public List<Map> getVariants() { return this.variants; }

    /**
     * Gets the actionResults
     * @return the actionResults
     */
    public List<ActionResult> getActionResults() { return this.actionResults; }

    /**
     * Gets the return type of the specified action
     * @param actionName - action to check for return type
     */
    public String getActionReturnTypeByActionName(String actionName) {
        if (this.actionResults.isEmpty()) {
            return null;
        }

        ActionResult actionResult = this.actionResults.stream()
                                                      .filter(ar -> ar.hasActionName(actionName))
                                                      .findFirst()
                                                      .orElse(null);

        return actionResult != null ? actionResult.getReturnType() : null;
    }
}
