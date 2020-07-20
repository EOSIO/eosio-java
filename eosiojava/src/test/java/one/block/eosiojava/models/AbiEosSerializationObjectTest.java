package one.block.eosiojava.models;

import org.junit.Test;
import static junit.framework.TestCase.*;

public class AbiEosSerializationObjectTest {
    private static final String CONTRACT_NAME = "contractName";
    private static final String ACTION_NAME = "actionName";
    private static final String TYPE = "type";
    private static final String ABI = "abi";

    private AbiEosSerializationObject serializationObject;

    @Test
    public void testCreateAbiEosSerializationObjectWithAllFields() {
        this.serializationObject = new AbiEosSerializationObject(CONTRACT_NAME, ACTION_NAME, TYPE, ABI);

        assertEquals(this.serializationObject.getContract(), CONTRACT_NAME);
        assertEquals(this.serializationObject.getName(), ACTION_NAME);
        assertEquals(this.serializationObject.getType(), TYPE);
        assertEquals(this.serializationObject.getAbi(), ABI);
    }

    @Test
    public void testCreateAbiEosSerializationObjectWithoutTypeShouldDefaultToNull() {
        this.serializationObject = new AbiEosSerializationObject(CONTRACT_NAME, ACTION_NAME, ABI);

        assertEquals(this.serializationObject.getContract(), CONTRACT_NAME);
        assertEquals(this.serializationObject.getName(), ACTION_NAME);
        assertNull(this.serializationObject.getType());
        assertEquals(this.serializationObject.getAbi(), ABI);
    }

    @Test
    public void testCreateAbiEosSerializationObjectWithoutContractOrActionNameShouldDefaultToNull() {
        this.serializationObject = new AbiEosSerializationObject(TYPE, ABI);

        assertNull(this.serializationObject.getContract());
        assertNull(this.serializationObject.getName());
        assertEquals(this.serializationObject.getType(), TYPE);
        assertEquals(this.serializationObject.getAbi(), ABI);
    }
}
