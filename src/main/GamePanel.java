package main;

import entity.Player;
import object.SuperObject;
import tile.TileManager;
import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    //SCREEN SETUP

    final int originalTileSize = 16; //16x16
    final int scale = 3;
    public final int tileSize = originalTileSize*scale;// 48 tiles
    public final int MaxScreenCol = 16;
    public final int MaxScreenRow = 12;
    public final int screenWidth = tileSize *MaxScreenCol; //768
    public final int screenHeight = tileSize *MaxScreenRow; //576

    // WORLD SETTINGS

    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    //FPS OF GAME

    int FPS = 60;

    //SYSTEM

    TileManager tileM = new TileManager(this);
    KeyInput KeyH = new KeyInput();
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter asetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread; // for starting the time clock in the game

    //ENTITY AND OBJECTs

     public Player player = new Player(this,KeyH);
     public SuperObject obj[] = new SuperObject[10];

     //GAME STATE
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); //helps with rendering performance
        this.addKeyListener(KeyH);
        this.setFocusable(true); // this helps the game to focus on key inputs

    }
    //SETUP BEFORE GAME LAUNCH
    public void setupGame(){
        asetter.setObject();
        playMusic(0);
        gameState = 1;
    }

// for the time clock of game to start
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }


    // GAME LOOP ----practically the core of this game
    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        double lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;

        while (gameThread != null){
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1){
                update();
                repaint();
                delta --;
            }
        }
    }


    public void update(){

        if(gameState == playState){
            player.update();
        }
        if(gameState == pauseState){
            //nothing
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;// Graphics2D: this extends the "Graphics" class to get more control
        //TILE
        tileM.draw(g2); //layer 0

        //OBJECT
        for (int i = 0; i<obj.length; i++){
            if(obj[i] != null){
                obj[i].draw(g2, this);
            }
        }

        //PLAYER
        player.draw(g2); //layer 1

        //UI
        ui.draw(g2);
        g2.dispose();  // disposes the resources used for the operation, after the operation is done
    }

    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic(){
        music.stop();
    }

    public void playSE(int i){
        se.setFile(i);
        se.play();

    }
}