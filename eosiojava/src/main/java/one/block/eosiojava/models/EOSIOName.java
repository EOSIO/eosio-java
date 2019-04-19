package one.block.eosiojava.models;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

/**
 * Class holds block chain account name
 */
public class EOSIOName {

    /**
     * EOSIO account name in String format.
     */
    @NotNull private String accountName;

    /**
     * Initialize EOSIOName object with EOSIO account name in String format
     *
     * @param accountName - input EOSIO account name in String format.
     */
    public EOSIOName(@NotNull String accountName) {
        this.accountName = accountName;
    }

    /**
     * Get EOSIO account name in String format.
     *
     * @return EOSIO account name in String format.
     */
    @NotNull
    public String getAccountName() {
        return accountName;
    }

    /**
     * Get EOSIO account name
     *
     * @param accountName input EOSIO account name in string format.
     */
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
