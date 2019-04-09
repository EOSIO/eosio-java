package one.block.eosiojava.session;

import one.block.eosiojava.error.session.TransactionProcessorConstructorInputError;
import one.block.eosiojava.interfaces.IABIProvider;
import one.block.eosiojava.interfaces.IRPCProvider;
import one.block.eosiojava.interfaces.ISerializationProvider;
import one.block.eosiojava.interfaces.ISignatureProvider;
import one.block.eosiojava.models.rpcProvider.Transaction;
import org.jetbrains.annotations.NotNull;

/**
 * Transaction Session class has a factory role for creating {@link TransactionProcessor} object from providers instances
 */
public class TransactionSession {

    @NotNull
    private ISerializationProvider serializationProvider;

    @NotNull
    private IRPCProvider rpcProvider;

    @NotNull
    private IABIProvider abiProvider;

    @NotNull
    private ISignatureProvider signatureProvider;

    public TransactionSession(
            @NotNull ISerializationProvider serializationProvider,
            @NotNull IRPCProvider rpcProvider, @NotNull IABIProvider abiProvider,
            @NotNull ISignatureProvider signatureProvider) {
        this.serializationProvider = serializationProvider;
        this.rpcProvider = rpcProvider;
        this.abiProvider = abiProvider;
        this.signatureProvider = signatureProvider;
    }

    /**
     * Create and return a new instance of TransactionProcessor
     *
     * @return new instance of TransactionProcessor
     */
    public TransactionProcessor getTransactionProcessor() {
        return new TransactionProcessor(this.serializationProvider, this.rpcProvider,
                this.abiProvider, this.signatureProvider);
    }

    /**
     * Create and return a new instance of TransactionProcessor with preset transaction
     *
     * @param transaction - preset transaction
     * @return new instance of TransactionProcessor
     */
    public TransactionProcessor getTransactionProcessor(Transaction transaction) throws TransactionProcessorConstructorInputError {
        return new TransactionProcessor(this.serializationProvider, this.rpcProvider,
                this.abiProvider, this.signatureProvider, transaction);
    }

    //region getters
    @NotNull
    public ISerializationProvider getSerializationProvider() {
        return serializationProvider;
    }

    @NotNull
    public IRPCProvider getRpcProvider() {
        return rpcProvider;
    }

    @NotNull
    public IABIProvider getAbiProvider() {
        return abiProvider;
    }

    @NotNull
    public ISignatureProvider getSignatureProvider() {
        return signatureProvider;
    }
    //endregion
}
