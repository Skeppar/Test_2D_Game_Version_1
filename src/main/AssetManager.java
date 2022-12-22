package main;

import objects.OBJ_BlueBoots;
import objects.OBJ_Chest;
import objects.OBJ_Door;
import objects.OBJ_Key;

public class AssetManager {

    GamePanel gp;

    public AssetManager(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {

        gp.obj[0] = new OBJ_Key();
        gp.obj[0].setWorldX(gp.getTileSize() * 32);
        gp.obj[0].setWorldY(gp.getTileSize() * 50);

        gp.obj[1] = new OBJ_Key();
        gp.obj[1].setWorldX(gp.getTileSize() * 15);
        gp.obj[1].setWorldY(gp.getTileSize() * 45);

        gp.obj[2] = new OBJ_Door();
        gp.obj[2].setWorldX(gp.getTileSize() * 15);
        gp.obj[2].setWorldY(gp.getTileSize() * 66);

        gp.obj[3] = new OBJ_Door();
        gp.obj[3].setWorldX(gp.getTileSize() * 32);
        gp.obj[3].setWorldY(gp.getTileSize() * 23);

        gp.obj[4] = new OBJ_Chest();
        gp.obj[4].setWorldX(gp.getTileSize() * 12);
        gp.obj[4].setWorldY(gp.getTileSize() * 77);

        gp.obj[5] = new OBJ_Chest();
        gp.obj[5].setWorldX(gp.getTileSize() * 10);
        gp.obj[5].setWorldY(gp.getTileSize() * 79);

        gp.obj[6] = new OBJ_BlueBoots();
        gp.obj[6].setWorldX(gp.getTileSize() * 20);
        gp.obj[6].setWorldY(gp.getTileSize() * 46);

    }
}
