package com.github.capstone.Scene;

import com.github.capstone.Components.Grid;
import com.github.capstone.Manager.AudioManager;
import com.github.capstone.Scene.Components.Button;
import com.github.capstone.Scene.Menus.MainMenu;
import com.github.capstone.Scene.Menus.Menu;
import com.github.capstone.Scene.Menus.Options;
import com.github.capstone.Twotris;
import com.github.capstone.Util.Textures;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class Game extends Scene
{
    private boolean isGameOver;
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
        this.grid = new Grid();
    }

    /**
     * @updatePauseMenu this method updates the pause menu to contain EITHER resume or play again depending on game status
     */
    private void updatePauseMenu()
    {
        pauseMenu = this.isGameOver ? new Menu(Textures.GAME_OVER, true) : new Menu(Textures.PAUSED);
        if (isGameOver)
        {
            pauseMenu.addSplashText(Display.getHeight() - 32, "Game Score: " + this.grid.getScore());
            pauseMenu.addButton(new Button(0, 0, "Play Again"), new Game());
            pauseMenu.addButton(new Button(0, 0, "Main Menu"), new MainMenu());
        }
        else
        {
            pauseMenu.addButton(new Button(0, 0, "Resume"), this);
            pauseMenu.addButton(new Button(0, 0, "Options"), new Options(this));
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
        this.grid.update(delta);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        GL11.glClearColor(.09F, 0.09F, 0.09F, 0F);
        this.grid.draw();

        // Grab the mouse if it isn't and this scene is active
        if (!Mouse.isGrabbed())
        {
            Mouse.setGrabbed(true);
        }

        // Instantiate the menu if it hasn't been yet
        this.isGameOver = grid.isGameOver();
        if (this.pauseMenu == null || this.isGameOver)
        {
            this.updatePauseMenu();
        }
        // Keypress handlers:
        if (Keyboard.isKeyDown(Twotris.getInstance().keybinds.screenshot))
        {
            Twotris.getInstance().screenshotManager.takeScreenshot();
        }
        if (this.isKeyPressed(Twotris.getInstance().keybinds.menuBack))
        {
            AudioManager.getInstance().play("pause");
            return false;
        }

        // If the window isn't the active one, pause:
        if (!Display.isActive())
        {
            AudioManager.getInstance().play("pause");
            return false;
        }

        // Screen resize handler
        if (Display.wasResized())
        {
            GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glLoadIdentity();
            GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
            this.grid = new Grid();
        }

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

    @Override
    public void resizeContents()
    {
        this.grid = new Grid();
    }

    public void recolor()
    {
        grid.recolor();
    }
}
