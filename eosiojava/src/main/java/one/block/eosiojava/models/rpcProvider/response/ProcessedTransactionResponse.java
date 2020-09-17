package one.block.eosiojava.models.rpcProvider.response;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;

public class ProcessedTransactionResponse {

    /**
     * The transaction id
     */
    @SerializedName("id")
    private String id;

    /**
     * The blockNumber
     */
    @SerializedName("block_num")
    private Integer blockNumber;

    /**
     * The blockTime
     */
    @SerializedName("block_time")
    private String blockTime;

    /**
     * The producerBlockId
     */
    @SerializedName("producer_block_id")
    private String producerBlockId;

    /**
     * The receipt
     */
    @SerializedName("receipt")
    private Map receipt;

    /**
     * The elapsed
     */
    @SerializedName("elapsed")
    private Integer elapsed;

    /**
     * The netUsage
     */
    @SerializedName("net_usage")
    private Integer netUsage;

    /**
     * The scheduled
     */
    @SerializedName("scheduled")
    private Boolean scheduled;

    /**
     * The accountRamDelta
     */
    @SerializedName("account_ram_delta")
    private String accountRamDelta;

    /**
     * The except
     */
    @SerializedName("except")
    private String except;

    /**
     * The errorCode
     */
    @SerializedName("error_code")
    private String errorCode;

    /**
     * The actionTraces
     */
    @SerializedName("action_traces")
    private List<ActionTrace> actionTraces;

    /**
     * Gets the transaction id
     * @return the transaction id
     */
    public String getId() { return this.id; }

    /**
     * Gets the blockNumber
     * @return the blockNumber
     */
    public Integer getBlockNumber() { return this.blockNumber; }

    /**
     * Gets the blockTime
     * @return the blockTime
     */
    public String getBlockTime() { return this.blockTime; }

    /**
     * Gets the producerBlockId
     * @return the producerBlockId
     */
    public String getProducerBlockId() { return this.producerBlockId; }

    /**
     * Gets the receipt
     * @return the receipt
     */
    public Map getReceipt() { return this.receipt; }

    /**
     * Gets the elapsed
     * @return the elapsed
     */
    public Integer getElapsed() { return this.elapsed; }

    /**
     * Gets the netUsage
     * @return the netUsage
     */
    public Integer getNetUsage() { return this.netUsage; }

    /**
     * Gets the scheduled
     * @return the scheduled
     */
    public Boolean getScheduled() { return this.scheduled; }

    /**
     * Gets the accountRamDelta
     * @return the accountRamDelta
     */
    public String getAccountRamDelta() { return this.accountRamDelta; }

    /**
     * Gets the except
     * @return the except
     */
    public String getExcept() { return this.except; }

    /**
     * Gets the errorCode
     * @return the errorCode
     */
    public String getErrorCode() { return this.errorCode; }

    /**
     * Gets the action traces
     * @return the action traces
     */
    public List<ActionTrace> getActionTraces() { return this.actionTraces; }
}
