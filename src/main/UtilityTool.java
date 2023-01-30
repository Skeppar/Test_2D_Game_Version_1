package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UtilityTool {

    public BufferedImage scaleImage(BufferedImage original, int width, int height) {

        GraphicsConfiguration gc = original.createGraphics().getDeviceConfiguration();
        BufferedImage scaledImage = gc.createCompatibleImage(width, height, BufferedImage.BITMASK);

        //BufferedImage scaledImage = new BufferedImage(width, height, original.getType()); // This doesn't work, have to use the two lines above.

        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0, 0, width, height, null);
        g2.dispose();

        return scaledImage;
    }
}
