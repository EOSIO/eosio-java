package one.block.eosiojava.models.queryit;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;

public class FieldDeserializer implements JsonDeserializer<Field> {
    @Override
    public Field deserialize(JsonElement json, Type typeOfT,
            JsonDeserializationContext context) throws JsonParseException {
        return new Field();
    }

}
