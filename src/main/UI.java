package main;

import objects.OBJ_Key;
import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {

    // This class will handle all the UI like text, item icons etc.

    GamePanel gp;
    Font arial_40, arial_80B;
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    double playTime;

    public UI(GamePanel gp) {

        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
        OBJ_Key key = new OBJ_Key();
        keyImage = key.getImage();
    }

    public void showMessage(String text) {

        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {

        if (gameFinished) {

            g2.setFont(arial_40);
            g2.setColor(Color.white);

            String text;
            int textLength;
            int x;
            int y;

            text = "You found the treasure!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth(); // This gets the length of the string "text" so that we can display it in the middle of the screen.
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 - gp.tileSize*3;
            g2.drawString(text, x, y);

            g2.setFont(arial_80B);
            g2.setColor(Color.yellow);
            text = "Congratulations!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 + gp.tileSize*2;
            g2.drawString(text, x, y);

            gp.gameThread = null;

        } else {

            // g2.setFont(new Font("Arial", Font.PLAIN, 40)); // This works but since it will render this 60 times per second it is more efficient to the next line instead.
            g2.setFont(arial_40);
            g2.setColor(Color.white);
            g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
            g2.drawString("x  " + gp.player.getHasKey(), 95, 70);

            // Time
            playTime +=(double)1/60;
            g2.drawString("Time: " + playTime, gp.tileSize*11, 65);

            // On screen message.
            if (messageOn) {

                g2.setFont(g2.getFont().deriveFont(30F)); // Gets the font from above (40) and sets it to 30. This accepts float which is what the F is for.
                g2.drawString(message, gp.tileSize / 2, gp.tileSize * 3);

                messageCounter++;

                if (messageCounter > 120) { // 120 frames is 2 sec, then it will reset the counter and stop displaying the message.
                    messageCounter = 0;
                    messageOn = false;
                }
            }
        }
    }
}
