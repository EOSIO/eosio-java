package one.block.eosiojava.utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Utils {

    private Utils() {}

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
