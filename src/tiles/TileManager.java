package tiles;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {

    GamePanel gp;
    Tile[] tile;
    int[][] mapTileNum; // Use a 2d array to store all the numbers from the map.

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[10];
        mapTileNum = new int[gp.getMaxWorldCol()][gp.getMaxWorldRow()];
        getTitleImage();
        loadMap("maps/bigTestMap.txt");
    }

    public void getTitleImage() {

        /*
        // This is all works for drawing the images but makes drawing a bit slower.
        try {

            tile[0] = new Tile();
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tile/Grass_1.png")));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tile/Wall_1.png")));
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tile/Water_1.png")));
            //tile[2].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tile/Water_1.png")); // Don't have to use requireNonNull but IntelliJ recommends the above way.
            tile[2].collision = true;

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tile/Sand_1.png")));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tile/Dirt_1.png")));

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tile/Tree_1.png")));
            tile[5].collision = true;

            tile[6] = new Tile();
            tile[6].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tile/Sakura_tree_test.png")));
            tile[6].collision = true;

        } catch (IOException e) {
            e.printStackTrace();
        }

         */

        setUp(0, "Grass_1", false);
        setUp(1, "Wall_1", true);
        setUp(2, "Water_1", true);
        setUp(3, "Sand_1", false);
        setUp(4, "Dirt_1", false);
        setUp(5, "Tree_1", true);
        setUp(6, "Sakura_tree_test", true);

    }

    public void setUp(int index, String imageName, boolean collision) {

        UtilityTool uTool = new UtilityTool();

        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tile/" + imageName + ".png")));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String mapPath) {

        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(mapPath); // Gets teh mapPath from TileManager method further up.
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.getMaxWorldCol() && row < gp.getMaxWorldRow()) { // With gp.getMaxScreenCol() we get the screen size, now we use the map size.

                String line = br.readLine(); // This reads a single line from a text file, which then becomes a string.

                while(col < gp.getMaxWorldRow()) {

                    String[] numbers = line.split(" "); // Use regex to split all numbers on space, and put them  in an array.

                    int num = Integer.parseInt(numbers[col]); // Use col as an index. Change the string above to an int so that we get numbers in int format.

                    mapTileNum[col][row] = num; // Put in al the numbers in the mapTileNum.
                    col++;
                }
                if(col == gp.getMaxWorldCol()) { // If col is max width of the map then it resets col to 0 and the row +1, meaning it begins at a new line.
                    col = 0;
                    row++;
                }
            }
            br.close();

        }catch (Exception e) {

        }
    }
    public void draw(Graphics2D g2) {

        // g2.drawImage(tile[0].image, 0, 0, gp.tileSize, gp.tileSize, null); // Draw out the grass tile

        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.getMaxWorldCol() && worldRow < gp.getMaxWorldRow()) {

            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.getTileSize(); // 0 * 48 now, if the player is on tile col 1 then it will be 1 * 48.
            int worldY = worldRow * gp.getTileSize(); // 0 * 48
            int screenX = worldX - gp.getPlayer().worldX + gp.getPlayer().screenX;
            int screenY = worldY - gp.getPlayer().worldY + gp.getPlayer().screenY;

            if( worldX + gp.tileSize > gp.getPlayer().worldX - gp.getPlayer().screenX && // Only draws the map if that is in the screen. Without this it will load the entire map.
                worldX - gp.tileSize < gp.getPlayer().worldX + gp.getPlayer().screenX && // +/- gp.tileSize is to make sure it doesn't get a black line around the map when moving around. If you move really fast might have to change this to more than one tile size.
                worldY + gp.tileSize > gp.getPlayer().worldY - gp.getPlayer().screenY &&
                worldY - gp.tileSize < gp.getPlayer().worldY + gp.getPlayer().screenY) {

                //g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null); // No longer need to draw tileSize every time.
                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }

            worldCol++;

            if(worldCol == gp.getMaxWorldCol()) {
                worldCol = 0;
                worldRow++;
            }
        }
    }

    public Tile[] getTile() {
        return tile;
    }

    public int[][] getMapTileNum() {
        return mapTileNum;
    }
}
