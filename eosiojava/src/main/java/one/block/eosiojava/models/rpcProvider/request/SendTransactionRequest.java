package one.block.eosiojava.models.rpcProvider.request;

import java.util.List;
import one.block.eosiojava.models.rpcProvider.Transaction;
import one.block.eosiojava.utilities.DateFormatter;
import one.block.eosiojava.utilities.Utils;
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

    public byte[] toBinary() {
        String sendTransactionRequestAsString = Utils.getGson(DateFormatter.BACKEND_DATE_PATTERN).toJson(this);
        byte[] sendTransactionRequestAsBytes = sendTransactionRequestAsString.getBytes();
        byte[] prependedTransactionRequestAsBytes = new byte[1 + sendTransactionRequestAsBytes.length];
        prependedTransactionRequestAsBytes[0] = (char)0;
        for(int j = 0; j < sendTransactionRequestAsBytes.length; j++) {
            prependedTransactionRequestAsBytes[j + 1] = sendTransactionRequestAsBytes[j];
        }

        return prependedTransactionRequestAsBytes;
    }
}
