package one.block.eosiojava.models.abiProvider;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;

public class Abi {
    @SerializedName("____comment")
    private String comment;

    @SerializedName("version")
    private String version;

    @SerializedName("types")
    private List<Map> types;

    @SerializedName("structs")
    private List<Map> structs;

    @SerializedName("actions")
    private List<Map> actions;

    @SerializedName("tables")
    private List<Map> tables;

    @SerializedName("ricardian_clauses")
    private List<Map> ricardianClauses;

    @SerializedName("variants")
    private List<Map> variants;

    @SerializedName("action_results")
    private List<ActionResult> actionResults;

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
