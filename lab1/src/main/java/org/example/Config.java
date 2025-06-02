package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

public class Config {
    private static Properties properties = new Properties();

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

    // Метод для получения абсолютного пути относительно корня проекта
    public static String getProp(String key) {
        String value = properties.getProperty(key);
        if (value != null) {
            File file = Paths.get(System.getProperty("user.dir"), value).toFile();
            return file.getAbsolutePath(); // Преобразование в абсолютный путь
        }
        return null;
    }
}

