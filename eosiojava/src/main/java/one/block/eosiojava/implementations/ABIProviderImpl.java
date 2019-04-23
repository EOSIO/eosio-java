/*
 * Copyright (c) 2017-2019 block.one all rights reserved.
 */

package one.block.eosiojava.implementations;

import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import one.block.eosiojava.error.ErrorConstants;
import one.block.eosiojava.error.abiprovider.GetAbiError;
import one.block.eosiojava.interfaces.IABIProvider;
import one.block.eosiojava.interfaces.IRPCProvider;
import one.block.eosiojava.interfaces.ISerializationProvider;
import one.block.eosiojava.models.EOSIOName;
import one.block.eosiojava.models.rpcprovider.request.GetRawAbiRequest;
import one.block.eosiojava.models.rpcprovider.response.GetRawAbiResponse;
import one.block.eosiojava.utilities.ByteFormatter;
import org.jetbrains.annotations.NotNull;

/**
 * Default ABI Provider implementation, providing in memory caching of previously fetched
 * ABI's as well as fetching of ABI's using the provided RPC provider.
 */

public class ABIProviderImpl implements IABIProvider {

    private @NotNull IRPCProvider rpcProvider;
    private @NotNull ISerializationProvider serializationProvider;
    private @NotNull Map<String, String> abiCache = new ConcurrentHashMap<>();

    /**
     * Initialize a new ABI Provider, passing the necessary RPC provider to fetch ABI's
     * if they are not found in the cache.
     *
     * @param rpcProvider RPC provider implementation to use to fetch ABIs if they are not
     * in the cache.
     * @param serializationProvider Serialization provider implementation to use to deserialize
     * the ABIs for return and storage in the cache.
     */
    public ABIProviderImpl(@NotNull IRPCProvider rpcProvider,
            @NotNull ISerializationProvider serializationProvider) {
        this.rpcProvider = rpcProvider;
        this.serializationProvider = serializationProvider;
    }

    /**
     * Return a map of ABIs given the chain id and account names desired.  The in-memory cache
     * will be checked first and any missing ABIs will be fetched via the RPC provider and placed
     * in the cache.  This call is synchronous.  Developers should wrap it
     * in an asynchronous mechanism to avoid blocking if necessary.
     *
     * @param chainId the chain id
     * @param accounts the accounts - duplicate names will be removed
     * @return Map of ABIs keyed by the account
     * @throws GetAbiError If there is an error retrieving or deserializing any of the ABIs
     */
    @Override
    public @NotNull Map<String, String> getAbis(@NotNull String chainId,
            @NotNull List<EOSIOName> accounts) throws GetAbiError {

        Map<String, String> returnAbis = new HashMap<>();
        List<EOSIOName> uniqAccounts = new ArrayList<>(new HashSet<>(accounts));

        for (EOSIOName account : uniqAccounts) {
            String abiJsonString = getAbi(chainId, account);
            returnAbis.put(account.getAccountName(), abiJsonString);
        }

        return returnAbis;
    }

    /**
     * Return an ABI given the chain id and account name desired.  The in-memory cache
     * will be checked first and if missing the ABI will be fetched via the RPC provider
     * and placed in the cache.  This call is synchronous.  Developers should wrap it
     * in an asynchronous mechanism to avoid blocking if necessary.
     * @param chainId the chain id
     * @param account the account
     * @return abiJsonString - the deserialized JSON string for the requested ABI
     * @throws GetAbiError If there is an error retrieving or deserializing the ABI.
     */
    @Override
    public @NotNull String getAbi(@NotNull String chainId, @NotNull EOSIOName account)
            throws GetAbiError {

        String abiJsonString;
        String cacheKey = chainId + account.getAccountName();

        abiJsonString = this.abiCache.get(cacheKey);
        if (!Strings.isNullOrEmpty(abiJsonString)) {
            return abiJsonString;
        }

        GetRawAbiRequest getRawAbiRequest = new GetRawAbiRequest(account.getAccountName());
        try {
            GetRawAbiResponse getRawAbiResponse = this.rpcProvider.getRawAbi(getRawAbiRequest);
            if (getRawAbiResponse == null) {
                throw new GetAbiError(ErrorConstants.NO_RESPONSE_RETRIEVING_ABI);
            }

            String abi = getRawAbiResponse.getAbi();
            if (Strings.isNullOrEmpty(abi)) {
                throw new GetAbiError(ErrorConstants.MISSING_ABI_FROM_RESPONSE);
            }

            ByteFormatter abiByteFormatter = ByteFormatter.createFromBase64(abi);

            String calculatedHash = abiByteFormatter.sha256().toHex().toLowerCase();
            String verificationHash = getRawAbiResponse.getAbiHash().toLowerCase();
            if (!calculatedHash.equals(verificationHash)) {
                throw new GetAbiError(ErrorConstants.CALCULATED_HASH_NOT_EQUAL_RETURNED);
            }

            if (!account.getAccountName().equals(getRawAbiResponse.getAccountName())) {
                throw new GetAbiError(ErrorConstants.REQUESTED_ACCCOUNT_NOT_EQUAL_RETURNED);
            }

            abiJsonString = this.serializationProvider.deserializeAbi(abiByteFormatter.toHex());
            if (abiJsonString.isEmpty()) {
                throw new GetAbiError(ErrorConstants.NO_ABI_FOUND);
            }

            this.abiCache.put(cacheKey, abiJsonString);

        } catch (Exception ex) {
            throw new GetAbiError(ErrorConstants.ERROR_RETRIEVING_ABI, ex);
        }

        return abiJsonString;
    }
}
