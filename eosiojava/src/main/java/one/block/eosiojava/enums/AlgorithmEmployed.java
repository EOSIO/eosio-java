package one.block.eosiojava.enums;

/**
 * Enum of supported algorithms which are employed in eosio-java library
 */
public enum AlgorithmEmployed {
    /**
     * Supported SECP256r1 (prime256v1) algorithm curve
     */
    SECP256R1("secp256r1"),

    /**
     * Supported SECP256k1 algorithm curve
     */
    SECP256K1("secp256k1"),

    /**
     * Supported prime256v1 algorithm curve
     */
    PRIME256V1("prime256v1");

    private String str;

    /**
     * Initialize AlgorithmEmployed enum object with a String value
     * @param str - input String value of enums in AlgorithmEmployed
     */
    AlgorithmEmployed(String str) {
        this.str = str;
    }

    /**
     * Gets string value of AlgorithmEmployed's enum
     * @return string value of AlgorithmEmployed's enum
     */
    public String getString() {
        return str;
    }
}
