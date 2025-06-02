package org.example;

import java.nio.file.Paths;
import java.util.Locale;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageAPI {

    private static final Logger log = LoggerFactory.getLogger(ImageAPI.class);

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
        log.info("Checking OS.....");
        switch (osType) {
            case WINDOWS -> nativeLibraryPath = Config.getProp("pathToNativeLibWin");
            case MACOS -> nativeLibraryPath = Config.getProp("pathToNativeLibMac");
            case LINUX -> nativeLibraryPath = Config.getProp("pathToNativeLibLinux");
            default -> throw new Exception("Unsupported operating system");
        }

        if (nativeLibraryPath == null || nativeLibraryPath.isEmpty()) {
            throw new Exception("Native library path not found in configuration for OS: " + osType);
        }

        // Преобразуем относительный путь к абсолютному
        String absLibPath = Paths.get(nativeLibraryPath).toAbsolutePath().toString();
        System.load(absLibPath);
        log.info("OpenCV library loaded successfully");
    }

    public void applyFilters(String imagePath, int kernelSize) {
        if (imagePath == null || imagePath.isEmpty()) {
            log.error("Image path is null or empty");
            return;
        }

        // Преобразуем относительный путь к абсолютному
        String absImagePath = Paths.get(imagePath).toAbsolutePath().toString();
        Mat src = Imgcodecs.imread(absImagePath);
        if (src.empty()) {
            log.error("Image not found at path: " + absImagePath);
            return;
        }

        Mat dst = new Mat();
        String outputDir = Config.getProp("outputDir1");
        String absOutputDir = Paths.get(outputDir).toAbsolutePath().toString();

        // Усредняющий фильтр
        Imgproc.blur(src, dst, new Size(kernelSize, kernelSize));
        Imgcodecs.imwrite(absOutputDir + "/blur_" + kernelSize + ".jpg", dst);

        // Гауссовский фильтр
        Imgproc.GaussianBlur(src, dst, new Size(kernelSize, kernelSize), 0);
        Imgcodecs.imwrite(absOutputDir + "/gaussian_" + kernelSize + ".jpg", dst);

        // Медианный фильтр
        Imgproc.medianBlur(src, dst, kernelSize);
        Imgcodecs.imwrite(absOutputDir + "/median_" + kernelSize + ".jpg", dst);

        // Двусторонний фильтр
        Imgproc.bilateralFilter(src, dst, kernelSize, kernelSize * 2, kernelSize / 2);
        Imgcodecs.imwrite(absOutputDir + "/bilateral_" + kernelSize + ".jpg", dst);
    }

    public void performMorphology(String imagePath, int[] kernelSizes, int[] morphTypes) {
        if (imagePath == null || imagePath.isEmpty()) {
            log.error("Image path is null or empty");
            return;
        }

        String absImagePath = Paths.get(imagePath).toAbsolutePath().toString();
        Mat src = Imgcodecs.imread(absImagePath);
        if (src.empty()) {
            log.error("Image not found at path: " + absImagePath);
            return;
        }

        String outputDir = Config.getProp("outputDir2");
        String absOutputDir = Paths.get(outputDir).toAbsolutePath().toString();

        for (int size : kernelSizes) {
            for (int type : morphTypes) {
                Mat element = Imgproc.getStructuringElement(type, new Size(size, size));
                Mat dst = new Mat();

                // Морфологическое сужение
                Imgproc.erode(src, dst, element);
                Imgcodecs.imwrite(absOutputDir + "/erode_" + size + "_" + type + ".jpg", dst);

                // Морфологическое расширение
                Imgproc.dilate(src, dst, element);
                Imgcodecs.imwrite(absOutputDir + "/dilate_" + size + "_" + type + ".jpg", dst);
            }
        }
    }
}