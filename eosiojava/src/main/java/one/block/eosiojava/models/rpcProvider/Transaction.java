package one.block.eosiojava.models.rpcProvider;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

/**
 * The Transaction class which has data of actions for each transaction.
 * It is used to carry actions data in hex and get broadcast to backend.
 */
public class Transaction implements Serializable {

    /**
     * The Expiration which will be calculated if the value is not set
     */
    @SerializedName("expiration")
    private Long expiration;

    /**
     * The Ref block num which will be calculated if the value is not set
     */
    @SerializedName("ref_block_num")
    private Long refBlockNum;

    /**
     * The Ref block prefix which will be calculated if the value is not set
     */
    @SerializedName("ref_block_prefix")
    private String refBlockPrefix;

    /**
     * The Max net usage words.
     */
    @SerializedName("max_net_usage_words")
    private int maxNetUsageWords;

    /**
     * The Max cpu usage ms.
     */
    @SerializedName("max_cpu_usage_ms")
    private int maxCpuUsageMs;

    /**
     * The Delay sec.
     */
    @SerializedName("delay_sec")
    private
    int delaySec;

    /**
     * The Context free actions.
     */
    @SerializedName("context_free_actions")
    private List<Object> contextFreeActions;

    /**
     * The Actions which have data about action of an account with hex/json data for the detail
     */
    @SerializedName("actions")
    private List<Action> actions;

    /**
     * The Transaction extensions.
     */
    @SerializedName("transaction_extensions")
    private
    List<Object> transactionExtensions;

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
    public Transaction(Long expiration, Long refBlockNum, String refBlockPrefix,
            int maxNetUsageWords,
            int maxCpuUsageMs, int delaySec, List<Object> contextFreeActions,
            List<Action> actions, List<Object> transactionExtensions) {
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
    public Long getExpiration() {
        return expiration;
    }

    /**
     * Sets expiration.
     *
     * @param expiration the expiration
     */
    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    /**
     * Gets ref block num.
     *
     * @return the ref block num
     */
    public Long getRefBlockNum() {
        return refBlockNum;
    }

    /**
     * Sets ref block num.
     *
     * @param refBlockNum the ref block num
     */
    public void setRefBlockNum(Long refBlockNum) {
        this.refBlockNum = refBlockNum;
    }

    /**
     * Gets ref block prefix.
     *
     * @return the ref block prefix
     */
    public String getRefBlockPrefix() {
        return refBlockPrefix;
    }

    /**
     * Sets ref block prefix.
     *
     * @param refBlockPrefix the ref block prefix
     */
    public void setRefBlockPrefix(String refBlockPrefix) {
        this.refBlockPrefix = refBlockPrefix;
    }

    /**
     * Gets max net usage words.
     *
     * @return the max net usage words
     */
    public int getMaxNetUsageWords() {
        return maxNetUsageWords;
    }

    /**
     * Sets max net usage words.
     *
     * @param maxNetUsageWords the max net usage words
     */
    public void setMaxNetUsageWords(int maxNetUsageWords) {
        this.maxNetUsageWords = maxNetUsageWords;
    }

    /**
     * Gets max cpu usage ms.
     *
     * @return the max cpu usage ms
     */
    public int getMaxCpuUsageMs() {
        return maxCpuUsageMs;
    }

    /**
     * Sets max cpu usage ms.
     *
     * @param maxCpuUsageMs the max cpu usage ms
     */
    public void setMaxCpuUsageMs(int maxCpuUsageMs) {
        this.maxCpuUsageMs = maxCpuUsageMs;
    }

    /**
     * Gets delay sec.
     *
     * @return the delay sec
     */
    public int getDelaySec() {
        return delaySec;
    }

    /**
     * Sets delay sec.
     *
     * @param delaySec the delay sec
     */
    public void setDelaySec(int delaySec) {
        this.delaySec = delaySec;
    }

    /**
     * Gets context free actions.
     *
     * @return the context free actions
     */
    public List<Object> getContextFreeActions() {
        return contextFreeActions;
    }

    /**
     * Sets context free actions.
     *
     * @param contextFreeActions the context free actions
     */
    public void setContextFreeActions(List<Object> contextFreeActions) {
        this.contextFreeActions = contextFreeActions;
    }

    /**
     * Gets actions.
     *
     * @return the actions
     */
    public List<Action> getActions() {
        return actions;
    }

    /**
     * Sets actions.
     *
     * @param actions the actions
     */
    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    /**
     * Gets transaction extensions.
     *
     * @return the transaction extensions
     */
    public List<Object> getTransactionExtensions() {
        return transactionExtensions;
    }

    /**
     * Sets transaction extensions.
     *
     * @param transactionExtensions the transaction extensions
     */
    public void setTransactionExtensions(List<Object> transactionExtensions) {
        this.transactionExtensions = transactionExtensions;
    }
}
