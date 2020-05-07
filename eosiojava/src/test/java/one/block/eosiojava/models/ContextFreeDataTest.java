package one.block.eosiojava.models;

import java.math.BigInteger;
import java.util.ArrayList;
import one.block.eosiojava.models.rpcProvider.Action;
import one.block.eosiojava.models.rpcProvider.ContextFreeData;
import one.block.eosiojava.models.rpcProvider.Transaction;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.*;
import static org.mockito.Mockito.*;

public class ContextFreeDataTest {
    ContextFreeData contextFreeData;

    @Before
    public void setup() {
        contextFreeData = new ContextFreeData(new ArrayList<String>());
    }

    @Test
    public void Something() {

    }
}
