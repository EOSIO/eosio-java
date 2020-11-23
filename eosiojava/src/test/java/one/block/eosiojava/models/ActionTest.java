package one.block.eosiojava.models;

import java.util.ArrayList;
import one.block.eosiojava.models.rpcProvider.Action;
import one.block.eosiojava.models.rpcProvider.Authorization;
import org.junit.Test;
import static junit.framework.TestCase.*;

public class ActionTest {
    Action action;

    public void setup() {
        action = new Action("Account", "Name", new ArrayList<Authorization>(), "{\"data\": \"test\"}");
    }

    public void setup(boolean isContextFree) {
        action = new Action("Account", "Name", new ArrayList<Authorization>(), "{\"data\": \"test\"}", isContextFree);
    }

    @Test
    public void testCreateActionWithDefaultConstructor() {
        this.setup();
        assertFalse(action.getIsContextFree());
    }

    @Test
    public void testCreateActionWithOverloadedConstructor() {
        this.setup(false);
        assertFalse(action.getIsContextFree());
    }

    @Test
    public void testCreateContextFreeActionWithOverloadedConstructor() {
        this.setup(true);
        assertTrue(action.getIsContextFree());
    }

    @Test
    public void testSetIsContextFree() {
        this.setup();
        action.setIsContextFree(true);

        assertTrue(action.getIsContextFree());
    }
}
