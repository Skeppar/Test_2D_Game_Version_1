package objects;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_BlueBoots extends SuperObject{
    public OBJ_BlueBoots() {

        name = "Key";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("objects/BlueBoots_1.png")));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
