package one.block.eosiojava.interfaces;

import one.block.eosiojava.error.rpcprovider.GetBlockRpcError;
import one.block.eosiojava.error.rpcprovider.GetInfoRpcError;
import one.block.eosiojava.error.rpcprovider.GetRawAbiRpcError;
import one.block.eosiojava.error.rpcprovider.GetRequiredKeysRpcError;
import one.block.eosiojava.error.rpcprovider.PushTransactionRpcError;
import one.block.eosiojava.models.rpcprovider.request.GetBlockRequest;
import one.block.eosiojava.models.rpcprovider.request.GetRawAbiRequest;
import one.block.eosiojava.models.rpcprovider.request.GetRequiredKeysRequest;
import one.block.eosiojava.models.rpcprovider.request.PushTransactionRequest;
import one.block.eosiojava.models.rpcprovider.response.GetBlockResponse;
import one.block.eosiojava.models.rpcprovider.response.GetInfoResponse;
import one.block.eosiojava.models.rpcprovider.response.GetRawAbiResponse;
import one.block.eosiojava.models.rpcprovider.response.GetRequiredKeysResponse;
import one.block.eosiojava.models.rpcprovider.response.PushTransactionResponse;
import org.jetbrains.annotations.NotNull;

/**
 * The interface of an RPC provider.
 */
public interface IRPCProvider {

    /**
     * Returns an object containing various details about the blockchain.
     *
     * @return the latest info/status of a chain.
     * @throws GetInfoRpcError thrown if there are any exceptions/backend errors during the
     * getInfo() process.
     */
    @NotNull
    GetInfoResponse getInfo() throws GetInfoRpcError;

    /**
     * Returns an object containing various details about a specific block on the blockchain.
     *
     * @param getBlockRequest Info of a specific block.
     * @return the info/status of a specific block in the request
     * @throws GetBlockRpcError thrown if there are any exceptions/backend error during the
     * getBlock() process.
     */
    @NotNull
    GetBlockResponse getBlock(GetBlockRequest getBlockRequest) throws GetBlockRpcError;

    /**
     * Gets raw abi for a given contract.
     *
     * @param getRawAbiRequest Info of a specific smart contract.
     * @return the serialized ABI of a smart contract in the request.
     * @throws GetRawAbiRpcError thrown if there are any exceptions/backend error during the
     * getRawAbi() process.
     */
    @NotNull
    GetRawAbiResponse getRawAbi(GetRawAbiRequest getRawAbiRequest) throws GetRawAbiRpcError;

    /**
     * Returns the required keys needed to sign a transaction.
     *
     * @param getRequiredKeysRequest Info to get required keys
     * @return the required keys to sign a transaction
     * @throws GetRequiredKeysRpcError thrown if there are any exceptions/backend error during the
     * getRequiredKeys() process.
     */
    @NotNull
    GetRequiredKeysResponse getRequiredKeys(GetRequiredKeysRequest getRequiredKeysRequest) throws GetRequiredKeysRpcError;

    /**
     * This method expects a transaction in JSON format and will attempt to apply it to the blockchain.
     *
     * @param pushTransactionRequest the transaction to push with signatures.
     * @return the push transaction response
     * @throws PushTransactionRpcError thrown if there are any exceptions/backend error during the
     * pushTransaction() process.
     */
    @NotNull
    PushTransactionResponse pushTransaction(PushTransactionRequest pushTransactionRequest) throws PushTransactionRpcError;
}
