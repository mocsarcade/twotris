package com.github.capstone.Scene.UserGuide;

import com.github.capstone.Scene.Menus.MainMenu;
import org.lwjgl.opengl.Display;

public class Page3 extends GuidePage
{
    public Page3(MainMenu menu)
    {
        super(new Page4(menu));
        // TODO: Everyone needs to set this text below, and where they want the textX and textY to begin.
        this.textX = 16;
        this.textY = (Display.getHeight() / 8) - (3 * this.font.getHeight());
        this.pageContent = "Starting a new game <br> <br> <br> <br> <br>Let's check out the options menu first. <br>" + 
				"This is where you can switch up the font choices and color schemes,<br>" + 
				"or adjust game-play keys or sound levels to exactly your liking.";
    }
}