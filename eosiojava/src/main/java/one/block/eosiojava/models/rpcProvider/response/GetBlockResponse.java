package one.block.eosiojava.models.rpcProvider.response;

import com.google.gson.annotations.SerializedName;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * The response of GetBlockResponse
 */
public class GetBlockResponse {

    /**
     * The Timestamp.
     */
    @SerializedName("timestamp")
    private String timestamp;

    /**
     * The Producer.
     */
    @SerializedName("producer")
    private String producer;

    /**
     * The Confirmed.
     */
    @SerializedName("confirmed")
    private BigInteger confirmed;

    /**
     * The Previous.x
     */
    @SerializedName("previous")
    private String previous;

    /**
     * The Transaction mroot.
     */
    @SerializedName("transaction_mroot")
    private String transactionMroot;

    /**
     * The Action mroot.
     */
    @SerializedName("action_mroot")
    private String actionMroot;

    /**
     * The Schedule version.
     */
    @SerializedName("schedule_version")
    private BigInteger  scheduleVersion;

    /**
     * The New producers.
     */
    @SerializedName("new_producers")
    private String newProducers;

    /**
     * The Header extensions.
     */
    @SerializedName("header_extensions")
    private List<String> headerExtensions;

    /**
     * The Producer signature.
     */
    @SerializedName("producer_signature")
    private String producerSignature;

    /**
     * The Transactions.
     */
    @SerializedName("transactions")
    private List<Map> transactions;

    /**
     * The Block extensions.
     */
    @SerializedName("block_extensions")
    private List<String> blockExtensions;

    /**
     * The Id.
     */
    @SerializedName("id")
    private String id;

    /**
     * The Block number.
     */
    @SerializedName("block_num")
    private BigInteger  blockNum;

    /**
     * The Ref block prefix.
     */
    @SerializedName("ref_block_prefix")
    private BigInteger  refBlockPrefix;

    /**
     * Gets timestamp.
     *
     * @return the timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Gets producer.
     *
     * @return the producer
     */
    public String getProducer() {
        return producer;
    }

    /**
     * Gets confirmed.
     *
     * @return the confirmed
     */
    public BigInteger  getConfirmed() {
        return confirmed;
    }

    /**
     * Gets previous.
     *
     * @return the previous
     */
    public String getPrevious() {
        return previous;
    }

    /**
     * Gets transaction mroot.
     *
     * @return the transaction mroot
     */
    public String getTransactionMroot() {
        return transactionMroot;
    }

    /**
     * Gets action mroot.
     *
     * @return the action mroot
     */
    public String getActionMroot() {
        return actionMroot;
    }

    /**
     * Gets schedule version.
     *
     * @return the schedule version
     */
    public BigInteger  getScheduleVersion() {
        return scheduleVersion;
    }

    /**
     * Gets new producers.
     *
     * @return the new producers
     */
    public Object getNewProducers() {
        return newProducers;
    }

    /**
     * Gets header extensions.
     *
     * @return the header extensions
     */
    public List<String> getHeaderExtensions() {
        return headerExtensions;
    }

    /**
     * Gets producer signature.
     *
     * @return the producer signature
     */
    public String getProducerSignature() {
        return producerSignature;
    }

    /**
     * Gets transactions.
     *
     * @return the transactions
     */
    public List<Map> getTransactions() {
        return transactions;
    }

    /**
     * Gets block extensions.
     *
     * @return the block extensions
     */
    public List<String> getBlockExtensions() {
        return blockExtensions;
    }

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
    public BigInteger  getBlockNum() {
        return blockNum;
    }

    /**
     * Gets ref block prefix.
     *
     * @return the ref block prefix
     */
    public BigInteger  getRefBlockPrefix() {
        return refBlockPrefix;
    }
}
