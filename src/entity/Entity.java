package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Entity {

    GamePanel gp;
    public int worldX, worldY;
    public int speed;
    public BufferedImage still1, still2, up1, up2, dStill, down1, down2, right1, right2, left1, left2;
    public String direction;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);

    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public int actionLockCounter = 0;
    String[] dialogues = new String[20]; // Now we can have up to 20 dialogues.
    int dialogueIndex = 0;

    // Entity always gets instantiated in another class like in the player class.
    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void setAction() {}
    public void speak() {

        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0; // If you finished the conversation this will reset it. Avoids null point and lets you read the conversation again if you missed something.
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex ++;

        // Character will now face the player when talking to them.
        switch (gp.getPlayer().direction) {
            case "up" -> {
                direction = "down";
            }
            case "down" -> {
                direction = "up";
            }
            case "left" -> {
                direction = "right";
            }
            case "right" -> {
                direction = "left";
            }
        }
    }
    public void update() {

        setAction(); // If a subclass has the same method it takes priority, so we don't need anything in setAction here since it takes it from the setAction in NPC_OldMan.

        collisionOn = false;
        gp.getcCheck().checkTile(this);
        gp.getcCheck().checkObject(this, false);
        gp.getcCheck().checkPlayer(this);

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

        spriteCounter++;
        if (spriteCounter > 12) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }
    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        int screenX = worldX - gp.getPlayer().worldX + gp.getPlayer().screenX;
        int screenY = worldY - gp.getPlayer().worldY + gp.getPlayer().screenY;

        if(worldX + gp.tileSize > gp.getPlayer().worldX - gp.getPlayer().screenX &&
                worldX - gp.tileSize < gp.getPlayer().worldX + gp.getPlayer().screenX &&
                worldY + gp.tileSize > gp.getPlayer().worldY - gp.getPlayer().screenY &&
                worldY - gp.tileSize < gp.getPlayer().worldY + gp.getPlayer().screenY) {

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
                default -> image = still1;
            }
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
    public BufferedImage setUp(String imagePath) {

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream( imagePath + ".png")));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
