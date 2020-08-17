package one.block.eosiojava.models.rpcProvider.request;

import java.util.List;
import one.block.eosiojava.models.rpcProvider.Transaction;
import org.jetbrains.annotations.NotNull;
import com.google.gson.annotations.SerializedName;

public class SendTransactionRequest extends TransactionRequest {
    /**
     * Instantiates a new SendTransactionRequest.
     *
     * @param signatures the list of signatures required to authorize transaction
     * @param compression the compression used, usually 0.
     * @param packagedContextFreeData the context free data in hex
     * @param packTrx the packed Transaction (serialized transaction). It is serialized version of
     * {@link Transaction}.
     */
    public SendTransactionRequest(
            @NotNull List<String> signatures, int compression,
            String packagedContextFreeData, @NotNull String packTrx) {
        super(signatures, compression, packagedContextFreeData, packTrx);
    }
}
