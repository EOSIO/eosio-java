package one.block.eosiojava.utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * This class provides generic utility methods
 */
public class Utils {

    private Utils() {}

    /**
     * Clone an object
     *
     * @param object input object
     * @param <T> - Class of the object
     * @return the cloned object.
     * @throws IOException Any exception thrown by the underlying OutputStream.
     * @throws ClassNotFoundException Class of a serialized object cannot be found.
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
     * Getting a GSON object with a date time pattern
     * @param datePattern - input date time pattern
     * @return Configured GSON object with input.
     */
    public static Gson getGson(String datePattern) {
        return new GsonBuilder()
                .setDateFormat(datePattern)
                .disableHtmlEscaping()
                .create();
    }
}
