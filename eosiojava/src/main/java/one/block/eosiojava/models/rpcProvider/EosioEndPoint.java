package one.block.eosiojava.models.rpcProvider;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;

/**
 * The EndPoint for Rpc provider
 */
public class EosioEndPoint {

    /**
     * The Protocol.
     */
    @SerializedName("protocol")
    @NotNull
    private String protocol;

    /**
     * The Port.
     */
    @SerializedName("port")
    @NotNull
    private String port;

    /**
     * The Host.
     */
    @SerializedName("host")
    @NotNull
    private String host;

    /**
     * Instantiates a new Eosio end point.
     *
     * @param protocol the protocol
     * @param port the port
     * @param host the host
     */
    public EosioEndPoint(@NotNull String protocol, @NotNull String port, @NotNull String host) {
        this.protocol = protocol;
        this.port = port;
        this.host = host;
    }

    /**
     * Gets protocol.
     *
     * @return the protocol
     */
    @NotNull
    public String getProtocol() {
        return protocol;
    }

    /**
     * Sets protocol.
     *
     * @param protocol the protocol
     */
    public void setProtocol(@NotNull String protocol) {
        this.protocol = protocol;
    }

    /**
     * Gets port.
     *
     * @return the port
     */
    @NotNull
    public String getPort() {
        return port;
    }

    /**
     * Sets port.
     *
     * @param port the port
     */
    public void setPort(@NotNull String port) {
        this.port = port;
    }

    /**
     * Gets host.
     *
     * @return the host
     */
    @NotNull
    public String getHost() {
        return host;
    }

    /**
     * Sets host.
     *
     * @param host the host
     */
    @NotNull
    public void setHost(@NotNull String host) {
        this.host = host;
    }

    /**
     * Return
     */
    public String toFULLURL() {
        return String.format("%s://%s:%s", this.protocol, this.host, this.port);
    }
}
