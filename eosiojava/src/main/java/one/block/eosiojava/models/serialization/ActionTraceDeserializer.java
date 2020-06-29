package one.block.eosiojava.models.serialization;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import one.block.eosiojava.error.ErrorConstants;
import one.block.eosiojava.error.abiProvider.GetAbiError;
import one.block.eosiojava.error.serializationProvider.DeserializeError;
import one.block.eosiojava.error.session.TransactionCreateSignatureRequestAbiError;
import one.block.eosiojava.error.session.TransactionCreateSignatureRequestError;
import one.block.eosiojava.error.session.TransactionCreateSignatureRequestSerializationError;
import one.block.eosiojava.interfaces.IABIProvider;
import one.block.eosiojava.interfaces.ISerializationProvider;
import one.block.eosiojava.models.EOSIOName;
import one.block.eosiojava.models.rpcProvider.Action;
import one.block.eosiojava.models.rpcProvider.response.ActionTrace;

public class ActionTraceDeserializer implements JsonDeserializer<ActionTrace> {
    private ISerializationProvider serializationProvider;

    @Override
    public ActionTrace deserialize(JsonElement json, Type typeOfT,
            JsonDeserializationContext context) throws JsonParseException {
        ActionTrace actionTrace = new Gson().fromJson(json.getAsJsonObject(), typeOfT);
//        String type = "test"; // How do we get this?
//        try {
//            AbiEosSerializationObject test = new AbiEosSerializationObject("cfhello", "complex", "studentType", "");
//            serializationProvider.deserialize(test);
//            actionTrace.setDeserializedReturnValue(test.getJson());
//        } catch (DeserializeError deserializeError) {
//            deserializeError.printStackTrace();
//        }

        return actionTrace;
    }

    private AbiEosSerializationObject deserializeActionTraceReturnValue(Action action, String chainId, IABIProvider abiProvider)
            throws TransactionCreateSignatureRequestError {
        String actionAbiJSON;
        try {
            actionAbiJSON = abiProvider
                    .getAbi(chainId, new EOSIOName(action.getAccount()));
        } catch (GetAbiError getAbiError) {
            throw new TransactionCreateSignatureRequestAbiError(
                    String.format(ErrorConstants.TRANSACTION_PROCESSOR_GET_ABI_ERROR,
                            action.getAccount()), getAbiError);
        }

        AbiEosSerializationObject actionAbiEosSerializationObject = new AbiEosSerializationObject(
                action.getAccount(), action.getName(),
                null, actionAbiJSON);
        actionAbiEosSerializationObject.setHex("d2040000");

        // !!! At this step, the data field of the action is still in JSON format.
        //actionAbiEosSerializationObject.setJson(action.getData());

        try {
            this.serializationProvider.deserialize(actionAbiEosSerializationObject);
            if (actionAbiEosSerializationObject.getHex().isEmpty()) {
                throw new TransactionCreateSignatureRequestSerializationError(
                        ErrorConstants.TRANSACTION_PROCESSOR_SERIALIZE_ACTION_WORKED_BUT_EMPTY_RESULT);
            }
        } catch (TransactionCreateSignatureRequestSerializationError | DeserializeError serializeError) {
            throw new TransactionCreateSignatureRequestSerializationError(
                    String.format(ErrorConstants.TRANSACTION_PROCESSOR_SERIALIZE_ACTION_ERROR,
                            action.getAccount()), serializeError);
        }

        return actionAbiEosSerializationObject;
    }
}
