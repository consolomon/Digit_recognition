package recognition;

import java.io.*;
import java.util.Scanner;

public class SerializationUtils {

    public static Scanner read(String pathname) throws IOException {
        File file = new File(pathname);
        return new Scanner(file);
    }

    public static void serialize(Object obj, String fileName) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        ObjectOutputStream oos = new ObjectOutputStream(bos);

        oos.writeObject(obj);
        oos.close();
    }

    public static Object deserialize(String fileName) throws IOException, ClassNotFoundException {

        FileInputStream fis = new FileInputStream(fileName);
        BufferedInputStream bis = new BufferedInputStream(fis);
        ObjectInputStream ois = new ObjectInputStream(bis);

        Object obj = ois.readObject();
        ois.close();

        return obj;
    }


}
