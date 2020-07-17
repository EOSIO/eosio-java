package one.block.eosiojava.models.queryit;


import java.util.ArrayList;
import java.util.List;

public class QueryIt {
    private String name;
    private Object value;

    private List<Field> fields;
    private List<QueryIt> queries;

    public QueryIt() {
        fields = new ArrayList<Field>();
        queries = new ArrayList<QueryIt>();
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

    public void addField(Field queryIt) {
        fields.add(queryIt);
    }

    public List<Field> getFields() {
        return this.fields;
    }

    public void addQueryIt(QueryIt queryIt) {
        queries.add(queryIt);
    }

    public List<QueryIt> getQueries() {
        return this.queries;
    }
}
