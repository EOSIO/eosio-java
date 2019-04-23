package one.block.eosiojava.models.rpcprovider;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import one.block.eosiojava.models.rpcprovider.response.GetBlockResponse;
import one.block.eosiojava.models.rpcprovider.response.GetInfoResponse;
import org.jetbrains.annotations.NotNull;

/**
 * The Transaction class which has data of actions for each transaction. It holds the serialized
 * action data that will be pushed to the blockchain.
 */
public class Transaction implements Serializable {

    /**
     * The maximum lifespan of the transaction.  It will be calculated if the value is not set.
     * It is based on {@link GetInfoResponse#getHeadBlockTime()}
     */
    @SerializedName("expiration")
    @NotNull
    private String expiration;

    /**
     * The reference block number used for Transaction as Proof-of-Stake (TAPOS).  It will be
     * calculated if the value is not set. It is based on {@link
     * GetInfoResponse#getHeadBlockNum()}
     */
    @SerializedName("ref_block_num")
    @NotNull
    private BigInteger refBlockNum;

    /**
     * The Ref block prefix which will be calculated if the value is not set. Its value get assigned
     * from {@link GetBlockResponse#getRefBlockPrefix()}
     */
    @SerializedName("ref_block_prefix")
    @NotNull
    private BigInteger refBlockPrefix;

    @SerializedName("max_net_usage_words")
    @NotNull
    private BigInteger maxNetUsageWords;

    @SerializedName("max_cpu_usage_ms")
    @NotNull
    private BigInteger maxCpuUsageMs;

    @SerializedName("delay_sec")
    @NotNull
    private BigInteger delaySec;

    @SerializedName("context_free_actions")
    @NotNull
    private List<Action> contextFreeActions;

    /**
     * The Actions which have data about action of an account with hex/json data for the detail
     */
    @SerializedName("actions")
    @NotNull
    private List<Action> actions;

    @SerializedName("transaction_extensions")
    @NotNull
    private List<String> transactionExtensions;

    /**
     * Instantiates a new Transaction.
     *
     * @param expiration the expiration
     * @param refBlockNum the ref block num
     * @param refBlockPrefix the ref block prefix
     * @param maxNetUsageWords the max net usage words
     * @param maxCpuUsageMs the max cpu usage ms
     * @param delaySec the delay sec
     * @param contextFreeActions the context free actions
     * @param actions the actions
     * @param transactionExtensions the transaction extensions
     */
    public Transaction(@NotNull String expiration, @NotNull BigInteger refBlockNum,
            @NotNull BigInteger refBlockPrefix,
            @NotNull BigInteger maxNetUsageWords,
            @NotNull BigInteger maxCpuUsageMs, @NotNull BigInteger delaySec,
            @NotNull List<Action> contextFreeActions,
            @NotNull List<Action> actions, @NotNull List<String> transactionExtensions) {
        this.expiration = expiration;
        this.refBlockNum = refBlockNum;
        this.refBlockPrefix = refBlockPrefix;
        this.maxNetUsageWords = maxNetUsageWords;
        this.maxCpuUsageMs = maxCpuUsageMs;
        this.delaySec = delaySec;
        this.contextFreeActions = contextFreeActions;
        this.actions = actions;
        this.transactionExtensions = transactionExtensions;
    }

    /**
     * Gets expiration.
     * <br>
     * It is calculated if the value is not set. It is based on {@link
     * GetInfoResponse#getHeadBlockTime()}
     *
     * @return the expiration
     */
    @NotNull
    public String getExpiration() {
        return expiration;
    }

    /**
     * Sets expiration.
     * <br>
     * It is calculated if the value is not set. It is based on {@link
     * GetInfoResponse#getHeadBlockTime()}
     *
     * @param expiration the expiration
     */
    public void setExpiration(@NotNull String expiration) {
        this.expiration = expiration;
    }

    /**
     * Gets ref block number.
     * <br>
     * It is calculated if the value is not set. Its value gets assigned from {@link
     * GetBlockResponse#getRefBlockPrefix()}
     *
     * @return the ref block number
     */
    @NotNull
    public BigInteger getRefBlockNum() {
        return refBlockNum;
    }

    /**
     * Sets ref block number.
     * <br>
     * It is calculated if the value is not set. Its value gets assigned from {@link
     * GetBlockResponse#getRefBlockPrefix()}
     *
     * @param refBlockNum the ref block number
     */
    public void setRefBlockNum(@NotNull BigInteger refBlockNum) {
        this.refBlockNum = refBlockNum;
    }

    /**
     * Gets ref block prefix.
     * <br>
     * It is calculated if the value is not set. Its value gets assigned from {@link
     * GetBlockResponse#getRefBlockPrefix()}
     *
     * @return the ref block prefix
     */
    @NotNull
    public BigInteger getRefBlockPrefix() {
        return refBlockPrefix;
    }

    /**
     * Sets ref block prefix.
     * <br>
     * It is calculated if the value is not set. Its value gets assigned from {@link
     * GetBlockResponse#getRefBlockPrefix()}
     *
     * @param refBlockPrefix the ref block prefix.
     */
    public void setRefBlockPrefix(@NotNull BigInteger refBlockPrefix) {
        this.refBlockPrefix = refBlockPrefix;
    }

    @NotNull
    public BigInteger getMaxNetUsageWords() {
        return maxNetUsageWords;
    }

    public void setMaxNetUsageWords(@NotNull BigInteger maxNetUsageWords) {
        this.maxNetUsageWords = maxNetUsageWords;
    }

    @NotNull
    public BigInteger getMaxCpuUsageMs() {
        return maxCpuUsageMs;
    }

    public void setMaxCpuUsageMs(@NotNull BigInteger maxCpuUsageMs) {
        this.maxCpuUsageMs = maxCpuUsageMs;
    }

    @NotNull
    public BigInteger getDelaySec() {
        return delaySec;
    }

    public void setDelaySec(@NotNull BigInteger delaySec) {
        this.delaySec = delaySec;
    }

    @NotNull
    public List<Action> getContextFreeActions() {
        return contextFreeActions;
    }

    public void setContextFreeActions(@NotNull List<Action> contextFreeActions) {
        this.contextFreeActions = contextFreeActions;
    }

    @NotNull
    public List<Action> getActions() {
        return actions;
    }

    public void setActions(@NotNull List<Action> actions) {
        this.actions = actions;
    }

    @NotNull
    public List<String> getTransactionExtensions() {
        return transactionExtensions;
    }

    public void setTransactionExtensions(@NotNull List<String> transactionExtensions) {
        this.transactionExtensions = transactionExtensions;
    }
}
