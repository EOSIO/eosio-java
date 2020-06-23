package one.block.eosiojava;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.math.BigInteger;
import one.block.eosiojava.models.rpcProvider.request.GetBlockRequest;
import one.block.eosiojava.models.rpcProvider.request.GetRawAbiRequest;
import one.block.eosiojava.models.rpcProvider.request.GetRequiredKeysRequest;
import one.block.eosiojava.models.rpcProvider.request.PushTransactionRequest;
import one.block.eosiojava.models.rpcProvider.response.ActionTrace;
import one.block.eosiojava.models.rpcProvider.response.GetBlockResponse;
import one.block.eosiojava.models.rpcProvider.response.GetInfoResponse;
import one.block.eosiojava.models.rpcProvider.response.GetRawAbiResponse;
import one.block.eosiojava.models.rpcProvider.response.GetRequiredKeysResponse;
import one.block.eosiojava.models.rpcProvider.response.PushTransactionResponse;
import one.block.eosiojava.models.serialization.ActionTraceDeserializer;
import org.junit.Before;
import org.junit.Test;

/**
 * Test JSON serialization/Deserialization for All models in RPC Provider
 */
public class RpcModelTest {

    private Gson gson;

    @Before
    public void setUpGSON() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(ActionTrace.class, new ActionTraceDeserializer())
                .setDateFormat("yyyy-MM-dd'T'hh:mm:ss zzz")
                .disableHtmlEscaping().create();
    }

    //region Request Models

    /**
     * Test GetBlockRequest model
     */
    @Test
    public void GetBlockRequestTest() {
        String jsonContent = "{"
                + "\"block_num_or_id\":\"20430951\""
                + "}";

        // FromJSON test
        GetBlockRequest getBlockRequest = this.gson.fromJson(jsonContent, GetBlockRequest.class);
        assertNotNull(getBlockRequest);
        assertEquals("20430951", getBlockRequest.getBlockNumOrId());

        // ToJSON test
        String toJSON = this.gson.toJson(getBlockRequest);
        assertNotNull(toJSON);
        assertNotEquals("", toJSON);
        assertEquals(jsonContent, toJSON);
    }

    /**
     * Test GetRawAbiRequest
     */
    @Test
    public void GetRawAbiRequestTest() {
        String jsonContent = "{"
                + "\"account_name\":\"eosio.token\""
                + "}";

        // FromJSON test
        GetRawAbiRequest getRawAbiRequest = this.gson.fromJson(jsonContent, GetRawAbiRequest.class);
        assertNotNull(getRawAbiRequest);
        assertEquals("eosio.token", getRawAbiRequest.getAccountName());

        // ToJSON test
        String toJSON = this.gson.toJson(getRawAbiRequest);
        assertNotNull(toJSON);
        assertNotEquals("", toJSON);
        assertEquals(jsonContent, toJSON);
    }

    /**
     * Test GetRequiredKeysRequest
     */
    @Test
    public void GetRequiredKeysRequestTest() {
        String jsonContent = "{\"available_keys\":[\"dummy key\"],\"transaction\":{\"expiration\":\"2019-01-25T22:13:55\",\"ref_block_num\":123456,\"ref_block_prefix\":123456789,\"max_net_usage_words\":0,\"max_cpu_usage_ms\":0,\"delay_sec\":0,\"context_free_actions\":[],\"actions\":[{\"account\":\"eosio.token\",\"name\":\"transfer\",\"authorization\":[{\"actor\":\"dummy_account\",\"permission\":\"active\"}],\"data\":\"hex data\"}],\"transaction_extensions\":[]}}";

        // FromJSON test
        GetRequiredKeysRequest getRequiredKeysRequest = this.gson
                .fromJson(jsonContent, GetRequiredKeysRequest.class);

        // Checking available key field
        assertNotNull(getRequiredKeysRequest);
        assertNotNull(getRequiredKeysRequest.getAvailableKeys());
        assertEquals(1, getRequiredKeysRequest.getAvailableKeys().size());
        assertEquals("dummy key",
                getRequiredKeysRequest.getAvailableKeys().get(0));

        // Checking transaction field
        assertNotNull(getRequiredKeysRequest.getTransaction());
        assertEquals("2019-01-25T22:13:55",
                getRequiredKeysRequest.getTransaction().getExpiration());
        assertEquals(BigInteger.valueOf(123456),
                getRequiredKeysRequest.getTransaction().getRefBlockNum());
        assertEquals(BigInteger.valueOf(123456789),
                getRequiredKeysRequest.getTransaction().getRefBlockPrefix());
        assertEquals(BigInteger.valueOf(0),
                getRequiredKeysRequest.getTransaction().getMaxNetUsageWords());
        assertEquals(BigInteger.valueOf(0),
                getRequiredKeysRequest.getTransaction().getMaxCpuUsageMs());
        assertEquals(BigInteger.valueOf(0), getRequiredKeysRequest.getTransaction().getDelaySec());
        assertNotNull(getRequiredKeysRequest.getTransaction().getContextFreeActions());
        assertEquals(0, getRequiredKeysRequest.getTransaction().getContextFreeActions().size());

        // Checking actions field inside transaction
        assertNotNull(getRequiredKeysRequest.getTransaction().getActions());
        assertEquals(1, getRequiredKeysRequest.getTransaction().getActions().size());
        assertEquals("eosio.token",
                getRequiredKeysRequest.getTransaction().getActions().get(0).getAccount());
        assertEquals("transfer",
                getRequiredKeysRequest.getTransaction().getActions().get(0).getName());

        // Checking authorizations field inside action
        assertNotNull(
                getRequiredKeysRequest.getTransaction().getActions().get(0).getAuthorization());
        assertEquals(1,
                getRequiredKeysRequest.getTransaction().getActions().get(0).getAuthorization()
                        .size());
        assertEquals("dummy_account",
                getRequiredKeysRequest.getTransaction().getActions().get(0).getAuthorization()
                        .get(0).getActor());
        assertEquals("active",
                getRequiredKeysRequest.getTransaction().getActions().get(0).getAuthorization()
                        .get(0).getPermission());

        assertEquals(
                "hex data",
                getRequiredKeysRequest.getTransaction().getActions().get(0).getData());

        assertNotNull(getRequiredKeysRequest.getTransaction().getTransactionExtensions());
        assertEquals(0, getRequiredKeysRequest.getTransaction().getTransactionExtensions().size());

        // ToJSON test
        String toJSON = this.gson.toJson(getRequiredKeysRequest);

        assertNotNull(toJSON);
        assertNotEquals("", toJSON);
        assertEquals(jsonContent, toJSON);
    }

    /**
     * Test PushTransactionRequest
     */
    @Test
    public void PushTransactionRequestTest() {
        String jsonContent = "{\"signatures\":[\"signature in EOS format\"],\"compression\":0,\"packed_context_free_data\":\"\",\"packed_trx\":\"packed transaction\"}";

        // FromJSON test
        PushTransactionRequest pushTransactionRequest = this.gson
                .fromJson(jsonContent, PushTransactionRequest.class);
        assertNotNull(pushTransactionRequest);
        assertEquals(0, pushTransactionRequest.getCompression());
        assertEquals(
                "packed transaction",
                pushTransactionRequest.getPackTrx());
        assertEquals("", pushTransactionRequest.getPackagedContextFreeData());
        assertNotNull(pushTransactionRequest.getSignatures());
        assertEquals(1, pushTransactionRequest.getSignatures().size());
        assertEquals(
                "signature in EOS format",
                pushTransactionRequest.getSignatures().get(0));

        // ToJSON test
        String toJSON = this.gson.toJson(pushTransactionRequest);
        assertNotNull(toJSON);
        assertNotEquals("", toJSON);
        assertEquals(jsonContent, toJSON);
    }

    //endregion

    //region Response models

    /**
     * Test GetBlockResponse
     */
    @Test
    public void GetBlockResponseTest() {
        //region bigString
        String jsonContent = "{\"timestamp\":\"2019-03-25T06:08:05.000\",\"producer\":\"eosnewyorkio\",\"confirmed\":0,\"previous\":\"02f217198bda8b9f88b0d7bd5450ce39c90352a01f896cdb64b8a4c88a9e7f3c\",\"transaction_mroot\":\"405a5daeb4510dc12cb4d90890b44a956178382fe4d80c22a7f80446e5a32d75\",\"action_mroot\":\"d83920d9e596ffc478480ede9a18a7bd8289a2edabc9c39396d1d5f9a2e0a184\",\"schedule_version\":737,\"new_producers\":null,\"header_extensions\":[],\"producer_signature\":\"SIG_K1_KZ3SwH4yW2oYaiRzRy8uVxV9QxZsZYp39hY7DWDjFqLtBp9QjFgKizzAgRgp5y5GkYyhci59SwSVNcGasiW6J6tVZ1zMZx\",\"transactions\":[{\"status\":\"executed\",\"cpu_usage_us\":123,\"net_usage_words\":0,\"trx\":\"dff93d1c318b5a71c85eab9473f2fbc20ddda98c686ac34dc7b28cf6f2e7d531\"},{\"status\":\"executed\",\"cpu_usage_us\":1526,\"net_usage_words\":28,\"trx\":{\"id\":\"0f7719461a2205279a86e30cd4f4eae2d4f5586301f9d00aac8ac59230d3ebe2\",\"signatures\":[\"SIG_K1_K5hVUFB4iVNs8WcvcvMpioM7RBECDRDmAnGN6h9qJvNABWvXn3fzfEkjHhnUL32oxy7T2317hgMUvjGnM56jQPcuLs9utg\"],\"compression\":\"none\",\"packed_context_free_data\":\"\",\"context_free_data\":[],\"packed_trx\":\"7f70985ccb15550e81720000000001309d4c462197b23a0000004044a3b6ba015035bd4c2197b23a00c055fb2aac904b8201f1e7c23e9d45306ebf9f955b311f5898c763a4a680a4852cc8d957c20a28b8ec00204943b014ddc858c7ed40f5fa2f4fc09bda22a76616ffc0f9ba212130fb972f573ce08e9639d6af11366160a713cbec41453683f70c3774a62a31d781d054ae69ceb96bdf5bf59ff13b98d59c8f4d9402cc1f036abc97797d9d92da09a1ac708900\",\"transaction\":{\"expiration\":\"2019-03-25T06:09:03\",\"ref_block_num\":5579,\"ref_block_prefix\":1921060437,\"max_net_usage_words\":0,\"max_cpu_usage_ms\":0,\"delay_sec\":0,\"context_free_actions\":[],\"actions\":[{\"account\":\"betdiceadmin\",\"name\":\"reveal2\",\"authorization\":[{\"actor\":\"betdicegroup\",\"permission\":\"diceserver\"}],\"data\":{\"hashSeedHash\":\"f1e7c23e9d45306ebf9f955b311f5898c763a4a680a4852cc8d957c20a28b8ec\",\"signature\":\"SIG_K1_KeLFYguv3Su8SnnkGZ6jzm27NQ7Ca9nS3c4TZX9UMGmfE4N8jKKgSfK2aZpUy7Yp12R8QKA8opj9hXec76fkCtK6JncKcb\",\"numberHash\":\"ceb96bdf5bf59ff13b98d59c8f4d9402cc1f036abc97797d9d92da09a1ac7089\"},\"hex_data\":\"f1e7c23e9d45306ebf9f955b311f5898c763a4a680a4852cc8d957c20a28b8ec00204943b014ddc858c7ed40f5fa2f4fc09bda22a76616ffc0f9ba212130fb972f573ce08e9639d6af11366160a713cbec41453683f70c3774a62a31d781d054ae69ceb96bdf5bf59ff13b98d59c8f4d9402cc1f036abc97797d9d92da09a1ac7089\"}],\"transaction_extensions\":[]}}}],\"block_extensions\":[],\"id\":\"02f2171af934752f7b39943ddfdfcd55341b02b1818161ae94399ab911052e0a\",\"block_num\":49420058,\"ref_block_prefix\":1033124219}";
        //endregion

        // FromJSON test
        GetBlockResponse getBlockRequest = this.gson.fromJson(jsonContent, GetBlockResponse.class);
        assertNotNull(getBlockRequest);

        assertEquals("2019-03-25T06:08:05.000", getBlockRequest.getTimestamp());
        assertEquals("eosnewyorkio", getBlockRequest.getProducer());
        assertEquals(BigInteger.valueOf(0), getBlockRequest.getConfirmed());
        assertEquals("02f217198bda8b9f88b0d7bd5450ce39c90352a01f896cdb64b8a4c88a9e7f3c",
                getBlockRequest.getPrevious());
        assertEquals("405a5daeb4510dc12cb4d90890b44a956178382fe4d80c22a7f80446e5a32d75",
                getBlockRequest.getTransactionMroot());
        assertEquals("d83920d9e596ffc478480ede9a18a7bd8289a2edabc9c39396d1d5f9a2e0a184",
                getBlockRequest.getActionMroot());
        assertEquals(BigInteger.valueOf(737), getBlockRequest.getScheduleVersion());
        assertNull(getBlockRequest.getNewProducers());
        assertEquals(0, getBlockRequest.getHeaderExtensions().size());
        assertEquals(
                "SIG_K1_KZ3SwH4yW2oYaiRzRy8uVxV9QxZsZYp39hY7DWDjFqLtBp9QjFgKizzAgRgp5y5GkYyhci59SwSVNcGasiW6J6tVZ1zMZx",
                getBlockRequest.getProducerSignature());
        assertEquals(2, getBlockRequest.getTransactions().size());
        assertEquals("dff93d1c318b5a71c85eab9473f2fbc20ddda98c686ac34dc7b28cf6f2e7d531",
                getBlockRequest.getTransactions().get(0).get("trx"));
        assertEquals(0, getBlockRequest.getBlockExtensions().size());
        assertEquals("02f2171af934752f7b39943ddfdfcd55341b02b1818161ae94399ab911052e0a",
                getBlockRequest.getId());
        assertEquals(BigInteger.valueOf(49420058), getBlockRequest.getBlockNum());
        assertEquals(BigInteger.valueOf(1033124219), getBlockRequest.getRefBlockPrefix());

        // ToJSON test
        String toJSON = this.gson.toJson(getBlockRequest);
        assertNotNull(toJSON);
        assertNotEquals("", toJSON);
        // This test does not do verify toJSON with original json content because they are generated different values due to deep level model which is not defined in the source code.
    }

    /**
     * Test GetRawAbiResponse
     */
    @Test
    public void GetRawAbiResponseTest() {
        String jsonContent = "{\"account_name\":\"eosio.token\",\"code_hash\":\"01bd013c4f8be142b9cadf511f007c6ac201c068d529f01ed5661803c575befa\",\"abi_hash\":\"f8f677996a8ca68388bc41cf55e727949c161b624a47e497e3b2f71a0e635dad\",\"abi\":\"DmVvc2lvOjphYmkvMS4wAQxhY2NvdW50X25hbWUEbmFtZQcIdHJhbnNmZXIABARmcm9tDGFjY291bnRfbmFtZQJ0bwxhY2NvdW50X25hbWUIcXVhbnRpdHkFYXNzZXQEbWVtbwZzdHJpbmcGY3JlYXRlAAIGaXNzdWVyDGFjY291bnRfbmFtZQ5tYXhpbXVtX3N1cHBseQVhc3NldAVpc3N1ZQADAnRvDGFjY291bnRfbmFtZQhxdWFudGl0eQVhc3NldARtZW1vBnN0cmluZwZyZXRpcmUAAghxdWFudGl0eQVhc3NldARtZW1vBnN0cmluZwVjbG9zZQACBW93bmVyDGFjY291bnRfbmFtZQZzeW1ib2wGc3ltYm9sB2FjY291bnQAAQdiYWxhbmNlBWFzc2V0DmN1cnJlbmN5X3N0YXRzAAMGc3VwcGx5BWFzc2V0Cm1heF9zdXBwbHkFYXNzZXQGaXNzdWVyDGFjY291bnRfbmFtZQUAAABXLTzNzQh0cmFuc2ZlcucFIyMgVHJhbnNmZXIgVGVybXMgJiBDb25kaXRpb25zCgpJLCB7e2Zyb219fSwgY2VydGlmeSB0aGUgZm9sbG93aW5nIHRvIGJlIHRydWUgdG8gdGhlIGJlc3Qgb2YgbXkga25vd2xlZGdlOgoKMS4gSSBjZXJ0aWZ5IHRoYXQge3txdWFudGl0eX19IGlzIG5vdCB0aGUgcHJvY2VlZHMgb2YgZnJhdWR1bGVudCBvciB2aW9sZW50IGFjdGl2aXRpZXMuCjIuIEkgY2VydGlmeSB0aGF0LCB0byB0aGUgYmVzdCBvZiBteSBrbm93bGVkZ2UsIHt7dG99fSBpcyBub3Qgc3VwcG9ydGluZyBpbml0aWF0aW9uIG9mIHZpb2xlbmNlIGFnYWluc3Qgb3RoZXJzLgozLiBJIGhhdmUgZGlzY2xvc2VkIGFueSBjb250cmFjdHVhbCB0ZXJtcyAmIGNvbmRpdGlvbnMgd2l0aCByZXNwZWN0IHRvIHt7cXVhbnRpdHl9fSB0byB7e3RvfX0uCgpJIHVuZGVyc3RhbmQgdGhhdCBmdW5kcyB0cmFuc2ZlcnMgYXJlIG5vdCByZXZlcnNpYmxlIGFmdGVyIHRoZSB7e3RyYW5zYWN0aW9uLmRlbGF5fX0gc2Vjb25kcyBvciBvdGhlciBkZWxheSBhcyBjb25maWd1cmVkIGJ5IHt7ZnJvbX19J3MgcGVybWlzc2lvbnMuCgpJZiB0aGlzIGFjdGlvbiBmYWlscyB0byBiZSBpcnJldmVyc2libHkgY29uZmlybWVkIGFmdGVyIHJlY2VpdmluZyBnb29kcyBvciBzZXJ2aWNlcyBmcm9tICd7e3RvfX0nLCBJIGFncmVlIHRvIGVpdGhlciByZXR1cm4gdGhlIGdvb2RzIG9yIHNlcnZpY2VzIG9yIHJlc2VuZCB7e3F1YW50aXR5fX0gaW4gYSB0aW1lbHkgbWFubmVyLgoAAAAAAKUxdgVpc3N1ZQAAAAAAqGzURQZjcmVhdGUAAAAAAKjrsroGcmV0aXJlAAAAAAAAhWlEBWNsb3NlAAIAAAA4T00RMgNpNjQBCGN1cnJlbmN5AQZ1aW50NjQHYWNjb3VudAAAAAAAkE3GA2k2NAEIY3VycmVuY3kBBnVpbnQ2NA5jdXJyZW5jeV9zdGF0cwAAAA===\"}";

        // FromJSON test
        GetRawAbiResponse getRawAbiResponse = this.gson
                .fromJson(jsonContent, GetRawAbiResponse.class);
        assertNotNull(getRawAbiResponse);
        assertEquals("eosio.token", getRawAbiResponse.getAccountName());
        assertEquals("01bd013c4f8be142b9cadf511f007c6ac201c068d529f01ed5661803c575befa",
                getRawAbiResponse.getCodeHash());
        assertEquals("f8f677996a8ca68388bc41cf55e727949c161b624a47e497e3b2f71a0e635dad",
                getRawAbiResponse.getAbiHash());
        assertEquals(
                "DmVvc2lvOjphYmkvMS4wAQxhY2NvdW50X25hbWUEbmFtZQcIdHJhbnNmZXIABARmcm9tDGFjY291bnRfbmFtZQJ0bwxhY2NvdW50X25hbWUIcXVhbnRpdHkFYXNzZXQEbWVtbwZzdHJpbmcGY3JlYXRlAAIGaXNzdWVyDGFjY291bnRfbmFtZQ5tYXhpbXVtX3N1cHBseQVhc3NldAVpc3N1ZQADAnRvDGFjY291bnRfbmFtZQhxdWFudGl0eQVhc3NldARtZW1vBnN0cmluZwZyZXRpcmUAAghxdWFudGl0eQVhc3NldARtZW1vBnN0cmluZwVjbG9zZQACBW93bmVyDGFjY291bnRfbmFtZQZzeW1ib2wGc3ltYm9sB2FjY291bnQAAQdiYWxhbmNlBWFzc2V0DmN1cnJlbmN5X3N0YXRzAAMGc3VwcGx5BWFzc2V0Cm1heF9zdXBwbHkFYXNzZXQGaXNzdWVyDGFjY291bnRfbmFtZQUAAABXLTzNzQh0cmFuc2ZlcucFIyMgVHJhbnNmZXIgVGVybXMgJiBDb25kaXRpb25zCgpJLCB7e2Zyb219fSwgY2VydGlmeSB0aGUgZm9sbG93aW5nIHRvIGJlIHRydWUgdG8gdGhlIGJlc3Qgb2YgbXkga25vd2xlZGdlOgoKMS4gSSBjZXJ0aWZ5IHRoYXQge3txdWFudGl0eX19IGlzIG5vdCB0aGUgcHJvY2VlZHMgb2YgZnJhdWR1bGVudCBvciB2aW9sZW50IGFjdGl2aXRpZXMuCjIuIEkgY2VydGlmeSB0aGF0LCB0byB0aGUgYmVzdCBvZiBteSBrbm93bGVkZ2UsIHt7dG99fSBpcyBub3Qgc3VwcG9ydGluZyBpbml0aWF0aW9uIG9mIHZpb2xlbmNlIGFnYWluc3Qgb3RoZXJzLgozLiBJIGhhdmUgZGlzY2xvc2VkIGFueSBjb250cmFjdHVhbCB0ZXJtcyAmIGNvbmRpdGlvbnMgd2l0aCByZXNwZWN0IHRvIHt7cXVhbnRpdHl9fSB0byB7e3RvfX0uCgpJIHVuZGVyc3RhbmQgdGhhdCBmdW5kcyB0cmFuc2ZlcnMgYXJlIG5vdCByZXZlcnNpYmxlIGFmdGVyIHRoZSB7e3RyYW5zYWN0aW9uLmRlbGF5fX0gc2Vjb25kcyBvciBvdGhlciBkZWxheSBhcyBjb25maWd1cmVkIGJ5IHt7ZnJvbX19J3MgcGVybWlzc2lvbnMuCgpJZiB0aGlzIGFjdGlvbiBmYWlscyB0byBiZSBpcnJldmVyc2libHkgY29uZmlybWVkIGFmdGVyIHJlY2VpdmluZyBnb29kcyBvciBzZXJ2aWNlcyBmcm9tICd7e3RvfX0nLCBJIGFncmVlIHRvIGVpdGhlciByZXR1cm4gdGhlIGdvb2RzIG9yIHNlcnZpY2VzIG9yIHJlc2VuZCB7e3F1YW50aXR5fX0gaW4gYSB0aW1lbHkgbWFubmVyLgoAAAAAAKUxdgVpc3N1ZQAAAAAAqGzURQZjcmVhdGUAAAAAAKjrsroGcmV0aXJlAAAAAAAAhWlEBWNsb3NlAAIAAAA4T00RMgNpNjQBCGN1cnJlbmN5AQZ1aW50NjQHYWNjb3VudAAAAAAAkE3GA2k2NAEIY3VycmVuY3kBBnVpbnQ2NA5jdXJyZW5jeV9zdGF0cwAAAA===",
                getRawAbiResponse.getAbi());

        // ToJSON test
        String toJSON = this.gson.toJson(getRawAbiResponse);
        assertNotNull(toJSON);
        assertNotEquals("", toJSON);
        assertEquals(jsonContent, toJSON);
    }

    /**
     * Test GetRequiredKeysResponse
     */
    @Test
    public void GetRequiredKeysResponseTest() {
        String jsonContent = "{\"required_keys\":[\"dummy_key\"]}";

        // FromJSON test
        GetRequiredKeysResponse getRequiredKeysResponse = this.gson
                .fromJson(jsonContent, GetRequiredKeysResponse.class);
        assertNotNull(getRequiredKeysResponse);
        assertNotNull(getRequiredKeysResponse.getRequiredKeys());
        assertEquals(1, getRequiredKeysResponse.getRequiredKeys().size());
        assertEquals("dummy_key", getRequiredKeysResponse.getRequiredKeys().get(0));

        // ToJSON test
        String toJSON = this.gson.toJson(getRequiredKeysResponse);
        assertNotNull(toJSON);
        assertNotEquals("", toJSON);
        assertEquals(jsonContent, toJSON);
    }

    /**
     * Test PushTransactionResponse
     */
    @Test
    public void PushTransactionResponseTest() {
        String jsonContent = "{\"transaction_id\":\"dump_transaction_id\",\"processed\":{\"id\":\"dump_id\",\"block_num\":1231214214,\"block_time\":\"2019-12-12T12:12:12.500\",\"receipt\":{\"status\":\"executed\",\"cpu_usage_us\":1,\"net_usage_words\":1},\"elapsed\":1,\"net_usage\":1,\"scheduled\":false,\"action_traces\":[{\"receipt\":{\"receiver\":\"eosio.assert\",\"act_digest\":\"digest\",\"global_sequence\":1,\"recv_sequence\":1,\"auth_sequence\":[],\"code_sequence\":1,\"abi_sequence\":1},\"act\":{\"account\":\"eosio.assert\",\"name\":\"require\",\"authorization\":[],\"data\":{\"chain_params_hash\":\"hash\",\"manifest_id\":\"manifest id\",\"actions\":[{\"contract\":\"eosio.token\",\"action\":\"transfer\"}],\"abi_hashes\":[\"abi hashes\"]},\"hex_data\":\"hex data\"},\"context_free\":false,\"elapsed\":1,\"cpu_usage\":0,\"console\":\"\",\"total_cpu_usage\":0,\"trx_id\":\"transaction id\",\"block_num\":1,\"block_time\":\"2019-12-12T12:12:12.500\",\"account_ram_deltas\":[],\"inline_traces\":[]},{\"receipt\":{\"receiver\":\"eosio.token\",\"act_digest\":\"digest\",\"global_sequence\":1,\"recv_sequence\":1,\"auth_sequence\":[[\"dummy_account\",1]],\"code_sequence\":1,\"abi_sequence\":1},\"act\":{\"account\":\"eosio.token\",\"name\":\"transfer\",\"authorization\":[{\"actor\":\"dummy_account\",\"permission\":\"active\"}],\"data\":{\"from\":\"dummy_account\",\"to\":\"dummy_account_2\",\"quantity\":\"1.0000 EOS\",\"memo\":\"dummy memo\"},\"hex_data\":\"hex data\"},\"context_free\":false,\"elapsed\":1,\"cpu_usage\":0,\"console\":\"\",\"total_cpu_usage\":0,\"trx_id\":\"transaction id\",\"block_num\":123123123,\"block_time\":\"2019-12-12T12:12:12.500\",\"account_ram_deltas\":[],\"inline_traces\":[{\"receipt\":{\"receiver\":\"dummy_account\",\"act_digest\":\"digest\",\"global_sequence\":1,\"recv_sequence\":1,\"auth_sequence\":[[\"dummy_account\",1]],\"code_sequence\":1,\"abi_sequence\":1},\"act\":{\"account\":\"eosio.token\",\"name\":\"transfer\",\"authorization\":[{\"actor\":\"dummy_account\",\"permission\":\"active\"}],\"data\":{\"from\":\"dummy_account\",\"to\":\"dummy_account_2\",\"quantity\":\"1.0000 EOS\",\"memo\":\"dummy memo\"},\"hex_data\":\"hex data\"},\"context_free\":false,\"elapsed\":1,\"cpu_usage\":0,\"console\":\"\",\"total_cpu_usage\":0,\"trx_id\":\"transaction id\",\"block_num\":1,\"block_time\":\"2019-12-12T12:12:12.500\",\"account_ram_deltas\":[],\"inline_traces\":[]},{\"receipt\":{\"receiver\":\"dummy_account_2\",\"act_digest\":\"digest\",\"global_sequence\":1,\"recv_sequence\":1,\"auth_sequence\":[[\"dummy_account\",111]],\"code_sequence\":1,\"abi_sequence\":1},\"act\":{\"account\":\"eosio.token\",\"name\":\"transfer\",\"authorization\":[{\"actor\":\"dummy_account\",\"permission\":\"active\"}],\"data\":{\"from\":\"dummy_account\",\"to\":\"dummy_account_2\",\"quantity\":\"1.0000 EOS\",\"memo\":\"dummy memo\"},\"hex_data\":\"hex data\"},\"context_free\":false,\"elapsed\":1,\"cpu_usage\":0,\"console\":\"\",\"total_cpu_usage\":0,\"trx_id\":\"transaction id\",\"block_num\":123123,\"block_time\":\"2019-12-12T12:12:12.500\",\"account_ram_deltas\":[],\"inline_traces\":[]}]}]}}";

        // FromJSON test
        PushTransactionResponse pushTransactionResponse = this.gson
                .fromJson(jsonContent, PushTransactionResponse.class);
        assertNotNull(pushTransactionResponse);
        assertEquals("dump_transaction_id", pushTransactionResponse.getTransactionId());
        assertEquals("dump_id", pushTransactionResponse.getProcessed().getId());

        // ToJSON test
        String toJSON = this.gson.toJson(pushTransactionResponse);
        assertNotNull(toJSON);
        assertNotEquals("", toJSON);
        // This test does not do verify toJSON with original json content because they are generated different values due to deep level model which is not defined in the source code.
    }

    @Test
    public void PushTransactionResponseWithReturnValuesTest() {
        String jsonContent = "{\"transaction_id\":\"dump_transaction_id\",\"processed\":{\"id\":\"dump_id\",\"block_num\":1231214214,\"block_time\":\"2019-12-12T12:12:12.500\",\"receipt\":{\"status\":\"executed\",\"cpu_usage_us\":1,\"net_usage_words\":1},\"elapsed\":1,\"net_usage\":1,\"scheduled\":false,\"action_traces\":[{\"receipt\":{\"receiver\":\"eosio.assert\",\"act_digest\":\"digest\",\"global_sequence\":1,\"recv_sequence\":1,\"auth_sequence\":[],\"code_sequence\":1,\"abi_sequence\":1},\"act\":{\"account\":\"eosio.assert\",\"name\":\"require\",\"authorization\":[],\"data\":{\"chain_params_hash\":\"hash\",\"manifest_id\":\"manifest id\",\"actions\":[{\"contract\":\"eosio.token\",\"action\":\"transfer\"}],\"abi_hashes\":[\"abi hashes\"]},\"hex_data\":\"hex data\"},\"context_free\":false,\"elapsed\":1,\"cpu_usage\":0,\"console\":\"\",\"total_cpu_usage\":0,\"trx_id\":\"transaction id\",\"block_num\":1,\"block_time\":\"2019-12-12T12:12:12.500\",\"account_ram_deltas\":[],\"inline_traces\":[], \"return_value\": \"000000000090b1ca\"},{\"receipt\":{\"receiver\":\"eosio.token\",\"act_digest\":\"digest\",\"global_sequence\":1,\"recv_sequence\":1,\"auth_sequence\":[[\"dummy_account\",1]],\"code_sequence\":1,\"abi_sequence\":1},\"act\":{\"account\":\"eosio.token\",\"name\":\"transfer\",\"authorization\":[{\"actor\":\"dummy_account\",\"permission\":\"active\"}],\"data\":{\"from\":\"dummy_account\",\"to\":\"dummy_account_2\",\"quantity\":\"1.0000 EOS\",\"memo\":\"dummy memo\"},\"hex_data\":\"hex data\"},\"context_free\":false,\"elapsed\":1,\"cpu_usage\":0,\"console\":\"\",\"total_cpu_usage\":0,\"trx_id\":\"transaction id\",\"block_num\":123123123,\"block_time\":\"2019-12-12T12:12:12.500\",\"account_ram_deltas\":[],\"inline_traces\":[{\"receipt\":{\"receiver\":\"dummy_account\",\"act_digest\":\"digest\",\"global_sequence\":1,\"recv_sequence\":1,\"auth_sequence\":[[\"dummy_account\",1]],\"code_sequence\":1,\"abi_sequence\":1},\"act\":{\"account\":\"eosio.token\",\"name\":\"transfer\",\"authorization\":[{\"actor\":\"dummy_account\",\"permission\":\"active\"}],\"data\":{\"from\":\"dummy_account\",\"to\":\"dummy_account_2\",\"quantity\":\"1.0000 EOS\",\"memo\":\"dummy memo\"},\"hex_data\":\"hex data\"},\"context_free\":false,\"elapsed\":1,\"cpu_usage\":0,\"console\":\"\",\"total_cpu_usage\":0,\"trx_id\":\"transaction id\",\"block_num\":1,\"block_time\":\"2019-12-12T12:12:12.500\",\"account_ram_deltas\":[],\"inline_traces\":[], \"return_value\": \"000000000090b1ca\"},{\"receipt\":{\"receiver\":\"dummy_account_2\",\"act_digest\":\"digest\",\"global_sequence\":1,\"recv_sequence\":1,\"auth_sequence\":[[\"dummy_account\",111]],\"code_sequence\":1,\"abi_sequence\":1},\"act\":{\"account\":\"eosio.token\",\"name\":\"transfer\",\"authorization\":[{\"actor\":\"dummy_account\",\"permission\":\"active\"}],\"data\":{\"from\":\"dummy_account\",\"to\":\"dummy_account_2\",\"quantity\":\"1.0000 EOS\",\"memo\":\"dummy memo\"},\"hex_data\":\"hex data\"},\"context_free\":false,\"elapsed\":1,\"cpu_usage\":0,\"console\":\"\",\"total_cpu_usage\":0,\"trx_id\":\"transaction id\",\"block_num\":123123,\"block_time\":\"2019-12-12T12:12:12.500\",\"account_ram_deltas\":[],\"inline_traces\":[], \"return_value\": \"000000000090b1ca\"}]}]}}";

        // FromJSON test
        PushTransactionResponse pushTransactionResponse = this.gson
                .fromJson(jsonContent, PushTransactionResponse.class);
        assertNotNull(pushTransactionResponse);
        assertEquals("dump_transaction_id", pushTransactionResponse.getTransactionId());
        assertEquals("dump_id", pushTransactionResponse.getProcessed().getId());

        // ToJSON test
        String toJSON = this.gson.toJson(pushTransactionResponse);
        assertNotNull(toJSON);
        assertNotEquals("", toJSON);
    }

    /**
     * Test GetInfoResponse
     */
    @Test
    public void GetInfoResponseTest() {
        String jsonContent = "{\"server_version\":\"1a14e9cd\",\"chain_id\":\"aca376f206b8fc25a6ed44dbdc66547c36c6c33e3a119ffbeaef943642f0e906\",\"head_block_num\":49675371,\"last_irreversible_block_num\":49675040,\"last_irreversible_block_id\":\"02f5fb2074e613769d56079fefbaa46b8fbfa813f4da1395043e3358ccabbb19\",\"head_block_id\":\"02f5fc6b3a3921573eacbd43615af133b5b09071a74bece17f7f9807db3251aa\",\"head_block_time\":\"2019-03-26T17:36:45.000\",\"head_block_producer\":\"eoscannonchn\",\"virtual_block_cpu_limit\":200000000,\"virtual_block_net_limit\":1048576000,\"block_cpu_limit\":185578,\"block_net_limit\":1046440,\"server_version_string\":\"v1.6.1\"}";

        // FromJSON test
        GetInfoResponse getInfoResponse = this.gson
                .fromJson(jsonContent, GetInfoResponse.class);
        assertNotNull(getInfoResponse);
        assertEquals("1a14e9cd", getInfoResponse.getServerVersion());
        assertEquals("aca376f206b8fc25a6ed44dbdc66547c36c6c33e3a119ffbeaef943642f0e906",
                getInfoResponse.getChainId());
        assertEquals(BigInteger.valueOf(49675371), getInfoResponse.getHeadBlockNum());
        assertEquals(BigInteger.valueOf(49675040), getInfoResponse.getLastIrreversibleBlockNum());
        assertEquals("02f5fb2074e613769d56079fefbaa46b8fbfa813f4da1395043e3358ccabbb19",
                getInfoResponse.getLastIrreversibleBlockId());
        assertEquals("02f5fc6b3a3921573eacbd43615af133b5b09071a74bece17f7f9807db3251aa",
                getInfoResponse.getHeadBlockId());
        assertEquals("2019-03-26T17:36:45.000", getInfoResponse.getHeadBlockTime());
        assertEquals("eoscannonchn", getInfoResponse.getHeadBlockProducer());
        assertEquals(BigInteger.valueOf(200000000), getInfoResponse.getVirtualBlockCpuLimit());
        assertEquals(BigInteger.valueOf(1048576000), getInfoResponse.getVirtualBlockNetLimit());
        assertEquals(BigInteger.valueOf(185578), getInfoResponse.getBlockCpuLimit());
        assertEquals(BigInteger.valueOf(1046440), getInfoResponse.getBlockNetLimit());
        assertEquals("v1.6.1", getInfoResponse.getServerVersionString());

        // ToJSON test
        String toJSON = this.gson.toJson(getInfoResponse);
        assertNotNull(toJSON);
        assertNotEquals("", toJSON);
        assertEquals(jsonContent, toJSON);
    }

    //endregion
}
