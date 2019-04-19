package one.block.eosiojava.models.rpcProvider.response;

import com.google.gson.annotations.SerializedName;
import java.math.BigInteger;
import java.util.List;
import one.block.eosiojava.interfaces.IRPCProvider;
import one.block.eosiojava.models.rpcProvider.Transaction;
import one.block.eosiojava.models.rpcProvider.request.GetBlockRequest;

/**
 * The response of GetInfo RPC call {@link IRPCProvider#getInfo()}
 */
public class GetInfoResponse {

    @SerializedName("server_version")
    private String serverVersion;

    /**
     * The Chain id. It is an argument to make signable serializeTransaction in {@link
     * one.block.eosiojava.utilities.EOSFormatter#prepareSerializedTransactionForSigning(String,
     * String)}
     */
    @SerializedName("chain_id")
    private String chainId;

    /**
     * The Head block number. It is an argument to specify the reference block to call {@link
     * one.block.eosiojava.interfaces.IRPCProvider#getBlock(GetBlockRequest)} at {@link
     * one.block.eosiojava.session.TransactionProcessor#prepare(List)}
     */
    @SerializedName("head_block_num")
    private BigInteger headBlockNum;

    @SerializedName("last_irreversible_block_num")
    private BigInteger lastIrreversibleBlockNum;

    @SerializedName("last_irreversible_block_id")
    private String lastIrreversibleBlockId;

    /**
     * The Head block id.
     */
    @SerializedName("head_block_id")
    private String headBlockId;

    /**
     * The Head block time. Which is used to calculate expiration time for {@link
     * Transaction#setExpiration(String)}} at {@link one.block.eosiojava.session.TransactionProcessor#prepare(List)}
     */
    @SerializedName("head_block_time")
    private String headBlockTime;

    /**
     * The Head block producer name.
     */
    @SerializedName("head_block_producer")
    private String headBlockProducer;

    @SerializedName("virtual_block_cpu_limit")
    private BigInteger virtualBlockCpuLimit;

    @SerializedName("virtual_block_net_limit")
    private BigInteger virtualBlockNetLimit;

    @SerializedName("block_cpu_limit")
    private BigInteger blockCpuLimit;

    @SerializedName("block_net_limit")
    private BigInteger blockNetLimit;

    @SerializedName("server_version_string")
    private String serverVersionString;

    public String getServerVersion() {
        return serverVersion;
    }

    /**
     * Gets the Chain id. It is an argument to make signable serializeTransaction in {@link
     * one.block.eosiojava.utilities.EOSFormatter#prepareSerializedTransactionForSigning(String,
     * String)}
     *
     * @return the chain id.
     */
    public String getChainId() {
        return chainId;
    }

    /**
     * Gets the head block number. It is an argument to specify the reference block to call {@link
     * one.block.eosiojava.interfaces.IRPCProvider#getBlock(GetBlockRequest)} at {@link
     * one.block.eosiojava.session.TransactionProcessor#prepare(List)}
     * @return the head block number.
     */
    public BigInteger getHeadBlockNum() {
        return headBlockNum;
    }

    public BigInteger getLastIrreversibleBlockNum() {
        return lastIrreversibleBlockNum;
    }

    public String getLastIrreversibleBlockId() {
        return lastIrreversibleBlockId;
    }

    /**
     * The head block id.
     * @return head block id.
     */
    public String getHeadBlockId() {
        return headBlockId;
    }

    /**
     * Gets the Head block time. Which is used to calculate expiration time for {@link
     * Transaction#setExpiration(String)}} at {@link one.block.eosiojava.session.TransactionProcessor#prepare(List)}
     * @return the head block time.
     */
    public String getHeadBlockTime() {
        return headBlockTime;
    }

    /**
     * Gets head block producer name.
     *
     * @return the head block producer name.
     */
    public String getHeadBlockProducer() {
        return headBlockProducer;
    }

    public BigInteger getVirtualBlockCpuLimit() {
        return virtualBlockCpuLimit;
    }

    public BigInteger getVirtualBlockNetLimit() {
        return virtualBlockNetLimit;
    }

    public BigInteger getBlockCpuLimit() {
        return blockCpuLimit;
    }

    public BigInteger getBlockNetLimit() {
        return blockNetLimit;
    }

    public String getServerVersionString() {
        return serverVersionString;
    }
}
