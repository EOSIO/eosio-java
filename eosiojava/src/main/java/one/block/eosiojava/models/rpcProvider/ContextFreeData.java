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
    @NotNull
    public List<String> rawContextFreeData;

    public ContextFreeData(@NotNull List<String> contextFreeData) {
        this.setContextFreeData(contextFreeData);
    }

    @NotNull
    public List<String> getContextFreeData() {
        List<String> hexedContextFreeData = new ArrayList<String>();

        for(String cfd : rawContextFreeData) {
            hexedContextFreeData.add(Hex.toHexString(cfd.getBytes()));
        }

        return hexedContextFreeData;
    }

    public void setContextFreeData(@NotNull List<String> contextFreeData) {
        this.rawContextFreeData = contextFreeData;
    }

    public String getPackedContextFreeData() {
        if (this.rawContextFreeData.size() == 0) {
            return "";
        }
        List<String> hexedContextFreeData = this.getContextFreeData();
        String packedContextFreeData = String.format("%02X", hexedContextFreeData.size());

        for(int i = 0; i < hexedContextFreeData.size(); i++) {
            String cfd = hexedContextFreeData.get(i);
            packedContextFreeData += String.format("%02X", cfd.length() / 2) + cfd;
        }

        return packedContextFreeData;
    }

//    public String getSerializedContextFreeData() {
//        if (this.originalContextFreeData.size() == 0) {
//            return "";
//        }
//        byte[] bytes = new byte[this.getTotalBytes()];
//        bytes[0] = Byte.parseByte(String.valueOf(this.originalContextFreeData.size()));
//        int index = 1;
//        for(String cfd : this.originalContextFreeData) {
//            byte[] cfdBytes = cfd.getBytes();
//            bytes[index] = Byte.parseByte(String.valueOf(cfdBytes.length));
//            index++;
//            for (int i = 0; i < cfdBytes.length; i++) {
//                bytes[index] = cfdBytes[i];
//                index++;
//            }
//        }
//
//        return Hex.toHexString(Sha256Hash.hash(bytes));
//    }

    private Integer getTotalBytes() {
        int bytes = 1;
        for(String cfd : this.rawContextFreeData) {
            bytes += 1 + cfd.getBytes().length;
        }
        return bytes;
    }
}
