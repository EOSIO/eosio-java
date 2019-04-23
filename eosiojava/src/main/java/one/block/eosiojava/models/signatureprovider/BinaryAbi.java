package one.block.eosiojava.models.signatureprovider;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;

/**
 * This class holds data of the ABI and its contract account name that is passed to a Signature
 * Provider inside {@link EosioTransactionSignatureRequest}
 */
public class BinaryAbi {

    /**
     * The contract account name.
     */
    @SerializedName("account_name")
    @NotNull
    private String accountName;

    /**
     * The ABI.
     */
    @SerializedName("abi")
    @NotNull
    private String abi;

    /**
     * Instantiates a new Binary abi.
     *
     * @param accountName the contract account name
     * @param abi the ABI
     */
    public BinaryAbi(@NotNull String accountName, @NotNull String abi) {
        this.accountName = accountName;
        this.abi = abi;
    }

    /**
     * Gets contract account name.
     *
     * @return the contract account name
     */
    @NotNull
    public String getAccountName() {
        return accountName;
    }

    /**
     * Sets contract account name.
     *
     * @param accountName the contract account name
     */
    public void setAccountName(@NotNull String accountName) {
        this.accountName = accountName;
    }

    /**
     * Gets ABI.
     *
     * @return the ABI
     */
    @NotNull
    public String getAbi() {
        return abi;
    }

    /**
     * Sets ABI.
     *
     * @param abi the ABI
     */
    public void setAbi(@NotNull String abi) {
        this.abi = abi;
    }
}
