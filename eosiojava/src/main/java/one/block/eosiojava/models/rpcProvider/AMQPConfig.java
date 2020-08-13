package one.block.eosiojava.models.rpcProvider;

/**
 * The config class for AMQP
 */
public class AMQPConfig {

    /**
     * The Connection timeout.
     */
    private long connectionTimeout;

    /**
     * The Read timeout.
     */
    private String replyToQueueName;

    /**
     * Instantiates a new Rpc config.
     *
     * @param connectionTimeout the connection timeout
     * @param replyToQueueName the read timeout
     */
    public AMQPConfig(long connectionTimeout, String replyToQueueName) {
        this.connectionTimeout = connectionTimeout;
        this.replyToQueueName = replyToQueueName;
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
     * Gets read replyToQueueName.
     *
     * @return the replyToQueueName
     */
    public String getReplyToQueueName() {
        return this.replyToQueueName;
    }

    /**
     * Sets read replyToQueueName.
     *
     * @param replyToQueueName the read timeout
     */
    public void setReplyToQueueName(String replyToQueueName) {
        this.replyToQueueName = replyToQueueName;
    }
}
