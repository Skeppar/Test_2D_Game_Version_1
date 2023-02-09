package objects;

import main.GamePanel;
import main.UtilityTool;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {

    BufferedImage image;
    String name;
    boolean collision = false;
    int worldX, worldY;
    Rectangle solidArea = new Rectangle(0, 0, 48, 48); // 48*48 is the one full tile.
    int solidAreaDefaultX = 0;
    int solidAreaDefaultY = 0;
    UtilityTool uTool = new UtilityTool();

    public void draw(Graphics2D g2, GamePanel gp) {

        int screenX = worldX - gp.getPlayer().worldX + gp.getPlayer().screenX;
        int screenY = worldY - gp.getPlayer().worldY + gp.getPlayer().screenY;

        if(worldX + gp.tileSize > gp.getPlayer().worldX - gp.getPlayer().screenX &&
                worldX - gp.tileSize < gp.getPlayer().worldX + gp.getPlayer().screenX &&
                worldY + gp.tileSize > gp.getPlayer().worldY - gp.getPlayer().screenY &&
                worldY - gp.tileSize < gp.getPlayer().worldY + gp.getPlayer().screenY) {

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            // g2.drawImage(image, screenX, screenY, gp.tileSize*2, gp.tileSize*2, null); // This would draw all the objects but larger
        }
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }
    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }
    public int getWorldX() {
        return worldX;
    }
    public int getWorldY() {
        return worldY;
    }
    public Rectangle getSolidArea() {
        return solidArea;
    }
    public int getSolidAreaDefaultX() {
        return solidAreaDefaultX;
    }
    public int getSolidAreaDefaultY() {
        return solidAreaDefaultY;
    }
    public boolean isCollision() {
        return collision;
    }
    public String getName() {
        return name;
    }
    public BufferedImage getImage() {
        return image;
    }
}
