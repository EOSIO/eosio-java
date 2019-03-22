package one.block.eosiojava.interfaces;

import java.util.List;
import one.block.eosiojava.models.rpcProvider.EosioEndPoint;
import one.block.eosiojava.models.rpcProvider.request.GetBlockRequest;
import one.block.eosiojava.models.rpcProvider.request.GetRawAbiRequest;
import one.block.eosiojava.models.rpcProvider.request.GetRequiredKeysRequest;
import one.block.eosiojava.models.rpcProvider.request.PushTransactionRequest;
import one.block.eosiojava.models.rpcProvider.response.GetBlockResponse;
import one.block.eosiojava.models.rpcProvider.response.GetInfoResponse;
import one.block.eosiojava.models.rpcProvider.response.GetRawAbiResponse;
import one.block.eosiojava.models.rpcProvider.response.GetRequiredKeysResponse;
import one.block.eosiojava.models.rpcProvider.response.PushTransactionResponse;

/**
 * The interface Irpc provider.
 */
public interface IRPCProvider {

    /**
     * Init RPC provider with configurations.
     *
     * @param endPoints the end points - list of open points to switch if selected endpoint could
     * not be reached.
     * @param failOverRetries the fail over retries - Amount of try again time for connection fail.
     * Switch to next endpoint in the list if the amount of try again time is reached.
     */
    void init(List<EosioEndPoint> endPoints, int failOverRetries);

    /**
     * Gets info RPC call to get latest info/status of a chain.
     *
     * @return the latest info/status of a chain.
     */
    GetInfoResponse getInfo();

    /**
     * Gets block RPC call to get info/status of a specific block in the request.
     *
     * @param getBlockRequest Info of a specific block.
     * @return the info/status of a specific block in the request
     */
    GetBlockResponse getBlock(GetBlockRequest getBlockRequest);

    /**
     * Gets raw abi RPC call to get serialized ABI of a smart contract in the request.
     *
     * @param getRawAbiRequest Info of a specific smart contract.
     * @return the serialized ABI of a smart contract in the request.
     */
    GetRawAbiResponse getRawAbi(GetRawAbiRequest getRawAbiRequest);

    /**
     * Gets required keys RPC call to get required keys to sign a transaction
     *
     * @param getRequiredKeysRequest Info to get required keys
     * @return the required keys to sign a transaction
     */
    GetRequiredKeysResponse getRequiredKeys(GetRequiredKeysRequest getRequiredKeysRequest);

    /**
     * Push transaction RPC call to broadcast a transaction to backend
     *
     * @param pushTransactionRequest the transaction to push with signatures.
     * @return the push transaction response
     */
    PushTransactionResponse pushTransaction(PushTransactionRequest pushTransactionRequest);
}
