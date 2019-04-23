package one.block.eosiojava.models.rpcProvider;

import com.google.gson.annotations.SerializedName;
import java.net.MalformedURLException;
import java.net.URL;
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
    private int port;

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
    public EosioEndPoint(@NotNull String protocol, int port, @NotNull String host) {
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
    public int getPort() {
        return port;
    }

    /**
     * Sets port.
     *
     * @param port the port
     */
    public void setPort(int port) {
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
     * Return {@link URL} object of the endpoint
     * @return {@link URL} object of the endpoint
     * @throws MalformedURLException if an unknown protocol is specified.
     */
    public URL toURL() throws MalformedURLException {
        return new URL(this.protocol, this.host, this.port, "");
    }
}
