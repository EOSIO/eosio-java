package one.block.eosiojava.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import one.block.eosiojava.models.abiProvider.ActionResult;
import org.junit.Before;
import org.junit.Test;

public class ActionResultTest {

    private Gson gson;

    @Before
    public void setUpGSON() {
        this.gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'hh:mm:ss zzz")
                .disableHtmlEscaping().create();
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
}
