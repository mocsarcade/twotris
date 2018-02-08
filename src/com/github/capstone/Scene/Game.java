package com.github.capstone.Scene;

import com.github.capstone.Entity.EntityBase;
import com.github.capstone.Entity.EntityTetromino;
import com.github.capstone.Twotris;
import com.github.capstone.Util.Helper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.TextureImpl;

import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;

public class Game extends Scene
{
    private boolean isGameOver;
    private int score;
    private TrueTypeFont font;
    private ArrayList<EntityBase> entities;
/**
@Game
This constructor method creates an arraylist of entities and adds a new Tetromino entity to it. Also, sets ‘gameover’ to false and gets the font from the helper. 
@param none 
@return none
@throws none
*/
    public Game()
    {
        entities = new ArrayList<>();
        entities.add(new EntityTetromino());

        this.isGameOver = false;
        this.font = Helper.getFont();
    }
/**
@drawFrame
This method Is able to resize the screen, and is responsible for updating the entities by delta, adjusting fullscreen, the score displayed, and controls when the game is over. 
@param delta
@return isGameOver true/false. 
@throws none
*/
    @Override
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
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
        {
            return false;
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
/**
@nextScene
This method controls the pause menu, allowing for access to the main menu, resuming the game, or heading directly to the options menu. 
@param none 
@return menu 
@throws none
*/
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
            menu.addButton(new Button(0, 0, "Options"), new Options(this));
        }
        menu.addButton(new Button(0, 0, "Save & Quit"), new MainMenu());
        menu.adjustButtons();
        return menu;
    }
/**
@reloadFont
This method is used for reloading the game’s font.  
@param none 
@return none
@throws none
*/
    @Override
    public void reloadFont()
    {
        this.font = Helper.getFont();
    }
}
