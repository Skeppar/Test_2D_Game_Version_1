package main;

import objects.OBJ_Key;
import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {

    // This class will handle all the UI like text, item icons etc.

    GamePanel gp;
    Font arial_40;
    BufferedImage keyImage;

    public UI(GamePanel gp) {

        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        OBJ_Key key = new OBJ_Key();
        keyImage = key.getImage();
    }

    public void draw(Graphics2D g2) {

        // g2.setFont(new Font("Arial", Font.PLAIN, 40)); // This works but since it will render this 60 times per second it is more efficient to the next line instead.
        g2.setFont(arial_40);
        g2.setColor(Color.white);
        g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
        g2.drawString("x  " + gp.player.getHasKey(), 95, 70);
    }
}
