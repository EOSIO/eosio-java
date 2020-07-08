package one.block.eosiojava.models;

import org.junit.Test;
import static junit.framework.TestCase.*;

public class AbiEosSerializationObjectTest {
    AbiEosSerializationObject serializationObject;

    @Test
    public void testCreateAbiEosSerializationObjectWithAllFields() {
        String contractName = "contractName";
        String actionName = "actionName";
        String type = "type";
        String abi = "abi";
        this.serializationObject = new AbiEosSerializationObject(contractName, actionName, type, abi);

        assertEquals(this.serializationObject.getContract(), contractName);
        assertEquals(this.serializationObject.getName(), actionName);
        assertEquals(this.serializationObject.getType(), type);
        assertEquals(this.serializationObject.getAbi(), abi);
    }

    @Test
    public void testCreateAbiEosSerializationObjectWithoutContractOrActionNameShouldDefaultToNull() {
        String type = "type";
        String abi = "abi";
        this.serializationObject = new AbiEosSerializationObject(type, abi);

        assertNull(this.serializationObject.getContract());
        assertNull(this.serializationObject.getName());
        assertEquals(this.serializationObject.getType(), type);
        assertEquals(this.serializationObject.getAbi(), abi);
    }
}
