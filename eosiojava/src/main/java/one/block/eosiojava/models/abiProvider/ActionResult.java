package one.block.eosiojava.models.abiProvider;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;

public class ActionResult {
    @NotNull
    @SerializedName("name")
    private String name;

    @NotNull
    @SerializedName("result_type")
    private String resultType;

    public Boolean hasActionName(String actionName) {
        return this.name.equals(actionName);
    }

    public String getReturnType() {
        return this.resultType;
    }
}
