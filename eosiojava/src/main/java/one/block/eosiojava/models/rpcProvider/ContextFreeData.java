package one.block.eosiojava.models.rpcProvider;

import org.bouncycastle.util.encoders.Hex;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ContextFreeData {
    @NotNull
    public List<String> rawContextFreeData;

    public ContextFreeData(@NotNull List<String> contextFreeData) {
        this.rawContextFreeData = contextFreeData;
    }

    @NotNull
    public List<String> getRaw() {
        return this.rawContextFreeData;
    }

    @NotNull
    public List<String> getHexed() {
        List<String> hexedContextFreeData = new ArrayList<String>();

        for(String cfd : rawContextFreeData) {
            hexedContextFreeData.add(Hex.toHexString(cfd.getBytes()));
        }

        return hexedContextFreeData;
    }

    public String getPacked() {
        if (this.rawContextFreeData.size() == 0) {
            return "";
        }
        List<String> hexContextFreeData = this.getHexed();
        String packedContextFreeData = this.getHexPrefix(hexContextFreeData.size());

        for(int i = 0; i < hexContextFreeData.size(); i++) {
            String hexData = hexContextFreeData.get(i);
            packedContextFreeData += this.getHexPrefix(hexData.length() / 2) + hexData;
        }

        return packedContextFreeData;
    }

    private String getHexPrefix(int length) {
        return String.format("%02X", length);
    }
}
