package com.github.capstone.Scene.Menus;

import com.github.capstone.Scene.Components.Button;
import com.github.capstone.Scene.Scene;
import com.github.capstone.Twotris;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.LinkedHashMap;

public class Options extends Scene
{
    private Button back;
    private Button keybinds;
    private LinkedHashMap<Button, String> buttons;
    private Scene last;
    private Scene next;

    /**
     * @param lastscene The scene supplied.
     * @return none
     * @throws none
     * @Options This constructor method adds button to a linkedhashmap, added the buttons to the options menu, adds a back button, adjusts the buttons, and then creates the next as the last scene.
     */
    public Options(Scene lastScene)
    {
        buttons = new LinkedHashMap<>();
        Twotris.getInstance().config.addButtonsToOptionsGUI(this);
        keybinds = new Button(256, 64, "Keybinds");
        back = new Button(256, 64, "Back");
        this.adjustButtons();
        this.last = lastScene;
    }

    /**
     * @param option The String given.
     * @return none
     * @throws none
     * @addButton This method receives buttons and puts them into the options menu.
     */
    public void addButton(String option)
    {
        this.buttons.put(new Button(256, 64, option), option);
    }

    /**
     * @param delta
     * @return isGameOver true/false.
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
                last.reloadFont();
                this.next = this.last;
                return false;
            }
            else if (keybinds.isClicked())
            {
                this.next = new OptionsKeybinds(this);
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
     * @param none
     * @return none
     * @throws none
     * @updateButtons This method updates the back button, and the other buttons in the keyset, then adjusts them.
     */
    public void updateButtons()
    {
        back.update();
        keybinds.update();
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
     * @drawButtons This method draws the back button, then all buttons in keyset.
     */
    public void drawButtons()
    {
        back.draw();
        keybinds.draw();

        for (Button b : buttons.keySet())
        {
            b.draw();
        }
    }

    /**
     * @param none
     * @return none
     * @throws none
     * @adjustButtons This method adjusts the SIZE of the buttons within the keySet.
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
        keybinds.getHitBox().setLocation(Display.getWidth() - keybinds.getHitBox().getWidth() - 16, Display.getHeight() - back.getHitBox().getHeight() - 16);
    }

    /**
     * @param none
     * @return next
     * @throws none
     * @nextScene This method sets the next scene, setting the next selection.
     */
    public Scene nextScene()
    {
        return next;
    }

    /**
     * @param none
     * @return none
     * @throws none
     * @reloadFont This method is used for reloading the buttons’ from the keySet fonts as well as the back button.
     */
    @Override
    public void reloadFont()
    {
        for (Button b : buttons.keySet())
        {
            b.reloadFont();
        }
        keybinds.reloadFont();
        back.reloadFont();
    }

}