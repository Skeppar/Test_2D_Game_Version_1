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
        tile = new Tile[50];
        mapTileNum = new int[gp.getMaxWorldCol()][gp.getMaxWorldRow()];
        getTitleImage();
        loadMap("maps/newTileMap.txt");
        // If changing map remember to change maxWorldCol and maxWorldRow in GP!
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
            tile[3].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tile/Road_0.png")));

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


        // Placeholder, start at 10 so the map doesn't look weird in the text file.
        setUp(0, "Grass_0", false);
        setUp(1, "Grass_1", false);
        setUp(2, "Grass_1", false);
        setUp(3, "Grass_1", false);
        setUp(4, "Grass_1", false);
        setUp(5, "Grass_1", false);
        setUp(6, "Grass_1", false);
        setUp(7, "Grass_1", false);
        setUp(8, "Grass_1", false);
        setUp(9, "Grass_1", false);

        // Actual tiles
        setUp(10, "Grass_0", false);
        setUp(11, "Grass_1", false);
        setUp(12, "Road_0", false);
        setUp(13, "Road_1", false);
        setUp(14, "Road_2", false);
        setUp(15, "Road_3", false);
        setUp(16, "Road_4", false);
        setUp(17, "Road_5", false);
        setUp(18, "Road_6", false);
        setUp(19, "Road_7", false);
        setUp(20, "Road_8", false);
        setUp(21, "Road_9", false);
        setUp(22, "Road_10", false);
        setUp(23, "Road_11", false);
        setUp(24, "Road_12", false);
        setUp(25, "Water_0", true);
        setUp(26, "Water_1", true);
        setUp(27, "Water_2", true);
        setUp(28, "Water_3", true);
        setUp(29, "Water_4", true);
        setUp(30, "Water_5", true);
        setUp(31, "Water_6", true);
        setUp(32, "Water_7", true);
        setUp(33, "Water_8", true);
        setUp(34, "Water_9", true);
        setUp(35, "Water_10", true);
        setUp(36, "Water_11", true);
        setUp(37, "Water_12", true);
        setUp(38, "Water_13", true);
        setUp(39, "Water_14", true);
        setUp(40, "Tree_1", true);
        setUp(41, "Dirt_1", false);
        setUp(42, "Wall_1", true);
        setUp(43, "Grass_2", false);
        setUp(44, "Tree_2", true);



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
