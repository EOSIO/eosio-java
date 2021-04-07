package one.block.eosiojava.models.rpcProvider;

import one.block.eosiojava.interfaces.IRPCProvider;
import one.block.eosiojava.models.rpcProvider.request.GetBlockInfoRequest;
import one.block.eosiojava.models.rpcProvider.request.GetBlockRequest;
import one.block.eosiojava.models.rpcProvider.response.GetInfoResponse;

/**
 * A configuration class that allows the developer to change certain defaults associated with a
 * Transaction.
 */
public class TransactionConfig {

    /**
     * Default blocks behind to use if blocksbehind is not set or instance of this class is not
     * used
     */
    private static final int DEFAULT_BLOCKS_BEHIND = 3;

    /**
     * Default expires seconds to use if expires seconds is not set or instance of this class is not
     * used
     */
    private static final int DEFAULT_EXPIRES_SECONDS = 5 * 60;

    /**
     * Default useLastIrreversible to use last irreversible block rather than blocksBehind when
     * calculating TAPOS
     */
    private static final boolean DEFAULT_USE_LAST_IRREVERSIBLE = true;

    /**
     * Default chain version string to use if none is specified and we cannot get the server version from
     * the chain itself.
     */
    private static final String DEFAULT_CHAIN_VERSION_STRING = "2.0.0";

    /**
     * Chain version at which {@link one.block.eosiojava.interfaces.IRPCProvider#getBlockInfo(GetBlockInfoRequest)}
     * became available.
     */
    private static final String GET_BLOCK_INFO_AVAILABLE_STRING = "2.1.0";

    /**
     * The Expires seconds.
     * <br>
     * Append this value to {@link GetInfoResponse#getHeadBlockTime()} in second then assign it to
     * {@link Transaction#setExpiration(String)}
     */
    private int expiresSeconds = DEFAULT_EXPIRES_SECONDS;

    /**
     * The amount blocks behind from head block.
     * <br>
     * It is an argument to calculate head block number to call {@link
     * one.block.eosiojava.interfaces.IRPCProvider#getBlockInfo(GetBlockInfoRequest)}
     */
    private int blocksBehind = DEFAULT_BLOCKS_BEHIND;

    /**
     * Use the last irreversible block when calculating TAPOS rather than blocks behind.
     * <br>
     * Mutually exclusive with {@link TransactionConfig#setBlocksBehind(int)}.  If set,
     * {@link TransactionConfig#getBlocksBehind()} will be ignored and TAPOS will be calculated by fetching the last
     * irreversible block with {@link IRPCProvider#getInfo()} and the expiration for the transaction
     * will be set {@link TransactionConfig#setExpiresSeconds(int)} after that block's time.
     */
    private boolean useLastIrreversible = DEFAULT_USE_LAST_IRREVERSIBLE;

    /**
     * Version of nodeos that the transaction is targeting.  This will allow the library to work with 2.1+ version
     * chains using {@link one.block.eosiojava.interfaces.IRPCProvider#getBlockInfo(GetBlockInfoRequest)} for
     * calculating TAPOS rather than {@link one.block.eosiojava.interfaces.IRPCProvider#getBlock(GetBlockInfoRequest)}.
     * If the value is left unset, the transaction will determine the chain version from the {@link IRPCProvider#getInfo()} call
     * and use that as the chain version string.
     */
    private String chainVersionString = null;

    /**
     * Gets the expiration time for the transaction.
     * <br>
     * Append this value to {@link GetInfoResponse#getHeadBlockTime()} in seconds. Assign it to
     * {@link Transaction#setExpiration(String)}.
     *
     * @return when the transaction expires (in seconds)
     */
    public int getExpiresSeconds() {
        return expiresSeconds;
    }

    /**
     * Sets the expiration time for the transaction.
     * <br>
     * Append this value to {@link GetInfoResponse#getHeadBlockTime()} in second then assign it to
     * {@link Transaction#setExpiration(String)}
     *
     * @param expiresSeconds when the transaction expires (in seconds)
     */
    public void setExpiresSeconds(int expiresSeconds) {
        this.expiresSeconds = expiresSeconds;
    }

    /**
     * Gets blocks behind.
     * <br>
     * It is an argument to calculate head block number to call {@link one.block.eosiojava.interfaces.IRPCProvider#getBlockInfo(GetBlockInfoRequest)}
     * @return the blocks behind
     */
    public int getBlocksBehind() {
        return blocksBehind;
    }

    /**
     * Sets blocks behind.
     * <br>
     * It is an argument to calculate head block number to call {@link
     * one.block.eosiojava.interfaces.IRPCProvider#getBlockInfo(GetBlockInfoRequest)}
     * @param blocksBehind the blocks behind
     */
    public void setBlocksBehind(int blocksBehind) {
        this.blocksBehind = blocksBehind;
    }

    /**
     * Gets useLastIrreversible.
     * <br>
     * It is an argument to calculate TAPOS from the last irreversible block rather than blocks behind the head block
     * @return useLastIrreversible whether to use the last irreversible block for calculating TAPOS
     */
    public boolean getUseLastIrreversible() { return useLastIrreversible; }

    /**
     * Sets useLastIrreversible.
     * <br>
     * It is an argument to calculate TAPOS from the last irreversible block rather than blocks behind the head block
     * @param useLastIrreversible whether to use the last irreversible block
     */
    public void setUseLastIrreversible(boolean useLastIrreversible) {
        this.useLastIrreversible = useLastIrreversible;
    }

    /**
     * Gets the current chain version that the transaction is targeting.
     * <br>
     * 2.1+ version chains will use {@link one.block.eosiojava.interfaces.IRPCProvider#getBlockInfo(GetBlockInfoRequest)} for
     * calculating TAPOS rather than {@link one.block.eosiojava.interfaces.IRPCProvider#getBlock(GetBlockRequest)}.  If
     * the value is left unset, the transaction will determine the chain version from the {@link IRPCProvider#getInfo()} call
     * and use that as the chain version string.
     * @return chainVersionString current nodeos version that we are targeting for this transaction
     */
    public String getChainVersionString() { return chainVersionString; }

    /**
     * Sets the current chain version that the transaction is targeting.
     * <br>
     * 2.1+ version chains will use {@link one.block.eosiojava.interfaces.IRPCProvider#getBlockInfo(GetBlockInfoRequest)} for
     * calculating TAPOS rather than {@link one.block.eosiojava.interfaces.IRPCProvider#getBlock(GetBlockRequest)}.  If
     * the value is left unset, the transaction will determine the chain version from the {@link IRPCProvider#getInfo()} call
     * and use that as the chain version string.
     * @param chainVersionString set the target nodeos version that this transaction is for
     */
    public void setChainVersionString(String chainVersionString) { this.chainVersionString = chainVersionString; }

    /**
     * Get the default chain version string to use for transactions if one is not specified and the version cannot
     * be read from the chain itself.
     * @return defaultChainVersionString the version of nodeos if one is not set and the version cannot be read from the chain itself.
     */
    public String getDefaultChainVersionString() { return DEFAULT_CHAIN_VERSION_STRING; }

    /**
     * Chain version at which {@link one.block.eosiojava.interfaces.IRPCProvider#getBlockInfo(GetBlockInfoRequest)}
     * became available.
     * @return getBlockInfoAvailableString the version of nodeos where {@link one.block.eosiojava.interfaces.IRPCProvider#getBlockInfo(GetBlockInfoRequest)}
     * became available.
     */
    public String getGetBlockInfoAvailableString() { return GET_BLOCK_INFO_AVAILABLE_STRING; }
}
