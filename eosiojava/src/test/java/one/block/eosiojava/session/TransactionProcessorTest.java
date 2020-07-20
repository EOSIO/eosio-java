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
import one.block.eosiojava.error.rpcProvider.SendTransactionRpcError;
import one.block.eosiojava.error.serializationProvider.DeserializeError;
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
import one.block.eosiojava.models.rpcProvider.request.SendTransactionRequest;
import one.block.eosiojava.models.rpcProvider.response.GetBlockResponse;
import one.block.eosiojava.models.rpcProvider.response.GetInfoResponse;
import one.block.eosiojava.models.rpcProvider.response.GetRequiredKeysResponse;
import one.block.eosiojava.models.rpcProvider.response.PushTransactionResponse;
import one.block.eosiojava.models.rpcProvider.response.SendTransactionResponse;
import one.block.eosiojava.models.rpcProvider.response.TransactionResponse;
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
            TransactionResponse transactionResponse = processor.broadcast();
            assertNotNull(transactionResponse);
            assertEquals(DUMP_TRANSACTION_ID, transactionResponse.getTransactionId());
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
            TransactionResponse transactionResponse = processor.signAndBroadcast();
            assertNotNull(transactionResponse);
            assertEquals(DUMP_TRANSACTION_ID, transactionResponse.getTransactionId());
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
            TransactionResponse transactionResponse = processor.signAndBroadcast();
            assertNotNull(transactionResponse);
            assertEquals(DUMP_TRANSACTION_ID, transactionResponse.getTransactionId());
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
                Utils.getGson(DateFormatter.BACKEND_DATE_PATTERN).fromJson(getMockedSendTransactionResponseJson(), SendTransactionResponse.class));

        this.mockAbiProvider(EOSIOABIJSON);
        this.mockSerializationProvider(MOCKED_ACTION_HEX, MOCKED_TRANSACTION_HEX, mockedDeserilizedTransaction, MOCKED_RETURN_VALUE_JSON);
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
           TransactionResponse transactionResponse = processor.signAndBroadcast();
           assertNotNull(transactionResponse);
           assertEquals(DUMP_TRANSACTION_ID, transactionResponse.getTransactionId());

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
            TransactionResponse transactionResponse = processor.signAndBroadcast();
            assertNotNull(transactionResponse);
            assertEquals(DUMP_TRANSACTION_ID, transactionResponse.getTransactionId());
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

    @Test
    public void testDeserializeActionTraceWithNoReturnValue() {
        String mockedActionTraceWithSimpleReturnValue = getMockedProcessedActionTrace("", "retval_null");
        String mockPushTransactionResponseJson = this.getMockedSendTransactionResponseJson(mockedActionTraceWithSimpleReturnValue);
        String expectedJson = null;
        this.mockDefaultSuccessData(mockPushTransactionResponseJson, expectedJson, EOSIOABIJSON);

        TransactionProcessor processor = createAndPrepareTransaction(this.defaultActions());
        assertNotNull(processor);

        try {
            TransactionResponse transactionResponse = processor.signAndBroadcast();
            assertNotNull(transactionResponse);
            assertEquals(expectedJson, transactionResponse.getActionTraces().get(0).getDeserializedReturnValue());
        } catch (TransactionSignAndBroadCastError transactionSignAndBroadCastError) {
            transactionSignAndBroadCastError.printStackTrace();
            fail("Exception should not be thrown here for calling signAndBroadcast");
        }
    }

    @Test
    public void testDeserializeActionTraceWithSimpleReturnValue() {
        String mockedActionTraceWithSimpleReturnValue = getMockedProcessedActionTrace("0a000000", "retval_simple");
        String mockPushTransactionResponseJson = this.getMockedSendTransactionResponseJson(mockedActionTraceWithSimpleReturnValue);
        String expectedJson = "10";
        this.mockDefaultSuccessData(mockPushTransactionResponseJson, expectedJson, EOSIOABIJSON);

        TransactionProcessor processor = createAndPrepareTransaction(this.defaultActions());
        assertNotNull(processor);

        try {
            TransactionResponse transactionResponse = processor.signAndBroadcast();
            assertNotNull(transactionResponse);
            assertEquals(expectedJson, transactionResponse.getActionTraces().get(0).getDeserializedReturnValue());
        } catch (TransactionSignAndBroadCastError transactionSignAndBroadCastError) {
            transactionSignAndBroadCastError.printStackTrace();
            fail("Exception should not be thrown here for calling signAndBroadcast");
        }
    }

    @Test
    public void testDeserializeActionTraceWithComplexReturnValue() {
        String mockedActionTraceWithComplexReturnValue = getMockedProcessedActionTrace("d2040000000000000090b1ca", "retval_complex");
        String mockPushTransactionResponseJson = this.getMockedSendTransactionResponseJson(mockedActionTraceWithComplexReturnValue);
        String expectedJson = "{\"id\":1234,\"name\":\"test\"}";
        this.mockDefaultSuccessData(mockPushTransactionResponseJson, expectedJson, EOSIOABIJSON);

        TransactionProcessor processor = createAndPrepareTransaction(this.defaultActions());
        assertNotNull(processor);

        try {
            TransactionResponse transactionResponse = processor.signAndBroadcast();
            assertNotNull(transactionResponse);
            assertEquals(expectedJson, transactionResponse.getActionTraces().get(0).getDeserializedReturnValue());
        } catch (TransactionSignAndBroadCastError transactionSignAndBroadCastError) {
            transactionSignAndBroadCastError.printStackTrace();
            fail("Exception should not be thrown here for calling signAndBroadcast");
        }
    }

    @Test
    public void testDeserializeQueryItActionTrace() {
        String mockedActionTraceWithSimpleReturnValue = getMockedProcessedActionTrace("0000000c5b22737472696e67222c2274657374225d", "queryit");
        String mockPushTransactionResponseJson = this.getMockedSendTransactionResponseJson(mockedActionTraceWithSimpleReturnValue);
        String expectedJson = "\"test\"";
        this.mockDefaultSuccessData(mockPushTransactionResponseJson, expectedJson, QUERYITABIJSON);

        TransactionProcessor processor = createAndPrepareTransaction(this.defaultActions());
        assertNotNull(processor);

        try {
            TransactionResponse transactionResponse = processor.signAndBroadcast();
            assertNotNull(transactionResponse);
            assertEquals(expectedJson, transactionResponse.getActionTraces().get(0).getDeserializedReturnValue());
        } catch (TransactionSignAndBroadCastError transactionSignAndBroadCastError) {
            transactionSignAndBroadCastError.printStackTrace();
            fail("Exception should not be thrown here for calling signAndBroadcast");
        }
    }

    @Test
    public void testDeserializeB1XQueryItActionTraceSignAndBroadcast() {
        String returnValue = "0D070571756F74650C103533383035393639302E32312055534404626173650C1238393335322E3534303030303030204254430B62616E636F7250726963650C0B363032312E3736205553440E6C617374547261646550726963650C0B363031302E383720555344116C61737454726164655175616E746974790C103136312E3839303835393437204254430461736B730D010565646765730E0004626964730D010565646765730E040D01046E6F64650D08076F726465724964020400000000000000056F776E65720C056D616B65720668616E646C650220766BF085AA05000570726963650C0B353030302E30302055534404636F73740C08302E3030205553440972656D61696E696E670C0E312E3030303030303030204254430473697A650C0E312E30303030303030302042544307637265617465640980791FA58CAA05000D01046E6F64650D08076F726465724964020900000000000000056F776E65720C0B6D61786E616D73746F726D0668616E646C6502A0DC160486AA05000570726963650C0B353030302E30302055534404636F73740C08302E3030205553440972656D61696E696E670C0F31302E3030303030303030204254430473697A650C0F31302E303030303030303020425443076372656174656409C09DBBB88CAA05000D01046E6F64650D08076F726465724964020A00000000000000056F776E65720C0B6D61786E616D73746F726D0668616E646C65026057090986AA05000570726963650C0B353030302E30302055534404636F73740C08302E3030205553440972656D61696E696E670C0F31302E3030303030303030204254430473697A650C0F31302E303030303030303020425443076372656174656409A0B9B5BD8CAA05000D01046E6F64650D08076F726465724964020700000000000000056F776E65720C056D616B65720668616E646C6502A06B50FA85AA05000570726963650C0B353139392E35302055534404636F73740C08302E3030205553440972656D61696E696E670C0E312E3030303030303030204254430473697A650C0E312E303030303030303020425443076372656174656409C02CF5AE8CAA0500";
        String mockedActionTraceWithSimpleReturnValue = getMockedProcessedActionTrace(returnValue, "queryit");
        String mockPushTransactionResponseJson = this.getMockedSendTransactionResponseJson(mockedActionTraceWithSimpleReturnValue);
        String expectedJson = "[\"any_object\",[{\"name\":\"quote\",\"value\":[\"string\",\"538059690.21 USD\"]},{\"name\":\"base\",\"value\":[\"string\",\"89352.54000000 BTC\"]},{\"name\":\"bancorPrice\",\"value\":[\"string\",\"6021.76 USD\"]},{\"name\":\"lastTradePrice\",\"value\":[\"string\",\"6010.87 USD\"]},{\"name\":\"lastTradeQuantity\",\"value\":[\"string\",\"161.89085947 BTC\"]},{\"name\":\"asks\",\"value\":[\"any_object\",[{\"name\":\"edges\",\"value\":[\"any_array\",[]]}]]},{\"name\":\"bids\",\"value\":[\"any_object\",[{\"name\":\"edges\",\"value\":[\"any_array\",[[\"any_object\",[{\"name\":\"node\",\"value\":[\"any_object\",[{\"name\":\"orderId\",\"value\":[\"uint64\",\"4\"]},{\"name\":\"owner\",\"value\":[\"string\",\"maker\"]},{\"name\":\"handle\",\"value\":[\"uint64\",\"1594867124500000\"]},{\"name\":\"price\",\"value\":[\"string\",\"5000.00 USD\"]},{\"name\":\"cost\",\"value\":[\"string\",\"0.00 USD\"]},{\"name\":\"remaining\",\"value\":[\"string\",\"1.00000000 BTC\"]},{\"name\":\"size\",\"value\":[\"string\",\"1.00000000 BTC\"]},{\"name\":\"created\",\"value\":[\"time_point\",\"2020-07-16T10:38:46.000\"]}]]}]],[\"any_object\",[{\"name\":\"node\",\"value\":[\"any_object\",[{\"name\":\"orderId\",\"value\":[\"uint64\",\"9\"]},{\"name\":\"owner\",\"value\":[\"string\",\"maxnamstorm\"]},{\"name\":\"handle\",\"value\":[\"uint64\",\"1594867454500000\"]},{\"name\":\"price\",\"value\":[\"string\",\"5000.00 USD\"]},{\"name\":\"cost\",\"value\":[\"string\",\"0.00 USD\"]},{\"name\":\"remaining\",\"value\":[\"string\",\"10.00000000 BTC\"]},{\"name\":\"size\",\"value\":[\"string\",\"10.00000000 BTC\"]},{\"name\":\"created\",\"value\":[\"time_point\",\"2020-07-16T10:44:15.000\"]}]]}]],[\"any_object\",[{\"name\":\"node\",\"value\":[\"any_object\",[{\"name\":\"orderId\",\"value\":[\"uint64\",\"10\"]},{\"name\":\"owner\",\"value\":[\"string\",\"maxnamstorm\"]},{\"name\":\"handle\",\"value\":[\"uint64\",\"1594867537500000\"]},{\"name\":\"price\",\"value\":[\"string\",\"5000.00 USD\"]},{\"name\":\"cost\",\"value\":[\"string\",\"0.00 USD\"]},{\"name\":\"remaining\",\"value\":[\"string\",\"10.00000000 BTC\"]},{\"name\":\"size\",\"value\":[\"string\",\"10.00000000 BTC\"]},{\"name\":\"created\",\"value\":[\"time_point\",\"2020-07-16T10:45:38.500\"]}]]}]],[\"any_object\",[{\"name\":\"node\",\"value\":[\"any_object\",[{\"name\":\"orderId\",\"value\":[\"uint64\",\"7\"]},{\"name\":\"owner\",\"value\":[\"string\",\"maker\"]},{\"name\":\"handle\",\"value\":[\"uint64\",\"1594867290500000\"]},{\"name\":\"price\",\"value\":[\"string\",\"5199.50 USD\"]},{\"name\":\"cost\",\"value\":[\"string\",\"0.00 USD\"]},{\"name\":\"remaining\",\"value\":[\"string\",\"1.00000000 BTC\"]},{\"name\":\"size\",\"value\":[\"string\",\"1.00000000 BTC\"]},{\"name\":\"created\",\"value\":[\"time_point\",\"2020-07-16T10:41:31.000\"]}]]}]]]]}]]}]]";
        String expectedSerializedJson = "{\"quote\":\"538059690.21 USD\",\"base\":\"89352.54000000 BTC\",\"bancorPrice\":\"6021.76 USD\",\"lastTradePrice\":\"6010.87 USD\",\"lastTradeQuantity\":\"161.89085947 BTC\",\"asks\":{\"edges\":[]},\"bids\":{\"edges\":[{\"node\":{\"orderId\":\"4\",\"owner\":\"maker\",\"handle\":\"1594867124500000\",\"price\":\"5000.00 USD\",\"cost\":\"0.00 USD\",\"remaining\":\"1.00000000 BTC\",\"size\":\"1.00000000 BTC\",\"created\":\"2020-07-16T10:38:46.000\"}},{\"node\":{\"orderId\":\"9\",\"owner\":\"maxnamstorm\",\"handle\":\"1594867454500000\",\"price\":\"5000.00 USD\",\"cost\":\"0.00 USD\",\"remaining\":\"10.00000000 BTC\",\"size\":\"10.00000000 BTC\",\"created\":\"2020-07-16T10:44:15.000\"}},{\"node\":{\"orderId\":\"10\",\"owner\":\"maxnamstorm\",\"handle\":\"1594867537500000\",\"price\":\"5000.00 USD\",\"cost\":\"0.00 USD\",\"remaining\":\"10.00000000 BTC\",\"size\":\"10.00000000 BTC\",\"created\":\"2020-07-16T10:45:38.500\"}},{\"node\":{\"orderId\":\"7\",\"owner\":\"maker\",\"handle\":\"1594867290500000\",\"price\":\"5199.50 USD\",\"cost\":\"0.00 USD\",\"remaining\":\"1.00000000 BTC\",\"size\":\"1.00000000 BTC\",\"created\":\"2020-07-16T10:41:31.000\"}}]}}";
        this.mockDefaultSuccessData(mockPushTransactionResponseJson, expectedJson, QUERYITABIJSON);

        TransactionProcessor processor = createAndPrepareTransaction(this.defaultActions());
        assertNotNull(processor);

        try {
            TransactionResponse transactionResponse = processor.signAndBroadcast();
            assertNotNull(transactionResponse);
            assertEquals(expectedSerializedJson, transactionResponse.getActionTraces().get(0).getDeserializedReturnValue());
        } catch (TransactionSignAndBroadCastError transactionSignAndBroadCastError) {
            transactionSignAndBroadCastError.printStackTrace();
            fail("Exception should not be thrown here for calling signAndBroadcast");
        }
    }

    @Test
    public void testDeserializeB1XQueryItActionTraceBroadcast() {
        String returnValue = "0D070571756F74650C103533383035393639302E32312055534404626173650C1238393335322E3534303030303030204254430B62616E636F7250726963650C0B363032312E3736205553440E6C617374547261646550726963650C0B363031302E383720555344116C61737454726164655175616E746974790C103136312E3839303835393437204254430461736B730D010565646765730E0004626964730D010565646765730E040D01046E6F64650D08076F726465724964020400000000000000056F776E65720C056D616B65720668616E646C650220766BF085AA05000570726963650C0B353030302E30302055534404636F73740C08302E3030205553440972656D61696E696E670C0E312E3030303030303030204254430473697A650C0E312E30303030303030302042544307637265617465640980791FA58CAA05000D01046E6F64650D08076F726465724964020900000000000000056F776E65720C0B6D61786E616D73746F726D0668616E646C6502A0DC160486AA05000570726963650C0B353030302E30302055534404636F73740C08302E3030205553440972656D61696E696E670C0F31302E3030303030303030204254430473697A650C0F31302E303030303030303020425443076372656174656409C09DBBB88CAA05000D01046E6F64650D08076F726465724964020A00000000000000056F776E65720C0B6D61786E616D73746F726D0668616E646C65026057090986AA05000570726963650C0B353030302E30302055534404636F73740C08302E3030205553440972656D61696E696E670C0F31302E3030303030303030204254430473697A650C0F31302E303030303030303020425443076372656174656409A0B9B5BD8CAA05000D01046E6F64650D08076F726465724964020700000000000000056F776E65720C056D616B65720668616E646C6502A06B50FA85AA05000570726963650C0B353139392E35302055534404636F73740C08302E3030205553440972656D61696E696E670C0E312E3030303030303030204254430473697A650C0E312E303030303030303020425443076372656174656409C02CF5AE8CAA0500";
        String mockedActionTraceWithSimpleReturnValue = getMockedProcessedActionTrace(returnValue, "queryit");
        String mockPushTransactionResponseJson = this.getMockedSendTransactionResponseJson(mockedActionTraceWithSimpleReturnValue);
        String expectedJson = "[\"any_object\",[{\"name\":\"quote\",\"value\":[\"string\",\"538059690.21 USD\"]},{\"name\":\"base\",\"value\":[\"string\",\"89352.54000000 BTC\"]},{\"name\":\"bancorPrice\",\"value\":[\"string\",\"6021.76 USD\"]},{\"name\":\"lastTradePrice\",\"value\":[\"string\",\"6010.87 USD\"]},{\"name\":\"lastTradeQuantity\",\"value\":[\"string\",\"161.89085947 BTC\"]},{\"name\":\"asks\",\"value\":[\"any_object\",[{\"name\":\"edges\",\"value\":[\"any_array\",[]]}]]},{\"name\":\"bids\",\"value\":[\"any_object\",[{\"name\":\"edges\",\"value\":[\"any_array\",[[\"any_object\",[{\"name\":\"node\",\"value\":[\"any_object\",[{\"name\":\"orderId\",\"value\":[\"uint64\",\"4\"]},{\"name\":\"owner\",\"value\":[\"string\",\"maker\"]},{\"name\":\"handle\",\"value\":[\"uint64\",\"1594867124500000\"]},{\"name\":\"price\",\"value\":[\"string\",\"5000.00 USD\"]},{\"name\":\"cost\",\"value\":[\"string\",\"0.00 USD\"]},{\"name\":\"remaining\",\"value\":[\"string\",\"1.00000000 BTC\"]},{\"name\":\"size\",\"value\":[\"string\",\"1.00000000 BTC\"]},{\"name\":\"created\",\"value\":[\"time_point\",\"2020-07-16T10:38:46.000\"]}]]}]],[\"any_object\",[{\"name\":\"node\",\"value\":[\"any_object\",[{\"name\":\"orderId\",\"value\":[\"uint64\",\"9\"]},{\"name\":\"owner\",\"value\":[\"string\",\"maxnamstorm\"]},{\"name\":\"handle\",\"value\":[\"uint64\",\"1594867454500000\"]},{\"name\":\"price\",\"value\":[\"string\",\"5000.00 USD\"]},{\"name\":\"cost\",\"value\":[\"string\",\"0.00 USD\"]},{\"name\":\"remaining\",\"value\":[\"string\",\"10.00000000 BTC\"]},{\"name\":\"size\",\"value\":[\"string\",\"10.00000000 BTC\"]},{\"name\":\"created\",\"value\":[\"time_point\",\"2020-07-16T10:44:15.000\"]}]]}]],[\"any_object\",[{\"name\":\"node\",\"value\":[\"any_object\",[{\"name\":\"orderId\",\"value\":[\"uint64\",\"10\"]},{\"name\":\"owner\",\"value\":[\"string\",\"maxnamstorm\"]},{\"name\":\"handle\",\"value\":[\"uint64\",\"1594867537500000\"]},{\"name\":\"price\",\"value\":[\"string\",\"5000.00 USD\"]},{\"name\":\"cost\",\"value\":[\"string\",\"0.00 USD\"]},{\"name\":\"remaining\",\"value\":[\"string\",\"10.00000000 BTC\"]},{\"name\":\"size\",\"value\":[\"string\",\"10.00000000 BTC\"]},{\"name\":\"created\",\"value\":[\"time_point\",\"2020-07-16T10:45:38.500\"]}]]}]],[\"any_object\",[{\"name\":\"node\",\"value\":[\"any_object\",[{\"name\":\"orderId\",\"value\":[\"uint64\",\"7\"]},{\"name\":\"owner\",\"value\":[\"string\",\"maker\"]},{\"name\":\"handle\",\"value\":[\"uint64\",\"1594867290500000\"]},{\"name\":\"price\",\"value\":[\"string\",\"5199.50 USD\"]},{\"name\":\"cost\",\"value\":[\"string\",\"0.00 USD\"]},{\"name\":\"remaining\",\"value\":[\"string\",\"1.00000000 BTC\"]},{\"name\":\"size\",\"value\":[\"string\",\"1.00000000 BTC\"]},{\"name\":\"created\",\"value\":[\"time_point\",\"2020-07-16T10:41:31.000\"]}]]}]]]]}]]}]]";
        String expectedSerializedJson = "{\"quote\":\"538059690.21 USD\",\"base\":\"89352.54000000 BTC\",\"bancorPrice\":\"6021.76 USD\",\"lastTradePrice\":\"6010.87 USD\",\"lastTradeQuantity\":\"161.89085947 BTC\",\"asks\":{\"edges\":[]},\"bids\":{\"edges\":[{\"node\":{\"orderId\":\"4\",\"owner\":\"maker\",\"handle\":\"1594867124500000\",\"price\":\"5000.00 USD\",\"cost\":\"0.00 USD\",\"remaining\":\"1.00000000 BTC\",\"size\":\"1.00000000 BTC\",\"created\":\"2020-07-16T10:38:46.000\"}},{\"node\":{\"orderId\":\"9\",\"owner\":\"maxnamstorm\",\"handle\":\"1594867454500000\",\"price\":\"5000.00 USD\",\"cost\":\"0.00 USD\",\"remaining\":\"10.00000000 BTC\",\"size\":\"10.00000000 BTC\",\"created\":\"2020-07-16T10:44:15.000\"}},{\"node\":{\"orderId\":\"10\",\"owner\":\"maxnamstorm\",\"handle\":\"1594867537500000\",\"price\":\"5000.00 USD\",\"cost\":\"0.00 USD\",\"remaining\":\"10.00000000 BTC\",\"size\":\"10.00000000 BTC\",\"created\":\"2020-07-16T10:45:38.500\"}},{\"node\":{\"orderId\":\"7\",\"owner\":\"maker\",\"handle\":\"1594867290500000\",\"price\":\"5199.50 USD\",\"cost\":\"0.00 USD\",\"remaining\":\"1.00000000 BTC\",\"size\":\"1.00000000 BTC\",\"created\":\"2020-07-16T10:41:31.000\"}}]}}";
        this.mockDefaultSuccessData(mockPushTransactionResponseJson, expectedJson, QUERYITABIJSON);

        TransactionProcessor processor = createAndPrepareTransaction(this.defaultActions());
        assertNotNull(processor);

        try {
            assertTrue(processor.sign());
        } catch (TransactionSignError transactionSignError) {
            transactionSignError.printStackTrace();
            fail("Exception should not be thrown here for calling sign");
        }

        try {
            TransactionResponse transactionResponse = processor.broadcast();
            assertNotNull(transactionResponse);
            assertEquals(expectedSerializedJson, transactionResponse.getActionTraces().get(0).getDeserializedReturnValue());
        } catch (TransactionBroadCastError transactionSignAndBroadCastError) {
            transactionSignAndBroadCastError.printStackTrace();
            fail("Exception should not be thrown here for calling signAndBroadcast");
        }
    }

    private String getMockedProcessedActionTrace(String returnValueHex, String actionName) {
        return "{\n"
                + "        \"action_ordinal\": 1,\n"
                + "        \"creator_action_ordinal\": 0,\n"
                + "        \"closest_unnotified_ancestor_action_ordinal\": 0,\n"
                + "        \"receipt\": {\n"
                + "          \"receiver\": \"account\",\n"
                + "          \"act_digest\": \"8f6c4be57658986020e3fc61294d339965a4491ddfae0ceacf598fcaf9955da0\",\n"
                + "          \"global_sequence\": 275242,\n"
                + "          \"recv_sequence\": 48,\n"
                + "          \"auth_sequence\": [[\n"
                + "              \"actor\",\n"
                + "              40\n"
                + "            ]\n"
                + "          ],\n"
                + "          \"code_sequence\": 14,\n"
                + "          \"abi_sequence\": 14\n"
                + "        },\n"
                + "        \"receiver\": \"account\",\n"
                + "        \"act\": {\n"
                + "          \"account\": \"account\",\n"
                + "          \"name\": \"" + actionName + "\",\n"
                + "          \"authorization\": [{\n"
                + "              \"actor\": \"actor\",\n"
                + "              \"permission\": \"active\"\n"
                + "            }\n"
                + "          ],\n"
                + "          \"data\": {\n"
                + "            \"user\": \"test\"\n"
                + "          },\n"
                + "          \"hex_data\": \"000000000090b1ca\"\n"
                + "        },\n"
                + "        \"context_free\": false,\n"
                + "        \"elapsed\": 760,\n"
                + "        \"console\": \"Hi, test\",\n"
                + "        \"trx_id\": \"7f2ccf898efcb08dd64cadf4ca2bcd61f2577f40162928283038a6a6950e6683\",\n"
                + "        \"block_num\": 275159,\n"
                + "        \"block_time\": \"2020-06-25T17:24:18.000\",\n"
                + "        \"producer_block_id\": null,\n"
                + "        \"account_ram_deltas\": [],\n"
                + "        \"account_disk_deltas\": [],\n"
                + "        \"except\": null,\n"
                + "        \"error_code\": null,\n"
                + "        \"return_value\": \"" + returnValueHex + "\"\n"
                + "      }";
    }

    private String getMockedSendTransactionResponseJson() {
        return getMockedSendTransactionResponseJson(null);
    }

    private String getMockedSendTransactionResponseJson(@Nullable String mockedProcessedActionTrace) {
        if (mockedProcessedActionTrace != null) {
            return "{\"transaction_id\":\"" + DUMP_TRANSACTION_ID + "\", \"processed\": { \"action_traces\": [" + mockedProcessedActionTrace + "]}}";
        }

        return "{\"transaction_id\":\"" + DUMP_TRANSACTION_ID + "\"}";
    }

    private void mockDefaultSuccessData() {
        mockDefaultSuccessData(getMockedSendTransactionResponseJson(), MOCKED_RETURN_VALUE_JSON, EOSIOABIJSON);
    }

    private void mockDefaultSuccessData(String mockedPushTransactionResponseJson, String mockedReturnValueJson, String mockedAbiJson) {
        this.mockRPC(
                Utils.getGson(DateFormatter.BACKEND_DATE_PATTERN).fromJson(mockedGetInfoResponse, GetInfoResponse.class),
                Utils.getGson(DateFormatter.BACKEND_DATE_PATTERN).fromJson(mockedGetBlockResponse, GetBlockResponse.class),
                Utils.getGson(DateFormatter.BACKEND_DATE_PATTERN).fromJson(mockedGetRequiredKeysResponse, GetRequiredKeysResponse.class),
                Utils.getGson(DateFormatter.BACKEND_DATE_PATTERN).fromJson(mockedPushTransactionResponseJson, SendTransactionResponse.class));

        this.mockAbiProvider(mockedAbiJson);
        this.mockSerializationProvider(MOCKED_ACTION_HEX, MOCKED_TRANSACTION_HEX, mockedDeserilizedTransaction, mockedReturnValueJson);
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
            @Nullable SendTransactionResponse sendTransactionResponse) {

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

        if (sendTransactionResponse != null) {
            try {
                when(this.mockedRpcProvider.sendTransaction(any(SendTransactionRequest.class))).thenReturn(sendTransactionResponse);
            } catch (SendTransactionRpcError pushTransactionRpcError) {
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
            @Nullable String mockedDeserializedTransaction,
            @Nullable String mockedReturnValueJson) {

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

        if (mockedReturnValueJson != null) {
            try {
                doAnswer(new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) {
                        Object[] args = invocationOnMock.getArguments();
                        ((AbiEosSerializationObject) args[0]).setJson(mockedReturnValueJson);
                        return null;
                    }
                }).when(this.mockedSerializationProvider).deserialize(any(AbiEosSerializationObject.class));
            } catch (DeserializeError deserializeTransactionError) {
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

    private static final String EOSIOABIJSON = "{\n"
            + "   \"version\":\"eosio::abi/1.2\",\n"
            + "   \"types\":[\n"
            + "      {\n"
            + "         \"new_type_name\":\"account_name\",\n"
            + "         \"type\":\"name\"\n"
            + "      }\n"
            + "   ],\n"
            + "   \"structs\":[\n"
            + "      {\n"
            + "         \"name\":\"transfer\",\n"
            + "         \"base\":\"\",\n"
            + "         \"fields\":[\n"
            + "            {\n"
            + "               \"name\":\"from\",\n"
            + "               \"type\":\"account_name\"\n"
            + "            },\n"
            + "            {\n"
            + "               \"name\":\"to\",\n"
            + "               \"type\":\"account_name\"\n"
            + "            },\n"
            + "            {\n"
            + "               \"name\":\"quantity\",\n"
            + "               \"type\":\"asset\"\n"
            + "            },\n"
            + "            {\n"
            + "               \"name\":\"memo\",\n"
            + "               \"type\":\"string\"\n"
            + "            }\n"
            + "         ]\n"
            + "      },\n"
            + "      {\n"
            + "         \"name\":\"create\",\n"
            + "         \"base\":\"\",\n"
            + "         \"fields\":[\n"
            + "            {\n"
            + "               \"name\":\"issuer\",\n"
            + "               \"type\":\"account_name\"\n"
            + "            },\n"
            + "            {\n"
            + "               \"name\":\"maximum_supply\",\n"
            + "               \"type\":\"asset\"\n"
            + "            }\n"
            + "         ]\n"
            + "      },\n"
            + "      {\n"
            + "         \"name\":\"issue\",\n"
            + "         \"base\":\"\",\n"
            + "         \"fields\":[\n"
            + "            {\n"
            + "               \"name\":\"to\",\n"
            + "               \"type\":\"account_name\"\n"
            + "            },\n"
            + "            {\n"
            + "               \"name\":\"quantity\",\n"
            + "               \"type\":\"asset\"\n"
            + "            },\n"
            + "            {\n"
            + "               \"name\":\"memo\",\n"
            + "               \"type\":\"string\"\n"
            + "            }\n"
            + "         ]\n"
            + "      },\n"
            + "      {\n"
            + "         \"name\":\"account\",\n"
            + "         \"base\":\"\",\n"
            + "         \"fields\":[\n"
            + "            {\n"
            + "               \"name\":\"balance\",\n"
            + "               \"type\":\"asset\"\n"
            + "            }\n"
            + "         ]\n"
            + "      },\n"
            + "      {\n"
            + "         \"name\":\"currency_stats\",\n"
            + "         \"base\":\"\",\n"
            + "         \"fields\":[\n"
            + "            {\n"
            + "               \"name\":\"supply\",\n"
            + "               \"type\":\"asset\"\n"
            + "            },\n"
            + "            {\n"
            + "               \"name\":\"max_supply\",\n"
            + "               \"type\":\"asset\"\n"
            + "            },\n"
            + "            {\n"
            + "               \"name\":\"issuer\",\n"
            + "               \"type\":\"account_name\"\n"
            + "            }\n"
            + "         ]\n"
            + "      },\n"
            + "      {\n"
            + "          \"name\": \"retval_complex\",\n"
            + "          \"base\": \"\",\n"
            + "          \"fields\": [\n"
            + "              {\n"
            + "                  \"name\": \"user\",\n"
            + "                  \"type\": \"name\"\n"
            + "              }\n"
            + "          ]\n"
            + "      },\n"
            + "      {\n"
            + "          \"name\": \"retval_simple\",\n"
            + "          \"base\": \"\",\n"
            + "          \"fields\": [\n"
            + "              {\n"
            + "                  \"name\": \"user\",\n"
            + "                  \"type\": \"name\"\n"
            + "              }\n"
            + "          ]\n"
            + "      },\n"
            + "      {\n"
            + "          \"name\": \"retval_null\",\n"
            + "          \"base\": \"\",\n"
            + "          \"fields\": [\n"
            + "              {\n"
            + "                  \"name\": \"user\",\n"
            + "                  \"type\": \"name\"\n"
            + "              }\n"
            + "          ]\n"
            + "      },\n"
            + "      {\n"
            + "          \"name\": \"returnValue\",\n"
            + "          \"base\": \"\",\n"
            + "          \"fields\": [\n"
            + "              {\n"
            + "                  \"name\": \"id\",\n"
            + "                  \"type\": \"uint32\"\n"
            + "              },\n"
            + "              {\n"
            + "                  \"name\": \"name\",\n"
            + "                  \"type\": \"name\"\n"
            + "              }\n"
            + "          ]\n"
            + "      }\n"
            + "   ],\n"
            + "   \"actions\":[\n"
            + "      {\n"
            + "         \"name\":\"transfer\",\n"
            + "         \"type\":\"transfer\",\n"
            + "         \"ricardian_contract\":\"---\\ntitle: Token Transfer\\nsummary: Transfer tokens from one account to another.\\nicon: https://cdn.testnet.dev.b1ops.net/token-transfer.png#ce51ef9f9eeca3434e85507e0ed49e76fff1265422bded0255f3196ea59c8b0c\\n---\\n\\n## Transfer Terms & Conditions\\n\\nI, {{from}}, certify the following to be true to the best of my knowledge:\\n\\n1. I certify that {{quantity}} is not the proceeds of fraudulent or violent activities.\\n2. I certify that, to the best of my knowledge, {{to}} is not supporting initiation of violence against others.\\n3. I have disclosed any contractual terms & conditions with respect to {{quantity}} to {{to}}.\\n\\nI understand that funds transfers are not reversible after the {{$transaction.delay_sec}} seconds or other delay as configured by {{from}}'s permissions.\\n\\nIf this action fails to be irreversibly confirmed after receiving goods or services from '{{to}}', I agree to either return the goods or services or resend {{quantity}} in a timely manner.\"\n"
            + "      },\n"
            + "      {\n"
            + "         \"name\":\"issue\",\n"
            + "         \"type\":\"issue\",\n"
            + "         \"ricardian_contract\":\"\"\n"
            + "      },\n"
            + "      {\n"
            + "         \"name\":\"create\",\n"
            + "         \"type\":\"create\",\n"
            + "         \"ricardian_contract\":\"\"\n"
            + "      },\n"
            + "      {\n"
            + "          \"name\": \"retval_complex\",\n"
            + "          \"type\": \"retval_complex\",\n"
            + "          \"ricardian_contract\": \"\"\n"
            + "      },\n"
            + "      {\n"
            + "          \"name\": \"retval_simple\",\n"
            + "          \"type\": \"retval_simple\",\n"
            + "          \"ricardian_contract\": \"\"\n"
            + "      },\n"
            + "      {\n"
            + "          \"name\": \"retval_null\",\n"
            + "          \"type\": \"retval_null\",\n"
            + "          \"ricardian_contract\": \"\"\n"
            + "      }\n"
            + "   ],\n"
            + "   \"tables\":[\n"
            + "      {\n"
            + "         \"name\":\"accounts\",\n"
            + "         \"index_type\":\"i64\",\n"
            + "         \"key_names\":[\n"
            + "            \"currency\"\n"
            + "         ],\n"
            + "         \"key_types\":[\n"
            + "            \"uint64\"\n"
            + "         ],\n"
            + "         \"type\":\"account\"\n"
            + "      },\n"
            + "      {\n"
            + "         \"name\":\"stat\",\n"
            + "         \"index_type\":\"i64\",\n"
            + "         \"key_names\":[\n"
            + "            \"currency\"\n"
            + "         ],\n"
            + "         \"key_types\":[\n"
            + "            \"uint64\"\n"
            + "         ],\n"
            + "         \"type\":\"currency_stats\"\n"
            + "      }\n"
            + "   ],\n"
            + "   \"ricardian_clauses\":[],\n"
            + "   \"error_messages\":[],\n"
            + "   \"abi_extensions\":[],\n"
            + "   \"variants\":[],\n"
            + "   \"action_results\": [\n"
            + "     {\n"
            + "         \"name\": \"retval_complex\",\n"
            + "         \"result_type\": \"returnValue\"\n"
            + "     },\n"
            + "     {\n"
            + "         \"name\": \"retval_simple\",\n"
            + "         \"result_type\": \"uint32\"\n"
            + "     }\n"
            + "   ]\n"
            + "}";

    private static final String QUERYITABIJSON = "{\n"
            + "    \"version\": \"eosio::abi/1.1\",\n"
            + "    \"types\": [\n"
            + "        {\n"
            + "            \"new_type_name\": \"any_array\",\n"
            + "            \"type\": \"anyvar[]\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"new_type_name\": \"any_object\",\n"
            + "            \"type\": \"field[]\"\n"
            + "        }\n"
            + "    ],\n"
            + "    \"structs\": [\n"
            + "        {\n"
            + "            \"name\": \"null_t\",\n"
            + "            \"base\": \"\",\n"
            + "            \"fields\": []\n"
            + "        },\n"
            + "        {\n"
            + "            \"name\": \"field\",\n"
            + "            \"base\": \"\",\n"
            + "            \"fields\": [\n"
            + "                {\n"
            + "                    \"name\": \"name\",\n"
            + "                    \"type\": \"string\"\n"
            + "                },\n"
            + "                {\n"
            + "                    \"name\": \"value\",\n"
            + "                    \"type\": \"anyvar\"\n"
            + "                }\n"
            + "            ]\n"
            + "        },\n"
            + "        {\n"
            + "            \"name\": \"query\",\n"
            + "            \"base\": \"\",\n"
            + "            \"fields\": [\n"
            + "                {\n"
            + "                    \"name\": \"method\",\n"
            + "                    \"type\": \"string\"\n"
            + "                },\n"
            + "                {\n"
            + "                    \"name\": \"arg\",\n"
            + "                    \"type\": \"anyvar?\"\n"
            + "                },\n"
            + "                {\n"
            + "                    \"name\": \"filter\",\n"
            + "                    \"type\": \"query[]\"\n"
            + "                }\n"
            + "            ]\n"
            + "        }\n"
            + "    ],\n"
            + "    \"variants\": [\n"
            + "        {\n"
            + "            \"name\": \"anyvar\",\n"
            + "            \"types\": [\n"
            + "                \"null_t\",\n"
            + "                \"int64\",\n"
            + "                \"uint64\",\n"
            + "                \"int32\",\n"
            + "                \"uint32\",\n"
            + "                \"int16\",\n"
            + "                \"uint16\",\n"
            + "                \"int8\",\n"
            + "                \"uint8\",\n"
            + "                \"time_point\",\n"
            + "                \"checksum256\",\n"
            + "                \"float64\",\n"
            + "                \"string\",\n"
            + "                \"any_object\",\n"
            + "                \"any_array\",\n"
            + "                \"bytes\",\n"
            + "                \"symbol\",\n"
            + "                \"symbol_code\",\n"
            + "                \"asset\"\n"
            + "            ]\n"
            + "        }\n"
            + "    ]\n"
            + "}";

    private static final String DUMP_TRANSACTION_ID = "17335a29eae22e531966f3775e44f8b02173e780c9549881e01e470ff0ab46ce";

    private static final BigInteger headBlockNum = BigInteger.valueOf(31984402L);
    private static final String headBlockTime = "2019-04-01T22:08:40.000";
    private static final BigInteger refBlockNum = BigInteger.valueOf(2831);
    private static final BigInteger refBlockPrefix = BigInteger.valueOf(3734378733L);
    private static final String MOCKED_ACTION_HEX = "Mocked Action Hex";
    private static final String MOCKED_TRANSACTION_HEX = "8BC2A35CF56E6CC25F7F000000000100A6823403EA3055000000572D3CCDCD01000000000000C03400000000A8ED32322A000000000000C034000000000000A682A08601000000000004454F530000000009536F6D657468696E6700";
    private static final String MOCKED_SIGNATURE = "SIG_R1_KsHAyy6EvQq2E6c7oxzxCtus5Jd4wP8KZkSxuhZUfxprU56okEWFjopjwy7wGH4fAjJKgTcceG4iUhZGRsWfYiDaTK5X5y";
    private static final String MOCKED_CHAIN_ID = "Mocked chain id";
    private static final String MOCKED_TRANSACTION_HEX_MODIFIED = "1ec3a35c1a706e886c51000000000100a6823403ea3055000000572d3ccdcd01000000000000c03400000000a8ed32322a000000000000c034000000000000a682a08601000000000004454f530000000009536f6d657468696e6700";
    private static final String MOCKED_RETURN_VALUE_JSON = "{\"id\":1234,\"name\":\"test\"}";
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
