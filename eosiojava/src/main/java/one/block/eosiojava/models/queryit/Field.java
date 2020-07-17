package one.block.eosiojava.models.queryit;

import com.google.gson.annotations.SerializedName;

public class Field {
    @SerializedName("name")
    private String name;

    @SerializedName("value")
    private QueryIt value;

    public QueryIt getValue() { return this.value; }

    public String getName() { return this.name; }
}
