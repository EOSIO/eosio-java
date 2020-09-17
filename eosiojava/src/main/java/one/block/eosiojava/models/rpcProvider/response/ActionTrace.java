package one.block.eosiojava.models.rpcProvider.response;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

public class ActionTrace {
    /**
     * The actionOrdinal
     */
    @SerializedName("action_ordinal")
    private Integer actionOrdinal;

    /**
     * The creatorActionOrdinal
     */
    @SerializedName("creator_action_ordinal")
    private Integer creatorActionOrdinal;

    /**
     * The closestUnnotifiedAncestorActionOrdinal
     */
    @SerializedName("closest_unnotified_ancestor_action_ordinal")
    private Integer closestUnnotifiedAncestorActionOrdinal;

    /**
     * The receipt
     */
    @SerializedName("receipt")
    private Map receipt;

    /**
     * The receiver
     */
    @SerializedName("receiver")
    private String receiver;

    /**
     * The act
     */
    @SerializedName("act")
    private Map act;

    /**
     * The contextFree
     */
    @SerializedName("context_free")
    private Boolean contextFree;

    /**
     * The elapsed
     */
    @SerializedName("elapsed")
    private Integer elapsed;

    /**
     * The console
     */
    @SerializedName("console")
    private String console;

    /**
     * The transactionId
     */
    @SerializedName("trx_id")
    private String transactionId;

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
     * The accountRamDeltas
     */
    @SerializedName("account_ram_deltas")
    private Map accountRamDeltas;

    /**
     * The accountDiskDeltas
     */
    @SerializedName("account_disk_deltas")
    private Map accountDiskDeltas;

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
     * The returnValue
     */
    @SerializedName("return_value")
    private String returnValue;

    /**
     * The deserialized return value
     */
    private String deserializedReturnValue;

    /**
     * Gets the actionOrdinal
     * @return the actionOrdinal
     */
    public Integer getActionOrdinal() { return this.actionOrdinal; }

    /**
     * Gets the creatorActionOrdinal
     * @return the creatorActionOrdinal
     */
    public Integer getCreatorActionOrdinal() { return this.creatorActionOrdinal; }

    /**
     * Gets the closestUnnotifiedAncestorActionOrdinal
     * @return the closestUnnotifiedAncestorActionOrdinal
     */
    public Integer getClosestUnnotifiedAncestorActionOrdinal() { return this.closestUnnotifiedAncestorActionOrdinal; }

    /**
     * Gets the receipt
     * @return the receipt
     */
    public Map getReceipt() { return this.receipt; }

    /**
     * Gets the receiver
     * @return the receiver
     */
    public String getReceiver() { return this.receiver; }

    /**
     * Gets the act
     * @return the act
     */
    public Map getAct() { return this.act; }

    /**
     * Gets the contextFree
     * @return the contextFree
     */
    public Boolean getContextFree() { return this.contextFree; }

    /**
     * Gets the elapsed
     * @return the elapsed
     */
    public Integer getElapsed() { return this.elapsed; }

    /**
     * Gets the console
     * @return the console
     */
    public String getConsole() { return this.console; }

    /**
     * Gets the transactionId
     * @return the transactionId
     */
    public String getTransactionId() { return this.transactionId; }

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
     * Gets the accountRamDeltas
     * @return the accountRamDeltas
     */
    public Map getAccountRamDeltas() { return this.accountRamDeltas; }

    /**
     * Gets the accountDiskDeltas
     * @return the accountDiskDeltas
     */
    public Map getAccountDiskDeltas() { return this.accountDiskDeltas; }

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
     * Gets the returnValue
     * @return the returnValue
     */
    public String getReturnValue() { return this.returnValue; }

    /**
     * Gets the deserializedReturnValue
     * @return the deserializedReturnValue
     */
    public String getDeserializedReturnValue() { return this.deserializedReturnValue; }

    /**
     * Gets the account name
     * @return the account name
     */
    public String getAccountName() { return (String)this.act.get("account"); }

    /**
     * Gets the action name
     * @return the action name
     */
    public String getActionName() { return (String)this.act.get("name"); }

    /**
     * Determines whether or not there is a return value to deserialize
     * @return true if there is a value; false otherwise
     */
    public Boolean hasReturnValue() { return !this.returnValue.isEmpty(); }

    /**
     * Sets the deserialized return value
     * @param deserializedReturnValue - the value to set to
     */
    public void setDeserializedReturnValue(String deserializedReturnValue) {
        this.deserializedReturnValue = deserializedReturnValue;
    }
}
