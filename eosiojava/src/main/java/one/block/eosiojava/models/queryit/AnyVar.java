package one.block.eosiojava.models.queryit;

import java.util.ArrayList;
import java.util.List;

public class AnyVar {
    private String type;
    private String name;
    private Object value;

    private List<Field> fields;
    private List<AnyVar> anyVars;

    public AnyVar() {
        fields = new ArrayList<Field>();
        anyVars = new ArrayList<AnyVar>();
    }

    public Boolean hasValue() {
        return this.value != null;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void addField(Field queryIt) {
        fields.add(queryIt);
    }

    public List<Field> getFields() {
        return this.fields;
    }

    public void addAnyVar(AnyVar anyVar) {
        anyVars.add(anyVar);
    }

    public List<AnyVar> getAnyVars() {
        return this.anyVars;
    }

    public Boolean isEmpty() {
        return this.name == null && this.value == null && this.getFields().size() == 0 && this.getAnyVars().size() == 0;
    }
}
