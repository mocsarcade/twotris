package com.github.capstone.Scene.Menus;

import com.github.capstone.Scene.Components.Button;
import com.github.capstone.Scene.Components.ButtonKeybind;
import com.github.capstone.Scene.Scene;
import com.github.capstone.Twotris;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import java.awt.Toolkit;
import java.util.ArrayList;

public class OptionsKeybinds extends Scene
{
    private Button back;
    private Scene next;
    private ArrayList<ButtonKeybind> buttons;

    /**
     * @param lastscene The scene supplied.
     * @return none
     * @throws none
     * @Options This constructor method adds button to a linkedhashmap, added the buttons to the options menu, adds a back button, adjusts the buttons, and then creates the next as the last scene.
     */
    public OptionsKeybinds(Scene lastScene)
    {
        this.buttons = new ArrayList<>();
        this.buttons.add(new ButtonKeybind(0, 0, "Left", Twotris.getInstance().keybinds.moveLeft));
        this.buttons.add(new ButtonKeybind(0, 0, "Right", Twotris.getInstance().keybinds.moveRight));
        this.buttons.add(new ButtonKeybind(0, 0, "Rotate", Twotris.getInstance().keybinds.rotate));
        this.buttons.add(new ButtonKeybind(0, 0, "Accel", Twotris.getInstance().keybinds.accelerate));
        this.buttons.add(new ButtonKeybind(0, 0, "Place", Twotris.getInstance().keybinds.place));
        this.buttons.add(new ButtonKeybind(0, 0, "Menu/Back", Twotris.getInstance().keybinds.menuBack));
        back = new Button(256, 64, "Back");
        this.adjustButtons();
        this.next = lastScene;
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

        return !back.isClicked();
    }


    /**
     * @param none
     * @return none
     * @throws none
     * @updateButtons This method updates the back button, and the other buttons in the keyset, then adjusts them.
     */
    public void updateButtons()
    {
        for (ButtonKeybind b : buttons)
        {
            b.update();
        }
        back.update();
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
        for (ButtonKeybind b : buttons)
        {
            b.draw();
        }
        back.draw();
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
        for (ButtonKeybind b : buttons)
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
        back.reloadFont();
        next.reloadFont();
    }

}