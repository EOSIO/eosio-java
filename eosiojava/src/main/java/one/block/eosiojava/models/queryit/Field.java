package one.block.eosiojava.models.queryit;

import com.google.gson.annotations.SerializedName;

public class Field {
    @SerializedName("name")
    private String name;

    @SerializedName("value")
    private AnyVar value;

    public AnyVar getValue() { return this.value; }

    public Object getAnyVarValue() { return this.value != null ? this.value.getValue() : null; }

    public Boolean hasPrimitiveValue() { return this.getValue().hasValue(); }

    public Boolean hasType() { return this.getValue() != null && this.getValue().getType() != null; }

    public String getType() { return this.getValue().getType(); }

    public Boolean hasType(String type) { return this.hasType() && this.getType().equals(type); }

    public String getName() { return this.name; }
}
