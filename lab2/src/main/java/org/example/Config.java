package org.example;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static Properties properties = new Properties();
    private static final String PROJECT_DIR = System.getProperty("user.dir");

    static {
        try {
            // Загрузка ресурса из classpath
            try (var input = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
                if (input == null) {
                    throw new IOException("config.properties not found in resources!");
                }
                properties.load(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для получения значения по ключу
    public static String getProp(String key) {
        String value = properties.getProperty(key);
        if (value != null && (value.startsWith("/") || value.startsWith("./") || value.startsWith("../"))) {
            return new File(PROJECT_DIR, value).getAbsolutePath();
        }
        return value;
    }
}

