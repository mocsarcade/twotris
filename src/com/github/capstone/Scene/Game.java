package com.github.capstone.Scene;

import com.github.capstone.Components.Grid;
import com.github.capstone.Manager.AudioManager;
import com.github.capstone.Scene.Components.Button;
import com.github.capstone.Scene.Menus.MainMenu;
import com.github.capstone.Scene.Menus.Menu;
import com.github.capstone.Scene.Menus.Options;
import com.github.capstone.Twotris;
import com.github.capstone.Util.Helper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.TextureImpl;

import java.awt.Toolkit;

public class Game extends Scene
{
    private boolean isGameOver;
    private TrueTypeFont font;
    private Menu pauseMenu;
    private Grid grid;

    /**
     * @param none
     * @return none
     * @throws none
     * @Game This constructor method initializes the piece manager. Also, sets ‘gameover’ to false and gets the font from the helper.
     */
    public Game()
    {
        this.font = Helper.getFont();
        this.grid = new Grid();
    }

    /**
     * @updatePauseMenu this method updates the pause menu to contain EITHER resume or play again depending on game status
     */
    private void updatePauseMenu()
    {
        pauseMenu = this.isGameOver ? new com.github.capstone.Scene.Menus.Menu("gui/game_over") : new Menu("gui/paused");
        if (isGameOver)
        {
            pauseMenu.addSplashText(Display.getHeight() - 32, "Game Score: " + this.grid.getScore());
            pauseMenu.addButton(new com.github.capstone.Scene.Components.Button(0, 0, "Play Again"), new Game());
            pauseMenu.addButton(new com.github.capstone.Scene.Components.Button(0, 0, "Main Menu"), new MainMenu());
        }
        else
        {
            pauseMenu.addButton(new com.github.capstone.Scene.Components.Button(0, 0, "Resume"), this);
            pauseMenu.addButton(new com.github.capstone.Scene.Components.Button(0, 0, "Options"), new Options(this));
            pauseMenu.addButton(new Button(0, 0, "Save & Quit"), new MainMenu());
        }
        pauseMenu.adjustButtons();
    }

    /**
     * @param delta
     * @return isGameOver true/false.
     * @throws none
     * @drawFrame This method Is able to resize the screen, and is responsible for updating the entities by delta, adjusting fullscreen, the score displayed, and controls when the game is over.
     */
    @Override
    public boolean drawFrame(float delta)
    {
        if (!Mouse.isGrabbed())
        {
            Mouse.setGrabbed(true);
        }
        // Instantiate the menu if it hasn't been yet
        if (this.pauseMenu == null)
        {
            this.updatePauseMenu();
        }
        if (this.isGameOver)
        {
            updatePauseMenu();
        }
        if (Display.wasResized())
        {
            this.grid = new Grid();
        }

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
        grid.update(delta);

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
            this.grid = new Grid();
        }
        if (Keyboard.isKeyDown(Twotris.getInstance().keybinds.menuBack))
        {
            AudioManager.getInstance().play("pause");
            return false;
        }

        // TODO: Put working draw code here:
        grid.draw();

        this.isGameOver = grid.isGameOver();
        if (this.isGameOver)
        {
            updatePauseMenu();
        }

        TextureImpl.bindNone();
        font.drawString(0, 0, "Score: " + grid.getScore());
        return !isGameOver;
    }

    /**
     * @param none
     * @return menu
     * @throws none
     * @nextScene This method controls the pause menu, allowing for access to the main menu, resuming the game, or heading directly to the options menu.
     */
    @Override
    public Scene nextScene()
    {
        Mouse.setGrabbed(false);
        return this.pauseMenu;
    }

    /**
     * @param none
     * @return none
     * @throws none
     * @reloadFont This method is used for reloading the game’s font.
     */
    @Override
    public void reloadFont()
    {
        this.font = Helper.getFont();
        this.grid.reloadFont();
        this.pauseMenu.reloadFont();
    }
}
