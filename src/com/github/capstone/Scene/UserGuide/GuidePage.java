package com.github.capstone.Scene.UserGuide;

import com.github.capstone.Scene.Components.Button;
import com.github.capstone.Scene.Components.FakeTetromino;
import com.github.capstone.Scene.Components.TitleSprite;
import com.github.capstone.Scene.Menus.MainMenu;
import com.github.capstone.Scene.Scene;
import com.github.capstone.Twotris;
import com.github.capstone.Util.Helper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.TextureImpl;

import java.util.ArrayList;

public class GuidePage extends Scene
{

    private Button nextButton;
    private ArrayList<FakeTetromino> pieces;
    private TitleSprite title;
    private Scene next;
    private Scene mainMenu;
    TrueTypeFont font;
    String pageContent;
    int textX = 0;
    int textY = 0;


    GuidePage(Scene next)
    {
        this.font = Helper.getFont();
        this.nextButton = new Button(0, 0, "Next");
        this.title = new TitleSprite("gui/title");
        this.next = next;
        if (next instanceof MainMenu)
        {
            this.mainMenu = next;
        }
        this.createTetrominos();
    }

    @Override
    public boolean drawFrame(float delta)
    {
        this.nextButton.update();
        this.nextButton.getHitBox().setLocation(Display.getWidth() - this.nextButton.getHitBox().getWidth() - 16, Display.getHeight() - this.nextButton.getHitBox().getHeight() - 16);
        this.title.getHitBox().setLocation(Display.getWidth() - this.title.getHitBox().getWidth(), 0);

        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        GL11.glClearColor(.09F, 0.09F, 0.09F, 0F);

        this.nextButton.draw();
        this.title.draw();
        this.drawTetromino();

        TextureImpl.bindNone();
        // Loop for handling newlines:
        int y = textY;
        for (String part : this.pageContent.split("<br>"))
        {
            font.drawString(textX, y, part);
            y += font.getHeight(part);
        }

        // Screen resize handler
        if (Display.wasResized())
        {
            GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glLoadIdentity();
            GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
            createTetrominos();
        }

        if (this.nextButton.isClicked())
        {
            return false;
        }
        else if (Keyboard.isKeyDown(Twotris.getInstance().keybinds.menuBack))
        {
            return false;
        }
        return true;
    }

    private void drawTetromino()
    {
        for (FakeTetromino fake : pieces)
        {
            fake.draw();
        }
    }

    private void createTetrominos()
    {
        int size = 32 + ((Display.getWidth() - 600) / 32);
        this.pieces = new ArrayList<>();
        this.pieces.add(new FakeTetromino(4, size, 0, 0, Display.getHeight() - (2 * size)));
        this.pieces.add(new FakeTetromino(3, size, 2, 0, Display.getHeight() - (4 * size)));
        this.pieces.add(new FakeTetromino(6, size, 0, 0, Display.getHeight() - (6 * size)));
        this.pieces.add(new FakeTetromino(5, size, 1, 0, Display.getHeight() - (9 * size)));
        this.pieces.add(new FakeTetromino(6, size, 0, 2 * size, Display.getHeight() - (3 * size)));
        this.pieces.add(new FakeTetromino(5, size, 0, 3 * size, Display.getHeight() - size));
        this.pieces.add(new FakeTetromino(0, size, 2, 3 * size, Display.getHeight() - (4 * size)));
    }

    @Override
    public void reloadFont()
    {
        this.font = Helper.getFont();
    }

    @Override
    public void resizeContents()
    {
        createTetrominos();
    }

    @Override
    public Scene nextScene()
    {
        if (Keyboard.isKeyDown(Twotris.getInstance().keybinds.menuBack))
        {
            return this.mainMenu;
        }
        return next;
    }
}
