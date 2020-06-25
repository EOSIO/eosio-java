package one.block.eosiojava.models.wasmAbiProvider;

public class WasmABI {
    public String account;

    public byte[] inputData;
    public byte[] ouputData0;
    public byte[] outputData1;

    // any
    public Object mod;
    public Object inst;
    public Object primitives;
    public Object actions;

    public WasmABI(String account, Object mod) {
        this.account = account;
        this.mod = mod;
    }

    public void reset() {

    }

    public void action_args_json_to_bin(String action) {

    }

    public void action_args_bin_to_json(String action, byte[] bin) {

    }

    public void action_ret_bin_to_json(String action, byte[] bin) {

    }
}
