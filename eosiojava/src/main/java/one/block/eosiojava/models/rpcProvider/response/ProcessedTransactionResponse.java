package one.block.eosiojava.models.rpcProvider.response;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;

public class ProcessedTransactionResponse {
    @SerializedName("id")
    private String id;

    @SerializedName("block_num")
    private Integer blockNumber;

    @SerializedName("block_time")
    private String blockTime;

    @SerializedName("producer_block_id")
    private String producerBlockId;

    @SerializedName("receipt")
    private Map receipt;

    @SerializedName("elapsed")
    private Integer elapsed;

    @SerializedName("net_usage")
    private Integer netUsage;

    @SerializedName("scheduled")
    private Boolean scheduled;

    @SerializedName("account_ram_delta")
    private String accountRamDelta;

    @SerializedName("except")
    private String except;

    @SerializedName("error_code")
    private String errorCode;

    @SerializedName("action_traces")
    private List<ActionTrace> actionTraces;

//    @SerializedName("action_traces")
//    private Map traces;

    public String getId() {
        return this.id;
    }
}
