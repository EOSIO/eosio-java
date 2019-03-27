package one.block.eosiojava.models.signatureProvider;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;

/**
 * The type Binary abi.
 */
public class BinaryAbi {

    /**
     * The Account name.
     */
    @SerializedName("account_name")
    @NotNull
    private String accountName;

    /**
     * The Abi.
     */
    @SerializedName("abi")
    @NotNull
    private String abi;

    /**
     * Instantiates a new Binary abi.
     *
     * @param accountName the account name
     * @param abi the abi
     */
    public BinaryAbi(@NotNull String accountName, @NotNull String abi) {
        this.accountName = accountName;
        this.abi = abi;
    }

    /**
     * Gets accont name.
     *
     * @return the accont name
     */
    @NotNull
    public String getAccontName() {
        return accountName;
    }

    /**
     * Sets accont name.
     *
     * @param accountName the account name
     */
    public void setAccontName(@NotNull String accountName) {
        this.accountName = accountName;
    }

    /**
     * Gets abi.
     *
     * @return the abi
     */
    @NotNull
    public String getAbi() {
        return abi;
    }

    /**
     * Sets abi.
     *
     * @param abi the abi
     */
    public void setAbi(@NotNull String abi) {
        this.abi = abi;
    }
}
