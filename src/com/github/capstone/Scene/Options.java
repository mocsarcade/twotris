package com.github.capstone.Scene;

import com.github.capstone.Twotris;
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


    Options()
    {
        buttons = new LinkedHashMap<>();
        Twotris.getInstance().config.addButtonsToOptionsGUI(this);
        back = new Button(256, 64, "Back");
        this.adjustButtons();
    }

    public void addButton(String option)
    {
        this.buttons.put(new Button(256, 64, option), option);
    }

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

    public void updateButtons()
    {
        back.update();
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
        back.draw();

        for (Button b : buttons.keySet())
        {
            b.draw();
        }
    }

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

    public Scene nextScene()
    {
        return new MainMenu();
    }
}