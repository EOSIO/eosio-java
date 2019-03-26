package one.block.eosiojava.models.signatureProvider;

/**
 * The type Binary abi.
 */
public class BinaryAbi {

    /**
     * The Account name.
     */
    private String accountName;

    /**
     * The Abi.
     */
    private String abi;

    /**
     * Instantiates a new Binary abi.
     *
     * @param accountName the account name
     * @param abi the abi
     */
    public BinaryAbi(String accountName, String abi) {
        this.accountName = accountName;
        this.abi = abi;
    }

    /**
     * Gets accont name.
     *
     * @return the accont name
     */
    public String getAccontName() {
        return accountName;
    }

    /**
     * Sets accont name.
     *
     * @param accountName the account name
     */
    public void setAccontName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * Gets abi.
     *
     * @return the abi
     */
    public String getAbi() {
        return abi;
    }

    /**
     * Sets abi.
     *
     * @param abi the abi
     */
    public void setAbi(String abi) {
        this.abi = abi;
    }
}
