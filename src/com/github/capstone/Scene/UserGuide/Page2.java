package com.github.capstone.Scene.UserGuide;

import com.github.capstone.Scene.Menus.MainMenu;
import org.lwjgl.opengl.Display;

public class Page2 extends GuidePage
{
    public Page2(MainMenu menu)
    {
        super(new Page3(menu));
        // TODO: Everyone needs to set this text below, and where they want the textX and textY to begin.
        this.textX = 16;
        this.textY = (Display.getHeight()/ 8) - (3 * this.font.getHeight());
        this.pageContent = "Beginning your game <br> <br> <br> <br> <br> <br>After initially starting the game, you arrive at the main menu. <br>"
				+"This is where you can elect to begin a new game,<br>" + 
				"head into the options menu to really show your style,<br>" +
				"or exit the game \t \t  (Please don't go, we'll miss you!)"; 
    }
}