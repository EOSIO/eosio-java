package one.block.eosiojava.models.queryit;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.time.Instant;

public class QueryItDeserializer implements JsonDeserializer<QueryIt> {
    @Override
    public QueryIt deserialize(JsonElement json, Type typeOfT,
            JsonDeserializationContext context) throws JsonParseException {

        QueryIt queryIt = new QueryIt();
        if (!json.isJsonArray() || ((JsonArray)json).size() == 0) { // null_t
            return queryIt; // Seems like this might actually be wrong for size() == 0, return []
        } else {
            JsonArray jsonAsArray = ((JsonArray) json);
            String type = jsonAsArray.get(0).getAsString();

            if (type.equals(QueryItConstants.INT32_TYPE)
                    || type.equals(QueryItConstants.INT16_TYPE) || type.equals(QueryItConstants.UINT16_TYPE)
                    || type.equals(QueryItConstants.INT8_TYPE) || type.equals(QueryItConstants.UINT8_TYPE)) {
                queryIt.setValue(Integer.parseInt(jsonAsArray.get(1).getAsString()));
            } else if (type.equals(QueryItConstants.UINT64_TYPE)) {
                queryIt.setValue(new BigInteger(jsonAsArray.get(1).getAsString()));
            } else if (type.equals(QueryItConstants.FLOAT64_TYPE)) {
                queryIt.setValue(Float.parseFloat(jsonAsArray.get(1).getAsString()));
            } else if (type.equals(QueryItConstants.INT64_TYPE) || type.equals(QueryItConstants.UINT32_TYPE)) {
                queryIt.setValue(Long.parseLong(jsonAsArray.get(1).getAsString()));
            } else if (type.equals(QueryItConstants.TIME_POINT_TYPE)) {
                String timePointAsString = jsonAsArray.get(1).getAsString();
                if (timePointAsString.charAt(timePointAsString.length() - 1) != QueryItConstants.OPTIONAL_ZONE_CHAR) {
                    timePointAsString += QueryItConstants.OPTIONAL_ZONE_CHAR;
                }
                queryIt.setValue(Instant.parse(timePointAsString));
            } else if (type.equals(QueryItConstants.ANY_OBJECT_TYPE)) {
                JsonArray array = jsonAsArray.get(1).getAsJsonArray();
                for(JsonElement subJson : array) {
                    Field field = context.deserialize(subJson, Field.class);
                    queryIt.addField(field);
                }
            } else if (type.equals(QueryItConstants.ANY_ARRAY_TYPE)) {
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
