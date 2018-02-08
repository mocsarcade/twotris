package com.github.capstone.Scene;

import com.github.capstone.Twotris;
import com.github.capstone.Util.Helper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import java.awt.Toolkit;
import java.util.LinkedHashMap;

public class Options extends Scene
{
    private Button back;
    private LinkedHashMap<Button, String> buttons;
    private Scene next;

/**
@Options
This constructor method adds button to a linkedhashmap, added the buttons to the options menu, adds a back button, adjusts the buttons, and then creates the next as the last scene. 
@param lastscene The scene supplied.  
@return none
@throws none
*/
    Options(Scene lastScene)
    {
        buttons = new LinkedHashMap<>();
        Twotris.getInstance().config.addButtonsToOptionsGUI(this);
        back = new Button(256, 64, "Back");
        this.adjustButtons();
        this.next = lastScene;
    }
		/**
@addButton
This method receives buttons and puts them into the options menu.  
@param option The String given. 
@return none
@throws none
*/
    public void addButton(String option)
    {
        this.buttons.put(new Button(256, 64, option), option);
    }
/**
@drawFrame
This method Is able to resize the screen, and is responsible for updating the entities by delta, adjusting fullscreen, the score displayed, and controls when the game is over. 
@param delta
@return isGameOver true/false. 
@throws none
*/
    public boolean drawFrame(float delta)
    {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        GL11.glClearColor(.09F, 0.09F, 0.09F, 0F);
        // Screen resize handler
        if (Display.wasResized())
        {
            GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glLoadIdentity();
            GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
        }
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        updateButtons();
        drawButtons();

        if (Keyboard.isKeyDown(Keyboard.KEY_F2))
        {
            Twotris.getInstance().screenshotManager.takeScreenshot();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_F11))
        {
            if (Display.isFullscreen())
            {
                Twotris.getInstance().setDisplayMode(800, 600, false);
            }
            else
            {
                Twotris.getInstance().setDisplayMode(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height, true);
            }
            Display.setResizable(true);
            GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glLoadIdentity();
            GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
            adjustButtons();
        }

        if (Mouse.isButtonDown(0))
        {
            if (back.isClicked())
            {
                this.reloadFont();
                next.reloadFont();
                return false;
            }
            else
            {
                for (Button b : buttons.keySet())
                {
                    if (b.isClicked())
                    {
                        String prop = buttons.get(b).substring(0, buttons.get(b).indexOf(":"));
                        Twotris.getInstance().config.toggleOption(prop, b);
                    }
                }
            }
        }

        return true;
    }
/**
@updateButtons
This method updates the back button, and the other buttons in the keyset, then adjusts them. 
@param none
@return none
@throws none
*/
    public void updateButtons()
    {
        back.update();
        for (Button b : buttons.keySet())
        {
            b.update();
        }
        adjustButtons();
    }
/**
@drawButtons
This method draws the back button, then all buttons in keyset. 
@param none
@return none
@throws none
*/
    public void drawButtons()
    {
        back.draw();

        for (Button b : buttons.keySet())
        {
            b.draw();
        }
    }
/**
@adjustButtons
This method adjusts the size of the buttons within the keySet. 
@param none
@return none
@throws none
*/
    public void adjustButtons()
    {
        int lastY = 32;
        boolean isLeft = true;
        for (Button b : buttons.keySet())
        {
            int offset = isLeft ? Display.getWidth() / 4 : 3 * (Display.getWidth() / 4);
            int x = offset - (b.getHitBox().getWidth() / 2);
            b.getHitBox().setLocation(x, lastY);
            isLeft = !isLeft;
            if (isLeft)
            {
                lastY += b.getHitBox().getHeight() + 16;
            }
        }
        back.getHitBox().setLocation(16, Display.getHeight() - back.getHitBox().getHeight() - 16);
    }
/**
@nextScene
This method sets the next scene, setting the next selection.  
@param none 
@return next
@throws none
*/
    public Scene nextScene()
    {
        return next;
    }
/**
@reloadFont
This method is used for reloading the buttons’ from the keySet fonts as well as the back button.  
@param none 
@return none
@throws none
*/
    @Override
    public void reloadFont()
    {
        for (Button b : buttons.keySet())
        {
            b.reloadFont();
        }
        back.reloadFont();
    }

}