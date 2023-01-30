package objects;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Key extends SuperObject{

    GamePanel gp;
    public OBJ_Key(GamePanel gp) {

        name = "Key";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("objects/Key_1.png")));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch (IOException e) {
            e.printStackTrace();
        }

        /*
        // Can set the specific solid are for each object like this.
        solidArea.x = 5;
        solidArea.y = 5;
        solidArea.height = 48;
        solidArea.width = 48;
         */
    }
}
