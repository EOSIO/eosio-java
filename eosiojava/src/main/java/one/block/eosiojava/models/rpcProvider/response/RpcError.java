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
     * A more comprehensive explanation of the error.
     */
    @SerializedName("details")
    private List<Detail> details;

    /**
     * Gets the error code.
     *
     * @return the code
     */
    public BigInteger getCode() {
        return code;
    }

    /**
     * Gets the name.
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
     * Gets a more comprehensive explanation of the error.
     *
     * @return the deeper details
     */
    public List<Detail> getDetails() {
        return details;
    }
}
