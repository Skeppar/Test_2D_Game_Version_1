package entity;

import main.GamePanel;

import java.util.Random;

public class NPC_OldMan extends Entity{

    public NPC_OldMan(GamePanel gp) {

        super(gp);

        direction = "down";
        speed = 2;

        getImage();
        setDialogue();

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

    public void setDialogue() {

        dialogues[0] = "Hello adventurer";
        dialogues[1] = "I heard you came to this \nisland in search of treasure.";
        dialogues[2] = "I used to be a wizard you know, but then I got old. \nNow I just help guide others to treasures.";
        dialogues[3] = "I wish you the best of luck.";

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

    public void speak() {

        // The program would work without this method since we have it in Entity, however, if we want to have NPC specific things this makes it possible to customize each NPC.
        super.speak(); // Calls the method from Entity since we might want to use it several times for other NPCs in the future.
    }
}
