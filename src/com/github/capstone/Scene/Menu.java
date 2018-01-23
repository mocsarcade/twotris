package com.github.capstone.Scene;

import com.github.capstone.Twotris;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import java.awt.Toolkit;
import java.util.LinkedHashMap;

public class Menu extends Scene
{
    private LinkedHashMap<Button, Scene> buttons;
    private Scene nextScene;

    public Menu()
    {
        buttons = new LinkedHashMap<>();
        try
        {
            Mouse.setNativeCursor(null);
        }
        catch (LWJGLException ignored)
        {
        }
    }

    public void addButton(Button button, Scene scene)
    {
        buttons.put(button, scene);
    }

    public boolean drawFrame(float delta)
    {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        GL11.glClearColor(0.25F, 0.25F, 0.25F, 0F);
        TitleSprite title = new TitleSprite();
        // Screen resize handler
        if (Display.wasResized())
        {
            GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glLoadIdentity();
            GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
        }
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        title.draw();
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
                Twotris.getInstance().setDisplayMode(Twotris.getInstance().config.resolutionWidth, Twotris.getInstance().config.resolutionHeight, false);
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

    public void updateButtons()
    {
        for (Button b : buttons.keySet())
        {
            b.update();
            if (Display.wasResized())
            {
                adjustButtons();
            }
        }
    }

    public void drawButtons()
    {
        for (Button b : buttons.keySet())
        {
            b.draw();
        }
    }

    public void adjustButtons()
    {
        int buttonCount = buttons.keySet().size();
        for (Button b : buttons.keySet())
        {
            int x = (Display.getWidth() / 2) - (b.getHitBox().getWidth() / 2);
            int y = (Display.getHeight() / 2) - (b.getHitBox().getHeight() * buttonCount) + (b.getHitBox().getHeight() / buttonCount);
            b.getHitBox().setLocation(x, y);
            buttonCount -= 1;
        }
    }

    public Scene nextScene()
    {
        return nextScene;
    }
}