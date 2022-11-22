package objects;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Door extends  SuperObject{
    public OBJ_Door() {

        name = "Door";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("objects/Door_1.png")));
        }catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }
}
