package one.block.eosiojava.models;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import one.block.eosiojava.models.rpcProvider.response.ActionTrace;
import org.junit.Before;
import org.junit.Test;

public class ActionTraceTest {
    private Gson gson;

    @Before
    public void setUpGSON() {
        this.gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'hh:mm:ss zzz")
                .disableHtmlEscaping().create();
    }

    @Test
    public void ActionTraceHasReturnValueTest() {
        String jsonContent = "{\"action_ordinal\":\"1\",\"creator_action_ordinal\":\"1\",\"closest_unnotified_ancestor_action_ordinal\":\"1\",\"receiver\":\"test\",\"producer_block_id\":\"test\",\"account_ram_deltas\":{},\"account_disk_deltas\":{},\"except\":\"test\",\"error_code\":\"test\",\"receipt\":{\"receiver\":\"eosio.assert\",\"act_digest\":\"digest\",\"global_sequence\":1,\"recv_sequence\":1,\"auth_sequence\":[],\"code_sequence\":1,\"abi_sequence\":1},\"act\":{\"account\":\"eosio.assert\",\"name\":\"require\",\"authorization\":[],\"data\":{\"chain_params_hash\":\"hash\",\"manifest_id\":\"manifest id\",\"actions\":[{\"contract\":\"eosio.token\",\"action\":\"transfer\"}],\"abi_hashes\":[\"abi hashes\"]},\"hex_data\":\"hex data\"},\"context_free\":false,\"elapsed\":1,\"cpu_usage\":0,\"console\":\"\",\"total_cpu_usage\":0,\"trx_id\":\"transaction id\",\"block_num\":1,\"block_time\":\"2019-12-12T12:12:12.500\",\"account_ram_deltas\":[],\"inline_traces\":[], \"return_value\": \"000000000090b1ca\"}";

        // FromJSON test
        ActionTrace actionTrace = this.gson
                .fromJson(jsonContent, ActionTrace.class);

        assertTrue(actionTrace.hasReturnValue());
    }

    @Test
    public void ActionTraceHasReturnNoValueTest() {
        String jsonContent = "{\"action_ordinal\":\"1\",\"creator_action_ordinal\":\"1\",\"closest_unnotified_ancestor_action_ordinal\":\"1\",\"receiver\":\"test\",\"producer_block_id\":\"test\",\"account_ram_deltas\":{},\"account_disk_deltas\":{},\"except\":\"test\",\"error_code\":\"test\",\"receipt\":{\"receiver\":\"eosio.assert\",\"act_digest\":\"digest\",\"global_sequence\":1,\"recv_sequence\":1,\"auth_sequence\":[],\"code_sequence\":1,\"abi_sequence\":1},\"act\":{\"account\":\"eosio.assert\",\"name\":\"require\",\"authorization\":[],\"data\":{\"chain_params_hash\":\"hash\",\"manifest_id\":\"manifest id\",\"actions\":[{\"contract\":\"eosio.token\",\"action\":\"transfer\"}],\"abi_hashes\":[\"abi hashes\"]},\"hex_data\":\"hex data\"},\"context_free\":false,\"elapsed\":1,\"cpu_usage\":0,\"console\":\"\",\"total_cpu_usage\":0,\"trx_id\":\"transaction id\",\"block_num\":1,\"block_time\":\"2019-12-12T12:12:12.500\",\"account_ram_deltas\":[],\"inline_traces\":[], \"return_value\": \"\"}";

        // FromJSON test
        ActionTrace actionTrace = this.gson
                .fromJson(jsonContent, ActionTrace.class);

        assertFalse(actionTrace.hasReturnValue());
    }

    @Test
    public void ActionTraceIsQueryItActionTest() {
        String queryItActionName = "queryit";
        String jsonContent = "{\"action_ordinal\":\"1\",\"creator_action_ordinal\":\"1\",\"closest_unnotified_ancestor_action_ordinal\":\"1\",\"receiver\":\"test\",\"producer_block_id\":\"test\",\"account_ram_deltas\":{},\"account_disk_deltas\":{},\"except\":\"test\",\"error_code\":\"test\",\"receipt\":{\"receiver\":\"eosio.assert\",\"act_digest\":\"digest\",\"global_sequence\":1,\"recv_sequence\":1,\"auth_sequence\":[],\"code_sequence\":1,\"abi_sequence\":1},\"act\":{\"account\":\"eosio.assert\",\"name\":\"" + queryItActionName + "\",\"authorization\":[],\"data\":{\"chain_params_hash\":\"hash\",\"manifest_id\":\"manifest id\",\"actions\":[{\"contract\":\"eosio.token\",\"action\":\"transfer\"}],\"abi_hashes\":[\"abi hashes\"]},\"hex_data\":\"hex data\"},\"context_free\":false,\"elapsed\":1,\"cpu_usage\":0,\"console\":\"\",\"total_cpu_usage\":0,\"trx_id\":\"transaction id\",\"block_num\":1,\"block_time\":\"2019-12-12T12:12:12.500\",\"account_ram_deltas\":[],\"inline_traces\":[], \"return_value\": \"000000000090b1ca\"}";

        // FromJSON test
        ActionTrace actionTrace = this.gson
                .fromJson(jsonContent, ActionTrace.class);

        assertTrue(actionTrace.isQueryItAction());
    }

    @Test
    public void ActionTraceIsNotQueryItActionTest() {
        String nonQueryItActionName = "someactionname";
        String jsonContent = "{\"action_ordinal\":\"1\",\"creator_action_ordinal\":\"1\",\"closest_unnotified_ancestor_action_ordinal\":\"1\",\"receiver\":\"test\",\"producer_block_id\":\"test\",\"account_ram_deltas\":{},\"account_disk_deltas\":{},\"except\":\"test\",\"error_code\":\"test\",\"receipt\":{\"receiver\":\"eosio.assert\",\"act_digest\":\"digest\",\"global_sequence\":1,\"recv_sequence\":1,\"auth_sequence\":[],\"code_sequence\":1,\"abi_sequence\":1},\"act\":{\"account\":\"eosio.assert\",\"name\":\"" + nonQueryItActionName + "\",\"authorization\":[],\"data\":{\"chain_params_hash\":\"hash\",\"manifest_id\":\"manifest id\",\"actions\":[{\"contract\":\"eosio.token\",\"action\":\"transfer\"}],\"abi_hashes\":[\"abi hashes\"]},\"hex_data\":\"hex data\"},\"context_free\":false,\"elapsed\":1,\"cpu_usage\":0,\"console\":\"\",\"total_cpu_usage\":0,\"trx_id\":\"transaction id\",\"block_num\":1,\"block_time\":\"2019-12-12T12:12:12.500\",\"account_ram_deltas\":[],\"inline_traces\":[], \"return_value\": \"000000000090b1ca\"}";

        // FromJSON test
        ActionTrace actionTrace = this.gson
                .fromJson(jsonContent, ActionTrace.class);

        assertFalse(actionTrace.isQueryItAction());
    }
}
