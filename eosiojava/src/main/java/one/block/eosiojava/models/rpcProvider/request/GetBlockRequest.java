package one.block.eosiojava.models.rpcProvider.request;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;

/**
 * The request class for GetBlock RPC call {@link one.block.eosiojava.interfaces.IRPCProvider#getBlock(GetBlockRequest)}
 */
public class GetBlockRequest {

    /**
     * Instantiates a new GetBlockRequest.
     *
     * @param blockNumOrId the block number or a block id
     */
    public GetBlockRequest(@NotNull String blockNumOrId) {
        this.blockNumOrId = blockNumOrId;
    }

    /**
     * Provide a block number or a block id
     */
    @SerializedName("block_num_or_id")
    @NotNull
    private String blockNumOrId;

    /**
     * Gets block number or a block id.
     *
     * @return the block number or a block id
     */
    @NotNull
    public String getBlockNumOrId() {
        return blockNumOrId;
    }

    /**
     * Sets block number or a block id.
     *
     * @param blockNumOrId the block number or a block id
     */
    public void setBlockNumOrId(@NotNull String blockNumOrId) {
        this.blockNumOrId = blockNumOrId;
    }
}
