package com.github.capstone.Scene;

import com.github.capstone.Manager.AudioManager;
import com.github.capstone.Twotris;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.LinkedHashMap;

public class Menu extends Scene
{
    private LinkedHashMap<Button, Scene> buttons;
    private Scene nextScene;
    private TitleSprite titleSprite;

    /**
     * @param title The String include for the title.
     * @return none
     * @throws none
     * @Menu This constructor method adds button to a linkedhashmap and creates a titlesprite.
     */
    public Menu(String title)
    {
        buttons = new LinkedHashMap<>();
        titleSprite = new TitleSprite(title);
        try
        {
            Mouse.setNativeCursor(null);
        }
        catch (LWJGLException ignored)
        {
        }
    }

    /**
     * @param button A button given to the method
     * @param Scene  the scene the button is to be placed inside of.
     * @return none
     * @throws none
     * @addButton This method receives a button and a scene, putting the button into the scene.
     */
    public void addButton(Button button, Scene scene)
    {
        buttons.put(button, scene);
    }

    /**
     * @param delta
     * @return isButtonDown true/false.
     * @throws none
     * @drawFrame This method Is able to resize the screen, and is responsible for updating the entities by delta, adjusting fullscreen, the score displayed, and controls when the game is over.
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
        titleSprite.draw();
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
            for (Button b : buttons.keySet())
            {
                if (b.isClicked())
                {
                    nextScene = buttons.get(b);
                    if (nextScene == null)
                    {
                        System.exit(0);
                    }
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * @param button A button given to the method
     * @param Scene  the scene the button is to be placed inside of.
     * @return none
     * @throws none
     * @updateButtons This method receives a button and a scene, putting the button into the scene.
     */
    public void updateButtons()
    {
        for (Button b : buttons.keySet())
        {
            b.update();
        }
        adjustButtons();
    }

    /**
     * @param none
     * @return none
     * @throws none
     * @drawButtons This method draws all buttons in keyset.
     */
    public void drawButtons()
    {
        for (Button b : buttons.keySet())
        {
            b.draw();
        }
    }

    /**
     * @param none
     * @return none
     * @throws none
     * @adjustButtons This method adjusts the size of the buttons within the keySet.
     */
    public void adjustButtons()
    {
        int lastY = titleSprite.getHitBox().getY() + titleSprite.getHitBox().getHeight() + 32;
        for (Button b : buttons.keySet())
        {
            int x = (Display.getWidth() / 2) - (b.getHitBox().getWidth() / 2);
            b.getHitBox().setLocation(x, lastY);
            lastY += b.getHitBox().getHeight() + 16;
        }
    }

    /**
     * @param none
     * @return menu
     * @throws none
     * @nextScene This method controls the pause menu, allowing for access to the main menu, resuming the game, or heading directly to the options menu.
     */
    public Scene nextScene()
    {
        AudioManager.getInstance().play("resume");
        return nextScene;
    }

    /**
     * @param none
     * @return none
     * @throws none
     * @reloadFont This method is used for reloading the fonts of all the button in keySet.
     */
    @Override
    public void reloadFont()
    {
        for (Button b : buttons.keySet())
        {
            b.reloadFont();
        }
    }
}