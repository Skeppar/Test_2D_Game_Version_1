package tiles;

import main.GamePanel;

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

        try {

            tile[0] = new Tile();
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tile/Grass_1.png")));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tile/Wall_1.png")));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tile/Water_1.png")));
            //tile[2].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tile/Water_1.png")); // Don't have to use requireNonNull but IntelliJ recommends the above way.

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tile/Sand_1.png")));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tile/Dirt_1.png")));

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tile/Tree_1.png")));

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

                    String numbers[] = line.split(" "); // Use regex to split all numbers on space, and put them  in an array.

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

            g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            worldCol++;

            if(worldCol == gp.getMaxWorldCol()) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
