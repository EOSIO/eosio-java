package one.block.eosiojava.implementations;

import java.util.List;
import java.util.Map;
import one.block.eosiojava.models.wasmAbiProvider.WasmABI;

public class WasmABIProviderImpl {
    public Map wasmAbis;

    public void setWasmAbis(List<WasmABI> wasmAbis) {
        wasmAbis.forEach(wasmABI -> {
            wasmABI.reset();

            this.wasmAbis.put(wasmABI.account, wasmABI);
        });
    }
}
