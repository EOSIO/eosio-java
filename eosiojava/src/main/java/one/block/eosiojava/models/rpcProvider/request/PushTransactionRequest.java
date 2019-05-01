package one.block.eosiojava.models.rpcProvider.request;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * The request of PushTransactionRequest RPC call {@link one.block.eosiojava.interfaces.IRPCProvider#pushTransaction(PushTransactionRequest)}
 */
public class PushTransactionRequest {

    /**
     * List of signatures required to authorize transaction
     */
    @SerializedName("signatures")
    @NotNull
    private List<String> signatures;

    /**
     * The compression used, usually 0.
     */
    @SerializedName("compression")
    private int compression;

    /**
     * Context free data in hex
     */
    @SerializedName("packed_context_free_data")
    private String packagedContextFreeData;

    /**
     * The Pack Transaction (Serialized Transaction).
     * <br> It is serialized version of {@link one.block.eosiojava.models.rpcProvider.Transaction}.
     */
    @SerializedName("packed_trx")
    @NotNull
    private String packTrx;

    /**
     * Instantiates a new PushTransactionRequest.
     *
     * @param signatures the list of signatures required to authorize transaction
     * @param compression the compression used, usually 0.
     * @param packagedContextFreeData the context free data in hex
     * @param packTrx the packed Transaction (serialized transaction). It is serialized version
     * of {@link one.block.eosiojava.models.rpcProvider.Transaction}.
     */
    public PushTransactionRequest(@NotNull List<String> signatures, int compression,
            String packagedContextFreeData, @NotNull String packTrx) {
        this.signatures = signatures;
        this.compression = compression;
        this.packagedContextFreeData = packagedContextFreeData;
        this.packTrx = packTrx;
    }

    /**
     * Gets list of signatures required to authorize transaction.
     *
     * @return the list of signatures
     */
    @NotNull
    public List<String> getSignatures() {
        return signatures;
    }

    /**
     * Sets list of signatures required to authorize transaction.
     *
     * @param signatures the list of signatures.
     */
    public void setSignatures(@NotNull List<String> signatures) {
        this.signatures = signatures;
    }

    /**
     * Gets the compression used, usually 0.
     *
     * @return the compression.
     */
    public int getCompression() {
        return compression;
    }

    /**
     * Sets the compression used, usually 0.
     *
     * @param compression the compression.
     */
    public void setCompression(int compression) {
        this.compression = compression;
    }

    /**
     * Gets packaged context free data in hex.
     *
     * @return the packaged context free data in hex.
     */
    public String getPackagedContextFreeData() {
        return packagedContextFreeData;
    }

    /**
     * Sets packaged context free data in hex
     *
     * @param packagedContextFreeData the packaged context free data in hex.
     */
    public void setPackagedContextFreeData(String packagedContextFreeData) {
        this.packagedContextFreeData = packagedContextFreeData;
    }

    /**
     * Gets the packed transaction (serialized transaction).
     * <br> It is serialized version of {@link one.block.eosiojava.models.rpcProvider.Transaction}.
     *
     * @return the Pack Transaction (Serialized Transaction).
     */
    @NotNull
    public String getPackTrx() {
        return packTrx;
    }

    /**
     * Sets the packed transaction (serialized transaction).
     * <br> It is the serialized version of {@link one.block.eosiojava.models.rpcProvider.Transaction}.
     *
     * @param packTrx the packed transaction (serialized transaction).
     */
    public void setPackTrx(@NotNull String packTrx) {
        this.packTrx = packTrx;
    }
}
