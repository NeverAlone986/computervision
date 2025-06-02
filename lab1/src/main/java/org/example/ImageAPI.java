package org.example;

import java.util.Locale;

public class ImageAPI {

    public enum OSType {
        WINDOWS, MACOS, LINUX, OTHER
    }

    public OSType getOperatingSystemType() {
        String os = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
        if ((os.contains("mac")) || (os.contains("darwin"))) {
            return OSType.MACOS;
        } else if (os.contains("win")) {
            return OSType.WINDOWS;
        } else if (os.contains("nux")) {
            return OSType.LINUX;
        } else {
            return OSType.OTHER;
        }
    }

    public ImageAPI() throws Exception {
        OSType osType = getOperatingSystemType();
        String nativeLibraryPath;
        System.out.println("Checking OS.....");
        switch (osType) {
            case WINDOWS -> nativeLibraryPath = Config.getProp("pathToNativeLibWin");
            case MACOS -> nativeLibraryPath = Config.getProp("pathToNativeLibMac");
            case LINUX -> nativeLibraryPath = Config.getProp("pathToNativeLibLinux");
            default -> throw new Exception("Unsupported operating system");
        }

        if (nativeLibraryPath == null || nativeLibraryPath.isEmpty()) {
            throw new Exception("Native library path not found in configuration for OS: " + osType);
        }

        System.load(nativeLibraryPath); // Абсолютный путь уже обработан в Config
        System.out.println("OpenCV library loaded successfully");
    }
}