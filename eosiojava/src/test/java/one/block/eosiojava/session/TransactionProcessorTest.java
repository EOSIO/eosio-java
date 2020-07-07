package one.block.eosiojava.session;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import one.block.eosiojava.error.abiProvider.GetAbiError;
import one.block.eosiojava.error.rpcProvider.GetBlockRpcError;
import one.block.eosiojava.error.rpcProvider.GetInfoRpcError;
import one.block.eosiojava.error.rpcProvider.GetRequiredKeysRpcError;
import one.block.eosiojava.error.rpcProvider.PushTransactionRpcError;
import one.block.eosiojava.error.serializationProvider.DeserializeTransactionError;
import one.block.eosiojava.error.serializationProvider.SerializeError;
import one.block.eosiojava.error.serializationProvider.SerializeTransactionError;
import one.block.eosiojava.error.session.TransactionBroadCastError;
import one.block.eosiojava.error.session.TransactionCreateSignatureRequestRequiredKeysEmptyError;
import one.block.eosiojava.error.session.TransactionPrepareError;
import one.block.eosiojava.error.session.TransactionProcessorConstructorInputError;
import one.block.eosiojava.error.session.TransactionSerializeError;
import one.block.eosiojava.error.session.TransactionSignAndBroadCastError;
import one.block.eosiojava.error.session.TransactionSignError;
import one.block.eosiojava.error.signatureProvider.GetAvailableKeysError;
import one.block.eosiojava.error.signatureProvider.SignTransactionError;
import one.block.eosiojava.interfaces.IABIProvider;
import one.block.eosiojava.interfaces.IRPCProvider;
import one.block.eosiojava.interfaces.ISerializationProvider;
import one.block.eosiojava.interfaces.ISignatureProvider;
import one.block.eosiojava.models.AbiEosSerializationObject;
import one.block.eosiojava.models.EOSIOName;
import one.block.eosiojava.models.rpcProvider.Action;
import one.block.eosiojava.models.rpcProvider.Authorization;
import one.block.eosiojava.models.rpcProvider.ContextFreeData;
import one.block.eosiojava.models.rpcProvider.Transaction;
import one.block.eosiojava.models.rpcProvider.TransactionConfig;
import one.block.eosiojava.models.rpcProvider.request.GetBlockRequest;
import one.block.eosiojava.models.rpcProvider.request.GetRequiredKeysRequest;
import one.block.eosiojava.models.rpcProvider.request.PushTransactionRequest;
import one.block.eosiojava.models.rpcProvider.response.GetBlockResponse;
import one.block.eosiojava.models.rpcProvider.response.GetInfoResponse;
import one.block.eosiojava.models.rpcProvider.response.GetRequiredKeysResponse;
import one.block.eosiojava.models.rpcProvider.response.PushTransactionResponse;
import one.block.eosiojava.models.signatureProvider.EosioTransactionSignatureRequest;
import one.block.eosiojava.models.signatureProvider.EosioTransactionSignatureResponse;
import one.block.eosiojava.utilities.DateFormatter;
import one.block.eosiojava.utilities.Utils;
import org.jetbrains.annotations.Nullable;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

@RunWith(JUnit4.class)
public class TransactionProcessorTest {

    private IRPCProvider mockedRpcProvider = mock(IRPCProvider.class);
    private ISignatureProvider mockedSignatureProvider = mock(ISignatureProvider.class);
    private IABIProvider mockedABIProvider = mock(IABIProvider.class);
    private ISerializationProvider mockedSerializationProvider = mock(ISerializationProvider.class);
    private TransactionSession session;

    @Before
    public void setUp() {
        this.session = new TransactionSession(this.mockedSerializationProvider, this.mockedRpcProvider, this.mockedABIProvider,
                this.mockedSignatureProvider);
    }

    @Test
    public void prepare() {
        // Test start
        TransactionProcessor processor = session.getTransactionProcessor();
        assertNotNull(processor);

        this.mockRPC(
                Utils.getGson(DateFormatter.BACKEND_DATE_PATTERN).fromJson(mockedGetInfoResponse, GetInfoResponse.class),
                Utils.getGson(DateFormatter.BACKEND_DATE_PATTERN).fromJson(mockedGetBlockResponse, GetBlockResponse.class),
                null, null);

        // Apply
        List<Action> actions = this.defaultActions();
        try {
            processor.prepare(actions);
        } catch (TransactionPrepareError transactionPrepareError) {
            transactionPrepareError.printStackTrace();
            fail("Exception should not be thrown here for calling prepare");
        }

        // Validate
        Transaction transaction = processor.getTransaction();
        assertNotNull(transaction);
        assertNotNull(transaction.getActions());
        assertArrayEquals(actions.toArray(), transaction.getActions().toArray());
        assertArrayEquals(actions.get(0).getAuthorization().toArray(), transaction.getActions().get(0).getAuthorization().toArray());

        assertNotNull(transaction.getExpiration());
        assertEquals(expectedExpiration, transaction.getExpiration());
        assertNotNull(transaction.getRefBlockNum());
        assertEquals(expectedRefBlockNum, transaction.getRefBlockNum());
        assertEquals(refBlockPrefix, transaction.getRefBlockPrefix());
        assertNotNull(transaction.getRefBlockPrefix());
    }

    @Test
    public void sign() {
        this.mockDefaultSuccessData();
        // Prepare has to be called before sign
        TransactionProcessor processor = createAndPrepareTransaction(this.defaultActions());
        assertNotNull(processor);

        try {
            assertTrue(processor.sign());
            assertEquals(MOCKED_TRANSACTION_HEX, processor.getSerializedTransaction());
            assertEquals(1, processor.getSignatures().size());
            assertEquals(MOCKED_SIGNATURE, processor.getSignatures().get(0));
        } catch (TransactionSignError transactionSignError) {
            transactionSignError.printStackTrace();
            fail("Exception should not be thrown here for calling sign");
        }
    }

    @Test
    public void broadcast() {
        this.mockDefaultSuccessData();
        // Prepare and sign has to be call in order to call broadcast one after the other
        // Prepare has to be called before sign
        TransactionProcessor processor = createAndPrepareTransaction(this.defaultActions());
        assertNotNull(processor);

        try {
            assertTrue(processor.sign());
        } catch (TransactionSignError transactionSignError) {
            transactionSignError.printStackTrace();
            fail("Exception should not be thrown here for calling sign");
        }

        try {
            PushTransactionResponse pushTransactionResponse = processor.broadcast();
            assertNotNull(pushTransactionResponse);
            assertEquals(DUMP_TRANSACTION_ID, pushTransactionResponse.getTransactionId());
        } catch (TransactionBroadCastError transactionBroadCastError) {
            transactionBroadCastError.printStackTrace();
            fail("Exception should not be thrown here for calling broadcast");
        }
    }

    @Test
    public void signAndBroadcast() {
        this.mockDefaultSuccessData();
        TransactionProcessor processor = createAndPrepareTransaction(this.defaultActions());
        assertNotNull(processor);

        try {
            PushTransactionResponse pushTransactionResponse = processor.signAndBroadcast();
            assertNotNull(pushTransactionResponse);
            assertEquals(DUMP_TRANSACTION_ID, pushTransactionResponse.getTransactionId());
        } catch (TransactionSignAndBroadCastError transactionSignAndBroadCastError) {
            transactionSignAndBroadCastError.printStackTrace();
            fail("Exception should not be thrown here for calling signAndBroadcast");
        }
    }

    @Test
    public void serialize() {
        this.mockDefaultSuccessData();
        TransactionProcessor processor = createAndPrepareTransaction(this.defaultActions());
        assertNotNull(processor);

        try {
            String serializedTransaction = processor.serialize();
            assertEquals(MOCKED_TRANSACTION_HEX, serializedTransaction);
        } catch (TransactionSerializeError transactionSerializeError) {
            transactionSerializeError.printStackTrace();
        }
    }

    @Test
    public void getTransaction() {
        this.mockDefaultSuccessData();
        List<Action> actions = this.defaultActions();
        TransactionProcessor processor = createAndPrepareTransaction(actions);
        assertNotNull(processor);

        Transaction transaction = processor.getTransaction();
        assertNotNull(transaction);

        assertNotNull(transaction.getActions());
        assertArrayEquals(actions.toArray(), transaction.getActions().toArray());
        assertArrayEquals(actions.get(0).getAuthorization().toArray(), transaction.getActions().get(0).getAuthorization().toArray());
        assertNotNull(transaction.getExpiration());
        assertEquals(expectedExpiration, transaction.getExpiration());
        assertNotNull(transaction.getRefBlockNum());
        assertEquals(expectedRefBlockNum, transaction.getRefBlockNum());
        assertEquals(refBlockPrefix, transaction.getRefBlockPrefix());
        assertNotNull(transaction.getRefBlockPrefix());
    }

    @Test
    public void getTransactionWithContextFreeActions() {
        this.mockDefaultSuccessData();
        List<Action> actions = this.defaultActions();
        TransactionProcessor processor = createAndPrepareTransaction(actions, actions, new ArrayList<String>());
        assertNotNull(processor);

        Transaction transaction = processor.getTransaction();
        assertNotNull(transaction);

        List<Action> contextFreeActions = transaction.getContextFreeActions();

        assertNotNull(contextFreeActions);
        assertArrayEquals(actions.toArray(), contextFreeActions.toArray());
        assertArrayEquals(actions.get(0).getAuthorization().toArray(), contextFreeActions.get(0).getAuthorization().toArray());
    }

    @Test
    public void getOriginalTransaction() {
        // Original Transaction value will be set in sign method
        this.mockDefaultSuccessData();
        List<Action> actions = this.defaultActions();
        // Prepare has to be called before sign
        TransactionProcessor processor = createAndPrepareTransaction(actions);
        assertNotNull(processor);

        try {
            assertTrue(processor.sign());
            Transaction transaction = processor.getOriginalTransaction();

            assertNotNull(transaction);
            assertNotNull(transaction.getActions());

            assertEquals(actions.get(0).getAuthorization().size(), transaction.getActions().get(0).getAuthorization().size());
            assertEquals(actions.get(0).getAuthorization().get(0).getActor(),
                    transaction.getActions().get(0).getAuthorization().get(0).getActor());
            assertEquals(actions.get(0).getAuthorization().get(0).getPermission(),
                    transaction.getActions().get(0).getAuthorization().get(0).getPermission());
            assertNotNull(transaction.getExpiration());
            assertEquals(expectedExpiration, transaction.getExpiration());
            assertNotNull(transaction.getRefBlockNum());
            assertEquals(expectedRefBlockNum, transaction.getRefBlockNum());
            assertEquals(refBlockPrefix, transaction.getRefBlockPrefix());
            assertNotNull(transaction.getRefBlockPrefix());
        } catch (TransactionSignError transactionSignError) {
            transactionSignError.printStackTrace();
            fail("Exception should not be thrown here for calling sign");
        }
    }

    @Test
    public void getSignatures() {
        this.mockDefaultSuccessData();
        // Prepare and sign has to be call in order to call broadcast one after the other
        // Prepare has to be called before sign
        TransactionProcessor processor = createAndPrepareTransaction(this.defaultActions());
        assertNotNull(processor);

        try {
            assertTrue(processor.sign());
        } catch (TransactionSignError transactionSignError) {
            transactionSignError.printStackTrace();
            fail("Exception should not be thrown here for calling sign");
        }

        List<String> signatures = processor.getSignatures();
        assertNotNull(signatures);
        assertEquals(1, signatures.size());
        assertEquals(MOCKED_SIGNATURE, signatures.get(0));
    }

    @Test
    public void getSerializedTransaction() {
        this.mockDefaultSuccessData();
        // Prepare has to be called before sign
        TransactionProcessor processor = createAndPrepareTransaction(this.defaultActions());
        assertNotNull(processor);

        try {
            assertTrue(processor.sign());
            assertEquals(MOCKED_TRANSACTION_HEX, processor.getSerializedTransaction());
        } catch (TransactionSignError transactionSignError) {
            transactionSignError.printStackTrace();
            fail("Exception should not be thrown here for calling sign");
        }
    }

    @Test
    public void getNoContextFreeData() {
        this.mockDefaultSuccessData();
        TransactionProcessor processor = createAndPrepareTransaction(this.defaultActions());
        assertNotNull(processor);

        ContextFreeData contextFreeData = processor.getContextFreeData();
        assertNotNull(contextFreeData);
        assertNotNull(contextFreeData.getData());
        assertNotNull(contextFreeData.getHexed());
        assertNotNull(contextFreeData.getSerialized());
        assertEquals(contextFreeData.getData().size(), 0);
        assertEquals(contextFreeData.getHexed(), "");
        assertEquals(contextFreeData.getSerialized(), "");
    }

    @Test
    public void getContextFreeData() {
        this.mockDefaultSuccessData();
        TransactionProcessor processor = createAndPrepareTransaction(this.defaultActions(), this.defaultContextFreeActions(false), this.defaultContextFreeData());
        assertNotNull(processor);

        ContextFreeData contextFreeData = processor.getContextFreeData();
        assertNotNull(contextFreeData);
        assertNotNull(contextFreeData.getData());
        assertNotNull(contextFreeData.getHexed());
        assertNotNull(contextFreeData.getSerialized());
        assertEquals(contextFreeData.getData().size(), 3);
        assertNotEquals(contextFreeData.getHexed(), "");
        assertNotEquals(contextFreeData.getSerialized(), "");
    }

    @Test
    public void getTransactionConfig() {
        this.mockDefaultSuccessData();
        TransactionProcessor processor = createAndPrepareTransaction(this.defaultActions());
        assertNotNull(processor);

        TransactionConfig config = new TransactionConfig();
        config.setBlocksBehind(7);
        config.setExpiresSeconds(100);
        processor.setTransactionConfig(config);

        TransactionConfig configToTest = processor.getTransactionConfig();
        assertEquals(config, configToTest);
    }

    @Test
    public void setChainId() {
        this.mockDefaultSuccessData();
        TransactionProcessor processor = createAndPrepareTransaction(this.defaultActions());
        assertNotNull(processor);
        processor.setChainId(MOCKED_CHAIN_ID);
    }

    @Test
    public void setAvailableKeys() {
        this.mockDefaultSuccessData();
        TransactionProcessor processor = createAndPrepareTransaction(this.defaultActions());
        assertNotNull(processor);

        List<String> availableKey = new ArrayList<>();
        availableKey.add("Key3");
        availableKey.add("Key4");
        processor.setAvailableKeys(availableKey);

        try {
            processor.signAndBroadcast();
        } catch (TransactionSignAndBroadCastError transactionSignAndBroadCastError) {
            // getAvailableKey is updated so it is different with RequiredKeys from backend
            assertTrue(transactionSignAndBroadCastError.getCause() instanceof TransactionCreateSignatureRequestRequiredKeysEmptyError);
        }
    }

    @Test
    public void setRequiredKeys() {
        this.mockDefaultSuccessData();
        TransactionProcessor processor = createAndPrepareTransaction(this.defaultActions());
        assertNotNull(processor);

        List<String> requiredKeys = new ArrayList<>();
        requiredKeys.add("Key3");
        requiredKeys.add("Key4");
        processor.setRequiredKeys(requiredKeys);

        try {
            PushTransactionResponse pushTransactionResponse = processor.signAndBroadcast();
            assertNotNull(pushTransactionResponse);
            assertEquals(DUMP_TRANSACTION_ID, pushTransactionResponse.getTransactionId());
        } catch (TransactionSignAndBroadCastError transactionSignAndBroadCastError) {
            transactionSignAndBroadCastError.printStackTrace();
            fail("Exception should not be thrown here for calling signAndBroadcast");
        }
    }

    @Test
    public void isAllowTransactionToBeModified() {
        this.mockRPC(
                Utils.getGson(DateFormatter.BACKEND_DATE_PATTERN).fromJson(mockedGetInfoResponse, GetInfoResponse.class),
                Utils.getGson(DateFormatter.BACKEND_DATE_PATTERN).fromJson(mockedGetBlockResponse, GetBlockResponse.class),
                Utils.getGson(DateFormatter.BACKEND_DATE_PATTERN).fromJson(mockedGetRequiredKeysResponse, GetRequiredKeysResponse.class),
                Utils.getGson(DateFormatter.BACKEND_DATE_PATTERN).fromJson(MOCKED_PUSHTRANSACTION_RESPONE_JSON, PushTransactionResponse.class));

        this.mockAbiProvider(EOSIOTOKENABIJSON);
        this.mockSerializationProvider(MOCKED_ACTION_HEX, MOCKED_TRANSACTION_HEX, mockedDeserilizedTransaction);
        this.mockSignatureProvider(Arrays.asList("Key1", "Key2"),
                Utils.getGson(DateFormatter.BACKEND_DATE_PATTERN)
                        .fromJson(mockedEosioTransactionSignatureResponseModifiedTransactionJSON, EosioTransactionSignatureResponse.class));

        TransactionProcessor processor = createAndPrepareTransaction(this.defaultActions());
        assertNotNull(processor);

        try {
            // Before signing, the transaction is still in original version
            assertEquals(MOCKED_TRANSACTION_HEX, processor.serialize());
        } catch (TransactionSerializeError transactionSerializeError) {
            transactionSerializeError.printStackTrace();
            fail("Exception should not be thrown here for calling serialize");
        }

        processor.setIsTransactionModificationAllowed(true);
        assertTrue(processor.isTransactionModificationAllowed());

       try {
           PushTransactionResponse pushTransactionResponse = processor.signAndBroadcast();
           assertNotNull(pushTransactionResponse);
           assertEquals(DUMP_TRANSACTION_ID, pushTransactionResponse.getTransactionId());

           // after signing and broadcast, serialized transaction is updated
           assertEquals(MOCKED_TRANSACTION_HEX_MODIFIED, processor.getSerializedTransaction());
       } catch (TransactionSignAndBroadCastError transactionSignAndBroadCastError) {
           transactionSignAndBroadCastError.printStackTrace();
           fail("Exception should not be thrown here for calling signAndBroadcast");
       }
    }

    @Test
    public void testPresetTransaction() {
        this.mockDefaultSuccessData();
        List<Action> actions = this.defaultActions();
        Transaction presetTransaction = new Transaction("", BigInteger.ZERO, BigInteger.ZERO,
                BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, new ArrayList<Action>(), actions,
                new ArrayList<String>());
        TransactionProcessor processor = null;
        try {
            processor = new TransactionProcessor(
                    this.mockedSerializationProvider,
                    this.mockedRpcProvider,
                    this.mockedABIProvider,
                    this.mockedSignatureProvider,
                    presetTransaction);
        } catch (TransactionProcessorConstructorInputError transactionProcessorConstructorInputError) {
            transactionProcessorConstructorInputError.printStackTrace();
            fail("Exception should not be thrown here for initializing TransactionProcessor with preset Transaction");
        }

        assertNotNull(processor);
        try {
            processor.prepare(actions);
        } catch (TransactionPrepareError transactionPrepareError) {
            transactionPrepareError.printStackTrace();
            fail("Exception should not be thrown here for calling prepare");
        }

        try {
            PushTransactionResponse pushTransactionResponse = processor.signAndBroadcast();
            assertNotNull(pushTransactionResponse);
            assertEquals(DUMP_TRANSACTION_ID, pushTransactionResponse.getTransactionId());
        } catch (TransactionSignAndBroadCastError transactionSignAndBroadCastError) {
            transactionSignAndBroadCastError.printStackTrace();
            fail("Exception should not be thrown here for sign and broadcast");
        }
    }

    @Test
    public void prepareWithOverrideActions() {
        this.mockDefaultSuccessData();
        List<Action> actions = this.defaultActions();
        Transaction presetTransaction = new Transaction("", BigInteger.ZERO, BigInteger.ZERO,
                BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, new ArrayList<Action>(), actions,
                new ArrayList<String>());
        TransactionProcessor processor = null;
        try {
            processor = new TransactionProcessor(
                    this.mockedSerializationProvider,
                    this.mockedRpcProvider,
                    this.mockedABIProvider,
                    this.mockedSignatureProvider,
                    presetTransaction);
        } catch (TransactionProcessorConstructorInputError transactionProcessorConstructorInputError) {
            transactionProcessorConstructorInputError.printStackTrace();
            fail("Exception should not be thrown here for initializing TransactionProcessor with preset Transaction");
        }

        assertNotNull(processor);
        try {
            String jsonData = "{\n" +
                    "\"from\": \"ken\",\n" +
                    "\"to\": \"an\",\n" +
                    "\"quantity\": \"1000.0000 EOS\",\n" +
                    "\"memo\" : \"Something else\"\n" +
                    "}";

            List<Authorization> authorizations = new ArrayList<>();
            authorizations.add(new Authorization("ken", "active"));
            List<Action> newActions = new ArrayList<>();
            newActions.add(new Action("eosio.create", "create", authorizations, jsonData));

            processor.prepare(newActions);
        } catch (TransactionPrepareError transactionPrepareError) {
            transactionPrepareError.printStackTrace();
            fail("Exception should not be thrown here for calling prepare");
        }

        Transaction transaction = processor.getTransaction();
        assertNotNull(transaction);
        assertEquals("eosio.create", transaction.getActions().get(0).getAccount());
        assertEquals("create", transaction.getActions().get(0).getName());
    }

    @Test
    public void testSerializedTransactionClearedOnPrepare() {
        // Prepare and sign
        this.mockDefaultSuccessData();
        // Prepare has to be called before sign
        TransactionProcessor processor = createAndPrepareTransaction(this.defaultActions());
        assertNotNull(processor);

        try {
            assertTrue(processor.sign());
            assertEquals(MOCKED_TRANSACTION_HEX, processor.getSerializedTransaction());
            assertEquals(1, processor.getSignatures().size());
            assertEquals(MOCKED_SIGNATURE, processor.getSignatures().get(0));
        } catch (TransactionSignError transactionSignError) {
            transactionSignError.printStackTrace();
            fail("Exception should not be thrown here for calling sign");
        }

        // Call prepare again to clear serialized transaction
        try {
            processor.prepare(this.defaultActions());
        } catch (TransactionPrepareError transactionPrepareError) {
            transactionPrepareError.printStackTrace();
            fail("Exception should not be thrown here for calling prepare");
        }

        assertEquals("", processor.getSerializedTransaction());
    }

    @Test
    public void testContextFreeActionPrepareWithoutData() {
        // Prepare and sign
        this.mockDefaultSuccessData();

        // Context free actions
        TransactionProcessor processor = session.getTransactionProcessor();
        try {
            processor.prepare(this.defaultActions(), this.defaultContextFreeActions(false));
        } catch (TransactionPrepareError transactionPrepareError) {
            transactionPrepareError.printStackTrace();
            fail("Exception should not be thrown here for calling prepare.");
        }

        try {
            assertNotEquals("", processor.serialize());
        } catch (TransactionSerializeError transactionSerializeError) {
            transactionSerializeError.printStackTrace();
            fail("Exception should not be thrown here for calling serialize.");
        }
    }

    @Test
    public void testContextFreeActionPrepareWithData() {
        // Prepare and sign
        this.mockDefaultSuccessData();

        // Context free actions
        TransactionProcessor processor = session.getTransactionProcessor();
        try {
            processor.prepare(this.defaultActions(), this.defaultContextFreeActions(true));
        } catch (TransactionPrepareError transactionPrepareError) {
            transactionPrepareError.printStackTrace();
            fail("Exception should not be thrown here for calling prepare.");
        }

        try {
            assertNotEquals("", processor.serialize());
        } catch (TransactionSerializeError transactionSerializeError) {
            transactionSerializeError.printStackTrace();
            fail("Exception should not be thrown here for calling serialize.");
        }
    }

    private void mockDefaultSuccessData() {
        this.mockRPC(
                Utils.getGson(DateFormatter.BACKEND_DATE_PATTERN).fromJson(mockedGetInfoResponse, GetInfoResponse.class),
                Utils.getGson(DateFormatter.BACKEND_DATE_PATTERN).fromJson(mockedGetBlockResponse, GetBlockResponse.class),
                Utils.getGson(DateFormatter.BACKEND_DATE_PATTERN).fromJson(mockedGetRequiredKeysResponse, GetRequiredKeysResponse.class),
                Utils.getGson(DateFormatter.BACKEND_DATE_PATTERN).fromJson(MOCKED_PUSHTRANSACTION_RESPONE_JSON, PushTransactionResponse.class));

        this.mockAbiProvider(EOSIOTOKENABIJSON);
        this.mockSerializationProvider(MOCKED_ACTION_HEX, MOCKED_TRANSACTION_HEX, mockedDeserilizedTransaction);
        this.mockSignatureProvider(Arrays.asList("Key1", "Key2"),
                Utils.getGson(DateFormatter.BACKEND_DATE_PATTERN).fromJson(mockedEosioTransactionSignatureResponseJSON, EosioTransactionSignatureResponse.class));
    }

    private List<Action> defaultActions() {
        String jsonData = "{\n" +
                "\"from\": \"an\",\n" +
                "\"to\": \"ken\",\n" +
                "\"quantity\": \"10.0000 EOS\",\n" +
                "\"memo\" : \"Something\"\n" +
                "}";

        List<Authorization> authorizations = new ArrayList<>();
        authorizations.add(new Authorization("cryptkeeper", "active"));
        List<Action> actions = new ArrayList<>();
        actions.add(new Action("eosio.token", "transfer", authorizations, jsonData));

        return actions;
    }

    private List<Action> defaultContextFreeActions(boolean addData) {
        String jsonData = addData ? "{\n" +
                "\"from\": \"an\",\n" +
                "\"to\": \"ken\",\n" +
                "\"quantity\": \"10.0000 EOS\",\n" +
                "\"memo\" : \"Something\"\n" +
                "}" : "";

        List<Action> actions = new ArrayList<>();
        actions.add(new Action("eosio.token", "transfer", new ArrayList<Authorization>(), jsonData, true));

        return actions;
    }

    private List<String> defaultContextFreeData() {
        String contextFreeData1 = "test";
        String contextFreeData2 = "{\"some\": \"jsonData\"}";
        String contextFreeData3 = "!@#$%^&*()_+";

        ArrayList<String> contextFreeData = new ArrayList<String>();
        contextFreeData.add(contextFreeData1);
        contextFreeData.add(contextFreeData2);
        contextFreeData.add(contextFreeData3);

        return contextFreeData;
    }

    private TransactionProcessor createAndPrepareTransaction(List<Action> actions, List<Action> contextFreeActions, List<String> contextFreeData) {
        try {
            TransactionProcessor processor = session.getTransactionProcessor();
            processor.prepare(actions, contextFreeActions, contextFreeData);
            return processor;
        } catch (TransactionPrepareError transactionPrepareError) {
            transactionPrepareError.printStackTrace();
            fail("Exception should not be thrown here for calling prepare");
            return null;
        }
    }

    private TransactionProcessor createAndPrepareTransaction(List<Action> actions, List<String> contextFreeData) {
        return this.createAndPrepareTransaction(actions, new ArrayList<Action>(), contextFreeData);
    }

    private TransactionProcessor createAndPrepareTransaction(List<Action> actions) {
        return this.createAndPrepareTransaction(actions, new ArrayList<String>());
    }

    private void mockRPC(
            @Nullable GetInfoResponse getInfoResponse,
            @Nullable GetBlockResponse getBlockResponse,
            @Nullable GetRequiredKeysResponse getRequiredKeysResponse,
            @Nullable PushTransactionResponse pushTransactionResponse) {

        if (getInfoResponse != null) {
            try {
                when(mockedRpcProvider.getInfo()).thenReturn(getInfoResponse);
            } catch (GetInfoRpcError getInfoRpcError) {
                getInfoRpcError.printStackTrace();
                fail("Exception should not be thrown here for mocking getInfo");
            }
        }

        if (getBlockResponse != null) {
            try {
                when(mockedRpcProvider.getBlock(any(GetBlockRequest.class))).thenReturn(getBlockResponse);
            } catch (GetBlockRpcError getBlockRpcError) {
                getBlockRpcError.printStackTrace();
                fail("Exception should not be thrown here for mocking getBlock");
            }
        }

        if (getRequiredKeysResponse != null) {
            try {
                when(this.mockedRpcProvider.getRequiredKeys(any(GetRequiredKeysRequest.class))).thenReturn(getRequiredKeysResponse);
            } catch (GetRequiredKeysRpcError getRequiredKeysRpcError) {
                getRequiredKeysRpcError.printStackTrace();
                fail("Exception should not be thrown here for mocking getRequiredKeys");
            }
        }

        if (pushTransactionResponse != null) {
            try {
                when(this.mockedRpcProvider.pushTransaction(any(PushTransactionRequest.class))).thenReturn(pushTransactionResponse);
            } catch (PushTransactionRpcError pushTransactionRpcError) {
                pushTransactionRpcError.printStackTrace();
                fail("Exception should not be thrown here for mocking pushTransaction");
            }
        }
    }

    private void mockAbiProvider(String abiJSON) {
        try {
            when(this.mockedABIProvider.getAbi(any(String.class), any(EOSIOName.class))).thenReturn(abiJSON);
        } catch (GetAbiError getAbiError) {
            getAbiError.printStackTrace();
            fail("Exception should not be thrown here for mocking getAbi");
        }
    }

    private void mockSerializationProvider(
            @Nullable final String mockedActionHex,
            @Nullable String mockedTransactionHex,
            @Nullable String mockedDeserializedTransaction) {

        if (mockedActionHex != null) {
            try {
                doAnswer(new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) {
                        Object[] args = invocationOnMock.getArguments();
                        ((AbiEosSerializationObject) args[0]).setHex(mockedActionHex);
                        return null;
                    }
                }).when(this.mockedSerializationProvider).serialize(any(AbiEosSerializationObject.class));
            } catch (SerializeError serializeError) {
                serializeError.printStackTrace();
                fail("Exception should not be thrown here for mocking serialize");
            }
        }

        if (mockedTransactionHex != null) {
            try {
                when(this.mockedSerializationProvider.serializeTransaction(any(String.class))).thenReturn(mockedTransactionHex);
            } catch (SerializeTransactionError serializeTransactionError) {
                serializeTransactionError.printStackTrace();
                fail("Exception should not be thrown here for mocking serializeTransaction");
            }
        }

        if (mockedDeserializedTransaction != null) {
            try {
                when(this.mockedSerializationProvider.deserializeTransaction(any(String.class))).thenReturn(mockedDeserializedTransaction);
            } catch (DeserializeTransactionError deserializeTransactionError) {
                deserializeTransactionError.printStackTrace();
                fail("Exception should not be thrown here for mocking deserializeTransaction");
            }
        }
    }

    private void mockSignatureProvider(List<String> mockedAvaialbleKeys, EosioTransactionSignatureResponse mockedResponse) {
        try {
            when(this.mockedSignatureProvider.getAvailableKeys()).thenReturn(mockedAvaialbleKeys);
        } catch (GetAvailableKeysError getAvailableKeysError) {
            getAvailableKeysError.printStackTrace();
            fail("Exception should not be thrown here for mocking getAvailableKeys");
        }

        try {
            when(this.mockedSignatureProvider.signTransaction(any(EosioTransactionSignatureRequest.class))).thenReturn(mockedResponse);
        } catch (SignTransactionError signTransactionError) {
            signTransactionError.printStackTrace();
            fail("Exception should not be thrown here for mocking signTransaction");
        }
    }

    private static final String EOSIOTOKENABIJSON = "{\"version\":\"eosio::abi/1.0\",\"types\":[{\"new_type_name\":\"account_name\",\"type\":\"name\"}],\"structs\":[{\"name\":\"transfer\",\"base\":\"\",\"fields\":[{\"name\":\"from\",\"type\":\"account_name\"},{\"name\":\"to\",\"type\":\"account_name\"},{\"name\":\"quantity\",\"type\":\"asset\"},{\"name\":\"memo\",\"type\":\"string\"}]},{\"name\":\"create\",\"base\":\"\",\"fields\":[{\"name\":\"issuer\",\"type\":\"account_name\"},{\"name\":\"maximum_supply\",\"type\":\"asset\"}]},{\"name\":\"issue\",\"base\":\"\",\"fields\":[{\"name\":\"to\",\"type\":\"account_name\"},{\"name\":\"quantity\",\"type\":\"asset\"},{\"name\":\"memo\",\"type\":\"string\"}]},{\"name\":\"account\",\"base\":\"\",\"fields\":[{\"name\":\"balance\",\"type\":\"asset\"}]},{\"name\":\"currency_stats\",\"base\":\"\",\"fields\":[{\"name\":\"supply\",\"type\":\"asset\"},{\"name\":\"max_supply\",\"type\":\"asset\"},{\"name\":\"issuer\",\"type\":\"account_name\"}]}],\"actions\":[{\"name\":\"transfer\",\"type\":\"transfer\",\"ricardian_contract\":\"---\\ntitle: Token Transfer\\nsummary: Transfer tokens from one account to another.\\nicon: https://cdn.testnet.dev.b1ops.net/token-transfer.png#ce51ef9f9eeca3434e85507e0ed49e76fff1265422bded0255f3196ea59c8b0c\\n---\\n\\n## Transfer Terms & Conditions\\n\\nI, {{from}}, certify the following to be true to the best of my knowledge:\\n\\n1. I certify that {{quantity}} is not the proceeds of fraudulent or violent activities.\\n2. I certify that, to the best of my knowledge, {{to}} is not supporting initiation of violence against others.\\n3. I have disclosed any contractual terms & conditions with respect to {{quantity}} to {{to}}.\\n\\nI understand that funds transfers are not reversible after the {{$transaction.delay_sec}} seconds or other delay as configured by {{from}}'s permissions.\\n\\nIf this action fails to be irreversibly confirmed after receiving goods or services from '{{to}}', I agree to either return the goods or services or resend {{quantity}} in a timely manner.\"},{\"name\":\"issue\",\"type\":\"issue\",\"ricardian_contract\":\"\"},{\"name\":\"create\",\"type\":\"create\",\"ricardian_contract\":\"\"}],\"tables\":[{\"name\":\"accounts\",\"index_type\":\"i64\",\"key_names\":[\"currency\"],\"key_types\":[\"uint64\"],\"type\":\"account\"},{\"name\":\"stat\",\"index_type\":\"i64\",\"key_names\":[\"currency\"],\"key_types\":[\"uint64\"],\"type\":\"currency_stats\"}],\"ricardian_clauses\":[],\"error_messages\":[],\"abi_extensions\":[],\"variants\":[]}";

    private static final String DUMP_TRANSACTION_ID = "17335a29eae22e531966f3775e44f8b02173e780c9549881e01e470ff0ab46ce";
    private static final String MOCKED_PUSHTRANSACTION_RESPONE_JSON = "{\"transaction_id\":\"" + DUMP_TRANSACTION_ID + "\"}";

    private static final BigInteger headBlockNum = BigInteger.valueOf(31984402L);
    private static final String headBlockTime = "2019-04-01T22:08:40.000";
    private static final BigInteger refBlockNum = BigInteger.valueOf(2831);
    private static final BigInteger refBlockPrefix = BigInteger.valueOf(3734378733L);
    private static final String MOCKED_ACTION_HEX = "Mocked Action Hex";
    private static final String MOCKED_TRANSACTION_HEX = "8BC2A35CF56E6CC25F7F000000000100A6823403EA3055000000572D3CCDCD01000000000000C03400000000A8ED32322A000000000000C034000000000000A682A08601000000000004454F530000000009536F6D657468696E6700";
    private static final String MOCKED_SIGNATURE = "SIG_R1_KsHAyy6EvQq2E6c7oxzxCtus5Jd4wP8KZkSxuhZUfxprU56okEWFjopjwy7wGH4fAjJKgTcceG4iUhZGRsWfYiDaTK5X5y";
    private static final String MOCKED_CHAIN_ID = "Mocked chain id";
    private static final String MOCKED_TRANSACTION_HEX_MODIFIED = "1ec3a35c1a706e886c51000000000100a6823403ea3055000000572d3ccdcd01000000000000c03400000000a8ed32322a000000000000c034000000000000a682a08601000000000004454f530000000009536f6d657468696e6700";
    private static final String MOCKED_CONTEXT_FREE_DATA_HEX = "C21BFB5AD4B64BFD04838B3B14F0CE0C7B92136CAC69BFED41BEF92F95A9BB20";



    // Expectation
    // headBlockTime + default 5 minutes
    private static final String expectedExpiration = "2019-04-01T22:13:40.000";
    private static TransactionConfig transactionConfig = new TransactionConfig();
    private static final BigInteger expectedRefBlockNum = headBlockNum.subtract(BigInteger.valueOf(transactionConfig.getBlocksBehind()))
            .and(BigInteger.valueOf(0xffff));

    // Mock data for prepare
    private static final String mockedGetInfoResponse = "{\n"
            + "    \"server_version\": \"1\",\n"
            + "    \"chain_id\": \"sample chain id\",\n"
            + "    \"head_block_num\": " + headBlockNum + ",\n"
            + "    \"last_irreversible_block_num\": 1,\n"
            + "    \"last_irreversible_block_id\": \"1\",\n"
            + "    \"head_block_id\": \"1\",\n"
            + "    \"head_block_time\": \"" + headBlockTime + "\",\n"
            + "    \"head_block_producer\": \"bp\",\n"
            + "    \"virtual_block_cpu_limit\": 1,\n"
            + "    \"virtual_block_net_limit\": 1,\n"
            + "    \"block_cpu_limit\": 1,\n"
            + "    \"block_net_limit\": 1,\n"
            + "    \"server_version_string\": \"v1.3.0\"\n"
            + "}";

    private static final String mockedGetBlockResponse = "{\n"
            + "    \"timestamp\": \"2019-04-01T22:08:38.500\",\n"
            + "    \"producer\": \"bp\",\n"
            + "    \"confirmed\": 0,\n"
            + "    \"previous\": \"1\",\n"
            + "    \"transaction_mroot\": \"0000000000000000000000000000000000000000000000000000000000000000\",\n"
            + "    \"action_mroot\": \"1\",\n"
            + "    \"schedule_version\": 3,\n"
            + "    \"new_producers\": null,\n"
            + "    \"header_extensions\": [],\n"
            + "    \"producer_signature\": \"SIG\",\n"
            + "    \"transactions\": [],\n"
            + "    \"block_extensions\": [],\n"
            + "    \"id\": \"1\",\n"
            + "    \"block_num\": 31984399,\n"
            + "    \"ref_block_prefix\": " + refBlockPrefix + "\n"
            + "}";

    private static final String mockedEosioTransactionSignatureResponseJSON = "{"
            + "\"serializedTransaction\": \"" + MOCKED_TRANSACTION_HEX + "\","
            + "\"signatures\": [\"" + MOCKED_SIGNATURE + "\"]"
            + "}";

    private static final String mockedEosioTransactionSignatureResponseModifiedTransactionJSON = "{"
            + "\"serializedTransaction\": \"" + MOCKED_TRANSACTION_HEX_MODIFIED + "\","
            + "\"signatures\": [\"" + MOCKED_SIGNATURE + "\"]"
            + "}";

    private static final String mockedGetRequiredKeysResponse = "{\n"
            + "    \"required_keys\": [\n"
            + "        \"Key1\"\n"
            + "    ]\n"
            + "}";

    private static final String mockedDeserilizedTransaction = "{\n" +
            "\"expiration\" : \"" + expectedExpiration + "\",\n" +
            "\"ref_block_num\" : " + expectedRefBlockNum + ",\n" +
            "\"ref_block_prefix\" : " + refBlockPrefix + ",\n" +
            "\"max_net_usage_words\" : 0,\n" +
            "\"max_cpu_usage_ms\" : 0,\n" +
            "\"delay_sec\" : 0,\n" +
            "\"context_free_actions\" : [],\n" +
            "\"actions\" : [\n" +
            "{\n" +
            "\"account\" : \"eosio.token\",\n" +
            "\"name\" : \"transfer\",\n" +
            "\"authorization\" : [\n" +
            "{\n" +
            "\"actor\" : \"an\",\n" +
            "\"permission\" : \"active\"\n" +
            "}\n" +
            "],\n" +
            "\"data\" : \"$transactionHex\"\n" +
            "}\n" +
            "]\n" +
            ",\n" +
            "\"transaction_extensions\" : []\n" +
            "}";

}
