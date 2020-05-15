package one.block.eosiojava.models;

import java.util.ArrayList;
import one.block.eosiojava.models.signatureProvider.BinaryAbi;
import one.block.eosiojava.models.signatureProvider.EosioTransactionSignatureRequest;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;
import static junit.framework.TestCase.*;

public class EosioTransactionSignatureRequestTest {
    EosioTransactionSignatureRequest request;

    public void setup() {
        this.setup(null);
    }

    public void setup(String contextFreeData) {
        if (contextFreeData == null) {
            request = new EosioTransactionSignatureRequest("", new ArrayList<String>(), "", new ArrayList<BinaryAbi>(), false);
        } else {
            request = new EosioTransactionSignatureRequest("", new ArrayList<String>(), "", new ArrayList<BinaryAbi>(), false, contextFreeData);
        }
    }

    @Test
    public void testCreateWithoutContextFreeDataSetsTo32Zeros() {
        this.setup();

        assertEquals(request.getSerializedContextFreeData(), Hex.toHexString(new byte[32]));
    }

    @Test
    public void testCreateWithContextFreeDataSetsParameter() {
        String serializedContextFreeData = "6595140530fcbd94469196e5e6d73af65693910df8fcf5d3088c3616bff5ee9f";
        this.setup(serializedContextFreeData);

        assertEquals(request.getSerializedContextFreeData(), serializedContextFreeData);
    }
}
