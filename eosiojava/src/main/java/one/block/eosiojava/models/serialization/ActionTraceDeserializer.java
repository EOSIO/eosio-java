package one.block.eosiojava.models.serialization;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import one.block.eosiojava.models.rpcProvider.response.ActionTrace;

public class ActionTraceDeserializer implements JsonDeserializer<ActionTrace> {
    @Override
    public ActionTrace deserialize(JsonElement json, Type typeOfT,
            JsonDeserializationContext context) throws JsonParseException {
        ActionTrace actionTrace = new Gson().fromJson(json.getAsJsonObject(), typeOfT);
        actionTrace.setDeserializedReturnValue();
        return actionTrace;
    }
}
