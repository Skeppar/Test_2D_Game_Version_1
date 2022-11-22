package objects;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_BlueBoots extends SuperObject{
    public OBJ_BlueBoots() {

        name = "Boots";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("objects/BlueBoot.png")));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
