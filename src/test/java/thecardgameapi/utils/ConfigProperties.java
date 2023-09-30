package thecardgameapi.utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigProperties {
    static Properties prop;

    public static void initConfig(){
        prop = new Properties();
        try {
            prop.load(new FileReader("src/test/resources/config.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getProperties(String key){
        return prop.getProperty(key);
    }
}
//rc/test/resources/config.properties