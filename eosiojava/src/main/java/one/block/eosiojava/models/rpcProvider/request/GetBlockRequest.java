package one.block.eosiojava.models.rpcProvider.request;

import com.google.gson.annotations.SerializedName;

/**
 * The request class for GetBlock RPC call
 */
public class GetBlockRequest {

    /**
     * Instantiates a new Get block request.
     *
     * @param blockNumOrId the block num or id
     */
    public GetBlockRequest(String blockNumOrId) {
        this.blockNumOrId = blockNumOrId;
    }

    /**
     * Block num or id
     */
    @SerializedName("block_num_or_id")
    private String blockNumOrId;

    /**
     * Gets block num or id.
     *
     * @return the block num or id
     */
    public String getBlockNumOrId() {
        return blockNumOrId;
    }

    /**
     * Sets block num or id.
     *
     * @param blockNumOrId the block num or id
     */
    public void setBlockNumOrId(String blockNumOrId) {
        this.blockNumOrId = blockNumOrId;
    }
}
