package com.github.capstone.Scene;

import com.github.capstone.Entity.EntityBase;
import com.github.capstone.Entity.EntityPiece;
import com.github.capstone.Entity.EntityTetromino;
import com.github.capstone.Twotris;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.TextureImpl;

import java.awt.*;
import java.util.ArrayList;

public class Game extends Scene
{
    private boolean isGameOver;
    private int score;
    private TrueTypeFont font;
    private ArrayList<EntityBase> entities;

    public Game()
    {
        entities = new ArrayList<>();
        entities.add(new EntityTetromino());

        this.isGameOver = false;
        this.font = new TrueTypeFont(new Font("Arial", Font.PLAIN, 14), false);
    }

    @Override
    public boolean drawFrame(float delta)
    {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        GL11.glClearColor(0.15F, 0.15F, 0.15F, 0F);
        // Screen resize handler
        if (Display.wasResized())
        {
            GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glLoadIdentity();
            GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
        }
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

        // TODO: Insert working logic here
        for (EntityBase e : entities)
        {
            e.update(delta);
        }

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
        if ((Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) && Keyboard.isKeyDown(Keyboard.KEY_EQUALS))
        {
            this.score++;
        }

        // TODO: Put working draw code here:
        for (EntityBase e : entities)
        {
            e.draw();
        }
        TextureImpl.bindNone();
        font.drawString(0, 0, "" + this.score);
        return !isGameOver;
    }

    @Override
    public Scene nextScene()
    {
        Menu menu = new Menu("gui/paused");
        if (isGameOver)
        {
            menu.addButton(new Button(0, 0, "Play Again"), new Game());
        }
        else
        {
            menu.addButton(new Button(0, 0, "Resume"), this);
        }
        menu.addButton(new Button(0, 0, "Save & Quit"), new MainMenu());
        menu.adjustButtons();
        return menu;
    }
}
