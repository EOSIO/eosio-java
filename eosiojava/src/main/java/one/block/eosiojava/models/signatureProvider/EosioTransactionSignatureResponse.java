package one.block.eosiojava.models.signatureProvider;

import java.util.List;
import one.block.eosiojava.error.signatureProvider.SignatureProviderError;
import org.jetbrains.annotations.Nullable;

/**
 * The type Eosio transaction signature response.
 */
public class EosioTransactionSignatureResponse {

    /**
     * The Serialize transaction.
     */
    private String serializeTransaction;

    /**
     * The Signatures.
     */
    private List<String> signatures;

    /**
     * The Error.
     */
    @Nullable
    private SignatureProviderError error;

    /**
     * Gets serialize transaction.
     *
     * @return the serialize transaction
     */
    public String getSerializeTransaction() {
        return serializeTransaction;
    }

    /**
     * Gets signatures.
     *
     * @return the signatures
     */
    public List<String> getSignatures() {
        return signatures;
    }

    /**
     * Gets error.
     *
     * @return the error
     */
    @Nullable
    public SignatureProviderError getError() {
        return error;
    }
}
