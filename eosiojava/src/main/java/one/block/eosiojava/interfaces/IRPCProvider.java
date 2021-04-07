package one.block.eosiojava.interfaces;

import one.block.eosiojava.error.rpcProvider.*;
import one.block.eosiojava.models.rpcProvider.request.*;

import one.block.eosiojava.models.rpcProvider.response.*;
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
     * @param getBlockInfoRequest Info of a specific block.
     * @return the info/status of a specific block in the request
     * @throws GetBlockInfoRpcError thrown if there are any exceptions/backend error during the
     * getBlockInfo() process.
     */
    @NotNull
    GetBlockInfoResponse getBlockInfo(GetBlockInfoRequest getBlockInfoRequest) throws GetBlockInfoRpcError;

    /**
     * Returns an object containing various details about a specific block on the blockchain.
     *
     * @param getBlockRequest Info of a specific block.
     * @return the info/status of a specific block in the request
     * @throws GetBlockRpcError thrown if there are any exceptions/backend error during the
     * getBlockInfo() process.
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
     * @param sendTransactionRequest the transaction to push with signatures.
     * @return the send transaction response
     * @throws SendTransactionRpcError thrown if there are any exceptions/backend error during the
     * sendTransaction() process.
     */
    @NotNull
    SendTransactionResponse sendTransaction(SendTransactionRequest sendTransactionRequest) throws SendTransactionRpcError;
}
