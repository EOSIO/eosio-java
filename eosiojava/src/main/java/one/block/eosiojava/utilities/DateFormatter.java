package one.block.eosiojava.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * This class provides utility methods to handle the formatting of dates and times to supported patterns.
 */
public class DateFormatter {

    /**
     * Blockchain pattern for SimpleDateFormat
     */
    public static final String BACKEND_DATE_PATTERN = "yyyy-MM-dd'T'kk:mm:ss.000";

    /**
     * Blockchain pattern for SimpleDateFormat.  It includes timezone.
     */
    public static final String BACKEND_DATE_PATTERN_WITH_TIMEZONE = "yyyy-MM-dd'T'kk:mm:ss.000 zzz";

    /**
     * Blockchain timezone/time standard for SimpleDateFormat
     */
    public static final String BACKEND_DATE_TIME_ZONE = "UTC";

    private DateFormatter() {}

    /**
     * Converting blockchain time to milliseconds
     * <p/>
     * Blockchain time pattern "yyyy-MM-dd'T'HH:mm:ss.sss" in GMT
     * @throws ParseException thrown if the input does not match with any supported datetime pattern.
     */
    public static long convertBackendTimeToMilli(String backendTime) throws ParseException {
        String[] datePatterns = new String[]{
                BACKEND_DATE_PATTERN, BACKEND_DATE_PATTERN_WITH_TIMEZONE
        };

        for (String datePattern : datePatterns) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
                sdf.setTimeZone(TimeZone.getTimeZone(BACKEND_DATE_TIME_ZONE));
                Date parsedDate = sdf.parse(backendTime);
                return parsedDate.getTime();
            } catch (ParseException ex) {
                // Keep going even if exception is thrown for trying different date pattern
            } catch (IllegalArgumentException ex) {
                // Keep going even if exception is thrown for trying different date pattern
            }
        }

        throw new ParseException("Unable to parse input backend time with supported date patterns!", 0);
    }

    /**
     * Convert milliseconds to time string format used on blockchain.
     * <p/>
     * Backend time pattern "yyyy-MM-dd'T'HH:mm:ss.sss" in GMT
     */
    public static String convertMilliSecondToBackendTimeString(long timeInMilliSeconds) {
        SimpleDateFormat sdf = new SimpleDateFormat(BACKEND_DATE_PATTERN);
        sdf.setTimeZone(TimeZone.getTimeZone(BACKEND_DATE_TIME_ZONE));

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMilliSeconds);

        return sdf.format(calendar.getTime());
    }
}
