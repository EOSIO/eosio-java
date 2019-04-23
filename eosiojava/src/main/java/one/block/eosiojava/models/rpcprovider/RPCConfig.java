package one.block.eosiojava.models.rpcprovider;

/**
 * The config class for RPC provider
 */
public class RPCConfig {

    /**
     * The Connection timeout.
     */
    private long connectionTimeout;

    /**
     * The Read timeout.
     */
    private long readTimeout;

    /**
     * Instantiates a new Rpc config.
     *
     * @param connectionTimeout the connection timeout
     * @param readTimeout the read timeout
     */
    public RPCConfig(long connectionTimeout, long readTimeout) {
        this.connectionTimeout = connectionTimeout;
        this.readTimeout = readTimeout;
    }

    /**
     * Gets connection timeout.
     *
     * @return the connection timeout
     */
    public long getConnectionTimeout() {
        return connectionTimeout;
    }

    /**
     * Sets connection timeout.
     *
     * @param connectionTimeout the connection timeout
     */
    public void setConnectionTimeout(long connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    /**
     * Gets read timeout.
     *
     * @return the read timeout
     */
    public long getReadTimeout() {
        return readTimeout;
    }

    /**
     * Sets read timeout.
     *
     * @param readTimeout the read timeout
     */
    public void setReadTimeout(long readTimeout) {
        this.readTimeout = readTimeout;
    }
}
