package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {
    public static String fetchProperty(String key) {
        Properties properties = new Properties();
        FileInputStream fis = null;

        try {
            fis = new FileInputStream("C:\\Users\\sneza\\Desktop\\template\\src\\test\\java\\resource\\testProperties");
            properties.load(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return properties.getProperty(key) != null ? properties.getProperty(key) : "";
    }
}
