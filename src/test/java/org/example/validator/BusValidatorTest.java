package org.example.validator;

import org.example.Validator.BusValidator;
import org.example.domain.Bus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BusValidatorTest {

    private final BusValidator busValidator = new BusValidator();

    @Test
    public void testSuccessfulParcer() {
        String line = "15,Model1,1300.57";

        Bus bus = busValidator.parcer(line);
        Bus busResult = Bus.createInstance(15,"Model1", 1300.57);

        assertEquals(bus, busResult);
    }

    @Test
    public void testInvalidNumberFailedParcer() {
        String line = "d9q7c,Model4,667";

        Bus bus = busValidator.parcer(line);

        assertNull(bus);
    }

    @Test
    public void testNegativeNumberFailedParcer() {
        String line = "-10,Model4,667";

        Bus bus = busValidator.parcer(line);

        assertNull(bus);
    }

    @Test
    public void testInvalidMileageFailedParcer() {
        String line = "100,Model4,667.gh";

        Bus bus = busValidator.parcer(line);

        assertNull(bus);
    }

    @Test
    public void testNegativeMileageFailedParcer() {
        String line = "100,Model4,-667";

        Bus bus = busValidator.parcer(line);

        assertNull(bus);
    }

    @Test
    public void testEmptyModelFailedParcer() {
        String line = "100, ,667";

        Bus bus = busValidator.parcer(line);

        assertNull(bus);
    }


    @Test
    public void testExtraValuesFailedParcer() {
        String line = "100,Model4,667,dd";

        Bus bus = busValidator.parcer(line);

        assertNull(bus);
    }

    @Test
    public void testMissingValuesFailedParcer() {
        String line = "100,667";

        Bus bus = busValidator.parcer(line);

        assertNull(bus);
    }

    @Test
    public void testInvalidLineFailedParcer() {
        String line = null;

        Bus bus = busValidator.parcer(line);

        assertNull(bus);
    }
}
