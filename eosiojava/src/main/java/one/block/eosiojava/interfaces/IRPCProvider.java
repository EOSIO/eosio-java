package one.block.eosiojava.interfaces;

import one.block.eosiojava.error.rpcProvider.GetBlockRpcError;
import one.block.eosiojava.error.rpcProvider.GetInfoRpcError;
import one.block.eosiojava.error.rpcProvider.GetRawAbiRpcError;
import one.block.eosiojava.error.rpcProvider.GetRequiredKeysRpcError;
import one.block.eosiojava.error.rpcProvider.PushTransactionRpcError;
import one.block.eosiojava.models.rpcProvider.request.GetBlockRequest;
import one.block.eosiojava.models.rpcProvider.request.GetRawAbiRequest;
import one.block.eosiojava.models.rpcProvider.request.GetRequiredKeysRequest;
import one.block.eosiojava.models.rpcProvider.request.PushTransactionRequest;
import one.block.eosiojava.models.rpcProvider.response.GetBlockResponse;
import one.block.eosiojava.models.rpcProvider.response.GetInfoResponse;
import one.block.eosiojava.models.rpcProvider.response.GetRawAbiResponse;
import one.block.eosiojava.models.rpcProvider.response.GetRequiredKeysResponse;
import one.block.eosiojava.models.rpcProvider.response.PushTransactionResponse;
import org.jetbrains.annotations.NotNull;

/**
 * The interface of RPC provider.
 */
public interface IRPCProvider {

    /**
     * Gets info RPC call to get latest info/status of a chain.
     *
     * @return the latest info/status of a chain.
     * @throws GetInfoRpcError thrown if there are any exceptions/backend error during the GetInfo process.
     */
    @NotNull
    GetInfoResponse getInfo() throws GetInfoRpcError;

    /**
     * Gets block RPC call to get info/status of a specific block in the request.
     *
     * @param getBlockRequest Info of a specific block.
     * @return the info/status of a specific block in the request
     * @throws GetBlockRpcError thrown if there are any exceptions/backend error during the GetBlock process.
     */
    @NotNull
    GetBlockResponse getBlock(GetBlockRequest getBlockRequest) throws GetBlockRpcError;

    /**
     * Gets raw abi RPC call to get serialized ABI of a smart contract in the request.
     *
     * @param getRawAbiRequest Info of a specific smart contract.
     * @return the serialized ABI of a smart contract in the request.
     * @throws GetRawAbiRpcError thrown if there are any exceptions/backend error during the GetRawAbi process.
     */
    @NotNull
    GetRawAbiResponse getRawAbi(GetRawAbiRequest getRawAbiRequest) throws GetRawAbiRpcError;

    /**
     * Gets required keys RPC call to get required keys to sign a transaction
     *
     * @param getRequiredKeysRequest Info to get required keys
     * @return the required keys to sign a transaction
     * @throws GetRequiredKeysRpcError thrown if there are any exceptions/backend error during the GetRequiredKeys process.
     */
    @NotNull
    GetRequiredKeysResponse getRequiredKeys(GetRequiredKeysRequest getRequiredKeysRequest) throws GetRequiredKeysRpcError;

    /**
     * Push transaction RPC call to broadcast a transaction to backend
     *
     * @param pushTransactionRequest the transaction to push with signatures.
     * @return the push transaction response
     * @throws PushTransactionRpcError thrown if there are any exceptions/backend error during the PushTransaction process.
     */
    @NotNull
    PushTransactionResponse pushTransaction(PushTransactionRequest pushTransactionRequest) throws PushTransactionRpcError;
}
