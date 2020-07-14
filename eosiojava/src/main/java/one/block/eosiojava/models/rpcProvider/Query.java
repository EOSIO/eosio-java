package one.block.eosiojava.models.rpcProvider;

import java.io.Serializable;
import java.util.ArrayList;

public class Query implements Serializable {
    private String account;
    private String data;

    public String getData() {
        return this.data;
    }

    public String getAccount() {
        return this.account;
    }

    public Action toAction() {
        return new Action(this.getAccount(), "queryit", new ArrayList<>(), this.getData());
    }
}