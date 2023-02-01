package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX; // ScreenX/Y will never change, and since they are set to the middle of the screen, where the character is, the character will always be in the center.
    public final int screenY;
    private int hasKey = 0;


    public int getHasKey() {
        return hasKey;
    }

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.getScreenWidth()/2 - (gp.tileSize/2);
        screenY = gp.getScreenHeight()/2 - (gp.tileSize/2);

        solidArea = new Rectangle(8, 16, 32, 32); // The area the player is solid now starts 8px in from the right and 16 down from the top. And is 32*32 big, which is a smaller box around the body to the feet.
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {

        worldX = gp.tileSize * 10;
        worldY = gp.tileSize * 10;
        speed = 4;
        direction = "still";
    }

    public void getPlayerImage() {

        /*
        // Don't need all of these anymore since we have optimized it.
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

         */

        up1 = setUp("2D_Character_Away_Moving_1");
        up2 = setUp("2D_Character_Away_Moving_2");
        still = setUp("2D_Character_Away_Still");
        down1 = setUp("2D_Character_Front_Moving_1");
        down2 = setUp("2D_Character_Front_Moving_2");
        dStill = setUp("2D_Character_Front_Still");
        right1 = setUp("2D_Character_Right_Moving_1");
        right2 = setUp("2D_Character_Right_Moving_2");
        left1 = setUp("2D_Character_Left_Moving_1");
        left2 = setUp("2D_Character_Left_Moving_2");

    }

    public BufferedImage setUp(String imageName) {

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/" + imageName + ".png")));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
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

            // Check tile collision.
            collisionOn = false;
            gp.getcCheck().checkTile(this); // Pass player class as the entity.

            // Check object collision.
            int objIndex = gp.getcCheck().checkObject(this, true);
            pickUpObject(objIndex);

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

    public void pickUpObject(int i) {

        if (i != 999) {
            String objectName = gp.getObj()[i].getName();

            switch (objectName) {
                case "Key" -> {
                    gp.playSE(1); // Plays sound effect 1, which is named coin.
                    hasKey++;
                    gp.getObj()[i] = null;
                    gp.ui.showMessage("You found a key!"); // Calls the UI method and passes "You found a key!" as a string, which then will be displayed in the UI draw method.
                }
                case "Door" -> {
                    if(hasKey > 0) {
                        gp.playSE(4); // Sound effect 4
                        hasKey--;
                        gp.getObj()[i] = null;
                        System.out.println("Key: " + hasKey);
                        gp.ui.showMessage("You opened the door.");
                    }
                    else {
                        gp.ui.showMessage("You need a key =(");
                    }
                }
                case "Chest" -> {
                    if(hasKey > 0) {
                        hasKey--;
                        gp.ui.gameFinished = true;
                        gp.stopMusic();
                        gp.playSE(2);
                    }
                    else {
                        gp.ui.showMessage("You need a key =(");
                    }
                }
                case "Boots" -> {
                    gp.playSE(3);
                    speed += 1;
                    gp.getObj()[i] = null;
                    gp.ui.showMessage("You found a pair of boots!");
                }
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
        // g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null); // Don't need to draw these every time, same as in TileManager
        g2.drawImage(image, screenX, screenY, null);
    }
}
