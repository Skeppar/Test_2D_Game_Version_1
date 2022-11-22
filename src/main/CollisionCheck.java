package main;

import entity.Entity;

public class CollisionCheck {

    GamePanel gp;

    public CollisionCheck(GamePanel gp) {

        this.gp = gp;
    }

    public void checkTile(Entity entity) {

        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX/gp.getTileSize();
        int entityRightCol = entityRightWorldX/gp.getTileSize();
        int entityTopRow = entityTopWorldY/gp.getTileSize();
        int entityBottomRow = entityBottomWorldY/gp.getTileSize();

        int tileNum1, tileNum2, tileNum3, tileNum4; // We only need to check 2 tiles for each direction if we only move in 2 dimensions, but since we move diagonally we need an additional 2. Without the diagonal movement the character got stuck in objets, but it works now.

        // Works for now but want to fix the diagonal movement so that instead of getting stuck it moves along the obstacle.
        switch (entity.direction) {
            case "up" -> {
                entityTopRow = (entityTopWorldY - entity.speed)/gp.getTileSize(); // This will find out what tile the player is about to hit.
                tileNum1 = gp.tileM.getMapTileNum()[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.getMapTileNum()[entityRightCol][entityTopRow];
                if(gp.tileM.getTile()[tileNum1].collision || gp.tileM.getTile()[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
            case "upL" -> {
                entityTopRow = (entityTopWorldY - entity.speed)/gp.getTileSize();
                tileNum1 = gp.tileM.getMapTileNum()[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.getMapTileNum()[entityRightCol][entityTopRow];

                entityLeftCol = (entityLeftWorldX - entity.speed)/gp.getTileSize();
                tileNum3 = gp.tileM.getMapTileNum()[entityLeftCol][entityTopRow];
                tileNum4 = gp.tileM.getMapTileNum()[entityLeftCol][entityBottomRow];
                if(gp.tileM.getTile()[tileNum1].collision || gp.tileM.getTile()[tileNum2].collision && gp.tileM.getTile()[tileNum3].collision || gp.tileM.getTile()[tileNum4].collision) {
                    entity.collisionOn = true;
                }
            }
            case "upR" -> {
                entityTopRow = (entityTopWorldY - entity.speed)/gp.getTileSize();
                tileNum1 = gp.tileM.getMapTileNum()[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.getMapTileNum()[entityRightCol][entityTopRow];

                entityRightCol = (entityRightWorldX + entity.speed)/gp.getTileSize();
                tileNum3 = gp.tileM.getMapTileNum()[entityRightCol][entityTopRow];
                tileNum4 = gp.tileM.getMapTileNum()[entityRightCol][entityBottomRow];
                if(gp.tileM.getTile()[tileNum1].collision || gp.tileM.getTile()[tileNum2].collision && gp.tileM.getTile()[tileNum3].collision || gp.tileM.getTile()[tileNum4].collision) {
                    entity.collisionOn = true;
                }
            }
            case "down" -> {
                entityBottomRow = (entityBottomWorldY + entity.speed)/gp.getTileSize();
                tileNum1 = gp.tileM.getMapTileNum()[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.getMapTileNum()[entityRightCol][entityBottomRow];
                if(gp.tileM.getTile()[tileNum1].collision || gp.tileM.getTile()[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
            case "downL" -> {
                entityBottomRow = (entityBottomWorldY + entity.speed)/gp.getTileSize();
                tileNum1 = gp.tileM.getMapTileNum()[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.getMapTileNum()[entityRightCol][entityBottomRow];

                entityLeftCol = (entityLeftWorldX - entity.speed)/gp.getTileSize();
                tileNum3 = gp.tileM.getMapTileNum()[entityLeftCol][entityTopRow];
                tileNum4 = gp.tileM.getMapTileNum()[entityLeftCol][entityBottomRow];
                if(gp.tileM.getTile()[tileNum1].collision || gp.tileM.getTile()[tileNum2].collision && gp.tileM.getTile()[tileNum3].collision || gp.tileM.getTile()[tileNum4].collision) {
                    entity.collisionOn = true;
                }
            }
            case "downR" -> {
                entityBottomRow = (entityBottomWorldY + entity.speed)/gp.getTileSize();
                tileNum1 = gp.tileM.getMapTileNum()[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.getMapTileNum()[entityRightCol][entityBottomRow];

                entityRightCol = (entityRightWorldX + entity.speed)/gp.getTileSize();
                tileNum3 = gp.tileM.getMapTileNum()[entityRightCol][entityTopRow];
                tileNum4 = gp.tileM.getMapTileNum()[entityRightCol][entityBottomRow];
                if(gp.tileM.getTile()[tileNum1].collision || gp.tileM.getTile()[tileNum2].collision && gp.tileM.getTile()[tileNum3].collision || gp.tileM.getTile()[tileNum4].collision) {
                    entity.collisionOn = true;
                }
            }
            case "left" -> {
                entityLeftCol = (entityLeftWorldX - entity.speed)/gp.getTileSize();
                tileNum1 = gp.tileM.getMapTileNum()[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.getMapTileNum()[entityLeftCol][entityBottomRow];
                if(gp.tileM.getTile()[tileNum1].collision || gp.tileM.getTile()[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
            case "right" -> {
                entityRightCol = (entityRightWorldX + entity.speed)/gp.getTileSize();
                tileNum1 = gp.tileM.getMapTileNum()[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.getMapTileNum()[entityRightCol][entityBottomRow];
                if(gp.tileM.getTile()[tileNum1].collision || gp.tileM.getTile()[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
        }
    }

    public int checkObject(Entity entity, boolean player) { // If the player hits an object, we return the index of that object.

        int index = 999;

        for(int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] != null) {
                // Find out the solid area position of the entity.
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // Get the object's solid area.
                gp.obj[i].getSolidArea().x = gp.obj[i].getWorldX() + gp.obj[i].getSolidArea().x;
                gp.obj[i].getSolidArea().y = gp.obj[i].getWorldY() + gp.obj[i].getSolidArea().y;

                switch (entity.direction) {
                    case "upR" -> {
                        entity.solidArea.y -= entity.speed;
                        entity.solidArea.x += entity.speed;
                        if(entity.solidArea.intersects(gp.obj[i].getSolidArea())) { // Rectangle has intersects built in which we use to check if two rectangles collide.
                            if(gp.obj[i].isCollision()) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                    }
                    case "upL" -> {
                        entity.solidArea.y -= entity.speed;
                        entity.solidArea.x -= entity.speed;
                        if(entity.solidArea.intersects(gp.obj[i].getSolidArea())) {
                            if(gp.obj[i].isCollision()) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                    }
                    case "downR" -> {
                        entity.solidArea.y += entity.speed;
                        entity.solidArea.x += entity.speed;
                        if(entity.solidArea.intersects(gp.obj[i].getSolidArea())) {
                            if(gp.obj[i].isCollision()) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                    }
                    case "downL" -> {
                        entity.solidArea.y += entity.speed;
                        entity.solidArea.x -= entity.speed;
                        if(entity.solidArea.intersects(gp.obj[i].getSolidArea())) {
                            if(gp.obj[i].isCollision()) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                    }
                    case "up" -> {
                        entity.solidArea.y -= entity.speed;
                        if(entity.solidArea.intersects(gp.obj[i].getSolidArea())) {
                            if(gp.obj[i].isCollision()) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                    }
                    case "down" -> {
                        entity.solidArea.y += entity.speed;
                        if(entity.solidArea.intersects(gp.obj[i].getSolidArea())) {
                            if(gp.obj[i].isCollision()) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                    }
                    case "left" -> {
                        entity.solidArea.x -= entity.speed;
                        if(entity.solidArea.intersects(gp.obj[i].getSolidArea())) {
                            if(gp.obj[i].isCollision()) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                    }
                    case "right" -> {
                        entity.solidArea.x += entity.speed;
                        if(entity.solidArea.intersects(gp.obj[i].getSolidArea())) {
                            if(gp.obj[i].isCollision()) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                    }
                }
                // Reset the numbers after the switch statement so that it doesn't draw out the object again.
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[i].getSolidArea().x = gp.obj[i].getSolidAreaDefaultX();
                gp.obj[i].getSolidArea().y = gp.obj[i].getSolidAreaDefaultY();
            }
        }

        return index;
    }
}
