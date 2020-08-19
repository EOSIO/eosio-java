package one.block.eosiojava.models.rpcProvider;

import java.nio.ByteBuffer;
import one.block.eosiojava.interfaces.IRPCProvider;
import one.block.eosiojava.interfaces.ISignatureProvider;
import one.block.eosiojava.models.rpcProvider.request.SendTransactionRequest;
import one.block.eosiojava.models.signatureProvider.EosioTransactionSignatureRequest;
import one.block.eosiojava.interfaces.IAMQPProvider;
import org.bitcoinj.core.Sha256Hash;
import org.bouncycastle.util.encoders.Hex;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * The ContextFreeData class which holds raw context free data, its bytes, and methods to format
 * the data to pass to the signatureProvider and rpcProvider.
 */
public class ContextFreeData {
    /**
     * The list of raw data being added as context free data.
     */
    @NotNull
    private List<String> data;

    /**
     * The raw data converted to bytes to be used later for formatting.
     */
    @NotNull
    private byte[] rawBytes;

    /**
     * Instantiates a new ContextFreeData.
     *
     * @param contextFreeData the list of raw data to be added
     */
    public ContextFreeData(@NotNull List<String> contextFreeData) {
        this.setData(contextFreeData);
    }

    /**
     * Gets the list of raw data.
     * @return the raw data
     */
    @NotNull
    public List<String> getData() {
        return this.data;
    }

    /**
     * Gets the raw data converted to bytes.
     * @return the converted raw data in bytes
     */
    @NotNull
    public byte[] getBytes() {
        return this.rawBytes;
    }

    /**
     * Gets the hex representation of the data to be used by {@link IRPCProvider#sendTransaction(SendTransactionRequest)}
     * or {@link IAMQPProvider#send(byte[])} depending on configuration
     * @return the hexed data
     */
    public String getHexed() {
        if (!this.hasData()) {
            return "";
        }

        return Hex.toHexString(this.getBytes()).toUpperCase();
    }

    /**
     * Gets the 32 byte serialized representation of the data to be used by {@link ISignatureProvider#signTransaction(EosioTransactionSignatureRequest)}
     * @return the 32 byte serialized data
     */
    public String getSerialized() {
        if (!this.hasData()) {
            return "";
        }

        return Hex.toHexString(Sha256Hash.hash(this.getBytes()));
    }

    /**
     * Determines whether or not the instance contains data
     * @return boolean to determine if instance contains data
     */
    public boolean hasData() {
        return !this.data.isEmpty();
    }

    /**
     * Sets the raw data converted to bytes.
     * @param bytes the converted raw data in bytes
     */
    private void setBytes(byte[] bytes) {
        this.rawBytes = bytes;
    }

    /**
     * Sets both raw data and its bytes with prefixes.
     * @param contextFreeData list of raw data to be set
     */
    private void setData(List<String> contextFreeData) {
        this.data = contextFreeData;
        if (!this.hasData()) {
            this.setBytes(new byte[0]);
            return;
        }

        ByteBuffer buffer = ByteBuffer.allocate(this.getTotalBytes(this.data));

        pushPrefix(buffer, this.data.size());

        for(String cfd : this.data) {
            byte[] cfdBytes = cfd.getBytes();
            pushPrefix(buffer, cfdBytes.length);
            buffer.put(cfdBytes);
        }

        this.setBytes(buffer.array());
    }

    /**
     * Add the prefix for the data to the end of the buffer.
     * @param buffer the buffer to append bytes to
     * @param length the length of the data to determine prefix length
     */
    private void pushPrefix(ByteBuffer buffer, int length) {
        while(true) {
            if (this.isLessThan128(length)) {
                buffer.put((byte)length);
                break;
            } else {
                buffer.put((byte)(0x80 | (length & 0x7f)));
                length = this.subtract128(length);
            }
        }
    }

    /**
     * Determines how many bytes to allocate to the ByteBuffer
     * @param contextFreeData list of raw data
     * @return integer representing the number of bytes to allocate to the ByteBuffer
     */
    private Integer getTotalBytes(List<String> contextFreeData) {
        int bytes =  this.getByteSizePrefix(contextFreeData.size());
        for(String cfd : contextFreeData) {
            byte[] cfdBytes = cfd.getBytes();
            bytes += this.getByteSizePrefix(cfdBytes.length) + cfdBytes.length;
        }
        return bytes;
    }

    /**
     * Determines the number of bytes in the prefix
     * @param length the length of the bytes
     * @return the number of bytes in the prefix
     */
    private Integer getByteSizePrefix(int length) {
        int size = 0;
        while(true) {
            if (this.isLessThan128(length)) {
                size++;
                break;
            } else {
                size++;
                length = this.subtract128(length);
            }
        }

        return size;
    }

    /**
     * Determines whether or not there needs to be a bitwise shift to increase prefix size
     * @param length the length of the bytes
     * @return whether or not there needs to be a bitwise shift to increase prefix size
     */
    private boolean isLessThan128(int length) {
        return length >>> 7 == 0;
    }

    /**
     * Conducts bitwise shift to increase prefix size
     * @param length the length of the bytes
     * @return the updated length
     */
    private int subtract128(int length) {
        return length >>> 7;
    }
}
