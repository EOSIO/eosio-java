package one.block.eosiojava.models.queryit;

import com.google.gson.annotations.SerializedName;

/**
 * Field in a queryit abi/json
 */
public class Field {

    /**
     * The name of the field
     */
    @SerializedName("name")
    private String name;

    /**
     * The value of the field, which might be a primitive, object, or collection
     */
    @SerializedName("value")
    private AnyVar value;

    /**
     * Get the name
     * @return - the name
     */
    public String getName() { return this.name; }

    /**
     * Set the name
     * @param name - the name
     */
    public void setName(String name) { this.name = name; }

    /**
     * Get the value
     * @return - the value
     */
    public AnyVar getValue() { return this.value; }

    /**
     * Set the value
     * @param value - the value
     */
    public void setValue(AnyVar value) { this.value = value; }

    /**
     * Determine whether or not there is a value
     * @return - whether or not there is a value
     */
    public Boolean hasValue() { return this.value != null; }

    /**
     * Determine whether or not this field has any type
     * @return - whether or not this field has any type
     */
    public Boolean hasType() { return this.hasValue() && this.getValue().getType() != null; }

    /**
     * Determine whether or not this field has the given type
     * @param type - the type to check
     * @return - whether or not this field has the given type
     */
    public Boolean hasType(String type) { return this.hasType() && this.getType().equals(type); }

    /**
     * Get the type from the anyVar
     * @return - the type
     */
    public String getType() { return this.hasValue() ? this.getValue().getType() : null; }

    /**
     * Get the anyVar's value if it exists
     * @return - the value if it exists
     */
    public Object getAnyVarValue() { return this.hasValue() ? this.getValue().getValue() : null; }

    /**
     * Determine whether or not this is a primitive field, meaning the anyVar has a direct value instead of collections
     * @return - whether or not this is a primitive field
     */
    public Boolean hasPrimitiveValue() { return this.hasValue() ? this.getValue().hasValue() : false; }
}
