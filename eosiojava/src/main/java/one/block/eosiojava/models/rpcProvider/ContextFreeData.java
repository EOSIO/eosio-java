package one.block.eosiojava.models.rpcProvider;

import java.nio.ByteBuffer;
import org.bitcoinj.core.Sha256Hash;
import org.bouncycastle.util.encoders.Hex;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ContextFreeData {
    @NotNull
    public List<String> data;

    @NotNull
    public byte[] rawBytes;

    public ContextFreeData(@NotNull List<String> contextFreeData) {
        this.setData(contextFreeData);
    }

    @NotNull
    public List<String> getData() {
        return this.data;
    }

    @NotNull
    public byte[] getBytes() {
        return this.rawBytes;
    }

    public void setBytes(byte[] bytes) {
        this.rawBytes = bytes;
    }

    public void setData(List<String> contextFreeData) {
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

    public String getHexed() {
        if (!this.hasData()) {
            return "";
        }

        return Hex.toHexString(this.getBytes()).toUpperCase();
    }

    public String getSerialized() {
        if (!this.hasData()) {
            return "";
        }

        return Hex.toHexString(Sha256Hash.hash(this.getBytes()));
    }

    public boolean hasData() {
        return this.data.isEmpty();
    }

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

    private Integer getTotalBytes(List<String> contextFreeData) {
        int bytes =  this.getByteSizePrefix(contextFreeData.size());
        for(String cfd : contextFreeData) {
            byte[] cfdBytes = cfd.getBytes();
            bytes += this.getByteSizePrefix(cfdBytes.length) + cfdBytes.length;
        }
        return bytes;
    }

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

    private boolean isLessThan128(int length) {
        return length >>> 7 == 0;
    }

    private int subtract128(int length) {
        return length >>> 7;
    }
}
