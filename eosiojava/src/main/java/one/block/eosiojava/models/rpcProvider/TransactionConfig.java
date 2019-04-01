package one.block.eosiojava.models.rpcProvider;

/**
 * The type Transaction config.
 */
public class TransactionConfig {

    /**
     * Default blocks behind to use if blocksbehind is not set or instance of this class is not used
     */
    public static final int DEFAULT_BLOCKS_BEHIND = 3;

    /**
     * Default expires seconds to use if expires seconds is not set or instance of this class is not used
     */
    public static final int DEFAULT_EXPIRES_SECONDS = 5 * 60;

    /**
     * The Expires seconds.
     */
    private int expiresSeconds = DEFAULT_EXPIRES_SECONDS;

    /**
     * The Blocks behind.
     */
    private int blocksBehind = DEFAULT_BLOCKS_BEHIND;

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
