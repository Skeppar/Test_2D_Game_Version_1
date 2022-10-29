package tiles;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class TileManager {

    GamePanel gp;
    Tile[] tile;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[10];
        getTitleImage();
    }

    public void getTitleImage() {

        try {

            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("/tiles/Grass_1.png)"));
            tile[0].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/2D_Character_Away_Moving_1.png"));

            tile[1] = new Tile();
            //tile[1].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/Wall_1.png)"));

            tile[2] = new Tile();
            //tile[2].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/Water_1.png)"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {

        g2.drawImage(tile[0].image, 0, 0, gp.tileSize, gp.tileSize, null); // Draw out the grass tile
    }
}
