package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX; // ScreenX/Y will never change, and since they are set tp the middle of the screen, where the character is, the character will always be in the center.
    public final int screenY;


    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.getScreenWidth()/2 - (gp.tileSize/2);
        screenY = gp.getScreenHeight()/2 - (gp.tileSize/2);

        solidArea = new Rectangle(8, 16, 32, 32); // The area the player is solid now starts 8px in from the right and 16 down from the top. And is 32*32 big, which is a smaller box around the body to the feet.

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {

        worldX = gp.tileSize * 32;
        worldY = gp.tileSize * 46;
        speed = 4;
        direction = "still";
    }

    public void getPlayerImage() {

        try {

            // up1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/2D_Character_Away_Moving_1.png")); // IntelliJ recommends requireNonNull. It doesn't make a difference in functionality, since either way if it's null it won't work.
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/2D_Character_Away_Moving_1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/2D_Character_Away_Moving_2.png")));
            still = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/2D_Character_Away_Still.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/2D_Character_Front_Moving_1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/2D_Character_Front_Moving_2.png")));
            dStill = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/2D_Character_Front_Still.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/2D_Character_Right_Moving_1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/2D_Character_Right_Moving_2.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/2D_Character_Left_Moving_1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/2D_Character_Left_Moving_2.png")));

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        // First we check the direction, after this we check the collision.
        if(keyH.upPressed || keyH.downPressed || keyH.rightPressed || keyH.leftPressed) {

            if (keyH.upPressed && keyH.rightPressed) {
                direction = "upR";
            } else if(keyH.upPressed && keyH.leftPressed){
                direction = "upL";
            } else if (keyH.downPressed && keyH.rightPressed) {
                direction = "downR";
            } else if(keyH.downPressed && keyH.leftPressed){
                direction = "downL";
            } else if (keyH.upPressed) {
                    direction = "up";
            } else if (keyH.rightPressed) {
                direction = "right";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else {
                direction = "down";
            }

            // Check tile collision
            collisionOn = false;
            gp.getcCheck().checkTile(this); // Pass player class as the entity.

            // If collision is false player can move.
            if(!collisionOn) {
                switch (direction) {
                    case "upR" -> {
                        worldY -= speed;
                        worldX += speed;
                    }
                    case "upL" -> {
                        worldY -= speed;
                        worldX -= speed;
                    }
                    case "downR" -> {
                        worldY += speed;
                        worldX += speed;
                    }
                    case "downL" -> {
                        worldY += speed;
                        worldX -= speed;
                    }
                    case "up" -> {
                        worldY -= speed;
                    }
                    case "down" -> {
                        worldY += speed;
                    }
                    case "left" -> {
                        worldX -= speed;
                    }
                    case "right" -> {
                        worldX += speed;
                    }
                }
            }

            // If you want more animations for walking, just add more numbers after 2. Change here and in draw.
            spriteCounter++;
            if (spriteCounter > 12) { // This changes the animation every 12 frames, which is 5 times per second.
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2) {

        /*
        g2.setColor(Color.white);
        g2.fillRect(x, y, gp.tileSize, gp.tileSize); // gp is GamePanel, remember to set tileSize public.
        // Remember to use playerX/Y and tileSize and not numbers, if you want to change the map or character size later you'd have to change it in several places.
        // If you use number the player won't be able to change position. That will be done in the update method.
         */

        BufferedImage image = null;

        // If you want more animations for walking, just add more numbers after 2. Change here and in update.
        // Add diagonal animations later.
        switch (direction) {
            case "up", "upR", "upL" -> {
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
            }
            case "down", "downR", "downL" -> {
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
            }
            case "left" -> {
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
            }
            case "right" -> {
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
            }
            default -> image = still;
        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
