package main;

import entity.NPC_OldMan;
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

        /*
        gp.obj[0] = new OBJ_Key(gp);
        gp.obj[0].setWorldX(gp.getTileSize() * 41);
        gp.obj[0].setWorldY(gp.getTileSize() * 41);

        gp.obj[1] = new OBJ_Key(gp);
        gp.obj[1].setWorldX(gp.getTileSize() * 8);
        gp.obj[1].setWorldY(gp.getTileSize() * 38);

        gp.obj[2] = new OBJ_Door(gp);
        gp.obj[2].setWorldX(gp.getTileSize() * 37);
        gp.obj[2].setWorldY(gp.getTileSize() * 13);

        gp.obj[3] = new OBJ_Chest(gp);
        gp.obj[3].setWorldX(gp.getTileSize() * 37);
        gp.obj[3].setWorldY(gp.getTileSize() * 11);

        gp.obj[4] = new OBJ_BlueBoots(gp);
        gp.obj[4].setWorldX(gp.getTileSize() * 16);
        gp.obj[4].setWorldY(gp.getTileSize() * 28);

         */

    }

    public void setNPC() {

        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.getTileSize()*12;
        gp.npc[0].worldY = gp.getTileSize()*23;
    }
}
