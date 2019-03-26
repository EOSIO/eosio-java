

package one.block.eosiojava.interfaces;

import one.block.eosiojava.error.serializationprovider.DeserializeAbiError;
import one.block.eosiojava.error.serializationprovider.DeserializeError;
import one.block.eosiojava.error.serializationprovider.DeserializeTransactionError;
import one.block.eosiojava.error.serializationprovider.SerializeAbiError;
import one.block.eosiojava.error.serializationprovider.SerializeError;
import one.block.eosiojava.error.serializationprovider.SerializeTransactionError;
import one.block.eosiojava.models.AbiEosSerializationObject;

public interface ISerializationProvider {

    public void deserialize(AbiEosSerializationObject serializationObject) throws DeserializeError;
    public void serialize(AbiEosSerializationObject serializationObject) throws SerializeError;

    public String deserializeTransaction(String hex) throws DeserializeTransactionError;
    public String serializeTransaction(String json) throws SerializeTransactionError;

    public String deserializeAbi(String hex) throws DeserializeAbiError;
    public String serializeAbi(String json) throws SerializeAbiError;
}
