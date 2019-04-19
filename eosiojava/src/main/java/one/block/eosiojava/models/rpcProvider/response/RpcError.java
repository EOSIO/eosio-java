package one.block.eosiojava.models.rpcProvider.response;

import com.google.gson.annotations.SerializedName;
import java.math.BigInteger;
import java.util.List;

/**
 * The error detail of {@link RPCResponseError}
 */
public class RpcError {

    /**
     * The error code.
     */
    @SerializedName("code")
    private BigInteger code;

    /**
     * The error name.
     */
    @SerializedName("name")
    private String name;

    @SerializedName("what")
    private String what;

    /**
     * The deeper error details.
     */
    @SerializedName("details")
    private List<Detail> details;

    /**
     * Gets error code.
     *
     * @return the code
     */
    public BigInteger getCode() {
        return code;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    public String getWhat() {
        return what;
    }

    /**
     * Gets deeper details.
     *
     * @return the deeper details
     */
    public List<Detail> getDetails() {
        return details;
    }
}
