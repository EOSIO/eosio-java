package one.block.eosiojava.interfaces;

import java.util.List;
import java.util.Map;
import one.block.eosiojava.error.abiProvider.GetAbiError;
import one.block.eosiojava.models.EOSIOName;
import org.jetbrains.annotations.NotNull;

/**
 * Interface of ABI Provider
 */
public interface IABIProvider {

    /**
     * Gets multiple ABI by list of EOSIOName.
     * <br/>
     * Check ABIProviderImpl.getABIs() flow in "complete workflow"
     * doc for more detail about the implementation
     *
     * @param chainId the chain id
     * @param accounts the accounts
     * @return the abis
     * @throws GetAbiError the get abi error
     */
    @NotNull
    Map<String, String> getAbis(@NotNull String chainId, @NotNull List<EOSIOName> accounts) throws GetAbiError;

    /**
     * Gets abi by EOSIOName.
     * <br/>
     * Check ABIProviderImpl.getABI() flow in "complete workflow"
     * doc for more detail about the implementation
     *
     * @param chainId the chain id
     * @param account the account
     * @return the abi
     * @throws GetAbiError the get abi error
     */
    @NotNull
    String getAbi(@NotNull String chainId, @NotNull EOSIOName account) throws GetAbiError;
}
