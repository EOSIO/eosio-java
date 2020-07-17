package one.block.eosiojava.models.queryit;


import java.util.ArrayList;
import java.util.List;

public class QueryIt {
    public Object value;

    public List<QueryIt> queryIts;

    public QueryIt() {
        queryIts = new ArrayList<QueryIt>();
    }

    public Object getValue() {
        return this.value;
    }

    public void addQueryIt(QueryIt queryIt) {
        queryIts.add(queryIt);
    }
}
