package one.block.eosiojava.models.queryit;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

public class AnyVarSerializer implements JsonSerializer<AnyVar> {

    @Override
    public JsonElement serialize(AnyVar src, Type typeOfSrc, JsonSerializationContext context) {
        if (src.isEmpty()) {
            return new JsonArray();
        } else {
            JsonObject object = new JsonObject();
            if (src.hasValue()) { // Primitive
                return new JsonPrimitive(src.getValue().toString());
            }
            for (Field field : src.getFields()) {
                if (field.hasPrimitiveValue()) { // Primitive
                    Object obj = field.getAnyVarValue();
                    object.addProperty(field.getName(), obj.toString());
                } else if (field.hasType(QueryItConstants.ANY_OBJECT_TYPE))  { // any_object
                    JsonElement element = serialize(field.getValue(), typeOfSrc, context);
                    object.add(field.getName(), element);
                } else if (field.hasType(QueryItConstants.ANY_ARRAY_TYPE)) { // any_array
                    JsonArray array = new JsonArray();
                    for (AnyVar anyVar : field.getValue().getAnyVars()) {
                        JsonElement element = serialize(anyVar, typeOfSrc, context);
                        array.add(element);
                    }
                    object.add(field.getName(), array);
                }
            }
            return object;
        }
    }
}
