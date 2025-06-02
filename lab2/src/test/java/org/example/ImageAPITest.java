package org.example;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.opencv.core.Mat;

import java.io.File;

public class ImageAPITest {

    @Test
    public void testImageProcessing() throws Exception {
        ImageAPI api = new ImageAPI();
        String projectDir = System.getProperty("user.dir");
        String inputPath = new File(projectDir, "Images/image.jpg").getAbsolutePath();
        String outputPath = new File(projectDir, "Images/imagef.jpg").getAbsolutePath();

        Mat image = api.loadImage(inputPath);
        assertNotNull(image, "Image should be loaded successfully");

        api.showImage(image);
        Thread.sleep(3000); // Задержка 3 секунды

        api.zeroChannel(image, 0); // Set the first channel to 0
        api.showImage(image);
        Thread.sleep(3000); // Задержка 3 секунды

        api.saveImage(outputPath, image);
        Mat savedImage = api.loadImage(outputPath);
        assertNotNull(savedImage, "Saved image should be loaded successfully");
    }
}
