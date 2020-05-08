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

public class TransactionTest {
    Transaction transaction;

    @Before
    public void setup() {
        transaction = new Transaction("", BigInteger.ZERO, BigInteger.ZERO,
                BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, new ArrayList<Action>(), new ArrayList<Action>(),
                new ArrayList<String>(), new ArrayList<String>());
    }

    @Test
    public void testCreateTransactionWithEmptyContextFreeData() {
        assertNotNull(transaction.contextFreeData);
        assertTrue(transaction.getContextFreeData().isEmpty());
        assertTrue(transaction.getHexContextFreeData().isEmpty());
        assertEquals(transaction.getPackedContextFreeData(), "");
    }

    @Test
    public void testGetContextFreeDataWithNullContextFreeDataReturnsEmptyString() {
        transaction.contextFreeData = null;

        assertEquals(transaction.getContextFreeData(), new ArrayList<String>());
    }

    @Test
    public void testGetHexContextFreeDataWithNullContextFreeDataReturnsEmptyString() {
        transaction.contextFreeData = null;

        assertEquals(transaction.getHexContextFreeData(), new ArrayList<String>());
    }

    @Test
    public void testGetPackedContextFreeDataWithNullContextFreeDataReturnsEmptyString() {
        transaction.contextFreeData = null;

        assertEquals(transaction.getPackedContextFreeData(), "");
    }

    @Test
    public void testGetContextFreeDataWithNonNullContextFreeDataCallsMethod() {
        transaction.contextFreeData = mock(ContextFreeData.class);

        transaction.getContextFreeData();

        verify(transaction.contextFreeData).getContextFreeData();
    }

    @Test
    public void testGetHexContextFreeDataWithNonNullContextFreeDataCallsMethod() {
        transaction.contextFreeData = mock(ContextFreeData.class);

        transaction.getHexContextFreeData();

        verify(transaction.contextFreeData).getHexContextFreeData();
    }

    @Test
    public void testGetPackedContextFreeDataWithNonNullContextFreeDataCallsMethod() {
        transaction.contextFreeData = mock(ContextFreeData.class);

        transaction.getPackedContextFreeData();

        verify(transaction.contextFreeData).getPackedContextFreeData();
    }
}
