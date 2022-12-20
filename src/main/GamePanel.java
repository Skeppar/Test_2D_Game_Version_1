package main;

import entity.Player;
import objects.OBJ_Key;
import objects.SuperObject;
import tiles.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // Screen settings
    final int originalTileSize = 16; // 16x16 tiles is the default size. This is standard for many old retro games.
    final int scale = 4; // 16 pixels is too small for a modern day screen, but if you multiply it by 3 it will still look the same but be bigger.
    public final int tileSize = originalTileSize * scale; // 48x48 is not the actual size of a tile.
    final int maxScreenCol = 20;
    final int maxScreenRow = 15;
    final int screenWidth = tileSize * maxScreenCol; // 960 pixels.
    final int screenHeight = tileSize * maxScreenRow; // 720 pixels.

    // World settings - If the col and row are just one off it won't load, make sure that the map size is correct!
    final int maxWorldCol = 90;
    final int maxWorldRow = 90;

    /*
    // Not in use.
    final int worldWidth = tileSize * maxWorldCol;
    final int WorldHeight = tileSize * maxWorldRow;
     */

    // FPS
    int FPS = 60;

    // System
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler(); // Instantiate the KeyHandler class and add it to GamePanel so that the GamePanel can recognize the key input.
    Sound sound = new Sound();
    CollisionCheck cCheck = new CollisionCheck(this);
    AssetManager aManager = new AssetManager(this);
    Thread gameThread; // Thread is something you can start and stop, it will keep the program running. This will make the game run even without the player doing anything.


    // Entity and Object
    Player player = new Player(this, keyH);
    SuperObject[] obj = new SuperObject[10];

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // Drawing the things from this component will be done in an offscreen painting buffer. This can improve the rendering performance.
        this.addKeyListener(keyH);
        this.setFocusable(true); // The GamePanel can be "focused" to receive key input.
    }

    public void setUpGame() {

        aManager.setObject();

        playMusic(0); // Plays the theme song.
    }

    public void startGameThread() {

        gameThread = new Thread(this); // This passes the whole GamePanel class into this Thread.
        gameThread.start();
    }

    /*

    // Game loop example 1 that works fine.
    @Override
    public void run() {

        double drawInterval = 1000000000/FPS; // Nanoseconds divided by FPS, so this is 1sec/60.
        double nextDrawTime = System.nanoTime() + drawInterval; // This will draw the next frame in current time + 1/60th of a second, giving us 60 FPS.

        while(gameThread != null) {


            // This method has a game loop which is the core of the game.
            // We want this loop to do 2 things:
            // 1 - Update information such as character position.
            update();

            // 2 - Draw everything on to the screen with the updated information.
            repaint();
            // System.out.println("The game loop is running"); // Test if the loop is working properly

            try {
                double remainingTime = nextDrawTime - System.nanoTime(); // This will check how much time is left until the next frame, and will then make the loop sleep while waiting, so we don't get more than 60 FPS.
                remainingTime = remainingTime/1000000; // Thread.sleep accepts milliseconds, and we are using nanoseconds, so we have to convert it before using it in Thread.sleep.

                if(remainingTime < 0) { // If the update took longer than the remaining time, then it would be less than 0 and wouldn't need to sleep, but that will result in error since it can't sleep - time. THen we just set it to 0 so that it repeats immediately.
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime); // There is a chance this is not as accurate as the delta method, sleep might be a few milliseconds off.

                nextDrawTime += drawInterval; // The next draw time will be set to 1/60th of a second.

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        }
    }
    */

    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS; // Nanoseconds divided by FPS, so this is 1sec/60.
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0; // Used to check FPS
        int drawCount = 0; // Used to check FPS

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval; // Subtract current time from last time to see how much time has passed, then divide and add to delta.
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) { // This 1 is equal to the drawInterval.
                update();
                repaint();
                delta--; // Reset delta.
                drawCount++; // This adds one to the drawCount every time it runs the loop, which should be 60 per second.
            }

            if (timer >= 1000000000) { // Divide with 1b so that we only print this once every second and not every nanosecond.
                System.out.println("FPS: " + drawCount);
                drawCount = 0; // Reset so that it doesn't increase but always shows the frame for the last second.
                timer = 0;
            }
        }
    }

    public void update() {

        player.update();
    }

    public void paintComponent(Graphics g) {
        // paintComponent is a built-in method in Java as a way to draw on JPanel.

        super.paintComponent(g); // Super means the parent class of this class, which is JPanel.

        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2); // This has to be before player so that the player is on top of the tiles.

        for (SuperObject superObject : obj) { // obj.length is the length of the obj array in this class.
            if (superObject != null) { // Check if the obj is null.
                superObject.draw(g2, this);
            }
        }

        player.draw(g2);

        g2.dispose(); // Works without but this saves some memory.

    }

    public void playMusic(int i) {
        // Selects file i from the array of all sound files, then plays and loops it.
        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    public void stopMusic() {

        sound.stop();
    }

    public void playSE(int i) {
        // Sound effect doesn't need to loop since.
        sound.setFile(i);
        sound.play();
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getMaxWorldCol() {
        return maxWorldCol;
    }

    public int getMaxWorldRow() {
        return maxWorldRow;
    }

    public Player getPlayer() {
        return player;
    }

    public CollisionCheck getcCheck() {
        return cCheck;
    }

    public void setObj(SuperObject[] obj) {
        this.obj = obj;
    }

    public SuperObject[] getObj() {
        return obj;
    }
}
