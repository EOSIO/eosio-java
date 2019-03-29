package one.block.eosiojava.models;

import org.jetbrains.annotations.NotNull;

public class EOSIOName {

    @NotNull private String accountName;

    public EOSIOName(@NotNull String accountName) {
        this.accountName = accountName;
    }

    @NotNull
    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(@NotNull String accountName) {
        this.accountName = accountName;
    }
}
