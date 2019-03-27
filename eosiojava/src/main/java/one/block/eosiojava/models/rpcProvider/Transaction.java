package one.block.eosiojava.models.rpcProvider;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * The Transaction class which has data of actions for each transaction. It is used to carry actions
 * data in hex and get broadcast to backend.
 */
public class Transaction implements Serializable {

    /**
     * The Expiration which will be calculated if the value is not set
     */
    @SerializedName("expiration")
    @NotNull
    private String expiration;

    /**
     * The Ref block num which will be calculated if the value is not set
     */
    @SerializedName("ref_block_num")
    @NotNull
    private BigInteger refBlockNum;

    /**
     * The Ref block prefix which will be calculated if the value is not set
     */
    @SerializedName("ref_block_prefix")
    @NotNull
    private BigInteger refBlockPrefix;

    /**
     * The Max net usage words.
     */
    @SerializedName("max_net_usage_words")
    @NotNull
    private BigInteger maxNetUsageWords;

    /**
     * The Max cpu usage ms.
     */
    @SerializedName("max_cpu_usage_ms")
    @NotNull
    private BigInteger maxCpuUsageMs;

    /**
     * The Delay sec.
     */
    @SerializedName("delay_sec")
    @NotNull
    private BigInteger delaySec;

    /**
     * The Context free actions.
     */
    @SerializedName("context_free_actions")
    @NotNull
    private List<Action> contextFreeActions;

    /**
     * The Actions which have data about action of an account with hex/json data for the detail
     */
    @SerializedName("actions")
    @NotNull
    private List<Action> actions;

    /**
     * The Transaction extensions.
     */
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
     *
     * @return the expiration
     */
    @NotNull
    public String getExpiration() {
        return expiration;
    }

    /**
     * Sets expiration.
     *
     * @param expiration the expiration
     */
    public void setExpiration(@NotNull String expiration) {
        this.expiration = expiration;
    }

    /**
     * Gets ref block num.
     *
     * @return the ref block num
     */
    @NotNull
    public BigInteger getRefBlockNum() {
        return refBlockNum;
    }

    /**
     * Sets ref block num.
     *
     * @param refBlockNum the ref block num
     */
    public void setRefBlockNum(@NotNull BigInteger refBlockNum) {
        this.refBlockNum = refBlockNum;
    }

    /**
     * Gets ref block prefix.
     *
     * @return the ref block prefix
     */
    @NotNull
    public BigInteger getRefBlockPrefix() {
        return refBlockPrefix;
    }

    /**
     * Sets ref block prefix.
     *
     * @param refBlockPrefix the ref block prefix
     */
    public void setRefBlockPrefix(@NotNull BigInteger refBlockPrefix) {
        this.refBlockPrefix = refBlockPrefix;
    }

    /**
     * Gets max net usage words.
     *
     * @return the max net usage words
     */
    @NotNull
    public BigInteger getMaxNetUsageWords() {
        return maxNetUsageWords;
    }

    /**
     * Sets max net usage words.
     *
     * @param maxNetUsageWords the max net usage words
     */
    public void setMaxNetUsageWords(@NotNull BigInteger maxNetUsageWords) {
        this.maxNetUsageWords = maxNetUsageWords;
    }

    /**
     * Gets max cpu usage ms.
     *
     * @return the max cpu usage ms
     */
    @NotNull
    public BigInteger getMaxCpuUsageMs() {
        return maxCpuUsageMs;
    }

    /**
     * Sets max cpu usage ms.
     *
     * @param maxCpuUsageMs the max cpu usage ms
     */
    public void setMaxCpuUsageMs(@NotNull BigInteger maxCpuUsageMs) {
        this.maxCpuUsageMs = maxCpuUsageMs;
    }

    /**
     * Gets delay sec.
     *
     * @return the delay sec
     */
    @NotNull
    public BigInteger getDelaySec() {
        return delaySec;
    }

    /**
     * Sets delay sec.
     *
     * @param delaySec the delay sec
     */
    public void setDelaySec(@NotNull BigInteger delaySec) {
        this.delaySec = delaySec;
    }

    /**
     * Gets context free actions.
     *
     * @return the context free actions
     */
    @NotNull
    public List<Action> getContextFreeActions() {
        return contextFreeActions;
    }

    /**
     * Sets context free actions.
     *
     * @param contextFreeActions the context free actions
     */
    public void setContextFreeActions(@NotNull List<Action> contextFreeActions) {
        this.contextFreeActions = contextFreeActions;
    }

    /**
     * Gets actions.
     *
     * @return the actions
     */
    @NotNull
    public List<Action> getActions() {
        return actions;
    }

    /**
     * Sets actions.
     *
     * @param actions the actions
     */
    public void setActions(@NotNull List<Action> actions) {
        this.actions = actions;
    }

    /**
     * Gets transaction extensions.
     *
     * @return the transaction extensions
     */
    @NotNull
    public List<String> getTransactionExtensions() {
        return transactionExtensions;
    }

    /**
     * Sets transaction extensions.
     *
     * @param transactionExtensions the transaction extensions
     */
    public void setTransactionExtensions(@NotNull List<String> transactionExtensions) {
        this.transactionExtensions = transactionExtensions;
    }
}
