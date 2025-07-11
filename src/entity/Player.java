package entity;

import main.CollisionChecker;
import main.GamePanel;
import main.KeyInput;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{

    GamePanel gp;
    KeyInput KeyH;

    public final int screenX;
    public final int screenY;
    public int hasKey = 0;

    public Player(GamePanel gp, KeyInput KeyH){
        this.gp = gp;
        this.KeyH = KeyH;

        screenX = gp.screenWidth/2 -(gp.tileSize/2);
        screenY = gp.screenHeight/2-(gp.tileSize/2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;
        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues(){
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage(){
        try {
            up1= ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
            up2= ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
            down1= ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
            down2= ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
            left1= ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
            left2= ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
            right1= ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
            right2= ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }


    }

    public void update(){

        if (KeyH.upPressed == true || KeyH.downPressed == true || KeyH.leftPressed == true
                || KeyH.rightPressed == true){
            if(KeyH.upPressed == true){
                direction ="up";
            }

            else if(KeyH.downPressed == true){
                direction ="down";
            }

            else if(KeyH.leftPressed == true){
                direction ="left";
            }

            else if(KeyH.rightPressed == true){
                direction ="right";
            }

            //CHECK TILE COLLISION or if it's hitting something or not
            collisionOn = false;
            gp.cChecker.checkTile(this);

            //CHECK OBJECT COLLISION

            int objIndex = gp.cChecker.checkObject(this,true);
            pickUpObject(objIndex);

            //IF COLLISION IS FASLE PLAYER CAN MOVE
            if(collisionOn == false){

                switch (direction){
                    case "up":worldY -= speed; break;

                    case "down":worldY += speed; break;

                    case "left":worldX -= speed; break;

                    case "right": worldX += speed; break;


                }
            }
            spriteCounter++;
            if(spriteCounter > 12){
                if (spriteNum == 1){
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
        else{
             spriteNum = 1;
        }

    }
     //PICK UP TIEMS
    public void pickUpObject(int i){
        if(i != 999){
           String objectName = gp.obj[i].name;

           switch (objectName){
               case "Key":
                   gp.playSE(1);
                   hasKey++;
                   gp.obj[i] = null;
                   gp.ui.showMessage("You got a Key!!");
                   break;

               case "Door":
                   gp.playSE(3);
                   if (hasKey > 0){
                       gp.obj[i] = null;
                       hasKey--;
                       gp.ui.showMessage("Door is Opened!!");
                   }else {
                       gp.ui.showMessage("Need Key?");
                   }
                   break;
               case "Boots":
                   gp.playSE(2);
                   speed += 3;
                   gp.obj[i] = null;
                   gp.ui.showMessage("SPEED!!");
                   break;
               case "Chest":
                   gp.ui.gameFinished = true;
                   gp.stopMusic();
                   gp.playSE(4);
                   break;

           }
        }
    }

    public void draw(Graphics2D g2){

        BufferedImage image = null;

        switch (direction){
            case "up":
                if (spriteNum == 1){
                    image = up1;
                }
                if (spriteNum == 2){
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1){
                    image = down1;
                }
                if (spriteNum == 2){
                    image = down2;
                }
                break;
            case "left":
                if (spriteNum == 1){
                    image = left1;
                }
                if (spriteNum == 2){
                    image = left2;
                }
                break;
            case "right":
                if (spriteNum == 1){
                    image = right1;
                }
                if (spriteNum == 2){
                    image = right2;
                }
                break;
        }

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
