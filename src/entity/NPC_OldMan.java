package entity;

import main.GamePanel;

import java.util.Random;

public class NPC_OldMan extends Entity{

    public NPC_OldMan(GamePanel gp) {

        super(gp);

        direction = "down";
        speed = 2;

        getImage();

    }

    public void getImage() {

        up1 = setUp("NPC/oldman_up_1");
        up2 = setUp("NPC/oldman_up_2");
        down1 = setUp("NPC/oldman_down_1");
        down2 = setUp("NPC/oldman_down_2");
        right1 = setUp("NPC/oldman_right_1");
        right2 = setUp("NPC/oldman_right_2");
        left1 = setUp("NPC/oldman_left_1");
        left2 = setUp("NPC/oldman_left_2");
    }

    public void setAction() {

        actionLockCounter ++;

        // actionLockCounter == 120 means the direction of this NPC will be locked for 120 frames, or 2 seconds, before it can pick a new direction.
        if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1; // Gives us a random number between 0-99 and ads 1 so that it's 1-100 instead.

            if (i > 75) {
                direction = "right";
            } else if (i > 50) {
                direction = "left";
            } else if (i > 25) {
                direction = "down";
            } else {
                direction = "up";
            }

            actionLockCounter = 0;
        }
    }
}
