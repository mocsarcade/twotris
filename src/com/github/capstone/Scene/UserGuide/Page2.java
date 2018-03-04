package com.github.capstone.Scene.UserGuide;

import com.github.capstone.Scene.Menus.MainMenu;
import org.lwjgl.opengl.Display;

public class Page2 extends GuidePage
{
    public Page2(MainMenu menu)
    {
        super(new Page3(menu));
        this.textX = 32;
        this.textY = (Display.getHeight() / 4);
        this.title = "Beginning your game";
        this.pageContent = "After initially starting the game, you arrive at the main menu. <br>"
                + "This is where you can elect to begin a new game,<br>" +
                "head into the options menu to really show your style,<br>" +
                " or exit the game.        (Please don't go, we'll miss you!)";
    }
}