package com.github.capstone.Scene.Menus;

import com.github.capstone.Manager.AudioManager;
import com.github.capstone.Scene.Components.Button;
import com.github.capstone.Scene.Components.TitleSprite;
import com.github.capstone.Scene.Game;
import com.github.capstone.Scene.Scene;
import com.github.capstone.Twotris;
import com.github.capstone.Util.Helper;
import com.github.capstone.Util.Textures;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

import java.util.LinkedHashMap;

public class Menu extends Scene
{
    private LinkedHashMap<Button, Scene> buttons;
    private LinkedHashMap<String, Integer[]> optTexts;
    private Scene nextScene;
    private TitleSprite titleSprite;

    private int curs; private long lastKeypress;

    /**
     * @param title The String include for the title.
     * @return none
     * @throws none
     * @Menu This constructor method adds button to a linkedhashmap and creates a titlesprite.
     */
    public Menu(Texture texture, boolean noColorOverlay)
    {
        this.buttons = new LinkedHashMap<>();
        this.titleSprite = new TitleSprite(texture, noColorOverlay);
        this.optTexts = new LinkedHashMap<>();
        this.curs = 0;
        try
        {
            Mouse.setNativeCursor(null);
        }
        catch (LWJGLException ignored)
        {
        }
    }

    /**
     * @param title The String include for the title.
     * @return none
     * @throws none
     * @Menu This constructor method adds button to a linkedhashmap and creates a titlesprite.
     */
    public Menu(Texture texture)
    {
        this.buttons = new LinkedHashMap<>();
        this.titleSprite = new TitleSprite(texture);
        this.optTexts = new LinkedHashMap<>();
        this.curs = 0;
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
        this.buttons.put(button, scene);
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

        for (String s : optTexts.keySet())
        {
            Textures.FONT.drawString(optTexts.get(s)[0], optTexts.get(s)[1], s);
        }

        if (Display.wasResized())
        {
            for (Button b : buttons.keySet())
            {
                if (buttons.get(b) instanceof Game)
                {
                    buttons.put(b, new Game());
                }
            }
        }

        if (Keyboard.isKeyDown(Twotris.getInstance().keybinds.screenshot))
        {
            Twotris.getInstance().screenshotManager.takeScreenshot();
        }

        //If left or right is pushed, move cursor
        if (Keyboard.isKeyDown(Twotris.getInstance().keybinds.moveRight) && Helper.getTime() - lastKeypress > 150)
        {
            curs++;
            if(curs >= buttons.keySet().size()) {
              curs = 0;
            }
            moveMouseOver(curs);
            lastKeypress = Helper.getTime();
        }
        if (Keyboard.isKeyDown(Twotris.getInstance().keybinds.moveLeft) && Helper.getTime() - lastKeypress > 150)
        {
            curs--;
            if(curs < 0) {
              curs = buttons.keySet().size()-1;
            }
            moveMouseOver(curs);
            lastKeypress = Helper.getTime();
        }

        //Select button if left-click or A(cellerate) is pushed
        if (Mouse.isButtonDown(0) || Keyboard.isKeyDown(Twotris.getInstance().keybinds.accelerate)
            || Keyboard.isKeyDown(Twotris.getInstance().keybinds.rotate))
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

        if (this.isKeyPressed(Twotris.getInstance().keybinds.menuBack) && this.titleSprite.isPauseMenu())
        {
            for (Button b : buttons.keySet())
            {
                if (buttons.get(b) instanceof Game)
                {
                    this.nextScene = buttons.get(b);
                    break;
                }
            }
            return false;
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

    public void moveMouseOver(int curs) {
      //Move mouse
      int pos=0;
      for (Button b : buttons.keySet())
      {
        if(pos==curs) {
          Mouse.setCursorPosition((b.getHitBox().getX() + (b.getHitBox().getWidth() / 2)), Display.getHeight() - (b.getHitBox().getY() + (b.getHitBox().getHeight() / 2)));
          break;
        }
        pos++;
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
        for (Button b : buttons.keySet())
        {
            if (b.getText().toLowerCase().contains("resume"))
            {
                AudioManager.getInstance().play("resume");
            }
        }
        return nextScene;
    }

    public void addSplashText(int x, int y, String toDraw)
    {
        optTexts.put(toDraw, new Integer[]{x, y});
    }

    public void addSplashText(int y, String toDraw)
    {
        addSplashText((Display.getWidth() / 2) - (Textures.FONT.getWidth(toDraw) / 2), y, toDraw);
    }
}
