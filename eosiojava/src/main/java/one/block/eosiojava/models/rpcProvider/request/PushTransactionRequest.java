package one.block.eosiojava.models.rpcProvider.request;

import java.util.List;

/**
 * The request of PushTransactionRequest RPC call.
 */
public class PushTransactionRequest {

    /**
     * The Signatures for packTrx
     */
    private List<String> signatures;

    /**
     * The Compression.
     */
    private int compression;

    /**
     * The Packaged context free data.
     */
    private String packagedContextFreeData;

    /**
     * The Pack Transaction (Serialized Transaction).
     * <br/> It is serialized version of {@link one.block.eosiojava.models.rpcProvider.Transaction}.
     */
    private String packTrx;

    /**
     * Instantiates a new Push transaction request.
     *
     * @param signatures the signatures
     * @param compression the compression
     * @param packagedContextFreeData the packaged context free data
     * @param packTrx the pack trx
     */
    public PushTransactionRequest(List<String> signatures, int compression,
            String packagedContextFreeData, String packTrx) {
        this.signatures = signatures;
        this.compression = compression;
        this.packagedContextFreeData = packagedContextFreeData;
        this.packTrx = packTrx;
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
     * Sets signatures.
     *
     * @param signatures the signatures
     */
    public void setSignatures(List<String> signatures) {
        this.signatures = signatures;
    }

    /**
     * Gets compression.
     *
     * @return the compression
     */
    public int getCompression() {
        return compression;
    }

    /**
     * Sets compression.
     *
     * @param compression the compression
     */
    public void setCompression(int compression) {
        this.compression = compression;
    }

    /**
     * Gets packaged context free data.
     *
     * @return the packaged context free data
     */
    public String getPackagedContextFreeData() {
        return packagedContextFreeData;
    }

    /**
     * Sets packaged context free data.
     *
     * @param packagedContextFreeData the packaged context free data
     */
    public void setPackagedContextFreeData(String packagedContextFreeData) {
        this.packagedContextFreeData = packagedContextFreeData;
    }

    /**
     * Gets pack trx.
     *
     * @return the pack trx
     */
    public String getPackTrx() {
        return packTrx;
    }

    /**
     * Sets pack trx.
     *
     * @param packTrx the pack trx
     */
    public void setPackTrx(String packTrx) {
        this.packTrx = packTrx;
    }
}
