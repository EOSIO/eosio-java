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
        assertTrue(contextFreeData.getData().isEmpty());
        assertEquals(contextFreeData.getBytes().length, 0);
        assertEquals(contextFreeData.getHexed(), "");
        assertEquals(contextFreeData.getPacked(), "");
    }

    @Test
    public void testGetDataReturnsRawContextFreeData() {
        setup(this.defaultContextFreeData());

        assertEquals(contextFreeData.getData(), this.defaultContextFreeData());
    }

    @Test
    public void testGetBytesReturnsFormattedBytes() {
        setup(this.defaultContextFreeData());

        assertEquals(contextFreeData.getBytes().length, 1281);
    }

    @Test
    public void testGetHexContextFreeDataReturnsCorrectlyFormattedHexContextFreeData() {
        setup(this.defaultContextFreeData());

        assertEquals(contextFreeData.getHexed(), this.hexedContextFreeData());
    }

    @Test
    public void testGetSerializedContextFreeDataReturnsCorrectlyFormattedSerializedContextFreeData() {
        setup(this.defaultContextFreeData());

        assertEquals(contextFreeData.getPacked(), this.packedContextFreeData());
    }

    private List<String> defaultContextFreeData() {
        String contextFreeData1 = "test";
        String contextFreeData2 = "{\"some\": \"jsonData\"}";
        String contextFreeData3 = "!@#$%^&*()_+";
        String contextFreeData4 = "This is some long context free data input. It can have whatever data you want in it. It will be copied multiple times to increase length. This is some long context free data input. It can have whatever data you want in it. It will be copied multiple times to increase length. This is some long context free data input. It can have whatever data you want in it. It will be copied multiple times to increase length.This is some long context free data input. It can have whatever data you want in it. It will be copied multiple times to increase length. This is some long context free data input. It can have whatever data you want in it. It will be copied multiple times to increase length. This is some long context free data input. It can have whatever data you want in it. It will be copied multiple times to increase length.This is some long context free data input. It can have whatever data you want in it. It will be copied multiple times to increase length. This is some long context free data input. It can have whatever data you want in it. It will be copied multiple times to increase length. This is some long context free data input. It can have whatever data you want in it. It will be copied multiple times to increase length.";

        ArrayList<String> contextFreeData = new ArrayList<String>();
        contextFreeData.add(contextFreeData1);
        contextFreeData.add(contextFreeData2);
        contextFreeData.add(contextFreeData3);
        contextFreeData.add(contextFreeData4);

        return contextFreeData;
    }

    private String hexedContextFreeData() {
        return "040474657374147B22736F6D65223A20226A736F6E44617461227D0C21402324255E262A28295F2BD7095468697320697320736F6D65206C6F6E6720636F6E746578742066726565206461746120696E7075742E2049742063616E2068617665207768617465766572206461746120796F752077616E7420696E2069742E2049742077696C6C20626520636F70696564206D756C7469706C652074696D657320746F20696E637265617365206C656E6774682E205468697320697320736F6D65206C6F6E6720636F6E746578742066726565206461746120696E7075742E2049742063616E2068617665207768617465766572206461746120796F752077616E7420696E2069742E2049742077696C6C20626520636F70696564206D756C7469706C652074696D657320746F20696E637265617365206C656E6774682E205468697320697320736F6D65206C6F6E6720636F6E746578742066726565206461746120696E7075742E2049742063616E2068617665207768617465766572206461746120796F752077616E7420696E2069742E2049742077696C6C20626520636F70696564206D756C7469706C652074696D657320746F20696E637265617365206C656E6774682E5468697320697320736F6D65206C6F6E6720636F6E746578742066726565206461746120696E7075742E2049742063616E2068617665207768617465766572206461746120796F752077616E7420696E2069742E2049742077696C6C20626520636F70696564206D756C7469706C652074696D657320746F20696E637265617365206C656E6774682E205468697320697320736F6D65206C6F6E6720636F6E746578742066726565206461746120696E7075742E2049742063616E2068617665207768617465766572206461746120796F752077616E7420696E2069742E2049742077696C6C20626520636F70696564206D756C7469706C652074696D657320746F20696E637265617365206C656E6774682E205468697320697320736F6D65206C6F6E6720636F6E746578742066726565206461746120696E7075742E2049742063616E2068617665207768617465766572206461746120796F752077616E7420696E2069742E2049742077696C6C20626520636F70696564206D756C7469706C652074696D657320746F20696E637265617365206C656E6774682E5468697320697320736F6D65206C6F6E6720636F6E746578742066726565206461746120696E7075742E2049742063616E2068617665207768617465766572206461746120796F752077616E7420696E2069742E2049742077696C6C20626520636F70696564206D756C7469706C652074696D657320746F20696E637265617365206C656E6774682E205468697320697320736F6D65206C6F6E6720636F6E746578742066726565206461746120696E7075742E2049742063616E2068617665207768617465766572206461746120796F752077616E7420696E2069742E2049742077696C6C20626520636F70696564206D756C7469706C652074696D657320746F20696E637265617365206C656E6774682E205468697320697320736F6D65206C6F6E6720636F6E746578742066726565206461746120696E7075742E2049742063616E2068617665207768617465766572206461746120796F752077616E7420696E2069742E2049742077696C6C20626520636F70696564206D756C7469706C652074696D657320746F20696E637265617365206C656E6774682E";
    }

    private String packedContextFreeData() {
        return "6595140530fcbd94469196e5e6d73af65693910df8fcf5d3088c3616bff5ee9f";
    }
}
