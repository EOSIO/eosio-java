package one.block.eosiojava.models.queryit;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.time.Instant;

public class QueryItDeserializer implements JsonDeserializer<QueryIt> {
    private static final String ANY_OBJECT_TYPE = "any_object";
    private static final String ANY_ARRAY_TYPE = "any_array";

    @Override
    public QueryIt deserialize(JsonElement json, Type typeOfT,
            JsonDeserializationContext context) throws JsonParseException {

        QueryIt queryIt = new QueryIt();
        if (!json.isJsonArray() || ((JsonArray)json).size() == 0) { // null_t
            return queryIt; // Seems like this might actually be wrong for size() == 0, return []
        } else {
            JsonArray jsonAsArray = ((JsonArray) json);
            String type = jsonAsArray.get(0).getAsString();

            if (type.equals("int32")
                    || type.equals("int16")
                    || type.equals("uint16")
                    || type.equals("int8")
                    || type.equals("uint8")) {
                queryIt.setValue(Integer.parseInt(jsonAsArray.get(1).getAsString()));
            } else if (type.equals("uint64")) {
                queryIt.setValue(new BigInteger(jsonAsArray.get(1).getAsString()));
            } else if (type.equals("float64")) {
                queryIt.setValue(Float.parseFloat(jsonAsArray.get(1).getAsString()));
            } else if (type.equals("int64") || type.equals("uint32")) {
                queryIt.setValue(Long.parseLong(jsonAsArray.get(1).getAsString()));
            } else if (type.equals("time_point")) {
                String timePointAsString = jsonAsArray.get(1).getAsString();
                if (timePointAsString.charAt(timePointAsString.length() - 1) != 'Z') {
                    timePointAsString += 'Z';
                }
                queryIt.setValue(Instant.parse(timePointAsString));
            } else if (type.equals("any_object")) {
                JsonArray array = jsonAsArray.get(1).getAsJsonArray();
                for(JsonElement subJson : array) {
                    JsonObject subJsonAsObject = subJson.getAsJsonObject();
                    String name = subJsonAsObject.get("name").getAsString();
                    JsonArray subJsonArray = subJsonAsObject.getAsJsonArray("value");
                    QueryIt subQueryIt = deserialize(subJsonArray, typeOfT, context);
                    subQueryIt.setName(name);
                    queryIt.addQueryIt(subQueryIt);
                }
                //queryIt.value = "test";
            } else if (type.equals("any_array")) {
                JsonArray array = jsonAsArray.get(1).getAsJsonArray();
                for(JsonElement subJson : array) {
                    QueryIt subQueryIt = deserialize(subJson, typeOfT, context);
                    queryIt.addQueryIt(subQueryIt);
                }
            } else { // string, checksum256, symbol, symbol_code, asset, bytes
                queryIt.setValue(jsonAsArray.get(1).getAsString());
            }
        }
        return queryIt;
    }
}
