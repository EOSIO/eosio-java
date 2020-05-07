package one.block.eosiojava.models.rpcProvider;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import org.bitcoinj.core.Sha256Hash;
import org.bouncycastle.util.encoders.Hex;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ContextFreeData implements Serializable {
    @SerializedName("context_free_data")
    @NotNull
    public List<String> contextFreeData;

    @NotNull
    public List<String> originalContextFreeData;

    public ContextFreeData(@NotNull List<String> contextFreeData) {
        this.setContextFreeData(contextFreeData);
    }

    @NotNull
    public List<String> getContextFreeData() { return contextFreeData; }

    public void setContextFreeData(@NotNull List<String> contextFreeData) {
        List<String> serializedContextFreeData = new ArrayList<String>();

        for(String cfd : contextFreeData) {
            serializedContextFreeData.add(Hex.toHexString(cfd.getBytes()));
        }

        this.contextFreeData = serializedContextFreeData;
        this.originalContextFreeData = contextFreeData;
    }

    // This will need to be updated to be dynamic
    public String getPackedContextFreeData() {
        String packedContextFreeData = String.format("%02X", this.contextFreeData.size());

        for(int i = 0; i < this.contextFreeData.size(); i++) {
            String cfd = this.contextFreeData.get(i);
            packedContextFreeData += String.format("%02X", cfd.length() / 2) + cfd;
        }

        return packedContextFreeData;
    }

    public String getHexContextFreeData() {
        if (this.contextFreeData.size() == 0) {
            return "";
        }
        byte[] bytes = new byte[this.getTotalBytes()];
        bytes[0] = Byte.parseByte(String.valueOf(this.contextFreeData.size()));
        int index = 1;
        for(String cfd : this.originalContextFreeData) {
            byte[] cfdBytes = cfd.getBytes();
            bytes[index] = Byte.parseByte(String.valueOf(cfdBytes.length));
            index++;
            for (int i = 0; i < cfdBytes.length; i++) {
                bytes[index] = cfdBytes[i];
                index++;
            }
        }

        return Hex.toHexString(Sha256Hash.hash(bytes));
    }

    private Integer getTotalBytes() {
        int bytes = this.originalContextFreeData.size();
        for(String cfd : this.originalContextFreeData) {
            bytes += 1 + cfd.getBytes().length;
        }
        return bytes;
    }
}
