package one.block.eosiojava.models.queryit;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.time.Instant;

public class AnyVarSerializer implements JsonSerializer<AnyVar> {

    @Override
    public JsonElement serialize(AnyVar src, Type typeOfSrc, JsonSerializationContext context) {
        if (src.isEmpty()) {
            return new JsonArray();
        } else {
            JsonObject object = new JsonObject();
            for (Field field : src.getFields()) {
                if (field.getValue().getType().equals(QueryItConstants.ANY_OBJECT_TYPE)) {
                    JsonElement jsonElement = serialize(field.getValue(), typeOfSrc, context);
                    String test = "";
                    //object.add(field.getValue());
                } else if (field.getValue().isEmpty()) {
                    return new JsonArray();
                } else {
                    try {
                        object.addProperty(field.getName(), field.getValue().getValue().toString());
                    } catch(NullPointerException exception) {
                        String test = "";
                    }

                }
            }

            for (AnyVar anyVar : src.getAnyVars()) {
                JsonElement array = serialize(anyVar, typeOfSrc, context);

                String test = "";
            }
            return object;
        }
//        else {
//            JsonArray array = new JsonArray();
//            String type = src.getType();
//            array.add(type);
//
//            for(AnyVar anyVar : src.getAnyVars()) {
//                JsonElement object = serialize(anyVar, typeOfSrc, context);
//                String test = "";
//            }
//
////            for(Field field : src.getFields()) {
////                JsonObject object = serialize(field, )
////            }
//
//            array.add(src.getValue().toString());
//
//            return array;
//        }
    }

    // Don't think this matters, even though it'd be nice.. doesn't match output JSON from EOSJS
//            switch(type) {
//                case QueryItConstants.INT32_TYPE:
//                case QueryItConstants.INT16_TYPE:
//                case QueryItConstants.UINT16_TYPE:
//                case QueryItConstants.INT8_TYPE:
//                case QueryItConstants.UINT8_TYPE:
//                    array.add((Integer)src.getValue());
//                    break;
//                case QueryItConstants.UINT64_TYPE:
//                    array.add((BigInteger)src.getValue());
//                    break;
//                case QueryItConstants.FLOAT64_TYPE:
//                    array.add((Float)src.getValue());
//                    break;
//                case QueryItConstants.UINT32_TYPE:
//                case QueryItConstants.INT64_TYPE:
//                    array.add((Long)src.getValue());
//                    break;
//                default: // time_point, string, checksum256, symbol, symbol_code, asset, bytes
//                    array.add((String)src.getValue());
//                    break;
//            }
}
