package one.block.eosiojava.models.rpcProvider;

import org.bouncycastle.util.encoders.Hex;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ContextFreeData implements Serializable {
    @NotNull
    public List<String> rawContextFreeData;

    public ContextFreeData(@NotNull List<String> contextFreeData) {
        this.rawContextFreeData = contextFreeData;
    }

    @NotNull
    public List<String> getContextFreeData() {
        return this.rawContextFreeData;
    }

    @NotNull
    public List<String> getHexContextFreeData() {
        List<String> hexedContextFreeData = new ArrayList<String>();

        for(String cfd : rawContextFreeData) {
            hexedContextFreeData.add(Hex.toHexString(cfd.getBytes()));
        }

        return hexedContextFreeData;
    }

    public String getPackedContextFreeData() {
        if (this.rawContextFreeData.size() == 0) {
            return "";
        }
        List<String> hexContextFreeData = this.getHexContextFreeData();
        String packedContextFreeData = String.format("%02X", hexContextFreeData.size());

        for(int i = 0; i < hexContextFreeData.size(); i++) {
            String cfd = hexContextFreeData.get(i);
            packedContextFreeData += String.format("%02X", cfd.length() / 2) + cfd;
        }

        return packedContextFreeData;
    }
}
