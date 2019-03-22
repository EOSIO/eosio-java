package one.block.eosiojava.models.rpcProvider;

/**
 * The type Transaction config.
 */
public class TransactionConfig {

    /**
     * Default blocks behind to use if blocksbehind is not set
     */
    private static final int DEFAULT_BLOCKS_BEHIND = 3;

    /**
     * The Expires seconds.
     */
    private int expiresSeconds;

    /**
     * The Blocks behind.
     */
    private int blocksBehind = DEFAULT_BLOCKS_BEHIND;

    /**
     * Gets default blocks behind.
     *
     * @return the default blocks behind
     */
    public static int getDefaultBlocksBehind() {
        return DEFAULT_BLOCKS_BEHIND;
    }

    /**
     * Gets expires seconds.
     *
     * @return the expires seconds
     */
    public int getExpiresSeconds() {
        return expiresSeconds;
    }

    /**
     * Sets expires seconds.
     *
     * @param expiresSeconds the expires seconds
     */
    public void setExpiresSeconds(int expiresSeconds) {
        this.expiresSeconds = expiresSeconds;
    }

    /**
     * Gets blocks behind.
     *
     * @return the blocks behind
     */
    public int getBlocksBehind() {
        return blocksBehind;
    }

    /**
     * Sets blocks behind.
     *
     * @param blocksBehind the blocks behind
     */
    public void setBlocksBehind(int blocksBehind) {
        this.blocksBehind = blocksBehind;
    }
}
