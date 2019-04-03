package one.block.eosiojava.utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Utils {

    private Utils() {}

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
            } catch (DateTimeParseException ex) {
                // Keep going even exception is thrown for trying different date pattern
            } catch (DateTimeException ex) {
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

    /**
     * Clone an object
     *
     * @param object input object
     * @param <T> - Class of the object
     */
    public static <T extends Serializable> T clone(T object) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(object);  // Could clone only the Transaction (i.e. this.transaction)
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        return (T) objectInputStream.readObject();
    }

    /**
     * Getting a default GSON
     */
    public static Gson getDefaultGson() {
        return new GsonBuilder()
                .setDateFormat(Constants.BACKEND_DATE_PATTERN)
                .disableHtmlEscaping()
                .create();
    }
}
