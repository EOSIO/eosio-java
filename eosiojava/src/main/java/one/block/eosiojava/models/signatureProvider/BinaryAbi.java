package one.block.eosiojava.models.signatureProvider;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;

/**
 * This class hold data of ABI along with its contract account name to be passed to Signature Provider inside {@link EosioTransactionSignatureRequest}
 */
public class BinaryAbi {

    /**
     * The contract account name.
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
     * @param accountName the contract account name
     * @param abi the abi
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
    public String getAccontName() {
        return accountName;
    }

    /**
     * Sets contract account name.
     *
     * @param accountName the contract account name
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
