package component.value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import network.Transput;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TransputValueHelperTest {
    private Map<TransputValue, TransputValue> valid = new HashMap<>();
    private Map<TransputValue, TransputValue> invalid = new HashMap<>();

    @Before
    public void setUp() throws Exception {
        valid.put(new TransputValue(null, 0.0, 1.0), new TransputValue(null, 0.0, 1.0));
        valid.put(new TransputValue("value", 0.0, 1.0), new TransputValue("value", 0.0, 1.0));

        invalid.put(new TransputValue(null, 0.0, 1.0), new TransputValue("value", 0.0, 1.0));
        invalid.put(new TransputValue("otherValue", 0.0, 1.0), new TransputValue("value", 0.0, 1.0));
        invalid.put(new TransputValue("value", 0.0, 1.0), new TransputValue("value", 0.0, 10.0));
        invalid.put(new TransputValue("value", 0.0, 1.0), new TransputValue("value", 10.0, 1.0));
    }

    @Test
    public void sameValuesDefinition(){
        Assert.assertTrue(TransputValueHelper.sameValuesDefinition(new ArrayList<>(valid.keySet()), new ArrayList<>(valid.values())));
        Assert.assertFalse(TransputValueHelper.sameValuesDefinition(new ArrayList<>(invalid.keySet()), new ArrayList<>(invalid.values())));
    }
}