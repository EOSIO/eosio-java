package one.block.eosiojava.models.queryit;

import static junit.framework.TestCase.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.math.BigInteger;
import java.time.Instant;
import org.junit.Before;
import org.junit.Test;

public class QueryItTest {

    // For generic deserialization of arrays
//    Type listType = new TypeToken<ArrayList<AnyObject>>(){}.getType();
//
//    // FromJSON test
//    List<AnyObject> anyObjects = this.gson.fromJson(jsonContent, listType);

    private Gson gson;

    @Before
    public void setUpGSON() {
        String datePattern = "yyyy-MM-dd'T'hh:mm:ss zzz";
        this.gson = new GsonBuilder()
                .registerTypeAdapter(QueryIt.class, new QueryItDeserializer())
                .setDateFormat(datePattern)
                .disableHtmlEscaping().create();
    }

    @Test
    public void testConvertNull() {
        String jsonContent = "[]";

        QueryIt queryIt = this.gson.fromJson(jsonContent, QueryIt.class);

        assertNotNull(queryIt);
        assertNull(queryIt.getValue());
    }

    @Test
    public void testConvertString() {
        String expectedValue = "someValue";
        String jsonContent = "[\"string\", \"" + expectedValue + "\"]";

        QueryIt queryIt = this.gson.fromJson(jsonContent, QueryIt.class);

        assertNotNull(queryIt);
        assertEquals(expectedValue, queryIt.getValue());
    }

    @Test
    public void testConvertChecksum256() {
        String expectedValue = "532eaabd9574880dbf76b9b8cc00832c20a6ec113d682299550d7a6e0f345e25";
        String jsonContent = "[\"checksum256\", \"" + expectedValue + "\"]";

        QueryIt queryIt = this.gson.fromJson(jsonContent, QueryIt.class);

        assertNotNull(queryIt);
        assertEquals(expectedValue, queryIt.getValue());
    }

    @Test
    public void testConvertSymbol() {
        String expectedValue = "10.0000 EOS";
        String jsonContent = "[\"symbol\", \"" + expectedValue + "\"]";

        QueryIt queryIt = this.gson.fromJson(jsonContent, QueryIt.class);

        assertNotNull(queryIt);
        assertEquals(expectedValue, queryIt.getValue());
    }

    @Test
    public void testConvertSymbolCode() {
        String expectedValue = "EOS";
        String jsonContent = "[\"symbol_code\", \"" + expectedValue + "\"]";

        QueryIt queryIt = this.gson.fromJson(jsonContent, QueryIt.class);

        assertNotNull(queryIt);
        assertEquals(expectedValue, queryIt.getValue());
    }

    @Test
    public void testConvertAsset() {
        String expectedValue = "10.0000 EOS";
        String jsonContent = "[\"asset\", \"" + expectedValue + "\"]";

        QueryIt queryIt = this.gson.fromJson(jsonContent, QueryIt.class);

        assertNotNull(queryIt);
        assertEquals(expectedValue, queryIt.getValue());
    }

    @Test
    public void testConvertMaxUInt64() {
        BigInteger expectedValue = new BigInteger("18446744073709551615");
        String jsonContent = "[\"uint64\", \"" + expectedValue.toString() + "\"]";

        QueryIt queryIt = this.gson.fromJson(jsonContent, QueryIt.class);

        assertNotNull(queryIt);
        assertEquals(expectedValue, queryIt.getValue());
    }

    @Test
    public void testConvertMinUInt64() {
        BigInteger expectedValue = new BigInteger("0");
        String jsonContent = "[\"uint64\", \"" + expectedValue.toString() + "\"]";

        QueryIt queryIt = this.gson.fromJson(jsonContent, QueryIt.class);

        assertNotNull(queryIt);
        assertEquals(expectedValue, queryIt.getValue());
    }

    @Test
    public void testConvertMaxInt64() {
        Long expectedValue = 9223372036854775807L;
        String jsonContent = "[\"int64\", \"" + expectedValue.toString() + "\"]";

        QueryIt queryIt = this.gson.fromJson(jsonContent, QueryIt.class);

        assertNotNull(queryIt);
        assertEquals(expectedValue, queryIt.getValue());
    }

    @Test
    public void testConvertMinInt64() {
        Long expectedValue = -9223372036854775808L;
        String jsonContent = "[\"int64\", \"" + expectedValue.toString() + "\"]";

        QueryIt queryIt = this.gson.fromJson(jsonContent, QueryIt.class);

        assertNotNull(queryIt);
        assertEquals(expectedValue, queryIt.getValue());
    }

    @Test
    public void testConvertMaxUInt32() {
        Long expectedValue = 4294967295L;
        String jsonContent = "[\"uint32\", \"" + expectedValue.toString() + "\"]";

        QueryIt queryIt = this.gson.fromJson(jsonContent, QueryIt.class);

        assertNotNull(queryIt);
        assertEquals(expectedValue, queryIt.getValue());
    }

    @Test
    public void testConvertMinUInt32() {
        Long expectedValue = 0L;
        String jsonContent = "[\"uint32\", \"" + expectedValue.toString() + "\"]";

        QueryIt queryIt = this.gson.fromJson(jsonContent, QueryIt.class);

        assertNotNull(queryIt);
        assertEquals(expectedValue, queryIt.getValue());
    }

    @Test
    public void testConvertMaxInt32() {
        Integer expectedValue = 2147483647;
        String jsonContent = "[\"int32\", \"" + expectedValue.toString() + "\"]";

        QueryIt queryIt = this.gson.fromJson(jsonContent, QueryIt.class);

        assertNotNull(queryIt);
        assertEquals(expectedValue, queryIt.getValue());
    }

    @Test
    public void testConvertMinInt32() {
        Integer expectedValue = -2147483648;
        String jsonContent = "[\"int32\", \"" + expectedValue.toString() + "\"]";

        QueryIt queryIt = this.gson.fromJson(jsonContent, QueryIt.class);

        assertNotNull(queryIt);
        assertEquals(expectedValue, queryIt.getValue());
    }

    @Test
    public void testConvertMaxUInt16() {
        Integer expectedValue = 65535;
        String jsonContent = "[\"uint16\", \"" + expectedValue.toString() + "\"]";

        QueryIt queryIt = this.gson.fromJson(jsonContent, QueryIt.class);

        assertNotNull(queryIt);
        assertEquals(expectedValue, queryIt.getValue());
    }

    @Test
    public void testConvertMinUInt16() {
        Integer expectedValue = 0;
        String jsonContent = "[\"uint16\", \"" + expectedValue.toString() + "\"]";

        QueryIt queryIt = this.gson.fromJson(jsonContent, QueryIt.class);

        assertNotNull(queryIt);
        assertEquals(expectedValue, queryIt.getValue());
    }

    @Test
    public void testConvertMaxInt16() {
        Integer expectedValue = 32767;
        String jsonContent = "[\"int16\", \"" + expectedValue.toString() + "\"]";

        QueryIt queryIt = this.gson.fromJson(jsonContent, QueryIt.class);

        assertNotNull(queryIt);
        assertEquals(expectedValue, queryIt.getValue());
    }

    @Test
    public void testConvertMinInt16() {
        Integer expectedValue = -32768;
        String jsonContent = "[\"int16\", \"" + expectedValue.toString() + "\"]";

        QueryIt queryIt = this.gson.fromJson(jsonContent, QueryIt.class);

        assertNotNull(queryIt);
        assertEquals(expectedValue, queryIt.getValue());
    }

    @Test
    public void testConvertMaxUInt8() {
        Integer expectedValue = 256;
        String jsonContent = "[\"uint8\", \"" + expectedValue.toString() + "\"]";

        QueryIt queryIt = this.gson.fromJson(jsonContent, QueryIt.class);

        assertNotNull(queryIt);
        assertEquals(expectedValue, queryIt.getValue());
    }

    @Test
    public void testConvertMinUInt8() {
        Integer expectedValue = 0;
        String jsonContent = "[\"uint8\", \"" + expectedValue.toString() + "\"]";

        QueryIt queryIt = this.gson.fromJson(jsonContent, QueryIt.class);

        assertNotNull(queryIt);
        assertEquals(expectedValue, queryIt.getValue());
    }

    @Test
    public void testConvertMaxInt8() {
        Integer expectedValue = 127;
        String jsonContent = "[\"int8\", \"" + expectedValue.toString() + "\"]";

        QueryIt queryIt = this.gson.fromJson(jsonContent, QueryIt.class);

        assertNotNull(queryIt);
        assertEquals(expectedValue, queryIt.getValue());
    }

    @Test
    public void testConvertMinInt8() {
        Integer expectedValue = -128;
        String jsonContent = "[\"int8\", \"" + expectedValue.toString() + "\"]";

        QueryIt queryIt = this.gson.fromJson(jsonContent, QueryIt.class);

        assertNotNull(queryIt);
        assertEquals(expectedValue, queryIt.getValue());
    }

    @Test
    public void testConvertMaxFloat64() {
        Float expectedValue = Float.parseFloat("3.40282e+038");
        String jsonContent = "[\"float64\", \"" + expectedValue.toString() + "\"]";

        QueryIt queryIt = this.gson.fromJson(jsonContent, QueryIt.class);

        assertNotNull(queryIt);
        assertEquals(expectedValue, queryIt.getValue());
    }

    @Test
    public void testConvertMinFloat64() {
        Float expectedValue = Float.parseFloat("1.17549e-038");
        String jsonContent = "[\"float64\", \"" + expectedValue.toString() + "\"]";

        QueryIt queryIt = this.gson.fromJson(jsonContent, QueryIt.class);

        assertNotNull(queryIt);
        assertEquals(expectedValue, queryIt.getValue());
    }

    @Test
    public void testConvertTimepointWithoutZone() {
        String expectedValueAsString = "2020-07-16T10:38:46.000";
        Instant expectedValue = Instant.parse(expectedValueAsString + 'Z');
        String expectedValueToString = "2020-07-16T10:38:46Z";
        String jsonContent = "[\"time_point\", \"" + expectedValueAsString + "\"]";

        QueryIt queryIt = this.gson.fromJson(jsonContent, QueryIt.class);

        assertNotNull(queryIt);
        assertEquals(expectedValue, queryIt.getValue());
        assertEquals(expectedValueToString, queryIt.getValue().toString());
    }

    @Test
    public void testConvertTimepointWithZone() {
        String expectedValueAsString = "2020-07-16T10:38:46.500Z";
        Instant expectedValue = Instant.parse(expectedValueAsString);
        String expectedValueToString = "2020-07-16T10:38:46.500Z";
        String jsonContent = "[\"time_point\", \"" + expectedValueAsString + "\"]";

        QueryIt queryIt = this.gson.fromJson(jsonContent, QueryIt.class);

        assertNotNull(queryIt);
        assertEquals(expectedValue, queryIt.getValue());
        assertEquals(expectedValueToString, queryIt.getValue().toString());
    }

    @Test
    public void testConvertBytes() {
        String expectedValue = "0110";
        String jsonContent = "[\"bytes\", \"" + expectedValue + "\"]";

        QueryIt queryIt = this.gson.fromJson(jsonContent, QueryIt.class);

        assertNotNull(queryIt);
        assertEquals(expectedValue, queryIt.getValue());
    }

    @Test
    public void testEmptyAnyArray() {
        int expectedSubQueriesCount = 0;
        String jsonContent = "[\"any_array\", []]";

        QueryIt queryIt = this.gson.fromJson(jsonContent, QueryIt.class);

        assertNotNull(queryIt);
        assertEquals(expectedSubQueriesCount, queryIt.getQueries().size());
    }

    @Test
    public void testSimpleAnyArray() {
        String expectedSubqueryValue = "test";
        int expectedSubQueriesCount = 1;
        String jsonContent = "[\"any_array\", [[\"string\", \"" + expectedSubqueryValue + "\"]]]";

        QueryIt queryIt = this.gson.fromJson(jsonContent, QueryIt.class);

        assertNotNull(queryIt);
        assertEquals(expectedSubQueriesCount, queryIt.getQueries().size());
        assertEquals(expectedSubqueryValue, queryIt.getQueries().get(0).getValue());
    }

    @Test
    public void testAnyArrayWithMultipleAnyVars() {
        String expectedSubqueryStringValue = "test";
        Long expectedSubqueryIntValue = 1234L;
        int expectedSubQueriesCount = 2;
        String jsonContent = "[\"any_array\", [[\"string\", \"" + expectedSubqueryStringValue + "\"], [\"uint32\", " + expectedSubqueryIntValue + "]]]";

        QueryIt queryIt = this.gson.fromJson(jsonContent, QueryIt.class);

        assertNotNull(queryIt);
        assertEquals(expectedSubQueriesCount, queryIt.getQueries().size());
        assertEquals(expectedSubqueryStringValue, queryIt.getQueries().get(0).getValue());
        assertEquals(expectedSubqueryIntValue, queryIt.getQueries().get(1).getValue());
    }

    @Test
    public void testEmptyNestedAnyArray() {
        int expectedValue = 1;
        int expectedNestedArrays = 0;
        String jsonContent = "[\"any_array\", [[\"any_array\", []]]]";

        QueryIt queryIt = this.gson.fromJson(jsonContent, QueryIt.class);

        assertNotNull(queryIt);
        assertEquals(expectedValue, queryIt.getQueries().size());
        assertEquals(expectedNestedArrays, queryIt.getQueries().get(0).getQueries().size());
    }

    @Test
    public void testSimpleNestedAnyArray() {
        int expectedValue = 1;
        int expectedNestedArrays = 1;
        String jsonContent = "[\"any_array\", [[\"any_array\", [[\"string\", \"test\"]]]]]";

        QueryIt queryIt = this.gson.fromJson(jsonContent, QueryIt.class);

        assertNotNull(queryIt);
        assertEquals(expectedValue, queryIt.getQueries().size());
        assertEquals(expectedNestedArrays, queryIt.getQueries().get(0).getQueries().size());
    }

    @Test
    public void testEmptyValueAnyObject() {
        String expectedName = "test";
        int expectedNestedArrays = 1;
        String jsonContent = "[\"any_object\",[{\"name\":\"" + expectedName + "\",\"value\":[]}]]";

        QueryIt queryIt = this.gson.fromJson(jsonContent, QueryIt.class);

        assertNotNull(queryIt);
        assertEquals(expectedNestedArrays, queryIt.getFields().size());
        assertEquals(expectedName, queryIt.getFields().get(0).getName());
    }

    @Test
    public void testSimpleAnyObject() {
        String expectedName = "test";
        int expectedNestedArrays = 1;
        BigInteger expectedIntValue = new BigInteger("7");
        String jsonContent = "[\"any_object\",[{\"name\":\"" + expectedName + "\",\"value\":[\"uint64\"," + expectedIntValue + "]}]]";

        QueryIt queryIt = this.gson.fromJson(jsonContent, QueryIt.class);

        assertNotNull(queryIt);
        assertEquals(expectedNestedArrays, queryIt.getFields().size());
        assertEquals(expectedName, queryIt.getFields().get(0).getName());
        assertEquals(expectedIntValue, queryIt.getFields().get(0).getValue().getValue());
    }

    @Test
    public void testFullAnyObjectConversion() {
        int expectedNestedArrays = 7;
        int expectedBidsNestedArrays = 1;
        int expectedBidsEdgesNestedArrays = 4;
        String jsonContent = "[\"any_object\",[{\"name\":\"quote\",\"value\":[\"string\",\"538059690.21 USD\"]},{\"name\":\"base\",\"value\":[\"string\",\"89352.54000000 BTC\"]},{\"name\":\"bancorPrice\",\"value\":[\"string\",\"6021.76 USD\"]},{\"name\":\"lastTradePrice\",\"value\":[\"string\",\"6010.87 USD\"]},{\"name\":\"lastTradeQuantity\",\"value\":[\"string\",\"161.89085947 BTC\"]},{\"name\":\"asks\",\"value\":[\"any_object\",[{\"name\":\"edges\",\"value\":[\"any_array\",[]]}]]},{\"name\":\"bids\",\"value\":[\"any_object\",[{\"name\":\"edges\",\"value\":[\"any_array\",[[\"any_object\",[{\"name\":\"node\",\"value\":[\"any_object\",[{\"name\":\"orderId\",\"value\":[\"uint64\",\"4\"]},{\"name\":\"owner\",\"value\":[\"string\",\"maker\"]},{\"name\":\"handle\",\"value\":[\"uint64\",\"1594867124500000\"]},{\"name\":\"price\",\"value\":[\"string\",\"5000.00 USD\"]},{\"name\":\"cost\",\"value\":[\"string\",\"0.00 USD\"]},{\"name\":\"remaining\",\"value\":[\"string\",\"1.00000000 BTC\"]},{\"name\":\"size\",\"value\":[\"string\",\"1.00000000 BTC\"]},{\"name\":\"created\",\"value\":[\"time_point\",\"2020-07-16T10:38:46.000\"]}]]}]],[\"any_object\",[{\"name\":\"node\",\"value\":[\"any_object\",[{\"name\":\"orderId\",\"value\":[\"uint64\",\"9\"]},{\"name\":\"owner\",\"value\":[\"string\",\"maxnamstorm\"]},{\"name\":\"handle\",\"value\":[\"uint64\",\"1594867454500000\"]},{\"name\":\"price\",\"value\":[\"string\",\"5000.00 USD\"]},{\"name\":\"cost\",\"value\":[\"string\",\"0.00 USD\"]},{\"name\":\"remaining\",\"value\":[\"string\",\"10.00000000 BTC\"]},{\"name\":\"size\",\"value\":[\"string\",\"10.00000000 BTC\"]},{\"name\":\"created\",\"value\":[\"time_point\",\"2020-07-16T10:44:15.000\"]}]]}]],[\"any_object\",[{\"name\":\"node\",\"value\":[\"any_object\",[{\"name\":\"orderId\",\"value\":[\"uint64\",\"10\"]},{\"name\":\"owner\",\"value\":[\"string\",\"maxnamstorm\"]},{\"name\":\"handle\",\"value\":[\"uint64\",\"1594867537500000\"]},{\"name\":\"price\",\"value\":[\"string\",\"5000.00 USD\"]},{\"name\":\"cost\",\"value\":[\"string\",\"0.00 USD\"]},{\"name\":\"remaining\",\"value\":[\"string\",\"10.00000000 BTC\"]},{\"name\":\"size\",\"value\":[\"string\",\"10.00000000 BTC\"]},{\"name\":\"created\",\"value\":[\"time_point\",\"2020-07-16T10:45:38.500\"]}]]}]],[\"any_object\",[{\"name\":\"node\",\"value\":[\"any_object\",[{\"name\":\"orderId\",\"value\":[\"uint64\",\"7\"]},{\"name\":\"owner\",\"value\":[\"string\",\"maker\"]},{\"name\":\"handle\",\"value\":[\"uint64\",\"1594867290500000\"]},{\"name\":\"price\",\"value\":[\"string\",\"5199.50 USD\"]},{\"name\":\"cost\",\"value\":[\"string\",\"0.00 USD\"]},{\"name\":\"remaining\",\"value\":[\"string\",\"1.00000000 BTC\"]},{\"name\":\"size\",\"value\":[\"string\",\"1.00000000 BTC\"]},{\"name\":\"created\",\"value\":[\"time_point\",\"2020-07-16T10:41:31.000\"]}]]}]]]]}]]}]]";

        // FromJSON test
        QueryIt queryIt = this.gson.fromJson(jsonContent, QueryIt.class);

        assertNotNull(queryIt);
        assertEquals(expectedNestedArrays, queryIt.getFields().size());
        assertEquals(expectedBidsNestedArrays, queryIt.getFields().get(6).getValue().getFields().size());
        assertEquals(expectedBidsEdgesNestedArrays, queryIt.getFields().get(6).getValue().getFields().get(0).getValue().getQueries().size());
    }

//    @Test
//    public void testAnyObject() {
//        String expectedValue = "0110";
//        String jsonContent = "[\"any_object\",[{\"name\":\"orderId\",\"value\":[\"uint64\",\"7\"]},{\"name\":\"owner\",\"value\":[\"string\",\"maker\"]},{\"name\":\"handle\",\"value\":[\"uint64\",\"1594867290500000\"]},{\"name\":\"price\",\"value\":[\"string\",\"5199.50 USD\"]},{\"name\":\"cost\",\"value\":[\"string\",\"0.00 USD\"]},{\"name\":\"remaining\",\"value\":[\"string\",\"1.00000000 BTC\"]},{\"name\":\"size\",\"value\":[\"string\",\"1.00000000 BTC\"]},{\"name\":\"created\",\"value\":[\"time_point\",\"2020-07-16T10:41:31.000\"]}]]";
//
//        QueryIt queryIt = this.gson.fromJson(jsonContent, QueryIt.class);
//
//        assertNotNull(queryIt);
//        assertEquals(expectedValue, queryIt.getValue());
//    }
}
