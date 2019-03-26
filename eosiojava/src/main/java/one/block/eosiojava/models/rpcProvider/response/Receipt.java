package one.block.eosiojava.models.rpcProvider.response;

import com.google.gson.annotations.SerializedName;
import java.math.BigInteger;

/**
 * The type Receipt.
 */
public class Receipt {

    /**
     * The Status.
     */
    @SerializedName("status")
    private String status;

    /**
     * The Cpu usage us.
     */
    @SerializedName("cpu_usage_us")
    private BigInteger cpuUsageUs;

    /**
     * The Net usage words.
     */
    @SerializedName("net_usage_words")
    private BigInteger netUsageWords;

    /**
     * Gets status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Gets cpu usage us.
     *
     * @return the cpu usage us
     */
    public BigInteger getCpuUsageUs() {
        return cpuUsageUs;
    }

    /**
     * Gets net usage words.
     *
     * @return the net usage words
     */
    public BigInteger getNetUsageWords() {
        return netUsageWords;
    }
}