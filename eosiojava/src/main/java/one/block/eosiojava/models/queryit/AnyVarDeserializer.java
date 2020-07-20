package one.block.eosiojava.models.queryit;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.math.BigInteger;

public class AnyVarDeserializer implements JsonDeserializer<AnyVar> {
    @Override
    public AnyVar deserialize(JsonElement json, Type typeOfT,
            JsonDeserializationContext context) throws JsonParseException {

        AnyVar anyVar = new AnyVar();
        if (json.isJsonPrimitive()) {
            anyVar.setValue(json.getAsJsonPrimitive().getAsString());
        } else if (!json.isJsonArray() || ((JsonArray)json).size() == 0) { // null_t
            return anyVar;
        } else {
            JsonArray jsonAsArray = ((JsonArray) json);
            String type = jsonAsArray.get(0).getAsString();
            anyVar.setType(type);

            if (type.equals(QueryItConstants.INT32_TYPE)
                    || type.equals(QueryItConstants.INT16_TYPE) || type.equals(QueryItConstants.UINT16_TYPE)
                    || type.equals(QueryItConstants.INT8_TYPE) || type.equals(QueryItConstants.UINT8_TYPE)) {
                anyVar.setValue(Integer.parseInt(jsonAsArray.get(1).getAsString()));
            } else if (type.equals(QueryItConstants.UINT64_TYPE)) {
                anyVar.setValue(new BigInteger(jsonAsArray.get(1).getAsString()));
            } else if (type.equals(QueryItConstants.FLOAT64_TYPE)) {
                anyVar.setValue(Float.parseFloat(jsonAsArray.get(1).getAsString()));
            } else if (type.equals(QueryItConstants.INT64_TYPE) || type.equals(QueryItConstants.UINT32_TYPE)) {
                anyVar.setValue(Long.parseLong(jsonAsArray.get(1).getAsString()));
//            } else if (type.equals(QueryItConstants.TIME_POINT_TYPE)) { // Serialization doesn't work exactly due to dropped milliseconds if .000
//                String timePointAsString = jsonAsArray.get(1).getAsString();
//                if (timePointAsString.charAt(timePointAsString.length() - 1) != QueryItConstants.OPTIONAL_ZONE_CHAR) {
//                    timePointAsString += QueryItConstants.OPTIONAL_ZONE_CHAR;
//                }
//                anyVar.setValue(Instant.parse(timePointAsString));
            } else if (type.equals(QueryItConstants.ANY_OBJECT_TYPE)) {
                JsonArray array = jsonAsArray.get(1).getAsJsonArray();
                for(JsonElement subJson : array) {
                    Field field = context.deserialize(subJson, Field.class);
                    anyVar.addField(field);
                }
            } else if (type.equals(QueryItConstants.ANY_ARRAY_TYPE)) {
                JsonArray array = jsonAsArray.get(1).getAsJsonArray();
                for(JsonElement subJson : array) {
                    AnyVar subAnyVar = deserialize(subJson, typeOfT, context);
                    anyVar.addAnyVar(subAnyVar);
                }
            } else { // string, checksum256, symbol, symbol_code, asset, bytes
                anyVar.setValue(jsonAsArray.get(1).getAsString());
            }
        }
        return anyVar;
    }
}
