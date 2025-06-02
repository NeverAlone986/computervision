package org.example;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.opencv.core.Point;
import org.opencv.core.Scalar;

public class ImageAPITest {

    @Test
    public void testOpenCVInitialization() {
        try {
            ImageAPI api = new ImageAPI();
            String os = api.getOperatingSystemType().name();
            String version = org.opencv.core.Core.getVersionString();

            System.out.println("OS version - " + os);
            System.out.println("Open CV version - " + version);

            assertNotNull(version, "OpenCV version should not be null");
            assertFalse(version.isEmpty(), "OpenCV version should not be empty");
        } catch (Exception e) {
            fail("OpenCV initialization failed: " + e.getMessage());
        }
    }

    @Test
    public void testFloodFill() throws Exception {
        ImageAPI api = new ImageAPI();
        api.floodFillImage("Images/1/1.jpg", new Point(0, 0), new Scalar(0, 255, 0), null, null);
        // Проверить результат вручную
    }

    @Test
    public void testPyramidOperations() throws Exception {
        ImageAPI api = new ImageAPI();
        api.pyramidOperations("Images/1/1.jpg", 2, true); // Понижение
        api.pyramidOperations("Images/1/1.jpg", 2, false); // Повышение
        // Проверить результат вручную
    }

    @Test
    public void testIdentifyRectangles() throws Exception {
        ImageAPI api = new ImageAPI();
        api.identifyRectangles("Images/1/1.jpg", 40, 75);
        // Проверить результат вручную
    }
}
