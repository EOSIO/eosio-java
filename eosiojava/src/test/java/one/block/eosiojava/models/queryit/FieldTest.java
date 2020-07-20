package one.block.eosiojava.models.queryit;

import static junit.framework.TestCase.*;

import org.junit.Before;
import org.junit.Test;

public class FieldTest {
    private Field field;

    @Before
    public void setup() {
        field = new Field();
    }

    @Test
    public void testHasValueWithoutValue() {
        assertFalse(field.hasValue());
    }

    @Test
    public void testHasValue() {
        field.setValue(new AnyVar());
        assertTrue(field.hasValue());
    }

    @Test
    public void testHasTypeWithoutValue() {
        assertFalse(field.hasType());
    }

    @Test
    public void testHasType() {
        String type = "type";
        setType(type);

        assertTrue(field.hasType());
    }

    @Test
    public void testHasTypeWithParamWithoutValue() {
        String typeToCheck = "type";
        assertFalse(field.hasType(typeToCheck));
    }

    @Test
    public void testHasTypeWithParamWithoutCorrectValue() {
        String typeToCheck = "type";
        setType("othertype");
        assertFalse(field.hasType(typeToCheck));
    }

    @Test
    public void testHasTypeWithParam() {
        String typeToCheck = "type";
        setType(typeToCheck);
        assertTrue(field.hasType(typeToCheck));
    }

    @Test
    public void testGetTypeWithoutValue() {
        assertNull(field.getType());
    }

    @Test
    public void testGetType() {
        String type = "type";
        setType(type);

        assertEquals(type, field.getType());
    }

    @Test
    public void testGetAnyVarValueWithoutValue() {
        assertNull(field.getAnyVarValue());
    }

    @Test
    public void testGetAnyVarValue() {
        Object test = "test";
        setValue(test);
        assertEquals(test, field.getAnyVarValue());
    }

    @Test
    public void testHasPrimitiveValueWithoutValue() {
        assertFalse(field.hasPrimitiveValue());
    }

    @Test
    public void testHasPrimitiveValue() {
        Object test = "test";
        setValue(test);
        assertTrue(field.hasPrimitiveValue());
    }

    private void setType(String type) {
        AnyVar anyVar = new AnyVar();
        anyVar.setType(type);
        field.setValue(anyVar);
    }

    private void setValue(Object value) {
        AnyVar anyVar = new AnyVar();
        anyVar.setValue(value);
        field.setValue(anyVar);
    }
}
