package one.block.eosiojava.interfaces;

import java.util.List;
import one.block.eosiojava.error.rpcProvider.GetBlockError;
import one.block.eosiojava.error.rpcProvider.GetInfoError;
import one.block.eosiojava.error.rpcProvider.GetRawAbiError;
import one.block.eosiojava.error.rpcProvider.GetRequiredKeysError;
import one.block.eosiojava.error.rpcProvider.InitError;
import one.block.eosiojava.error.rpcProvider.PushTransactionError;
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
     * @throws InitError
     */
    void init(List<EosioEndPoint> endPoints, int failOverRetries) throws InitError;

    /**
     * Gets info RPC call to get latest info/status of a chain.
     *
     * @return the latest info/status of a chain.
     * @throws GetInfoError
     */
    GetInfoResponse getInfo() throws GetInfoError;

    /**
     * Gets block RPC call to get info/status of a specific block in the request.
     *
     * @param getBlockRequest Info of a specific block.
     * @return the info/status of a specific block in the request
     * @throws GetBlockError
     */
    GetBlockResponse getBlock(GetBlockRequest getBlockRequest) throws GetBlockError;

    /**
     * Gets raw abi RPC call to get serialized ABI of a smart contract in the request.
     *
     * @param getRawAbiRequest Info of a specific smart contract.
     * @return the serialized ABI of a smart contract in the request.
     * @throws GetRawAbiError
     */
    GetRawAbiResponse getRawAbi(GetRawAbiRequest getRawAbiRequest) throws GetRawAbiError;

    /**
     * Gets required keys RPC call to get required keys to sign a transaction
     *
     * @param getRequiredKeysRequest Info to get required keys
     * @return the required keys to sign a transaction
     * @throws GetRequiredKeysError
     */
    GetRequiredKeysResponse getRequiredKeys(GetRequiredKeysRequest getRequiredKeysRequest) throws GetRequiredKeysError;

    /**
     * Push transaction RPC call to broadcast a transaction to backend
     *
     * @param pushTransactionRequest the transaction to push with signatures.
     * @return the push transaction response
     * @throws PushTransactionError
     */
    PushTransactionResponse pushTransaction(PushTransactionRequest pushTransactionRequest) throws PushTransactionError;
}
