package one.block.eosiojava.models.queryit;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;

public class QueryItDeserializer implements JsonDeserializer<QueryIt> {
    private static final String ANY_OBJECT_TYPE = "any_object";
    private static final String ANY_ARRAY_TYPE = "any_array";

    private String datePattern;

    public QueryItDeserializer(String datePattern) {
        this.datePattern = datePattern;
    }

    // Remember to handle null_t, which likely would result in exception below
//    @Override
//    public QueryIt deserialize(JsonElement json, Type typeOfT,
//            JsonDeserializationContext context) throws JsonParseException {
//
//        QueryIt queryIt = null;
//        // null_t?
//        if (!json.isJsonArray() || ((JsonArray)json).size() == 0) {
//            return null;
//        } else if (json.isJsonPrimitive()) {
//            JsonPrimitive primitive = (JsonPrimitive)json;
//            if (primitive.isString()) {
//                queryIt.value = json.getAsString();
//            } else if (primitive.isNumber()) {
//                queryIt.value = json.getAsNumber();
//            } else if (primitive.isBoolean()) {
//                queryIt.value = json.getAsBoolean();
//            }
//        } else if (json.isJsonObject()) {
//
//        } else {
//            JsonArray jsonAsArray = ((JsonArray) json);
//            JsonElement element = jsonAsArray.get(0);
//            if (element.isJsonObject()) {
//
//            }
//            String type = jsonAsArray.get(0).getAsString();
//
//            if(type.equals(ANY_OBJECT_TYPE)) {
//                JsonArray value = (JsonArray)jsonAsArray.get(1);
//                QueryIt subType = deserialize(value, typeOfT, context);
//                String test = "";
//            } else if (type.equals(ANY_ARRAY_TYPE)) {
//                JsonArray value = (JsonArray)jsonAsArray.get(1);
//            } else {
//
//            }
//        }
//
////        ActionTrace actionTrace = new Gson().fromJson(json.getAsJsonObject(), typeOfT);
////        actionTrace.setDeserializedReturnValue();
//        return queryIt;
//    }

    @Override
    public QueryIt deserialize(JsonElement json, Type typeOfT,
            JsonDeserializationContext context) throws JsonParseException {

        QueryIt queryIt = new QueryIt();
        if (!json.isJsonArray() || ((JsonArray)json).size() == 0) { // null_t
            return queryIt;
        } else {
            JsonArray jsonAsArray = ((JsonArray) json);
            String type = jsonAsArray.get(0).getAsString();

            if (type.equals("int32")
                    || type.equals("int16")
                    || type.equals("uint16")
                    || type.equals("int8")
                    || type.equals("uint8")) {
                queryIt.value = Integer.parseInt(jsonAsArray.get(1).getAsString());
            } else if (type.equals("uint64")) {
                queryIt.value = new BigInteger(jsonAsArray.get(1).getAsString());
            } else if (type.equals("float64")) {
                queryIt.value = Float.parseFloat(jsonAsArray.get(1).getAsString());
            } else if (type.equals("int64") || type.equals("uint32")) {
                queryIt.value = Long.parseLong(jsonAsArray.get(1).getAsString());
            } else if (type.equals("time_point")) {
                String timePointAsString = jsonAsArray.get(1).getAsString();
                if (timePointAsString.charAt(timePointAsString.length() - 1) != 'Z') {
                    timePointAsString += 'Z';
                }
                queryIt.value = Instant.parse(timePointAsString);
            } else if (type.equals("any_object")) {
                JsonArray array = jsonAsArray.get(1).getAsJsonArray();
                for(JsonElement subJson : array) {
                    // { "name": "something", "value": ["string", "test"] }
                    QueryIt subQueryIt = deserialize(subJson, typeOfT, context);
                    queryIt.addQueryIt(subQueryIt);
                }
                queryIt.value = "test";
            } else if (type.equals("any_array")) {

            } else { // string, checksum256, symbol, symbol_code, asset, bytes
                queryIt.value = jsonAsArray.get(1).getAsString();
            }
        }
        return queryIt;
    }
}
