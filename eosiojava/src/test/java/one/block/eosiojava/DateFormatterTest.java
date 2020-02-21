package one.block.eosiojava;

import java.text.ParseException;
import one.block.eosiojava.utilities.DateFormatter;
import org.junit.Assert;
import org.junit.Test;

public class DateFormatterTest {

    @Test
    public void testConvertBackendTimeToMilli() throws ParseException {
        this.convertStrDateToMilliAndVerify("2019-09-11T19:38:05.000");
        this.convertStrDateToMilliAndVerify("2019-01-01T19:38:05.000");
        this.convertStrDateToMilliAndVerify("2019-09-11T00:38:05.000");
        this.convertStrDateToMilliAndVerify("2019-09-11T00:00:00.000");
        this.convertStrDateToMilliAndVerify("2019-01-01T00:00:00.000");
        this.convertStrDateToMilliAndVerify("1900-01-01T00:00:00.000");
    }

    @Test
    public void testIncreaseTime() throws ParseException {
        this.increaseTimeByMilliSecondToStrDateAndVerify("2019-09-11T19:38:05.000", 300 * 1000,
                "2019-09-11T19:43:05.000");
        this.increaseTimeByMilliSecondToStrDateAndVerify("2019-01-01T19:38:05.000", 180 * 1000,
                "2019-01-01T19:41:05.000");
        this.increaseTimeByMilliSecondToStrDateAndVerify("2019-09-11T00:38:05.000", 600 * 1000,
                "2019-09-11T00:48:05.000");
        this.increaseTimeByMilliSecondToStrDateAndVerify("2019-09-11T00:00:00.000", 300 * 1000,
                "2019-09-11T00:05:00.000");
        this.increaseTimeByMilliSecondToStrDateAndVerify("2019-01-01T00:00:00.000", 300 * 1000,
                "2019-01-01T00:05:00.000");
        this.increaseTimeByMilliSecondToStrDateAndVerify("1900-01-01T00:00:00.000", 300 * 1000,
                "1900-01-01T00:05:00.000");
    }

    private void convertStrDateToMilliAndVerify(String strDate) throws ParseException {
        long milliSeconds = DateFormatter.convertBackendTimeToMilli(strDate);
        String convertedStrDate = DateFormatter.convertMilliSecondToBackendTimeString(milliSeconds);
        Assert.assertEquals(strDate, convertedStrDate);
    }

    private void increaseTimeByMilliSecondToStrDateAndVerify(String strDate,
            long appendMilliSeconds, String strDateAfterAppended)
            throws ParseException {
        long milliSeconds = DateFormatter.convertBackendTimeToMilli(strDate);
        milliSeconds += appendMilliSeconds;
        String convertedStrDate = DateFormatter.convertMilliSecondToBackendTimeString(milliSeconds);
        Assert.assertEquals(strDateAfterAppended, convertedStrDate);
    }
}
