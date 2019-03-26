package one.block.eosiojava.models.rpcProvider.response;

import com.google.gson.annotations.SerializedName;
import java.math.BigInteger;

/**
 * The response of GetInfo RPC call
 */
public class GetInfoResponse {

    /**
     * The Server version.
     */
    @SerializedName("server_version")
    private String serverVersion;

    /**
     * The Chain id.
     */
    @SerializedName("chain_id")
    private String chainId;

    /**
     * The Head block num.
     */
    @SerializedName("head_block_num")
    private BigInteger  headBlockNum;

    /**
     * The Last irreversible block num.
     */
    @SerializedName("last_irreversible_block_num")
    private BigInteger lastIrreversibleBlockNum;

    /**
     * The Last irreversible block id.
     */
    @SerializedName("last_irreversible_block_id")
    private String lastIrreversibleBlockId;

    /**
     * The Head block id.
     */
    @SerializedName("head_block_id")
    private String headBlockId;

    /**
     * The Head block time.
     */
    @SerializedName("head_block_time")
    private String headBlockTime;

    /**
     * The Head block producer.
     */
    @SerializedName("head_block_producer")
    private String headBlockProducer;

    /**
     * The Virtual block cpu limit.
     */
    @SerializedName("virtual_block_cpu_limit")
    private BigInteger  virtualBlockCpuLimit;

    /**
     * The Virtual block net limit.
     */
    @SerializedName("virtual_block_net_limit")
    private BigInteger  virtualBlockNetLimit;

    /**
     * The Block cpu limit.
     */
    @SerializedName("block_cpu_limit")
    private BigInteger  blockCpuLimit;

    /**
     * The Block net limit.
     */
    @SerializedName("block_net_limit")
    private BigInteger  blockNetLimit;

    /**
     * The Server version string.
     */
    @SerializedName("server_version_string")
    private String serverVersionString;

    /**
     * Gets server version.
     *
     * @return the server version
     */
    public String getServerVersion() {
        return serverVersion;
    }

    /**
     * Gets chain id.
     *
     * @return the chain id
     */
    public String getChainId() {
        return chainId;
    }

    /**
     * Gets head block num.
     *
     * @return the head block num
     */
    public BigInteger  getHeadBlockNum() {
        return headBlockNum;
    }

    /**
     * Gets last irreversible block num.
     *
     * @return the last irreversible block num
     */
    public BigInteger  getLastIrreversibleBlockNum() {
        return lastIrreversibleBlockNum;
    }

    /**
     * Gets last irreversible block id.
     *
     * @return the last irreversible block id
     */
    public String getLastIrreversibleBlockId() {
        return lastIrreversibleBlockId;
    }

    /**
     * Gets head block id.
     *
     * @return the head block id
     */
    public String getHeadBlockId() {
        return headBlockId;
    }

    /**
     * Gets head block time.
     *
     * @return the head block time
     */
    public String getHeadBlockTime() {
        return headBlockTime;
    }

    /**
     * Gets head block producer.
     *
     * @return the head block producer
     */
    public String getHeadBlockProducer() {
        return headBlockProducer;
    }

    /**
     * Gets virtual block cpu limit.
     *
     * @return the virtual block cpu limit
     */
    public BigInteger  getVirtualBlockCpuLimit() {
        return virtualBlockCpuLimit;
    }

    /**
     * Gets virtual block net limit.
     *
     * @return the virtual block net limit
     */
    public BigInteger  getVirtualBlockNetLimit() {
        return virtualBlockNetLimit;
    }

    /**
     * Gets block cpu limit.
     *
     * @return the block cpu limit
     */
    public BigInteger  getBlockCpuLimit() {
        return blockCpuLimit;
    }

    /**
     * Gets block net limit.
     *
     * @return the block net limit
     */
    public BigInteger  getBlockNetLimit() {
        return blockNetLimit;
    }

    /**
     * Gets server version string.
     *
     * @return the server version string
     */
    public String getServerVersionString() {
        return serverVersionString;
    }
}
