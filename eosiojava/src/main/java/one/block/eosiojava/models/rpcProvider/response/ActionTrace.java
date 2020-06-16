package one.block.eosiojava.models.rpcProvider.response;

import com.google.gson.annotations.SerializedName;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import one.block.eosiojava.models.rpcProvider.Action;
import org.bouncycastle.util.encoders.Hex;

public class ActionTrace {
    private String name;
    private String data;

    @SerializedName("action_ordinal")
    private Integer actionOrdinal;

    @SerializedName("creator_action_ordinal")
    private Integer creatorActionOrdinal;

    @SerializedName("closest_unnotified_ancestor_action_ordinal")
    private Integer closestUnnotifiedAncestorActionOrdinal;

    @SerializedName("receipt")
    private Map receipt;

    @SerializedName("receiver")
    private String receiver;

    @SerializedName("act")
    private Map act;

    @SerializedName("context_free")
    private Boolean contextFree;

//    @SerializedName("elapsed")
//    private Integer elapsed;
//
//    @SerializedName("console")
//    private String console;
//
//    @SerializedName("trx_id")
//    private String transactionId;
//
//    @SerializedName("block_num")
//    private Integer blockNumber;
//
//    @SerializedName("block_time")
//    private String blockTime;
//
//    @SerializedName("producer_block_id")
//    private String producerBlockId;
//
//    @SerializedName("account_ram_deltas")
//    private Map account_ram_deltas;
//
//    @SerializedName("account_disk_deltas")
//    private Map account_disk_deltas;
//
//    @SerializedName("except")
//    private String except;
//
//    @SerializedName("error_code")
//    private String error_code;

    @SerializedName("return_value")
    private String returnValue;

    public ActionTrace() throws UnsupportedEncodingException {
        String actionName = "normal"; // Necessary to prepend

        String testReturnValue = "d2040000"; // 1234
        String testReturnValue2 = "000000000090b1ca"; // "test"

        byte[] bytes = Hex.decode(testReturnValue);
        String test = new String(bytes, "UTF-8");

        byte[] bytes2 = Hex.decode(testReturnValue2);
        String test2 = new String(bytes2, "UTF-8");

        String testDerp = "something";
    }
}
