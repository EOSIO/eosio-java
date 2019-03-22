package one.block.eosiojava.models.rpcProvider.response;

import com.google.gson.annotations.SerializedName;

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
    private int cpuUsageUs;

    /**
     * The Net usage words.
     */
    @SerializedName("net_usage_words")
    private int netUsageWords;

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
    public int getCpuUsageUs() {
        return cpuUsageUs;
    }

    /**
     * Gets net usage words.
     *
     * @return the net usage words
     */
    public int getNetUsageWords() {
        return netUsageWords;
    }
}