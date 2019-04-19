package one.block.eosiojava.models;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The request/response object to pass to Serialization Provider for generic serialization/deserialization
 */
public class AbiEosSerializationObject {

    /**
     * A string representing contract name for the serialize action lookup for the ABIEOS conversion.
     */
    private @Nullable String contract;

    /**
     * A string representing an action name that is used in conjunction with contract (above) to derive the serialize type name.
     */
    private @NotNull String name;

    /**
     * A string representing the type name for the serialize action lookup for this serialize conversion.
     */
    private @Nullable String type;

    /**
     * The Hex String to deserialize to a JSON String.
     * <br>
     *     Leave this field blank for serialization.
     */
    private @NotNull String hex = "";

    /**
     * The JSON data String to serialize to binary.
     * <br>
     *     Leave this field blank for deserialization.
     */
    private @NotNull String json = "";

    /**
     * A String representation of the ABI to use for conversion.
     */
    private @NotNull String abi;

    /**
     * Gets contract.
     * <br>
     *     A string representing contract name for the serialize action lookup for the ABIEOS conversion.
     * @return the contract.
     */
    @Nullable
    public String getContract() {
        return contract;
    }

    /**
     * Gets name.
     * <br>
     *     A string representing an action name that is used in conjunction with contract (above) to derive the serialize type name.
     *
     * @return the action name.
     */
    @NotNull
    public String getName() {
        return name;
    }

    /**
     * Gets type.
     * <br>
     *     A string representing the type name for the serialize action lookup for this serialize conversion.
     *
     * @return the type.
     */
    @Nullable
    public String getType() {
        return type;
    }

    /**
     * Gets Hex.
     * <br>
     * The Hex String to deserialize to a JSON String.
     * <br>
     *     This field is blank for serialization.
     *
     * @return the hex.
     */
    @NotNull
    public String getHex() {
        return hex;
    }

    /**
     * Gets the json.
     * <br>
     * The JSON data String to serialize to binary.
     * <br>
     *     Leave this field blank for deserialization.
     * @return the json.
     */
    @NotNull
    public String getJson() {
        return json;
    }

    /**
     * Gets the abi.
     * <br>
     *     A String representation of the ABI to use for conversion.
     * @return the abi.
     */
    @NotNull
    public String getAbi() {
        return abi;
    }

    /**
     * Sets Hex.
     * <br>
     * The Hex String to deserialize to a JSON String.
     * <br>
     *     Set this field to blank for serialization.
     *
     * @param hex - input hex value.
     */
    public void setHex(@NotNull String hex) {
        this.hex = hex;
    }

    /**
     * Set json.
     * <br>
     * The JSON data String to serialize to binary.
     * <br>
     *     Set this field to blank for deserialization.
     * @param json - input json value.
     */
    public void setJson(@NotNull String json) {
        this.json = json;
    }

    /**
     * Initialize AbiEosSerializationObject
     *
     * @param contract - the contract name.
     * @param name - the action name.
     * @param type - the type name.
     * @param abi - the abi to use for conversion.
     */
    public AbiEosSerializationObject(@Nullable String contract,
            @NotNull String name, @Nullable String type,
            @NotNull String abi) {
        this.contract = contract;
        this.name = name;
        this.type = type;
        this.abi = abi;
    }

}
