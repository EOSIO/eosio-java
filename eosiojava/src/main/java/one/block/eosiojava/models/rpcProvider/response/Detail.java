package one.block.eosiojava.models.rpcProvider.response;

import com.google.gson.annotations.SerializedName;

/**
 * The Error Detail of RPC response error.
 */
public class Detail {

    /**
     * The Message.
     */
    @SerializedName("message")
    private String message;

    /**
     * The File.
     */
    @SerializedName("file")
    private String file;

    /**
     * The Line number.
     */
    @SerializedName("line_number")
    private int lineNumber;

    /**
     * The Method.
     */
    @SerializedName("method")
    private String method;

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets file.
     *
     * @return the file
     */
    public String getFile() {
        return file;
    }

    /**
     * Gets line number.
     *
     * @return the line number
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * Gets method.
     *
     * @return the method
     */
    public String getMethod() {
        return method;
    }
}
