package one.block.eosiojava.models.rpcProvider.response;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * The type Processed.
 */
public class Processed {

    /**
     * The Id.
     */
    @SerializedName("id")
    private String id;

    /**
     * The Block num.
     */
    @SerializedName("block_num")
    private int blockNum;

    /**
     * The Block time.
     */
    @SerializedName("block_time")
    private String blockTime;

    /**
     * The Producer block id.
     */
    @SerializedName("producer_block_id")
    private Object producerBlockId;

    /**
     * The Receipt.
     */
    @SerializedName("receipt")
    private Receipt receipt;

    /**
     * The Elapsed.
     */
    @SerializedName("elapsed")
    private int elapsed;

    /**
     * The Net usage.
     */
    @SerializedName("net_usage")
    private int netUsage;

    /**
     * The Scheduled.
     */
    @SerializedName("scheduled")
    private boolean scheduled;

    /**
     * The Action traces.
     */
    @SerializedName("action_traces")
    private List<ActionTrace> actionTraces = null;

    /**
     * The Except.
     */
    @SerializedName("except")
    private Object except;

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Gets block num.
     *
     * @return the block num
     */
    public int getBlockNum() {
        return blockNum;
    }

    /**
     * Gets block time.
     *
     * @return the block time
     */
    public String getBlockTime() {
        return blockTime;
    }

    /**
     * Gets producer block id.
     *
     * @return the producer block id
     */
    public Object getProducerBlockId() {
        return producerBlockId;
    }

    /**
     * Gets receipt.
     *
     * @return the receipt
     */
    public Receipt getReceipt() {
        return receipt;
    }

    /**
     * Gets elapsed.
     *
     * @return the elapsed
     */
    public int getElapsed() {
        return elapsed;
    }

    /**
     * Gets net usage.
     *
     * @return the net usage
     */
    public int getNetUsage() {
        return netUsage;
    }

    /**
     * Is scheduled boolean.
     *
     * @return the boolean
     */
    public boolean isScheduled() {
        return scheduled;
    }

    /**
     * Gets action traces.
     *
     * @return the action traces
     */
    public List<ActionTrace> getActionTraces() {
        return actionTraces;
    }

    /**
     * Gets except.
     *
     * @return the except
     */
    public Object getExcept() {
        return except;
    }
}