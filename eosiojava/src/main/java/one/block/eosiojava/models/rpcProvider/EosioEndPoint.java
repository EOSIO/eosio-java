package one.block.eosiojava.models.rpcProvider;

import com.google.gson.annotations.SerializedName;

/**
 * The EndPoint for Rpc provider
 */
public class EosioEndPoint {

    /**
     * The Protocol.
     */
    @SerializedName("protocol")
    private String protocol;

    /**
     * The Port.
     */
    @SerializedName("port")
    private String port;

    /**
     * The Host.
     */
    @SerializedName("host")
    private String host;

    /**
     * Instantiates a new Eosio end point.
     *
     * @param protocol the protocol
     * @param port the port
     * @param host the host
     */
    public EosioEndPoint(String protocol, String port, String host) {
        this.protocol = protocol;
        this.port = port;
        this.host = host;
    }

    /**
     * Gets protocol.
     *
     * @return the protocol
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * Sets protocol.
     *
     * @param protocol the protocol
     */
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    /**
     * Gets port.
     *
     * @return the port
     */
    public String getPort() {
        return port;
    }

    /**
     * Sets port.
     *
     * @param port the port
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * Gets host.
     *
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * Sets host.
     *
     * @param host the host
     */
    public void setHost(String host) {
        this.host = host;
    }
}
