package one.block.eosiojava.models;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The request/response object to pass to Serialization Provider for generic serialization/deserialization
 */
public class AbiEosSerializationObject {

    private @Nullable String contract;
    private @NotNull String name;
    private @Nullable String type;
    private @NotNull String hex = "";
    private @NotNull String json = "";
    private @NotNull String abi;

    @Nullable
    public String getContract() {
        return contract;
    }

    @NotNull
    public String getName() {
        return name;
    }

    @Nullable
    public String getType() {
        return type;
    }

    @NotNull
    public String getHex() {
        return hex;
    }

    @NotNull
    public String getJson() {
        return json;
    }

    @NotNull
    public String getAbi() {
        return abi;
    }

    public void setHex(@NotNull String hex) {
        this.hex = hex;
    }

    public void setJson(@NotNull String json) {
        this.json = json;
    }

    public AbiEosSerializationObject(@Nullable String contract,
            @NotNull String name, @Nullable String type,
            @NotNull String abi) {
        this.contract = contract;
        this.name = name;
        this.type = type;
        this.abi = abi;
    }

}
