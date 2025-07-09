package main;

import object.OBJ_key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI{

    GamePanel gp;
    Graphics2D g2;
    Font montserrat_40,montserrat_80;
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public double playTime= 0;
    DecimalFormat dformat = new DecimalFormat("#0.00");

    public UI(GamePanel gp){
        this.gp = gp;

        montserrat_40 = new Font("Montserrat Bold", Font.BOLD, 40);
        montserrat_80 = new Font("Montserrat Bold", Font.BOLD, 65);
        OBJ_key key = new OBJ_key();
        keyImage = key.image;
    }

    public void showMessage(String text){
        message = text;
        messageOn = true;
    }
    public void draw(Graphics2D g2){


        if (gameFinished == true){

            g2.setFont(montserrat_40);
            g2.setColor(Color.white);

            String text;
            int textLength;
            int x;
            int y;

            text = "YOU FOUND THE TREASURE!!!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 -(gp.tileSize*3);
            g2.drawString(text, x, y);


            text = "Your Time: " + dformat.format(playTime)+ "secs !";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 +(gp.tileSize*4);
            g2.drawString(text, x, y);


            g2.setFont(montserrat_80);
            g2.setColor(Color.yellow);
            text = "CONGRATULATIONS!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 +(gp.tileSize*2);
            g2.drawString(text, x, y);

            gp.gameThread = null;


        }else{
            g2.setFont(montserrat_40);
            g2.setColor(Color.white);
            g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
            g2.drawString("x "+gp.player.hasKey, 70, 65);

            //TIMER

            playTime +=(double)1/60;
            g2.drawString("Time: "+ dformat.format(playTime), gp.tileSize* 11, 65);

            //MESSAGES LIKE OBTAINING SOMETHING
            if(messageOn == true){
                g2.drawString(message,gp.tileSize/2, gp.tileSize*5);

                messageCounter++;

                if(messageCounter > 120){
                    messageCounter = 0;
                    messageOn = false;
                }
            }
        }
    }
//    public void drawPauseScreen(){
//        String text = "PAUSED";
//        int x= getXforCenteredText(text);
//        int y = gp.screenHeight/2;
//        g2.drawString(text, x, y);
//    }

    public int getXforCenteredText(String text){
        int length =(int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }
}
