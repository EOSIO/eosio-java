package one.block.eosiojava.models.rpcProvider.response;

import com.google.gson.annotations.SerializedName;
import java.math.BigInteger;

/**
 * The generic response error could be thrown from Rpc calls of {@link one.block.eosiojava.interfaces.IRPCProvider}
 */
public class RPCResponseError {

    /**
     * The error Code.
     */
    @SerializedName("code")
    private BigInteger code;

    /**
     * The error Message.
     */
    @SerializedName("message")
    private String message;

    /**
     * The Error detail.
     */
    @SerializedName("error")
    private RpcError error;

    /**
     * Gets error code.
     *
     * @return the error code.
     */
    public BigInteger getCode() {
        return code;
    }

    /**
     * Gets error message.
     *
     * @return the error message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets error detail.
     *
     * @return the error detail.
     */
    public RpcError getError() {
        return error;
    }
}
