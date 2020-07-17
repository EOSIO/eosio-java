package one.block.eosiojava.models.queryit;


import java.util.ArrayList;
import java.util.List;

public class QueryIt {
    private String name;
    private Object value;

    private List<QueryIt> subQueries;

    public QueryIt() {
        subQueries = new ArrayList<QueryIt>();
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

    public void addQueryIt(QueryIt queryIt) {
        subQueries.add(queryIt);
    }

    public List<QueryIt> getSubQueries() {
        return this.subQueries;
    }
}
