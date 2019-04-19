package one.block.eosiojava.models.signatureProvider;

import java.util.List;
import one.block.eosiojava.error.signatureProvider.SignatureProviderError;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The type Eosio transaction signature response.
 */
public class EosioTransactionSignatureResponse {

    /**
     * The Serialized (Hex) version of {@link one.block.eosiojava.models.rpcProvider.Transaction}.
     * <br>
     * It is the result of {@link one.block.eosiojava.interfaces.ISerializationProvider#serializeTransaction(String)}
     * <br>
     * Could be modified by Signature Provider.
     * <br>
     * If signature provider modify the serialized transaction on the response {@link
     * EosioTransactionSignatureResponse#getSerializeTransaction()} but {@link
     * EosioTransactionSignatureRequest#isModifiable()} is false then {@link
     * one.block.eosiojava.error.session.TransactionGetSignatureNotAllowModifyTransactionError} will
     * be thrown
     */
    @NotNull
    private String serializeTransaction;

    /**
     * The Signatures which are signed by private keys of {@link EosioTransactionSignatureRequest#getSigningPublicKey()}
     */
    @NotNull
    private List<String> signatures;

    /**
     * The Error.
     */
    @Nullable
    private SignatureProviderError error;

    public EosioTransactionSignatureResponse(@NotNull String serializeTransaction,
            @NotNull List<String> signatures, @Nullable SignatureProviderError error) {
        this.serializeTransaction = serializeTransaction;
        this.signatures = signatures;
        this.error = error;
    }

    /**
     * Gets serialize transaction.
     *
     * @return the serialize transaction
     */
    @NotNull
    public String getSerializeTransaction() {
        return serializeTransaction;
    }

    /**
     * Gets signatures.
     *
     * @return the signatures
     */
    @NotNull
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
