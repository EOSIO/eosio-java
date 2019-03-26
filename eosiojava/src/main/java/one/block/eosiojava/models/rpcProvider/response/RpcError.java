package one.block.eosiojava.models.rpcProvider.response;

import com.google.gson.annotations.SerializedName;
import java.math.BigInteger;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * The Error of RPC response error
 */
public class RpcError {

    /**
     * The Code.
     */
    @SerializedName("code")
    @NotNull
    private BigInteger code;

    /**
     * The Name.
     */
    @SerializedName("name")
    @NotNull
    private String name;

    /**
     * The What.
     */
    @SerializedName("what")
    @NotNull
    private String what;

    /**
     * The Details.
     */
    @SerializedName("details")
    @NotNull
    private List<Detail> details;

    /**
     * Gets code.
     *
     * @return the code
     */
    @NotNull
    public BigInteger getCode() {
        return code;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    @NotNull
    public String getName() {
        return name;
    }

    /**
     * Gets what.
     *
     * @return the what
     */
    @NotNull
    public String getWhat() {
        return what;
    }

    /**
     * Gets details.
     *
     * @return the details
     */
    @NotNull
    public List<Detail> getDetails() {
        return details;
    }
}
