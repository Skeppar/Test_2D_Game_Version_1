package objects;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Key extends SuperObject{
    public OBJ_Key() {

        name = "Key";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("objects/Key_1.png")));
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
