package one.block.eosiojava.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * This class provides utility methods to handle date time objects with supported date time patterns in {@link Constants}
 */
public class DateFormatter {

    private DateFormatter() {}

    /**
     * Converting backend time to millisecond
     * <p/>
     * Backend time pattern "yyyy-MM-dd'T'HH:mm:ss.sss" in GMT
     */
    public static long convertBackendTimeToMilli(String backendTime) throws ParseException {
        String[] datePatterns = new String[]{
                Constants.BACKEND_DATE_PATTERN, Constants.BACKEND_DATE_PATTERN_WITH_TIMEZONE
        };

        for (String datePattern : datePatterns) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
                sdf.setTimeZone(TimeZone.getTimeZone(Constants.BACKEND_DATE_TIME_ZONE));
                Date parsedDate = sdf.parse(backendTime);
                return parsedDate.getTime();
            } catch (ParseException ex) {
                // Keep going even exception is thrown for trying different date pattern
            } catch (IllegalArgumentException ex) {
                // Keep going even exception is thrown for trying different date pattern
            }
        }

        throw new ParseException("Unable to parse input backend time with supported date patterns!", 0);
    }

    /**
     * Convert MilliSeconds to backend time string
     * <p/>
     * Backend time pattern "yyyy-MM-dd'T'HH:mm:ss.sss" in GMT
     */
    public static String convertMilliSecondToBackendTimeString(long timeInMilliSeconds) {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.BACKEND_DATE_PATTERN);
        sdf.setTimeZone(TimeZone.getTimeZone(Constants.BACKEND_DATE_TIME_ZONE));

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMilliSeconds);

        return sdf.format(calendar.getTime());
    }
}
