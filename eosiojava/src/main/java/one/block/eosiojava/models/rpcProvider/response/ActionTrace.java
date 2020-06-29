package one.block.eosiojava.models.rpcProvider.response;

import com.google.common.io.BaseEncoding;
import com.google.gson.annotations.SerializedName;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Map;
import javax.xml.bind.DatatypeConverter;
import one.block.eosiojava.models.rpcProvider.Action;
import org.bouncycastle.util.encoders.Hex;

public class ActionTrace {
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

    @SerializedName("elapsed")
    private Integer elapsed;

    @SerializedName("console")
    private String console;

    @SerializedName("trx_id")
    private String transactionId;

    @SerializedName("block_num")
    private Integer blockNumber;

    @SerializedName("block_time")
    private String blockTime;

    @SerializedName("producer_block_id")
    private String producerBlockId;

    @SerializedName("account_ram_deltas")
    private Map account_ram_deltas;

    @SerializedName("account_disk_deltas")
    private Map account_disk_deltas;

    @SerializedName("except")
    private String except;

    @SerializedName("error_code")
    private String error_code;

    @SerializedName("return_value")
    private String returnValue;

    private String deserializedReturnValue;

    public String getHexReturnValue() { return this.returnValue; }

    public String getDeserializedReturnValue() { return this.deserializedReturnValue; }

    public String getAccountName() {
        return (String)this.act.get("account");
    }

    public String getActionName() {
        return (String)this.act.get("name");
    }

    public void setDeserializedReturnValue(String deserializedReturnValue) {
        this.deserializedReturnValue = deserializedReturnValue;
    }
}
