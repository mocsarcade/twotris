package com.github.capstone.Scene;

import com.github.capstone.Twotris;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.TrueTypeFont;

import java.awt.*;

public class Game extends Scene
{
    private boolean isGameOver;
    private int score;
    private TrueTypeFont font;

    public Game()
    {
        this.isGameOver = false;
        this.font = new TrueTypeFont(new Font("Arial", Font.PLAIN, 32), false);
    }

    @Override
    public boolean drawFrame(float delta)
    {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        GL11.glClearColor(0.25F, 0.25F, 0.25F, 0F);
        // Screen resize handler
        if (Display.wasResized())
        {
            GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glLoadIdentity();
            GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
        }
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

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
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
        {
            return false;
        }

        font.drawString(0, 0, "" + this.score);
        return !isGameOver;
    }

    @Override
    public Scene nextScene()
    {
        Menu menu = new Menu();
        if (isGameOver)
        {
            menu.addButton(new Button(256, 64, "Play Again", new org.newdawn.slick.Color(200, 200, 200), new org.newdawn.slick.Color(85, 124, 0)), new Game());
        }
        else
        {
            menu.addButton(new Button(256, 64, "Resume", new org.newdawn.slick.Color(200, 200, 200), new org.newdawn.slick.Color(85, 124, 0)), this);
        }
        menu.addButton(new Button(256, 64, "Exit", new org.newdawn.slick.Color(200, 200, 200), new org.newdawn.slick.Color(85, 124, 0)), null);
        menu.adjustButtons();
        return menu;
    }
}
