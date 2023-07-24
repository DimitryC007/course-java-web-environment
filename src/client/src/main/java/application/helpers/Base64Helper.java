package application.helpers;

import java.io.*;
import java.util.Base64;

public class Base64Helper {
    public static String objectToBase64(Object obj) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {

            objectOutputStream.writeObject(obj);
            byte[] objectBytes = byteArrayOutputStream.toByteArray();

            return Base64.getEncoder().encodeToString(objectBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T base64ToObject(String base64) {
        try {
            byte[] objectBytes = Base64.getDecoder().decode(base64);
            try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(objectBytes);
                 ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {

                return (T) objectInputStream.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
