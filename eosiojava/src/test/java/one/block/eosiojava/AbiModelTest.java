package one.block.eosiojava;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import one.block.eosiojava.models.abiProvider.Abi;
import one.block.eosiojava.models.abiProvider.ActionResult;
import org.junit.Before;
import org.junit.Test;

/**
 * Test JSON serialization/Deserialization for All models in RPC Provider
 */
public class AbiModelTest {

    private Gson gson;

    @Before
    public void setUpGSON() {
        this.gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'hh:mm:ss zzz")
                .disableHtmlEscaping().create();
    }

    //region Request Models

    /**
     * Test Abi
     */
    @Test
    public void AbiTest() {
        String jsonContent = "{\"____comment\":\"Test comment\",\"version\":\"eosio::abi/1.2\",\"types\":[{\"new_type_name\":\"account_name\",\"type\":\"name\"}],\"structs\":[{\"name\":\"transfer\",\"base\":\"\",\"fields\":[{\"name\":\"from\",\"type\":\"account_name\"},{\"name\":\"to\",\"type\":\"account_name\"},{\"name\":\"quantity\",\"type\":\"asset\"},{\"name\":\"memo\",\"type\":\"string\"}]},{\"name\":\"create\",\"base\":\"\",\"fields\":[{\"name\":\"issuer\",\"type\":\"account_name\"},{\"name\":\"maximum_supply\",\"type\":\"asset\"}]},{\"name\":\"issue\",\"base\":\"\",\"fields\":[{\"name\":\"to\",\"type\":\"account_name\"},{\"name\":\"quantity\",\"type\":\"asset\"},{\"name\":\"memo\",\"type\":\"string\"}]},{\"name\":\"account\",\"base\":\"\",\"fields\":[{\"name\":\"balance\",\"type\":\"asset\"}]},{\"name\":\"currency_stats\",\"base\":\"\",\"fields\":[{\"name\":\"supply\",\"type\":\"asset\"},{\"name\":\"max_supply\",\"type\":\"asset\"},{\"name\":\"issuer\",\"type\":\"account_name\"}]},{\"name\":\"retval_test\",\"base\":\"\",\"fields\":[{\"name\":\"user\",\"type\":\"name\"}]},{\"name\":\"returnValue\",\"base\":\"\",\"fields\":[{\"name\":\"id\",\"type\":\"uint32\"},{\"name\":\"name\",\"type\":\"name\"}]}],\"actions\":[{\"name\":\"transfer\",\"type\":\"transfer\",\"ricardian_contract\":\"---\\ntitle: Token Transfer\\nsummary: Transfer tokens from one account to another.\\nicon: https://cdn.testnet.dev.b1ops.net/token-transfer.png#ce51ef9f9eeca3434e85507e0ed49e76fff1265422bded0255f3196ea59c8b0c\\n---\\n\\n## Transfer Terms & Conditions\\n\\nI, {{from}}, certify the following to be true to the best of my knowledge:\\n\\n1. I certify that {{quantity}} is not the proceeds of fraudulent or violent activities.\\n2. I certify that, to the best of my knowledge, {{to}} is not supporting initiation of violence against others.\\n3. I have disclosed any contractual terms & conditions with respect to {{quantity}} to {{to}}.\\n\\nI understand that funds transfers are not reversible after the {{$transaction.delay_sec}} seconds or other delay as configured by {{from}}'s permissions.\\n\\nIf this action fails to be irreversibly confirmed after receiving goods or services from '{{to}}', I agree to either return the goods or services or resend {{quantity}} in a timely manner.\"},{\"name\":\"issue\",\"type\":\"issue\",\"ricardian_contract\":\"\"},{\"name\":\"create\",\"type\":\"create\",\"ricardian_contract\":\"\"},{\"name\":\"retval_test\",\"type\":\"retval_test\",\"ricardian_contract\":\"\"}],\"tables\":[{\"name\":\"accounts\",\"index_type\":\"i64\",\"key_names\":[\"currency\"],\"key_types\":[\"uint64\"],\"type\":\"account\"},{\"name\":\"stat\",\"index_type\":\"i64\",\"key_names\":[\"currency\"],\"key_types\":[\"uint64\"],\"type\":\"currency_stats\"}],\"ricardian_clauses\":[],\"variants\":[],\"action_results\":[{\"name\":\"retval_test\",\"result_type\":\"returnValue\"}]}";

        // FromJSON test
        Abi abi = this.gson
                .fromJson(jsonContent, Abi.class);
        assertNotNull(abi);
        assertNotNull(abi.getComment());
        assertNotNull(abi.getVersion());
        assertNotNull(abi.getTypes());
        assertEquals(1, abi.getTypes().size());
        assertNotNull(abi.getStructs());
        assertEquals(7, abi.getStructs().size());
        assertNotNull(abi.getActions());
        assertEquals(4, abi.getActions().size());
        assertNotNull(abi.getTables());
        assertEquals(2, abi.getTables().size());
        assertNotNull(abi.getRicardianClauses());
        assertEquals(0, abi.getRicardianClauses().size());
        assertNotNull(abi.getVariants());
        assertEquals(0, abi.getVariants().size());
        assertNotNull(abi.getActionResults());
        assertEquals(1, abi.getActionResults().size());

        // ToJSON test
        String toJSON = this.gson.toJson(abi);
        assertNotNull(toJSON);
        assertNotEquals("", toJSON);
        assertEquals(jsonContent, toJSON);
    }

    @Test
    public void AbiQueryItVariantTest() {
        String jsonContent = "{\"version\":\"eosio::abi/1.1\",\"types\":[{\"new_type_name\":\"any_array\",\"type\":\"anyvar[]\"},{\"new_type_name\":\"any_object\",\"type\":\"field[]\"}],\"structs\":[{\"name\":\"null_t\",\"base\":\"\",\"fields\":[]},{\"name\":\"field\",\"base\":\"\",\"fields\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"value\",\"type\":\"anyvar\"}]},{\"name\":\"query\",\"base\":\"\",\"fields\":[{\"name\":\"method\",\"type\":\"string\"},{\"name\":\"arg\",\"type\":\"anyvar?\"},{\"name\":\"filter\",\"type\":\"query[]\"}]}],\"variants\":[{\"name\":\"anyvar\",\"types\":[\"null_t\",\"int64\",\"uint64\",\"int32\",\"uint32\",\"int16\",\"uint16\",\"int8\",\"uint8\",\"time_point\",\"checksum256\",\"float64\",\"string\",\"any_object\",\"any_array\",\"bytes\",\"symbol\",\"symbol_code\",\"asset\"]}]}";

        // FromJSON test
        Abi abi = this.gson
                .fromJson(jsonContent, Abi.class);

        assertEquals(19, abi.getVariantTypesByName("anyvar").size());
    }

    /**
     * Test ActionResult
     */
    @Test
    public void ActionResultTest() {
        String actionName = "test_action";
        String returnType = "testReturnType";
        String jsonContent = "{\"name\":\"" + actionName + "\",\"result_type\":\"" + returnType + "\"}";

        // FromJSON test
        ActionResult actionResult = this.gson
                .fromJson(jsonContent, ActionResult.class);
        assertNotNull(actionResult);
        assertEquals(actionName, actionResult.getName());
        assertTrue(actionResult.hasActionName(actionName));
        assertEquals(returnType, actionResult.getReturnType());

        // ToJSON test
        String toJSON = this.gson.toJson(actionResult);
        assertNotNull(toJSON);
        assertNotEquals("", toJSON);
        assertEquals(jsonContent, toJSON);
    }

    //endregion
}
