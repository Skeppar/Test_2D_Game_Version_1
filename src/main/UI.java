package main;

import objects.OBJ_Key;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class UI {

    // This class will handle all the UI like text, item icons etc.

    GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_80B, maruMonica, bellmore;
    // BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;

    public String currentDialogue = "";

    // Not using this anymore, but it was used to display the time it took to finish the game when you had to open the chest.
    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gp) {

        this.gp = gp;

        //arial_40 = new Font("Arial", Font.PLAIN, 40);
        //arial_80B = new Font("Arial", Font.BOLD, 80);
        // OBJ_Key key = new OBJ_Key(gp);
        // keyImage = key.getImage();

        try {
            // Unsupported sfnt means the font is not a TrueType (ttf) font. Even if the file name says ttf it may not be correct.
            InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            assert is != null;
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/BellmoreFree-1GB2M.ttf"); // Some characters don't work properly.
            bellmore = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showMessage(String text) {

        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {

        this.g2 = g2;

        g2.setFont(maruMonica);
        g2.setColor(Color.white);

        // Play state
        if(gp.gameState == gp.playState) {
            // Add this later
        }
        // Pause state
        if(gp.gameState == gp.pauseState) {
            drawPauseScreen();
            gp.stopMusic();
            // Music doesn't play when playing again, fix this later.
        }
        // Dialogue state
        if (gp.gameState == gp.dialogueState) {
            drawDialogueScreen();
        }

        /*
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

            text = "Your time is : " + dFormat.format(playTime) + "!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth(); // This gets the length of the string "text" so that we can display it in the middle of the screen.
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 + gp.tileSize*4;
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
            g2.drawString("Time: " +dFormat.format(playTime), gp.tileSize*11, 65);

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

         */
    }

    public void drawPauseScreen() {

        String text = "Game Paused";
        int x = getXForCenteredText(text); // Made a method for this since I will most likely be using it again in the future.
        int y = gp.screenHeight/2;

        g2.drawString(text, x, y);
    }

    public void drawDialogueScreen() {

        // Dialogue window
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 5;

        drawSubWindow(x, y, width, height);

        // Give x & y new values and draw the text inside the window we made.
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 42F));
        x += gp.tileSize;
        y += gp.tileSize;

        for (String line : currentDialogue.split("\n")) { // Can break the line on any symbol, but used \n since it's what you normally use.
            g2.drawString(line, x, y);
            y += 40;
        }
    }

    public void drawSubWindow(int x, int y, int width, int height) {

        Color c = new Color(0, 0, 0, 200); // Fourth number is the alpha value, which is the opacity.
        g2.setColor(c);
        // g2.setColor(Color.black); // We can write like this, but if we want to be more precise we can create our own color with rgb as seen above.
        g2.fillRoundRect(x, y, width, height, 30, 30); // Rounds the edges of the rectangle.

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5)); // This defines the width of the outlines of graphics rendered with Graphics2D.
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25); // Since this rectangle is smaller the arc W/H has to be a bit smaller, otherwise the corners look weird.
    }

    public int getXForCenteredText(String text) {

        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth/2 - length/2;
    }
}
