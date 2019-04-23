package one.block.eosiojava.models.rpcprovider.response;

import com.google.gson.annotations.SerializedName;
import java.math.BigInteger;

/**
 * The Error Detail of {@link RPCResponseError}
 */
public class Detail {

    /**
     * The error message.
     */
    @SerializedName("message")
    private String message;

    /**
     * The error file on backend.
     */
    @SerializedName("file")
    private String file;

    /**
     * The error line number on backend.
     */
    @SerializedName("line_number")
    private BigInteger lineNumber;

    /**
     * The method that caused the error on backend.
     */
    @SerializedName("method")
    private String method;

    /**
     * Gets the error message.
     *
     * @return the error message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets the error file from backend.
     *
     * @return the error file
     */
    public String getFile() {
        return file;
    }

    /**
     * Gets the error line number from backend.
     *
     * @return the error line number
     */
    public  BigInteger getLineNumber() {
        return lineNumber;
    }

    /**
     * Gets the method that caused the error from backend.
     *
     * @return the method causing the error
     */
    public String getMethod() {
        return method;
    }
}
