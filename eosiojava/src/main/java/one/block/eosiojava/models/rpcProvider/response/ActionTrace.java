package one.block.eosiojava.models.rpcProvider.response;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * The type Action trace
 */
public class ActionTrace {

    /**
     * The Receipt.
     */
    @SerializedName("receipt")
    private ActionTraceReceipt receipt;

    /**
     * The Act.
     */
    @SerializedName("act")
    private ActionTraceAction act;

    /**
     * The Context free.
     */
    @SerializedName("context_free")
    private boolean contextFree;

    /**
     * The Elapsed.
     */
    @SerializedName("elapsed")
    private int elapsed;

    /**
     * The Cpu usage.
     */
    @SerializedName("cpu_usage")
    private int cpuUsage;

    /**
     * The Console.
     */
    @SerializedName("console")
    private String console;

    /**
     * The Total cpu usage.
     */
    @SerializedName("total_cpu_usage")
    private int totalCpuUsage;

    /**
     * The Trx id.
     */
    @SerializedName("trx_id")
    private String trxId;

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
     * The Account ram deltas.
     */
    @SerializedName("account_ram_deltas")
    private List<Object> accountRamDeltas = null;

    /**
     * The Inline traces.
     */
    @SerializedName("inline_traces")
    private List<ActionTrace> inlineTraces = null;

    /**
     * Gets receipt.
     *
     * @return the receipt
     */
    public ActionTraceReceipt getReceipt() {
        return receipt;
    }

    /**
     * Gets act.
     *
     * @return the act
     */
    public ActionTraceAction getAct() {
        return act;
    }

    /**
     * Is context free boolean.
     *
     * @return the boolean
     */
    public boolean isContextFree() {
        return contextFree;
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
     * Gets cpu usage.
     *
     * @return the cpu usage
     */
    public int getCpuUsage() {
        return cpuUsage;
    }

    /**
     * Gets console.
     *
     * @return the console
     */
    public String getConsole() {
        return console;
    }

    /**
     * Gets total cpu usage.
     *
     * @return the total cpu usage
     */
    public int getTotalCpuUsage() {
        return totalCpuUsage;
    }

    /**
     * Gets trx id.
     *
     * @return the trx id
     */
    public String getTrxId() {
        return trxId;
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
     * Gets account ram deltas.
     *
     * @return the account ram deltas
     */
    public List<Object> getAccountRamDeltas() {
        return accountRamDeltas;
    }

    /**
     * Gets inline traces.
     *
     * @return the inline traces
     */
    public List<ActionTrace> getInlineTraces() {
        return inlineTraces;
    }
}