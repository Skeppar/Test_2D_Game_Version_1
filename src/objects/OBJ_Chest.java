package objects;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Chest extends SuperObject{
    public OBJ_Chest() {

        name = "Key";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("objects/Chest_Gold_1.png")));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
