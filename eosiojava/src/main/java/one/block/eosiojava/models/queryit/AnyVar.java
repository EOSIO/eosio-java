package one.block.eosiojava.models.queryit;

import java.util.ArrayList;
import java.util.List;

/**
 * AnyVar in a queryit abi/json
 */
public class AnyVar {

    /**
     * The variant type of data
     */
    private String type;
    /**
     * Name of the field
     */
    private String name;
    /**
     * Dynamic value based on type
     */
    private Object value;

    /**
     * List of fields that this object may contain
     */
    private List<Field> fields;
    /**
     * List of sub anyVars this object may contain
     */
    private List<AnyVar> anyVars;

    /**
     * Create a new anyVar with empty lists
     */
    public AnyVar() {
        fields = new ArrayList<Field>();
        anyVars = new ArrayList<AnyVar>();
    }

    /**
     * Get the name
     * @return - the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the name
     * @param name - the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the dynamic value
     * @return - the value
     */
    public Object getValue() {
        return this.value;
    }

    /**
     * Set the value
     * @param value - the value
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * Get the variant type
     * @return - the type
     */
    public String getType() {
        return this.type;
    }

    /**
     * Set the variant type
     * @param type - the type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Add a new field
     * @param field - the field to add
     */
    public void addField(Field field) {
        fields.add(field);
    }

    /**
     * Get the fields
     * @return - the fields
     */
    public List<Field> getFields() {
        return this.fields;
    }

    /**
     * Add a new sub anyVar
     * @param anyVar - the anyVar
     */
    public void addAnyVar(AnyVar anyVar) {
        anyVars.add(anyVar);
    }

    /**
     * Get the anyVars
     * @return - the anyVars
     */
    public List<AnyVar> getAnyVars() {
        return this.anyVars;
    }

    /**
     * Determine whether or not this object is empty
     * The object is empty if there is no name, value, and no collections
     * @return - whether or not this object is empty
     */
    public Boolean isEmpty() {
        return this.name == null && this.value == null && this.getFields().size() == 0 && this.getAnyVars().size() == 0;
    }

    /**
     * Determine whether or not there is a value
     * If there isn't, it means that this object must contain data in fields or anyVars
     * @return - whether or not there is a value
     */
    public Boolean hasValue() {
        return this.value != null;
    }
}
