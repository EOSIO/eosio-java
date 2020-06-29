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

    public ActionTrace() {
        String actionName = "normal"; // Necessary to prepend

        // returning 10 becomes "0a000000" --- Numbers can be parsed with Integer.parseInt(0000000a, 16)
        // returning 2 becomes "02000000"
        // returning 3 becomes "03000000"
        // returning 16 becomes "10000000"
        // "a" becomes "0000000000000030"
        // "b" becomes "0000000000000038"
        // "c" becomes "0000000000000040"
        // "d" becomes "0000000000000048"
        // "1" becomes "0000000000000008"
        // "2" becomes "0000000000000010"
        // 1234 becomes "d2040000"
        // "test" becomes "000000000090b1ca"
        // "1234" becomes "0000000000408608"
        // "12" becomes "0000000000008008"
        // 100 becomes "64000000"
        // 128 becomes "80000000"
        // 129 becomes "81000000"
        // 127 becomes "7f000000"
        // 255 becomes "ff000000"
        // 256 becomes "00010000"
        // 257 becomes "01010000"
        // 9999999999999999999 becomes "ffffe789" and is truncated to 2313682943 instead


//        String testReturnValue = "d2040000"; // 1234
//        String testReturnValue2 = "000000000090b1ca"; // "test"
//
//        ByteBuffer buffer = ByteBuffer.allocate(10);
//        buffer.put("normal".getBytes());
//        buffer.put(Hex.decode(testReturnValue));
//
//        byte[] bytesTest = buffer.array();
//        String testStr = new String(bytesTest, "UTF-8");
//
//        byte[] bytes = Hex.decode(testReturnValue);
//        String test = new String(bytes, "UTF-8");
//
//        String derp = DatatypeConverter.printBase64Binary(bytes);
//        byte[] der = DatatypeConverter.parseBase64Binary(testReturnValue);
//        String gotIt = new String(der);
//
//        String result = BaseEncoding.base32Hex().encode(bytes);
//
//        byte[] bytes2 = Hex.decode(testReturnValue2);
//        String test2 = new String(bytes2);
//
//        String testDerp = "something";
//        String returnValueTwo = "00000002";
//        byte[] bytes = returnValueTwo.getBytes();
//
//        String bin = hexToBin("02000000");
//        String bin2 = hexToBin("00000020");
//
//        int charCode = Integer.parseInt("02000000", 2);
//        int charCode2 = Integer.parseInt("00000020", 2);
//
//        String str = new Character((char)charCode).toString();
//
//        String result = new String(Hex.decode(returnValueTwo));
//        String result2 = new String(bytes);
//
//        byte[] hexData = hexStringToByteArray(returnValueTwo);
//
    }

    public String getHexReturnValue() {
        return this.returnValue;
    }

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
