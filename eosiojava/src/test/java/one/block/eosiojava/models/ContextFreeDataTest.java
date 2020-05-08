package one.block.eosiojava.models;

import java.util.ArrayList;
import java.util.List;
import one.block.eosiojava.models.rpcProvider.ContextFreeData;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.*;

public class ContextFreeDataTest {
    ContextFreeData contextFreeData;

    public void setup(List<String> cfd) {
        contextFreeData = new ContextFreeData(cfd);
    }

    @Before
    public void setup() {
        this.setup(new ArrayList<String>());
    }

    @Test
    public void testCreateContextFreeDataWithEmptyData() {
        assertTrue(contextFreeData.getContextFreeData().isEmpty());
        assertTrue(contextFreeData.getHexContextFreeData().isEmpty());
        assertEquals(contextFreeData.getPackedContextFreeData(), "");
    }

    @Test
    public void testGetContextFreeDataReturnsRawContextFreeData() {
        setup(this.defaultContextFreeData());

        assertEquals(contextFreeData.getContextFreeData(), this.defaultContextFreeData());
    }

    @Test
    public void testGetHexContextFreeDataReturnsCorrectlyFormattedHexContextFreeData() {
        setup(this.defaultContextFreeData());

        assertEquals(contextFreeData.getHexContextFreeData(), this.hexedContextFreeData());
    }

    @Test
    public void testGetPackedContextFreeDataReturnsCorrectlyFormattedPackedContextFreeData() {
        setup(this.defaultContextFreeData());

        assertEquals(contextFreeData.getPackedContextFreeData(), this.packedContextFreeData());
    }

    private List<String> defaultContextFreeData() {
        String contextFreeData1 = "test";
        String contextFreeData2 = "{\"some\": \"jsonData\"}";
        String contextFreeData3 = "!@#$%^&*()_+";
        String contextFreeData4 = "This is some long context free data input. It can have whatever data you want in it. It will be copied multiple times to increase length. This is some long context free data input. It can have whatever data you want in it. It will be copied multiple times to increase length. This is some long context free data input. It can have whatever data you want in it. It will be copied multiple times to increase length.";

        ArrayList<String> contextFreeData = new ArrayList<String>();
        contextFreeData.add(contextFreeData1);
        contextFreeData.add(contextFreeData2);
        contextFreeData.add(contextFreeData3);
        contextFreeData.add(contextFreeData4);

        return contextFreeData;
    }

    private List<String> hexedContextFreeData() {
        String hexedContextFreeData1 = "74657374";
        String hexedContextFreeData2 = "7b22736f6d65223a20226a736f6e44617461227d";
        String hexedContextFreeData3 = "21402324255e262a28295f2b";
        String hexedContextFreeData4 = "5468697320697320736f6d65206c6f6e6720636f6e746578742066726565206461746120696e7075742e2049742063616e2068617665207768617465766572206461746120796f752077616e7420696e2069742e2049742077696c6c20626520636f70696564206d756c7469706c652074696d657320746f20696e637265617365206c656e6774682e205468697320697320736f6d65206c6f6e6720636f6e746578742066726565206461746120696e7075742e2049742063616e2068617665207768617465766572206461746120796f752077616e7420696e2069742e2049742077696c6c20626520636f70696564206d756c7469706c652074696d657320746f20696e637265617365206c656e6774682e205468697320697320736f6d65206c6f6e6720636f6e746578742066726565206461746120696e7075742e2049742063616e2068617665207768617465766572206461746120796f752077616e7420696e2069742e2049742077696c6c20626520636f70696564206d756c7469706c652074696d657320746f20696e637265617365206c656e6774682e";

        ArrayList<String> hexedContextFreeData = new ArrayList<String>();
        hexedContextFreeData.add(hexedContextFreeData1);
        hexedContextFreeData.add(hexedContextFreeData2);
        hexedContextFreeData.add(hexedContextFreeData3);
        hexedContextFreeData.add(hexedContextFreeData4);

        return hexedContextFreeData;
    }

    private String packedContextFreeData() {
        return "040474657374147b22736f6d65223a20226a736f6e44617461227d0C21402324255e262a28295f2b19D5468697320697320736f6d65206c6f6e6720636f6e746578742066726565206461746120696e7075742e2049742063616e2068617665207768617465766572206461746120796f752077616e7420696e2069742e2049742077696c6c20626520636f70696564206d756c7469706c652074696d657320746f20696e637265617365206c656e6774682e205468697320697320736f6d65206c6f6e6720636f6e746578742066726565206461746120696e7075742e2049742063616e2068617665207768617465766572206461746120796f752077616e7420696e2069742e2049742077696c6c20626520636f70696564206d756c7469706c652074696d657320746f20696e637265617365206c656e6774682e205468697320697320736f6d65206c6f6e6720636f6e746578742066726565206461746120696e7075742e2049742063616e2068617665207768617465766572206461746120796f752077616e7420696e2069742e2049742077696c6c20626520636f70696564206d756c7469706c652074696d657320746f20696e637265617365206c656e6774682e";
    }
}
