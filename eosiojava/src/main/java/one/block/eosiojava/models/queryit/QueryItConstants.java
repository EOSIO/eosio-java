package one.block.eosiojava.models.queryit;

/**
 * Queryit abi variants and constants
 */
public class QueryItConstants {

    /**
     * AnyObject type - structure will be an object ["any_object",[{"name":"test","value":[]}]]
     */
    public static final String ANY_OBJECT_TYPE = "any_object";
    /**
     * AnyArray type - structure will be an array ["any_array", [["any_array", [["string", "test"]]]]]
     */
    public static final String ANY_ARRAY_TYPE = "any_array";
    /**
     * NullT type - structure will be an empty array []
     */
    public static final String NULL_T_TYPE = "null_t";
    /**
     * Int64 primitive - structure will contain ["int64", 1]
     */
    public static final String INT64_TYPE = "int64";
    /**
     * UInt64 primitive - structure will contain ["uint64", 1]
     */
    public static final String UINT64_TYPE = "uint64";
    /**
     * Int32 primitive - structure will contain ["int32", 1]
     */
    public static final String INT32_TYPE = "int32";
    /**
     * UInt32 primitive - structure will contain ["uint32", 1]
     */
    public static final String UINT32_TYPE = "uint32";
    /**
     * Int16 primitive - structure will contain ["int16", 1]
     */
    public static final String INT16_TYPE = "int16";
    /**
     * UInt16 primitive - structure will contain ["uint16", 1]
     */
    public static final String UINT16_TYPE = "uint16";
    /**
     * Int8 primitive - structure will contain ["int8", 1]
     */
    public static final String INT8_TYPE = "int8";
    /**
     * UInt8 primitive - structure will contain ["uint8", 1]
     */
    public static final String UINT8_TYPE = "uint8";
    /**
     * TimePoint primitive - structure will contain ["time_point", "2020-07-16T10:44:15.000"]
     */
    public static final String TIME_POINT_TYPE = "time_point";
    /**
     * Checksum256 primitive - structure will contain ["checksum256", "532eaabd9574880dbf76b9b8cc00832c20a6ec113d682299550d7a6e0f345e25"]
     */
    public static final String CHECKSUM256_TYPE = "checksum256";
    /**
     * Float64 primitive - structure will contain ["float64", 1.00]
     */
    public static final String FLOAT64_TYPE = "float64";
    /**
     * String primitive - structure will contain ["string", "test"]
     */
    public static final String STRING_TYPE = "string";
    /**
     * Bytes primitive - structure will contain ["bytes", "0101"]
     */
    public static final String BYTES_TYPE = "bytes";
    /**
     * Symbol primitive - structure will contain ["symbol", "EOS 10.000"]
     */
    public static final String SYMBOL_TYPE = "symbol";
    /**
     * SymbolCode primitive - structure will contain ["symbol_code", "EOS"]
     */
    public static final String SYMBOL_CODE_TYPE = "symbol_code";
    /**
     * Asset primitive - structure will contain ["asset", "EOS 10.000"]
     */
    public static final String ASSET_TYPE = "asset";
    /**
     * TimePoint optional character - Java does not interpret timepoint to Instant correctly without the Z
     */
    public static final char OPTIONAL_ZONE_CHAR = 'Z';
}
