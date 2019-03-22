package one.block.eosiojava.models.rpcProvider.response;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * The type Action trace receipt.
 */
public class ActionTraceReceipt {

    /**
     * The Receiver.
     */
    @SerializedName("receiver")
    private String receiver;

    /**
     * The Act digest.
     */
    @SerializedName("act_digest")
    private String actDigest;

    /**
     * The Global sequence.
     */
    @SerializedName("global_sequence")
    private int globalSequence;

    /**
     * The Recv sequence.
     */
    @SerializedName("recv_sequence")
    private int recvSequence;

    /**
     * The Auth sequence.
     */
    @SerializedName("auth_sequence")
    private List<List<String>> authSequence;

    /**
     * The Code sequence.
     */
    @SerializedName("code_sequence")
    private int codeSequence;

    /**
     * The Abi sequence.
     */
    @SerializedName("abi_sequence")
    private int abiSequence;

    /**
     * Gets receiver.
     *
     * @return the receiver
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * Gets act digest.
     *
     * @return the act digest
     */
    public String getActDigest() {
        return actDigest;
    }

    /**
     * Gets global sequence.
     *
     * @return the global sequence
     */
    public int getGlobalSequence() {
        return globalSequence;
    }

    /**
     * Gets recv sequence.
     *
     * @return the recv sequence
     */
    public int getRecvSequence() {
        return recvSequence;
    }

    /**
     * Gets auth sequence.
     *
     * @return the auth sequence
     */
    public List<List<String>> getAuthSequence() {
        return authSequence;
    }

    /**
     * Gets code sequence.
     *
     * @return the code sequence
     */
    public int getCodeSequence() {
        return codeSequence;
    }

    /**
     * Gets abi sequence.
     *
     * @return the abi sequence
     */
    public int getAbiSequence() {
        return abiSequence;
    }
}
