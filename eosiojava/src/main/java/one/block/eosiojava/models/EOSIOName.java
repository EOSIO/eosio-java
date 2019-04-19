package one.block.eosiojava.models;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

/**
 * Class holds block chain account name
 */
public class EOSIOName {

    @NotNull private String accountName;

    /**
     * Initialize EOSIOName object with account name
     *
     * @param accountName - input account name
     */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EOSIOName eosioName = (EOSIOName) o;
        return getAccountName().equals(eosioName.getAccountName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccountName());
    }
}
