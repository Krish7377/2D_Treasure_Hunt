package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {
    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed;

    public void KeyInput(GamePanel gp){
        this.gp = gp;
    }
    @Override
    public void keyTyped(KeyEvent e){
    }

    @Override
    //when the button is pressed
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W){
            upPressed = true;
        }
        if (code == KeyEvent.VK_S){
            downPressed = true;
        }
        if (code == KeyEvent.VK_A){
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D){
            rightPressed = true;
        }


    }

    @Override
    //when the button is not pressed
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W){
            upPressed = false;
        }
        if (code == KeyEvent.VK_S){
            downPressed = false;
        }
        if (code == KeyEvent.VK_A){
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D){
            rightPressed = false;
        }
    }
}
