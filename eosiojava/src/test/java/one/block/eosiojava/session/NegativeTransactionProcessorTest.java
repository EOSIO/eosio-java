package one.block.eosiojava.session;

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import one.block.eosiojava.error.EosioError;
import one.block.eosiojava.error.ErrorConstants;
import one.block.eosiojava.error.abiprovider.GetAbiError;
import one.block.eosiojava.error.rpcprovider.GetBlockRpcError;
import one.block.eosiojava.error.rpcprovider.GetInfoRpcError;
import one.block.eosiojava.error.rpcprovider.GetRequiredKeysRpcError;
import one.block.eosiojava.error.rpcprovider.PushTransactionRpcError;
import one.block.eosiojava.error.serializationprovider.DeserializeTransactionError;
import one.block.eosiojava.error.serializationprovider.SerializeError;
import one.block.eosiojava.error.serializationprovider.SerializeTransactionError;
import one.block.eosiojava.error.session.TransactionBroadCastError;
import one.block.eosiojava.error.session.TransactionCreateSignatureRequestError;
import one.block.eosiojava.error.session.TransactionCreateSignatureRequestKeyError;
import one.block.eosiojava.error.session.TransactionGetSignatureDeserializationError;
import one.block.eosiojava.error.session.TransactionGetSignatureSigningError;
import one.block.eosiojava.error.session.TransactionPrepareError;
import one.block.eosiojava.error.session.TransactionPrepareInputError;
import one.block.eosiojava.error.session.TransactionPrepareRpcError;
import one.block.eosiojava.error.session.TransactionProcessorConstructorInputError;
import one.block.eosiojava.error.session.TransactionPushTransactionError;
import one.block.eosiojava.error.session.TransactionSignError;
import one.block.eosiojava.error.signatureprovider.GetAvailableKeysError;
import one.block.eosiojava.error.signatureprovider.SignTransactionError;
import one.block.eosiojava.interfaces.IABIProvider;
import one.block.eosiojava.interfaces.IRPCProvider;
import one.block.eosiojava.interfaces.ISerializationProvider;
import one.block.eosiojava.interfaces.ISignatureProvider;
import one.block.eosiojava.models.AbiEosSerializationObject;
import one.block.eosiojava.models.EOSIOName;
import one.block.eosiojava.models.rpcprovider.Action;
import one.block.eosiojava.models.rpcprovider.Authorization;
import one.block.eosiojava.models.rpcprovider.Transaction;
import one.block.eosiojava.models.rpcprovider.TransactionConfig;
import one.block.eosiojava.models.rpcprovider.request.GetBlockRequest;
import one.block.eosiojava.models.rpcprovider.request.GetRequiredKeysRequest;
import one.block.eosiojava.models.rpcprovider.request.PushTransactionRequest;
import one.block.eosiojava.models.rpcprovider.response.GetBlockResponse;
import one.block.eosiojava.models.rpcprovider.response.GetInfoResponse;
import one.block.eosiojava.models.rpcprovider.response.GetRequiredKeysResponse;
import one.block.eosiojava.models.signatureprovider.EosioTransactionSignatureRequest;
import one.block.eosiojava.models.signatureprovider.EosioTransactionSignatureResponse;
import one.block.eosiojava.utilities.DateFormatter;
import one.block.eosiojava.utilities.Utils;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

@RunWith(JUnit4.class)
public class NegativeTransactionProcessorTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

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

    //region negative tests for "prepare"
    @Test
    public void prepare_thenFailWithEmptyActions() throws TransactionPrepareError {
        exceptionRule.expect(TransactionPrepareInputError.class);
        exceptionRule.expectMessage(ErrorConstants.TRANSACTION_PROCESSOR_ACTIONS_EMPTY_ERROR_MSG);
        TransactionProcessor processor = session.getTransactionProcessor();
        processor.prepare(new ArrayList<Action>());
    }

    @Test
    public void prepare_thenFailWithGetInfoError() throws TransactionPrepareError {
        exceptionRule.expect(TransactionPrepareRpcError.class);
        exceptionRule.expectMessage(ErrorConstants.TRANSACTION_PROCESSOR_RPC_GET_INFO);
        exceptionRule.expectCause(IsInstanceOf.<EosioError>instanceOf(GetInfoRpcError.class));

        try {
            // Mock RpcProvider to throw exception
            when(this.mockedRpcProvider.getInfo()).thenThrow(new GetInfoRpcError());
        } catch (GetInfoRpcError getInfoRpcError) {
            getInfoRpcError.printStackTrace();
            fail("Exception should not be thrown here for mocking getInfo");
        }

        TransactionProcessor processor = session.getTransactionProcessor();
        processor.prepare(this.defaultActions());
    }

    @Test
    public void prepare_thenFailWithGetBlockError() throws TransactionPrepareError {
        exceptionRule.expect(TransactionPrepareRpcError.class);
        exceptionRule.expectMessage(ErrorConstants.TRANSACTION_PROCESSOR_PREPARE_RPC_GET_BLOCK);
        exceptionRule.expectCause(IsInstanceOf.<EosioError>instanceOf(GetBlockRpcError.class));

        // Mock RpcProvider
        this.mockGetInfoPositively();

        try {
            when(this.mockedRpcProvider.getBlock(any(GetBlockRequest.class))).thenThrow(new GetBlockRpcError());
        } catch (GetBlockRpcError getBlockRpcError) {
            getBlockRpcError.printStackTrace();
            fail("Exception should not be thrown here for mocking getBlock");
        }

        TransactionProcessor processor = session.getTransactionProcessor();
        processor.prepare(this.defaultActions());
    }

    @Test
    public void prepare_thenFailWithWrongDateFormat() throws TransactionPrepareError {
        exceptionRule.expect(TransactionPrepareError.class);
        exceptionRule.expectMessage(ErrorConstants.TRANSACTION_PROCESSOR_HEAD_BLOCK_TIME_PARSE_ERROR);
        exceptionRule.expectCause(IsInstanceOf.<Exception>instanceOf(ParseException.class));

        String weirdHeadBlockTime = "2019-04-01TGM22:08:40.000";
        String mockedGetInfoResponseWithWeirdDateFormat = "{\n"
                + "    \"server_version\": \"1\",\n"
                + "    \"chain_id\": \"sample chain id\",\n"
                + "    \"head_block_num\": " + headBlockNum + ",\n"
                + "    \"last_irreversible_block_num\": 1,\n"
                + "    \"last_irreversible_block_id\": \"1\",\n"
                + "    \"head_block_id\": \"1\",\n"
                + "    \"head_block_time\": \"" + weirdHeadBlockTime + "\",\n"
                + "    \"head_block_producer\": \"bp\",\n"
                + "    \"virtual_block_cpu_limit\": 1,\n"
                + "    \"virtual_block_net_limit\": 1,\n"
                + "    \"block_cpu_limit\": 1,\n"
                + "    \"block_net_limit\": 1,\n"
                + "    \"server_version_string\": \"v1.3.0\"\n"
                + "}";

        try {
            when(this.mockedRpcProvider.getInfo())
                    .thenReturn(Utils.getGson(DateFormatter.BACKEND_DATE_PATTERN).fromJson(mockedGetInfoResponseWithWeirdDateFormat, GetInfoResponse.class));
        } catch (GetInfoRpcError getInfoRpcError) {
            getInfoRpcError.printStackTrace();
            fail("Exception should not be thrown here for mocking getInfo");
        }

        TransactionProcessor processor = session.getTransactionProcessor();
        processor.prepare(this.defaultActions());
    }

    @Test
    public void prepare_thenFailWithWrongChainId() throws TransactionPrepareError {
        exceptionRule.expect(TransactionPrepareError.class);
        exceptionRule.expectMessage(String.format(ErrorConstants.TRANSACTION_PROCESSOR_PREPARE_CHAINID_NOT_MATCH, "chainId2", "sample chain id"));

        this.mockGetInfoPositively();

        Transaction wrongChainIdTransaction = new Transaction("", BigInteger.ZERO, BigInteger.ZERO,
                BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, new ArrayList<Action>(), this.defaultActions(),
                new ArrayList<String>());

        TransactionProcessor processor = null;

        try {
            processor = session.getTransactionProcessor(wrongChainIdTransaction);
            processor.setChainId("chainId2");
        } catch (TransactionProcessorConstructorInputError transactionProcessorConstructorInputError) {
            transactionProcessorConstructorInputError.printStackTrace();
            fail("Exception should not be thrown here for create transaction here");
        }

        processor.setChainId("chainId2");
        processor.prepare(this.defaultActions());
    }

    //endregion

    //region negative tests for "sign"

    @Test
    public void signCallSerialize_thenFailWithGetAbiError() throws TransactionSignError {
        exceptionRule.expect(TransactionSignError.class);
        exceptionRule.expectMessage(ErrorConstants.TRANSACTION_PROCESSOR_SIGN_CREATE_SIGN_REQUEST_ERROR);
        exceptionRule.expectCause(IsInstanceOf.<EosioError>instanceOf(TransactionCreateSignatureRequestError.class));

        // Mock RpcProvider
        this.mockGetInfoPositively();
        this.mockGetBlockPositively();

        // Mock ABI provider
        try {
            when(this.mockedABIProvider.getAbi(any(String.class), any(EOSIOName.class))).thenThrow(new GetAbiError());
        } catch (GetAbiError getAbiError) {
            getAbiError.printStackTrace();
            fail("Exception should not be thrown here for mocking getAbi");
        }

        TransactionProcessor processor = session.getTransactionProcessor();

        try {
            processor.prepare(this.defaultActions());
        } catch (TransactionPrepareError transactionPrepareError) {
            transactionPrepareError.printStackTrace();
            fail("Exception should not be thrown here for calling prepare");
        }

        processor.sign();
    }

    @Test
    public void signCallCreateSignature_thenFailWithNullTransaction() throws TransactionSignError {
        exceptionRule.expect(TransactionSignError.class);
        exceptionRule.expectMessage(ErrorConstants.TRANSACTION_PROCESSOR_SIGN_CREATE_SIGN_REQUEST_ERROR);
        exceptionRule.expectCause(IsInstanceOf.<EosioError>instanceOf(TransactionCreateSignatureRequestError.class));

        TransactionProcessor processor = this.session.getTransactionProcessor();
        processor.sign();
    }

    @Test
    public void signCallCreateSignature_thenFailWithGetAvailableKeyError() throws TransactionSignError {
        exceptionRule.expect(TransactionSignError.class);
        exceptionRule.expectMessage(ErrorConstants.TRANSACTION_PROCESSOR_SIGN_CREATE_SIGN_REQUEST_ERROR);
        exceptionRule.expectCause(IsInstanceOf.<EosioError>instanceOf(TransactionCreateSignatureRequestKeyError.class));

        // Mock RpcProvider
        this.mockGetInfoPositively();
        this.mockGetBlockPositively();

        // Mock AbiProvider
        this.mockGetAbi(EOSIOTOKENABIJSON);

        // mock serialization
        this.mockSerialize(MOCKED_ACTION_HEX);
        this.mockSerializeTransaction(MOCKED_TRANSACTION_HEX);

        // Mock signature provider to throw exception on getAvailableKeys
        try {
            when(this.mockedSignatureProvider.getAvailableKeys()).thenThrow(new GetAvailableKeysError());
        } catch (GetAvailableKeysError getAvailableKeysError) {
            getAvailableKeysError.printStackTrace();
            fail("Exception should not be thrown here for mocking getAvailableKeys");
        }

        TransactionProcessor processor = session.getTransactionProcessor();

        try {
            processor.prepare(this.defaultActions());
        } catch (TransactionPrepareError transactionPrepareError) {
            transactionPrepareError.printStackTrace();
            fail("Exception should not be thrown here for calling prepare");
        }

        processor.sign();
    }

    @Test
    public void signCallGetSignature_thenFailWithEmptySerializedTransactionError()
            throws TransactionSignError {
        exceptionRule.expect(TransactionSignError.class);
        exceptionRule.expectMessage(ErrorConstants.TRANSACTION_PROCESSOR_SIGN_TRANSACTION_TRANS_EMPTY_ERROR);
        exceptionRule.expectCause(IsInstanceOf.<EosioError>instanceOf(
                TransactionGetSignatureSigningError.class));

        // Mock RpcProvider
        this.mockGetInfoPositively();
        this.mockGetBlockPositively();
        this.mockRequiredKeys(Utils.getGson(DateFormatter.BACKEND_DATE_PATTERN).fromJson(mockedGetRequiredKeysResponse, GetRequiredKeysResponse.class));

        // Mock AbiProvider
        this.mockGetAbi(EOSIOTOKENABIJSON);

        // mock serialization
        this.mockSerialize(MOCKED_ACTION_HEX);
        this.mockSerializeTransaction(MOCKED_TRANSACTION_HEX);

        // Mock signature provider
        this.mockGetAvailableKey(Arrays.asList("Key1", "Key2"));

        String mockedEmptyTransactionSignatureResponseJSON = "{"
                + "\"serializeTransaction\": \"\","
                + "\"signatures\": [\"" + MOCKED_SIGNATURE + "\"]"
                + "}";

        this.mockSignTransaction(Utils.getGson(DateFormatter.BACKEND_DATE_PATTERN)
                .fromJson(mockedEmptyTransactionSignatureResponseJSON, EosioTransactionSignatureResponse.class));

        TransactionProcessor processor = session.getTransactionProcessor();

        try {
            processor.prepare(this.defaultActions());
        } catch (TransactionPrepareError transactionPrepareError) {
            transactionPrepareError.printStackTrace();
            fail("Exception should not be thrown here for calling prepare");
        }

        processor.sign();
    }

    @Test
    public void signCallGetSignature_thenFailWithEmptySignatureError()
            throws TransactionSignError {
        exceptionRule.expect(TransactionSignError.class);
        exceptionRule.expectMessage(ErrorConstants.TRANSACTION_PROCESSOR_SIGN_TRANSACTION_SIGN_EMPTY_ERROR);
        exceptionRule.expectCause(IsInstanceOf.<EosioError>instanceOf(
                TransactionGetSignatureSigningError.class));

        // Mock RpcProvider
        this.mockGetInfoPositively();
        this.mockGetBlockPositively();
        this.mockRequiredKeys(Utils.getGson(DateFormatter.BACKEND_DATE_PATTERN).fromJson(mockedGetRequiredKeysResponse, GetRequiredKeysResponse.class));

        // Mock AbiProvider
        this.mockGetAbi(EOSIOTOKENABIJSON);

        // mock serialization
        this.mockSerialize(MOCKED_ACTION_HEX);
        this.mockSerializeTransaction(MOCKED_TRANSACTION_HEX);

        // Mock signature provider
        this.mockGetAvailableKey(Arrays.asList("Key1", "Key2"));

        String mockedEmptySignaturesSignatureResponseJSON = "{"
                + "\"serializeTransaction\": \"" + MOCKED_TRANSACTION_HEX + "\","
                + "\"signatures\": []"
                + "}";

        this.mockSignTransaction(Utils.getGson(DateFormatter.BACKEND_DATE_PATTERN)
                .fromJson(mockedEmptySignaturesSignatureResponseJSON, EosioTransactionSignatureResponse.class));

        TransactionProcessor processor = session.getTransactionProcessor();

        try {
            processor.prepare(this.defaultActions());
        } catch (TransactionPrepareError transactionPrepareError) {
            transactionPrepareError.printStackTrace();
            fail("Exception should not be thrown here for calling prepare");
        }

        processor.sign();
    }

    @Test
    public void signCallGetSignatureWithTransactionModified_thenFailWithDeserializeTransactionError()
            throws TransactionSignError {
        exceptionRule.expect(TransactionSignError.class);
        exceptionRule.expectMessage(ErrorConstants.TRANSACTION_PROCESSOR_GET_SIGN_DESERIALIZE_TRANS_ERROR);
        exceptionRule.expectCause(IsInstanceOf.<EosioError>instanceOf(
                TransactionGetSignatureDeserializationError.class));

        // Mock RpcProvider
        this.mockGetInfoPositively();
        this.mockGetBlockPositively();
        this.mockRequiredKeys(Utils.getGson(DateFormatter.BACKEND_DATE_PATTERN).fromJson(mockedGetRequiredKeysResponse, GetRequiredKeysResponse.class));

        // Mock AbiProvider
        this.mockGetAbi(EOSIOTOKENABIJSON);

        // mock serialization
        this.mockSerialize(MOCKED_ACTION_HEX);
        this.mockSerializeTransaction(MOCKED_TRANSACTION_HEX);

        // Mock Deserialize Transaction to throw error
        try {
            when(this.mockedSerializationProvider.deserializeTransaction(any(String.class))).thenThrow(new DeserializeTransactionError());
        } catch (DeserializeTransactionError deserializeTransactionError) {
            deserializeTransactionError.printStackTrace();
            fail("Exception should not be thrown here for mocking deserializeTransaction");
        }

        // Mock signature provider
        this.mockGetAvailableKey(Arrays.asList("Key1", "Key2"));
        this.mockSignTransaction(Utils.getGson(DateFormatter.BACKEND_DATE_PATTERN)
                .fromJson(mockedEosioTransactionSignatureResponseModifiedTransactionJSON, EosioTransactionSignatureResponse.class));

        TransactionProcessor processor = session.getTransactionProcessor();
        processor.setIsTransactionModificationAllowed(true);

        try {
            processor.prepare(this.defaultActions());
        } catch (TransactionPrepareError transactionPrepareError) {
            transactionPrepareError.printStackTrace();
            fail("Exception should not be thrown here for calling prepare");
        }

        processor.sign();
    }

    //endregion

    //region negative tests for broadcast

    @Test
    public void broadCast_thenFailWithPushTransactionError() throws TransactionBroadCastError {
        exceptionRule.expect(TransactionBroadCastError.class);
        exceptionRule.expectMessage(ErrorConstants.TRANSACTION_PROCESSOR_BROADCAST_TRANS_ERROR);
        exceptionRule.expectCause(IsInstanceOf.<EosioError>instanceOf(TransactionPushTransactionError.class));

        // Mock RpcProvider
        this.mockGetInfoPositively();
        this.mockGetBlockPositively();
        this.mockRequiredKeys(Utils.getGson(DateFormatter.BACKEND_DATE_PATTERN).fromJson(mockedGetRequiredKeysResponse, GetRequiredKeysResponse.class));

        // Mock PushTransaction RPC to throw error
        try {
            when(this.mockedRpcProvider.pushTransaction(any(PushTransactionRequest.class))).thenThrow(new PushTransactionRpcError());
        } catch (PushTransactionRpcError pushTransactionRpcError) {
            pushTransactionRpcError.printStackTrace();
            fail("Exception should not be thrown here for mocking pushTransaction");
        }

        // Mock AbiProvider
        this.mockGetAbi(EOSIOTOKENABIJSON);

        // mock serialization
        this.mockSerialize(MOCKED_ACTION_HEX);
        this.mockSerializeTransaction(MOCKED_TRANSACTION_HEX);

        // Mock signature provider
        this.mockGetAvailableKey(Arrays.asList("Key1", "Key2"));
        this.mockSignTransaction(Utils.getGson(DateFormatter.BACKEND_DATE_PATTERN)
                .fromJson(mockedEosioTransactionSignatureResponseJSON, EosioTransactionSignatureResponse.class));

        TransactionProcessor processor = session.getTransactionProcessor();

        try {
            processor.prepare(this.defaultActions());
        } catch (TransactionPrepareError transactionPrepareError) {
            transactionPrepareError.printStackTrace();
            fail("Exception should not be thrown here for calling prepare");
        }

        try {
            processor.sign();
        } catch (TransactionSignError transactionSignError) {
            transactionSignError.printStackTrace();
            fail("Exception should not be thrown here for calling prepare");
        }

        processor.broadcast();
    }

    private void mockRequiredKeys(GetRequiredKeysResponse response) {
        try {
            when(this.mockedRpcProvider.getRequiredKeys(any(GetRequiredKeysRequest.class))).thenReturn(response);
        } catch (GetRequiredKeysRpcError getRequiredKeysRpcError) {
            getRequiredKeysRpcError.printStackTrace();
            fail("Exception should not be thrown here for mocking getRequiredKeys");
        }
    }

    //endregion

    private void mockGetAvailableKey(List<String> mockedAvaialbleKeys) {
        try {
            when(this.mockedSignatureProvider.getAvailableKeys()).thenReturn(mockedAvaialbleKeys);
        } catch (GetAvailableKeysError getAvailableKeysError) {
            getAvailableKeysError.printStackTrace();
            fail("Exception should not be thrown here for mocking getAvailableKeys");
        }
    }

    private void mockSignTransaction(EosioTransactionSignatureResponse mockedResponse) {
        try {
            when(this.mockedSignatureProvider.signTransaction(any(EosioTransactionSignatureRequest.class))).thenReturn(mockedResponse);
        } catch (SignTransactionError signTransactionError) {
            signTransactionError.printStackTrace();
            fail("Exception should not be thrown here for mocking signTransaction");
        }
    }

    private void mockGetAbi(String abiJSON) {
        try {
            when(this.mockedABIProvider.getAbi(any(String.class), any(EOSIOName.class))).thenReturn(abiJSON);
        } catch (GetAbiError getAbiError) {
            getAbiError.printStackTrace();
            fail("Exception should not be thrown here for mocking getAbi");
        }
    }

    private void mockSerialize(final String mockedActionHex) {
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

    private void mockSerializeTransaction(String mockedTransactionHex) {
        try {
            when(this.mockedSerializationProvider.serializeTransaction(any(String.class))).thenReturn(mockedTransactionHex);
        } catch (SerializeTransactionError serializeTransactionError) {
            serializeTransactionError.printStackTrace();
            fail("Exception should not be thrown here for mocking serializeTransaction");
        }
    }

    private void mockDeserializeTransaction(String mockedDeserializedTransaction) {
        try {
            when(this.mockedSerializationProvider.deserializeTransaction(any(String.class))).thenReturn(mockedDeserializedTransaction);
        } catch (DeserializeTransactionError deserializeTransactionError) {
            deserializeTransactionError.printStackTrace();
            fail("Exception should not be thrown here for mocking deserializeTransaction");
        }
    }

    private void mockGetBlockPositively() {
        try {
            when(this.mockedRpcProvider.getBlock(any(GetBlockRequest.class)))
                    .thenReturn(Utils.getGson(DateFormatter.BACKEND_DATE_PATTERN).fromJson(mockedGetBlockResponse, GetBlockResponse.class));
        } catch (GetBlockRpcError getBlockRpcError) {
            getBlockRpcError.printStackTrace();
            fail("Exception should not be thrown here for mocking getBlock");
        }
    }

    private void mockGetInfoPositively() {
        try {
            when(this.mockedRpcProvider.getInfo())
                    .thenReturn(Utils.getGson(DateFormatter.BACKEND_DATE_PATTERN).fromJson(mockedGetInfoResponse, GetInfoResponse.class));
        } catch (GetInfoRpcError getInfoRpcError) {
            getInfoRpcError.printStackTrace();
            fail("Exception should not be thrown here for mocking getInfo");
        }
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


    private static final BigInteger headBlockNum = BigInteger.valueOf(31984402L);
    private static final String headBlockTime = "2019-04-01T22:08:40.000";
    private static final BigInteger refBlockPrefix = BigInteger.valueOf(3734378733L);
    private static final String MOCKED_SIGNATURE = "SIG_R1_KsHAyy6EvQq2E6c7oxzxCtus5Jd4wP8KZkSxuhZUfxprU56okEWFjopjwy7wGH4fAjJKgTcceG4iUhZGRsWfYiDaTK5X5y";

    // Expectation
    // headBlockTime + default 5 minutes
    private static final String expectedExpiration = "2019-04-01T22:13:40.000";
    private static TransactionConfig transactionConfig = new TransactionConfig();
    private static final BigInteger expectedRefBlockNum = headBlockNum.subtract(BigInteger.valueOf(transactionConfig.getBlocksBehind()))
            .and(BigInteger.valueOf(0xffff));

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

    private static final String MOCKED_ACTION_HEX = "Mocked Action Hex";
    private static final String MOCKED_TRANSACTION_HEX = "8BC2A35CF56E6CC25F7F000000000100A6823403EA3055000000572D3CCDCD01000000000000C03400000000A8ED32322A000000000000C034000000000000A682A08601000000000004454F530000000009536F6D657468696E6700";
    private static final String MOCKED_TRANSACTION_HEX_MODIFIED = "1ec3a35c1a706e886c51000000000100a6823403ea3055000000572d3ccdcd01000000000000c03400000000a8ed32322a000000000000c034000000000000a682a08601000000000004454f530000000009536f6d657468696e6700";

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

    private static final String EOSIOTOKENABIJSON = "{\"version\":\"eosio::abi/1.0\",\"types\":[{\"new_type_name\":\"account_name\",\"type\":\"name\"}],\"structs\":[{\"name\":\"transfer\",\"base\":\"\",\"fields\":[{\"name\":\"from\",\"type\":\"account_name\"},{\"name\":\"to\",\"type\":\"account_name\"},{\"name\":\"quantity\",\"type\":\"asset\"},{\"name\":\"memo\",\"type\":\"string\"}]},{\"name\":\"create\",\"base\":\"\",\"fields\":[{\"name\":\"issuer\",\"type\":\"account_name\"},{\"name\":\"maximum_supply\",\"type\":\"asset\"}]},{\"name\":\"issue\",\"base\":\"\",\"fields\":[{\"name\":\"to\",\"type\":\"account_name\"},{\"name\":\"quantity\",\"type\":\"asset\"},{\"name\":\"memo\",\"type\":\"string\"}]},{\"name\":\"account\",\"base\":\"\",\"fields\":[{\"name\":\"balance\",\"type\":\"asset\"}]},{\"name\":\"currency_stats\",\"base\":\"\",\"fields\":[{\"name\":\"supply\",\"type\":\"asset\"},{\"name\":\"max_supply\",\"type\":\"asset\"},{\"name\":\"issuer\",\"type\":\"account_name\"}]}],\"actions\":[{\"name\":\"transfer\",\"type\":\"transfer\",\"ricardian_contract\":\"---\\ntitle: Token Transfer\\nsummary: Transfer tokens from one account to another.\\nicon: https://cdn.testnet.dev.b1ops.net/token-transfer.png#ce51ef9f9eeca3434e85507e0ed49e76fff1265422bded0255f3196ea59c8b0c\\n---\\n\\n## Transfer Terms & Conditions\\n\\nI, {{from}}, certify the following to be true to the best of my knowledge:\\n\\n1. I certify that {{quantity}} is not the proceeds of fraudulent or violent activities.\\n2. I certify that, to the best of my knowledge, {{to}} is not supporting initiation of violence against others.\\n3. I have disclosed any contractual terms & conditions with respect to {{quantity}} to {{to}}.\\n\\nI understand that funds transfers are not reversible after the {{$transaction.delay_sec}} seconds or other delay as configured by {{from}}'s permissions.\\n\\nIf this action fails to be irreversibly confirmed after receiving goods or services from '{{to}}', I agree to either return the goods or services or resend {{quantity}} in a timely manner.\"},{\"name\":\"issue\",\"type\":\"issue\",\"ricardian_contract\":\"\"},{\"name\":\"create\",\"type\":\"create\",\"ricardian_contract\":\"\"}],\"tables\":[{\"name\":\"accounts\",\"index_type\":\"i64\",\"key_names\":[\"currency\"],\"key_types\":[\"uint64\"],\"type\":\"account\"},{\"name\":\"stat\",\"index_type\":\"i64\",\"key_names\":[\"currency\"],\"key_types\":[\"uint64\"],\"type\":\"currency_stats\"}],\"ricardian_clauses\":[],\"error_messages\":[],\"abi_extensions\":[],\"variants\":[]}";

    private static final String mockedEosioTransactionSignatureResponseJSON = "{"
            + "\"serializeTransaction\": \"" + MOCKED_TRANSACTION_HEX + "\","
            + "\"signatures\": [\"" + MOCKED_SIGNATURE + "\"]"
            + "}";

    private static final String mockedEosioTransactionSignatureResponseModifiedTransactionJSON = "{"
            + "\"serializeTransaction\": \"" + MOCKED_TRANSACTION_HEX_MODIFIED + "\","
            + "\"signatures\": [\"" + MOCKED_SIGNATURE + "\"]"
            + "}";

    private static final String mockedGetRequiredKeysResponse = "{\n"
            + "    \"required_keys\": [\n"
            + "        \"Key1\"\n"
            + "    ]\n"
            + "}";
}
