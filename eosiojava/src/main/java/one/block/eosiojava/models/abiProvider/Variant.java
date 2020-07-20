package one.block.eosiojava.models.abiProvider;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * The Abi type variants
 */
public class Variant {
    /**
     * The name
     */
    @SerializedName("name")
    private String name;

    /**
     * The types
     */
    @SerializedName("types")
    private List<String> types;

    /**
     * Gets the name
     * @return the name
     */
    public String getName() { return this.name; }

    /**
     * Gets the types
     * @return the types
     */
    public List<String> getTypes() { return this.types; }

    /**
     *
     * @param variantName the variant name
     * @return true if this variant's name equals value looking for
     */
    public Boolean hasName(String variantName) { return this.name.equals(variantName); }
}
