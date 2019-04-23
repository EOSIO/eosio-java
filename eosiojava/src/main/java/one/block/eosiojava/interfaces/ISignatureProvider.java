package one.block.eosiojava.interfaces;

import java.util.List;
import one.block.eosiojava.error.signatureProvider.GetAvailableKeysError;
import one.block.eosiojava.error.signatureProvider.SignTransactionError;
import one.block.eosiojava.models.signatureProvider.EosioTransactionSignatureRequest;
import one.block.eosiojava.models.signatureProvider.EosioTransactionSignatureResponse;
import org.jetbrains.annotations.NotNull;

/**
 * The interface of Signature provider.
 */
public interface ISignatureProvider {

    /**
     * Sign a transaction in Signature Provider <br/> Check signTransaction flow() in "complete
     * workflow" for more detail
     *
     * @param eosioTransactionSignatureRequest the request
     * @return the response
     * @throws SignTransactionError thrown if there are any exceptions during the signing process.
     */
    @NotNull
    EosioTransactionSignatureResponse signTransaction(
            @NotNull EosioTransactionSignatureRequest eosioTransactionSignatureRequest)
            throws SignTransactionError;

    /**
     * Gets available keys from signature provider <br/> Check createSignatureRequest() flow in
     * "complete workflow" for more detail of how the method is used
     *
     * @return the available keys of signature provider in EOS format
     * @throws GetAvailableKeysError thrown if there are any exceptions during the get available keys process.
     */
    @NotNull
    List<String> getAvailableKeys() throws GetAvailableKeysError;
}
