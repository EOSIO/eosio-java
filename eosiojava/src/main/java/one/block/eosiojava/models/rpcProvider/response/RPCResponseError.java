package one.block.eosiojava.models.rpcProvider.response;

import com.google.gson.annotations.SerializedName;
import java.math.BigInteger;

/**
 * The generic response error could be thrown from Rpc calls.
 */
public class RPCResponseError {

    /**
     * The Code.
     */
    @SerializedName("code")
    private BigInteger code;

    /**
     * The Message.
     */
    @SerializedName("message")
    private String message;

    /**
     * The Error.
     */
    @SerializedName("error")
    private RpcError error;

    /**
     * Gets code.
     *
     * @return the code
     */
    public BigInteger getCode() {
        return code;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets error.
     *
     * @return the error
     */
    public RpcError getError() {
        return error;
    }
}
